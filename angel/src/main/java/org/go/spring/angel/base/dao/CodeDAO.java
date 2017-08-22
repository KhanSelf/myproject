package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.CodeBean;

import java.util.List;

public interface CodeDAO {

	public List<CodeBean> selectCodeList(int startRow, int endRow);

	public int selectCodeCount();

	public CodeBean selectCode(String codeNo);

	public void insertCode(CodeBean codeBean);

	public void updateCode(String modifyCodeNo, CodeBean codeBean);

	public void deleteCode(String removeCodeNo);

}
