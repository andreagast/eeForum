package it.gas.eeforum.backing;

import it.gas.eeforum.beans.LoginEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@RequestScoped
public class LogoutBacking {
	@EJB
	private LoginEJB lEJB;

	public void checkLogin(ComponentSystemEvent cse) {
		if (!lEJB.isLoggedIn()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication()
					.getNavigationHandler()
					.handleNavigation(fc, null,
							"../index.xhtml?faces-redirect=true");
		}
	}

	public String doLogout() {
		lEJB.logout();
		return "../index.xhtml?faces-redirect=true";
	}
}
