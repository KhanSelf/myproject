package org.go.spring.angel.logistics.item.dao;

import org.go.spring.angel.base.to.CodeDetailBean;
import org.go.spring.angel.logistics.item.to.BomBean;

import java.util.List;

public interface BomDAO {
    public List<CodeDetailBean> selectBomMenu();

    public List<BomBean> selectBomList(String itemNo);

    public List<BomBean> selectBomTurnList(String itemNo);
}
