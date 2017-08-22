package org.go.spring.angel.logistics.product.dao;

import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;

import java.util.HashMap;
import java.util.List;

public interface MrpDAO {
	public int selectMrpCount(String workplace, String startDate, String endDate);

	public List<MpsBean> selectMrpList(String workplace, String startDate, String endDate, int startRow, int endRow);

	public HashMap<String, Object> selectMrpDisassembleList(String workplace, String startDate, String endDate);

	public int selectMrpTotalCount();

	public List<MrpBean> selectMrpTotalList(int startRow, int endRow);

	public void insertMrpTotalList(MrpTotalBean mrpTotalBean);

	public int selectMrpTotalReviewCount();

	public List<MrpTotalBean> selectMrpTotalReviewList(int startRow, int endRow);
}
