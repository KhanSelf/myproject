package org.go.spring.angel.logistics.business.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingBean extends BaseBean{

	String shippingNo;
	String contractItemNo;
	String shippingAmount;
	String shippingDate;
	String itemNo;
	String itemName;
	String customerNo;

	public String getShippingNo() {
		return shippingNo;
	}
	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}
	public String getContractItemNo() {
		return contractItemNo;
	}
	public void setContractItemNo(String contractItemNo) {
		this.contractItemNo = contractItemNo;
	}
	public String getShippingAmount() {
		return shippingAmount;
	}
	public void setShippingAmount(String shippingAmount) {
		this.shippingAmount = shippingAmount;
	}
	public String getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}



}