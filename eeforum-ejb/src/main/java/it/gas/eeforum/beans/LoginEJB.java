package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Member;
import it.gas.eeforum.exceptions.MemberNotFoundException;
import it.gas.eeforum.exceptions.NotLoggedInException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class LoginEJB {
	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	@EJB
	private LoginDetailsEJB ldEJB;

	public void login(String user, String pass) throws MemberNotFoundException {
		TypedQuery<Member> query = em.createNamedQuery("member.login",
				Member.class);
		query.setParameter("user", user);
		query.setParameter("pass", pass);
		try {
			ldEJB.setMember(query.getSingleResult());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new MemberNotFoundException();
		}
	}

	public void logout() {
		ldEJB.setMember(null);
	}

	public boolean isLoggedIn() {
		return ldEJB.getMember() != null;
	}

	public Member getMember() throws NotLoggedInException {
		if (ldEJB.getMember() == null)
			throw new NotLoggedInException();
		return ldEJB.getMember();
	}

	public void register(String nick, String mail, String pass) {
		Member m = new Member();
		m.setNickname(nick);
		m.setEmail(mail);
		m.setPassword(pass);
		em.persist(m);
	}
	
	public boolean checkEmailIfExist(String email) {
		TypedQuery<Member> q = em.createNamedQuery("member.mail", Member.class);
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
