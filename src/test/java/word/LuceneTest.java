
/**
* @(#)LuceneTest.java 2017年6月9日
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
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

/**
 * 描述:
 * @author chenlt
 */

public class LuceneTest {

        static final String[] STOP_WORD_ARR = {
            "phoebe","monica","i","a","oh","carl","and","is","to","her","go","not","out","this"
        };

      //private String msg = "我喜欢你，我的祖国！china 中国";
      private String msg = "Phoebe: Just, 'cause, I don't want her to go through what I went through with Carl- oh!Monica: Okay, everybody relax. This is not even a date. It's just two people going out to dinner and- not having sex.";


      @Test
      public void testStandardAnalyzer() throws IOException{

          Analyzer analyzer = new StandardAnalyzer(StopFilter.makeStopSet(StopWordAnalyzer.STOP_WORD_ARR, true));
          WordFilter filter = new WordFilter(analyzer.tokenStream("text", new StringReader(msg)));
          CharTermAttribute charTermAttribute = filter.addAttribute(CharTermAttribute.class);
          filter.reset();
          while (filter.incrementToken()) {
              System.out.print(charTermAttribute + " ");

          }
          
      }
        
    
}
