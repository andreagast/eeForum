package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Post;
import it.gas.eeforum.entities.Topic;
import it.gas.eeforum.exceptions.InvalidIdException;
import it.gas.eeforum.exceptions.NotLoggedInException;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PostEJB {

	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	@EJB
	private LoginEJB lEJB;

	public List<Post> getPosts(int id) {
		TypedQuery<Post> query = em.createNamedQuery("post.byId", Post.class);
		query.setParameter(1, id);
		return query.getResultList();
	}

	public void addPost(int tId, String content) throws NotLoggedInException,
			InvalidIdException {
		if (!lEJB.isLoggedIn())
			throw new NotLoggedInException();
		Topic t = em.find(Topic.class, tId);
		if (t == null)
			throw new InvalidIdException();
		
		Post p = new Post();
		p.setCreator(lEJB.getMember());
		p.setContent(content);
		p.setCreated(Calendar.getInstance().getTime());
		p.setTopic(t);
		em.persist(p);
		
		t.setPostsCount(t.getPostsCount() + 1);
		
		em.flush();
	}
}
