package it.gas.eeforum.admin.backing;

import it.gas.eeforum.beans.ForumEJB;
import it.gas.eeforum.entities.Board;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIData;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@RequestScoped
public class AdminBoardsBacking {
	@EJB
	private ForumEJB fEJB;
	private UIData tbl;
	private String newBoardName;

	public void addAndReturn() {
		fEJB.addBoard(newBoardName);
	}

	public DataModel<Board> getBoardList() {
		// you need to do this otherwise setDelSelection() won't get called
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
