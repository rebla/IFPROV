package es.upm.dit.isst.offers.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.users.User;

import es.upm.dit.isst.offers.dao.EMFService;
import es.upm.dit.isst.offers.model.Offer;


public class OfferDAOImpl implements OfferDAO {

	private static OfferDAOImpl instance;

	private OfferDAOImpl() {
	}

	public static OfferDAOImpl getInstance() {
		if (instance == null)
			instance = new OfferDAOImpl();
		return instance;
	}

	@Override
	public List<Offer> listOffers() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Offer m");
		List<Offer> offers = q.getResultList();
		return offers;
	}

	@Override
	public void add(String title, String description) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Offer offer = new Offer(title, description);
			em.persist(offer);
			em.close();
		}

	}

	@Override
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Offer offer = em.find(Offer.class, id);
			em.remove(offer);
		} finally {
			em.close();
		}
	}

	

	@Override
	public Offer getOffer(long offerId) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Offer offer = em.find(Offer.class, offerId);
			return offer;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(long offerId, String title, String description) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Offer offer = em.find(Offer.class, offerId);
			offer.setTitle(title);
			offer.setDescription(description);
			em.merge(offer);
		} finally {
			em.close();
		}
	}
	

}
