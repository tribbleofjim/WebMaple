document.getElementById("username").innerHTML = "用户：" + decodeURIComponent(getCookieByKey("user"));

var gotAuths = [
    {"value": "1", "title": "server1", "disabled": "", "checked": ""}
    ,{"value": "2", "title": "mysql1", "disabled": "", "checked": ""}
    ,{"value": "3", "title": "server2", "disabled": "", "checked": ""}
    ,{"value": "4", "title": "mongo1", "disabled": "", "checked": ""}
    ,{"value": "5", "title": "redis1", "disabled": "", "checked": ""}
    ,{"value": "6", "title": "redis2", "disabled": "", "checked": ""}
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