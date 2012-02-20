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

import org.apache.shiro.SecurityUtils;

@Stateless
public class PostEJB {

	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	@EJB
	private LoginEJB lEJB;

	public List<Post> getPosts(int id, int page) {
		TypedQuery<Post> query = em.createNamedQuery("post.byId", Post.class);
		query.setParameter(1, id);
		query.setMaxResults(5);
		query.setFirstResult(page * 5);
		return query.getResultList();
	}
	
	public long getPostsCount(int id) {
		TypedQuery<Long> query = em.createNamedQuery("post.count.byId", Long.class);
		query.setParameter(1, id);
		return query.getSingleResult();
	}

	public void addPost(int tId, String content) throws NotLoggedInException,
			InvalidIdException {
		if (!SecurityUtils.getSubject().isAuthenticated())
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
