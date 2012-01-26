package it.gas.eeforum.backing;

import it.gas.eeforum.beans.ForumEJB;
import it.gas.eeforum.entities.Board;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@RequestScoped
public class IndexBacking {
	@EJB
	private ForumEJB forumEJB;
	private List<Board> boards;
	
	public void loadBoards(ComponentSystemEvent e) {
		setBoards(forumEJB.getBoards());
	}

	public ForumEJB getForumEJB() {
		return forumEJB;
	}

	public void setForumEJB(ForumEJB forumEJB) {
		this.forumEJB = forumEJB;
	}

	public List<Board> getBoards() {
		return boards;
	}

	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}
	
}
