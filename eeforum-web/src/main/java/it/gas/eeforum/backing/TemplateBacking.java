package it.gas.eeforum.backing;

import it.gas.eeforum.beans.LoginEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;

@ManagedBean
@RequestScoped
public class TemplateBacking {
	@EJB
	private LoginEJB lEJB;

	public boolean isLoggedIn() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public String getNickname() {
		return lEJB.getNickname();
	}
	
	public boolean isAdmin() {
		return SecurityUtils.getSubject().hasRole("admin");
	}

	/** Force the template to not show the login form when inside the login
	 * tree.
	 * @return true is the login box should be show.
	 */
	public boolean isOutsideLogin() {
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		if (viewId.startsWith("/login"))
			return false;
		return true;
	}

}
