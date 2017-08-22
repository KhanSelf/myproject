package org.go.spring.angel.logistics.business.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimateBean extends BaseBean{
	String estimateNo;
	String estimateDate;
	String customerNo;
	String conApplyYN;

	List<EstimateItemBean> estimateItemList;

	public String getEstimateNo() {
		return estimateNo;
	}

	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
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

	public String getConApplyYN() {
		return conApplyYN;
	}

	public void setConApplyYN(String conApplyYN) {
		this.conApplyYN = conApplyYN;
	}


	public List<EstimateItemBean> getEstimateItemList() {
		return estimateItemList;
	}

	public void setEstimateItemList(List<EstimateItemBean> estimateItemList) {
		this.estimateItemList = estimateItemList;
	}


}
