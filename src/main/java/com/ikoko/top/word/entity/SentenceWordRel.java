package com.ikoko.top.word.entity;

import com.ikoko.top.sys.entity.DataEntity;

public class SentenceWordRel  extends DataEntity<SentenceWordRel> {
    private String sentenceId;
    private String wordId;
    public String getSentenceId() {
        return sentenceId;
    }
    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }
    public String getWordId() {
        return wordId;
    }
    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
    
    
}