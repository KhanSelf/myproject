package org.go.spring.angel.logistics.product.to;

public class PurchaseBean {
	String purchaseOrderNo;
	String purchaseOrderDate;
	String purchaseOrderAmount;
	String itemNo;
	String mrpTotalNo;
	String customerNo;

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
	public String getPurchaseOrderDate() {
		return purchaseOrderDate;
	}
	public void setPurchaseOrderDate(String purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}
	public String getPurchaseOrderAmount() {
		return purchaseOrderAmount;
	}
	public void setPurchaseOrderAmount(String purchaseOrderAmount) {
		this.purchaseOrderAmount = purchaseOrderAmount;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getMrpTotalNo() {
		return mrpTotalNo;
	}
	public void setMrpTotalNo(String mrpTotalNo) {
		this.mrpTotalNo = mrpTotalNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

}
