package it.gas.eeforum.backing;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

@ManagedBean
@RequestScoped
public class LoginBacking {
	private String mail, pass;
	private boolean rememberMe;

	public void redirectIfLogged(ComponentSystemEvent cse) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.getApplication().getNavigationHandler()
					.handleNavigation(fc, null, "../index.xhtml?faces-redirect=true");
		}
	}

	public String doLogin() {
		try {
			FacesContext cx = FacesContext.getCurrentInstance();
			UsernamePasswordToken token = new UsernamePasswordToken(mail, pass.toCharArray(), rememberMe);
			SecurityUtils.getSubject().login(token);
			SavedRequest s = WebUtils.getAndClearSavedRequest((ServletRequest) cx.getExternalContext().getRequest());
			//NOT WORKING,DISCOVER WHY
			if (s == null) {
				System.out.println(cx.getExternalContext().getRequest());
				return "../index.xhtml?faces-redirect=true";
			} else {
				System.out.println(s.getRequestUrl());
				return s.getRequestUrl();
			}
		} catch (AuthenticationException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Login not recognized. Try again."));
		}
		return null;
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

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
