package com.ikoko.top.word.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.SentenceWordRel;

public interface SentenceWordRelMapper extends ICrudDao<SentenceWordRel> {
    SentenceWordRel selectByPrimaryKey(Long id);
}