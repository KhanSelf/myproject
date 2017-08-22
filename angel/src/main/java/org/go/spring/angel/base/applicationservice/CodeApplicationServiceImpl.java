package org.go.spring.angel.base.applicationservice;

import org.go.spring.angel.base.dao.CodeDAO;
import org.go.spring.angel.base.dao.CodeDetailDAO;
import org.go.spring.angel.base.exception.CodeNotEditException;
import org.go.spring.angel.base.exception.CodeOverlapException;
import org.go.spring.angel.base.to.CodeBean;
import org.go.spring.angel.base.to.CodeDetailBean;

import java.util.List;

public class CodeApplicationServiceImpl implements CodeApplicationService {

    // -------------------dependency(bean-ref)-------------------//
    private CodeDAO codeDAO;
    private CodeDetailDAO codeDetailDAO;

    public void setCodeDAO(CodeDAO codeDAO) {
        this.codeDAO = codeDAO;
    }

    public void setCodeDetailDAO(CodeDetailDAO codeDetailDAO) {
        this.codeDetailDAO = codeDetailDAO;
    }

    // -------------------dependency(bean-ref)-------------------//


    /* findCodeList */
    public List<CodeBean> findCodeList(int startRow, int endRow) {
        // TODO Auto-generated method stub
        List<CodeBean> codeList = codeDAO.selectCodeList(startRow, endRow);
        return codeList;
    }


    /* findCodeCount */
    public int findCodeCount() {
        // TODO Auto-generated method stub
        int codeCount = codeDAO.selectCodeCount();
        return codeCount;
    }


    /* registerCode */
    public void registerCode(CodeBean codeBean) throws CodeOverlapException {
        // TODO Auto-generated method stub
        if (codeDAO.selectCode(codeBean.getCodeNo()) != null) {
            throw new CodeOverlapException("오류:코드중복");
        } else {
            codeDAO.insertCode(codeBean);
        }
    }


    /* modifyCode */
    public void modifyCode(String modifyCodeNo, CodeBean codeBean) throws CodeNotEditException {
        // TODO Auto-generated method stub
        if (modifyCodeNo.equals(codeBean.getCodeNo())) {
            codeDAO.updateCode(modifyCodeNo, codeBean);
        } else {
            throw new CodeNotEditException("오류:코드수정");
        }
    }


    /* removeCode */
    public void removeCode(String removeCodeNo) {
        // TODO Auto-generated method stub
        codeDAO.deleteCode(removeCodeNo);
    }


    /* findCodeDetailList */
    public List<CodeDetailBean> findCodeDetailList(int startRow, int endRow, String codeNo) {
        // TODO Auto-generated method stub
        List<CodeDetailBean> codeDatailList = codeDetailDAO.selectCodeDetailList(startRow, endRow, codeNo);
        return codeDatailList;
    }


    /* findCodeDetailCount */
    public int findCodeDetailCount(String codeNo) {
        // TODO Auto-generated method stub
        int codeDetailCount = codeDetailDAO.selectCodeDetailCount(codeNo);
        return codeDetailCount;
    }


    /* registerCodeDetail */
    public void registerCodeDetail(CodeDetailBean codeDetailBean) throws CodeOverlapException {
        // TODO Auto-generated method stub
        if (codeDetailDAO.selectCodeDetail(codeDetailBean.getCodeDetailNo().toUpperCase()) != null) {
            throw new CodeOverlapException("오류:코드중복");
        } else {
            codeDetailDAO.insertCodeDetail(codeDetailBean);
        }
    }


    /* modifyCodeDetail */
    public void modifyCodeDetail(String modifyCodeDetailNo, CodeDetailBean codeDetailBean) throws CodeOverlapException {
        // TODO Auto-generated method stub
        if (codeDetailDAO.selectCodeDetail(codeDetailBean.getCodeDetailNo().toUpperCase()) != null) {
            throw new CodeOverlapException("오류:코드수정");
        } else {
            codeDetailDAO.updateCodeDetail(modifyCodeDetailNo, codeDetailBean);
        }
    }


    /* removeCodeDetail */
    public void removeCodeDetail(String removeCodeDetailNo) {
        // TODO Auto-generated method stub
        codeDetailDAO.deleteCodeDetail(removeCodeDetailNo);
    }
}
