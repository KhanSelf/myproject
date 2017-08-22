package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.business.to.ContractBean;
import org.go.spring.angel.logistics.business.to.ContractItemBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContractDAOImpl implements ContractDAO {
	// -------------------dependency(bean-ref)-------------------//

	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	// -------------------dependency(bean-ref)-------------------//


	/* selectContractList */
	@Override
	public List<ContractBean> selectContractList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ContractBean> contractList = new ArrayList<>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT * FROM CONTRACT ORDER BY CONTRACT_NO DESC ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ContractBean contractBean = new ContractBean();
				contractBean.setContractNo(rs.getString("CONTRACT_NO"));
				contractBean.setContractDate(rs.getString("CONTRACT_DATE"));
				contractBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
				contractList.add(contractBean);
			}
			return contractList;
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/* selectContractItemList */
	@Override
	public List<ContractItemBean> selectContractItemList(String contractNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ContractItemBean> contractItemList = new ArrayList<>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT CONTRACT_ITEM.*, ITEM.ITEM_NAME, ITEM.ITEM_UNIT ");
			query.append(" FROM CONTRACT_ITEM  CONTRACT_ITEM,ITEM ITEM ");
			query.append(" WHERE CONTRACT_ITEM.ITEM_NO =  ITEM.ITEM_NO ");
			query.append(" AND CONTRACT_NO = ? ORDER BY CONTRACT_NO ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, contractNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ContractItemBean contractItemBean = new ContractItemBean();
				contractItemBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
				contractItemBean.setContractNo(rs.getString("CONTRACT_NO"));
				contractItemBean.setDemantDate(rs.getString("DEMANT_DATE"));
				contractItemBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
				contractItemBean.setShippingStatus(rs.getString("SHIPPING_STATUS"));
				contractItemBean.setMpsStatus(rs.getString("MPS_STATUS"));
				contractItemBean.setItemNo(rs.getString("ITEM_NO"));
				contractItemBean.setItemName(rs.getString("ITEM_NAME"));
				contractItemBean.setItemUnit(rs.getString("ITEM_UNIT"));
				contractItemList.add(contractItemBean);
			}
			return contractItemList;
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/* insertContract */
	@Override
	public void insertContract(ContractBean contractBean) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO CONTRACT(CONTRACT_NO,CONTRACT_DATE,CUSTOMER_NO) ");
			query.append(" VALUES (?,?,?) ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, contractBean.getContractNo());
			pstmt.setString(2, contractBean.getContractDate());
			pstmt.setString(3, contractBean.getCustomerNo());
			pstmt.executeUpdate();
			dataSourceTransactionManager.close(pstmt);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}


	/* insertContractItem */
	@Override
	public void insertContractItem(ContractItemBean contractItemBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO CONTRACT_ITEM(CONTRACT_ITEM_NO,CONTRACT_NO, ");
			query.append(" DEMANT_DATE,ITEM_NO,CONTRACT_AMOUNT,SHIPPING_STATUS,MPS_STATUS) VALUES ");
			query.append(" (?||'-'||LPAD(CONTRACT_ITEM_NO_SEQ.NEXTVAL,4,0),?,?,?,?,?,?) ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, contractItemBean.getContractNo());
			pstmt.setString(2, contractItemBean.getContractNo());
			pstmt.setString(3, contractItemBean.getDemantDate());
			pstmt.setString(4, contractItemBean.getItemNo());
			pstmt.setString(5, contractItemBean.getContractAmount());
			pstmt.setString(6, contractItemBean.getShippingStatus());
			pstmt.setString(7, contractItemBean.getMpsStatus());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}


	/* selectContractReviewList */
	@Override
	public List<ContractItemBean> selectContractReviewList(String startDate, String endDate)
			throws DataAccessException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ContractItemBean> contractItemList = new ArrayList<>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT CONTRACT.*,CONTRACT_ITEM.*,ITEM.* FROM ");
			query.append(" CONTRACT CONTRACT,CONTRACT_ITEM CONTRACT_ITEM, ITEM ITEM ");
			query.append(" WHERE CONTRACT.CONTRACT_NO = CONTRACT_ITEM.CONTRACT_NO ");
			query.append(" AND CONTRACT_ITEM.ITEM_NO=ITEM.ITEM_NO ");
			query.append(" AND CONTRACT.CONTRACT_DATE BETWEEN ? AND ? ORDER BY CONTRACT.CONTRACT_NO DESC ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ContractItemBean contractItemBean = new ContractItemBean();
				contractItemBean.setContractItemNo(rs.getString("CONTRACT_ITEM_NO"));
				contractItemBean.setContractNo(rs.getString("CONTRACT_NO"));
				contractItemBean.setDemantDate(rs.getString("DEMANT_DATE"));
				contractItemBean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
				contractItemBean.setShippingStatus(rs.getString("SHIPPING_STATUS"));
				contractItemBean.setMpsStatus(rs.getString("MPS_STATUS"));
				contractItemBean.setItemNo(rs.getString("ITEM_NO"));
				contractItemBean.setItemName(rs.getString("ITEM_NAME"));
				contractItemBean.setItemUnit(rs.getString("ITEM_UNIT"));
				contractItemBean.setContractDate(rs.getString("CONTRACT_DATE"));
				contractItemBean.setCustomerNo(rs.getString("CUSTOMER_NO"));
				contractItemList.add(contractItemBean);
			}
			return contractItemList;
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/* updateContractShippingStatus */
	@Override
	public void updateContractShippingStatus(String contractItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" UPDATE CONTRACT_ITEM SET SHIPPING_STATUS = 'Y' WHERE CONTRACT_ITEM_NO = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, contractItemNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}


	/* updateContractMpsStatus */
	@Override
	public void updateContractMpsStatus(String contractItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" UPDATE CONTRACT_ITEM SET MPS_STATUS = 'Y' WHERE CONTRACT_ITEM_NO = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, contractItemNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
}
