package org.go.spring.angel.base.applicationservice;

import org.go.spring.angel.base.exception.IdNotFoundException;
import org.go.spring.angel.base.exception.PwMissmatchException;
import org.go.spring.angel.base.to.PostBean;
import org.go.spring.angel.hr.emp.to.EmpBean;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;

import java.util.List;

public interface BaseApplicationService {

	EmpBean login(String empno, String password)
			throws IdNotFoundException, PwMissmatchException;

	public List<PostBean> findPostList(String dong);

	List<PostBean> findSidoList();

	List<PostBean> findSigunguList(String sido);

	/*List<MenuBean> findMenuList(String empNo);*/

    List<PostBean> findRoadList(String sido, String sigungu, String roadname);


	public List<EstimatePDFBean> findEstimateReport(String estimateNo);


}
