package org.go.spring.angel.logistics.item.service;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.item.exception.ItemOverlapException;
import org.go.spring.angel.logistics.item.to.BomBean;
import org.go.spring.angel.logistics.item.to.ItemBean;
import org.go.spring.angel.logistics.item.to.WarehouseBean;

import java.util.List;

public interface ItemServiceFacade {
	public List<ItemBean> findItemList();

	public void batchItem(List<ItemBean> itemList) throws ItemOverlapException;

	public List<CodeDetailBean> findBomMenu();

	public List<BomBean> findBomList(String itemNo);

	public List<BomBean> findBomTurnList(String itemNo);

	public List<WarehouseBean> findWarehouseList();
}
