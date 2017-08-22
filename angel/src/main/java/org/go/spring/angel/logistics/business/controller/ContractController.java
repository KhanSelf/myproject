package org.go.spring.angel.logistics.business.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.logistics.business.exception.ContractNotEditException;
import org.go.spring.angel.logistics.business.service.BusinessServiceFacade;
import org.go.spring.angel.logistics.business.to.ContractBean;
import org.go.spring.angel.logistics.business.to.ContractItemBean;
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

public class ContractController extends MultiActionController {
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

    public ModelAndView findContractList(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<ContractBean> contractList = businessServiceFacade.findContractList();
            modelMap.put("emptyContract", new ContractBean());
            modelMap.put("emptyContractItem", new ContractItemBean());
            modelMap.put("contractList", contractList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "수주조회");
        } catch (DataAccessException e) {
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView batchContract(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {

            String list = request.getParameter("batchList");
            System.out.println(list);
            ObjectMapper mapper = new ObjectMapper();
            List<ContractBean> contractList = mapper.readValue(list, new TypeReference<List<ContractBean>>() {
            });
            businessServiceFacade.batchContract(contractList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "일괄처리");
        } catch (ContractNotEditException cne) {
            String errorMsg = messageSource.getMessage("ContractNotEditException", new Object[]{cne.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            cne.printStackTrace();
        } catch (Exception e) {
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    public ModelAndView findContractReviewList(HttpServletRequest request, HttpServletResponse response) {

        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            System.out.println("startDate : " + startDate + ", endDate : " + endDate);

            List<ContractItemBean> contractItemList = businessServiceFacade.findContractReviewList(startDate, endDate);
            modelMap.put("contractItemList", contractItemList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "수주리스트조회");
        } catch (DataAccessException e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}