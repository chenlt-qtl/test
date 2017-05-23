/**
* @(#)ArticleService.java 2016年11月8日
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ikoko.top.word.dao.AcceptationMapper;
import com.ikoko.top.word.dao.ArticleMapper;
import com.ikoko.top.word.dao.IcibaSentenceMapper;
import com.ikoko.top.word.dao.SentenceMapper;
import com.ikoko.top.word.dao.SentenceWordRelMapper;
import com.ikoko.top.word.dao.WordMapper;
import com.ikoko.top.word.dto.SentenceList;
import com.ikoko.top.word.entity.Acceptation;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.entity.ArticleExample;
import com.ikoko.top.word.entity.IcibaSentence;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.entity.SentenceWordRel;
import com.ikoko.top.word.entity.Word;
import com.ikoko.top.word.entity.WordExample;
import com.ikoko.top.word.util.HttpClientFactory;
import com.ikoko.top.word.util.ParseIciba;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Service
public class ArticleService {
	@Autowired
    private ArticleMapper articleMapper;
	
	public String testQuery() throws Exception {
        List<Article> articles = articleMapper.selectByExample(new ArticleExample());
        String res = "";
        if (articles != null && articles.size() > 0) {
            for (Article article : articles) {
                res += article.getTitle().toString() + "|";
            }
        } else {
            res = "Not found.";
        }
        return res;
    }
	
	public Article getById(String id){
	    return articleMapper.selectByPrimaryKey(Long.parseLong(id));
	}
	
   public Article getByIdWithoutMp3(String id){
        return articleMapper.selectByIdWithoutMp3(Long.parseLong(id));
    }
	
	@Autowired
	private SentenceMapper sentenceMapper;
	
	@Autowired
	private WordMapper wordMapper;
	
	@Autowired
	private SentenceWordRelMapper sentenceWordRelMapper;
	
	@Autowired
	private AcceptationMapper acceptationMapper;
	
	@Autowired
	private IcibaSentenceMapper icibaSentenceMapper;
	
	private final static String KEY="C772DB1F60B2839AD948507D91E7B04A"; 
	
	public void saveNewWord(SentenceList sentenceList,String title,MultipartFile file) throws IOException{
		Article article = new Article();
		article.setTitle(title);
		int wordNum = 0;
		if(file != null){
		    if (!file.isEmpty()) {    
		        byte [] mp3=new byte[1024];  //接收缓存   
		        InputStream fis = null; 
	            ByteArrayOutputStream baos = null;
	            fis = null;
		        try {
                    fis = file.getInputStream();
                    baos =  new ByteArrayOutputStream();
                    int len;
                    while( (len=fis.read(mp3))>0){ //接收
                        baos.write(mp3,0,len);
                    }
                    article.setMp3(baos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    baos.close();
                    fis.close();
                }
		    }
		}
		article.setStatus(1);
		articleMapper.insert(article);
		for(Sentence sentence:sentenceList.getSentences()){
		    sentence.setContent(sentence.getContent().replaceAll(" "," "));
		    if(StringUtils.isBlank(sentence.getContent().trim())){
		        continue;
		    }
			sentence.setArticleId(article.getId());
			sentenceMapper.insert(sentence);
			if(sentence.getWordList()!=null){
    			for(Word word:sentence.getWordList()){
    				word.setWordName(word.getWordName().toLowerCase());
    				WordExample wordExample = new WordExample();
    				wordExample.createCriteria().andWordNameEqualTo(word.getWordName());
    				List<Word> wordList = wordMapper.selectByExample(wordExample);
    				if(wordList.size()>0){
    					word.setId(wordList.get(0).getId());
    				}else{
    					Map detailMap = new HashMap();
    					CloseableHttpResponse response = null;
    					HttpRequestBase httpRequest = new HttpGet("http://dict-co.iciba.com/api/dictionary.php?w="+word.getWordName()+"&key="+KEY);
    					try {
    						response = HttpClientFactory.getHttpClient().execute(httpRequest);
    					} catch (Exception e1) {
    						System.out.println("查词失败:"+e1.getMessage());
    					}
    					int status = response.getStatusLine().getStatusCode();
    					if(status == 200){
    						try {
    							detailMap = ParseIciba.parse(EntityUtils.toString(response.getEntity(), "UTF-8"),word);
    						} catch (Exception e) {
    							System.out.println("解析查词结果失败:"+e.getMessage());
    						}
    
    					}
    					
    					wordMapper.insert(word);
    					if(detailMap.containsKey("acceptations")){
    						List acceptations= (List)detailMap.get("acceptations");
    						for(Iterator<Acceptation> iter = acceptations.iterator();iter.hasNext();){
    							Acceptation acceptation = iter.next();
    							acceptation.setWordId(word.getId());
    							acceptationMapper.insert(acceptation);
    						}
    					}
    					if(detailMap.containsKey("icibaSentence")){
    						List icibaSentences= (List)detailMap.get("icibaSentence");
    						for(Iterator<IcibaSentence> iter = icibaSentences.iterator();iter.hasNext();){
    							IcibaSentence icibaSentence = iter.next();
    							icibaSentence.setWordId(word.getId());
    							icibaSentenceMapper.insert(icibaSentence);
    						}
    					}
    					
    				}
    				
    				SentenceWordRel sentenceWordRel = new SentenceWordRel();
    				sentenceWordRel.setSentenceId(sentence.getId());
    				sentenceWordRel.setWordId(word.getId());
    				sentenceWordRelMapper.insert(sentenceWordRel);
    				wordNum++;
    			}
    		}
		}
		article.setWordNum(wordNum);
		articleMapper.updateByPrimaryKey(article);
	}
	
	public List getArticleByPage(Map map){
		return articleMapper.selectByPage(map);
	}
	
	public void delete(String id){
	    Article article = new Article();
	    article.setStatus(0);
	    ArticleExample ae = new ArticleExample();
	    ae.or().andIdEqualTo(Long.parseLong(id));
        articleMapper.updateByExampleSelective(article,ae);
    }
	
}
