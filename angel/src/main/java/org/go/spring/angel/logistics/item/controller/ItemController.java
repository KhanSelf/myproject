package org.go.spring.angel.logistics.item.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.go.spring.angel.logistics.item.exception.ItemOverlapException;
import org.go.spring.angel.logistics.item.service.ItemServiceFacade;
import org.go.spring.angel.logistics.item.to.ItemBean;
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

public class ItemController extends MultiActionController {

    private ItemServiceFacade itemServiceFacade;
    private MessageSource messageSource;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setItemServiceFacade(ItemServiceFacade itemServiceFacade) {
        this.itemServiceFacade = itemServiceFacade;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // item리스트
    public ModelAndView findItemList(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<ItemBean> itemList = itemServiceFacade.findItemList();
            modelMap.put("emptyItem", new ItemBean());
            modelMap.put("itemList", itemList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "품목 조회 성공");
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

    // item일괄처리
    public ModelAndView batchItem(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {

            String list = request.getParameter("batchList");
            ObjectMapper mapper = new ObjectMapper();
            List<ItemBean> itemList = mapper.readValue(list, new TypeReference<List<ItemBean>>() {
            });
            itemServiceFacade.batchItem(itemList);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "처리 성공");
        } catch (ItemOverlapException iteme) {
            String errorMsg = messageSource.getMessage("ItemOverlapException", new Object[] { iteme.getMessage() },
                    (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
            modelMap.put("errorCode", -1);
            modelMap.put("errorMsg", errorMsg);
            iteme.printStackTrace();
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            modelMap.put("errorCode", -2);
            modelMap.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }

}
