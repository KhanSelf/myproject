package org.go.spring.angel.logistics.item.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemBean extends BaseBean {

	String itemNo; // 품목번호
	String itemName; // 품목명
	String itemUnit; // 단위
	String itemPrice; // 단가
	String lossRate; // 손실율
	String leadTime; // 소요일
	String demandQuantity; // 정미수량
	String itemSupply; // 구매생산여부
	String finishedItemStatus; // 완제품여부
	String customerNo; // 거래처번호
	String detailCodeNo; // 코드상세번호
	String codeNo; // 코드번호
	String detailCodeName; // 코드상세명

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

	public String getLossRate() {
		return lossRate;
	}

	public void setLossRate(String lossRate) {
		this.lossRate = lossRate;
	}

	public String getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	public String getDemandQuantity() {
		return demandQuantity;
	}

	public void setDemandQuantity(String demandQuantity) {
		this.demandQuantity = demandQuantity;
	}

	public String getItemSupply() {
		return itemSupply;
	}

	public void setItemSupply(String itemSupply) {
		this.itemSupply = itemSupply;
	}

	public String getFinishedItemStatus() {
		return finishedItemStatus;
	}

	public void setFinishedItemStatus(String finishedItemStatus) {
		this.finishedItemStatus = finishedItemStatus;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getDetailCodeNo() {
		return detailCodeNo;
	}

	public void setDetailCodeNo(String detailCodeNo) {
		this.detailCodeNo = detailCodeNo;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getDetailCodeName() {
		return detailCodeName;
	}

	public void setDetailCodeName(String detailCodeName) {
		this.detailCodeName = detailCodeName;
	}

}
