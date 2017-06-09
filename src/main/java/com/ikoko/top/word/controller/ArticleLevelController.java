
/**
* @(#)ArticleLevelController.java 2017年6月5日
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleLevelService;
import com.ikoko.top.word.service.ArticleService;

/**
 * 描述:
 * @author chenlt
 */

@Controller
@RequestMapping("${adminPath}/articleLevel")
public class ArticleLevelController extends BaseController {
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private ArticleLevelService articleLevelService;
    
    @RequestMapping(value = "/level")
    public String enterLevel(HttpServletRequest request, HttpServletResponse response,Model model) {
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.getByIdWithoutMp3(String.valueOf(id));
            int wordNum = article.getWordNum();
            int totalLevel = wordNum/10;
            if(wordNum%10!=0){
                totalLevel+=1;
            }
            Map map = new HashMap<>();
            model.addAttribute("article", article);
            model.addAttribute("wordNum", wordNum);
            model.addAttribute("totalLevel", totalLevel);
            
            model.addAttribute("level", articleLevelService.getLevel(String.valueOf(id), UserUtils.getLoginUser().getId()));
        }
        return "word/articleLevel/level";
    }
    
    @RequestMapping(value = "/levelList")
    public String enterLevelList(HttpServletRequest request, HttpServletResponse response,Model model) {
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.getByIdWithoutMp3(String.valueOf(id));
            int wordNum = article.getWordNum();
            int totalLevel = wordNum/10;
            if(wordNum%10!=0){
                totalLevel+=1;
            }
            model.addAttribute("levels", articleLevelService.selectByArticle(String.valueOf(id), UserUtils.getLoginUser().getId()));
            model.addAttribute("totalLevel", totalLevel);
        }
        return "word/articleLevel/levelList";
    }
}
