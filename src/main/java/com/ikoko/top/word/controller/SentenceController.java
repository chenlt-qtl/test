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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ikoko.top.word.dto.SentenceList;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.service.SentenceService;
import com.ikoko.top.word.util.RequestUtil;
import com.ikoko.top.common.BaseController;

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
	
    @RequestMapping(value = "/save")
    public String save(SentenceList sentenceList,String title,@RequestParam("mp3") MultipartFile file, HttpServletResponse response, RedirectAttributes redirectAttributes) throws ClientProtocolException, IOException {
    	articleService.saveNewWord(sentenceList,title,file);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + adminPath + "/article/articleList";
    }
    
    @RequestMapping(value = "/getContent")
    public void getContent(HttpServletResponse response, HttpServletRequest request) throws IOException{
        Map map = RequestUtil.getParameterMap(request);
        List<Sentence> list = sentenceService.getSentenceByArticle(Long.parseLong((String)map.get("id")));
        String content = "";
        for(Sentence sentence:list){
            content += sentence.getContent()+". ";
        }
        map.clear();
        map.put("content", content);
        renderString(response, map);
    }
}
