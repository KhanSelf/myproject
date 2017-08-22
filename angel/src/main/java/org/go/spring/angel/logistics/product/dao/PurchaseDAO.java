package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.util.List;

public interface PurchaseDAO {
	public int selectPurchaseCount(String customer);

	public List<MrpTotalBean> selectPurchaseList(int startRow, int endRow, String customer);

	public void insertPurchase(PurchaseBean purchaseBean);

	public List<PurchaseBean> selectPurchaseReviewList(String startDate, String endDate);
}
