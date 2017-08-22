package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.CodeDetailBean;

import java.util.List;

public interface CodeDetailDAO {

	public List<CodeDetailBean> selectCodeDetailList(int startRow, int endRow, String codeNo);

	public int selectCodeDetailCount(String codeNo);

	public String selectCodeDetail(String codeDetailNo);

	public void insertCodeDetail(CodeDetailBean codeDetailBean);

	public void updateCodeDetail(String modifyCodeDetailNo, CodeDetailBean codeDetailBean);

	public void deleteCodeDetail(String removeCodeDetailNo);

	public List<CodeDetailBean> selectCodeDetailList(String codeNo);

}
