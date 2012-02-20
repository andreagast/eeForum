package it.gas.eeforum.admin.backing;

import it.gas.eeforum.beans.LoginEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class AdminTemplateBacking {
	@EJB
	private LoginEJB lEJB;

}
