package com.ikoko.top.word.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Word;

public interface WordMapper extends ICrudDao<Word> {
    int updateByPrimaryKeyWithBLOBs(Word record);
    
    List<Word> selectByParam(Map map);
    
    List<Word> selectByArticleId(Map map);
    
    List<Word> selectWords(@Param("wordIds") String[] wordIds);
    
}