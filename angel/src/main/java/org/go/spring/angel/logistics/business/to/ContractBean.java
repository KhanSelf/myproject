package org.go.spring.angel.logistics.business.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.go.spring.angel.common.to.BaseBean;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractBean extends BaseBean{

	String contractNo;
	String contractDate;
	String customerNo;

	List<ContractItemBean> contractItemList;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public List<ContractItemBean> getContractItemList() {
		return contractItemList;
	}

	public void setContractItemList(List<ContractItemBean> contractItemList) {
		this.contractItemList = contractItemList;
	}


}
