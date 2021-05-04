var phone = decodeURIComponent(getCookieByKey('user'));
layui.use(['form', 'layer', 'table', 'jquery', 'element'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var element = layui.element;
    var $ = layui.jquery;

//    table.on('toolbar(messages)', function(obj) {
//        let checkStatus = table.checkStatus(obj.config.id);
//        let layEvent = obj.event;//获取该点击事件
//        if (layEvent == 'deleteMessage') {
//            console.log("删除消息：" + obj);
//            var data = checkStatus.data;
//            for (idx in data) {
//                if (!data[idx].valid) {
//                    layer.msg("您删除的消息中，有的消息仍有效，确定删除？");
//                }
//            }
//            $.ajax({
//                type: 'post',
//                url: "deleteMessage",
//                data: {
//                    messages: data
//                },
//                success: function (res) {
//                    layer.msg(res.message);
//                }
//            });
//        }
//        // event.stoppropagation();
//    });
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