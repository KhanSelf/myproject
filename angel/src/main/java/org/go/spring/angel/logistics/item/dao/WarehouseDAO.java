package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.logistics.item.to.WarehouseBean;

import java.util.List;

public interface WarehouseDAO {
	public List<WarehouseBean> selectWarehouseList();
}
