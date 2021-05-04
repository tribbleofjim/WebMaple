var phone = decodeURIComponent(getCookieByKey('user'));
layui.use(['form', 'layer', 'table', 'jquery', 'element'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var element = layui.element;
    var $ = layui.jquery;
});

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