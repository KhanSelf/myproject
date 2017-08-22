package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.CodeBean;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodeDAOImpl implements CodeDAO {
    // -------------------dependency(bean-ref)-------------------//
    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }
    // -------------------dependency(bean-ref)-------------------//


    /* selectCodeList */
    @Override
    public List<CodeBean> selectCodeList(int startRow, int endRow) {
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        List<CodeBean> codeList = new ArrayList<CodeBean>();

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT CODE.CODE_NO, CODE.CODE_NAME FROM ");
            query.append(" (SELECT ROWNUM NUM, CODE.* FROM CODE ORDER BY NUM ASC) CODE ");
            query.append(" WHERE NUM BETWEEN ? AND ? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setInt(1, startRow);
            preparedstatement.setInt(2, endRow);
            resultset = preparedstatement.executeQuery();

            while (resultset.next()) {
                CodeBean codeBean = new CodeBean();
                codeBean.setCodeNo(resultset.getString("CODE_NO"));
                codeBean.setCodeName(resultset.getString("CODE_NAME"));
                codeList.add(codeBean);
            }
            return codeList;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }
    }


    /* selectCodeCount */
    @Override
    public int selectCodeCount() {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT COUNT(*) FROM CODE ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            resultset = preparedstatement.executeQuery();
            resultset.next();
            int codeCount = resultset.getInt(1);
            return codeCount;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }

    }


    /* selectCode */
    @Override
    public CodeBean selectCode(String codeNo) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        CodeBean codeBean = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM CODE WHERE CODE_NO=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeNo);
            resultset = preparedstatement.executeQuery();

            while (resultset.next()) {
                codeBean = new CodeBean();
                codeBean.setCodeNo(resultset.getString("CODE_NO"));
                codeBean.setCodeName(resultset.getString("CODE_NAME"));
            }
            return codeBean;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement, resultset);
        }

    }


    /* insertCode */
    @SuppressWarnings("null")
    public void insertCode(CodeBean codeBean) {
        // TODO Auto-generated method stub
        @SuppressWarnings("unused")
        Connection connection = null;
        PreparedStatement preparedstatement = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" INSERT INTO CODE (CODE_NO, CODE_NAME) VALUES (?,?) ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement.setString(1, codeBean.getCodeNo().toUpperCase());
            preparedstatement.setString(2, codeBean.getCodeName().toUpperCase());
            preparedstatement.executeUpdate();
            dataSourceTransactionManager.close(preparedstatement);
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }

    /* updateCode */
    @Override
    public void updateCode(String modifyCodeNo, CodeBean codeBean) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" UPDATE CODE SET CODE_NO=?, CODE_NAME=? WHERE CODE_NO=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, codeBean.getCodeNo().toUpperCase());
            preparedstatement.setString(2, codeBean.getCodeName().toUpperCase());
            preparedstatement.setString(3, modifyCodeNo);
            preparedstatement.executeUpdate();


        } catch (SQLException e) {
            // TODO: handle exception

            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }


    /* deleteCode */
    @Override
    public void deleteCode(String removeCodeNo) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        try {
            StringBuffer query = new StringBuffer();
            query.append(" DELETE FROM CODE WHERE CODE_NO=? ");
            connection = dataSourceTransactionManager.getConnection();
            preparedstatement = connection.prepareStatement(query.toString());
            preparedstatement.setString(1, removeCodeNo);
            preparedstatement.executeUpdate();
            dataSourceTransactionManager.close(preparedstatement);
        } catch (SQLException e) {
            // TODO: handle exception
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(preparedstatement);
        }
    }
}
