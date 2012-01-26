package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Board;
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
public class TopicEJB {

	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	@EJB
	private ForumEJB fEJB;
	@EJB
	private LoginEJB lEJB;

	public List<Topic> getTopics(int boardId) {
		TypedQuery<Topic> query = em.createNamedQuery("topic.byId", Topic.class);
		query.setParameter(1, boardId);
		return query.getResultList();
	}

	public Topic getTopic(int topicId) throws InvalidIdException {
		Topic t = em.find(Topic.class, topicId);
		if (t == null)
			throw new InvalidIdException();
		return t;
	}

	public Topic addTopic(int boardId, String title, String content)
			throws NotLoggedInException, InvalidIdException {
		if (!lEJB.isLoggedIn())
			throw new NotLoggedInException();
		Board b = fEJB.getBoard(boardId);
		if (b == null)
			throw new InvalidIdException();

		Topic t = new Topic();
		t.setTitle(title);
		t.setBoard(b);
		t.setPostsCount(1);
		t.setCreator(lEJB.getMember());
		em.persist(t);

		Post p = new Post();
		p.setContent(content);
		p.setCreated(Calendar.getInstance().getTime());
		p.setCreator(lEJB.getMember());
		p.setTopic(t);
		em.persist(p);
		
		b.setTopicsCount(b.getTopicsCount() + 1);
		em.merge(b);
		
		return t;
	}

}
