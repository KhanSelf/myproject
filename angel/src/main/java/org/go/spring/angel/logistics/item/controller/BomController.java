package org.go.spring.angel.logistics.item.controller;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.item.service.ItemServiceFacade;
import org.go.spring.angel.logistics.item.to.BomBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BomController extends MultiActionController {

    private ItemServiceFacade itemServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setItemServiceFacade(ItemServiceFacade itemServiceFacade) {
        this.itemServiceFacade = itemServiceFacade;
    }

    public ModelAndView findBomMenu(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<CodeDetailBean> bomMenuList = itemServiceFacade.findBomMenu();
            modelMap.put("bomMenuList", bomMenuList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "BOM조회");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findBomList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String itemNo = request.getParameter("itemNo");
            List<BomBean> bomList = itemServiceFacade.findBomList(itemNo);

            modelMap.put("bomList", bomList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "BOM전개");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findBomTurnList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String itemNo = request.getParameter("itemNo");
            List<BomBean> bomList = itemServiceFacade.findBomTurnList(itemNo);
            modelMap.put("bomList", bomList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "BOM역전개");
        } catch (Exception e) {
            // TODO: handle exception
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

}
