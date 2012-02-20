package it.gas.eeforum.backing;

import it.gas.eeforum.beans.LoginEJB;
import it.gas.eeforum.validation.Email;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

@ManagedBean
@RequestScoped
public class RegisterBacking {
	@EJB
	private LoginEJB lEJB;
	private String nick, mail, pass1, pass2;
	private Object pass1_validator;

	public String registerNewUser() {
		try {
			lEJB.register(nick, mail, pass1);
			UsernamePasswordToken token = new UsernamePasswordToken(mail, pass1.toCharArray(), true);
			SecurityUtils.getSubject().login(token);
			return "../index.xhtml?faces-redirect=true";
		} catch (AuthenticationException e) {
			//TODO think something better
			return "login.xhtml?faces-redirect=true";
		}
	}
	
	public void checkDuplicateNick(FacesContext context, UIComponent component,
			Object value) {
		if (lEJB.checkNicknameIfExist(String.valueOf(value)))
			throw new ValidatorException(new FacesMessage("Duplicate nickname."));
	}
	
	public void checkDuplicateMail(FacesContext context, UIComponent component,
			Object value) {
		if (lEJB.checkEmailIfExist(String.valueOf(value)))
			throw new ValidatorException(new FacesMessage("Duplicate email."));
	}

	public void validatePassword1(FacesContext context, UIComponent component,
			Object value) {
		pass1_validator = value;
	}

	public void validatePassword2(FacesContext context, UIComponent component,
			Object value) {
		if (!value.equals(pass1_validator)) {
			throw new ValidatorException(new FacesMessage(
					"Passwords must match."));
		}
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Email
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass1() {
		return pass1;
	}

	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

}
