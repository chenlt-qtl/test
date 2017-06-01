package com.ikoko.top.word.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.Acceptation;

public interface AcceptationMapper extends ICrudDao<Acceptation> {
    int deleteByPrimaryKey(Long id);
    Acceptation selectByPrimaryKey(Long id);
}