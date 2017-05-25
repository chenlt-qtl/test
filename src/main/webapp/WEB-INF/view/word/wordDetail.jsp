<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单词明细</title>
<%@ include file="/common/header.jsp"%>
</head>
<body>
<audio id = 'audio'></audio>
 <font class='wordName'><c:out value='${requestScope.word.wordName}'/></font><span class="oper oper-play1"></span><br/>
[<c:out value='${requestScope.word.phAm}'/>]<br/>
<c:forEach items="${requestScope.word.acceptations}" var="acceptation">
    ${acceptation.pos} ${acceptation.acceptation}<br/>
</c:forEach>

<script type="text/javascript">
$(".oper-play").live("click",function(){
    var wordId = "<c:out value='${requestScope.word.id}'/>";
    var audio = document.getElementById("audio");
    audio.src = "${ctx}/word/getMp3?id="+wordId;
    audio.play();
})
</script>
</body>
</html>