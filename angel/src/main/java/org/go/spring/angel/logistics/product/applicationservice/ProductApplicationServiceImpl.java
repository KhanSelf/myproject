package org.go.spring.angel.logistics.product.applicationservice;

import org.go.spring.angel.logistics.business.dao.ContractDAO;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.item.dao.StockDAO;
import org.go.spring.angel.logistics.product.dao.*;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.util.HashMap;
import java.util.List;

public class ProductApplicationServiceImpl implements ProductApplicationService {

    MpsDAO mpsDAO;
    MrpDAO mrpDAO;
    ContractDAO contractDAO;
    PurchaseDAO purchaseDAO;
    StockDAO stockDAO;

    public void setMpsDAO(MpsDAO mpsDAO) {
        this.mpsDAO = mpsDAO;
    }

    public void setMrpDAO(MrpDAO mrpDAO) {
        this.mrpDAO = mrpDAO;
    }

    public void setContractDAO(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public int findMpsCount() {
        // TODO Auto-generated method stub
        return mpsDAO.selectMpsCount();
    }

    @Override
    public List<ContractItemBean> findMpsList() {
        // TODO Auto-generated method stub
        return mpsDAO.selectMpsList();
    }

    @Override
    public void registerMps(List<MpsBean> mpsList) {
        // TODO Auto-generated method stub

        for (MpsBean mpsBean : mpsList) {
            mpsDAO.insertMps(mpsBean);

            contractDAO.updateContractMpsStatus(mpsBean.getContractItemNo());
        }
    }

    @Override
    public int findMpsReviewCount() {
        // TODO Auto-generated method stub
        return mpsDAO.selectMpsReviewCount();
    }

    @Override
    public List<MpsBean> findMpsReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return mpsDAO.selectMpsReviewList(startRow, endRow);
    }

    @Override
    public int findMrpCount(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpCount(workplace, startDate, endDate);
    }

    @Override
    public List<MpsBean> findMrpList(String workplace, String startDate, String endDate, int startRow, int endRow) {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpList(workplace, startDate, endDate, startRow, endRow);
    }

    @Override
    public HashMap<String, Object> findMrpDisassembleList(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpDisassembleList(workplace, startDate, endDate);
    }

    @Override
    public int findMrpTotalCount() {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpTotalCount();
    }

    @Override
    public List<MrpBean> findMrpTotalList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpTotalList(startRow, endRow);
    }

    @Override
    public void registerMrpTotalList(List<MrpTotalBean> mrpTotalList) {
        // TODO Auto-generated method stub

        for (MrpTotalBean mrpTotalBean : mrpTotalList) {
            mrpDAO.insertMrpTotalList(mrpTotalBean);
        }
    }

    @Override
    public int findMrpTotalReviewCount() {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpTotalReviewCount();
    }

    @Override
    public List<MrpTotalBean> findMrpTotalReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        return mrpDAO.selectMrpTotalReviewList(startRow, endRow);
    }

    @Override
    public int findPurchaseCount(String customer) {
        // TODO Auto-generated method stub
        return purchaseDAO.selectPurchaseCount(customer);
    }

    @Override
    public List<MrpTotalBean> findPurchaseList(int startRow, int endRow, String customer) {
        // TODO Auto-generated method stub
        return purchaseDAO.selectPurchaseList(startRow, endRow, customer);
    }

    @Override
    public void registerPurchase(List<PurchaseBean> purchaseList) {
        // TODO Auto-generated method stub

        for (PurchaseBean purchaseBean : purchaseList) {
            int purchaseAmount = Integer.parseInt(purchaseBean.getPurchaseOrderAmount());

            int stockAmount = stockDAO.selectStockAmount(purchaseBean.getItemNo());
            purchaseDAO.insertPurchase(purchaseBean);
            String warehouseNo = stockDAO.selectWarehouseNo(purchaseBean.getItemNo());

            int amount = stockAmount + purchaseAmount;

            stockDAO.insertPurchaseList(purchaseBean, warehouseNo, amount);
        }
    }

    @Override
    public List<PurchaseBean> findPurchaseReviewList(String startDate, String endDate) {
        // TODO Auto-generated method stub
        return purchaseDAO.selectPurchaseReviewList(startDate, endDate);
    }
}