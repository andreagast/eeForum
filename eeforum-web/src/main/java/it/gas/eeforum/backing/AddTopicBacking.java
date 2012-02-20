package it.gas.eeforum.backing;

import it.gas.eeforum.beans.ForumEJB;
import it.gas.eeforum.beans.TopicEJB;
import it.gas.eeforum.entities.Topic;
import it.gas.eeforum.exceptions.InvalidIdException;
import it.gas.eeforum.exceptions.NotLoggedInException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.shiro.SecurityUtils;

@ManagedBean
@RequestScoped
public class AddTopicBacking {
	@EJB
	private ForumEJB fEJB;
	@EJB
	private TopicEJB tEJB;
	private int boardId;
	private String postText;
	private String topicTitle;

	public void checkId(ComponentSystemEvent cse) {
		boolean continu = true;
		try {
			fEJB.getBoard(boardId);
		} catch (InvalidIdException e) {
			continu = false;
		}
		continu &= SecurityUtils.getSubject().isAuthenticated();
		if (continu == false) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication().getNavigationHandler()
					.handleNavigation(fc, null, "index?faces-redirect=true");
		}
	}

	public String make() {
		try {
			Topic t = tEJB.addTopic(boardId, topicTitle, postText);
			return "post?id=" + t.getId() + "&faces-redirect=true";
		} catch (NotLoggedInException e) {
			System.err.println(e.getMessage());
		} catch (InvalidIdException e) {
			System.err.println(e.getMessage());
		}
		return "index?faces-redirect=true";
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

}
