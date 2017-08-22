package org.go.spring.angel.logistics.business.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.logistics.business.exception.EstimateNotEditException;
import org.go.spring.angel.logistics.business.service.BusinessServiceFacade;
import org.go.spring.angel.logistics.business.to.EstimateBean;
import org.go.spring.angel.logistics.business.to.EstimateItemBean;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

public class EstimateController extends MultiActionController {
    private BusinessServiceFacade businessServiceFacade;
    private MessageSource messageSource;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setBusinessServiceFacade(BusinessServiceFacade businessServiceFacade) {
        this.businessServiceFacade = businessServiceFacade;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ModelAndView findEstimateList(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<EstimateBean> estimateList = businessServiceFacade.findEstimateList();
            modelMap.put("emptyEstimate", new EstimateBean());
            modelMap.put("emptyEstimateItem", new EstimateItemBean());
            modelMap.put("estimateList", estimateList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "견적서조회성공");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView batchEstimate(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        try {

            String list = request.getParameter("batchList");
            ObjectMapper mapper = new ObjectMapper();
            List<EstimateBean> estimateList = mapper.readValue(list, new TypeReference<List<EstimateBean>>() {
            });
            businessServiceFacade.batchEstimate(estimateList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "견적서등록성공");
        } catch (EstimateNotEditException e) {
            String errorMsg = messageSource.getMessage("EstimateNotEditException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findEstimateReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            System.out.println("startDate : " + startDate + ", endDate : " + endDate);

            List<EstimateItemBean> estimateItemList = businessServiceFacade.findEstimateReviewList(startDate, endDate);
            modelMap.put("estimateItemList", estimateItemList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "견적서조회");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}
