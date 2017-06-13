<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>增加单词</title>
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
                            <div class="widget-title am-fl">选择单词</div>
                        </div>
                        <div class="widget-body am-fr" id='addDiv'>
                            <form id="addForm" class="am-form tpl-form-border-form" action="${ctx}/sentence/analy" data-am-validator modelAttribute="article" method="post">
                                <input type="hidden" name="id" value="${article.id}" />
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">名称：</label>
                                    <div class="am-u-sm-9">
                                        ${article.title}
                                    </div>
                                </div>
                                
                                <div class="am-u-sm-12">
	                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
	                                    <thead>
	                                    <tr>
	                                        <th>单词</th>
	                                        <th width="10">频率</th>
	                                        <th>所在句子</th>
	                                    </tr>
	                                    </thead>
	                                    <tbody>
	                                    <c:forEach items="${words}" var="word" varStatus="status">
	                                        <tr>
	                                            <td>${word.word}</td>
	                                            <td>${word.count}</td>
	                                            <td>
	                                                
	                                                   ${word.sentence[0]}</br>
	                                                   <c:if test="${word.count>1}">
	                                                   		<a href="#" onclick="return showDetail(${word.word})" title="查看更多"><i class="am-icon-ellipsis-h" aria-hidden="true"></i></a>
		                                                   <span name=${word.word}_span style="display:none">
			                                                   <c:forEach items="${word.sentences}" var="sentence" varStatus="status" >
			                                                   	 <c:if test="${status.index>=1}">
			                                                   	 	${sentence}</br>
			                                                   	 </c:if>
			                                                   </c:forEach>
		                                                   </span>
	                                                   </c:if>
	                                                
	                                            </td>
	                                        </tr>
	                                    </c:forEach>
	                                    </tbody>
	                                </table>
	                            </div>
                                
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="submit" class="am-btn am-btn-primary">提交</button>
                                        <button type="button" class="am-btn am-btn-danger" onclick="history.go(-1)">上一步</button>
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

function showDetail(word){
	alert(word);
	return false;
}

$(document).ready(function() {
	
	$("#showForm").on('click','.word', function (params) {
        $(this).removeClass('word');
        $(this).addClass('newWord');
    });
    
    $("#showForm").on('click','.newWord', function (params) {
        $(this).removeClass('newWord');
        $(this).addClass('word');
    });
    
    //消息提醒
    var msg = '${msg}';
    if(msg!=''){
        showMsg(msg);
        if(msg=="保存成功"){
            closeModel(true);//关闭窗口
        }
    }
    initSelectValue(true);//初始化下拉框的值
	
	var file;
	
	var pullfiles=function(){ 
	    var fileInput = document.querySelector("#mp3");
	    file = fileInput.files[0];
	    if(file != null){
	    	$("#mp3_label").html(" "+file.name);
	    }
	}

	document.querySelector("#mp3").onchange=pullfiles;

	$("#addForm").submit(function(){
		$("#addDiv").hide();
        $("#showDiv [name='title']").html($("#addDiv [name='title']").val());
        var content = $("#addDiv [name='content']").val();
        var html = "";
        var patt=new RegExp("^[a-zA-Z]+$");
        var patt1=new RegExp("^[a-zA-Z]+[.,:;?!]{1}$");
        var patt2=new RegExp("^[.,:;?!]{1}[a-zA-Z]+$");
        var patt3=new RegExp("^[\"\']{1}[a-zA-Z]+[\"\']{1}$");
        var patt4=new RegExp("[a-zA-Z]");
        $.each(content.split(/[.;?!\r]+/),function(){
            html += '<span class="sentence">';
            html += "<span class='original'>"+this+"</span>"
            $.each(this.split(" "),function(){ 
                var word = "";
                var letters = this;
                var available = true;
                $.each(letters.split(""),function(index){
                    
                    if(patt4.test(this)){
                        word += this;
                    }else if(this=='\''&&word.length!=0){//中间有' 前后字母都不要
                        available = false;
                        word += this;
                    }else{ 
                        if(word.length!=0){
                            if(available){
                                html += "<span class=word>"+word+"</span>";
                            }else{
                                html += word;
                            }
                        }
                        available = true;
                        html += this;
                        word = "";
                    }
                    
                    if(index==letters.length-1){
                        if(word.length!=0){
                            if(available){
                                html += "<span class=word>"+word+"</span>";
                            }else{
                                html += word;
                            }
                        }
                        available = true;
                        word = "";
                    }
                })
                html += " ";
            })
            
            html += '</span><br/>';
        })
        $("#showDiv [name='content']").html(html);
        $("#showDiv").show();
        return false;
	});
	
	
    var options = {   
        url: "${ctx}/sentence/save",  
        resetForm: true,   
        dataType: 'json',
        success:function(data){
            Mask.unmaskElement('body'); 
            var status = data["status"];
            if(status=="success"){
                BUI.Message.Alert('保存成功', function(){window.location.href="${ctx}/article/articleList"; },'success');
            }else{
                BUI.Message.Alert(data["msg"]);
            }
        }
    }; 
    
    $("#showForm").submit(function(){
        if(file){
        	if(file.length>102400000){
	        	showMsg("文件不能大于100M");
	        	return false;
        	}else if(file.name.slice(-4)!=".mp3"&&file.name.slice(-4)!=".MP3"){
        		showMsg("请选择mp3文件");
                return false;
        	}
        }
        var hasNew = false;
        var sentenceIndex = 0;
        $("#showDiv .sentence").each(function(index,element) {
            $("#showForm").append($("<input type='hidden' name='sentences["+sentenceIndex+"].content' value='"+HTMLEncode($(this).children('.original').html())+"'/>"));
            var wordIndex = 0;
            $(this).children(".newWord").each(function(wordIndex){
                $("#showForm").append($("<input type='hidden' name='sentences["+sentenceIndex+"].wordList["+wordIndex+"].wordName' value='"+HTMLEncode($(this).html())+"'/>"));
                wordIndex++;
            });
            sentenceIndex++;
            hasNew = true;
        });
        if(hasNew){
            $("#showForm").append($("<input type='hidden' name='title' value='"+$("#showDiv [name='title']").html()+"'/>"));
        }
    });
    
    // 替换特殊字符
    function HTMLEncode(text){
     text = text.replace(/&/g, "&amp;") ;
     text = text.replace(/"/g, "&quot;") ;
     text = text.replace(/</g, "&lt;") ;
     text = text.replace(/>/g, "&gt;") ;
     text = text.replace(/'/g, "&#146;") ;
     text = text.replace(/\ /g,"&nbsp;");
     text = text.replace(/\n/g,"<br>");
     text = text.replace(/\t/g,"&nbsp;&nbsp;&nbsp;&nbsp;");
     return text;
    }

});
</script>
</body>
</html>