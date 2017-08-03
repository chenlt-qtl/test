package com.ikoko.top.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Acceptation;

public interface AcceptationMapper extends ICrudDao<Acceptation> {
    int deleteByPrimaryKey(Long id);
    List<Acceptation> selectByWordId(@Param("wordId") String wordId);
    List<Acceptation> selectByWordIds(@Param("wordIds") String[] wordIds);
}