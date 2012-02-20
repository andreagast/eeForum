package it.gas.eeforum.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.shiro.SecurityUtils;

@ManagedBean
@RequestScoped
public class LogoutBacking {

	public void checkLogin(ComponentSystemEvent cse) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication()
					.getNavigationHandler()
					.handleNavigation(fc, null,
							"../index.xhtml?faces-redirect=true");
		}
	}

	public String doLogout() {
		SecurityUtils.getSubject().logout();
		return "../index.xhtml?faces-redirect=true";
	}
}
