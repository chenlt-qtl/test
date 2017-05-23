<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加文章</title>
<%@ include file="/common/header.jsp"%>
<script src='<c:url value="/common/jquery.form.min.js"/>'></script>
</head>
<body>
	<div id='addDiv'>
		<form id='articleForm'>
			名称:<input type='text' name="title" value='123' data-rules="{required:true}" /><br/>
			内容：<textarea name='content' value=" I'll be fine, alright? Really, everyone. I hope she'll be very happy. "  data-rules="{required:true}" ></textarea><br/>
			<input id='trans' type='button' value='提交'>
		</form>
	</div>
	<div id='showDiv' style='display:none'>
		名称:<span name="title"></span><br/>
		内容：<span name="content"></span><br/>
		<form id='newWordForm' method="post" enctype="multipart/form-data">
		  <input id='mp3' type='file' accept="audio/mpeg" name="mp3"/>
		</form>
		<input id='add' type='button' value='加入生词本'>
	</div>
	
</body>
<script type="text/javascript">
//window.location="${pageContext.request.contextPath}/page/chart.html";
$(function(){
	var articleForm;
	BUI.use(['bui/form'],function (Form) {
		articleForm = new Form.Form({srcNode : '#articleForm'});
		articleForm.render();
	})
	$('#trans').on('click', function (params) {
		if(articleForm.isValid()){
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
	
	$('#add').on('click', function (params) {
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
})
</script>
</html>