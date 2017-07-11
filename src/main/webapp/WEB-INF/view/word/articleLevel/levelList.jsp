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
                            <div class="widget-title am-fl">${article.title}</div>
                        </div>
                        <div id='level'>
	                        <c:forEach var="i" begin="1" end="${totalLevel}" step="1">   
								<c:set var='level' value="${totalLevel-i+1}"/>
								<c:set var='star' value="0"/>
								<c:if test="${levels[level] ne null}">
								    <c:set var='star' value="${levels[level].star}"/>
								</c:if> 
								<a href="#" onclick="openModel(false,'${ctx}/articleLevel/editLevel?articleId=${article.id}&level=${level}<c:if test="${levels[level] ne null}">&id=${levels[level].id}</c:if> ')" >${level}</a>
								<c:forEach var="ii" begin="1" end="3" step="1"> 
								    <c:if test="${star>=ii}">
								        <i class="am-icon-star" aria-hidden="true"></i>
								    </c:if>  
								    <c:if test="${star<ii}">
                                        <i class="am-icon-star-o" aria-hidden="true"></i>
                                    </c:if>  
								</c:forEach>
								
								<br>   
							</c:forEach>  
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
var totalLevel = '${totalLevel}';

</script>
</body>
</html>