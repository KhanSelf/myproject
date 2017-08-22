package org.go.spring.angel.logistics.product.service;

import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.product.applicationservice.ProductApplicationService;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.util.HashMap;
import java.util.List;

public class ProductServiceFacadeImpl implements ProductServiceFacade {

    ProductApplicationService productApplicationService;

    public void setProductApplicationService(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    public int findMpsCount() {
        // TODO Auto-generated method stub
        return productApplicationService.findMpsCount();
    }

    public List<ContractItemBean> findMpsList() {
        // TODO Auto-generated method stub
        return productApplicationService.findMpsList();
    }

    public void registerMps(List<MpsBean> mpsList) {
        // TODO Auto-generated method stub
        productApplicationService.registerMps(mpsList);
    }

    public int findMpsReviewCount() {
        // TODO Auto-generated method stub
        return productApplicationService.findMpsReviewCount();
    }

    public List<MpsBean> findMpsReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return productApplicationService.findMpsReviewList(startRow, endRow);
    }

    public int findMrpCount(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpCount(workplace, startDate, endDate);
    }

    public List<MpsBean> findMrpList(String workplace, String startDate, String endDate, int startRow, int endRow) {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpList(workplace, startDate, endDate, startRow, endRow);
    }

    public HashMap<String, Object> findMrpDisassembleList(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpDisassembleList(workplace, startDate, endDate);
    }

    public int findMrpTotalCount() {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpTotalCount();
    }

    public List<MrpBean> findMrpTotalList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpTotalList(startRow, endRow);
    }

    public void registerMrpTotalList(List<MrpTotalBean> mrpTotalList) {
        // TODO Auto-generated method stub
        productApplicationService.registerMrpTotalList(mrpTotalList);
    }

    public int findMrpTotalReviewCount() {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpTotalReviewCount();
    }

    public List<MrpTotalBean> findMrpTotalReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return productApplicationService.findMrpTotalReviewList(startRow, endRow);
    }

    public int findPurchaseCount(String customer) {
        // TODO Auto-generated method stub
        return productApplicationService.findPurchaseCount(customer);
    }

    public List<MrpTotalBean> findPurchaseList(int startRow, int endRow, String customer) {
        // TODO Auto-generated method stub
        return productApplicationService.findPurchaseList(startRow, endRow, customer);
    }

    public void registerPurchase(List<PurchaseBean> purchaseList) {
        // TODO Auto-generated method stub
        productApplicationService.registerPurchase(purchaseList);
    }

    public List<PurchaseBean> findPurchaseReviewList(String startDate, String endDate) {
        // TODO Auto-generated method stub
        return productApplicationService.findPurchaseReviewList(startDate, endDate);
    }
}
