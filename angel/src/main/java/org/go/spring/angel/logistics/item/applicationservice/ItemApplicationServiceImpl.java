package org.go.spring.angel.logistics.item.applicationservice;

import org.go.spring.angel.base.dao.CodeDetailDAO;
import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.item.dao.BomDAO;
import org.go.spring.angel.logistics.item.dao.ItemDAO;
import org.go.spring.angel.logistics.item.dao.StockDAO;
import org.go.spring.angel.logistics.item.dao.WarehouseDAO;
import org.go.spring.angel.logistics.item.exception.ItemOverlapException;
import org.go.spring.angel.logistics.item.to.BomBean;
import org.go.spring.angel.logistics.item.to.ItemBean;
import org.go.spring.angel.logistics.item.to.WarehouseBean;

import java.util.List;

public class ItemApplicationServiceImpl implements ItemApplicationService {

    ItemDAO itemDAO;
    BomDAO bomDAO;
    WarehouseDAO warehouseDAO;
    StockDAO stockDAO;
    CodeDetailDAO codeDetailDAO;

    public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
        this.codeDetailDAO = codeDetailDAO;
    }

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void setBomDAO(BomDAO bomDAO) {
        this.bomDAO = bomDAO;
    }

    public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public List<ItemBean> findItemList() {

        return itemDAO.selectItemList();
    }

    @Override
    public void batchItem(List<ItemBean> itemList) throws ItemOverlapException {

        for (ItemBean itemBean : itemList) {
            switch (itemBean.getStatus()) {
                case "insert":
                    if (itemDAO.selectItem(itemBean.getItemNo()) != null) {
                        throw new ItemOverlapException("동일한 품목번호가 존재합니다");
                    } else {
                        itemBean.setCodeNo(itemDAO.selectItemDetail(itemBean.getFinishedItemStatus()));
                        itemBean.setDetailCodeNo(itemBean.getItemNo());
                        itemBean.setDetailCodeName(itemBean.getItemName());
                        itemDAO.insertItem(itemBean);
                    }
                    break;
                case "update":
                    itemDAO.updateItem(itemBean);
                    break;
                case "delete":
                    itemDAO.deleteItem(itemBean.getItemNo());
                    break;
            }
        }
    }

    @Override
    public List<CodeDetailBean> findBomMenu() {
        return bomDAO.selectBomMenu();
    }

    @Override
    public List<BomBean> findBomList(String itemNo) {

        List<BomBean> bomList = bomDAO.selectBomList(itemNo);
        for (BomBean bomBean : bomList) {
            bomBean.setItemDetailBean(itemDAO.selectItem(bomBean.getItemNo()));
        }
        return bomList;
    }

    @Override
    public List<BomBean> findBomTurnList(String itemNo) {

        List<BomBean> bomList = bomDAO.selectBomTurnList(itemNo);
        for (BomBean bomBean : bomList) {
            bomBean.setItemDetailBean(itemDAO.selectItem(bomBean.getItemNo()));
        }
        return bomList;
    }

    @Override
    public List<WarehouseBean> findWarehouseList() {

        List<WarehouseBean> warehouseList = warehouseDAO.selectWarehouseList();
        for (WarehouseBean warehouseBean : warehouseList) {
            warehouseBean.setStockList(stockDAO.selectStockList(warehouseBean.getWarehouseNo()));
        }
        return warehouseList;
    }
}
