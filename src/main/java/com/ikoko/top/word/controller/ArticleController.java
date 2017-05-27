/**
* @(#)AR.java 2016年11月8日
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
package com.ikoko.top.word.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.common.Page;
import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.util.RequestUtil;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Controller
@RequestMapping("${adminPath}/article")
public class ArticleController extends BaseController {
	@Autowired
    private ArticleService articleService;
    
    @RequestMapping(value = "/add")
    public String enterAdd(HttpServletRequest request, HttpServletResponse response) {
    	return "word/article/editArticle";
    }
    
    @RequestMapping(value = "/articleList")
    public String showArticle(Article article,Model model, Page<Article> page){
        article.setUser(UserUtils.getLoginUser());
        page.setEntity(article);
        model.addAttribute("page", page.setList(articleService.findPage(page)));
    	return "word/article/listArticle";
    }
    
    @RequestMapping(value = "/getArticlesPage")
    public void getArticlesPage(HttpServletResponse response, HttpServletRequest request) throws IOException{
    	List list = articleService.getArticleByPage(RequestUtil.getParameterMap(request));
    	Map map = new HashMap<>();
    	map.put("rows", list);
        map.put("results", list.size());
    	renderString(response, map);
    }
    
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response, HttpServletRequest request){
        Object id = request.getParameter("id");
        if(id != null){
            articleService.delete(String.valueOf(id));
            writeSuccess(response);
        }
    }
    
    @RequestMapping(value = "/getMp3")
    public void getMp3(HttpServletResponse response, HttpServletRequest request) {
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.get(String.valueOf(id));
            if(article.getMp3()!=null){
                writeMp3(response,article.getMp3());
            }
        }
    }
    
    @RequestMapping(value = "/enterLevel")
    public void enterLevel(HttpServletResponse response, HttpServletRequest request) {//进入关卡页面
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.getByIdWithoutMp3(String.valueOf(id));
            int wordNum = article.getWordNum();
            int level = wordNum/10+1;
            Map map = new HashMap<>();
            map.put("wordNum", wordNum);
            map.put("level", level);
            renderString(response, map);
        }
    }
}
