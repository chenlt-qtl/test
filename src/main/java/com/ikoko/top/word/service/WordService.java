
/**
* @(#)WordService.java 2017年4月12日
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

package com.ikoko.top.word.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikoko.top.word.dao.WordMapper;
import com.ikoko.top.word.entity.Acceptation;
import com.ikoko.top.word.entity.IcibaSentence;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.entity.Word;

/**
 * 描述:
 * @author chenlt
 */
@Service
public class WordService {
    
    private final static int LEVEL_WORD_NUM = 10;//每关单词数
    
    @Autowired
    private WordMapper wordMapper;
    
    @Autowired
    private AcceptationService acceptationService;
    
    @Autowired
    private IcibaSentenceService icibaSentenceService;    
    
    @Autowired
    private SentenceService sentenceService;  
    
    public List getByArticle(String articleId){
        return wordMapper.selectByArticleId(new HashMap(){{put("articleId", articleId);}});
    }
    
    public List getByLevel(String articleId,int level){
        return wordMapper.selectByArticleId(new HashMap() {
            {
                put("articleId", articleId);
                put("start", level*LEVEL_WORD_NUM);
                put("limit", LEVEL_WORD_NUM);
            }
        });
    }
    
    public Word getById(String id){
        Word word = wordMapper.get(id);
        word.setAcceptations(acceptationService.getByWordId(word.getId()));
        return word;
    }
    
    /**
     * 获得多个words
     * @param ids
     * @return
     */
    public List<Word> getWords(String ids){
        List<Word> words = new ArrayList<Word>();
        if(StringUtils.isNotBlank(ids)){
            words = wordMapper.selectWords(ids.split(","));
            List<IcibaSentence> icibaSentences = icibaSentenceService.selectByWordIds(ids.split(","));
            List<Acceptation> acceptations = acceptationService.getByWordIds(ids.split(","));
            Map<String,List<Acceptation>> acceptationsMap = new HashMap<String,List<Acceptation>>();
            Map<String,List<IcibaSentence>> icibaSentenceMap = new HashMap<String,List<IcibaSentence>>();
            for(Acceptation acceptation:acceptations){
                List<Acceptation> list = new ArrayList<Acceptation>();
                if(acceptationsMap.containsKey(acceptation.getWordId())){
                    list = acceptationsMap.get(acceptation.getWordId());
                }else{
                    acceptationsMap.put(acceptation.getWordId(),list);
                }
                list.add(acceptation);
                
            }
            for(IcibaSentence icibaSentence:icibaSentences){
                List<IcibaSentence> list = new ArrayList<IcibaSentence>();
                if(icibaSentenceMap.containsKey(icibaSentence.getWordId())){
                    list = icibaSentenceMap.get(icibaSentence.getWordId());
                }else{
                    icibaSentenceMap.put(icibaSentence.getWordId(),list);
                }
                list.add(icibaSentence);
            }
            for(Word word:words){
                word.setAcceptations(acceptationsMap.get(word.getId()));
                word.setIcibaSentence(icibaSentenceMap.get(word.getId()));
                word.setSentences(sentenceService.selectByWord(word.getId()));
            }
        }
        return words;
    }
}
