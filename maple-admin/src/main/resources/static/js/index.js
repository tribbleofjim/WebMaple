layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let carousel = layui.carousel;
    let $ = layui.jquery;

    $.ajax({
        type: 'get',
        url: "basicData",
        data: {},
        success: function (res) {
            document.getElementById("nodeNum").innerHTML = res.model.nodeNum;
            document.getElementById("spiderNum").innerHTML = res.model.spiderNum;
            document.getElementById("timedJobNum").innerHTML = res.model.timedJobNum;
        }
    });

    $.ajax({
        type: 'get',
        url: "advance",
        data: {},
        success: function (res) {
            document.getElementById("nodeNum").innerHTML = res.model.nodeNum;
            document.getElementById("spiderNum").innerHTML = res.model.spiderNum;
            document.getElementById("timedJobNum").innerHTML = res.model.timedJobNum;
        }
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