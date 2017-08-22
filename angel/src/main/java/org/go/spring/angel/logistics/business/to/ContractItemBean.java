package org.go.spring.angel.logistics.business.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractItemBean extends BaseBean {

	String contractItemNo;
	String contractNo;
	String demantDate;
	String contractAmount;
	String shippingStatus;
	String mpsStatus;
	String itemNo;
	String stockAmount;
	String contractDate;
	String customerNo;
	String itemName;
	String itemUnit;

	public String getContractItemNo() {
		return contractItemNo;
	}
	public void setContractItemNo(String contractItemNo) {
		this.contractItemNo = contractItemNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getDemantDate() {
		return demantDate;
	}
	public void setDemantDate(String demantDate) {
		this.demantDate = demantDate;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public String getMpsStatus() {
		return mpsStatus;
	}
	public void setMpsStatus(String mpsStatus) {
		this.mpsStatus = mpsStatus;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
	public String getContractDate() {
		return contractDate;
	}
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
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

}
