<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>文章编辑</title>
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
                          <div id='wordContent' style="display:none;">
                             <div class="wordBody">
		                        <font class='wordName'><div id="wordName"></div></font>
		                        <br /> 
		                        <div class="ph">[<div id="phAm">]&nbsp;&nbsp;&nbsp;&nbsp;</div>
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
		                    
                            <div class="am-progress">
							  <div id='progressBar' class="am-progress-bar"></div>
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
var words = '${words}';
$("#progressBar").css("width",'${index}'/words.split(',').length);

function end(){
    $("#play").attr("class","oper oper-play1");
}

function play(id){
    var audio = document.getElementById("audio");
    audio.src = "${ctx}/word/getMp3?id="+id;
    audio.play();
    $("#play").attr("class","oper oper-pause");
    return false;
}
</script>
</body>
</html>