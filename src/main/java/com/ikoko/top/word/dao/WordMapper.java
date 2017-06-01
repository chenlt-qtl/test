package com.ikoko.top.word.dao;

import java.util.List;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Word;

public interface WordMapper extends ICrudDao<Word> {
    Word selectByPrimaryKey(Long id);
    int updateByPrimaryKeyWithBLOBs(Word record);
    
    List<Word> selectByWordName(String wordName);
}