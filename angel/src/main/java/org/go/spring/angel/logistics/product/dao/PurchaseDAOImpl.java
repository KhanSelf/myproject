package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {

    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public int selectPurchaseCount(String customer) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM MRP_TOTAL WHERE CUSTOMER_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            rs = pstmt.executeQuery();
            rs.next();
            int shippingCount = rs.getInt(1);

            return shippingCount;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<MrpTotalBean> selectPurchaseList(int startRow, int endRow, String customer) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MrpTotalBean> mrpTotalList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM (SELECT MRP_TOTAL.*,ROWNUM NUM FROM MRP_TOTAL ");
            query.append(" WHERE CUSTOMER_NO = ? AND PURCHASE_ORDER_STATUS='N') A ,ITEM B ");
            query.append(" WHERE  A.ITEM_NO = B.ITEM_NO AND A.NUM BETWEEN ? AND ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            pstmt.setInt(2, startRow);
            pstmt.setInt(3, endRow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MrpTotalBean mrpTotalBean = new MrpTotalBean();
                mrpTotalBean.setMrpTotalNo(rs.getString("MRP_TOTAL_NO"));
                mrpTotalBean.setPurchaseOrderDate(rs.getString("PURCHASE_ORDER_DATE"));
                mrpTotalBean.setItemNo(rs.getString("ITEM_NO"));
                mrpTotalBean.setItemName(rs.getString("ITEM_NAME"));
                mrpTotalBean.setItemUnit(rs.getString("ITEM_UNIT"));
                mrpTotalBean.setPurchaseOrderAmount(rs.getString("PURCHASE_ORDER_AMOUNT"));
                mrpTotalBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                mrpTotalList.add(mrpTotalBean);
            }
            return mrpTotalList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public void insertPurchase(PurchaseBean purchaseBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO PURCHASE_ORDER(PURCHASE_ORDER_NO,PURCHASE_ORDER_DATE, ");
            query.append(" PURCHASE_ORDER_AMOUNT,ITEM_NO,MRP_TOTAL_NO,CUSTOMER_NO) ");
            query.append(" VALUES ('PO'||?||LPAD(PURCHASE_ORDER_SEQ.NEXTVAL,4,0),?,?,?,?,?)");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, purchaseBean.getPurchaseOrderDate().replace("-", ""));
            pstmt.setString(2, purchaseBean.getPurchaseOrderDate());
            pstmt.setString(3, purchaseBean.getPurchaseOrderAmount());
            pstmt.setString(4, purchaseBean.getItemNo());
            pstmt.setString(5, purchaseBean.getMrpTotalNo());
            pstmt.setString(6, purchaseBean.getCustomerNo());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
            con = dataSourceTransactionManager.getConnection();
            query.setLength(0);
            query.append(" UPDATE MRP_TOTAL SET PURCHASE_ORDER_STATUS = 'Y' WHERE MRP_TOTAL_NO = ? ");
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, purchaseBean.getMrpTotalNo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    @Override
    public List<PurchaseBean> selectPurchaseReviewList(String startDate, String endDate) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PurchaseBean> purchaseReviewList = new ArrayList<PurchaseBean>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM PURCHASE_ORDER WHERE PURCHASE_ORDER_DATE BETWEEN ? AND ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PurchaseBean purchaseBean = new PurchaseBean();
                purchaseBean.setPurchaseOrderNo(rs.getString("PURCHASE_ORDER_NO"));
                purchaseBean.setPurchaseOrderDate(rs.getString("PURCHASE_ORDER_DATE"));
                purchaseBean.setPurchaseOrderAmount(rs.getString("PURCHASE_ORDER_AMOUNT"));
                purchaseBean.setItemNo(rs.getString("ITEM_NO"));
                purchaseBean.setMrpTotalNo(rs.getString("MRP_TOTAL_NO"));
                purchaseBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                purchaseReviewList.add(purchaseBean);
            }
            return purchaseReviewList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}