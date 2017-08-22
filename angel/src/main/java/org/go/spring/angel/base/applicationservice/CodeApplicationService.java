package org.go.spring.angel.base.applicationservice;

import org.go.spring.angel.base.exception.CodeNotEditException;
import org.go.spring.angel.base.exception.CodeOverlapException;
import org.go.spring.angel.base.to.CodeBean;
import org.go.spring.angel.base.to.CodeDetailBean;

import java.util.List;

public interface CodeApplicationService {

	public List<CodeBean> findCodeList(int startRow, int endRow);

	public int findCodeCount();

	public void registerCode(CodeBean codeBean) throws CodeOverlapException;

	public void modifyCode(String modifyCodeNo, CodeBean codeBean) throws CodeNotEditException;

	public void removeCode(String removeCodeNo);

	public List<CodeDetailBean> findCodeDetailList(int startRow, int endRow, String codeNo);

	public int findCodeDetailCount(String codeNo);

	public void registerCodeDetail(CodeDetailBean codeDetailBean) throws CodeOverlapException;

	public void modifyCodeDetail(String modifyCodeDetailNo, CodeDetailBean codeDetailBean) throws CodeOverlapException;

	public void removeCodeDetail(String removeCodeDetailNo);

}