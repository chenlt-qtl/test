<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示文章</title>
<%@ include file="/common/header.jsp"%>
<style type="text/css">
</style>
</head>
<body>
<div id='articleGrid'></div>
<div class='article'>
	<div id='article' class='article-content'>
	</div>
	<div class='bar-div'>
	   <div id="bar" class='bar'></div> 
	</div>
</div>

<div id='wordGrid' style="float:left;"></div>

<audio id = 'audio'></audio>

</body>
<script type="text/javascript">
BUI.use(['bui/form','bui/grid','bui/data','bui/overlay','bui/tooltip','bui/toolbar','bui/mask'],function (Form,Grid,Data,Overlay,Tooltip,Toolbar,Mask) {
	
	 var buttonGroup = new Toolbar.Bar({
         elCls : 'button-group',  //设置工具栏应用的样式
         defaultChildCfg : {
           elCls : 'button button-small' //设置按钮样式
         },
         children : [ //按钮
           {content : '开始背诵',handler : startRecite}
         ],
         render : '#bar'
    });
	buttonGroup.render();
	
	var articleStore = new Data.Store({
		url :"${pageContext.request.contextPath}/article/getArticlesPage",
		pageSize : 5
    });
	
	var articleGrid = new Grid.Grid({
		height:300,
        render:'#articleGrid',
        columns : [ 
			{title : '文章名',width : 300,sortable : false,dataIndex : 'title'},
			{title : '单词数',width : 300,sortable : false,dataIndex : 'wordNum'},
			{title : '操作',width : 300,sortable : false,dataIndex : 'id',
                renderer : function(value, obj) {
                	var str = '<span class="oper oper-delete" objId='+value+'></span>&nbsp;&nbsp;&nbsp;&nbsp;'+
                	   '<span class="oper oper-detail article-detail" objId='+value+'></span>'
                	if(obj["hasMp3"]){
                		str = '<span class="oper oper-play articleMp3" objId='+value+'></span>'+
                		'&nbsp;&nbsp;&nbsp;&nbsp;'+str;
                	}
                	return str;
            }}
		],
        loadMask: true, //加载数据时显示屏蔽层
        store: articleStore,
        // 底部工具栏
        bbar:{
            pagingBar:true
        }
	});
	articleGrid.render();
	articleStore.load();
	
	var wordStore = new Data.Store({
        url :"${ctx}/word/getWordByArticle",
        pageSize : 5
    });
	var wordGrid = new Grid.Grid({
        width:400,
        height:270,
        render:'#wordGrid',
        columns : [ 
            {title : '单词',width : 100,sortable : false,dataIndex : 'wordName'},
            {title : '音标',width : 100,sortable : false,dataIndex : 'phAm'},
            {title : '操作',width : 200,sortable : false,dataIndex : 'id',
            	renderer : function(value, obj) {
                return '<span class="oper oper-play wordMp3" objId='+value+'></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="oper oper-detail word-detail" objId='+value+'></span>';
            }}
        ],
        loadMask: true, //加载数据时显示屏蔽层
        store: wordStore,
        // 底部工具栏
        bbar:{
            pagingBar:true
        }
    });
	wordGrid.render();
	
	$(".wordMp3").live("click",function(){
		var wordId = $(this).attr("objId");
		var audio = document.getElementById("audio");
		audio.src = "${ctx}/word/getMp3?id="+wordId;
		audio.play();
	})
	
	$(".articleMp3").live("click",function(){
        var articleId = $(this).attr("objId");
        var audio = document.getElementById("audio");
        audio.src = "${ctx}/article/getMp3?id="+articleId;
        audio.play();
    })
	
	$(".article-detail").live("click",function(){
        var articleId = $(this).attr("objId");
        Util.doAjaxPost("${ctx}/sentence/getContent",{id:articleId},function(text){
            var content = jQuery.parseJSON(text)["content"];
            $("#article").html(content);
        });
        wordStore.load({articleId:articleId});
    })
    
    $(".word-detail").live("click",function(){
        var wordId = $(this).attr("objId");
        window.open("${ctx}/word/detail?id="+wordId);
    })
    
    $(".oper-delete").live("click",function(){
    	var articleId = $(this).attr("objId");
    	BUI.Message.Confirm('确认要删除吗？',function(){
    		Mask.maskElement('body',"删除中...");
	        Util.doAjaxPost("${ctx}/article/delete",{id:articleId},function(text){
	        	Mask.unmaskElement('body'); 
	        	var status = jQuery.parseJSON(text)["status"];
	            if(status=="success"){
	                BUI.Message.Alert('操作成功');
	                articleStore.load();
	            }else{
	                BUI.Message.Alert(data["msg"]);
	            }
	        });
    	});
    })
    
    function startRecite(){
		var record = articleGrid.getSelected();
		window.open("${ctx}/article/recite?id="+wordId);
	}
	
});
</script>
</html>