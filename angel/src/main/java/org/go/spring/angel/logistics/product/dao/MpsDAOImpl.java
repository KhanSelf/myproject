package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.product.to.MpsBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MpsDAOImpl implements MpsDAO {
    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public int selectMpsCount() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM CONTRACT_ITEM WHERE SHIPPING_STATUS = ");
            query.append(" 'N' AND MPS_STATUS = 'N' ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            rs.next();
            int codeCount = rs.getInt(1);

            return codeCount;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<ContractItemBean> selectMpsList() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ContractItemBean> contractItemList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT CONTRACT_ITEM.*,ITEM.*,ROWNUM NUM FROM ");
            query.append(" (SELECT CONTRACT_ITEM.*,ROWNUM NUM 	FROM CONTRACT_ITEM ");
            query.append(" WHERE SHIPPING_STATUS = 'N' AND MPS_STATUS = 'N') CONTRACT_ITEM, ");
            query.append(" (SELECT ITEM_NO,ITEM_NAME,ITEM_UNIT FROM ITEM) ITEM ");
            query.append(" WHERE CONTRACT_ITEM.ITEM_NO=ITEM.ITEM_NO ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ContractItemBean contractItemBean = new ContractItemBean();
                contractItemBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
                contractItemBean.setContractNo(rs.getString("CONTRACT_NO"));
                contractItemBean.setDemantDate(rs.getString("DEMANT_DATE"));
                contractItemBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
                contractItemBean.setItemNo(rs.getString("ITEM_NO"));
                contractItemBean.setItemName(rs.getString("ITEM_NAME"));
                contractItemBean.setItemUnit(rs.getString("ITEM_UNIT"));
                contractItemList.add(contractItemBean);
            }
            return contractItemList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public void insertMps(MpsBean mpsBean) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO MPS(MPS_NO,CONTRACT_ITEM_NO,PLAN_DATE,PLAN_AMOUNT, ");
            query.append(" WORKPLACE_NO,MRP_STATUS,PRODUCT_STATUS) VALUES ('MP'||?||LPAD ");
            query.append(" (MPS_NO_SEQ.NEXTVAL,4,0),?,?,?,?,'N','N') ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, mpsBean.getPlanDate().replace("-", ""));
            pstmt.setString(2, mpsBean.getContractItemNo());
            pstmt.setString(3, mpsBean.getPlanDate());
            pstmt.setString(4, mpsBean.getPlanAmount());
            pstmt.setString(5, mpsBean.getWorkplaceNo());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    @Override
    public int selectMpsReviewCount() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM MPS ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            rs.next();
            int mpsCount = rs.getInt(1);
            return mpsCount;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<MpsBean> selectMpsReviewList(int startRow, int endRow) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MpsBean> mpsList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM (SELECT MPS.*,ROWNUM NUM FROM MPS) MPS,(SELECT ");
            query.append(" A.CONTRACT_AMOUNT,A.CONTRACT_ITEM_NO,A.ITEM_NO,A.DEMANT_DATE,B.ITEM_NAME,B.ITEM_UNIT ");
            query.append(" FROM CONTRACT_ITEM A,ITEM B WHERE A.ITEM_NO = B.ITEM_NO) CONTRACT_ITEM WHERE ");
            query.append(" MPS.CONTRACT_ITEM_NO = CONTRACT_ITEM.CONTRACT_ITEM_NO AND NUM BETWEEN ? AND ? ");
            query.append(" ORDER BY MPS.CONTRACT_ITEM_NO DESC ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MpsBean mpsBean = new MpsBean();
                mpsBean.setMpsNo(rs.getString("MPS_NO"));
                mpsBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
                mpsBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
                mpsBean.setPlanDate(rs.getString("PLAN_DATE"));
                mpsBean.setPlanAmount(rs.getString("PLAN_AMOUNT"));
                mpsBean.setWorkplaceNo(rs.getString("WORKPLACE_NO"));
                mpsBean.setMrpStatus(rs.getString("MRP_STATUS"));
                mpsBean.setProductStatus(rs.getString("PRODUCT_STATUS"));
                mpsBean.setItemNo(rs.getString("ITEM_NO"));
                mpsBean.setDemantDate(rs.getString("DEMANT_DATE"));
                mpsBean.setItemName(rs.getString("ITEM_NAME"));
                mpsBean.setItemUnit(rs.getString("ITEM_UNIT"));
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
}
