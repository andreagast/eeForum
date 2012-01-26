package it.gas.eeforum.backing;

import it.gas.eeforum.beans.ForumEJB;
import it.gas.eeforum.beans.LoginEJB;
import it.gas.eeforum.entities.Board;
import it.gas.eeforum.exceptions.NotLoggedInException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@RequestScoped
public class AdminBacking {
	@EJB
	private ForumEJB fEJB;
	@EJB
	private LoginEJB lEJB;
	private UIData tbl;
	private String newBoardName;

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

	public void addAndReturn() {
		fEJB.addBoard(newBoardName);
	}

	public DataModel<Board> getbList() {
		// you need to do this otherwise setDelSelection() don't get called
		return new ListDataModel<Board>(fEJB.getBoards());
	}

	public boolean getDelSelection() {
		return false;
	}

	public void setDelSelection(boolean delSelection) {
		if (delSelection) {
			Board b = (Board) tbl.getRowData();
			fEJB.deleteBoard(b);
		}
	}

	public UIData getTbl() {
		return tbl;
	}

	public void setTbl(UIData tbl) {
		this.tbl = tbl;
	}

	public String getNewBoardName() {
		return newBoardName;
	}

	public void setNewBoardName(String newBoardName) {
		this.newBoardName = newBoardName;
	}

}
