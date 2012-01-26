package it.gas.eeforum.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "topics")
@NamedQuery(name = "topic.byId", query = "SELECT t FROM Topic t WHERE t.board.id = ?1")
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private int postsCount;
	private Member creator;
	private List<Post> postsList;
	private Board board;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 255, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}

	@JoinColumn(name = "creator", nullable = false)
	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	@OneToMany(orphanRemoval = true, mappedBy = "topic")
	@OrderBy("created")
	public List<Post> getPostsList() {
		return postsList;
	}

	public void setPostsList(List<Post> postsList) {
		this.postsList = postsList;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "boardid", nullable = false)
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
