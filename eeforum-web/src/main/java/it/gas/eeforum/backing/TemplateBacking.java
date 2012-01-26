package it.gas.eeforum.backing;

import it.gas.eeforum.beans.LoginEJB;
import it.gas.eeforum.exceptions.NotLoggedInException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class TemplateBacking {
	@EJB
	private LoginEJB lEJB;

	public boolean isLoggedIn() {
		return lEJB.isLoggedIn();
	}

	public String getNickname() {
		try {
			return lEJB.getMember().getNickname();
		} catch (NotLoggedInException e) {
			System.err.println(e.getMessage());
		}
		return "guest";
	}
	
	public boolean isAdmin() {
		try {
			return lEJB.getMember().isAdmin();
		} catch (NotLoggedInException e) {
			System.err.println(e.getMessage());
		}
		return false;
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
