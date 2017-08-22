package org.go.spring.angel.logistics.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.logistics.business.exception.CustomerNotEditException;
import org.go.spring.angel.logistics.business.service.BusinessServiceFacade;
import org.go.spring.angel.logistics.business.to.CustomerBean;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class CustomerController extends MultiActionController {
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

    public ModelAndView findCustomerList(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<CustomerBean> customerList = businessServiceFacade.findCustomerList();
            modelMap.put("emptyCustomer", new CustomerBean());
            modelMap.put("customerList", customerList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "거래처조회성공");
        } catch (Exception e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView registerCustomer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String s = enu.nextElement();
            System.out.println(s + "," + request.getParameter(s));
        }

        try {

            String customer = request.getParameter("customer");
            ObjectMapper mapper = new ObjectMapper();
            CustomerBean customerBean = mapper.readValue(customer, CustomerBean.class);
            businessServiceFacade.registerCustomer(customerBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "거래처등록성공");
        } catch (CustomerNotEditException e) {
            String errorMsg = messageSource.getMessage("CustomerNotEditException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView removeCustomer(HttpServletRequest request, HttpServletResponse response) {

        try {

            String customer = request.getParameter("customer");
            businessServiceFacade.removeCustomer(customer);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "거래처삭제성공");
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}