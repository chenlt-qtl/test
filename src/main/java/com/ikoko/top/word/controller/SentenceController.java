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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.common.utils.JStringUtils;
import com.ikoko.top.common.utils.JUploadUtils;
import com.ikoko.top.word.dto.SentenceListForm;
import com.ikoko.top.word.dto.WordListForm;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.service.SentenceService;

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
    private SentenceService sentenceService;
	
	@ModelAttribute
    public Article get(@RequestParam(required = false) String id) {
	    Article entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = articleService.get(id);
        }
        if (entity == null) {
            entity = new Article();
        }
        return entity;
    }
	
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(SentenceListForm sentences,WordListForm words,Article article,@RequestParam("mp3") MultipartFile file,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
        if(file != null){
            if (!file.isEmpty()) {    
                File targetFile = JUploadUtils.save("2",file, request);
                article.setMp3(targetFile.getName());
            }
        }
        articleService.saveArticle(sentences.getSentences(),words.getWords(),article);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + adminPath + "/article/articleList";
    }
    
    @RequestMapping(value = "/analy")
    public String analy(Article article,HttpServletRequest request,Model model) throws IOException{
        Map map = sentenceService.analyArticle(article.getContent());
        model.addAttribute("words", map.get("words"));
        model.addAttribute("sentences", map.get("sentences"));
        model.addAttribute("article", article);
        return "word/article/addWord";
    }
}
