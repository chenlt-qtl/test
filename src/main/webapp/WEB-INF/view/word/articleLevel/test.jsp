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

<div class="am-modal am-modal-alert" tabindex="-1" id="result">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">Amaze UI</div>
    <div class="am-modal-bd">
      Hello world！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>


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
                            <div id='testBySpell' style="display:none">
                                <span name='chinese'></span><br />
                                <span name='wordName'></span><br /><br />
                                <span class="am-badge am-badge-secondary am-round">提示<span id="play" class="oper oper-play1" onclick="return play()"></span></span><br /><br />
                                <div id="letters"></div>
                                <button id='button' style="margin-left:310px;margin-top:20px;" type="button" onclick="return checkResult()" class="am-btn am-btn-secondary am-round">确定</button>
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
var divIds = ["testByWordName","testByChinese","testByMp3","testBySpell"];
testObj.score = 0;//总得分
testObj.testNum = 0;//做过的题数
testObj.typeNum = 1;//测试方式 1选意思  2选单词 3听发音选意思 4拼写
var spellBlank = "<span name='blank' style='margin-left:3px;margin-right:3px'>_</span>";

var letter = "abcdefghijklmnopqrstuvwxyz";
var letterArr = toArr(letter);

function toArr(str){
	var arr = [];
	for (var i = 0; i < str.length; i++) {
	    arr[i] = str[i];
	}
	return arr;
}

	$.ajax({
		traditional : true,
		type : "POST",
		url : "../word/getWords",
		data : {
			"ids" : "${words}"
		},
		dataType : "json",
		success : function(result, textStatus) {
			words = result;
			if (words.length <= 4) {
				alert("测试单词数要多于4个");
				return;
			}
			origWords = $.extend([], words);
			$("#total").html((testObj.typeNum) * (words.length));
			test();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			Message.errorAlert("Invoke ajax error:" + textStatus + " "
					+ errorThrown);
		}
	});

	//开始测试
	function test() {
		$("#finish").html(testObj.testNum);
		index = getIndex();
		if (index == null) {
			alert(JSON.stringify(testObj))
		} else {
			word = words[index];
			type = getType(word.id);
			switch (type) {
			case 1:
				testBySpell();
				break;
			case 2:
				testByChinese();
				break;
			case 3:
				testByMp3();
				break;
			case 4:
				testBySpell();
				break;
			default:
				alert("未知的测试类型");
			}
		}
		index++;
	}

	//获取测试类型
	function getType(wordId) {
		if (!testObj[wordId]) {
			return 1;
		} else {
			return testObj[wordId].type + 1;
		}
	}

	//获取随机单词序号
	function getIndex() {
		if (words.length == 0) {
			return null;//全部做完
		} else {
			index = Math.floor(Math.random() * (words.length));
			word = words[index];
			if (!testObj[word.id]) {
				return index;
			} else {
				if (testObj[word.id].type == testObj.typeNum) {//全部做过了
					words.splice(index, 1);
					return getIndex();
				}
				return index;
			}
		}
	}

	//显示div
	function showDiv(index) {
		$.each(divIds, function(i) {
			if (i == index) {
				$("#" + this).show();
			} else {
				$("#" + this).hide();
			}
		})
	}

	//选意思
	function testByWordName() {
		$("#" + divIds[0] + ">[name=wordName]").html(word.wordName);
		testObj.rightOption = Math.floor(Math.random() * 4) + 1;
		var acceptations = word.acceptations;

		setOption(divIds[0], 'option' + testObj.rightOption, getChinese(word));
		var indexs = [];
		indexs.push(index);
		for (var i = 1; i <= 4; i++) {
			if (i != testObj.rightOption) {
				var next = getRandomIndex(indexs);
				indexs.push(next);
				setOption(divIds[0], "option" + i, getChinese(origWords[next]));
			}
		}
		showDiv(0);
	}
	//选中文
	function testByChinese() {
		var acceptations = word.acceptations;
		var chinese = "";
		$.each(acceptations, function() {
			chinese += this.pos + " " + this.acceptation + "</br>";
		})

		$("#" + divIds[1] + ">[name=chinese]").html(chinese);
		testObj.rightOption = Math.floor(Math.random() * 4) + 1;

		setOption(divIds[1], 'option' + testObj.rightOption, word.wordName);
		var indexs = [];
		indexs.push(index);
		for (var i = 1; i <= 4; i++) {
			if (i != testObj.rightOption) {
				var next = getRandomIndex(indexs);
				indexs.push(next);
				setOption(divIds[1], "option" + i, origWords[next].wordName);
			}
		}
		showDiv(1);
	}

	//根据读音选中文
	function testByMp3() {
		testObj.rightOption = Math.floor(Math.random() * 4) + 1;
		setOption(divIds[2], 'option' + testObj.rightOption, getChinese(word));
		var indexs = [];
		indexs.push(index);
		for (var i = 1; i <= 4; i++) {
			if (i != testObj.rightOption) {
				var next = getRandomIndex(indexs);
				indexs.push(next);
				setOption(divIds[2], "option" + i, getChinese(origWords[next]));
			}
		}
		showDiv(2);
	}

	//拼写测试
	function testBySpell() {
		var acceptations = word.acceptations;
		var chinese = "";
		$.each(acceptations, function() {
			chinese += this.pos + " " + this.acceptation + "</br>";
		})
		$("#" + divIds[3] + ">[name=chinese]").html(chinese);
		var newWordName = "";
		for (var i = 0; i < word.wordName.length; i++) {
			if (i == 0 || i == word.wordName.length - 1) {
				newWordName += word.wordName[i];
			} else {
				newWordName += spellBlank;
			}
		}
		$("#" + divIds[3] + ">[name=wordName]").html(newWordName);
		
		var letters = letterArr.sort(function(){ return 0.5 - Math.random() }).slice(0,word.wordName.length/2);//打乱字母表，取出单词长度的一半
		var letters = letters.concat(toArr(word.wordName.slice(1,word.wordName.length-1))).sort(function(){ return 0.5 - Math.random() });
		$("#letters").html("");
		$.each(letters,function(i){
			$("#letters").append('<span name="letter_'+i+'" style="margin:5px;cursor:pointer;" class="am-badge am-round">'+this+'</span>');
			if((i+1)%5==0){
				$("#letters").append("<br/>");
			}
		})
		
		showDiv(3);
		$("button").attr("disabled","disabled");
	}
	
	function checkResult(){
		var spans = $("#" + divIds[3] + ">[name=wordName]").find("[name='blank']")
        if(spans.length>0){
        	showMsg("请填写完整的单词");
        	return false;
        }else{
        	var spans = $("#" + divIds[3] + ">[name=wordName]").find("span");
        	var wordResult = word.wordName[0];
        	$.each(spans,function(){
        		wordResult += $(this).find("u").html();
        	})
        	wordResult += word.wordName[word.wordName.length-1];
        	if(wordResult == word.wordName){
        		resultHandler(true);
        	}else{
        		//错误处理
        		resultHandler(false);
        	}
        	
        }
	}
	
	$("#letters").on("click","span",function(){
		var span = $(this);
		if(span.hasClass("am-badge-primary")){
			var spans = $("#" + divIds[3] + ">[name=wordName]").find("[name='"+span.attr("name")+"']");
			if (spans.length == 1) {
				$(spans).html("_");
				$(spans).attr("name", "blank");
				span.removeClass("am-badge-primary");
				$("button").attr("disabled", "disabled");
			}
		} else {
			var spans = $("#" + divIds[3] + ">[name=wordName]").find(
					"[name='blank']");
			if (spans.length == 1) {
				$("button").removeAttr("disabled");
			}
			if (spans.length > 0) {
				var blank = spans[0];
				$(blank).attr("name", span.attr("name"));
				$(blank).html("<u>" + span.html() + "</u>");
				span.addClass("am-badge-primary");

			}

		}
	})

	function setOption(divId, spanName, value) {
		$("#" + divId + ">[name=" + spanName + "]").html(value);
	}

	//获取中文信息
	function getChinese(obj) {
		var value = "";
		$.each(obj.acceptations, function() {
			value += this.pos + " " + this.acceptation + "</br>";
		})
		return value;
	}
	//获取随机单词
	function getRandomIndex(indexs) {
		var wordIndex = Math.floor(Math.random() * (origWords.length));
		var repeat = false;
		if (indexs) {
			$.each(indexs, function() {
				if (wordIndex == this) {
					repeat = true;
				}
			})
		}
		if (!repeat) {
			return wordIndex;
		} else {
			return getRandomIndex(indexs);
		}

	}

	/**
	 *答对总分加1 单词分数加1 
	 *答错单词分数减1
	 */
	$(".answer").on("click", function() {
		var answerIndex = $(this).attr("index");
		var right = false;
		if (answerIndex == testObj.rightOption) {
			right = true;
		}
		resultHandler(right);
	})
	
	function resultHandler(right){
		if(right){
			testObj.score++;
		}

        var wordId = word.id;

        if (!testObj[wordId]) {//初始化
            testObj[wordId] = {};
            testObj[wordId].score = 0;
            testObj[wordId].type = 0;
        }
        if (right) {
            testObj[wordId].score++;
        } else {
            testObj[wordId].score--;
        }
        testObj[wordId].type++;
        testObj.testNum++;//做过的题数
        msg += '/n' + JSON.stringify(testObj);
        test();
	}

	//播放音频
	function end() {
		$("#play").attr("class", "oper oper-play1");
	}

	function play() {
		var audio = document.getElementById("audio");
		audio.src = "/file/ph_mp3/" + word.phAmMp3;
		audio.play();
		$("#play").attr("class", "oper oper-pause");
		return false;
	}
</script>
</body>
</html>