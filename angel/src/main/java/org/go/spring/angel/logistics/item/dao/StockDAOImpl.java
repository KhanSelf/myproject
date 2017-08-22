package org.go.spring.angel.logistics.item.dao;

import kr.co.angelinus.common.exception.DataAccessException;
import kr.co.angelinus.common.transaction.DataSourceTransactionManager;
import kr.co.angelinus.logistics.business.to.ShippingBean;
import kr.co.angelinus.logistics.item.to.StockBean;
import kr.co.angelinus.logistics.product.to.PurchaseBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public List<StockBean> selectStockList(String warehouseNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StockBean> stockList = new ArrayList<>();
        try {

            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM STOCK WHERE WAREHOUSE_NO = ? ORDER BY SUBSTR(STOCK_NO,11,14) DESC ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, warehouseNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StockBean stockBean = new StockBean();
                stockBean.setStockNo(rs.getString("STOCK_NO"));
                stockBean.setAdjustDate(rs.getString("ADJUST_DATE"));
                stockBean.setWarehouseNo(rs.getString("WAREHOUSE_NO"));
                stockBean.setOutAmount(rs.getString("OUT_AMOUNT"));
                stockBean.setInAmount(rs.getString("IN_AMOUNT"));
                stockBean.setStockAmount(rs.getString("STOCK_AMOUNT"));
                stockBean.setItemNo(rs.getString("ITEM_NO"));
                stockList.add(stockBean);
            }
            return stockList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public int selectStockAmount(String itemNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT STOCK_AMOUNT FROM STOCK WHERE ROWNUM = 1 AND ITEM_NO = ? ORDER BY STOCK_NO DESC ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            rs = pstmt.executeQuery();
            rs.next();
            int stockAmount = rs.getInt(1);

            return stockAmount;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public String selectWarehouseNo(String itemNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT DISTINCT WAREHOUSE_NO FROM STOCK WHERE ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            rs = pstmt.executeQuery();
            rs.next();
            String warehouseNo = rs.getString(1);

            return warehouseNo;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public void insertShippingList(ShippingBean shippingBean, String warehouseNo, int amount) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {

            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO STOCK(STOCK_NO, ADJUST_DATE, WAREHOUSE_NO, ");
            query.append(" OUT_AMOUNT, IN_AMOUNT, STOCK_AMOUNT, ITEM_NO) ");
            query.append(" VALUES ('ST'||?||LPAD(STOCK_NO_SEQ.NEXTVAL,4,0),?,?,?,0,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, shippingBean.getShippingDate().replace("-", ""));
            pstmt.setString(2, shippingBean.getShippingDate());
            pstmt.setString(3, warehouseNo);
            pstmt.setString(4, shippingBean.getShippingAmount());
            pstmt.setInt(5, amount);
            pstmt.setString(6, shippingBean.getItemNo());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    @Override
    public void insertPurchaseList(PurchaseBean purchaseBean, String warehouseNo, int amount) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO STOCK(STOCK_NO, ADJUST_DATE, WAREHOUSE_NO, ");
            query.append(" OUT_AMOUNT, IN_AMOUNT, STOCK_AMOUNT, ITEM_NO) ");
            query.append(" VALUES ('ST'||?||LPAD(STOCK_NO_SEQ.NEXTVAL,4,0),?,?,0,?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, purchaseBean.getPurchaseOrderDate().replace("-", ""));
            pstmt.setString(2, purchaseBean.getPurchaseOrderDate());
            pstmt.setString(3, warehouseNo);
            pstmt.setString(4, purchaseBean.getPurchaseOrderAmount());
            pstmt.setInt(5, amount);
            pstmt.setString(6, purchaseBean.getItemNo());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }
}
