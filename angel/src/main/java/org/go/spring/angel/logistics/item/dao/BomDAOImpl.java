package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.item.to.BomBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BomDAOImpl implements BomDAO {
    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public List<CodeDetailBean> selectBomMenu() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CodeDetailBean> codeDetailList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM DETAIL_CODE WHERE CODE_NO LIKE 'IT-%' ");
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
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<BomBean> selectBomList(String itemNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BomBean> bomList = new ArrayList<>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT LPAD(' ', 5*(LEVEL-1)) || ITEM_NO HIERARCHICAL_ITEM, ");
            query.append(" BOM_NO, ITEM_NO, PARENT_ITEM_NO, QUANTITY FROM BOM ");
            query.append(" CONNECT BY PRIOR ITEM_NO = PARENT_ITEM_NO ");
            query.append(" START WITH ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BomBean bomBean = new BomBean();
                bomBean.setHierarchicalItem(rs.getString("HIERARCHICAL_ITEM"));
                bomBean.setBomNo(rs.getString("BOM_NO"));
                bomBean.setItemNo(rs.getString("ITEM_NO"));
                bomBean.setParentItemNo(rs.getString("PARENT_ITEM_NO"));
                bomBean.setQuantity(rs.getString("QUANTITY"));
                bomList.add(bomBean);
            }
            return bomList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<BomBean> selectBomTurnList(String itemNo) {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BomBean> bomList = new ArrayList<>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT LPAD(ITEM_NO, LEVEL * 5, ' ') HIERARCHICAL_ITEM, BOM_NO , ");
            query.append(" ITEM_NO, PARENT_ITEM_NO, QUANTITY FROM BOM CONNECT BY ");
            query.append(" PRIOR PARENT_ITEM_NO = ITEM_NO START WITH ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BomBean bomBean = new BomBean();
                bomBean.setHierarchicalItem(rs.getString("HIERARCHICAL_ITEM"));
                bomBean.setBomNo(rs.getString("BOM_NO"));
                bomBean.setItemNo(rs.getString("ITEM_NO"));
                bomBean.setParentItemNo(rs.getString("PARENT_ITEM_NO"));
                bomBean.setQuantity(rs.getString("QUANTITY"));
                bomList.add(bomBean);
            }
            return bomList;
        } catch (Exception e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}
