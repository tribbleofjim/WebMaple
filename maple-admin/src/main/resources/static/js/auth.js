document.getElementById("username").innerHTML = "用户：" + decodeURIComponent(getCookieByKey("user"));

var gotAuths = [
    {"value": "1", "title": "server1", "disabled": "", "checked": "", "type": "server"}
    ,{"value": "2", "title": "mysql1", "disabled": "", "checked": "", "type": "db"}
    ,{"value": "3", "title": "server2", "disabled": "", "checked": "", "type": "server"}
    ,{"value": "4", "title": "mongo1", "disabled": "", "checked": "", "type": "db"}
    ,{"value": "5", "title": "redis1", "disabled": "", "checked": "", "type": "db"}
    ,{"value": "6", "title": "redis2", "disabled": "", "checked": "", "type": "db"}
]

var value = ["4", "5", "6"]

var layer,form, element, transfer, $;
layui.use(['layer', 'form', 'jquery', 'element', 'transfer'], function () {
    layer = layui.layer;
    element = layui.element;
    transfer = layui.transfer;
    form = layui.form;
    $ = layui.jquery;

    transfer.render({
        elem: '#sourceAuths'  //绑定元素
        ,title: ['未获取权限', '已获取权限']
        ,data: gotAuths
        ,value: value
        ,id: 'sourceAuths' //定义索引
    });
});

showSources({
    target: 'sources'
    , data: gotAuths
});

function showSources(param) {
    var target = document.getElementById(param.target);
    var data = param.data;
    for (idx in data) {
        console.log(data[idx]);
        target.insertAdjacentHTML("beforeEnd", sourceHTML(data[idx]));
    }
}

function sourceHTML(source) {
    var html;
    if (source.type === 'server') {
        html = '<div class="layui-col-md4">\n'+
                    '<div class="layui-card" style="height: 150px">\n'+
                    '<div class="layui-card-header">\n'+
                        '<i class="layui-icon layui-icon-console" style="font-size: 30px;"></i>服务器</div>\n'+
                    '<div class="layui-card-body">\n'+
                        '<div>ip地址：<span>101.37.89.120</span></div>\n'+
                        '<div>服务器类型：<span>admin、worker</span></div>\n'+
                        '<div>\n'+
                            '<button type="button" class="layui-btn layui-btn-radius layui-btn-sm">\n'+
                                '<i class="layui-icon layui-icon-ok"></i> 您有权限\n'+
                            '</button>\n'+
                            '<button type="button" class="layui-btn layui-btn-radius layui-btn-normal layui-btn-sm">\n'+
                                '<i class="layui-icon layui-icon-key"></i> 查看信息\n'+
                            '</button></div></div></div></div>'

    } else if (source.type === 'db') {
        html = '<div class="layui-col-md4">\n'+
                '<div class="layui-card" style="height: 150px">\n'+
                '<div class="layui-card-header">\n'+
                    '<i class="layui-icon layui-icon-template-1" style="font-size: 30px;"></i>数据库</div>\n'+
                '<div class="layui-card-body">\n'+
                    '<div>ip地址：<span>101.37.89.120</span></div>\n'+
                    '<div>数据库类型：<span>mongodb</span></div>\n'+
                    '<div>\n'+
                        '<button type="button" class="layui-btn layui-btn-radius layui-btn-sm">\n'+
                            '<i class="layui-icon layui-icon-ok"></i> 您有权限\n'+
                        '</button>\n'+
                        '<button type="button" class="layui-btn layui-btn-radius layui-btn-normal layui-btn-sm">\n'+
                            '<i class="layui-icon layui-icon-key"></i> 查看信息\n'+
                        '</button></div></div></div></div>'
    } else {
        console.log("无效的资源类型:" + source.type);
    }
    return html;
}

function addSource() {
    layer.open({
        type:1,
        area:['700px','500px'],
        title: '添加资源',
        content: $("#addForm"),
        shade: 0,
        btn: '提交'
        ,yes: function(index, layero) {
            let sourceName = document.getElementById("sourceName").value;
            let sourceType = document.getElementById("sourceType").value;
            let sourceIp = document.getElementById("sourceIp").value;
            let account = document.getElementById("account").value;
            let pass = document.getElementById("pass").value;
            $.ajax({
                type: 'post',
                url: "addSource",
                data: {
                    sourceName: sourceName,
                    sourceType: sourceType,
                    sourceIp: sourceIp,
                    account: account,
                    pass: pass
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

function editAuth() {
    layer.open({
        type:1,
        area:['570px','530px'],
        title: '用户授权',
        content: $("#editAuth"),
        shade: 0,
        btn: '提交'
        ,yes: function(index, layero) {
            $.ajax({
                type: 'get',
                url: "editAuth",
                data: {

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