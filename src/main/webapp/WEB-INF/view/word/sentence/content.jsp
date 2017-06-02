<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>文章明细</title>
    <%@ include file="../../include/head.jsp"%>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<style>
    .tpl-content-wrapper{margin-left:0}
    .am-u-sm-9{
        padding-top:5px;
    }
</style>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">文章明细</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator method="post">
                                <input type="hidden" name="id" value="${articleId}" />
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">名称：</label>
                                    <div class="am-u-sm-9">
                                        ${title}
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">内容：</label>
                                    <div class="am-u-sm-9">
                                        ${content}
                                    </div>
                                </div>
                        </div>
                        
                        <div class="am-u-sm-12">
                            <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                <thead>
                                <tr>
                                    <th>单词</th>
                                    <th>音标</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${words}" var="word" varStatus="status">
                                    <tr>
                                        <td>${word.wordName}</td>
                                        <td>${word.phAm}</td>
                                        <td>
                                            <a href="${ctx}/word/getMp3?id=${word.id}" onclick="return play(this.href,this)" title="发音"><span
                                                class="oper oper-play"></span></a>
                                            <a href="${ctx}/word/detail?id=${word.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                                                onclick="openModel(false,this.href)" title="查看明细"><span class="am-text-primary am-icon-search"></span></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="am-form-group">
                            <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<audio id = 'audio' onended="end()"></audio>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">

var span;

$(document).ready(function() {
    //消息提醒
    var msg = '${msg}';
    if(msg!=''){
        showMsg(msg);
        if(msg=="保存成功"){
            closeModel(true);//关闭窗口
        }
    }

});

function end(){
	if(span){
	    $(span).attr("class","oper oper-play");
	}
}

function play(href,a){
	var audio = document.getElementById("audio");
    span = $(a).children("span");
    if($(span).attr("class")=="oper oper-pause"){
        audio.pause();
        $(span).attr("class","oper oper-play");
    }else{
        audio.src = href;
        audio.play();
        $(span).attr("class","oper oper-pause");
    }
    return false;
}
</script>
</body>
</html>