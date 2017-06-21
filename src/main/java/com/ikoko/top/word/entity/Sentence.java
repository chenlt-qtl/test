package com.ikoko.top.word.entity;

import java.util.List;

import com.ikoko.top.sys.entity.DataEntity;

public class Sentence  extends DataEntity<Sentence> {

    private String articleId;

    private String content;
    
    private List<Word> wordList;


    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public List<Word> getWordList() {
		return wordList;
	}

	public void setWordList(List<Word> wordList) {
		this.wordList = wordList;
	}
    
    
}