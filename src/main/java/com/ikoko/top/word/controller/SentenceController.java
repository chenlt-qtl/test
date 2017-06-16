/**
* @(#)SentenceController.java 2016年12月1日
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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.word.dto.SentenceList;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.service.SentenceService;
import com.ikoko.top.word.service.WordService;
import com.ikoko.top.word.util.RequestUtil;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Controller
@RequestMapping("${adminPath}/sentence")
public class SentenceController extends BaseController{
	@Autowired
    private ArticleService articleService;
	
    @Autowired
    private WordService wordService;
	
	@Autowired
    private SentenceService sentenceService;
	
    @RequestMapping(value = "/save")
    public String save(SentenceList sentenceList,String title,@RequestParam("mp3") MultipartFile file, HttpServletResponse response, RedirectAttributes redirectAttributes) throws ClientProtocolException, IOException {
    	articleService.saveNewWord(sentenceList,title,file);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + adminPath + "/article/articleList";
    }
    
    @RequestMapping(value = "/getContent")
    public String getContent(HttpServletResponse response, HttpServletRequest request,Model model) throws IOException{
        Map map = RequestUtil.getParameterMap(request);
        List<Sentence> list = sentenceService.getSentenceByArticle(Long.parseLong((String)map.get("id")));
        String content = "";
        for(Sentence sentence:list){
            content += sentence.getContent()+". ";
        }
        model.addAttribute("articleId", map.get("id")); 
        model.addAttribute("title", map.get("title"));
        model.addAttribute("content", content);
        list = wordService.getByArticle((String)map.get("id"));
        model.addAttribute("words", list);
        model.addAttribute("wordNum", list.size());
        return "word/sentence/content";
    }
    
    @RequestMapping(value = "/analy")
    public String analy(Article article,HttpServletRequest request,Model model) throws IOException{
        List words = sentenceService.analyArticle(article.getContent());
        model.addAttribute("words", words);
        model.addAttribute("article", article);
        return "word/article/addWord";
    }
}
