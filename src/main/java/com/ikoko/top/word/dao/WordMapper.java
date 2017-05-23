package com.ikoko.top.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.word.entity.Word;
import com.ikoko.top.word.entity.WordExample;

public interface WordMapper {
    int countByExample(WordExample example);
    int deleteByExample(WordExample example);
    int deleteByPrimaryKey(Long id);
    int insert(Word record);
    int insertSelective(Word record);
    List<Word> selectByExampleWithBLOBs(WordExample example);
    List<Word> selectByExample(WordExample example);
    List<Word> selectByArticle(String articleId);
    Word selectByPrimaryKey(Long id);
    int updateByExampleSelective(@Param("record") Word record, @Param("example") WordExample example);
    int updateByExampleWithBLOBs(@Param("record") Word record, @Param("example") WordExample example);
    int updateByExample(@Param("record") Word record, @Param("example") WordExample example);
    int updateByPrimaryKeySelective(Word record);
    int updateByPrimaryKeyWithBLOBs(Word record);
    int updateByPrimaryKey(Word record);
}