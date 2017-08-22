package org.go.spring.angel.base.service;

import org.go.spring.angel.base.applicationservice.BaseApplicationService;
import org.go.spring.angel.base.applicationservice.CodeApplicationService;
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

public class BaseServiceFacadeImpl implements BaseServiceFacade {
    // -------------------dependency(bean-ref)-------------------//

    BaseApplicationService baseApplicationService;
    CodeApplicationService codeApplicationService;

    public void setBaseApplicationService(BaseApplicationService baseApplicationService) {
        this.baseApplicationService = baseApplicationService;
    }

    public void setCodeApplicationService(CodeApplicationService codeApplicationService) {
        this.codeApplicationService = codeApplicationService;
    }

    // -------------------dependency(bean-ref)-------------------//


	/* login */
    public EmpBean login(String empno, String password) throws IdNotFoundException, PwMissmatchException {
        return baseApplicationService.login(empno, password);
    }


	/*findCodeCount*/
    public int findCodeCount() {
        return codeApplicationService.findCodeCount();
    }


	/*findCodeList*/

    public List<CodeBean> findCodeList(int startRow, int endRow) {
        return codeApplicationService.findCodeList(startRow, endRow);
    }


	/*registerCode*/
    public void registerCode(CodeBean codeBean) throws CodeOverlapException {
        codeApplicationService.registerCode(codeBean);
    }


	/*modifyCode*/

    public void modifyCode(String modifyCodeNo, CodeBean codeBean) throws CodeNotEditException {
        codeApplicationService.modifyCode(modifyCodeNo, codeBean);
    }


	/*removeCode*/
    public void removeCode(String removeCodeNo) {
        codeApplicationService.removeCode(removeCodeNo);
    }


	/*findCodeDetailList*/

    public List<CodeDetailBean> findCodeDetailList(int startRow, int endRow, String codeNo) {
        return codeApplicationService.findCodeDetailList(startRow, endRow, codeNo);
    }


	/*findCodeDetailCount*/

    public int findCodeDetailCount(String codeNo) {
        return codeApplicationService.findCodeDetailCount(codeNo);
    }


	/*registerCodeDetail*/

    public void registerCodeDetail(CodeDetailBean codeDetailBean) throws CodeOverlapException {
        codeApplicationService.registerCodeDetail(codeDetailBean);
    }


	/*modifyCodeDetail*/

    public void modifyCodeDetail(String modifyCodeDetailNo, CodeDetailBean codeDetailBean) throws CodeOverlapException {
        codeApplicationService.modifyCodeDetail(modifyCodeDetailNo, codeDetailBean);
    }


	/*removeCodeDetail*/

    public void removeCodeDetail(String removeCodeDetailNo) {
        codeApplicationService.removeCodeDetail(removeCodeDetailNo);
    }


	/*findPostList*/

    public List<PostBean> findPostList(String dong) {
        return baseApplicationService.findPostList(dong);
    }


	/*findSidoList*/

    public List<PostBean> findSidoList() {
        return baseApplicationService.findSidoList();
    }


	/*findSigunguList*/

    public List<PostBean> findSigunguList(String sido) {
        return baseApplicationService.findSigunguList(sido);
    }


    /*findEstimateReport*/

    public List<EstimatePDFBean> findEstimateReport(String estimateNo) {
        return baseApplicationService.findEstimateReport(estimateNo);
    }

    /*  @Override
      public List<MenuBean> getMenuList(String empNo) {
          return baseApplicationService.findEstimateReport(empNo);
      }*/

  /*  @Override
    public List<PostBean> findRoadList(String sido, String sigungu, String roadname) {
        return baseApplicationService.findRoadList(sido, sigungu, roadname);
    }*/


}

