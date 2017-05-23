/**
* @(#)SentenceService.java 2016年12月1日
* 
* Copyright 2000-2016 by ChinanetCenter Corporation.
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

import com.ikoko.top.word.dao.SentenceMapper;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Service
public class SentenceService {
    
    @Autowired
    private SentenceMapper sentenceMapper;
	
    public List getSentenceByArticle(long id){
        
        return sentenceMapper.selectByArticle(id);
    }
}
