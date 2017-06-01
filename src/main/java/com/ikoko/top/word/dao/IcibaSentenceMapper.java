package com.ikoko.top.word.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.IcibaSentence;

public interface IcibaSentenceMapper extends ICrudDao<IcibaSentence> {
    IcibaSentence selectByPrimaryKey(Long id);
}