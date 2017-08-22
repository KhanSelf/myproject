package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.PostBean;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAOImpl implements PostDAO {
	// -------------------dependency(bean-ref)-------------------//

	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	// -------------------dependency(bean-ref)-------------------//


	/* searchPostList */
	@Override
	public List<PostBean> searchPostList(String dong)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		List<PostBean> postList = new ArrayList<PostBean>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(
					" SELECT SIDO, GUGUN, DONG, RI, ZIP_NO FROM S_ZIPCODE WHERE DONG LIKE '%"+ dong + "%' ");
			connection = dataSourceTransactionManager.getConnection();
			preparedstatement = connection.prepareStatement(query.toString());
			resultset = preparedstatement.executeQuery();
			while (resultset.next()) {
				PostBean postBean = new PostBean();
				postBean.setDong(resultset.getString("DONG"));
				postBean.setRi(resultset.getString("RI"));
				postBean.setSido(resultset.getString("SIDO"));
				postBean.setSigungu(resultset.getString("GUGUN"));
				postBean.setZipno(resultset.getString("ZIP_NO"));
				postList.add(postBean);
			}
			return postList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(preparedstatement, resultset);
		}
	}


	/* searchSidoList */
	@Override
	public List<PostBean> searchSidoList() throws DataAccessException {
		// TODO Auto-generated method stub
				Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		List<PostBean> postSidoList = new ArrayList<PostBean>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT distinct * FROM POST_SI ORDER BY VALUE ");
			connection = dataSourceTransactionManager.getConnection();
			preparedstatement = connection.prepareStatement(query.toString());
			resultset = preparedstatement.executeQuery();

			while (resultset.next()) {
				PostBean postBean = new PostBean();
				postBean.setSido(resultset.getString("CODE"));
				postBean.setSidoname(resultset.getString("VALUE"));
				postSidoList.add(postBean);
			}
			return postSidoList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(preparedstatement, resultset);
		}
	}


	/* searchSigunguList */
	@Override
	public List<PostBean> searchSigunguList(String sido)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		List<PostBean> postSigunguList = new ArrayList<PostBean>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT VALUE FROM POST_SIGUNGU WHERE CODE = ? GROUP BY VALUE");
			connection = dataSourceTransactionManager.getConnection();
			preparedstatement = connection.prepareStatement(query.toString());
			preparedstatement.setString(1, sido);
			resultset = preparedstatement.executeQuery();
			while (resultset.next()) {
				PostBean postBean = new PostBean();
				postBean.setSidoname(resultset.getString("VALUE"));
				postSigunguList.add(postBean);
			}
			return postSigunguList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataAccessException(e.getMessage());
		} finally {
			dataSourceTransactionManager.close(preparedstatement, resultset);
		}
	}



	/* searchSigunguList */
	@Override
public List<PostBean> searchRoadList(String sido, String sigungu, String roadname) {
// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
	ArrayList<PostBean> postRoadList=new ArrayList<PostBean>();
		try {
			String sidoTable="road_post_"+sido;
			StringBuffer query = new StringBuffer();
			query.append("SELECT zipcode, road_name, building_code1, building_code2 ");
			query.append(" FROM "+sidoTable+" where sigungu='"+sigungu+"' and road_name like '%"+ roadname +"%'");
			preparedstatement = connection.prepareStatement(query.toString());
			resultset = preparedstatement.executeQuery();
			while(resultset.next()){
				PostBean postBean = new PostBean();
				postBean.setZipno(resultset.getString("zipcode"));
				postBean.setRoadname(resultset.getString("road_name"));
				postBean.setBuildingcode1(resultset.getString("building_code1"));
				postBean.setBuildingcode2(resultset.getString("building_code2"));
				postRoadList.add(postBean);
			}

			return postRoadList;
		} catch(SQLException sqle) {
			throw new DataAccessException("데이터접속오류!");
		} finally {
			try{
				dataSourceTransactionManager.close(preparedstatement, resultset);
			}catch(Exception e){throw new DataAccessException("오류다"); }
		}
	}
}
