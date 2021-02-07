layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let $ = layui.jquery;

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
            case 'restart':
                restartSpider(data);
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
        event.stoppropagation();
    });

    function startSpider(data) {
        // do start spider
        console.log(data);
        layer.msg('启动成功！');
    }

    function restartSpider(data) {
        layer.open({
            content: '确认要重启这个爬虫吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do restart spider
                    layer.msg('重启成功');
                },
                btn2: function(index, layero){
                    layer.closeAll();
                }
        });
    }

    function stopSpider(data) {
        layer.open({
            content: '确认要停止这个爬虫吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do stop spider
                    console.log(data);
                    layer.msg('停止');
                },
                btn2: function(index, layero){
                    layer.closeAll();
                }
        });
    }

    function updateSpider(data) {
        layer.open({
            type:1,
            area:['400px','300px'],
            title: '修改spider',
            content: $("#update"),
            shade: 0,
            btn: ['提交', '重置']
            ,btn1: function(index, layero){
                // do update spider
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
        layer.open({
            content: '确认要删除吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do delete spider
                    layer.msg('删除成功');
                },
                btn2: function(index, layero){
                    layer.closeAll();
                }
        });
    }
});




