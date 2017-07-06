<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
	<title>文章管理</title>
	<%@ include file="../../include/head.jsp" %>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
<div class="am-g tpl-g">
    <!-- 内容区域 -->
	                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">我的文章</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="sys:user:create">
                                            <button type="button" class="am-btn am-btn-default am-btn-success"
                                                    onclick="openModel(false,'${ctx}/article/my/listOther')"><span class="am-icon-plus"></span> 增加
                                            </button></shiro:hasPermission>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/article/articleList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="tagsinput">
                                            <c:if test="${not empty page.entity.title}"><span class="tags"><input type="hidden" name="title" value="${page.entity.title}" />标题=${page.entity.title} <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span></c:if>
                                                <span class="am-select am-input-group-sm">
                                                     <input type="text" class="am-select-input" autocomplete="off" style="border: none;"
                                                            placeholder="关键字" am-data='[{"field":"title","desc":"标题","type":"string"}]'>
                                                    <ul class="am-select-ul"></ul>
                                                </span>
                                        </div>
                                        <span class="am-input-group-btn">
                                        <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit" onclick="initSearchForm()"></button>
                                      </span>
                                    </div>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                        <th>文章名</th>
                                        <th>单词数</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="article" varStatus="status">
                                        <tr>
                                            <td>${article.title}</td>
                                            <td>${article.wordNum}</td>
                                            <td>
                                                
                                                <c:if test="${article.mp3 ne null && article.mp3 ne ''}">
                                                    <a href="/file/mp3/${article.mp3}"
                                                       onclick="return play(this.href,this)" title="发音"><span class="am-icon-volume-up"></span></a>
                                                </c:if>
                                                <a href="${ctx}/articleUserRel/delete?id=${article.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                                                       onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span
                                                            class="am-text-danger am-icon-trash-o"></span></a>
                                                <a href="#" onclick="openModel(false,'${ctx}/sentence/getContent?id=${article.id}&title=${article.title}&pageNo=${page.pageNo}&pageSize=${page.pageSize}')" 
                                                        title="查看明细"><span class="am-text-primary am-icon-search"></span></a>
                                                <a href="#" onclick="openModel(false,'${ctx}/articleLevel/level?id=${article.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}')" 
                                                        title="开始背诵"><span class="am-text-success am-icon-play"></span></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp" %>
                            </div>
                      </div>
                    </div>
                </div>
                
	<div class='article'>
		<div id='article' class='article-content'>
		</div>
		<div class='bar-div'>
		   <div id="bar" class='bar'></div> 
		</div>
	</div>
	
	<div id='wordGrid' style="float:left;"></div>
	
	<audio id = 'audio'></audio>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if (msg != '') {
            showMsg(msg);
        }
    });
</script>
<script type="text/javascript">
    
    function play(href,a){
		var span = $(a).children("span");
		var audio = document.getElementById("audio");
		if($(span).attr("class")=="oper oper-pause"){
			audio.pause();
	        $(span).attr("class","oper am-icon-volume-up");
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