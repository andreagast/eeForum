package it.gas.eeforum.backing;

import it.gas.eeforum.beans.PostEJB;
import it.gas.eeforum.beans.TopicEJB;
import it.gas.eeforum.entities.Board;
import it.gas.eeforum.entities.Post;
import it.gas.eeforum.entities.Topic;
import it.gas.eeforum.exceptions.InvalidIdException;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@RequestScoped
public class PostBacking {
	@EJB
	private PostEJB pEJB;
	@EJB
	private TopicEJB tEJB;
	private int topicId;
	private List<Post> posts;
	private Topic topic;
	private Board board;

	public void checkId(ComponentSystemEvent cse) {
		try {
			topic = tEJB.getTopic(topicId);
			posts = pEJB.getPosts(getTopicId());
			board = topic.getBoard();
		} catch (InvalidIdException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication()
					.getNavigationHandler()
					.handleNavigation(fc, null,
							"index.xhtml?faces-redirect=true");
		}
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Topic getTopic() {
		return topic;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
