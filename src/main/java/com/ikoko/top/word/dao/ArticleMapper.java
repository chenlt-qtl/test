package com.ikoko.top.word.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.entity.ArticleExample;

@MyBatisDao
public interface ArticleMapper extends ICrudDao<Article> {
    int countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);
    
    List<Article> selectByExample(ArticleExample example);
    
    List<Article> selectByPage(Map<String, String> parameters);
    
    List<Article> selectAll();

    Article selectByPrimaryKey(Long id);
    
    Article selectByIdWithoutMp3(Long id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);
    int updateByPrimaryKey(Article record);
}