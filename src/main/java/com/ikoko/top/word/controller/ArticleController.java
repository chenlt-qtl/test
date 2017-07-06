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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.ikoko.top.common.Page;
import com.ikoko.top.common.utils.JStringUtils;
import com.ikoko.top.common.utils.JUploadUtils;
import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.word.dto.SentenceListForm;
import com.ikoko.top.word.dto.WordListForm;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.service.ArticleUserRelService;
import com.ikoko.top.word.service.WordService;
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
	
	@Autowired
    private ArticleUserRelService articleUserRelService;
	
	@Autowired
    private WordService wordService;
    
    @RequestMapping(value = "/add")
    public String enterAdd(HttpServletRequest request, HttpServletResponse response) {
    	return "word/article/addArticle";
    }
    
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
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String enterEdit(Model model,String id) {
        if(StringUtils.isNotBlank(id)){
            Article article = articleService.get(id);
            model.addAttribute("article", article);
        }
        return "word/article/editArticle";
    }
    
    @RequestMapping(value = "/save")
    public String saveEdit(SentenceListForm sentences,WordListForm words,String id,String title,String content,@RequestParam("mp3") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Article article = null;
        if(StringUtils.isBlank(id)){
            article = new Article();
            article.setContent(content);
            article.setTitle(title);
        }else{
            article = articleService.get(id);
            article.setTitle(title);
        }
        if(file != null){
            if (!file.isEmpty()) {
                if(StringUtils.isNotBlank(article.getMp3())){
                    JUploadUtils.del("2", article.getMp3(), request);//删除已存在的文件
                }
                File targetFile = JUploadUtils.save("2",file, request);
                article.setMp3(targetFile.getName());
            }
        }
        articleService.saveArticle(sentences.getSentences(),words.getWords(),article);
        addMessage(redirectAttributes, "保存成功");
        return  "redirect:" + adminPath + "/article/articleList";
    }
    
    @RequestMapping(value = "/articleList")
    public String showArticle(Article article,Model model, Page<Article> page){
        page.setEntity(article);
        model.addAttribute("page", page.setList(articleService.findPage(page)));
    	return "word/article/listArticle";
    }
    
    @RequestMapping(value = "/my/listOther")
    public String listOther(Article article,Model model, Page<Article> page){
        article.setUser(UserUtils.getLoginUser());
        page.setEntity(article);
        model.addAttribute("page", page.setList(articleService.findOtherPage(page)));
        return "word/article/listOther";
    }
    
    @RequestMapping(value = "/my/add")
    public String addMy(String[] ids, int pageNo, int pageSize, RedirectAttributes redirectAttributes) {
        articleUserRelService.addAll(ids,UserUtils.getLoginUser().getId());
        addMessage(redirectAttributes, "添加成功");
        return "redirect:" + adminPath + "/article/my/articleList?pageNo=" + pageNo + "&pageSize=" + pageSize;
    }
    
    
    
    
    @RequestMapping(value = "/my/articleList")
    public String showMyArticle(Article article,Model model, Page<Article> page){
        article.setUser(UserUtils.getLoginUser());
        page.setEntity(article);
        model.addAttribute("page", page.setList(articleService.findPage(page)));
        return "word/article/listMyArticle";
    }
    
    @RequestMapping(value = "/delete")
    public String delete(Article article,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        if(article != null){
            articleService.delete(article);
            addMessage(redirectAttributes, "删除成功");
        }
        return "redirect:" + adminPath + "/article/articleList?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value = "/getContent")
    public String getContent(HttpServletResponse response, HttpServletRequest request,Model model) throws IOException{
        Map map = RequestUtil.getParameterMap(request);
        String articleId = String.valueOf(map.get("id"));
        Article article = articleService.get(articleId);
        
        model.addAttribute("articleId", articleId); 
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent().replaceAll("\r\n", "</br>"));
        List list = wordService.getByArticle(articleId);
        model.addAttribute("words", list);
        model.addAttribute("wordNum", list.size());
        return "word/sentence/content";
    }
    
}
