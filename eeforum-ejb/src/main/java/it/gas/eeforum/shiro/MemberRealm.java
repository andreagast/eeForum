package it.gas.eeforum.shiro;

import it.gas.eeforum.entities.Member;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MemberRealm extends AuthorizingRealm {
	private EntityManager em;

	public MemberRealm() {
		System.out.println("Instantiating MemberRealm...");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("eeForumPU");
		em = factory.createEntityManager();
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		//retrieve member
		TypedQuery<Member> query = em.createNamedQuery("member.get",
				Member.class);
		query.setParameter("mail", principals.getPrimaryPrincipal());
		Member member = query.getSingleResult();
		//add roles
		if (member.isAdmin())
			auth.addRole("admin");
		//TODO banned role and operators permissions
		return auth;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("Yay! Authenticating users BEGIN!");
		// retrieve username
		// String username = String.valueOf(token.getPrincipal());
		// System.out.println(username);
		// query the db
		/*
		 * if (em == null) System.out.println("em is null!");
		 */
		TypedQuery<Member> query = em.createNamedQuery("member.get",
				Member.class);
		query.setParameter("mail", token.getPrincipal());
		List<Member> resultList = query.getResultList();
		System.out.println("Ok, the query went fine... Now checking...");
		System.out.println("Found " + resultList.size() + " records...");
		if (resultList.size() == 0)
			return null;
		String password = resultList.get(0).getPassword();
		/*
		 * String password = String.valueOf((char[]) token.getCredentials()); if
		 * (password.compareTo(resultList.get(0).getPassword()) != 0) return
		 * null; System.out.println("User authenticated.");
		 */
		return new SimpleAuthenticationInfo(token.getPrincipal(),
				password.toCharArray(), getName());
	}

}
