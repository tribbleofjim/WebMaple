layui.use(['form', 'layer','table', 'jquery', 'element','upload'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let upload = layui.upload;
    let $ = layui.jquery;

    table.on('toolbar(nodes)', function(obj) {
        let checkStatus = table.checkStatus(obj.config.id);
        let layEvent = obj.event;//获取该点击事件
        switch(layEvent){
            case 'addNode':
                addNode();
            break;
            case 'removeNode':
                removeNode(checkStatus.data);
            break;
        };
        event.stoppropagation();
    });

    var uploadInst = upload.render({
        elem: '#upload' //绑定元素
        ,url: '/upload/' //上传接口
        ,progress: function(n, elem) {
            var percent = n + '%' //获取进度百分比
            element.progress('demo', percent); //可配合 layui 进度条元素使用

            //以下系 layui 2.5.6 新增
            console.log(elem); //得到当前触发的元素 DOM 对象。可通过该元素定义的属性值匹配到对应的进度条。
        }
        ,done: function(res){
            //上传完毕回调
            console.log("upload success");
        }
        ,error: function(){
            //请求异常回调
            console.log("upload error");
        }
    });

    function addNode() {
        layer.open({
            type:1,
            area:['400px','300px'],
            title: '添加节点',
            content: $("#nodeInfo"),
            shade: 0,
            btn: '提交'
            ,yes: function(index, layero){
                let ip = document.getElementById('ip').value;
                let name = document.getElementById('name').value;
                let xhr = new XMLHttpRequest();
                let url = "/addWorker?ip=" + ip + "&name=" + name;
                xhr.open('GET', url, true);
                xhr.onreadystatechange = function() {
                    if (xhr.status == 200) {
                        layer.msg('添加节点成功！');
                    }
                }
                xhr.send(null);
                return true;
            },
            cancel: function(layero,index){
                layer.closeAll();
            }
        });
    }

    function removeNode(data) {
        console.log(data);
        let node = data[0];
        // cannot remove admin
        if (node.type !== 'worker') {
            return;
        }

        layer.open({
            content: '确认要删除吗？',
            btn: ['确认', '取消']
                ,btn1: function(index, layero){
                    // do remove
                    let xhr = new XMLHttpRequest();
                    let url = "/removeWorker?name=" + node.name;
                    xhr.open('GET', url, true);
                    xhr.onreadystatechange = function() {
                        if (xhr.status == 200) {
                            layer.msg('删除节点成功！');
                        }
                    }
                    xhr.send(null);
                },
                btn2: function(index, layero){
                    layer.closeAll();
                }
        });
    }

});