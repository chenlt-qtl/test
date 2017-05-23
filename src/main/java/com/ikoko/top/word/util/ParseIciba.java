/**
* @(#)ParseIciba.java 2016年12月1日
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
package com.ikoko.top.word.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ikoko.top.word.entity.Acceptation;
import com.ikoko.top.word.entity.IcibaSentence;
import com.ikoko.top.word.entity.Word;

/**
 * 描述：
 * 
 * @author chenlt
 */
public class ParseIciba {

	public static Map parse(String data,Word word) throws Exception{
		Map result = new HashMap();
		Document doc = DocumentHelper.parseText(data);
		Element rootElt = doc.getRootElement(); // 获取根节点
		Iterator phIter = rootElt.elementIterator("ps"); // 美式音标
		while (phIter.hasNext()) {
			Element phElement = (Element) phIter.next();
			if(!phIter.hasNext()) {//最后一个元素
				word.setPhAm(phElement.getTextTrim());
			}
		}
		Iterator pronIter = rootElt.elementIterator("pron"); // 美式发音
		while (pronIter.hasNext()) {
			Element pronElement = (Element) pronIter.next();
			System.out.println("-----------"+pronElement.getText());
			if(!pronIter.hasNext()) {//最后一个元素
				InputStream in = null;
				ByteArrayOutputStream baos = null;
				try {
					in = new URL(pronElement.getTextTrim()).openConnection().getInputStream();//创建连接、输入流
					baos = new ByteArrayOutputStream();
			        
					byte [] mp3=new byte[1024];  //接收缓存
					int len;
					while( (len=in.read(mp3))>0){ //接收
					    baos.write(mp3,0,len);
					}
			        word.setPhAmMp3(baos.toByteArray());
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
						baos.close();
						in.close();
				}
				
		        
			}
		}
		
		List posList = rootElt.elements("pos"); 
		List acceptationElements = rootElt.elements("acceptation"); 
		if(posList.size()!=acceptationElements.size()){
			throw new Exception("意思和时态配对失败");
		}
		
		List<Acceptation> acceptationList = new ArrayList<Acceptation>();
				
		for(int i = 0 ;i<posList.size();i++){
			
			Element posElement = (Element) posList.get(i);
			Element acceptationElement = (Element) acceptationElements.get(i);
			Acceptation acceptation = new Acceptation();
			acceptation.setPos(posElement.getText());
			acceptation.setAcceptation(acceptationElement.getText());
			acceptationList.add(acceptation);
		}
		
		List<IcibaSentence> isList = new ArrayList<IcibaSentence>();
		Iterator sentElement = rootElt.elementIterator("sent"); 
		while(sentElement.hasNext()){
			Element sent = (Element)sentElement.next();
			Element orig = (Element)sent.element("orig");
			Element trans = (Element)sent.element("trans");
			IcibaSentence is = new IcibaSentence();
			is.setOrig(URLEncoder.encode(orig.getText(), "utf-8"));
			is.setTrans(URLEncoder.encode(trans.getText(), "utf-8"));
			isList.add(is);
			
		}
		
		result.put("acceptations", acceptationList);
		result.put("icibaSentence", isList);
		return result;
	}
	
	public static void main(String args[]) throws IOException, Exception{
		File file = new File(ParseIciba.class.getClassLoader().getResource("").getPath() + "a.xml");
		Word word = new Word();
		word.setWordName("identify");
		Map map = ParseIciba.parse(FileUtils.readFileToString(file, "UTF-8"),word);
		List list1= (List)map.get("acceptations");
		List list2= (List)map.get("icibaSentence");
		System.out.println(map.get("acceptations").toString());
	}
}
