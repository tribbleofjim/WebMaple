var userId = decodeURIComponent(getCookieByKey("user"));
layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let $ = layui.jquery;

    $.ajax({
        type: 'get',
        url: "commanderMsgList",
        data: {
            id: userId
        },
        success: function (res) {
            console.log(res);
            layui.config({
                base: "/layui/mods/"
            }).use("mods", function (mods) {
                //初始化消息组件
                mods(["layer", "jsanNotice"], function (layer) {
                    const notice = layer.noticeMarker({
                        "elem": "#noticeMarker",
                        "positionX": "right",
                        "positionY": "100px",
                        "lowKey": true,
                        "noticeWindow": {
                            "type": 1,
                            "title": "消息",
                            "classType": {"notice": "升级申请", "accuse": "异常举报", "other": "其他"},
                            "width": "500px",
                            "height": "720px",
                            "contentWidth": "80%",
                            "contentHeight": "65%"
                        }
                    });

                    //手动推送新消息，在使用消息组件自带的消息窗口时使用
            //        notice.addNews({
            //            "lowKey": false,
            //            "classTypeId": "notice",
            //            "content": [{"title": "【申请】升级成为管理员申请", "content": "用户 test 申请成为管理员", "date": "2021-04-27 19:30:36", "url": "/auth"},
            //                {"title": "【申请】升级成为管理员申请", "content": "用户 name1 申请成为管理员", "date": "2019-07-28 19:30:36", "url": "/auth"},
            //                {"title": "【申请】升级成为管理员申请", "content": "用户 111 申请成为管理员", "date": "2019-07-28 19:30:36", "url": "/auth"},
            //                {"title": "【申请】升级成为管理员申请", "content": "用户 1234 申请成为管理员", "date": "2019-07-28 19:30:36", "url": "/auth"},
            //                {"title": "【申请】升级成为管理员申请", "content": "用户 樱桃小丸子 申请成为管理员", "date": "2019-07-28 19:30:36", "url": "/auth"},
            //            ]
            //        });
                    notice.addNews({
                        "lowKey": false,
                        "classTypeId": "notice",
                        "content": res.noticeView
                    });
                    notice.addNews({
                        "lowKey": false,
                        "classTypeId": "accuse",
                        "content": res.accuseView
                    });
                    notice.addNews({
                        "lowKey": false,
                        "classTypeId": "other",
                        "content": res.otherView
                    });
                    //手动进行消息盒子提醒，通常用于自定义消息窗口的时候使用
                    /*notice.remind({
                        "lowKey": false
                    });*/
                });
            });
        }
    });
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