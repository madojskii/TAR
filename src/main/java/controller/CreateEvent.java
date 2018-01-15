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

import model.User;

import com.google.appengine.api.datastore.Key;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@SuppressWarnings("serial")
@WebServlet(name = "createBlogPost", value = "/create")
public class CreateEvent extends HttpServlet {

	DatastoreService datastore;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//System.out.println(req.getUserPrincipal().getName()+ " test");
		
		if (req.getUserPrincipal() != null && req.getUserPrincipal().getName().equalsIgnoreCase("19madoy94@gmail.com")) {
			// Create a map of the httpParameters that we want and run it
			// through jSoup
			Map<String, String> eventContent = req.getParameterMap().entrySet().stream()
					.collect(Collectors.toMap(p -> p.getKey(), p -> Jsoup.clean(p.getValue()[0], Whitelist.basic())));

			Entity post = new Entity("Event"); // create a new entity
			List<String> lista = new ArrayList<String>();
			lista.add("");
			post.setProperty("name", eventContent.get("name"));
			post.setProperty("data", eventContent.get("data"));
			post.setProperty("description", eventContent.get("description"));
			post.setProperty("list", lista);
			post.setProperty("timestamp", new Date().getTime());

			try {
				datastore.put(post); // store the entity

				// Send the user to the confirmation page with personalised
				// confirmation text
				String confirmation = "Post with tytul " + eventContent.get("name") + " created.";

				// req.setAttribute("confirmation", confirmation);
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			} catch (DatastoreFailureException e) {
				throw new ServletException("Datastore error", e);
			}

		}else{
			req.getRequestDispatcher("/test.jsp").forward(req, resp);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Entity e1 = datastore.get(key);
		// System.out.println("Dziala?: "+e1);
		Query q = new Query("Event");
		PreparedQuery pq = datastore.prepare(q);

		String confirmation = "Wstawiono: ";
		for (Entity e : pq.asIterable()) {
			confirmation += e.getProperty("name").toString() + " " + e.getProperty("data").toString() + "\n ";
		}
		req.setAttribute("confirmation", confirmation);
		req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {

		// setup datastore service
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
}