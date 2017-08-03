package com.ikoko.top.word.entity;

import java.util.ArrayList;
import java.util.List;

import com.ikoko.top.sys.entity.DataEntity;

public class Word  extends DataEntity<Word> {
    private String wordName;
    private String phAm;
    private String exchange;
    private String parts;
    private String phAmMp3;
    private List<Acceptation> acceptations = new ArrayList<Acceptation>(); 
    private List<IcibaSentence> icibaSentence = new ArrayList<IcibaSentence>();
    private List<Sentence> sentences = new ArrayList<Sentence>();
    
    private String sentenceIndexs;

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
    public String getPhAm() {
        return phAm;
    }
    public void setPhAm(String phAm) {
        this.phAm = phAm;
    }

    public String getExchange() {
        return exchange;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    public String getParts() {
        return parts;
    }
    public void setParts(String parts) {
        this.parts = parts;
    }
   
    public String getPhAmMp3() {
        return phAmMp3;
    }

    public void setPhAmMp3(String phAmMp3) {
        this.phAmMp3 = phAmMp3;
    }

    public List<Acceptation> getAcceptations() {
        return acceptations;
    }

    public void setAcceptations(List<Acceptation> acceptations) {
        this.acceptations = acceptations;
    }

    public String getSentenceIndexs() {
        return sentenceIndexs;
    }

    public void setSentenceIndexs(String sentenceIndexs) {
        this.sentenceIndexs = sentenceIndexs;
    }

    public List<IcibaSentence> getIcibaSentence() {
        return icibaSentence;
    }

    public void setIcibaSentence(List<IcibaSentence> icibaSentence) {
        this.icibaSentence = icibaSentence;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
    
}