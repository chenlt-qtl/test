package com.ikoko.top.word.dao;

import java.util.List;
import java.util.Map;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.word.entity.IcibaSentence;

public interface IcibaSentenceMapper extends ICrudDao<IcibaSentence> {
    IcibaSentence get(String id);
    
    List<IcibaSentence> select(Map map);
}