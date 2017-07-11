
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ikoko.top.common.BaseController;
import com.ikoko.top.word.entity.Word;
import com.ikoko.top.word.service.IcibaSentenceService;
import com.ikoko.top.word.service.SentenceService;
import com.ikoko.top.word.service.WordService;

/**
 * 描述:
 * @author chenlt
 */
@Controller
@RequestMapping("${adminPath}/word")
public class WordController extends BaseController{

    @Autowired
    private WordService wordService;    
    
    @Autowired
    private IcibaSentenceService icibaSentenceService;    
    
    @Autowired
    private SentenceService sentenceService;    
    
    @RequestMapping(value="/detail")
    public String enterDetail(HttpServletRequest request,Model model) {
        Object id = request.getParameter("id");
        Word word = null;
        if(id != null){
            word = wordService.getById(String.valueOf(id));
        }
        model.addAttribute("word", word);
        model.addAttribute("icibaSentence", icibaSentenceService.selectByWordId(String.valueOf(word.getId())));
        model.addAttribute("sentences", sentenceService.selectByWord(String.valueOf(word.getId())));
        return "word/wordDetail";
    }
    
}
