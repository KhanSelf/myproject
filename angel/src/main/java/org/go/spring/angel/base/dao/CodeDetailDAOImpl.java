package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodeDetailDAOImpl implements CodeDetailDAO {
// -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//


    /* selectCodeDetailList */
    @Override
    public List<CodeDetailBean> selectCodeDetailList(int startRow, int endRow,
                                                     String codeNo) throws DataAccessException {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        List<CodeDetailBean> codeDetailList = new ArrayList<CodeDetailBean>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(
                    " SELECT DETAIL_CODE.CODE_NO,DETAIL_CODE.DETAIL_CODE_NO,");
            query.append(" DETAIL_CODE.DETAIL_CODE_NAME FROM ");
            query.append(" (SELECT ROWNUM NUM,DETAIL_CODE.* FROM DETAIL_CODE ");
            query.append(" WHERE CODE_NO=? ORDER BY NUM ASC) DETAIL_CODE ");
            query.append(" WHERE NUM BETWEEN ? AND ? ");

            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeNo);
            preparedstatement.setInt(2, startRow);
            preparedstatement.setInt(3, endRow);
            resultset = preparedstatement.executeQuery();

            while (resultset.next()) {
                CodeDetailBean codeDetailBean = new CodeDetailBean();
                codeDetailBean.setCodeNo(resultset.getString("CODE_NO"));
                codeDetailBean
                        .setCodeDetailNo(resultset.getString("DETAIL_CODE_NO"));
                codeDetailBean.setCodeDetailName(
                        resultset.getString("DETAIL_CODE_NAME"));
                codeDetailList.add(codeDetailBean);
            }
            return codeDetailList;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }
    }


    /*selectCodeDetailCount*/
    @Override
    public int selectCodeDetailCount(String codeNo) throws DataAccessException {
        // TODO Auto-generated method stub

        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM DETAIL_CODE WHERE CODE_NO=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeNo);
            resultset = preparedstatement.executeQuery();
            resultset.next();
            int codeDetailCount = resultset.getInt(1);
            return codeDetailCount;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }

    }


    /*selectCodeDetail*/
    @Override
    public String selectCodeDetail(String codeDetailNo)
            throws DataAccessException {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM DETAIL_CODE WHERE DETAIL_CODE=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeDetailNo);
            resultset = preparedstatement.executeQuery();
            String getCodeDetail = null;
            while (resultset.next()) {
                getCodeDetail = resultset.getString(1);
            }
            return getCodeDetail;
        } catch (SQLException e) {
            // TODO: handle exception

            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }
    }

    /*insertCodeDetail*/
    @Override
    public void insertCodeDetail(CodeDetailBean codeDetailBean)
            throws DataAccessException {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(
                    " INSERT INTO DETAIL_CODE (DETAIL_CODE_NO, CODE_NO, DETAIL_CODE_NAME) VALUES(?,?,?) ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1,
                    codeDetailBean.getCodeDetailNo().toUpperCase());
            preparedstatement.setString(2,
                    codeDetailBean.getCodeNo().toUpperCase());
            preparedstatement.setString(3,
                    codeDetailBean.getCodeDetailName().toUpperCase());
            preparedstatement.executeUpdate();
            dataSourceTransactionManager.close(preparedstatement);
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }


    /*updateCodeDetail*/
    @Override
    public void updateCodeDetail(String modifyCodeDetailNo,
                                 CodeDetailBean codeDetailBean) throws DataAccessException {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(
                    " UPDATE DETAIL_CODE SET DETAIL_CODE_NO = ? , DETAIL_CODE_NAME = ? WHERE DETAIL_CODE_NO = ? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1,
                    codeDetailBean.getCodeDetailNo().toUpperCase());
            preparedstatement.setString(2,
                    codeDetailBean.getCodeDetailName().toUpperCase());
            preparedstatement.setString(3, modifyCodeDetailNo);
            preparedstatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }


    /*deleteCodeDetail*/
    @Override
    public void deleteCodeDetail(String removeCodeDetailNo) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" DELETE FROM DETAIL_CODE WHERE DETAIL_CODE_NO = ? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, removeCodeDetailNo);
            preparedstatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }


    /*selectCodeDetailList*/
    @Override
    public List<CodeDetailBean> selectCodeDetailList(String codeNo)
            throws DataAccessException {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        List<CodeDetailBean> codeDetailList = new ArrayList<CodeDetailBean>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM DETAIL_CODE WHERE CODE_NO=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeNo);
            resultset = preparedstatement.executeQuery();
            while (resultset.next()) {
                CodeDetailBean codeDetailBean = new CodeDetailBean();
                codeDetailBean.setCodeNo(resultset.getString("CODE_NO"));
                codeDetailBean
                        .setCodeDetailNo(resultset.getString("DETAIL_CODE_NO"));
                codeDetailBean.setCodeDetailName(
                        resultset.getString("DETAIL_CODE_NAME"));
                codeDetailList.add(codeDetailBean);
            }
            return codeDetailList;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }
    }

}
