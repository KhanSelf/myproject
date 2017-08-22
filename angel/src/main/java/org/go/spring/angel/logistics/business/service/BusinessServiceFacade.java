package org.go.spring.angel.logistics.business.service;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.business.exception.*;
import org.go.spring.angel.logistics.business.to.*;

import java.util.List;

public interface BusinessServiceFacade {

	public List<CustomerBean> findCustomerList();

	public void registerCustomer(CustomerBean customerBean)
			throws CustomerNotEditException;

	public void removeCustomer(String customer);

	public List<CodeDetailBean> findCustomerMenu();

	public List<EstimateBean> findEstimateList();

	public void batchEstimate(List<EstimateBean> estimateList)
			throws EstimateNotEditException;

	public List<EstimateItemBean> findEstimateReviewList(String startDate,
                                                         String endDate);

	public List<ContractBean> findContractList();

	public void batchContract(List<ContractBean> contractList)
			throws ContractNotEditException;

	public List<ContractItemBean> findContractReviewList(String startDate,
                                                         String endDate);

	public int findShippingCount(String customer);

	public List<ContractItemBean> findShippingList(String customer);

	public void registerShipping(List<ShippingBean> shippingList)
			throws ShippingNotEditException, ShippingAmountOverException;

	public List<ShippingBean> findShippingReviewList(String startDate,
                                                     String endDate);

}
