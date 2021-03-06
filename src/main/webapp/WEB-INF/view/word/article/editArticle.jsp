<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>文章编辑</title>
    <%@ include file="../../include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
        #showForm .am-form-label{
            padding-top:0px;
        }
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">文章信息</div>
                        </div>
                        <div class="widget-body am-fr" id='addDiv'>
                            <form id="addForm" class="am-form tpl-form-border-form" action="${ctx}/article/save" data-am-validator method="post" enctype="multipart/form-data">
                                <input type="hidden" name="id" value="${article.id}" />
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>名称：</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" name="title" minlength="3" placeholder="名称（至少3个字符）"
                                               value="${article.title}" required />
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">mp3：</label>
                                    <div id='mp3_div' class="am-form-group am-form-file">
                                        <i class="am-icon-cloud-upload"></i><span id='mp3_label'> <c:choose><c:when test="${article.mp3 ne null && article.mp3 ne ''}">${article.mp3}</c:when><c:otherwise>请选择</c:otherwise></c:choose></span>
                                        <input type="file" name="mp3" accept="audio/mpeg" multiple id='mp3'>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="submit" class="am-btn am-btn-primary">保存</button>
                                        <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">

$(document).ready(function() {
	
	var file;
    var pullfiles=function(){ 
        var fileInput = document.querySelector("#mp3");
        file = fileInput.files[0];
        if(file != null){
            $("#mp3_label").html(" "+file.name);
        }
    }

    document.querySelector("#mp3").onchange=pullfiles;
    
    if("${article.mp3}"){
        $("#mp3_div").addClass("am-form-success");
    }
    
    //消息提醒
    var msg = '${msg}';
    if(msg!=''){
        showMsg(msg);
        if(msg=="保存成功"){
            closeModel(true);//关闭窗口
        }
    }

});
</script>
</body>
</html>