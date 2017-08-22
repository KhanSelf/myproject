package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.business.to.CustomerBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//


	/* selectCustomerList */
    @Override
    public List<CustomerBean> selectCustomerList() {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CustomerBean> customerList = new ArrayList<>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM CUSTOMER ORDER BY CUSTOMER_NO ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CustomerBean customerBean = new CustomerBean();
                customerBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                customerBean.setCustomerName(rs.getString("CUSTOMER_NAME"));
                customerBean.setBusinessNumber(rs.getString("BUSINESS_NUMBER"));
                customerBean.setRepresentaticeName(rs.getString("REPRESENTATICE_NAME"));
                customerBean.setRepresentaticeNumber(rs.getString("REPRESENTATICE_NUMBER"));
                customerBean.setBusinessCategory(rs.getString("BUSINESS_CATEGORY"));
                customerBean.setBusinessType(rs.getString("BUSINESS_TYPE"));
                customerBean.setZipCode(rs.getString("ZIP_CODE"));
                customerBean.setCustomerAddress(rs.getString("CUSTOMER_ADDRESS"));
                customerBean.setCustomerTel(rs.getString("CUSTOMER_TEL"));
                customerBean.setCustomerFax(rs.getString("CUSTOMER_FAX"));
                customerBean.setCustomerHomepage(rs.getString("CUSTOMER_HOMEPAGE"));
                customerBean.setCustomerEmail(rs.getString("CUSTOMER_EMAIL"));
                customerBean.setCountryCode(rs.getString("COUNTRY_CODE"));
                customerBean.setCustomerTradeType(rs.getString("CUSTOMER_TRADE_TYPE"));
                customerList.add(customerBean);
            }
            return customerList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }


	/* selectCustomer */
    @Override
    public CustomerBean selectCustomer(String customerNo) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CustomerBean customerBean = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT CUSTOMER_NO FROM CUSTOMER WHERE CUSTOMER_NO=? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customerNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                customerBean = new CustomerBean();
                customerBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
            }
            return customerBean;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }


	/* insertCustomer */
    @Override
    public void insertCustomer(CustomerBean customerBean) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO CUSTOMER (CUSTOMER_NO, CUSTOMER_NAME, ");
            query.append(" BUSINESS_NUMBER, REPRESENTATICE_NAME, REPRESENTATICE_NUMBER, ");
            query.append(" BUSINESS_CATEGORY, BUSINESS_TYPE, ZIP_CODE, CUSTOMER_ADDRESS, ");
            query.append(" CUSTOMER_TEL, CUSTOMER_FAX, CUSTOMER_HOMEPAGE, CUSTOMER_EMAIL, ");
            query.append(" COUNTRY_CODE, CUSTOMER_TRADE_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customerBean.getCustomerNo().toUpperCase());
            pstmt.setString(2, customerBean.getCustomerName());
            pstmt.setString(3, customerBean.getBusinessNumber());
            pstmt.setString(4, customerBean.getRepresentaticeName());
            pstmt.setString(5, customerBean.getRepresentaticeNumber());
            pstmt.setString(6, customerBean.getBusinessCategory());
            pstmt.setString(7, customerBean.getBusinessType());
            pstmt.setString(8, customerBean.getZipCode());
            pstmt.setString(9, customerBean.getCustomerAddress());
            pstmt.setString(10, customerBean.getCustomerTel());
            pstmt.setString(11, customerBean.getCustomerFax());
            pstmt.setString(12, customerBean.getCustomerHomepage());
            pstmt.setString(13, customerBean.getCustomerEmail());
            pstmt.setString(14, customerBean.getCountryCode());
            pstmt.setString(15, customerBean.getCustomerTradeType());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
            con = dataSourceTransactionManager.getConnection();
            query.setLength(0);
            query.append(" INSERT INTO DETAIL_CODE (DETAIL_CODE_NO,CODE_NO,DETAIL_CODE_NAME) VALUES (?,'CU',?) ");
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customerBean.getCustomerNo().toUpperCase());
            pstmt.setString(2, customerBean.getCustomerName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }


	/* deleteCustomer */
    @Override
    public void deleteCustomer(String customer) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" DELETE FROM CUSTOMER WHERE CUSTOMER_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);

            query.setLength(0);
            query.append(" DELETE FROM DETAIL_CODE WHERE CODE_NO='CU' AND DETAIL_CODE_NO = ? ");
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, customer);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }
}