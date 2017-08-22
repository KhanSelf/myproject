package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.logistics.business.to.ShippingBean;
import org.go.spring.angel.logistics.item.to.StockBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.util.List;

public interface StockDAO {
	public List<StockBean> selectStockList(String warehouseNo);

	public int selectStockAmount(String itemNo);

	public String selectWarehouseNo(String itemNo);

	public void insertShippingList(ShippingBean shippingBean, String warehouseNo, int amount);

	public void insertPurchaseList(PurchaseBean purchaseBean, String warehouseNo, int amount);

}
