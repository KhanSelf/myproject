package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.logistics.business.to.ContractBean;
import org.go.spring.angel.logistics.business.to.ContractItemBean;

import java.util.List;

public interface ContractDAO {

	public List<ContractBean> selectContractList();

	public List<ContractItemBean> selectContractItemList(String contractNo);

	public void insertContract(ContractBean contractBean);

	public void insertContractItem(ContractItemBean contractItemBean);

	public List<ContractItemBean> selectContractReviewList(String startDate, String endDate);

	public void updateContractShippingStatus(String contractItemNo);

	public void updateContractMpsStatus(String contractItemNo);

}
