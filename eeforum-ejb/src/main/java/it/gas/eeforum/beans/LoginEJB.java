package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Member;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.shiro.SecurityUtils;

@Stateless
public class LoginEJB {
	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	
	public Member getMember() {
		Object user = SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		TypedQuery<Member> query = em.createNamedQuery("member.get", Member.class);
		query.setParameter("mail", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public void register(String nick, String mail, String pass) {
		Member m = new Member();
		m.setNickname(nick);
		m.setEmail(mail);
		m.setPassword(pass);
		em.persist(m);
	}
	
	public boolean checkEmailIfExist(String email) {
		TypedQuery<Member> q = em.createNamedQuery("member.get", Member.class);
		q.setParameter("mail", email);
		List<Member> l = q.getResultList();
		if (l.size() > 0)
			return true;
		return false;
	}

	public boolean checkNicknameIfExist(String nick) {
		TypedQuery<Member> q = em.createNamedQuery("member.nick", Member.class);
		q.setParameter("nick", nick);
		List<Member> l = q.getResultList();
		if (l.size() > 0)
			return true;
		return false;
	}
	
}
