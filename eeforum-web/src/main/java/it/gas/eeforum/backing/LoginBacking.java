package it.gas.eeforum.backing;

import it.gas.eeforum.beans.LoginEJB;
import it.gas.eeforum.exceptions.MemberNotFoundException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@RequestScoped
public class LoginBacking {
	@EJB
	private LoginEJB lEJB;

	private String mail, pass;

	public void redirectIfLogged(ComponentSystemEvent cse) {
		if (lEJB.isLoggedIn()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication().getNavigationHandler()
					.handleNavigation(fc, null, "../index.xhtml?faces-redirect=true");
		}
	}

	public String doLogin() {
		try {
			lEJB.login(mail, pass);
			return "../index.xhtml?faces-redirect=true";
		} catch (MemberNotFoundException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Login not recognized. Try again."));
		}
		return null;
	}

	public String doLogout() {
		lEJB.logout();
		return "../index.xhtml?faces-redirect=true";
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
