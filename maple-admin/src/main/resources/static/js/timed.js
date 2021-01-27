layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let $ = layui.jquery;

    form.on('submit(addTimedSpider)', function(data){
        $.ajax({
            type: 'post',
            url: "createTimedSpider",
            data: {
                worker: data.field.worker,
                uuid: data.field.uuid,
                type: data.field.type,
                maintain: data.field.maintain,
                cronNum: data.field.cronNum,
                cronUnit: data.field.cronUnit
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
});