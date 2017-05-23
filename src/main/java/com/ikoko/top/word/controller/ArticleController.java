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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.util.RequestUtil;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController{
	@Autowired
    private ArticleService articleService;
    
    @RequestMapping("/add")
    public String enterAdd(HttpServletRequest request, HttpServletResponse response) {
    	return "addArticle";
    }
    
    @RequestMapping("/articleList")
    public ModelAndView showArticle(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView modelAndView = new ModelAndView("articleList");
    	return modelAndView;
    }
    
    @RequestMapping("/getArticlesPage")
    public void getArticlesPage() throws IOException{
    	List list = articleService.getArticleByPage(RequestUtil.getParameterMap(request));
    	Map map = new HashMap<>();
    	map.put("rows", list);
        map.put("results", list.size());
    	writeResponse(map);
    }
    
    @RequestMapping("/delete")
    public void delete(){
        Object id = request.getParameter("id");
        if(id != null){
            articleService.delete(String.valueOf(id));
            writeSuccess();
        }
    }
    
    @RequestMapping("/getMp3")
    public void getMp3() {
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.getById(String.valueOf(id));
            if(article.getMp3()!=null){
                writeMp3(article.getMp3());
            }
        }
    }
    
    @RequestMapping("/enterLevel")
    public void enterLevel() {//进入关卡页面
        Object id = request.getParameter("id");
        if(id != null){
            Article article = articleService.getByIdWithoutMp3(String.valueOf(id));
            int wordNum = article.getWordNum();
            int level = wordNum/10+1;
            Map map = new HashMap<>();
            map.put("wordNum", wordNum);
            map.put("level", level);
            writeResponse(map);
        }
    }
}