package org.go.spring.angel.logistics.item.to;

import java.util.List;

public class WarehouseBean {
	String warehouseNo;
	String warehouseName;
	String workplaceNo;
	String locationCode;
	String empNo;
	List<StockBean> stockList;

	public List<StockBean> getStockList() {
		return stockList;
	}
	public void setStockList(List<StockBean> stockList) {
		this.stockList = stockList;
	}
	public String getWarehouseNo() {
		return warehouseNo;
	}
	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWorkplaceNo() {
		return workplaceNo;
	}
	public void setWorkplaceNo(String workplaceNo) {
		this.workplaceNo = workplaceNo;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}


}
