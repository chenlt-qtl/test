/**
* @(#)SentenceService.java 2016年12月1日
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
package com.ikoko.top.word.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.word.dao.SentenceMapper;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.filter.WordFilter;


/**
 * 描述：
 * 
 * @author chenlt
 */
@Service
public class SentenceService  extends CrudService<SentenceMapper, Sentence> {
    
    static final String[] STOP_WORD_ARR = { "phoebe", "monica", "i", "a", "oh", "carl", "and", "is", "to", "her", "go",
            "not", "out", "can", "your", "here", "get", "we", "know", "but", "on", "of", "no", "my", "me", "that", "it",
            "in", "if", "he", "chandler", "she", "do", "joey","paul","be","an","you","the","like" };
    
    public List getSentenceByArticle(long id){
        return dao.selectByArticle(id);
    }
    
    public List selectByWord(String wordId){
        return dao.selectByWord(wordId);
    }
    
    public Map analyArticle(String content) throws IOException{
        content = com.ikoko.top.common.utils.StringUtils.unCleanXSS(content);
        Map result = new HashMap();
        List<Map<String,String>> words = new ArrayList<Map<String,String>>();
        
        Map<String,Set> wordMap = new HashMap<String,Set>();
        
        String regEx="[。？！?.!\r]"; 
        Pattern p =Pattern.compile(regEx);  
        Matcher m = p.matcher(content);  
        List<String> sentences = Arrays.asList(p.split(content));  
        if(sentences.size()>0){
            
            Analyzer analyzer = new StandardAnalyzer(StopFilter.makeStopSet(STOP_WORD_ARR, true));
            
            for(int i = 0; i<sentences.size();i++){
                String sentence = sentences.get(i);
                if(StringUtils.isBlank(sentence)){
                    continue;
                }
                WordFilter filter = new WordFilter(analyzer.tokenStream("content", new StringReader(sentence)));  
                filter.reset();  
                CharTermAttribute charTermAttribute = filter.addAttribute(CharTermAttribute.class);  
                while (filter.incrementToken()) {  
                    String word = charTermAttribute.toString();
                    if(StringUtils.isNotBlank(word)){
                        if(wordMap.containsKey(word)){
                            ((Set)wordMap.get(word)).add(i);
                        }else{
                            final int index = i;
                            wordMap.put(word,new HashSet(){{ add(index); } });
                        }
                    }
                    
                }  
                filter.end();  
                filter.close();
            }
        }
        
        //整理结果数据
        for(String word:wordMap.keySet()){
            Map map = new HashMap();
            map.put("word", word);
            Set sentenceSet = (Set)wordMap.get(word);
            map.put("count", sentenceSet.size());
            map.put("sentences", Arrays.asList(sentenceSet.toArray()));
            words.add(map);
        }

        result.put("words", words);
        result.put("sentences", sentences);
        return result;
    }
}
