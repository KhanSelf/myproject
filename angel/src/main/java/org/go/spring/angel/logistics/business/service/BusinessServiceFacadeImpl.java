package org.go.spring.angel.logistics.business.service;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.business.applicationservice.BusinessApplicationService;
import org.go.spring.angel.logistics.business.exception.*;
import org.go.spring.angel.logistics.business.to.*;

import java.util.List;

public class BusinessServiceFacadeImpl implements BusinessServiceFacade {

	BusinessApplicationService businessApplicationService;

	public void setBusinessApplicationService(BusinessApplicationService businessApplicationService) {
		this.businessApplicationService = businessApplicationService;
	}

	@Override
	public List<CustomerBean> findCustomerList() {
		return businessApplicationService.findCustomerList();
	}

	@Override
	public void registerCustomer(CustomerBean customerBean) throws CustomerNotEditException {
		businessApplicationService.registerCustomer(customerBean);
	}

	@Override
	public void removeCustomer(String customer) {
		businessApplicationService.removeCustomer(customer);
	}

	@Override
	public List<EstimateBean> findEstimateList() {
		return businessApplicationService.findEstimateList();
	}

	@Override
	public List<CodeDetailBean> findCustomerMenu() {
		return businessApplicationService.findCustomerMenu();
	}

	@Override
	public void batchEstimate(List<EstimateBean> estimateList) throws EstimateNotEditException {
		businessApplicationService.batchEstimate(estimateList);
	}

	@Override
	public List<EstimateItemBean> findEstimateReviewList(String startDate, String endDate) {
		return businessApplicationService.findEstimateReviewList(startDate, endDate);
	}

	@Override
	public List<ContractBean> findContractList() {
		return businessApplicationService.findContractList();
	}

	@Override
	public void batchContract(List<ContractBean> contractList) throws ContractNotEditException {
		businessApplicationService.batchContract(contractList);
	}

	@Override
	public List<ContractItemBean> findContractReviewList(String startDate, String endDate) {
		return businessApplicationService.findContractReviewList(startDate, endDate);
	}

	@Override
	public int findShippingCount(String customer) {
		return businessApplicationService.findShippingCount(customer);
	}

	@Override
	public List<ContractItemBean> findShippingList(String customer) {
		return businessApplicationService.findShippingList(customer);
	}

	@Override
	public void registerShipping(List<ShippingBean> shippingList)
			throws ShippingNotEditException, ShippingAmountOverException {
		businessApplicationService.registerShipping(shippingList);
	}

	@Override
	public List<ShippingBean> findShippingReviewList(String startDate, String endDate) {
		return businessApplicationService.findShippingReviewList(startDate, endDate);
	}

}