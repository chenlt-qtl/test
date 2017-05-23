
/**
* @(#)WordService.java 2017年4月12日
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

import com.ikoko.top.word.dao.WordMapper;
import com.ikoko.top.word.entity.Word;

/**
 * 描述:
 * @author chenlt
 */
@Service
public class WordService {
    
    @Autowired
    private WordMapper wordMapper;
    
    @Autowired
    private AcceptationService acceptationService;
    
    public List getByArticle(String articleId){
        return wordMapper.selectByArticle(articleId);
    }
    
    public Word getMp3(String id){
        return wordMapper.selectByPrimaryKey(Long.parseLong(id));
    }
    
    public Word getById(String id){
        Word word = wordMapper.selectByPrimaryKey(Long.parseLong(id));
        word.setAcceptations(acceptationService.getByWordId(word.getId()));
        return word;
    }
}
