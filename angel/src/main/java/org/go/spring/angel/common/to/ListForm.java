package org.go.spring.angel.common.to;

import java.util.List;

public class ListForm {

	private int endrow;
	private int pagenum;
	private int rowsize;
	private int endpage;
	private int pagesize = 5;
	private int pagecount;
	private int dbcount;
	private List<?> list;

	public ListForm(int rowsize, int pagenum, int dbcount) {
		this.rowsize = rowsize;
		this.pagenum = pagenum;
		this.dbcount = dbcount;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public void setDbcount(int dbcount) {
		this.dbcount = dbcount;
	}

	public void setRowsize(int rowsize) {
		this.rowsize = rowsize;
	}

	public int getPagenum() {
		return pagenum;
	}

	public int getStartrow() {
		return (getPagenum() - 1) * getRowsize() + 1;
	}

	public int getEndrow() {
		endrow = getStartrow() + getRowsize() - 1;

		if (endrow > getDbcount())
			endrow = getDbcount();
		return endrow;
	}

	public int getRowsize() {
		return rowsize;
	}

	public int getDbcount() {
		return dbcount;
	}

	public int getStartpage() {
		return getPagenum() - ((getPagenum() - 1) % getPagesize());
	}

	public int getEndpage() {
		endpage = getStartpage() + getPagesize() - 1;
		if (endpage > getPagecount())
			endpage = getPagecount();
		return endpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public int getPagecount() {
		pagecount = (getDbcount() - 1) / getRowsize() + 1;
		return pagecount;
	}

	public boolean isPrevious() {
		if (getStartpage() - getPagesize() > 0)
			return true;
		else
			return false;
	}

	public boolean isNext() {
		if (getStartpage() + getPagesize() <= getPagecount())
			return true;
		else
			return false;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public List<?> getList() {
		return list;
	}

}
