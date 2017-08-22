package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.transaction.DataSourceTransactionManager;
import org.go.spring.angel.logistics.item.to.WarehouseBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {

    // -------------------dependency(bean-ref)-------------------//

    private DataSourceTransactionManager dataSourceTransactionManager;

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    // -------------------dependency(bean-ref)-------------------//

    @Override
    public List<WarehouseBean> selectWarehouseList() {
        // TODO Auto-generated method stub

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WarehouseBean> warehouseList = new ArrayList<>();
        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT * FROM WAREHOUSE ORDER BY WAREHOUSE_NO ");
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WarehouseBean warehouseBean = new WarehouseBean();
                warehouseBean.setWarehouseNo(rs.getString("WAREHOUSE_NO"));
                warehouseBean.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
                warehouseBean.setWorkplaceNo(rs.getString("WORKPLACE_NO"));
                warehouseBean.setLocationCode(rs.getString("LOCATION_CODE"));
                warehouseBean.setEmpNo(rs.getString("EMP_NO"));
                warehouseList.add(warehouseBean);
            }
            return warehouseList;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            dataSourceTransactionManager.close(pstmt, rs);
        }
    }
}