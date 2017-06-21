package com.ikoko.top.word.dao;

import java.util.List;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Acceptation;

public interface AcceptationMapper extends ICrudDao<Acceptation> {
    int deleteByPrimaryKey(Long id);
    List<Acceptation> selectByWordId(String wordId);
}