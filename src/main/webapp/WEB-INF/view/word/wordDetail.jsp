<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>单词明细</title>
    <%@ include file="../include/head.jsp"%>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<style>
    .tpl-content-wrapper{margin-left:0}
    #play {
        margin-top:5px;
    }
    .ph{
        float:left;
    }
</style>
<audio id = 'audio' onended="end()"></audio>
<div class="tpl-content-wrapper">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="widget-head am-cf">
						<div class="widget-title am-fl">单词明细</div>
					</div>
					<div class="wordBody">
						<font class='wordName'><c:out value='${word.wordName}'></c:out></font>
					    <br /> 
						<div class="ph">[<c:out value='${word.phAm}' />]&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<span id="play" class="oper oper-play1" onclick="return play(${word.id})"></span><br /><br />
				         
				        <div class="am-tabs" data-am-tabs>
						  <ul class="am-tabs-nav am-nav am-nav-tabs">
						    <li class="am-active"><a href="#tab1">解释</a></li>
						    <li><a href="#tab2">例句</a></li>
						    <li><a href="#tab3">原文</a></li>
						  </ul>

						  <div class="am-tabs-bd">
						    <div class="am-tab-panel am-fade am-in am-active" id="tab1">
						        <c:forEach items="${word.acceptations}" var="acceptation">
						            ${acceptation.pos} ${acceptation.acceptation}<br />
						        </c:forEach>
						    </div>
                            <div class="am-tab-panel am-fade" id="tab2">
                                <c:forEach items="${icibaSentence}" var="icibaSentence">
                                    ${icibaSentence.orig}<br />
                                    ${icibaSentence.trans}<br /><br />
                                </c:forEach>
                            </div>
                            <div class="am-tab-panel am-fade" id="tab3">
                                <c:forEach items="${sentences}" var="sentence">
                                    ${sentence.content}<br />
                                </c:forEach>
                            </div>
                        </div>
                    </div>
				         
				        
					</div>
					<div class="am-form-group" style="margin-top:10px">
                        <div class="am-u-sm-9 am-u-sm-push-3">
                                <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
                        </div>
                    </div>
					
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../include/bottom.jsp"%>
<script type="text/javascript">
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