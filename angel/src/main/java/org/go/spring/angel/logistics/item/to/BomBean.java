package org.go.spring.angel.logistics.item.to;

import org.go.spring.angel.common.to.BaseBean;

public class BomBean extends BaseBean {
	String bomNo;
	String itemNo;
	String parentItemNo;
	String quantity;
	String hierarchicalItem;
	ItemBean itemBean;

	public ItemBean getItemBean() {
		return itemBean;
	}
	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}
	public String getBomNo() {
		return bomNo;
	}
	public void setBomNo(String bomNo) {
		this.bomNo = bomNo;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getParentItemNo() {
		return parentItemNo;
	}
	public void setParentItemNo(String parentItemNo) {
		this.parentItemNo = parentItemNo;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getHierarchicalItem() {
		return hierarchicalItem;
	}
	public void setHierarchicalItem(String hierarchicalItem) {
		this.hierarchicalItem = hierarchicalItem;
	}
	public ItemBean getItemDetailBean() {
		return itemBean;
	}

	public void setItemDetailBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

}
