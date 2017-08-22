package org.go.spring.angel.base.dao;

import org.go.spring.angel.base.to.PostBean;

import java.util.List;

public interface PostDAO {

    public List<PostBean> searchPostList(String dong);

    public List<PostBean> searchSidoList();

    public List<PostBean> searchSigunguList(String sido);

    List<PostBean> searchRoadList(String sido, String sigungu, String roadname);

   /* public List<PostBean> searchRoadList(String sido, String sigungu,  String roadname);*/
}
