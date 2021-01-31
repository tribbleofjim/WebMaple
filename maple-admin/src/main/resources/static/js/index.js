layui.use(['form', 'layer','table', 'jquery', 'element','carousel'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let carousel = layui.carousel;
    let $ = layui.jquery;

    carousel.render({
        elem: '#carousel'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //始终显示箭头
    });
});

var myChart = echarts.init(document.getElementById('visits', 'dark'));
var option = {
    color: ['#008B8B'],
    xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [150, 230, 224, 218, 135, 147, 260],
        type: 'line'
    }]
};
option && myChart.setOption(option);