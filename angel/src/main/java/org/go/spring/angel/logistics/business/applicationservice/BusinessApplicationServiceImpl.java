package org.go.spring.angel.logistics.business.applicationservice;

import org.go.spring.angel.base.dao.CodeDetailDAO;
import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.business.dao.*;
import org.go.spring.angel.logistics.business.exception.*;
import org.go.spring.angel.logistics.business.to.*;
import org.go.spring.angel.logistics.item.dao.StockDAO;

import java.util.List;

public class BusinessApplicationServiceImpl
        implements
        BusinessApplicationService {
    // -------------------dependency(bean-ref)-------------------//

    ContractDAO contractDAO;
    CustomerDAO customerDAO;
    EstimateDAO estimateDAO;
    ShippingDAO shippingDAO;
    StockDAO stockDAO;
    CodeDetailDAO codeDetailDAO;

    public void setContractDAO(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void setEstimateDAO(EstimateDAO estimateDAO) {
        this.estimateDAO = estimateDAO;
    }

    public void setShippingDAO(ShippingDAO shippingDAO) {
        this.shippingDAO = shippingDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
        this.codeDetailDAO = codeDetailDAO;
    }

    // -------------------dependency(bean-ref)-------------------//


	/* findContractList */
    @Override
    public List<ContractBean> findContractList() {

        List<ContractBean> contractList = contractDAO.selectContractList();
        for (ContractBean contractBean : contractList) {
            contractBean.setContractItemList(contractDAO.selectContractItemList(contractBean.getContractNo()));
        }
        return contractList;
    }


	/* batchContract */
    /* 수주 일괄처리 */
    @Override
    public void batchContract(List<ContractBean> contractList) throws ContractNotEditException {
        //System.out.println("contractList"+"\n"+contractList);
        for (ContractBean contractBean : contractList) {
            switch (contractBean.getStatus()) {
                case "select":
                    throw new ContractNotEditException("등록된 내역은 변경 할수없습니다");
                case "insert":
                    contractDAO.insertContract(contractBean);
                    //System.out.println("contractBean"+"\n"+contractBean);
                    break;
            }
            List<ContractItemBean> contractItemList = contractBean.getContractItemList();
            //System.out.println("contractItemList"+"\n"+contractItemList);
            if (contractItemList != null) {
                for (ContractItemBean contractItemBean : contractItemList) {
                    switch (contractItemBean.getStatus()) {
                        case "select":
                            throw new ContractNotEditException("등록된 내역은 변경 할수없습니다");
                        case "insert":
                            contractDAO.insertContractItem(contractItemBean);
                            //System.out.println("contractItemBean"+"\n"+contractItemBean);
                            break;
                    }
                }
            }
        }
    }


	/*findContractReviewList*/
    @Override
    public List<ContractItemBean> findContractReviewList(String startDate, String endDate) {
        return contractDAO.selectContractReviewList(startDate, endDate);
    }


	/* findCustomerList */
    @Override
    public List<CustomerBean> findCustomerList() {
        return customerDAO.selectCustomerList();
    }


	/* registerCustomer */
    @Override
    // 거래처 등록
    public void registerCustomer(CustomerBean customerBean) throws CustomerNotEditException {
        // 거래처번호 중복 확인
        if (customerDAO.selectCustomer(customerBean.getCustomerNo()) != null) {
            throw new CustomerNotEditException("동일한 거래처번호가 존재합니다");
        } else {
            customerDAO.insertCustomer(customerBean);
        }
    }


	/* removeCustomer */
    @Override
    public void removeCustomer(String customer) {
        customerDAO.deleteCustomer(customer);
    }


	/* findEstimateList */
    @Override
    public List<EstimateBean> findEstimateList() {
        List<EstimateBean> estimateList = estimateDAO.selectEstimateList();
        for (EstimateBean estimateBean : estimateList) {
            estimateBean.setEstimateItemList(estimateDAO.selectEstimateItemList(estimateBean.getEstimateNo()));
        }
        return estimateList;
    }


	/* findCustomerMenu */
    @Override
    public List<CodeDetailBean> findCustomerMenu() {
        return estimateDAO.selectCustomerMenu();
    }


	/* batchEstimate */
	/* 견적서 일괄처리 */
    @Override
    public void batchEstimate(List<EstimateBean> estimateList) throws EstimateNotEditException {

        for (EstimateBean estimateBean : estimateList) {
            switch (estimateBean.getStatus()) {
                case "select":
                    throw new EstimateNotEditException("등록된 내역은 변경 할수없습니다");
                case "insert":
                    estimateDAO.insertEstimate(estimateBean);
                    break;
            }
            List<EstimateItemBean> estimateItemList = estimateBean.getEstimateItemList();
            for (EstimateItemBean estimateItemBean : estimateItemList) {
                switch (estimateItemBean.getStatus()) {
                    case "select":
                        throw new EstimateNotEditException("등록된 내역은 변경 할수없습니다");
                    case "insert":
                        // 견적번호 세팅
                        estimateDAO.insertEstimateItem(estimateItemBean);
                        break;
                }
            }
        }
    }


	/* findEstimateReviewList */
    @Override
    public List<EstimateItemBean> findEstimateReviewList(String startDate, String endDate) {
        return estimateDAO.selectEstimateReviewList(startDate, endDate);
    }


	/* findShippingCount */
    @Override
    public int findShippingCount(String customer) {
        return shippingDAO.selectShippingCount(customer);
    }


	/* findShippingList */
    @Override
    public List<ContractItemBean> findShippingList(String customer) {
        return shippingDAO.selectShippingList(customer);
    }


	/* registerShipping */
    @Override
    public void registerShipping(List<ShippingBean> shippingList)
            throws ShippingNotEditException, ShippingAmountOverException {

        for (ShippingBean shippingBean : shippingList) {
            int shippingAmount = Integer.parseInt(shippingBean.getShippingAmount());
            int stockAmount = stockDAO.selectStockAmount(shippingBean.getItemNo());                // 현재고량
            if (shippingAmount <= stockAmount) {
                shippingDAO.insertShipping(shippingBean);                                        // 납품내역추가
                // 수주품목 납품상태 변경
                contractDAO.updateContractShippingStatus(shippingBean.getContractItemNo());
                String warehouseNo = stockDAO.selectWarehouseNo(shippingBean.getItemNo());        // 창고검색
                int amount = stockAmount - shippingAmount;
                // 재고 납품내역추가
                stockDAO.insertShippingList(shippingBean, warehouseNo, amount);
            } else {
                throw new ShippingAmountOverException(shippingBean.getItemNo() + "의 재고수량을 확인해주세요");
            }
        }
    }


	/* findShippingReviewList */
    @Override
    public List<ShippingBean> findShippingReviewList(String startDate, String endDate) {
        return shippingDAO.selectShippingReviewList(startDate, endDate);
    }

}