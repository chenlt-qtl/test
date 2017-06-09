<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>文章编辑</title>
    <%@ include file="../../include/head.jsp"%>
    <script src="${ctxStatic}/3rd-lib/echarts/echarts.min.js"></script>
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
                        <div id="main" style="width: 600px;height:400px;"></div>
                        <div style='position:absolute; left:400px; top:230px;'>
                                                                                            第${level}关<br/>
                                                                                            共${totalLevel}关
                        </div>
                        <button style="margin-left:350px;" type="button" onclick="openModel(false,'${ctx}/articleLevel/levelList?id=${article.id}&title=${article.title}&pageNo=${page.pageNo}&pageSize=${page.pageSize}')" class="am-btn am-btn-secondary am-round">开始闯关</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('main'));

option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        show: false
    },
    series: [
        {
            type:'pie',
            radius: ['50%', '70%'],
            center : ['400', '200'],
            avoidLabelOverlap: false,
            animation:false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: false,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            tooltip:{
            	show:false
            },
            color:['#91c7ae','#d48265'],
            data:[
                {value:'${totalLevel}', name:'已完成'},
                {value:'${level}', name:'未完成'}
            ]
        }
    ]
};
myChart.setOption(option);
</script>
</body>
</html>