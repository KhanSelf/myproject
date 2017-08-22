package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.business.to.ShippingBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShippingDAOImpl implements ShippingDAO {

    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

	/* selectShippingCount */

    @Override
    public int selectShippingCount(String customer) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM CONTRACT CONTRACT, ");
            query.append(" CONTRACT_ITEM CONTRACT_ITEM WHERE CONTRACT.CONTRACT_NO ");
            query.append(" = CONTRACT_ITEM.CONTRACT_NO AND CONTRACT.CUSTOMER_NO=? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            rs = pstmt.executeQuery();
            rs.next();
            int shippingCount = rs.getInt(1);

            return shippingCount;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* selectShippingList */

    @Override
    public List<ContractItemBean> selectShippingList(String customer) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ContractItemBean> contractItemList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM (SELECT ITEM_NO,ITEM_NAME,ITEM_UNIT FROM ITEM) ITEM, ");
            query.append(" (SELECT * FROM CONTRACT_ITEM) CONTRACT_ITEM, ");
            query.append(" (SELECT CONTRACT_NO,CONTRACT_DATE,CUSTOMER_NO FROM CONTRACT) CONTRACT, ");
            query.append(" (SELECT B.ITEM_NO,B.STOCK_AMOUNT FROM (SELECT MAX(STOCK_NO) ");
            query.append(" STOCK_NO,ITEM_NO FROM STOCK GROUP BY ITEM_NO) A,STOCK B WHERE A.STOCK_NO = ");
            query.append(" B.STOCK_NO) STOCK WHERE CONTRACT.CONTRACT_NO = CONTRACT_ITEM.CONTRACT_NO ");
            query.append(" AND CONTRACT_ITEM.ITEM_NO = ITEM.ITEM_NO AND ITEM.ITEM_NO = STOCK.ITEM_NO ");
            query.append(" AND CONTRACT_ITEM.SHIPPING_STATUS = 'N' ");
            query.append(" AND CONTRACT.CUSTOMER_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ContractItemBean contractItemBean = new ContractItemBean();
                contractItemBean.setContractNo(rs.getString("CONTRACT_NO"));
                contractItemBean.setContractDate(rs.getString("CONTRACT_DATE"));
                contractItemBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                contractItemBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
                contractItemBean.setDemantDate(rs.getString("DEMANT_DATE"));
                contractItemBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
                contractItemBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
                contractItemBean.setShippingStatus(rs.getString("SHIPPING_STATUS"));
                contractItemBean.setMpsStatus(rs.getString("MPS_STATUS"));
                contractItemBean.setItemNo(rs.getString("ITEM_NO"));
                contractItemBean.setStockAmount(rs.getString("STOCK_AMOUNT"));
                contractItemList.add(contractItemBean);
            }
            return contractItemList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* selectShipping */

    @Override
    public String selectShipping(String ShippingNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM STOCK WHERE STOCK_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, ShippingNo);
            rs = pstmt.executeQuery();
            rs.next();
            String shipping = rs.getString(1);

            return shipping;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* insertShipping */

    @Override
    public void insertShipping(ShippingBean shippingBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO SHIPPING(SHIPPING_NO,CONTRACT_ITEM_NO, ");
            query.append(" SHIPPING_AMOUNT,SHIPPING_DATE) VALUES ('SH'||?||LPAD(SHIPPING_NO_SEQ.NEXTVAL,4,0),?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, shippingBean.getShippingDate().replace("-", ""));
            pstmt.setString(2, shippingBean.getContractItemNo());
            pstmt.setString(3, shippingBean.getShippingAmount());
            pstmt.setString(4, shippingBean.getShippingDate());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

	/* selectShippingReviewList */

    @Override
    public List<ShippingBean> selectShippingReviewList(String startDate, String endDate) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ShippingBean> shippingList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT SHIPPING.*,CONTRACT.*,CONTRACT_ITEM.* ,ITEM.ITEM_NO, ");
            query.append(" ITEM.ITEM_NAME FROM SHIPPING,CONTRACT,CONTRACT_ITEM,ITEM ");
            query.append(" WHERE SHIPPING.CONTRACT_ITEM_NO=CONTRACT_ITEM.CONTRACT_ITEM_NO ");
            query.append(" AND CONTRACT_ITEM.ITEM_NO=ITEM.ITEM_NO ");
            query.append(" AND CONTRACT.CONTRACT_NO=CONTRACT_ITEM.CONTRACT_NO ");
            query.append(" AND SHIPPING.SHIPPING_DATE BETWEEN ? AND ? ORDER BY SHIPPING_DATE ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ShippingBean shippingBean = new ShippingBean();
                shippingBean.setShippingNo(rs.getString("SHIPPING_NO"));
                shippingBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
                shippingBean.setShippingAmount(rs.getString("SHIPPING_AMOUNT"));
                shippingBean.setShippingDate(rs.getString("SHIPPING_DATE"));
                shippingBean.setItemNo(rs.getString("ITEM_NO"));
                shippingBean.setItemName(rs.getString("ITEM_NAME"));
                shippingBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                shippingList.add(shippingBean);
            }
            return shippingList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}
