package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Member;

import javax.ejb.Stateful;

/**
 * It should only keep information about the current member.
 * @author andrea
 */
@Stateful
public class LoginDetailsEJB {
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member user) {
		this.member = user;
	}
	
	
}
