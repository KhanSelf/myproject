package org.go.spring.angel.logistics.business.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.logistics.business.exception.ShippingAmountOverException;
import org.go.spring.angel.logistics.business.exception.ShippingNotEditException;
import org.go.spring.angel.logistics.business.service.BusinessServiceFacade;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
import org.go.spring.angel.logistics.business.to.ShippingBean;
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

public class ShippingController extends MultiActionController {
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

    public ModelAndView findShippingList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String customer = request.getParameter("customer");

            List<ContractItemBean> contractItemList = businessServiceFacade.findShippingList(customer);
            modelMap.put("contractItemList", contractItemList);
            modelMap.put("shippingBean", new ShippingBean());
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "납품내역조회");
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView registerShipping(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {

            String list = request.getParameter("registerList");
            ObjectMapper mapper = new ObjectMapper();
            List<ShippingBean> shippingList = mapper.readValue(list, new TypeReference<List<ShippingBean>>() {
            });
            businessServiceFacade.registerShipping(shippingList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "납품등록");
        } catch (ShippingNotEditException e) {
            String errorMsg = messageSource.getMessage("ShippingNotEditException", new Object[] { e.getMessage() },
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (ShippingAmountOverException e) {
            String errorMsg = messageSource.getMessage("ShippingNotEditException", new Object[] { e.getMessage() },
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            modelMap.put("errorCode", -3);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findShippingReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            System.out.println("startDate : " + startDate + ", endDate : " + endDate);
            List<ShippingBean> shippingList = businessServiceFacade.findShippingReviewList(startDate, endDate);
            modelMap.put("shippingList", shippingList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "납품내역조회");
        } catch (DataAccessException e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}