package org.go.spring.angel.common.transaction;

import org.go.spring.angel.common.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class DataSourceTransactionManager {

	private static DataSource dataSource;
	private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public Connection getConnection() {
		Connection connection = (Connection) threadLocal.get();
		try {
			if (connection == null) {
				connection = dataSource.getConnection();
				threadLocal.set(connection);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
		return connection;
	}

	public void closeConnection() {
		Connection conn = (Connection) threadLocal.get();
		threadLocal.set(null);
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void beginTransaction() {
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void rollbackTransaction() {
		try {
			getConnection().rollback();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void commitTransaction() {
		try {
			getConnection().commit();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}


	public void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void close(PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void close(Connection conn) {
		closeConnection();
	}

	public void close(Connection conn, PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}
}