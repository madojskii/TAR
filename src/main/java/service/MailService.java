package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import model.Event;

@SuppressWarnings("serial")
@WebServlet(name = "sendMail", value = "/mail")
public class MailService extends HttpServlet {

	DatastoreService datastore;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		

			Query q = new Query("Event");
			PreparedQuery pq = datastore.prepare(q);
			List<Event> lista = new ArrayList();
			for (Entity e : pq.asIterable()) {
				// System.out.println(e.getKey().getId());
				Event event = new Event(e.getKey().getId(), e.getProperty("name").toString(),
						e.getProperty("data").toString(), e.getProperty("description").toString());
				event.setUsers((ArrayList<String>) e.getProperty("list"));
				lista.add(event);
			}
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			cal.add(Calendar.DATE, +1);
			List<String> users = new ArrayList<String>();
			for (Event e : lista) {
				String string = e.getData();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
				LocalDate date = LocalDate.parse(string, formatter);
				System.out.println(date);
				if (date.toString().equalsIgnoreCase(dateFormat.format(cal.getTime()).toString())) {
					System.out.println("tak");
					if (e.getUsers() != null && e.getUsers().size() > 1) {
						users.addAll(e.getUsers());
						System.out.println(e.getUsers().toString() + " +");
					}
				}
			}
			if (users.size() != 0) {
				System.out.println("tutaj");
				sendSimpleMail(users);
			}
			resp.getWriter().print("Sending simple email.");
	}

	/*
	 * @Override public void doPost(HttpServletRequest req, HttpServletResponse
	 * resp) throws IOException {
	 * 
	 * Query q = new Query("Event"); PreparedQuery pq = datastore.prepare(q);
	 * List<Event> lista = new ArrayList(); for (Entity e : pq.asIterable()) {
	 * // System.out.println(e.getKey().getId()); Event event = new
	 * Event(e.getKey().getId(), e.getProperty("name").toString(),
	 * e.getProperty("data").toString(),
	 * e.getProperty("description").toString());
	 * event.setUsers((ArrayList<String>) e.getProperty("list"));
	 * lista.add(event); } Calendar cal = Calendar.getInstance(); DateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd"); cal.add(Calendar.DATE,
	 * +1); List<String> users = new ArrayList<String>(); for (Event e : lista)
	 * { String string = e.getData(); DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH); LocalDate date
	 * = LocalDate.parse(string, formatter); System.out.println(date); if
	 * (date.toString().equalsIgnoreCase(dateFormat.format(cal.getTime()).
	 * toString())) { System.out.println("tak"); if (e.getUsers() != null &&
	 * e.getUsers().size() > 1) { users.addAll(e.getUsers());
	 * System.out.println(e.getUsers().toString() + " +"); } } } if
	 * (users.size() != 0) { System.out.println("tutaj"); sendSimpleMail(users);
	 * } resp.getWriter().print("Sending simple email."); }
	 */
	private void sendSimpleMail(List<String> users) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		//props.setProperty("mail.user", "emaildozaliczeniatar@gmail.com");
		//props.setProperty("mail.password", "qazxsw12");
		 Session session = Session.getDefaultInstance(props);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("19madoy94@gmail.com", "Admin"));
			// msg.addRecipient(Message.RecipientType.TO, new
			// InternetAddress("marcin.miniuk@gmail.com", "Mr. User"));
			for (String odb : users) {
				if (odb.contains("@"))
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(odb, "Mr. User"));
			}
			msg.setSubject("Przypomnienie");
			msg.setText("Przypominamy, ze wydarzenie na które się zapisałeś odbędzie się jutro.");
			Transport.send(msg);
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// ...
		}
	}

	@Override
	public void init() throws ServletException {

		// setup datastore service
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
}
