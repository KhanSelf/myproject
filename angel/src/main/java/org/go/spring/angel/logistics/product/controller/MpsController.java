package org.go.spring.angel.logistics.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.to.ListForm;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.product.service.ProductServiceFacade;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MpsController extends MultiActionController {

    private ProductServiceFacade productServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setProductServiceFacade(ProductServiceFacade productServiceFacade) {
        this.productServiceFacade = productServiceFacade;
    }

    public ModelAndView findMpsList(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<ContractItemBean> contractItemList = productServiceFacade.findMpsList();
            modelMap.put("contractItemList", contractItemList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MPS조회");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView registerMps(HttpServletRequest request, HttpServletResponse response) {

        try {

            String list = request.getParameter("registerList");
            ObjectMapper mapper = new ObjectMapper();
            List<MpsBean> mpsList = mapper.readValue(list, new TypeReference<List<MpsBean>>() {
            });
            productServiceFacade.registerMps(mpsList);

            modelMap.put("mpsList", mpsList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MPS등록");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findMpsReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {

            int pagenum = 1;
            int rowsize = 5;
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }
            int mpsReviewCount = productServiceFacade.findMpsReviewCount();
            ListForm page = new ListForm(rowsize, pagenum, mpsReviewCount);
            List<MpsBean> mpsReviewList = productServiceFacade.findMpsReviewList(page.getStartrow(), page.getEndrow());
            page.setList(mpsReviewList);

            modelMap.put("mpsReviewList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MPS조회");

        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}