
/**
* @(#)WordController.java 2017年4月12日
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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ikoko.top.word.entity.Word;
import com.ikoko.top.word.service.AcceptationService;
import com.ikoko.top.word.service.WordService;

/**
 * 描述:
 * @author chenlt
 */
@Controller
@RequestMapping("/word")
public class WordController extends BaseController{

    @Autowired
    private WordService wordService;    
    
    @RequestMapping("/getWordByArticle")
    public void getWordByArticle() {
        Object id = request.getParameter("articleId");
        if(id != null){
            List list = wordService.getByArticle(String.valueOf(id));
            Map map = new HashMap<>();
            map.put("rows", list);
            map.put("results", list.size());
            writeResponse(map);
        }
    }
    
    @RequestMapping("/getMp3")
    public void getMp3() {
        Object id = request.getParameter("id");
        if(id != null){
            Word word = wordService.getMp3(String.valueOf(id));
            writeMp3(word.getPhAmMp3());
        }
    }
    
    @RequestMapping("/detail")
    public ModelAndView enterDetail() {
        Object id = request.getParameter("id");
        Word word = null;
        if(id != null){
            word = wordService.getById(String.valueOf(id));
        }
        return new ModelAndView("wordDetail","word",word);
    }

}
