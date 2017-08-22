package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.item.to.ItemBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public ItemBean selectItem(String item) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT ITEM.*,DETAIL_CODE.* FROM ITEM ITEM, ");
            query.append(" DETAIL_CODE DETAIL_CODE WHERE ITEM.ITEM_NO = ");
            query.append(" DETAIL_CODE.DETAIL_CODE_NO AND ITEM.ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, item);
            rs = pstmt.executeQuery();
            ItemBean itemBean = null;
            while (rs.next()) {
                itemBean = new ItemBean();
                itemBean.setItemNo(rs.getString("ITEM_NO"));
                itemBean.setItemName(rs.getString("ITEM_NAME"));
                itemBean.setItemUnit(rs.getString("ITEM_UNIT"));
                itemBean.setItemPrice(rs.getString("ITEM_PRICE"));
                itemBean.setLossRate(rs.getString("LOSS_RATE"));
                itemBean.setLeadTime(rs.getString("LEAD_TIME"));
                itemBean.setDemandQuantity(rs.getString("DEMAND_QUANTITY"));
                itemBean.setItemSupply(rs.getString("ITEM_SUPPLY"));
                itemBean.setFinishedItemStatus(rs.getString("FINISHED_ITEM_STATUS"));
                itemBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                itemBean.setDetailCodeNo(rs.getString("DETAIL_CODE_NO"));
                itemBean.setCodeNo(rs.getString("CODE_NO"));
                itemBean.setDetailCodeName(rs.getString("DETAIL_CODE_NAME"));
            }
            return itemBean;

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public String selectItemDetail(String finishedItemStatus) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM DETAIL_CODE WHERE CODE_NO = 'IT' AND DETAIL_CODE_NAME = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, finishedItemStatus);
            rs = pstmt.executeQuery();
            String detailItemCode = null;
            while (rs.next()) {
                detailItemCode = rs.getString("DETAIL_CODE_NO");
            }
            return detailItemCode;

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public List<ItemBean> selectItemList() {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ItemBean> itemList = new ArrayList<>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM ITEM ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ItemBean itemBean = new ItemBean();
                itemBean.setItemNo(rs.getString("ITEM_NO"));
                itemBean.setItemName(rs.getString("ITEM_NAME"));
                itemBean.setItemUnit(rs.getString("ITEM_UNIT"));
                itemBean.setItemPrice(rs.getString("ITEM_PRICE"));
                itemBean.setLossRate(rs.getString("LOSS_RATE"));
                itemBean.setLeadTime(rs.getString("LEAD_TIME"));
                itemBean.setDemandQuantity(rs.getString("DEMAND_QUANTITY"));
                itemBean.setItemSupply(rs.getString("ITEM_SUPPLY"));
                itemBean.setFinishedItemStatus(rs.getString("FINISHED_ITEM_STATUS"));
                itemBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
                itemList.add(itemBean);
            }
            return itemList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }

    @Override
    public void insertItem(ItemBean itemBean) {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO ITEM VALUES (?,?,?,?,?,?,?,?,?,?) ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemBean.getItemNo());
            pstmt.setString(2, itemBean.getItemName());
            pstmt.setString(3, itemBean.getItemUnit());
            pstmt.setString(4, itemBean.getItemPrice());
            pstmt.setString(5, itemBean.getLossRate());
            pstmt.setString(6, itemBean.getLeadTime());
            pstmt.setString(7, itemBean.getDemandQuantity());
            pstmt.setString(8, itemBean.getItemSupply());
            pstmt.setString(9, itemBean.getFinishedItemStatus());
            pstmt.setString(10, itemBean.getCustomerNo());
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);
            con = dataSourceTransactionManager.getConnection();
            query.setLength(0);
            query.append(" INSERT INTO DETAIL_CODE (DETAIL_CODE_NO,CODE_NO,DETAIL_CODE_NAME) ");
            query.append(" VALUES (?,?,?) ");
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemBean.getDetailCodeNo());
            pstmt.setString(2, itemBean.getCodeNo());
            pstmt.setString(3, itemBean.getDetailCodeName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    @Override
    public void updateItem(ItemBean itemBean) {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" UPDATE ITEM SET ITEM_NO = ?, ITEM_NAME = ?,ITEM_UNIT = ?, ");
            query.append(" ITEM_PRICE = ?,LOSS_RATE = ?, LEAD_TIME = ?, ");
            query.append(" DEMAND_QUANTITY = ?, ITEM_SUPPLY = ?,FINISHED_ITEM_STATUS = ?, ");
            query.append(" CUSTOMER_NO = ? WHERE ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemBean.getItemNo());
            pstmt.setString(2, itemBean.getItemName());
            pstmt.setString(3, itemBean.getItemUnit());
            pstmt.setString(4, itemBean.getItemPrice());
            pstmt.setString(5, itemBean.getLossRate());
            pstmt.setString(6, itemBean.getLeadTime());
            pstmt.setString(7, itemBean.getDemandQuantity());
            pstmt.setString(8, itemBean.getItemSupply());
            pstmt.setString(9, itemBean.getFinishedItemStatus());
            pstmt.setString(10, itemBean.getCustomerNo());
            pstmt.setString(11, itemBean.getItemNo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }

    @Override
    public void deleteItem(String itemNo) {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" DELETE ITEM WHERE ITEM_NO = ? ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            pstmt.executeUpdate();
            dataSourceTransactionManager.close(pstmt);

            query.setLength(0);
            query.append(" DELETE DETAIL_CODE WHERE DETAIL_CODE_NO = ? ");
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, itemNo);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt);
        }
    }
}
