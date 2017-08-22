package org.go.spring.angel.logistics.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.to.ListForm;
import org.go.spring.angel.common.to.ResultBean;
import org.go.spring.angel.logistics.product.service.ProductServiceFacade;
import org.go.spring.angel.logistics.product.to.MpsBean;
import org.go.spring.angel.logistics.product.to.MrpBean;
import org.go.spring.angel.logistics.product.to.MrpTotalBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class MrpController extends MultiActionController {
    private ProductServiceFacade productServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setProductServiceFacade(ProductServiceFacade productServiceFacade) {
        this.productServiceFacade = productServiceFacade;
    }

    public ModelAndView findMrpList(HttpServletRequest request, HttpServletResponse response) {

        try {

            int pagenum = 1;
            int rowsize = 5;
            // 페이지 요청
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }
            String workplace = request.getParameter("workplace");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            int mrpCount = productServiceFacade.findMrpCount(workplace, startDate, endDate);
            ListForm page = new ListForm(rowsize, pagenum, mrpCount);
            List<MpsBean> mpsList = productServiceFacade.findMrpList(workplace, startDate, endDate, page.getStartrow(),
                    page.getEndrow());
            page.setList(mpsList);

            modelMap.put("mpsList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MRP조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findMrpDisassembleList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String workplace = request.getParameter("workplace");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            HashMap<String, Object> resultList = productServiceFacade.findMrpDisassembleList(workplace, startDate,
                    endDate);
            ResultBean error = (ResultBean) resultList.get("error");
            if (error.getErrorCode() == 0) {
                modelMap.put("mrpList", resultList.get("mrpList"));
                modelMap.put("errorCode", error.getErrorCode());
                modelMap.put("errorMsg", error.getErrorMsg());
            } else {
                logger.fatal(error.getErrorMsg());
                modelMap.put("errorCode", error.getErrorCode());
                modelMap.put("errorMsg", error.getErrorMsg());
            }
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findMrpTotalList(HttpServletRequest request, HttpServletResponse response) {

        try {

            int pagenum = 1;
            int rowsize = 5;
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }
            int mrpTotalCount = productServiceFacade.findMrpTotalCount();
            ListForm page = new ListForm(rowsize, pagenum, mrpTotalCount);
            List<MrpBean> mrpTotalList = productServiceFacade.findMrpTotalList(page.getStartrow(), page.getEndrow());
            page.setList(mrpTotalList);

            modelMap.put("mrpTotalList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MRP조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView registerMrpTotalList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String list = request.getParameter("mrpTotalList");
            ObjectMapper mapper = new ObjectMapper();
            List<MrpTotalBean> mrpTotalList = mapper.readValue(list, new TypeReference<List<MrpTotalBean>>() {
            });
            productServiceFacade.registerMrpTotalList(mrpTotalList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MRP등록");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findMrpTotalReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {

            int pagenum = 1;
            int rowsize = 5;
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }
            int mrpTotalReviewCount = productServiceFacade.findMrpTotalReviewCount();
            ListForm page = new ListForm(rowsize, pagenum, mrpTotalReviewCount);
            List<MrpTotalBean> mrpTotalReviewList = productServiceFacade.findMrpTotalReviewList(page.getStartrow(),
                    page.getEndrow());
            page.setList(mrpTotalReviewList);
            modelMap.put("mrpTotalReviewList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "MRP조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}
