package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.to.ResultBean;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MrpDAOImpl implements MrpDAO {
    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    public int selectMrpCount(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM MPS A, (SELECT MPS.MPS_NO, ROWNUM NUM FROM MPS ");
            query.append(" WHERE PRODUCT_STATUS = 'N' AND MRP_STATUS = 'N' AND WORKPLACE_NO = ? )B ");
            query.append(" WHERE A.MPS_NO = B.MPS_NO AND A.PLAN_DATE BETWEEN ? AND ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, workplace);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            rs = pstmt.executeQuery();
            rs.next();
            int mrpCount = rs.getInt(1);

            return mrpCount;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    public List<MpsBean> selectMrpList(String workplace, String startDate, String endDate, int startRow, int endRow) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MpsBean> mpsList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT MPS.*,ITEM.ITEM_NO,ITEM.ITEM_NAME,ITEM.ITEM_UNIT,CONTRACT_ITEM.CONTRACT_NO FROM ");
            query.append(" (SELECT MPS.*,ROWNUM NUM FROM MPS WHERE PRODUCT_STATUS = 'N' AND MRP_STATUS = 'N' AND ");
            query.append(" WORKPLACE_NO = ? AND PLAN_DATE BETWEEN ? AND ?) MPS,(SELECT ITEM_NO,ITEM_NAME,ITEM_UNIT ");
            query.append(" FROM ITEM) ITEM,CONTRACT_ITEM WHERE MPS.CONTRACT_ITEM_NO = CONTRACT_ITEM.CONTRACT_ITEM_NO ");
            query.append(" AND CONTRACT_ITEM.ITEM_NO = ITEM.ITEM_NO AND NUM BETWEEN ? AND ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, workplace);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            pstmt.setInt(4, startRow);
            pstmt.setInt(5, endRow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MpsBean mpsBean = new MpsBean();
                mpsBean.setWorkplaceNo(rs.getString("WORKPLACE_NO"));
                mpsBean.setContractNo(rs.getString("CONTRACT_NO"));
                mpsBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
                mpsBean.setItemNo(rs.getString("ITEM_NO"));
                mpsBean.setItemName(rs.getString("ITEM_NAME"));
                mpsBean.setItemUnit(rs.getString("ITEM_UNIT"));
                mpsBean.setPlanAmount(rs.getString("PLAN_AMOUNT"));
                mpsBean.setPlanDate(rs.getString("PLAN_DATE"));
                mpsBean.setMrpStatus(rs.getString("MRP_STATUS"));
                mpsList.add(mpsBean);
            }
            return mpsList;

        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    public HashMap<String, Object> selectMrpDisassembleList(String workplace, String startDate, String endDate) {
        // TODO Auto-generated method stub

        CallableStatement cstmt = null;
        Connection con = null;
        HashMap<String, Object> resultList = new HashMap<>();
        List<MrpBean> mrpList = new ArrayList<>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" {CALL P_MRP(?,?,?,?,?,?)} ");

            con = dataSourceTransactionManager.getConnection();
            cstmt = con.prepareCall(query.toString());
            cstmt.setString(1, workplace);
            cstmt.setString(2, startDate);
            cstmt.setString(3, endDate);
            cstmt.registerOutParameter(4, OracleTypes.NUMBER);
            cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
            cstmt.registerOutParameter(6, OracleTypes.CURSOR);
            cstmt.executeUpdate();

            ResultSet rs = (ResultSet) cstmt.getObject(6);
            while (rs.next()) {
                MrpBean mrpBean = new MrpBean();
                mrpBean.setMrpNo(rs.getString("MRP_NO"));
                mrpBean.setMpsNo(rs.getString("MPS_NO"));
                mrpBean.setPurchaseOrderAmount(rs.getString("PURCHASE_ORDER_AMOUNT"));
                mrpBean.setItemNo(rs.getString("ITEM_NO"));
                mrpBean.setMrpTotalStatus(rs.getString("MRP_TOTAL_STATUS"));
                mrpBean.setRequiredDate(rs.getString("REQUIRED_DATE"));
                mrpBean.setOrderDemantDate(rs.getString("ORDER_DEMANT_DATE"));
                mrpBean.setMrpTotalNo(rs.getString("MRP_TOTAL_NO"));
                mrpBean.setWorkplaceNo(rs.getString("WORKPLACE_NO"));
                mrpBean.setItemName(rs.getString("ITEM_NAME"));
                mrpBean.setItemUnit(rs.getString("ITEM_UNIT"));
                mrpBean.setItemSupply(rs.getString("ITEM_SUPPLY"));
                mrpBean.setFinishedItemStatus(rs.getString("FINISHED_ITEM_STATUS"));
                mrpBean.setParentItemNo(rs.getString("PARENT_ITEM_NO"));
                mrpList.add(mrpBean);
            }
            ResultBean resultBean = new ResultBean();
            resultBean.setErrorCode(cstmt.getInt(4));
            resultBean.setErrorMsg(cstmt.getString(5));
            resultList.put("mrpList", mrpList);
            resultList.put("error", resultBean);

            return resultList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(cstmt);
        }
    }

    public int selectMrpTotalCount() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM (SELECT ITEM_NO FROM MRP GROUP BY ITEM_NO) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            rs.next();
            int mrpTotalCount = rs.getInt(1);

            return mrpTotalCount;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    public List<MrpBean> selectMrpTotalList(int startRow, int endRow) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MrpBean> mrpTotalList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT A. *, B.ITEM_NAME, B.ITEM_UNIT, B.ITEM_SUPPLY, B.CUSTOMER_NO, ");
            query.append(" 'MT'||LPAD(MRP_TOTAL_NO_SEQ.NEXTVAL,8,0) MRP_TOTAL_NO ");
            query.append(" FROM (SELECT ITEM_NO, SUM(PURCHASE_ORDER_AMOUNT) PURCHASE_ORDER_AMOUNT, ");
            query.append(" MIN(REQUIRED_DATE) REQUIRED_DATE, MIN(ORDER_DEMANT_DATE) ORDER_DEMANT_DATE, ");
            query.append(" ROW_NUMBER() OVER (ORDER BY ITEM_NO DESC ) NUM FROM MRP GROUP BY ITEM_NO ");
            query.append(" ) A, ITEM B WHERE A.ITEM_NO = B.ITEM_NO AND NUM BETWEEN ? AND ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MrpBean mrpBean = new MrpBean();
                mrpBean.setItemNo(rs.getString("ITEM_NO"));
                mrpBean.setItemName(rs.getString("ITEM_NAME"));
                mrpBean.setItemUnit(rs.getString("ITEM_UNIT"));
                mrpBean.setRequiredDate(rs.getString("REQUIRED_DATE"));
                mrpBean.setOrderDemantDate(rs.getString("ORDER_DEMANT_DATE"));
                mrpBean.setPurchaseOrderAmount(rs.getString("PURCHASE_ORDER_AMOUNT"));
                mrpBean.setItemSupply(rs.getString("ITEM_SUPPLY"));
                mrpBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                mrpBean.setMrpTotalNo(rs.getString("MRP_TOTAL_NO"));
                mrpTotalList.add(mrpBean);
            }
            return mrpTotalList;

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    public void insertMrpTotalList(MrpTotalBean mrpTotalBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO MRP_TOTAL (MRP_TOTAL_NO, ITEM_NO, CUSTOMER_NO, ");
            query.append(" PURCHASE_ORDER_STATUS, PURCHASE_ORDER_AMOUNT, ");
            query.append(" PURCHASE_ORDER_DATE )  VALUES  ( ?, ?, ?, ?, ?, ? ) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, mrpTotalBean.getMrpTotalNo());
            pstmt.setString(2, mrpTotalBean.getItemNo());
            pstmt.setString(3, mrpTotalBean.getCustomerNo());
            pstmt.setString(4, "N");
            pstmt.setString(5, mrpTotalBean.getPurchaseOrderAmount());
            pstmt.setString(6, mrpTotalBean.getPurchaseOrderDate());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    public int selectMrpTotalReviewCount() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM MRP_TOTAL ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            rs.next();
            int mrpTotalReviewCount = rs.getInt(1);

            return mrpTotalReviewCount;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    public List<MrpTotalBean> selectMrpTotalReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MrpTotalBean> mrpTotalReviewList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT A.*,B.ITEM_NAME,B.ITEM_UNIT,B.ITEM_SUPPLY ");
            query.append(" FROM (SELECT MRP_TOTAL.*,ROWNUM NUM FROM MRP_TOTAL) A, ITEM B ");
            query.append(" WHERE A.ITEM_NO = B.ITEM_NO AND NUM BETWEEN ? AND ? ");
            query.append(" ORDER BY  A.PURCHASE_ORDER_STATUS,A.PURCHASE_ORDER_DATE DESC ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MrpTotalBean mrpTotalBean = new MrpTotalBean();
                mrpTotalBean.setMrpTotalNo(rs.getString("MRP_TOTAL_NO"));
                mrpTotalBean.setItemNo(rs.getString("ITEM_NO"));
                mrpTotalBean.setItemName(rs.getString("ITEM_NAME"));
                mrpTotalBean.setItemUnit(rs.getString("ITEM_UNIT"));
                mrpTotalBean.setPurchaseOrderDate(rs.getString("PURCHASE_ORDER_DATE"));
                mrpTotalBean.setPurchaseOrderAmount(rs.getString("PURCHASE_ORDER_AMOUNT"));
                mrpTotalBean.setItemSupply(rs.getString("ITEM_SUPPLY"));
                mrpTotalBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                mrpTotalBean.setPurchaseOrderStatus(rs.getString("PURCHASE_ORDER_STATUS"));
                mrpTotalReviewList.add(mrpTotalBean);
            }
            return mrpTotalReviewList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}
