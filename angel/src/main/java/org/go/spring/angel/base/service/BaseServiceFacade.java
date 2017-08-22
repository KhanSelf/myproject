package org.go.spring.angel.base.service;

import org.go.spring.angel.base.exception.CodeNotEditException;
import org.go.spring.angel.base.exception.CodeOverlapException;
import org.go.spring.angel.base.exception.IdNotFoundException;
import org.go.spring.angel.base.exception.PwMissmatchException;
import org.go.spring.angel.base.to.CodeBean;
import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.base.to.PostBean;
import org.go.spring.angel.hr.emp.to.EmpBean;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;

import java.util.List;

public interface BaseServiceFacade {

	public EmpBean login(String empno, String password) throws IdNotFoundException, PwMissmatchException;

	public List<CodeBean> findCodeList(int startRow, int endRow);

	public int findCodeCount();

	public void registerCode(CodeBean codeBean) throws CodeOverlapException;

	public void modifyCode(String modifyCodeNo, CodeBean codeBean) throws CodeNotEditException;

	public void removeCode(String removeCodeNo);

	public List<CodeDetailBean> findCodeDetailList(int startRow, int endRow, String codeNo);

	public int findCodeDetailCount(String codeNo);

	public void registerCodeDetail(CodeDetailBean codeDetailBean) throws CodeOverlapException;

	public void modifyCodeDetail(String modifyCodeDetailNo, CodeDetailBean codeDetailBean) throws CodeOverlapException;

	public void removeCodeDetail(String removeCodeDetailNo);

	public List<PostBean> findPostList(String dong);

	public List<PostBean> findSidoList();

	public List<PostBean> findSigunguList(String sido);

	/*public List<MenuBean> getMenuList(String empNo);*/

	/*public List<PostBean> findRoadList(String sido, String sigungu, String roadname);*/

    List<EstimatePDFBean> findEstimateReport(String estimateNo);
}
