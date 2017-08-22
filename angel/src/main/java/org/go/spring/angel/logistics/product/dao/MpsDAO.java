package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.product.to.MpsBean;

import java.util.List;

public interface MpsDAO {
	public int selectMpsCount();

	public List<ContractItemBean> selectMpsList();

	public void insertMps(MpsBean mpsBean);

	public int selectMpsReviewCount();

	public List<MpsBean> selectMpsReviewList(int startRow, int endRow);
}
