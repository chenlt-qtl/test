
/**
* @(#)ArticleUserRelController.java 2017年6月5日
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

package com.ikoko.top.word.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleUserRelService;

/**
 * 描述:
 * @author chenlt
 */
@Controller
@RequestMapping("${adminPath}/articleUserRel")
public class ArticleUserRelController extends BaseController {
    
    @Autowired
    private ArticleUserRelService articleUserRelService;
    
    @RequestMapping(value = "/delete")
    public String deleteMy(Article article,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        if(article != null){
            articleUserRelService.deleteByArticle(article.getId(),UserUtils.getLoginUser().getId());
            addMessage(redirectAttributes, "删除成功");
        }
        return "redirect:" + adminPath + "/article/my/articleList?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
