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
                            <form id="addForm" class="am-form tpl-form-border-form" action="return trans();" data-am-validator modelAttribute="article" method="post">
                                <input type="hidden" name="id" value="${article.id}" />
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>名称：</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" name="title" minlength="3" placeholder="名称（至少3个字符）"
                                               value="${article.title}" required />
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">内容：</label>
                                    <div class="am-u-sm-9">
                                        <textarea name="content" class="" rows="5">${article.content}</textarea>
                                    </div>
                                </div>
                                <div class="am-form-group">
	                                <div class="am-u-sm-9 am-u-sm-push-3">
	                                    <button type="button" id='trans' class="am-btn am-btn-primary">提交</button>
	                                    <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
	                                </div>
	                            </div>
                            </form>
                        </div>
                        
                        <div class="widget-body am-fr" id='showDiv' style='display:none'>
                            <form id="showForm" class="am-form tpl-form-border-form" data-am-validator modelAttribute="article" action="${ctx}/article/<c:choose><c:when test="${empty article.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                                <input type="hidden" name="id" value="${article.id}" />
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">名称：</label>
                                    <div class="am-u-sm-9">
                                        <span name="title"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">内容：</label>
                                    <div class="am-u-sm-9">
                                        <span name="content"></span>
                                    </div>
                                </div>
                                <div class="am-form-group am-form-file">
								  <i class="am-icon-cloud-upload"></i> 选择要上传的文件
								  <input type="file" multiple>
								</div
                            </form>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="button" id='add' class="am-btn am-btn-primary">保存1</button>
                                    <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
                                </div>
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
$(document).ready(function() {
//    $('#trans').on('click', function (params) {
	function trans(){
	    alert(1);		
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
                
                html += '</span>';
            })
            $("#showDiv [name='content']").html(content+"<br\/><br\/><br\/>"+html);
            $("#showDiv").show();
            $(".word").on('click', function (params) {
                $(this).removeClass('word');
                $(this).addClass('newWord');
            });
            $(".newWord").on('click', function (params) {
                $(this).removeClass('newWord');
                $(this).addClass('word');
            });
    }
    
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
    
    $('#add').on('click', function (params) {
    	alert(1);
        Mask.maskElement('body',"保存中...");
        var hasNew = false;
        var sentenceIndex = 0;
        $("#showDiv .sentence").each(function(index,element) {
            $("#newWordForm").append($("<input name='sentences["+sentenceIndex+"].content' value='"+HTMLEncode($(this).children('.original').html())+"'/>"));
            var wordIndex = 0;
            $(this).children(".newWord").each(function(wordIndex){
                $("#newWordForm").append($("<input name='sentences["+sentenceIndex+"].wordList["+wordIndex+"].wordName' value='"+HTMLEncode($(this).html())+"'/>"));
                wordIndex++;
            });
            sentenceIndex++;
            hasNew = true;
        });
        if(hasNew){
            $("#newWordForm").append($("<input name='title' value='"+$("#showDiv [name='title']").html()+"'/>"));
            $("#newWordForm").ajaxSubmit(options); 
        }else{
            $("#newWordForm").children().each(function(){$(this).remove();});
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
    //还原特殊字符
    function TEXTAREAcode(text){
     text = text.replace(/\n/g,"");
     text = text.replace(/&amp;/g, "&") ;
     text = text.replace(/&quot;/g, "\"") ;
     text = text.replace(/&lt;/g, "<") ;
     text = text.replace(/&gt;/g, ">") ;
     text = text.replace(/&#146;/g, "\'") ;
     text = text.replace(/&nbsp;/g," ");
     text = text.replace(/<br>/g,"\n");
     return text;
    }
});
</script>
</body>
</html>