package org.go.spring.angel.base.controller;

import org.go.spring.angel.base.exception.CodeNotEditException;
import org.go.spring.angel.base.exception.CodeOverlapException;
import org.go.spring.angel.base.service.BaseServiceFacade;
import org.go.spring.angel.base.to.CodeBean;
import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.common.to.ListForm;
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

public class CodeController extends MultiActionController {
    // -------------------dependency(bean-ref)-------------------//


    private BaseServiceFacade baseServiceFacade;
    private MessageSource messageSource;
    private ModelMap modelMap = new ModelMap();
    private ModelAndView modelAndView = null;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }
    // -------------------dependency(bean-ref)-------------------//


    /* findCodeList */
    public ModelAndView findCodeList(HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            int pagenum = 1;
            int rowsize = 5;

            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }

            int codeCount = baseServiceFacade.findCodeCount();
            ListForm page = new ListForm(rowsize, pagenum, codeCount);
            List<CodeBean> codeList = baseServiceFacade.findCodeList(page.getStartrow(), page.getEndrow());
            page.setList(codeList);

            modelMap.put("codeList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "코드조회");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* modifyCode */
    /* LOCALE_SESSION_ATTRIBUTE_NAME - CodeNotEditException */
    public ModelAndView modifyCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            CodeBean codeBean = new CodeBean();
            codeBean.setCodeNo(request.getParameter("codeNo"));
            codeBean.setCodeName(request.getParameter("codeName"));
            String modifyCodeNo = request.getParameter("id");
            baseServiceFacade.modifyCode(modifyCodeNo, codeBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "코드수정");
        } catch (CodeNotEditException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("CodeNotEditException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* registerCode */
    /* messageSource - CodeOverlapException */
    public ModelAndView registerCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            CodeBean codeBean = new CodeBean();
            codeBean.setCodeNo(request.getParameter("codeNo"));
            codeBean.setCodeName(request.getParameter("codeName"));
            baseServiceFacade.registerCode(codeBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "코드등록");
        } catch (CodeOverlapException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("CodeOverlapException", new Object[]{e.getMessage()}, null);
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* removeCode */
    public ModelAndView removeCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String removeCodeNo = request.getParameter("id");
            baseServiceFacade.removeCode(removeCodeNo);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "코드삭제");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* findCodeDetailList */
    public ModelAndView findCodeDetailList(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pagenum = 1;
            int rowsize = 5;

            if (request.getParameter("page") != null) {
                pagenum = Integer.parseInt(request.getParameter("page"));
                rowsize = Integer.parseInt(request.getParameter("rows"));
            }

            int codeDetailCount = baseServiceFacade.findCodeDetailCount(request.getParameter("codeNo"));
            ListForm page = new ListForm(rowsize, pagenum, codeDetailCount);
            List<CodeDetailBean> codeDetailList = baseServiceFacade.findCodeDetailList(page.getStartrow(),
                    page.getEndrow(), request.getParameter("codeNo"));
            page.setList(codeDetailList);
            modelMap.put("codeDetailList", page);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "상세코드조회");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* registerCodeDetail */
    public ModelAndView registerCodeDetail(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            CodeDetailBean codeDetailBean = new CodeDetailBean();
            codeDetailBean.setCodeNo(request.getParameter("codeNo"));
            codeDetailBean.setCodeDetailNo(request.getParameter("codeDetailNo"));
            codeDetailBean.setCodeDetailName(request.getParameter("codeDetailName"));
            baseServiceFacade.registerCodeDetail(codeDetailBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "상세코드등록");
        } catch (CodeOverlapException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("CodeOverlapException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* modifyCodeDetail */
    /* LOCALE_SESSION_ATTRIBUTE_NAME - CodeOverlapException */
    public ModelAndView modifyCodeDetail(HttpServletRequest request,
                                         HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            CodeDetailBean codeDetailBean = new CodeDetailBean();
            codeDetailBean.setCodeDetailNo(request.getParameter("codeDetailNo"));
            codeDetailBean.setCodeDetailName(request.getParameter("codeDetailName"));
            String modifyCodeDetailNo = request.getParameter("id");
            baseServiceFacade.modifyCodeDetail(modifyCodeDetailNo, codeDetailBean);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "상세코드수정");
        } catch (CodeOverlapException e) {
            // TODO: handle exception
            String errorMsg = messageSource.getMessage("CodeOverlapException", new Object[]{e.getMessage()},
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }


    /* removeCodeDetail */
    public ModelAndView removeCodeDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String removeCodeDetailNo = request.getParameter("id");
            baseServiceFacade.removeCodeDetail(removeCodeDetailNo);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "상세코드삭제");
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
