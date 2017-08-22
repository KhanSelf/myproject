package org.go.spring.angel.base.controller;

import org.go.spring.angel.base.service.BaseServiceFacade;
import org.go.spring.angel.logistics.business.to.EstimatePDFBean;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ReportController extends MultiActionController {
    private BaseServiceFacade baseServiceFacade;
    private ModelAndView modelAndView = null;
    private ModelMap modelMap = new ModelMap();

    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }

    public ModelAndView getReport(HttpServletRequest request, HttpServletResponse response) {

        try {
            String estimateNo = request.getParameter("estimateNo");
            String format = request.getParameter("format");
            List<EstimatePDFBean> reportEastimateList = baseServiceFacade.findEstimateReport(estimateNo);

            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportEastimateList);
            modelMap.put("format", format);
            modelMap.put("source", source);

            if (format.equals("pdf")) {
                modelAndView = new ModelAndView("pdfView", modelMap);
            } /*else if (format.equals("xls")) {
                modelAndView = new ModelAndView("xlsView", modelMap);
			}*/
        } catch (Exception error) {
            error.printStackTrace();
        }
        return modelAndView;
    }
}