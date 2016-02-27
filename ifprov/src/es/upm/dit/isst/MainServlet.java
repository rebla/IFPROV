package es.upm.dit.isst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/*import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Resource;*/

public class MainServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		/*ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		GroupDAO groupDAO = GroupDAOImpl.getInstance();
		ConfigDAO configDAO = ConfigDAOImpl.getInstance();
		AppUserDAO appUserDAO = AppUserDAOImpl.getInstance();*/

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		/*AppUser appUser = null;*/
		
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		/*List<Resource> resources = new ArrayList<Resource>();*/
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    /*resources = resourceDAO.listResources();
			appUser = appUserDAO.getUser(user.getUserId());*/
		}
		
		/*List<Group> groups = groupDAO.listGroups();
		
		Config config = configDAO.getConfig("global");*/
		
		//req.getSession().setAttribute("groups", new ArrayList<Group>(groups));
		req.getSession().setAttribute("user", user);
		//req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		//Contador para mensajes Flash
	    Object counter = req.getSession().getAttribute("Count");
		if (counter == null) {
			req.getSession().setAttribute("Count", 2);
		} else {
			if (req.getSession().getAttribute("flashMessageSuccess") != null){
				int count = (Integer) counter;
				count--;
			    if (count == 0) {
			        count = 2;
			        req.getSession().setAttribute("Count", count);
					req.getSession().setAttribute("flashMessageSuccess", null);
			    } else {
			    	req.getSession().setAttribute("Count", count);
			    }
			} else {
		    	req.getSession().setAttribute("Count", 2);

			}
		}	
		
		try {
			boolean admin = userService.isUserAdmin();
			if (admin) {
				/*if (config == null){
					RequestDispatcher view = req.getRequestDispatcher("SetConfig.jsp");
			        view.forward(req, resp);
				} else {*/
					//req.getSession().setAttribute("config", config);
					RequestDispatcher view = req.getRequestDispatcher("menu.jsp");
			        view.forward(req, resp);
				//}

			}
			else {
				if (user != null){
				//if (user != null && appUser == null){
				//	resp.sendRedirect("/main");
				//} else {
					//req.getSession().setAttribute("appUser", appUser);
					RequestDispatcher view = req.getRequestDispatcher("menu.jsp");
			        view.forward(req, resp);
				}
			}
		} catch (IllegalStateException e){
			System.out.println("User is not logged in");
			RequestDispatcher view = req.getRequestDispatcher("index.jsp");
	        view.forward(req, resp);
		}
		

		
	}

}
