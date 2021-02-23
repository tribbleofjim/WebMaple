var form, layer, table, element, $;
layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    form = layui.form;
    layer = layui.layer;
    table = layui.table;
    element = layui.element;
    $ = layui.jquery;

    let startUrlNum = 1;
    $("#addUrl").click(function () {
        let str = '<div class="layui-form-item"  style="margin-left: 10px;"   > ' +
            '<div class="layui-input-inline" style="width: 300px;!important;">' +
            '<input type="text" name="startUrls[]" lay-verify="required"' +
            'class="form-control layui-input" style="width: 200%;"   >' +
            '</div>' +
            '<div class="layui-input-block" style="margin-left: 610px">' +
            '<button type="button" class="layui-btn layui-btn-normal layui-btn-sm removeclass"><i class="layui-icon">&#xe67e;</i></button>' +
            '</div>' +
            '</div>';

        $("#startUrl").append(str);
        startUrlNum++;
    });
    $("body").on('click', ".removeclass", function () {
        //元素移除前校验是否被引用
        let approvalName = $(this).parent().prev('div.layui-input-inline').children().val();
        let parentEle = $(this).parent().parent();
        parentEle.remove();
    });

    $.ajax({
        url: 'workers',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, value) {
                $('#workers').append(new Option(value, index));// 下拉菜单里添加元素
            });
            layui.form.render("select");//重新渲染 固定写法
        }
    });

    form.on('submit(createSpider)', function(data) {
        let urls = new Array();
        for (i = 0; i < startUrlNum; i++) {
            urls[i] = data.field["startUrls[" + i + "]"];
        }
        $.ajax({
            type: 'post',
            url: "createSpider",
            data: {
                uuid: data.field.uuid,
                ip: data.field.ip,
                processor: data.field.processor,
                downloader: data.field.downloader,
                pipeline: data.field.pipeline,
                threadNum: data.field.threadNum,
                startUrls: urls
            },
            success: function (res) {
                if (res.status == 200) {
                    layer.alert(res.message, {icon: 5}, function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                       //刷新页面
                        parent.location.reload();
                    });

                } else {
                    layer.alert(res.message, {icon: 6}, function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                        //刷新页面
                        parent.location.reload();
                    });
                }
            }
        });
        return false;
    });

    table.on('tool(spiders)', function(obj) {
        let data = obj.data;//获取该行数据
        let layEvent = obj.event;//获取该点击事件
        switch(layEvent){
            case 'start':
                startSpider(data);
            break;
            case 'stop':
                stopSpider(data);
            break;
            case 'update':
                updateSpider(data);
            break;
            case 'delete':
                deleteSpider(data);
            break;
        };
    });

    function startSpider(data) {
        // do start spider
        var uuid = data.uuid;
        layer.open({
            content: '确认要启动这个爬虫吗？',
            btn: ['确认', '取消']
            ,btn1: function(index, layero){
                // do restart spider
                $.ajax({
                    type: 'get',
                    url: 'startSpider',
                    data: {
                        uuid: uuid
                    },
                    success: function (res) {
                        layer.msg(res.message);
                    }
                });
            },
            btn2: function(index, layero){
                layer.closeAll();
            }
        });
    }

    function stopSpider(data) {
        var uuid = data.uuid;
        layer.open({
            content: '确认要停止这个爬虫吗？',
            btn: ['确认', '取消']
            ,btn1: function(index, layero){
                // do stop spider
                $.ajax({
                    type: 'get',
                    url: 'stopSpider',
                    data: {
                        uuid: uuid
                    },
                    success: function (res) {
                        layer.msg(res.message);
                    }
                });
            },
            btn2: function(index, layero){
                layer.closeAll();
            }
        });
    }

    function updateSpider(data) {
        var newThreadNum = document.getElementById("newThreadNum").value;
        layer.open({
            type:1,
            area:['400px','300px'],
            title: '修改spider',
            content: $("#update"),
            shade: 0,
            btn: ['提交', '重置']
            ,btn1: function(index, layero){
                // do update spider
                $.ajax({
                    type: 'post',
                    url: 'modifySpider',
                    data: {
                        threadNum: newThreadNum
                    },
                    success: function (res) {
                        layer.msg(res.message);
                    }
                });
                layer.msg('修改成功');
                return true;
            },
            btn2: function(index, layero){
                return false;
            },
            cancel: function(layero,index){
                layer.closeAll();
            }
        });
    }

    function deleteSpider(data) {
        var uuid = data.uuid;
        layer.open({
            content: '确认要删除这个爬虫吗？',
            btn: ['确认', '取消']
            ,btn1: function(index, layero){
                // do restart spider
                $.ajax({
                    type: 'get',
                    url: 'deleteSpider',
                    data: {
                        uuid: uuid
                    },
                    success: function (res) {
                        layer.msg(res.message);
                    }
                });
            },
            btn2: function(index, layero){
                layer.closeAll();
            }
        });
    }
});

function useTemplate() {
    var templateName = document.getElementById("templateName").value;
    console.log(templateName);
    if (templateName === null || templateName === '') {
        layer.msg('请填写模板名称，可在左侧导航栏"爬虫定制化"中查看');
        return;
    }
    var template = document.getElementById("template");
    $.ajax({
        type: 'get',
        url: 'getTemplate',
        data: {
            templateName: templateName
        },
        success: function (res) {
            $("#template").html("\n");
            template.insertAdjacentHTML("beforeEnd", res.model.html);
        }
    });
}




