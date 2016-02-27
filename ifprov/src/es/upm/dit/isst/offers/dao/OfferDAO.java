package es.upm.dit.isst.offers.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.offers.model.Offer;

public interface OfferDAO {

	public void remove(long id);

	List<Offer> listOffers();

	public void add (String title, String description);

	public void update(long id, String title, String description);

	Offer getOffer(long offerId);
	
}