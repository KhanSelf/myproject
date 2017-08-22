package org.go.spring.angel.logistics.product.service;

import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;

import java.util.HashMap;
import java.util.List;

public interface ProductServiceFacade {
	public int findMpsCount();

	public List<ContractItemBean> findMpsList();

	public void registerMps(List<MpsBean> mpsList);

	public int findMpsReviewCount();

	public List<MpsBean> findMpsReviewList(int startRow, int endRow);

	public int findMrpCount(String workplace, String startDate, String endDate);

	public List<MpsBean> findMrpList(String workplace, String startDate, String endDate, int startRow, int endRow);

	public HashMap<String, Object> findMrpDisassembleList(String workplace, String startDate, String endDate);

	public int findMrpTotalCount();

	public List<MrpBean> findMrpTotalList(int startRow, int endRow);

	public void registerMrpTotalList(List<MrpTotalBean> mrpTotalList);

	public int findMrpTotalReviewCount();

	public List<MrpTotalBean> findMrpTotalReviewList(int startRow, int endRow);

	public int findPurchaseCount(String customer);

	public List<MrpTotalBean> findPurchaseList(int startRow, int endRow, String customer);

	public void registerPurchase(List<PurchaseBean> purchaseList);

	public List<PurchaseBean> findPurchaseReviewList(String startDate, String endDate);
}
