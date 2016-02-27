package es.upm.dit.isst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.offers.dao.OfferDAO;
import es.upm.dit.isst.offers.dao.OfferDAOImpl;
import es.upm.dit.isst.offers.model.Offer;

public class OffersDashboard extends HttpServlet{
	
	private static final Long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		OfferDAO dao = OfferDAOImpl.getInstance();
		List<Offer> offers = new ArrayList<Offer>();
		offers = dao.listOffers();
		req.getSession().setAttribute("offers",
				new ArrayList<Offer>(offers));
		
	
		if (user == null){
			resp.sendRedirect("/"); //si no estoy logueado me devuelve al inicio
			
		} else{
			RequestDispatcher view = req.getRequestDispatcher("OffersDashboard.jsp");
	        view.forward(req, resp);
		}
		
		
		
	}

}
