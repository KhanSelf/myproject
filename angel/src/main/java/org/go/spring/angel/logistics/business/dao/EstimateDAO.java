package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.business.to.EstimateBean;
import org.go.spring.angel.logistics.business.to.EstimateItemBean;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;

import java.util.List;

public interface EstimateDAO {

	public List<CodeDetailBean> selectCustomerMenu();

	public List<EstimateBean> selectEstimateList();

	public List<EstimateItemBean> selectEstimateItemList(String estimateNo);

	public void insertEstimate(EstimateBean estimateBean);

	public void insertEstimateItem(EstimateItemBean estimateItemBean);

	public List<EstimateItemBean> selectEstimateReviewList(String startDate, String endDate);

	public List<EstimatePDFBean> selectEstimateReport(String estimateNo);

}
