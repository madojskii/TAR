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
@WebServlet(name = "dolacz", value = "/dolacz")
public class JoinEvent extends HttpServlet {

	DatastoreService datastore;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String paramName = "id";
		String paramValue = req.getParameter(paramName);
		System.out.println(paramValue);
		Key key = KeyFactory.createKey("Event", Long.parseLong(paramValue));
		Entity e1 = null;
		Event event = null;
		User user = new User(req.getUserPrincipal().getName());
		Entity postUser = new Entity("User", user.getEmail());
		try {
			e1 = datastore.get(key);
			event = new Event(e1.getProperty("name").toString(), e1.getProperty("data").toString(),
					e1.getProperty("description").toString());
			//ArrayList<String> retrievedFruits = (ArrayList<String>) employee.getProperty("favoriteFruit");
			ArrayList<String> users = (ArrayList<String>)e1.getProperty("list");
			event.setUsers(users);
			System.out.println(users+" +");
			users.add(user.getEmail());
			event.setUsers(users);
			user.setEvent(key);
			//postUser.setProperty("email", user.getEmail());
			postUser.setProperty("Event", key);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			datastore.put(e1); // store the entity
			datastore.put(postUser);
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		} catch (DatastoreFailureException e) {
			throw new ServletException("Datastore error", e);
		}

	}

	@Override
	public void init() throws ServletException {

		// setup datastore service
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
}