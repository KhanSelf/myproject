package org.go.spring.angel.hr.emp.dao;

import org.go.spring.angel.hr.emp.to.EmpBean;

import java.util.List;

public interface EmpDAO {

	public EmpBean findEmp(String empno);

	public List<EmpBean> selectEmpList(int startRow, int endRow);

	public int selectEmpCount();

	public EmpBean selectEmpDetail(String empNo);

	public void insertEmp(EmpBean empBean);

	public void updateEmp(EmpBean empBean);

	public void deleteEmp(String EmpNo);

}