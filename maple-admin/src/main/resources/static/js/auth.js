var userId = decodeURIComponent(getCookieByKey("user"));
var auth = false;
document.getElementById("username").innerHTML = "用户：" + userId;

// auth : 0-有权限，1-无权限（0以外都算无权限）
var gotAuths = [
    {"value": "1", "title": "server1", "disabled": "", "checked": "", "auth": true, "type": "server", "ip": "142.43.43.59", "stype": "worker", "user": "root", "password":"123456"}
    ,{"value": "2", "title": "mysql1", "disabled": "", "checked": "", "auth": false,"type": "db", "ip": "142.43.43.60", "stype": "mysql","user": "root", "password":"123456"}
    ,{"value": "3", "title": "server2", "disabled": "", "checked": "", "auth": true,"type": "server", "ip": "142.43.43.61", "stype": "worker","user": "root", "password":"123456"}
    ,{"value": "4", "title": "mongo1", "disabled": "", "checked": "", "auth": true,"type": "db", "ip": "142.43.43.73", "stype": "mongodb","user": "root", "password":"123456"}
    ,{"value": "5", "title": "redis1", "disabled": "", "checked": "", "auth": true,"type": "db", "ip": "142.43.43.249", "stype": "redis","user": "root", "password":"123456"}
    ,{"value": "6", "title": "redis2", "disabled": "", "checked": "", "auth": false,"type": "db", "ip": "142.43.413.59", "stype": "redis","user": "root", "password":"123456"}
]

var value = ["4", "5", "6"]

var layer,form, element, transfer, $;
layui.use(['layer', 'form', 'jquery', 'element', 'transfer'], function () {
    layer = layui.layer;
    element = layui.element;
    transfer = layui.transfer;
    form = layui.form;
    $ = layui.jquery;

    $.ajax({
        type: 'get',
        url: "searchUserAuth",
        data: {
            phone: userId
        },
        success: function (res) {
            if (res.success) {
                if (res.model.isCommander) {
                    auth = true;
                }
                gotAuths = res.model.sourceAuthViews;
                showSources({
                    target: 'sources'
                    , data: gotAuths
                });
            } else {
                layer.msg(res.message);
            }
        }
    });

    transfer.render({
        elem: '#sourceAuths'  //绑定元素
        ,title: ['未获取权限', '已获取权限']
        ,data: gotAuths
        ,value: value
        ,id: 'sourceAuths' //定义索引
    });
});

function showSources(param) {
    var target = document.getElementById(param.target);
    var data = param.data;
    for (idx in data) {
        // console.log(data[idx]);
        target.insertAdjacentHTML("beforeEnd", sourceHTML(data[idx], idx));
    }
}

function sourceHTML(source, idx) {
    var html;
    var sourceType = (source.type === 'server') ? '服务器' : '数据库';
    var isServer = (source.type === 'server') ? true : false;
    html = '<div class="layui-col-md4">\n'+
            '<div class="layui-card" style="height: 150px">\n'+
            '<div class="layui-card-header">\n';
    if (isServer) {
        html += '<i class="layui-icon layui-icon-console" style="font-size: 30px;"></i>'+ sourceType +'</div>\n';
    } else {
        html += '<i class="layui-icon layui-icon-template-1" style="font-size: 30px;"></i>'+ sourceType +'</div>\n';
    }

    if (source.auth) {
        html += '<div class="layui-card-body">\n'+
            '<div>ip地址：<span>' + source.ip + '</span></div>\n'+
            '<div>' + source.type + '类型：<span>' + source.stype + '</span></div>\n'+
            '<div>\n'+
                '<button type="button" class="layui-btn layui-btn-radius layui-btn-sm">\n'+
                    '<i class="layui-icon layui-icon-ok"></i> 您有权限\n'+
                '</button>\n'+
                '<button type="button" class="layui-btn layui-btn-radius layui-btn-normal layui-btn-sm" '
                + 'id = "'+ idx + '"' + 'onclick = "sourceInfo(this.id)"' + '>\n'+
                    '<i class="layui-icon layui-icon-key"></i> 查看信息\n'+
                '</button></div></div></div></div>';

    } else {
        html += '<div class="layui-card-body">\n'+
            '<div>ip地址：<span>' + source.ip + '</span></div>\n'+
            '<div>' + source.type + '类型：<span>' + source.stype + '</span></div>\n'+
            '<div>\n'+
                '<button type="button" class="layui-btn layui-btn-radius layui-btn-danger layui-btn-sm">\n' +
                    '<i class="layui-icon layui-icon-close"></i> 您没有权限\n' +
                '</button>\n' +
                '<button type="button" class="layui-btn layui-btn-radius layui-btn-disabled layui-btn-sm">\n' +
                    '<i class="layui-icon layui-icon-key"></i> 查看信息\n' +
                '</button></div></div></div></div>'
    }
    return html;
}

function sourceInfo(id) {
    var idx = parseInt(id);
    console.log(gotAuths[idx]);
    var data = gotAuths[idx];
    layer.open({
        type:1,
        title:'资源信息展示',
        content: '用户名:' + data.user + ', 密码:' + data.password
    });
}

function addSource() {
    if (!auth) {
        layer.msg("您不是管理员，没有添加资源权限");
        return;
    }
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
    if (!auth) {
        layer.msg("您不是管理员，不能添加用户权限");
        return;
    }
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

function searchUser() {
    var userId = document.getElementById("searchUser").value;
    if (userId) {
        $.ajax({
            type: 'get',
            url: "searchUserAuth",
            data: {
                phone: userId
            },
            success: function (res) {
                if (res.success) {
                    // 重新渲染穿梭框
                    transfer.render({
                        elem: '#sourceAuths'  //绑定元素
                        ,title: ['未获取权限', '已获取权限']
                        ,data: res.model.sourceAuthViews
                        ,value: res.model.authValues
                        ,id: 'sourceAuths' //定义索引
                        });
                } else {
                    layer.msg(res.message);
                }
            }
        });
    }
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