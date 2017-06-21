
/**
* @(#)AcceptationService.java 2017年5月19日
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoko.top.word.dao.AcceptationMapper;
import com.ikoko.top.word.entity.Acceptation;

/**
 * 描述:
 * @author chenlt
 */
@Service
public class AcceptationService {
    @Autowired
    private AcceptationMapper acceptationMapper;
    
    public List<Acceptation> getByWordId(String wordId){
        return acceptationMapper.selectByWordId(wordId);
    }
}
