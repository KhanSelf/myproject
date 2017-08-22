package org.go.spring.angel.logistics.business.applicationservice;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.business.exception.*;
import org.go.spring.angel.logistics.business.to.*;

import java.util.List;

public interface BusinessApplicationService {
    public List<ContractBean> findContractList();

    public void batchContract(List<ContractBean> contractList)
            throws ContractNotEditException;

    public List<ContractItemBean> findContractReviewList(String startDate,
                                                         String endDate);

    public List<CustomerBean> findCustomerList();

    public void registerCustomer(CustomerBean customerBean)
            throws CustomerNotEditException;

    public void removeCustomer(String customer);

    public List<EstimateBean> findEstimateList();

    public List<CodeDetailBean> findCustomerMenu();

    public void batchEstimate(List<EstimateBean> estimateList)
            throws EstimateNotEditException;

    public List<EstimateItemBean> findEstimateReviewList(String startDate,
                                                         String endDate);

    int findShippingCount(String customer);

    List<ContractItemBean> findShippingList(String customer);

    void registerShipping(List<ShippingBean> shippingList)
            throws ShippingNotEditException, ShippingAmountOverException;

    List<ShippingBean> findShippingReviewList(String startDate, String endDate);

}
