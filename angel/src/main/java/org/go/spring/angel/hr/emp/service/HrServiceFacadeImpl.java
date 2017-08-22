package org.go.spring.angel.hr.emp.service;

import org.go.spring.angel.hr.emp.applicationservice.EmpApplicationService;
import org.go.spring.angel.hr.emp.exception.IDNotEditException;
import org.go.spring.angel.hr.emp.to.EmpBean;

import java.util.List;

public class HrServiceFacadeImpl implements HrServiceFacade{
	// -------------------dependency(bean-ref)-------------------//

	private EmpApplicationService empApplicationService;

	public void setEmpApplicationService(EmpApplicationService empApplicationService) {
		this.empApplicationService = empApplicationService;
	}

	// -------------------dependency(bean-ref)-------------------//


	/* findEmpList */
	@Override
	public List<EmpBean> findEmpList(int startRow, int endRow) {
		// TODO Auto-generated method stub
		return empApplicationService.findEmpList(startRow, endRow);
	}


	/* findEmpCount */
	@Override
	public int findEmpCount() {
		// TODO Auto-generated method stub
		return empApplicationService.findEmpCount();
	}


	/* findEmpDetail */
	@Override
	public EmpBean findEmpDetail(String empNo) {
		// TODO Auto-generated method stub
		return empApplicationService.findEmpDetail(empNo);
	}


	/* registerEmp */
	@Override
	public void registerEmp(EmpBean empBean) throws IDNotEditException {
		// TODO Auto-generated method stub
		empApplicationService.registerEmp(empBean);
	}


	/* modifyEmp */
	@Override
	public void modifyEmp(EmpBean empBean) {
		empApplicationService.modifyEmp(empBean);
	}


	/* removeEmp */
	@Override
	public void removeEmp(String empNo) {
		// TODO Auto-generated method stub
		empApplicationService.removeEmp(empNo);
	}

}
