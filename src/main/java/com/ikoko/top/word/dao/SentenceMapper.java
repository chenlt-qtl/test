package com.ikoko.top.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Sentence;

public interface SentenceMapper extends ICrudDao<Sentence> {

    int deleteByPrimaryKey(Long id);
    Sentence selectByPrimaryKey(Long id);
    
    List<Sentence> selectByArticle(Long articleId);
    List<Sentence> selectByWord(@Param("wordId") String wordId);
}