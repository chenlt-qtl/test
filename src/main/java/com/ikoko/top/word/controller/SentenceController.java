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

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ikoko.top.word.dto.SentenceList;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.service.ArticleService;
import com.ikoko.top.word.service.SentenceService;
import com.ikoko.top.word.util.RequestUtil;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Controller
@RequestMapping("/sentence")
public class SentenceController extends BaseController{
	@Autowired
    private ArticleService articleService;
	
	@Autowired
    private SentenceService sentenceService;
	
    @RequestMapping("/save")
    public void enterChart(SentenceList sentenceList,String title,@RequestParam("mp3") MultipartFile[] files) throws ClientProtocolException, IOException {
        MultipartFile myfile = null;
        if(files.length>0) {
            myfile = files[0];
            if(myfile.getSize()>102400000){
                Map map = new HashMap();
                map.put("status", "fail");
                map.put("msg", "文件不能大于100M");
                writeResponse(map);
                return;
            }
        }
    	articleService.saveNewWord(sentenceList,title,myfile);
    	writeSuccess();
    }
    
    @RequestMapping("/getContent")
    public void getContent() throws IOException{
        Map map = RequestUtil.getParameterMap(request);
        List<Sentence> list = sentenceService.getSentenceByArticle(Long.parseLong((String)map.get("id")));
        String content = "";
        for(Sentence sentence:list){
            content += sentence.getContent()+". ";
        }
        map.clear();
        map.put("content", content);
        writeResponse(map);
    }
}
