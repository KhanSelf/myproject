package org.go.spring.angel.base.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class FileController extends MultiActionController {
    private ModelAndView modelAndView;
    private ModelMap modelMap = new ModelMap();

    public ModelAndView uploadfile(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        String uploadPath = "C:/Users/khans/Desktop/angelinus/img";
        String empId = request.getParameter("empId");
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = null;
        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile((iterator.next()));
            if (!multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                File file = new File(uploadPath + empId + fileExtension);
                try {
                    multipartFile.transferTo(file);
                    modelMap.put("url", "/img/" + empId + fileExtension);
                    modelMap.put("errorCode", 1);
                    modelMap.put("errorMsg", "사진이 저장되엇습니다");
                    logger.info("/data/" + empId + fileExtension);
                } catch (IOException e) {
                    modelMap.put("errorCode", -1);
                    modelMap.put("errorMsg", "사진이 저장 되지 않앗습니다");
                }
            }
        }
        modelAndView = new ModelAndView("jsonView", modelMap);
        return modelAndView;
    }
}
