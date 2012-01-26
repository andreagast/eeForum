package it.gas.eeforum.backing;

import it.gas.eeforum.beans.PostEJB;
import it.gas.eeforum.exceptions.InvalidIdException;
import it.gas.eeforum.exceptions.NotLoggedInException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;

@ManagedBean
@RequestScoped
public class AddPostBacking {
	@EJB
	private PostEJB pEJB;
	private UIInput hidden;
	private String content;
	
	public String addReplyPost() {
		int tId = (Integer) hidden.getValue();
		try {
			pEJB.addPost(tId, content);
		} catch (NotLoggedInException e) {
			System.err.println(e.getMessage());
		} catch (InvalidIdException e) {
			System.err.println(e.getMessage());
		}
		content = "";
		return "post.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UIInput getHidden() {
		return hidden;
	}

	public void setHidden(UIInput hidden) {
		this.hidden = hidden;
	}
	
}
