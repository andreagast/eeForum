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
	private static int POST_PER_PAGE = 5;
	@EJB
	private PostEJB pEJB;
	@EJB
	private TopicEJB tEJB;
	private int topicId;
	private int page;
	private long totalPages;
	private List<Post> posts;
	private Topic topic;
	private Board board;
	private boolean nextEnabled, prevEnabled;

	public void checkId(ComponentSystemEvent cse) {
		try {
			topic = tEJB.getTopic(topicId);
			posts = pEJB.getPosts(getTopicId(), page);
			board = topic.getBoard();
			long postsCount = pEJB.getPostsCount(topicId);
			totalPages = postsCount / POST_PER_PAGE;
			if (postsCount % POST_PER_PAGE != 0)
				totalPages++;
			System.out.println("page: " + page);
			System.out.println("totalPages: " + totalPages);
			prevEnabled = page > 0;
			nextEnabled = (page + 1) < totalPages;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotalPages() {
		return totalPages;
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

	public boolean isNextEnabled() {
		return nextEnabled;
	}

	public boolean isPrevEnabled() {
		return prevEnabled;
	}

}
