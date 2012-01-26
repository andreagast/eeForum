package it.gas.eeforum.backing;

import it.gas.eeforum.beans.ForumEJB;
import it.gas.eeforum.beans.TopicEJB;
import it.gas.eeforum.entities.Board;
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
public class TopicBacking {
	@EJB
	private ForumEJB fEJB;
	@EJB
	private TopicEJB tEJB;
	private int boardId;
	private Board board;
	private List<Topic> topics;

	public void loadOrRedirect(ComponentSystemEvent cse) {
		try {
			board = fEJB.getBoard(boardId);
			topics = tEJB.getTopics(boardId);
		} catch (InvalidIdException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication().getNavigationHandler()
					.handleNavigation(fc, null, "index?faces-redirect=true");
		}
	}

	public ForumEJB getfEJB() {
		return fEJB;
	}

	public void setfEJB(ForumEJB fEJB) {
		this.fEJB = fEJB;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public Board getBoard() {
		return board;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topicsList) {
		this.topics = topicsList;
	}

}
