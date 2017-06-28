<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>增加单词</title>
    <%@ include file="../../include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
<div id='a'></div>
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <form id="addForm" class="am-form tpl-form-border-form" action="${ctx}/sentence/save" data-am-validator modelAttribute="article" method="post" enctype="multipart/form-data">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">名称：${article.title}</div>
                            </br>
                            </br>
                            <div class="am-form-group am-form-file">mp3：
							  <i class="am-icon-cloud-upload"></i><span id='mp3_label'> <c:choose><c:when test="${1==article.hasMp3}">已选择</c:when><c:otherwise>请选择</c:otherwise></c:choose></span>
                              <input type="file" name="mp3" accept="audio/mpeg" multiple id='mp3'>
							</div>
                        </div>
                        <div class="widget-body am-fr" id='addDiv'>
                                <input type="hidden" name="id" value="${article.id}" />
                                <input type="hidden" name="title" value="${article.title}" />
                                <input type="hidden" name="content" value="${article.content}" />
                                <c:forEach items="${sentences}" var="sentence" varStatus="status" > 
                                    <input type="hidden" name="sentences[${status.index}].content" value="${sentence}" />
                                </c:forEach>
                                
                                <div class="am-list-news-bd">
                                    <div class="am-u-sm-12">
	                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
	                                    <thead>
	                                    <tr>
	                                        <th width="20"><input name="checkboxall" type="checkbox" style="margin-top: -17px;" /></th>
	                                        <th width="100">单词</th>
	                                        <th width="50">频率</th>
	                                        <th width="200">所在句子</th>
	                                    </tr>
	                                    </thead>
	                                    <tbody>
	                                    <c:forEach items="${words}" var="word" varStatus="status">
	                                        <tr>
	                                            <td>
	                                               <input name="checkbox" type="checkbox" value="${word.word}" />
	                                               <input type="hidden" value="${word.sentences.toString()}" />
	                                            </td>
	                                            <td>${word.word}</td>
	                                            <td>${word.count}</td>
	                                            <td>
	                                               ${sentences[word.sentences[0]]}
	                                               <c:if test="${word.count>1}">
	                                                   <div name='${word.word}_span' style="display:none;">
                                                            <c:forEach items="${word.sentences}" var="sentence" varStatus="status" > 
	                                                            <c:if test="${status.index>=1}">
	                                                                </br>${sentences[sentence]}</br>
	                                                            </c:if>
	                                                        </c:forEach>
                                                       </div>
                                                       <a href="javascript:;" onclick="showDetail('${word.word}')" title="查看更多"><div name='${word.word}_icon' class="am-icon-angle-down"></div></a>
	                                               </c:if>
	                                            </td>
	                                        </tr>
	                                    </c:forEach>
	                                    </tbody>
	                                </table>
	                            </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="submit" class="am-btn am-btn-primary">提交</button>
                                        <button type="button" class="am-btn am-btn-danger" onclick="history.go(-1)">上一步</button>
                                        <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
                                    </div>
                                </div>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">

function showDetail(word){
	$("div [name='"+word+"_span']").toggle();
	var icon_class = $("div [name='"+word+"_icon']").attr("class");
	if(icon_class=="am-icon-angle-up"){
		$("div [name='"+word+"_icon']").attr("class","am-icon-angle-down");
	}else{
	   $("div [name='"+word+"_icon']").attr("class","am-icon-angle-up");
    }
}

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
	
    //消息提醒
    var msg = '${msg}';
    if(msg!=''){
        showMsg(msg);
        if(msg=="保存成功"){
            closeModel(true);//关闭窗口
        }
    }
    
    //多选按钮的全选和反选
    $("input[name='checkboxall']").click(function(){
        $("input[name='checkbox']").prop("checked",$(this).is(":checked"));
    });
	
	$("#addForm").submit(function(){
		if(file){
            if(file.length>102400000){
                showMsg("文件不能大于100M");
                return false;
            }else if(file.name.slice(-4)!=".mp3"&&file.name.slice(-4)!=".MP3"){
                showMsg("请选择mp3文件");
                return false;
            }
        }

		var hasSelect = false;
		var index = 0;
        $("input[name='checkbox']:checked").each(function(){
        	$("#addForm").append("<input type='hidden' name='words["+index+"].wordName' value='"+$(this).val()+"'></input>");
        	$("#addForm").append("<input type='hidden' name='words["+index+"].sentenceIndexs' value='"+$(this).next().val()+"'></input>");
        	hasSelect = true;
        	index++;
        });
        if(!hasSelect){
            showMsg('请勾选要增加的单词');
            return false;
        }
        
	});

});
</script>
</body>
</html>