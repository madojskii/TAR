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

import com.google.appengine.api.datastore.Key;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@SuppressWarnings("serial")
@WebServlet(name = "showEvents", value="/EventList")
public class ViewEvents extends HttpServlet {
	
	DatastoreService datastore;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Entity e1 = datastore.get(key);
		System.out.println("Dziala?: ");
		Query q = new Query("Event");
		PreparedQuery pq = datastore.prepare(q);
		List<Event> lista = new ArrayList();
		String confirmation = "Wstawiono: ";
		for(Entity e:pq.asIterable()){
		 //confirmation += e.getProperty("name").toString()+" "+e.getProperty("data").toString()+"\n ";
			System.out.println(e.getKey().getId());
			Event event = new Event(e.getKey().getId(),e.getProperty("name").toString(),e.getProperty("data").toString(),e.getProperty("description").toString());
			lista.add(event);
		}
		req.setAttribute("EventList", lista);
		req.getRequestDispatcher("/eventList.jsp").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {

	  // setup datastore service
	  datastore = DatastoreServiceFactory.getDatastoreService();
	}
}