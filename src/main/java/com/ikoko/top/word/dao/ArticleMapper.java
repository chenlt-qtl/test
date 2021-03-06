package com.ikoko.top.word.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.Page;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.word.entity.Article;

@MyBatisDao
public interface ArticleMapper extends ICrudDao<Article> {
    
    List<Article> selectByPage(Map<String, String> parameters);
    
    List<Article> selectAll();

    Article selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(Article record);
    
    public int countOther(@Param("page") Page<Article> page);
    
    public List<Article> findOtherPage(@Param("page") Page<Article> page);
}