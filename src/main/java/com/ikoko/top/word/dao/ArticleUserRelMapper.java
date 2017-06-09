package com.ikoko.top.word.dao;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.ArticleUserRel;

public interface ArticleUserRelMapper extends ICrudDao<ArticleUserRel> {

    public void deleteByArticle(@Param("articleId")String articleId,@Param("userId")String userId);
    
}