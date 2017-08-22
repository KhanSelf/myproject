package org.go.spring.angel.logistics.business.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimateItemBean extends BaseBean{

	String estimateItemNo;
	String estimateNo;
	String demandDate;
	String itemNo;
	String estimateAmount;
	String estimateDate;
	String customerNo;
	String itemName;
	String itemUnit;
	String itemPrice;

	public String getEstimateItemNo() {
		return estimateItemNo;
	}
	public void setEstimateItemNo(String estimateItemNo) {
		this.estimateItemNo = estimateItemNo;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(String demandDate) {
		this.demandDate = demandDate;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getEstimateAmount() {
		return estimateAmount;
	}
	public void setEstimateAmount(String estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public String getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}



}
