var form, layer, element, $;
layui.use(['form', 'layer', 'jquery', 'element'], function () {
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    $ = layui.jquery;
});

function editNickname() {
    var phone = decodeURIComponent(getCookieByKey('user'));
    layer.open({
        type:1,
        area:['400px','300px'],
        title: '修改用户昵称',
        content: $("#nicknameForm"),
        shade: 0,
        btn: '提交'
        ,yes: function(index, layero) {
            var nickname = document.getElementById("newNick").value;
            var password = document.getElementById("editNickPass").value;
            $.ajax({
                type: 'get',
                url: "modifyNickname",
                data: {
                    phone: phone,
                    nickname: nickname,
                    password: password
                },
                success: function (res) {
                    layer.msg(res.message);
                }
            });
            return true;
        },
        cancel: function(layero,index){
            layer.closeAll();
        }
    });
}

function editPassword() {
    var phone = decodeURIComponent(getCookieByKey('user'));
    layer.open({
        type:1,
        area:['400px','300px'],
        title: '修改密码',
        content: $("#passForm"),
        shade: 0,
        btn: '提交'
        ,yes: function(index, layero) {
            var oldPass = document.getElementById("oldPass").value;
            var newPass = document.getElementById("newPass").value;
            $.ajax({
                type: 'get',
                url: "modifyPassword",
                data: {
                    phone: phone,
                    oldPass: oldPass,
                    newPass: newPass
                },
                success: function (res) {
                    layer.msg(res.message);
                }
            });
            return true;
        },
        cancel: function(layero,index){
            layer.closeAll();
        }
    });
}

function getCookieByKey(name){
var cookies = document.cookie.split(';');
var c;
for(var i=0; i<cookies.length ; i++){
        c = cookies[i].split('=');
        if (c[0].replace(' ', '') == name) {
            return c[1];
        }
    }
}
//var r = decodeURIComponent(getCookieByArray('user'));
//alert(r);

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