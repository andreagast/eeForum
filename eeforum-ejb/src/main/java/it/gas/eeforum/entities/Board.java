package it.gas.eeforum.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "board.all", query = "SELECT b FROM Board b")
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private int topicsCount;
	private List<Member> operators;
	private List<Topic> topicsList;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTopicsCount() {
		return topicsCount;
	}

	public void setTopicsCount(int topicsCount) {
		this.topicsCount = topicsCount;
	}

	@JoinTable(name = "operator", joinColumns = @JoinColumn(name = "board"), inverseJoinColumns = @JoinColumn(name = "member"))
	public List<Member> getOperators() {
		return operators;
	}

	public void setOperators(List<Member> operators) {
		this.operators = operators;
	}

	@OneToMany(orphanRemoval = true, mappedBy = "board")
	public List<Topic> getTopicsList() {
		return topicsList;
	}

	public void setTopicsList(List<Topic> topicsList) {
		this.topicsList = topicsList;
	}

}
