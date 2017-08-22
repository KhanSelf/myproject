package org.go.spring.angel.hr.emp.applicationservice;

import org.go.spring.angel.hr.emp.dao.EmpDAO;
import org.go.spring.angel.hr.emp.exception.IDNotEditException;
import org.go.spring.angel.hr.emp.to.EmpBean;

import java.util.List;

public class EmpApplicationServiceImpl implements EmpApplicationService {

    // -------------------dependency(bean-ref)-------------------//

    private EmpDAO empDAO;
    public void setEmpDAO(EmpDAO empDAO) {
        this.empDAO = empDAO;
    }

    // -------------------dependency(bean-ref)-------------------//



    /* findEmpList */
    @Override
    public List<EmpBean> findEmpList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        List<EmpBean> empList = empDAO.selectEmpList(startRow, endRow);
        return empList;
    }


    /* findEmpCount */
    @Override
    public int findEmpCount() {
        // TODO Auto-generated method stub
        int empCount = empDAO.selectEmpCount();
        return empCount;
    }

    /* findEmpDetail */
    @Override
    public EmpBean findEmpDetail(String empNo) {
        // TODO Auto-generated method stub
        return empDAO.selectEmpDetail(empNo);
    }



    /*registerEmp*/
    @Override
    public void registerEmp(EmpBean empBean) throws IDNotEditException {
        // TODO Auto-generated method stub
        if (empDAO.findEmp(empBean.getEmpNo()) != null) {
            throw new IDNotEditException("오류:사원번호중복");
        } else {
            empDAO.insertEmp(empBean);
        }
    }


    /*modifyEmp*/
    @Override
    public void modifyEmp(EmpBean empBean) {
        // TODO Auto-generated method stub
        empDAO.updateEmp(empBean);
      }


    /*removeEmp*/
    @Override
    public void removeEmp(String empNo) {
        // TODO Auto-generated method stub
        empDAO.deleteEmp(empNo);
    }
}
