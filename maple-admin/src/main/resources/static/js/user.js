var form, layer, element, $;
layui.use(['form', 'layer',' jquery', 'element'], function () {
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    $ = layui.jquery;
});

function editNickname() {
    
}

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('visits', 'dark'));

// 指定图表的配置项和数据
var option = {
    color: ['#008B8B'],
    title: {
        text: '用户一周内日访问量'
    },
    tooltip: {},
    legend: {
        data:['访问次数']
    },
    xAxis: {
        data: ["Sun","Mon","Tue","Wen","Thu","Fri","Sat"]
    },
    yAxis: {},
    series: [{
        name: '访问次数',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20, 16]
    }]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);