
/**
* @(#)ArticleLevelMapper.java 2017年6月5日
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

package com.ikoko.top.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.word.entity.ArticleLevel;

/**
 * 描述:
 * @author chenlt
 */
@MyBatisDao
public interface ArticleLevelMapper extends ICrudDao<ArticleLevel> {

    public List<ArticleLevel> selectByArticle(@Param("articleId")String articleId,@Param("userId")String userId);
}
