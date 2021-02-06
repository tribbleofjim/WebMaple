document.getElementById("username").innerHTML = "用户：" + decodeURIComponent(getCookieByKey("user"));

var layer,form, element, $;
layui.use(['layer', 'form', 'jquery', 'element'], function () {
    layer = layui.layer;
    element = layui.element;
    form = layui.form;
    $ = layui.jquery;
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