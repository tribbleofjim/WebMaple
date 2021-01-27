layui.use(['form', 'layer','table', 'jquery', 'element'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let $ = layui.jquery;

    table.on('toolbar(timedJobs)', function(obj) {
        let checkStatus = table.checkStatus(obj.config.id);
        let layEvent = obj.event;//获取该点击事件
        switch(layEvent){
            case 'resumeJob':
                resumeJob(checkStatus.data);
            break;
            case 'pauseJob':
                pauseJob(checkStatus.data);
            break;
            case 'delJob':
                delJob(checkStatus.data);
            break;
        };
    });

    function resumeJob(data) {
        let job = data[0];
        if (job === null) {
            layer.msg("请先选中数据");
        }
        if (job.state !== '暂停中') {
            layer.msg("定时任务已经在运行");
            return;
        }
        layer.open({
            content: '确认要启动定时任务吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do resume spider
                    $.ajax({
                        type: 'get',
                        url: "resumeTimedSpider",
                        data: {
                            jobName: job.jobName
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

    function pauseJob(data) {
        let job = data[0];
        if (job === null) {
            layer.msg("请先选中数据");
        }
        if (job.state !== '执行中') {
            layer.msg("定时任务不在执行中，无法暂停");
            return;
        }
        layer.open({
            content: '确认要暂停定时任务吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do
                    $.ajax({
                        type: 'get',
                        url: "pauseTimedSpider",
                        data: {
                            jobName: job.jobName
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

    function delJob(data) {
        let job = data[0];
        if (job === null) {
            layer.msg("请先选中数据");
        }
        if (job.state !== '暂停中') {
            layer.msg("定时任务正在执行，无法删除");
            return;
        }
        layer.open({
            content: '确认要删除定时任务吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do restart spider
                    $.ajax({
                        type: 'get',
                        url: "deleteTimedSpider",
                        data: {
                            jobName: job.jobName
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
                    layer.alert(res.message, {icon: 6}, function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                       //刷新页面
                        parent.location.reload();
                    });

                } else {
                    layer.alert(res.message, {icon: 5}, function () {
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