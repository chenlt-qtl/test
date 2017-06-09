package word;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class AnalyzerDemo {


/**WhitespaceAnalyzer分析器*/
public void whitespaceAnalyzer(String msg){
WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_34);
this.getTokens(analyzer, msg);
}

/**SimpleAnalyzer分析器*/
public void simpleAnalyzer(String msg){
SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_34);
this.getTokens(analyzer, msg);
}

/**StopAnalyzer分析器*/
public void stopAnalyzer(String msg){
StopAnalyzer analyzer = new StopAnalyzer(Version.LUCENE_34);
this.getTokens(analyzer, msg);
}

/**StandardAnalyzer分析器*/
public void standardAnalyzer(String msg){
StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
this.getTokens(analyzer, msg);
}


private void getTokens(Analyzer analyzer,String msg){
TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(msg));
this.printTokens(analyzer.getClass().getSimpleName(),tokenStream);
}

private void printTokens(String analyzerType,TokenStream tokenStream){
CharTermAttribute ta = tokenStream.addAttribute(CharTermAttribute.class);
StringBuffer result =new StringBuffer();
try {
while(tokenStream.incrementToken()){
if(result.length()>0){
result.append(",");
}
result.append("["+ta.toString()+"]");
}
} catch (IOException e) {
e.printStackTrace();
}

System.out.println(analyzerType+"->"+result.toString());
}
}