package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import model.Event;
import model.User;

import com.google.appengine.api.datastore.Key;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@SuppressWarnings("serial")
@WebServlet(name = "eventDetails", value = "/eventdetails")
public class EventDetails extends HttpServlet {

	DatastoreService datastore;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String paramName = "id";
		String paramValue = req.getParameter(paramName);
		System.out.println(paramValue);
		Key keyEvent = KeyFactory.createKey("Event", Long.parseLong(paramValue));
		Key keyUser = null;
		if (req.getUserPrincipal() != null){
		keyUser = KeyFactory.createKey("User", req.getUserPrincipal().getName());
		}
		Entity e1 = null;
		Entity eUser = null;
		Event event = null;
		boolean dolaczyl = false;
		boolean inEvent = false;
		boolean isAdmin = false;
		boolean isMember = false;
		User user = null;
		if (req.getUserPrincipal() != null
				&& req.getUserPrincipal().getName().equalsIgnoreCase("19madoy94@gmail.com")) {
			isAdmin = true;
		}
		try {
			e1 = datastore.get(keyEvent);
			event = new Event(e1.getKey().getId(), e1.getProperty("name").toString(), e1.getProperty("data").toString(),
					e1.getProperty("description").toString());
			if (req.getUserPrincipal() != null){
			eUser = datastore.get(keyUser);
			user = new User(eUser.getKey().getName(), (Key) eUser.getProperty("Event"));
			}
			
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (req.getUserPrincipal() != null) {
			isMember = true;
			Key onEvent = null;
			if(user!=null){
			onEvent = user.getEvent();
			}
			System.out.println("+" + onEvent + "+" + " " + "+" + keyEvent + "+");
			if (onEvent != null) {
				if (onEvent.toString().equalsIgnoreCase(keyEvent.toString())) {
					dolaczyl = true;
				}else{
					inEvent = true;
				}
			}
		}
		List<String> lista = (ArrayList<String>) e1.getProperty("list");
		System.out.println(lista);
		
		req.setAttribute("Event", event);
		req.setAttribute("Dolaczyl", dolaczyl);
		req.setAttribute("isAdmin", isAdmin);
		req.setAttribute("isMember", isMember);
		req.setAttribute("inEvent", inEvent);
		req.setAttribute("UserList", lista);
		req.getRequestDispatcher("/eventdetails.jsp").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {

		// setup datastore service
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
}