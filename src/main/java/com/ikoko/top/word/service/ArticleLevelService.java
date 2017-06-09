
/**
* @(#)ArticleLevelService.java 2017年6月5日
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

import com.ikoko.top.word.dao.ArticleLevelMapper;
import com.ikoko.top.word.entity.ArticleLevel;

/**
 * 描述:
 * @author chenlt
 */
@Service
public class ArticleLevelService {
    @Autowired
    private ArticleLevelMapper articleLevelMapper;
    
    public int getLevel(String articleId,String userId){
        List<ArticleLevel> articleLevel = articleLevelMapper.selectByArticle(articleId, userId);
        if(articleLevel.size()==0){
            return 1;
        }else{
            return articleLevel.size();
        }
    }
    
    public List<ArticleLevel> selectByArticle(String articleId,String userId){
        List<ArticleLevel> articleLevel = articleLevelMapper.selectByArticle(articleId, userId);
        return articleLevel;
    }
}
