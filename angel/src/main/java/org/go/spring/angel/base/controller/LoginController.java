package org.go.spring.angel.base.controller;

import org.go.spring.angel.base.exception.IdNotFoundException;
import org.go.spring.angel.base.exception.PwMissmatchException;
import org.go.spring.angel.base.service.BaseServiceFacade;
import org.go.spring.angel.common.exception.DataAccessException;
import org.go.spring.angel.hr.emp.to.EmpBean;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;


public class LoginController extends MultiActionController {

    // -------------------dependency(bean-ref)-------------------//

    private BaseServiceFacade baseServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();
    private MessageSource messageSource;

    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // -------------------dependency(bean-ref)-------------------//

	/* login */
    /*
     * LOCALE_SESSION_ATTRIBUTE_NAME - IdNotFoundException, PwMissmatchException
	 */

    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String empNo = request.getParameter("empNo");
        String password = request.getParameter("password");
        try {
            EmpBean empBean = baseServiceFacade.login(empNo, password);
            if (empBean != null) {
                session.setAttribute("empNo", empBean.getEmpNo());
                session.setAttribute("empName", empBean.getEmpName());
                modelMap.put("errorCode", 1);
                modelMap.put("errorMsg", "로그인");
                modelAndView = new ModelAndView("redirect:welcome.html", modelMap);
            }
        } catch (IdNotFoundException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("IdNotFoundException", new Object[]{empNo, e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            modelAndView = new ModelAndView("loginform", modelMap);
        } catch (PwMissmatchException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("PwMissmatchException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", errorMsg);
            modelAndView = new ModelAndView("loginform", modelMap);
        } catch (DataAccessException e) {
            // TODO: handle exception
            modelMap.put("errorCode", -3);
            modelMap.put("errorMsg", "로그인실패");
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -4);
            modelMap.put("errorMsg", "로그인실패");
            e.printStackTrace();
        }
        return modelAndView;
    }


    /* logout */
    /*
     * LOCALE_SESSION_ATTRIBUTE_NAME - IdNotFoundException, PwMissmatchException	 */
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response) {
        String viewname = null;
        try {

            request.getSession().invalidate();
            viewname = "redirect:/angelinus/welcome.html";
            modelAndView = new ModelAndView(viewname, modelMap);
        } catch (Exception e) {
            viewname = "error";
            modelAndView = new ModelAndView(viewname, modelMap);
        }
        System.out.println(viewname);

        return modelAndView;
    }
}








