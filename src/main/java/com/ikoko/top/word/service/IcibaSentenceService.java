
/**
* @(#)IcibaSentenceService.java 2017年6月2日
*
* Copyright 2000-2017 by ChinanetCenter Corporation.
*
* All rights reserved.
*
* This software is the confidential and proprietary information of
* ChinanetCenter Corporation ("Confidential Information"). You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with ChinanetCenter.
*
*/

package com.ikoko.top.word.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.word.dao.IcibaSentenceMapper;
import com.ikoko.top.word.entity.IcibaSentence;

/**
 * 描述:
 * @author chenlt
 */

@Service
@Transactional(readOnly = true)
public class IcibaSentenceService  extends CrudService<IcibaSentenceMapper, IcibaSentence> {
    
    public List<IcibaSentence> selectByWordId(String wordId){
        Map map = new HashMap();
        map.put("wordId", wordId);
        return dao.select(map);
    }
    
    public List<IcibaSentence> selectByWordIds(String[] wordIds){
        Map map = new HashMap();
        map.put("wordIds", wordIds);
        return dao.select(map);
    }
}
