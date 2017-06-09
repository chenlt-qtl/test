
/**
* @(#)StopAnalyzerExtend.java 2017年6月9日
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

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseTokenizer;
import org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 描述:
 * @author chenlt
 */

class StopAnalyzerExtend extends Analyzer {
    private CharArraySet stopWordSet;//停止词词典

    public CharArraySet getStopWordSet() {
        return this.stopWordSet;
    }

    public void setStopWordSet(CharArraySet stopWordSet) {
        this.stopWordSet = stopWordSet;
    }

    /**
     * @param stops 需要扩展的停止词
     */
    public StopAnalyzerExtend(List<String> stops) {
        this();
        /**如果直接为stopWordSet赋值的话，会报如下异常，这是因为在StopAnalyzer中有ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
         * ENGLISH_STOP_WORDS_SET 被设置为不可更改的set集合
         */
        //stopWordSet = getStopWordSet();
        stopWordSet = CharArraySet.copy(getStopWordSet());
        stopWordSet.addAll(StopFilter.makeStopSet(stops));
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, new StopFilter(source, stopWordSet));
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> strings = new ArrayList<String>() {{
            add("小鬼子");
            add("美国佬");
        }};
        
        Analyzer analyzer = new StopAnalyzerExtend(strings);
        String content = "小鬼子 and 美国佬 are playing together!";
        TokenStream tokenStream = analyzer.tokenStream("myfield", content);
        tokenStream.reset();
        
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            // 已经过滤掉自定义停用词
            // 输出：playing   together
            System.out.println(charTermAttribute.toString());
        }
        tokenStream.end();
        tokenStream.close();
    }

    @Override
    public TokenStream tokenStream(String arg0, Reader arg1) {
        // TODO Auto-generated method stub
        return null;
    }
}
