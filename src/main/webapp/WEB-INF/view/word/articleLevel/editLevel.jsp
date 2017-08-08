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
                            <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                <thead>
                                <tr>
                                    <th>单词</th>
                                    <th>音标</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${wordList}" var="word" varStatus="status">
                                    <tr>
                                        <td>${word.wordName}<input name='wordId' type='hidden' value='${word.id}'/></td>
                                        <td>${word.phAm}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <button style="margin-left:310px;" type="button" onclick="return recite()" class="am-btn am-btn-secondary am-round">开始背诵</button> 
                            
                            <button style="margin-left:30px;" type="button" onclick="return test()" class="am-btn am-btn-secondary am-round">开始测试</button>
                     </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
function recite(){
	var words = '';
	$("tbody").find("[name='wordId']").each(function(){
		if(words.length!=0){
			words += ","
		}
		words += $(this).val();
	});
	openModel(false,'${ctx}/articleLevel/recite?pageNo=${page.pageNo}&pageSize=${page.pageSize}&index=1&words='+words);
}

function test(){
    var words = '';
    $("tbody").find("[name='wordId']").each(function(){
        if(words.length!=0){
            words += ","
        }
        words += $(this).val();
    });
    openModel(false,'${ctx}/articleLevel/test?pageNo=${page.pageNo}&pageSize=${page.pageSize}&ids='+words);
}

</script>
</body>
</html>