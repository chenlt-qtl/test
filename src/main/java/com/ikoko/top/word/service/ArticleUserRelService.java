
/**
* @(#)ArticleUserRelService.java 2017年6月2日
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.word.dao.ArticleUserRelMapper;
import com.ikoko.top.word.entity.ArticleUserRel;

/**
 * 描述:
 * @author chenlt
 */

@Service
@Transactional(readOnly = true)
public class ArticleUserRelService  extends CrudService<ArticleUserRelMapper, ArticleUserRel> {

    public void addAll(String[] ids,String userId){
        for(String id:ids){
            if(StringUtils.isNotBlank(id)){
                ArticleUserRel entity = new ArticleUserRel();
                entity.setArticleId(Long.parseLong(id));
                entity.setUserId(Long.parseLong(userId));
                entity.setStatus("0");
                dao.insert(entity);
            }
        }
    }
}
