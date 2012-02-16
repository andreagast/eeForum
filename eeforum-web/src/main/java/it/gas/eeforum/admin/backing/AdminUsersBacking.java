package it.gas.eeforum.admin.backing;

import it.gas.eeforum.entities.Member;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIData;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@RequestScoped
public class AdminUsersBacking {
	private UIData table;
	private int page;	
	
	public DataModel<Member> getMembersList() {
		return new ListDataModel<Member>();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public UIData getTable() {
		return table;
	}

	public void setTable(UIData table) {
		this.table = table;
	}
	
}
