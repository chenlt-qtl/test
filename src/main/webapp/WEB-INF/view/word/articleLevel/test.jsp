<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>开始测试</title>
    <%@ include file="../../include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>

<audio id = 'audio' onended="end()"></audio>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                         <div id="test_content">
                            <div class="am-progress" style="margin-top: 10px; margin-right: 100px;">
                                <div id='progressBar' class="am-progress-bar"></div>
                            </div>
                            <div style='float:right'><span id="finish"></span>/<span id='total'></span></div>
                            <span id='wordLength'></span>
                            <div id='testByWordName' style="display:none">
                                <span name='wordName'></span>
                                <span id="play" class="oper oper-play1" onclick="return play()"></span><br /><br />
                                <b>A</b>  <span class="answer" name='option1' index='1'></span></br>
                                <b>B</b>  <span class="answer" name='option2' index='2'></span></br>
                                <b>C</b>  <span class="answer" name='option3' index='3'></span></br>
                                <b>D</b>  <span class="answer" name='option4' index='4'></span></br>
                            </div>
                            <div id='testByChinese' style="display:none">
                                <span name='chinese'></span><br/><br/>
                                <b>A</b>  <span class="answer" name='option1' index='1'></span><br/><br/>
                                <b>B</b>  <span class="answer" name='option2' index='2'></span><br/><br/>
                                <b>C</b>  <span class="answer" name='option3' index='3'></span><br/><br/>
                                <b>D</b>  <span class="answer" name='option4' index='4'></span><br/><br/>
                            </div>
                            <div id='testByMp3' style="display:none">
                                                                                                请听发音<span id="play" class="oper oper-play1" onclick="return play()"></span><br /><br />
                                <b>A</b>  <span class="answer" name='option1' index='1'></span><br/><br/>
                                <b>B</b>  <span class="answer" name='option2' index='2'></span><br/><br/>
                                <b>C</b>  <span class="answer" name='option3' index='3'></span><br/><br/>
                                <b>D</b>  <span class="answer" name='option4' index='4'></span><br/><br/>
                            </div>
                         </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
var words = [];
var origWords = [];
var word;
var index = 0;
var testObj={};
var msg;
var divIds = ["testByWordName","testByChinese","testByMp3"];
testObj.score = 0;//总得分
testObj.testNum = 0;//做过的题数
testObj.typeNum = 3;//测试方式 1选意思  2选单词 3听发音选意思 4拼写

$.ajax({
    traditional : true,
    type : "POST",
    url : "../word/getWords",
    data : {"ids":"${words}"},
    dataType : "json",
    success : function(result, textStatus) {
        words = result;
        if(words.length<=4){
        	alert("测试单词数要多于4个");
        	return;
        }
        origWords = $.extend([],words);
        $("#total").html((testObj.typeNum)*(words.length));
        test();
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
        Message.errorAlert("Invoke ajax error:" + textStatus + " " + errorThrown);
    }
}); 

//开始测试
function test(){
	$("#finish").html(testObj.testNum);
	index = getIndex();
	if(index == null){
		alert(JSON.stringify(testObj))
	}else{
		word = words[index];
		type = getType(word.id);
		switch(type){
		case 1:
			testByWordName();  
		    break;
		case 2:
			testByChinese();  
            break;
		case 3:
            testByMp3();  
            break;
		case 4:
            testByChinese();  
            break;
		default:
		    alert("未知的测试类型");
		}		
	}
	index++;
}

//获取测试类型
function getType(wordId){
	if(!testObj[wordId]){
		return 1;
	}else{
		return testObj[wordId].type+1;
	}
}

//获取随机单词序号
function getIndex(){
	if(words.length == 0){
		return null;//全部做完
	}else{
		index = Math.floor(Math.random()*(words.length));
		word = words[index];
	    if(!testObj[word.id]){
	        return index;
	    }else{
	        if(testObj[word.id].type==testObj.typeNum){//全部做过了
	        	words.splice(index,1);
	            return getIndex();
	        }
	        return index;
	    }
	}
}

//显示div
function showDiv(index){
	$.each(divIds,function(i){
		if(i==index){
			$("#"+this).show();
		}else{
			$("#"+this).hide();
		}
	})
}

//选意思
function testByWordName(){
	$("#"+divIds[0]+">[name=wordName]").html(word.wordName);
	testObj.rightOption = Math.floor(Math.random()*4)+1;
	var acceptations = word.acceptations;
	
	setOption(divIds[0],'option'+testObj.rightOption,getChinese(word));
	var indexs = [];
	indexs.push(index);
	for(var i = 1; i<=4 ; i++){
	    if(i!=testObj.rightOption){
	    	var next = getRandomIndex(indexs);
	        indexs.push(next);
	        setOption(divIds[0],"option"+i,getChinese(origWords[next]));
	    }
	}
	showDiv(0);
}

function testByChinese(){
	var acceptations = word.acceptations;
	var chinese = "";
	$.each(acceptations,function(){
		chinese += this.pos+" "+this.acceptation + "</br>";
	})
	
    $("#"+divIds[1]+">[name=chinese]").html(chinese);
    testObj.rightOption = Math.floor(Math.random()*4)+1;
    
    setOption(divIds[1],'option'+testObj.rightOption,word.wordName);
    var indexs = [];
    indexs.push(index);
    for(var i = 1; i<=4 ; i++){
        if(i!=testObj.rightOption){
            var next = getRandomIndex(indexs);
            indexs.push(next);
            setOption(divIds[1],"option"+i,origWords[next].wordName);
        }
    }
    showDiv(1);
}

function testByMp3(){
    testObj.rightOption = Math.floor(Math.random()*4)+1;
    setOption(divIds[2],'option'+testObj.rightOption,getChinese(word));
    var indexs = [];
    indexs.push(index);
    for(var i = 1; i<=4 ; i++){
        if(i!=testObj.rightOption){
            var next = getRandomIndex(indexs);
            indexs.push(next);
            setOption(divIds[2],"option"+i,getChinese(origWords[next]));
        }
    }
    showDiv(2);
}

function setOption(divId,spanName,value){
	$("#"+divId+">[name="+spanName+"]").html(value);
}

//获取中文信息
function getChinese(obj){
	var value = "";
	$.each(obj.acceptations,function(){
        value += this.pos+" "+this.acceptation + "</br>";
    })
    return value;
}
//获取随机单词
function getRandomIndex(indexs){
    var wordIndex = Math.floor(Math.random()*(origWords.length));
    var repeat = false;
    if(indexs){
    	$.each(indexs,function(){
    		if(wordIndex==this){
                repeat = true;
            }
    	})
    }
    if(!repeat){
        return wordIndex;
    }else{
        return getRandomIndex(indexs);
    }

}

/**
 *答对总分加1 单词分数加1 
 *答错单词分数减1
 */
$(".answer").on("click",function(){
	var answerIndex = $(this).attr("index");
	var right = false;
	if(answerIndex == testObj.rightOption){
		right = true;
		testObj.score++;
	}
	
    var wordId = word.id;
    
    if(!testObj[wordId]){//初始化
    	testObj[wordId] = {};
    	testObj[wordId].score = 0;
    	testObj[wordId].type = 0;
    }
   	if(right){
   	    testObj[wordId].score++;
   	}else{
   		testObj[wordId].score--;
   	}   
   	testObj[wordId].type++;
    testObj.testNum++;//做过的题数
    msg += '/n'+JSON.stringify(testObj);
	test();
})


//播放音频
function end(){
    $("#play").attr("class","oper oper-play1");
}

function play(){
    var audio = document.getElementById("audio");
    audio.src = "/file/ph_mp3/"+word.phAmMp3;
    audio.play();
    $("#play").attr("class","oper oper-pause");
    return false;
}
</script>
</body>
</html>