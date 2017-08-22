package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.business.to.ShippingBean;

import java.util.List;

public interface ShippingDAO {

	public int selectShippingCount(String customer);

	public List<ContractItemBean> selectShippingList(String customer);

	public String selectShipping(String ShippingNo);

	public void insertShipping(ShippingBean shippingBean);

	public List<ShippingBean> selectShippingReviewList(String startDate, String endDate);

}
