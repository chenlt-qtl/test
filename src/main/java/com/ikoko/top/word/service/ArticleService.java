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
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ikoko.top.common.Page;
import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.word.dao.AcceptationMapper;
import com.ikoko.top.word.dao.ArticleMapper;
import com.ikoko.top.word.dao.IcibaSentenceMapper;
import com.ikoko.top.word.dao.SentenceMapper;
import com.ikoko.top.word.dao.SentenceWordRelMapper;
import com.ikoko.top.word.dao.WordMapper;
import com.ikoko.top.word.entity.Acceptation;
import com.ikoko.top.word.entity.Article;
import com.ikoko.top.word.entity.IcibaSentence;
import com.ikoko.top.word.entity.Sentence;
import com.ikoko.top.word.entity.SentenceWordRel;
import com.ikoko.top.word.entity.Word;
import com.ikoko.top.word.util.HttpClientFactory;
import com.ikoko.top.word.util.ParseIciba;

/**
 * 描述：
 * 
 * @author chenlt
 */
@Service
@Transactional(readOnly = true)
public class ArticleService  extends CrudService<ArticleMapper, Article> {
	
   public Article getByIdWithoutMp3(String id){
        return dao.selectByIdWithoutMp3(Long.parseLong(id));
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
	
	public void saveNewWord(List<Sentence> sentenceList,List<Word> wordList,String title,String content,MultipartFile file) throws IOException{
	    List<String> sentenceIds = new ArrayList<String>();
		Article article = new Article();
		article.setTitle(title);
		content = com.ikoko.top.common.utils.StringUtils.unCleanXSS(content);
		article.setContent(content);
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
		article.setStatus("0");
		dao.insert(article);
		for(Sentence sentence:sentenceList){
		    sentence.setContent(sentence.getContent().replaceAll("\r\n"," "));
		    if(StringUtils.isBlank(sentence.getContent().trim())){
		        sentenceIds.add("");
		        continue;
		    }
			sentence.setArticleId(article.getId());
			sentenceMapper.insert(sentence);
			sentenceIds.add(sentence.getId());
		}
    	
		for(Word word:wordList){
				word.setWordName(word.getWordName().toLowerCase());
				Map map = new HashMap();
		        map.put("wordName", word.getWordName());
				List<Word> wordDbList = wordMapper.selectByParam(map);
				if(wordDbList.size()>0){
					word.setId(wordDbList.get(0).getId());//已存在数据库
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
							detailMap = ParseIciba.parse(EntityUtils.toString(response.getEntity(),"utf-8"),word);
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
				String sentenceIndexs = word.getSentenceIndexs();
                sentenceIndexs = sentenceIndexs.replace("[", "");
                sentenceIndexs = sentenceIndexs.replace("]", "");
                String[] sentenceIndex = sentenceIndexs.split(",");
                for(String index:sentenceIndex){
                    if(StringUtils.isNotBlank(index)){
        				SentenceWordRel sentenceWordRel = new SentenceWordRel();
        				sentenceWordRel.setWordId(word.getId());
        				sentenceWordRel.setSentenceId(sentenceIds.get(Integer.parseInt(index.trim())));
        				sentenceWordRelMapper.insert(sentenceWordRel);
                    }
                }
                
                wordNum++;
		}
		article.setWordNum(wordNum);
		dao.update(article);
	}
	
	 public List<Article> findOtherPage(Page<Article> page) {
	        page.setTotal(dao.countOther(page));
	        return dao.findOtherPage(page);
	    }
	
}
