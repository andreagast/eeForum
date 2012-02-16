package it.gas.eeforum.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "post.byId", query = "SELECT p FROM Post p WHERE p.topic.id = ?1"),
	@NamedQuery(name = "post.count.byId", query = "SELECT COUNT(p) FROM Post p WHERE p.topic.id = ?1")
})
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Member creator;
	private String content;
	private Date created;
	private Topic topic;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JoinColumn(name = "creator", nullable = false)
	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member member) {
		this.creator = member;
	}

	@Column(length = 2000, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "topicid", nullable = false)
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
