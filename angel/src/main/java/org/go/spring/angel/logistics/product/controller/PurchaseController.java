package org.go.spring.angel.logistics.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.to.ListForm;
import org.go.spring.angel.logistics.product.service.ProductServiceFacade;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.go.spring.angel.logistics.product.to.PurchaseBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PurchaseController extends MultiActionController {
    private ProductServiceFacade productServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setProductServiceFacade(ProductServiceFacade productServiceFacade) {
        this.productServiceFacade = productServiceFacade;
    }

    public ModelAndView findPurchaseList(HttpServletRequest request, HttpServletResponse response) {

        try {

            int pagenum = 1;
            int rowsize = 5;
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }
            String customer = request.getParameter("customer");
            int purchaseCount = productServiceFacade.findPurchaseCount(customer);
            ListForm page = new ListForm(rowsize, pagenum, purchaseCount);
            List<MrpTotalBean> mrpTotalList = productServiceFacade.findPurchaseList(page.getStartrow(),
                    page.getEndrow(), customer);
            page.setList(mrpTotalList);
            modelMap.put("mrpTotalList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "발주조회");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView registerPurchase(HttpServletRequest request, HttpServletResponse response) {

        try {

            String list = request.getParameter("registerList");
            ObjectMapper mapper = new ObjectMapper();
            List<PurchaseBean> purchaseList = mapper.readValue(list, new TypeReference<List<PurchaseBean>>() {
            });
            productServiceFacade.registerPurchase(purchaseList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "발주등록");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findPurchaseReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            System.out.println("startDate : " + startDate + ", endDate : " + endDate);
            List<PurchaseBean> purchaseReviewList = productServiceFacade.findPurchaseReviewList(startDate, endDate);
            modelMap.put("purchaseReviewList", purchaseReviewList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "발주내역조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}
