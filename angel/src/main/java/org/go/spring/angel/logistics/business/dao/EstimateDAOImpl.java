package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.business.to.EstimateBean;
import org.go.spring.angel.logistics.business.to.EstimateItemBean;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstimateDAOImpl implements EstimateDAO {
    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

	/* selectCustomerMenu */

    public List<CodeDetailBean> selectCustomerMenu() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CodeDetailBean> codeDetailList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM DETAIL_CODE WHERE CODE_NO='CU' ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CodeDetailBean codeDetailBean = new CodeDetailBean();
                codeDetailBean.setCodeNo(rs.getString("CODE_NO"));
                codeDetailBean.setCodeDetailNo(rs.getString("DETAIL_CODE_NO"));
                codeDetailBean.setCodeDetailName(rs.getString("DETAIL_CODE_NAME"));
                codeDetailList.add(codeDetailBean);
            }
            return codeDetailList;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* selectEstimateList */

    public List<EstimateBean> selectEstimateList() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EstimateBean> estimateList = new ArrayList<EstimateBean>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM ESTIMATE ORDER BY ESTIMATE_NO ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EstimateBean estimateBean = new EstimateBean();
                estimateBean.setEstimateNo(rs.getString("ESTIMATE_NO"));
                estimateBean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
                estimateBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                //estimateBean.setConApplyYN(rs.getString("CON_APPLY_YN"));
                //System.out.println("fucking1"+"\n"+estimateList.get(0).getEstimateNo());
                //System.out.println("fucking2"+"\n"+estimateList.get(0).getCustomerNo());
                estimateList.add(estimateBean);
            }
            return estimateList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* selectEstimateItemList */

    public List<EstimateItemBean> selectEstimateItemList(String estimateNo) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EstimateItemBean> estimateItemList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT ESTIMATE_ITEM.*, ITEM.ITEM_NAME, ");
            query.append(" ITEM.ITEM_UNIT, ITEM.ITEM_PRICE FROM ESTIMATE_ITEM ");
            query.append(" ESTIMATE_ITEM,ITEM ITEM WHERE ESTIMATE_ITEM.ITEM_NO = ");
            query.append(" ITEM.ITEM_NO AND ESTIMATE_NO = ? ORDER BY ESTIMATE_NO ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, estimateNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EstimateItemBean estimateItemBean = new EstimateItemBean();
                estimateItemBean.setEstimateItemNo(rs.getString("ESTIMATE_ITEM_NO"));
                estimateItemBean.setEstimateNo(rs.getString("ESTIMATE_NO"));
                estimateItemBean.setDemandDate(rs.getString("DEMAND_DATE"));
                estimateItemBean.setItemNo(rs.getString("ITEM_NO"));
                estimateItemBean.setEstimateAmount(rs.getString("ESTIMATE_AMOUNT"));
                estimateItemBean.setItemName(rs.getString("ITEM_NAME"));
                estimateItemBean.setItemUnit(rs.getString("ITEM_UNIT"));
                estimateItemBean.setItemPrice(rs.getString("ITEM_PRICE"));
                estimateItemList.add(estimateItemBean);
            }
            return estimateItemList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* insertEstimate */

    public void insertEstimate(EstimateBean estimateBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO ESTIMATE(ESTIMATE_NO, CUSTOMER_NO, ESTIMATE_DATE) ");
            query.append(" VALUES (?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, estimateBean.getEstimateNo());
            pstmt.setString(2, estimateBean.getCustomerNo());
            pstmt.setString(3, estimateBean.getEstimateDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }
    /*, CON_APPLY_YN*/

	/* insertEstimateItem */

    public void insertEstimateItem(EstimateItemBean estimateItemBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO ESTIMATE_ITEM(ESTIMATE_ITEM_NO,ESTIMATE_NO, ");
            query.append(" DEMAND_DATE,ITEM_NO,ESTIMATE_AMOUNT) VALUES ");
            query.append(" (?||'-'||LPAD(ESTIMATE_ITEM_NO_SEQ.NEXTVAL,4,0),?,?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, estimateItemBean.getEstimateNo());
            pstmt.setString(2, estimateItemBean.getEstimateNo());
            pstmt.setString(3, estimateItemBean.getDemandDate());
            pstmt.setString(4, estimateItemBean.getItemNo());
            pstmt.setString(5, estimateItemBean.getEstimateAmount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

	/* selectEstimateReviewList */

    public List<EstimateItemBean> selectEstimateReviewList(String startDate, String endDate) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EstimateItemBean> estimateItemList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT ESTIMATE.*,ESTIMATE_ITEM.*,ITEM.* FROM ");
            query.append(" ESTIMATE ESTIMATE,ESTIMATE_ITEM ESTIMATE_ITEM, ITEM ITEM ");
            query.append(" WHERE ESTIMATE.ESTIMATE_NO=ESTIMATE_ITEM.ESTIMATE_NO ");
            query.append(" AND ESTIMATE_ITEM.ITEM_NO=ITEM.ITEM_NO ");
            query.append(" AND ESTIMATE.ESTIMATE_DATE BETWEEN ? AND ? ORDER BY ESTIMATE_DATE ");

            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EstimateItemBean estimateItemBean = new EstimateItemBean();
                estimateItemBean.setEstimateItemNo(rs.getString("ESTIMATE_ITEM_NO"));
                estimateItemBean.setEstimateNo(rs.getString("ESTIMATE_NO"));
                estimateItemBean.setDemandDate(rs.getString("DEMAND_DATE"));
                estimateItemBean.setItemNo(rs.getString("ITEM_NO"));
                estimateItemBean.setEstimateAmount(rs.getString("ESTIMATE_AMOUNT"));
                estimateItemBean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
                estimateItemBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                estimateItemBean.setItemName(rs.getString("ITEM_NAME"));
                estimateItemBean.setItemUnit(rs.getString("ITEM_UNIT"));
                estimateItemBean.setItemPrice(rs.getString("ITEM_PRICE"));
                estimateItemList.add(estimateItemBean);
            }
            return estimateItemList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

	/* selectEstimateReport */
    public List<EstimatePDFBean> selectEstimateReport(String estimateNo) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EstimatePDFBean> estimatePDFList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT ESTIMATE_ITEM.ESTIMATE_ITEM_NO, ESTIMATE.ESTIMATE_NO, ESTIMATE_ITEM.DEMAND_DATE, ");
            query.append(" ESTIMATE_ITEM.ITEM_NO, ESTIMATE_ITEM.ESTIMATE_AMOUNT, ESTIMATE.ESTIMATE_DATE, ");
            query.append(" ESTIMATE.CUSTOMER_NO, ITEM.ITEM_NAME, ITEM.ITEM_UNIT, ");
            query.append(" ITEM.ITEM_PRICE, CUSTOMER.BUSINESS_TYPE, CUSTOMER.BUSINESS_CATEGORY, ");
            query.append(" CUSTOMER.CUSTOMER_NAME, CUSTOMER.CUSTOMER_ADDRESS, CUSTOMER.BUSINESS_NUMBER, ");
            query.append(" ITEM.DEMAND_QUANTITY, CUSTOMER.REPRESENTATICE_NUMBER ");
            query.append(" FROM ESTIMATE ESTIMATE,ESTIMATE_ITEM ESTIMATE_ITEM,ITEM ITEM, CUSTOMER CUSTOMER");
            query.append(" WHERE ESTIMATE.ESTIMATE_NO=ESTIMATE_ITEM.ESTIMATE_NO ");
            query.append(" AND ESTIMATE_ITEM.ITEM_NO=ITEM.ITEM_NO ");
            query.append(" AND ESTIMATE.CUSTOMER_NO=CUSTOMER.CUSTOMER_NO ");
            query.append(" AND ESTIMATE.ESTIMATE_NO=? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, estimateNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EstimatePDFBean estimatePDFBean = new EstimatePDFBean();
                estimatePDFBean.setEstimateItemNo(rs.getString("ESTIMATE_ITEM_NO"));
                estimatePDFBean.setEstimateNo(rs.getString("ESTIMATE_NO"));
                estimatePDFBean.setDemandDate(rs.getString("DEMAND_DATE"));
                estimatePDFBean.setItemNo(rs.getString("ITEM_NO"));
                estimatePDFBean.setEstimateAmount(rs.getString("ESTIMATE_AMOUNT"));
                estimatePDFBean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
                estimatePDFBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                estimatePDFBean.setItemName(rs.getString("ITEM_NAME"));
                estimatePDFBean.setItemUnit(rs.getString("ITEM_UNIT"));
                estimatePDFBean.setItemPrice(rs.getInt("ITEM_PRICE"));
                estimatePDFBean.setBusinessType(rs.getString("BUSINESS_TYPE"));
                estimatePDFBean.setBusinessCategory(rs.getString("BUSINESS_CATEGORY"));
                estimatePDFBean.setCustomerName(rs.getString("CUSTOMER_NAME"));
                estimatePDFBean.setCustomerAddress(rs.getString("CUSTOMER_ADDRESS"));
                estimatePDFBean.setRepresentaticeNumber(rs.getString("BUSINESS_NUMBER"));
                estimatePDFList.add(estimatePDFBean);
                //System.out.println(estimatePDFBean.getItemName());
            }
            return estimatePDFList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}