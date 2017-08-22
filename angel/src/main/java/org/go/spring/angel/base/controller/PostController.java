package org.go.spring.angel.base.controller;

import org.go.spring.angel.base.service.BaseServiceFacade;
import org.go.spring.angel.base.to.PostBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PostController extends MultiActionController {

    // -------------------dependency(bean-ref)-------------------//

    private BaseServiceFacade baseServiceFacade;
    private ModelMap modelMap = new ModelMap();
    private ModelAndView modelAndView = null;

    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }

    // -------------------dependency(bean-ref)-------------------//

	/* searchJibun */

    public ModelAndView searchJibun(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dong = request.getParameter("dong");
            List<PostBean> postList = baseServiceFacade.findPostList(dong);

            modelMap.put("postList", postList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "검색완료");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", "실패실화??");
            e.printStackTrace();
        }
        return modelAndView;
    }

	/* searchSido */

    public ModelAndView searchSido(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PostBean> sidoList = baseServiceFacade.findSidoList();
            modelMap.put("sidoList", sidoList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "검색완료");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", "실패실화??");
            e.printStackTrace();
        }
        return modelAndView;
    }

	/* searchSigungu */

    public ModelAndView searchSigungu(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sido = request.getParameter("sido");
            List<PostBean> sigunguList = baseServiceFacade.findSigunguList(sido);
            modelMap.put("sigunguList", sigunguList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "검색완료");
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", "실패실화??");
            e.printStackTrace();
        }
        return modelAndView;
    }
}