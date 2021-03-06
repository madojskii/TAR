package service;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(
    name = "UserAPI1",
    description = "UserAPI: Login / Logout with UserService",
    value = "/userapi"
)
public class UserServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    UserService userService = UserServiceFactory.getUserService();

    String thisUrl = req.getRequestURI();

    resp.setContentType("text/html");
    if (req.getUserPrincipal() != null) {
      resp.getWriter()
          .println(
              "<p>Hello, "
                  + req.getUserPrincipal().getName()
                  + "!  You can <a href=\""
                  + userService.createLogoutURL(thisUrl)
                  + "\">sign out</a>.</p>");
      req.setAttribute("userservice", userService.createLogoutURL(thisUrl));
    } else {
      resp.getWriter()
          .println(
              "<p>Please <a href=\"" + userService.createLoginURL(thisUrl) + "\">sign in</a>.</p>");
      req.setAttribute("userservice", userService.createLoginURL(thisUrl));
    }
    
    //req.getRequestDispatcher("/index.jsp").forward(req, resp);
  }
}
