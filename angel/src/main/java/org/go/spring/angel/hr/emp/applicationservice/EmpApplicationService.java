package org.go.spring.angel.hr.emp.applicationservice;

import org.go.spring.angel.hr.emp.exception.IDNotEditException;
import org.go.spring.angel.hr.emp.to.EmpBean;

import java.util.List;

public interface EmpApplicationService {

    public List<EmpBean> findEmpList(int startRow, int endRow);

    public int findEmpCount();

    public EmpBean findEmpDetail(String empNo);

    public void registerEmp(EmpBean empBean) throws IDNotEditException;

    public void modifyEmp(EmpBean empBean);

    public void removeEmp(String empNo);

}
