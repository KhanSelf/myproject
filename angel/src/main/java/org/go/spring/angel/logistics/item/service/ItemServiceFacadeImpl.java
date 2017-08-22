package org.go.spring.angel.logistics.item.service;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.item.applicationservice.ItemApplicationService;
import org.go.spring.angel.logistics.item.exception.ItemOverlapException;
import org.go.spring.angel.logistics.item.to.BomBean;
import org.go.spring.angel.logistics.item.to.ItemBean;
import org.go.spring.angel.logistics.item.to.WarehouseBean;

import java.util.List;

public class ItemServiceFacadeImpl implements ItemServiceFacade {
    private ItemApplicationService itemApplicationService;

    public void setItemApplicationService(ItemApplicationService itemApplicationService) {
        this.itemApplicationService = itemApplicationService;
    }

    @Override
    public List<ItemBean> findItemList() {
        return itemApplicationService.findItemList();
    }

    @Override
    public void batchItem(List<ItemBean> itemList) throws ItemOverlapException {
        itemApplicationService.batchItem(itemList);
    }

    @Override
    public List<CodeDetailBean> findBomMenu() {
        return itemApplicationService.findBomMenu();
    }

    @Override
    public List<BomBean> findBomList(String itemNo) {
        return itemApplicationService.findBomList(itemNo);
    }

    @Override
    public List<BomBean> findBomTurnList(String itemNo) {
        return itemApplicationService.findBomTurnList(itemNo);
    }

    @Override
    public List<WarehouseBean> findWarehouseList() {
        return itemApplicationService.findWarehouseList();
    }

}