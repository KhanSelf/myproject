package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.logistics.item.to.ItemBean;

import java.util.List;

public interface ItemDAO {
	public ItemBean selectItem(String item);

	public List<ItemBean> selectItemList();

	public String selectItemDetail(String finishedItemStatus);

	void insertItem(ItemBean itemBean);

	void updateItem(ItemBean itemBean);

	void deleteItem(String itemNo);
}
