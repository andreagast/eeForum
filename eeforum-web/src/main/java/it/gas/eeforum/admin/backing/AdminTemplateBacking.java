package it.gas.eeforum.admin.backing;

import it.gas.eeforum.beans.LoginEJB;
import it.gas.eeforum.exceptions.NotLoggedInException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@RequestScoped
public class AdminTemplateBacking {
	@EJB
	private LoginEJB lEJB;
	
	public void checkIfAdmin(ComponentSystemEvent cse) {
		try {
			if (lEJB.getMember().isAdmin())
				return;
		} catch (NotLoggedInException e) {
			System.err.println(e.getMessage());
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().getNavigationHandler()
				.handleNavigation(fc, "", "../index.xhtml?faces-redirect=true");

	}

}
