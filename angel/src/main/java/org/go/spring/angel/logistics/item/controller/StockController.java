package org.go.spring.angel.logistics.item.controller;

import org.go.spring.angel.logistics.item.service.ItemServiceFacade;
import org.go.spring.angel.logistics.item.to.WarehouseBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class StockController extends MultiActionController {
    private ItemServiceFacade itemServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setItemServiceFacade(ItemServiceFacade itemServiceFacade) {
        this.itemServiceFacade = itemServiceFacade;
    }

    public ModelAndView findStockList(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<WarehouseBean> warehouseList = itemServiceFacade.findWarehouseList();
            modelMap.put("warehouseList", warehouseList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "재고조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

}
