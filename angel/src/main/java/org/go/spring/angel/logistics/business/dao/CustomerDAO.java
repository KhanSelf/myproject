package org.go.spring.angel.logistics.business.dao;

import org.go.spring.angel.logistics.business.to.CustomerBean;

import java.util.List;

public interface CustomerDAO {

	public List<CustomerBean> selectCustomerList();

	public CustomerBean selectCustomer(String customerNo);

	public void insertCustomer(CustomerBean customerBean);

	public void deleteCustomer(String customer);

}
