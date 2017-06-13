
/**
* @(#)MyAnalyzer.java 2017年6月12日
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

package word;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 描述:
 * @author chenlt
 * 自定义Analyzer实现扩展停用词  
 */

public class StopWordAnalyzer extends Analyzer {  
    public StopWordAnalyzer() {  
        super();  
        setStopWordSet(StopAnalyzer.ENGLISH_STOP_WORDS_SET);  
    }  
    
    static final String[] STOP_WORD_ARR = {
        "phoebe","monica","i","a","oh","carl","and","is","to","her","go","not","out"
    };
  
    private CharArraySet stopWordSet;//停止词词典  
  
    public StopWordAnalyzer(List<String> stops) {  
        this();  
        //stopWordSet = getStopWordSet();  //如果直接这么调用的话，则并没有什么效果  
        stopWordSet = CharArraySet.copy(getStopWordSet());//执行过滤分析  
        stopWordSet.addAll(StopFilter.makeStopSet(stops));  
    }  
  
    public CharArraySet getStopWordSet() {  
        return stopWordSet;  
    }  
  
    public void setStopWordSet(CharArraySet stopWordSet) {  
        this.stopWordSet = stopWordSet;  
    }  
  
    @Override  
    protected TokenStreamComponents createComponents(String s) {  
        Tokenizer source = new LowerCaseTokenizer();  
        return new TokenStreamComponents(source, new StopFilter(source, stopWordSet));  
    }  
  
    public static void main(String[] args) throws Exception {  
        Analyzer analyzer = new StandardAnalyzer(StopFilter.makeStopSet(StopWordAnalyzer.STOP_WORD_ARR, true));
        String content = "Phoebe: Just, 'cause, I don't want her to go through what I went through with Carl- oh!Monica: Okay, everybody relax. This is not even a date. It's just two people going out to dinner and- not having sex.";  
        TokenStream tokenStream = analyzer.tokenStream("myfield", content);  
        LengthFilter filter = new LengthFilter(tokenStream, 4, 7);
        tokenStream.reset();  
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);  
        while (tokenStream.incrementToken()) {  
            // 已经过滤掉自定义停用词  
            // 输出：playing   together  
            System.out.print(charTermAttribute.toString()+" ");  
        }  
        tokenStream.end();  
        tokenStream.close();
    }  
}  
