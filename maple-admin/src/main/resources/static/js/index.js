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
            if (res.success) {
                showAdvance(res.model);
            } else {
                layer.msg(res.message);
            }
        }
    });

    function showAdvance(data) {
        var th = document.getElementById("advance");
        for (idx in data) {
            th.insertAdjacentHTML("beforeEnd", advanceHTML(data[idx], idx));
        }
    }

    function advanceHTML(advance, idx) {
        var html = '<th>';
        html += advance.site;
        var percent = (advance.temp / advance.pageNum) * 100;
        html += '<div class="layui-progress layui-progress-big" lay-showPercent="true">' +
                     '<div class="layui-progress-bar layui-bg-blue" lay-percent="' + percent + '%"></div>' +
                 '</div></th>';
        return html;
    }
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