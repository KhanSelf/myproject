package org.go.spring.angel.hr.emp.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.hr.emp.to.EmpBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpDAOImpl implements EmpDAO {
	// -------------------dependency(bean-ref)-------------------//

	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	// -------------------dependency(bean-ref)-------------------//


	/* findEmp */
	@Override
	public EmpBean findEmp(String empno) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpBean empBean = null;

		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT * FROM EMP WHERE EMP_NO=? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empBean = new EmpBean();
				empBean.setEmpNo(rs.getString("EMP_NO"));
				empBean.setEmpName(rs.getString("EMP_NAME"));
				empBean.setPassword(rs.getString("PASSWORD"));
			}
			return empBean;
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/*selectEmpList*/
	@Override
	public List<EmpBean> selectEmpList(int startRow, int endRow) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EmpBean> empList = new ArrayList<EmpBean>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT EMP.NUM,EMP.EMP_NO,EMP.EMP_NAME FROM ");
			query.append(" (SELECT ROWNUM NUM,EMP.* FROM EMP ORDER BY NUM ASC) EMP ");
			query.append(" WHERE NUM BETWEEN ? AND ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmpBean empBean = new EmpBean();
				empBean.setEmpNo(rs.getString("EMP_NO"));
				empBean.setEmpName(rs.getString("EMP_NAME"));
				empList.add(empBean);
			}
			return empList;
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/*selectEmpCount*/
	@Override
	public int selectEmpCount() {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT COUNT(*) FROM EMP ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rs.next();
			int empCount = rs.getInt(1);
			return empCount;
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/*selectEmpDetail*/
	@Override
	public EmpBean selectEmpDetail(String empNo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpBean empBean = new EmpBean();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT * FROM EMP WHERE EMP_NO = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empBean.setEmpNo(rs.getString("EMP_NO"));
				empBean.setDeptNo(rs.getString("DEPT_NO"));
				empBean.setEmpName(rs.getString("EMP_NAME"));
				empBean.setEmpGender(rs.getString("EMP_GENDER"));
				empBean.setHireDate(rs.getString("HIRE_DATE"));
				empBean.setEmpTel(rs.getString("EMP_TEL"));
				empBean.setEmpCelpone(rs.getString("EMP_CELPONE"));
				empBean.setZipCode(rs.getString("ZIP_CODE"));
				empBean.setBasicAddress(rs.getString("BASIC_ADDRESS"));
				empBean.setDetailAddress(rs.getString("DETAIL_ADDRESS"));
				empBean.setEmpEmail(rs.getString("EMP_EMAIL"));
				empBean.setPosition(rs.getString("POSITION"));
				empBean.setPassword(rs.getString("PASSWORD"));
				empBean.setImage(rs.getString("IMAGE"));
			}
			return empBean;
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}


	/*insertEmp*/
	@Override
	public void insertEmp(EmpBean empBean) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO EMP (EMP_NO,EMP_NAME) VALUES (?,?) ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empBean.getEmpNo());
			pstmt.setString(2, empBean.getEmpName());
			pstmt.executeUpdate();
			dataSourceTransactionManager.close(pstmt);
			con = dataSourceTransactionManager.getConnection();
			query.setLength(0);
			query.append(" INSERT INTO DETAIL_CODE (DETAIL_CODE_NO,CODE_NO,DETAIL_CODE_NAME) VALUES (?,'EMP',?) ");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empBean.getEmpNo());
			pstmt.setString(2, empBean.getEmpNo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}


	/*updateEmp*/
	@Override
	public void updateEmp(EmpBean empBean) {
		// TODO Auto-generated method stub
			Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" UPDATE EMP SET DEPT_NO = ?,EMP_NAME = ?,EMP_GENDER = ?, ");
			query.append(" HIRE_DATE = ?,EMP_TEL = ?,EMP_CELPONE = ?,ZIP_CODE = ?, ");
			query.append(" BASIC_ADDRESS = ?,DETAIL_ADDRESS = ?,EMP_EMAIL = ?,POSITION = ?, ");
			query.append(" PASSWORD = ?,IMAGE = ? WHERE EMP_NO = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empBean.getDeptNo());
			pstmt.setString(2, empBean.getEmpName());
			pstmt.setString(3, empBean.getEmpGender());
			pstmt.setString(4, empBean.getHireDate());
			pstmt.setString(5, empBean.getEmpTel());
			pstmt.setString(6, empBean.getEmpCelpone());
			pstmt.setString(7, empBean.getZipCode());
			pstmt.setString(8, empBean.getBasicAddress());
			pstmt.setString(9, empBean.getDetailAddress());
			pstmt.setString(10, empBean.getEmpEmail());
			pstmt.setString(11, empBean.getPosition());
			pstmt.setString(12, empBean.getPassword());
			pstmt.setString(13, empBean.getImage());
			pstmt.setString(14, empBean.getEmpNo());
			pstmt.executeUpdate();
			dataSourceTransactionManager.close(pstmt);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}


	/*deleteEmp*/
	@Override
	public void deleteEmp(String EmpNo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" DELETE FROM EMP WHERE EMP_NO = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, EmpNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

}
