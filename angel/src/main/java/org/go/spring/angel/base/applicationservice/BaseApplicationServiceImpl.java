package org.go.spring.angel.base.applicationservice;

import org.go.spring.angel.base.dao.PostDAO;
import org.go.spring.angel.base.exception.IdNotFoundException;
import org.go.spring.angel.base.exception.PwMissmatchException;
import org.go.spring.angel.base.to.PostBean;
import org.go.spring.angel.hr.emp.dao.EmpDAO;
import org.go.spring.angel.hr.emp.to.EmpBean;
import org.go.spring.angel.logistics.business.dao.EstimateDAO;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;

import java.util.List;

public class BaseApplicationServiceImpl implements BaseApplicationService {

    // -------------------dependency(bean-ref)-------------------//

    private EmpDAO empDAO;
    private PostDAO postDAO;
    private EstimateDAO estimateDAO;

    public void setEmpDAO(EmpDAO empDAO) {
        this.empDAO = empDAO;
    }

    public void setPostDAO(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public void setEstimateDAO(EstimateDAO estimateDAO) {
        this.estimateDAO = estimateDAO;
    }
    // -------------------dependency(bean-ref)-------------------//


    /* login */
    public EmpBean login(String empno, String password)
            throws IdNotFoundException, PwMissmatchException {
        // TODO Auto-generated method stub
        EmpBean empBean = empDAO.findEmp(empno);
        if (empBean == null) {
            throw new IdNotFoundException("오류:사원번호 확인");
        } else if (!empBean.getPassword().equals(password)) {
            throw new PwMissmatchException("오류:비밀번호 확인");
        }
        return empBean;
    }


    /* findPostList */
    public List<PostBean> findPostList(String dong) {
        // TODO Auto-generated method stub
        List<PostBean> PostList = postDAO.searchPostList(dong);
        return PostList;
    }


    /* findPostList */
    public List<PostBean> findSidoList() {
        // TODO Auto-generated method stub
        List<PostBean> sidoList = postDAO.searchSidoList();
        return sidoList;
    }


    /* findSigunguList */
    public List<PostBean> findSigunguList(String sido) {
        // TODO Auto-generated method stub
        List<PostBean> sigunguList = postDAO.searchSigunguList(sido);
        return sigunguList;
    }


   /* public List<MenuBean> findMenuList(String empNo) {
        // TODO Auto-generated method stub
          List<MenuBean> menuList = menuDAO.searchMenuList(empNo);
          return menuList;
    }*/


    /*findRoadList*/
    public List<PostBean> findRoadList(String sido,
                                       String sigungu, String roadname) {
        // TODO Auto-generated method stub
        return postDAO.searchRoadList(sido, sigungu, roadname);
    }

    /* findEstimateReport */
    public List<EstimatePDFBean> findEstimateReport(String estimateNo) {
    // TODO Auto-generated method stub
        return estimateDAO.selectEstimateReport(estimateNo);
    }
}
