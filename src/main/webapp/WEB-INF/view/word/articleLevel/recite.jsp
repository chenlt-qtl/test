<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>背诵</title>
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
                          <div id='wordContent'>
								<div class="am-progress" style="margin-top: 10px; margin-left: 20px">
									<div id='progressBar' class="am-progress-bar"></div>
								</div>
								<div class="wordBody">
		                        <font class='wordName'><span id="wordName"></span></font>
		                        <br /> 
		                        <div class="ph">[<span id="phAm"></span>]&nbsp;&nbsp;&nbsp;&nbsp;</div>
		                        <span id="play" class="oper oper-play1" onclick="return play()"></span><br /><br />
		                         
		                        <div class="am-tabs" data-am-tabs>
		                          <ul class="am-tabs-nav am-nav am-nav-tabs">
		                            <li class="am-active"><a href="#tab1">解释</a></li>
		                            <li><a href="#tab2">例句</a></li>
		                            <li><a href="#tab3">原文</a></li>
		                          </ul>
		
		                          <div class="am-tabs-bd">
		                            <div class="am-tab-panel am-fade am-in am-active" id="tab1">
		                            </div>
		                            <div class="am-tab-panel am-fade" id="tab2">
		                            </div>
		                            <div class="am-tab-panel am-fade" id="tab3">
		                            </div>
		                        </div>
		                    </div>
		                         
		                    </div>
		                    
                          </div>
                          
                          <button id='button' style="margin-left:310px;margin-top:20px;" type="button" onclick="return getWord()" class="am-btn am-btn-secondary am-round">下一个</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
var words = '${words}';
var wordArr = words.split(',');
var word;
var index = 0;

function getWord(){
	$.ajax({
        traditional : true,
        type : "POST",
        url : "../word/getWord",
        data : {"id":wordArr[index]},
        dataType : "json",
        success : function(result, textStatus) {
        	word = result;
            $("#wordName").html(word.wordName);
            $("#phAm").html(word.phAm);
            $("#tab1").html("");
            $("#tab2").html("");
            $("#tab3").html("");
            $.each(word.acceptations,function(){
            	$("#tab1").append(this.pos+" "+this.acceptation+"<br />");
            })
            
            $.each(word.icibaSentence,function(){
                $("#tab2").append(this.orig+"<br />"+this.trans+"<br /><br />");
            })
            
            $.each(word.sentences,function(){
                $("#tab3").append(this.content+"<br />");
            })
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            Message.errorAlert("Invoke ajax error:" + textStatus + " " + errorThrown);
        }
    }); 
	index ++;
	if(index==wordArr.length){
        $("#button").html("开始测试");
        $("#button").on('click', function (params) {
        	openModel(false,'${ctx}/articleLevel/test?pageNo=${page.pageNo}&pageSize=${page.pageSize}&ids='+words);
        });
    }
	$("#progressBar").css("width",index*100/wordArr.length+"%");
}

getWord();

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