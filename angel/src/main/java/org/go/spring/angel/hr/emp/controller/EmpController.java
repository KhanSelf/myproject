package org.go.spring.angel.hr.emp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.common.to.ListForm;
import org.go.spring.angel.hr.emp.exception.IDNotEditException;
import org.go.spring.angel.hr.emp.service.HrServiceFacade;
import org.go.spring.angel.hr.emp.to.EmpBean;
import net.sf.json.JSONObject;
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

//import net.sf.json.JSONObject;

public class EmpController extends MultiActionController {
    // -------------------dependency(bean-ref)-------------------//

    private HrServiceFacade hrServiceFacade;
    private MessageSource messageSource;
    private ModelMap modelMap = new ModelMap();
    private ModelAndView modelAndView = null;

    public void setHrServiceFacade(HrServiceFacade hrServiceFacade) {
        this.hrServiceFacade = hrServiceFacade;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // -------------------dependency(bean-ref)-------------------//


    /* findEmpList */
    public ModelAndView findEmpList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            int pagenum = 1;
            int rowsize = 5;
            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }

            int empCount = hrServiceFacade.findEmpCount();
            ListForm page = new ListForm(rowsize, pagenum, empCount);
            List<EmpBean> empList = hrServiceFacade.findEmpList(page.getStartrow(), page.getEndrow());
            page.setList(empList);
            modelMap.put("empList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "사원조회");
        } catch (DataAccessException e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }



    /*findEmpDetail*/
    public ModelAndView findEmpDetail(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String s = enu.nextElement();
            System.out.println(s + "," + request.getParameter(s));
        }

        try {

            EmpBean empBean = hrServiceFacade.findEmpDetail(request.getParameter("empNo"));
            modelMap.put("empDetail", empBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "상세정보조회");
        } catch (DataAccessException e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }

        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }



    /*registerEmp*/
    public ModelAndView registerEmp(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String s = enu.nextElement();
            System.out.println(s + "," + request.getParameter(s));
        }

        try {
            EmpBean empBean = new EmpBean();
            empBean.setEmpNo(request.getParameter("empNo"));
            empBean.setEmpName(request.getParameter("empName"));
            hrServiceFacade.registerEmp(empBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "사원등록");
        } catch (IDNotEditException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("IDNotEditException", new Object[] { e.getMessage() },
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (DataAccessException e) {
            // TODO: handle exception
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /*modifyEmp*/
    public ModelAndView modifyEmp(HttpServletRequest request, HttpServletResponse response) {

        try {
            String emp = request.getParameter("emp");
            ObjectMapper mapper = new ObjectMapper();
            EmpBean empBean = mapper.readValue(emp, EmpBean.class);
            hrServiceFacade.modifyEmp(empBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "사원수정");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /*removeEmp*/
    public ModelAndView removeEmp(HttpServletRequest request, HttpServletResponse response) {

        try {
            String empNo = request.getParameter("id");
            hrServiceFacade.removeEmp(empNo);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "사원삭제");
        } catch (DataAccessException e) {
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