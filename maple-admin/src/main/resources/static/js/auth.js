layui.use(['layer', 'jquery', 'element'], function () {
    let layer = layui.layer;
    let element = layui.element;
    let $ = layui.jquery;
});

function addSource() {
    layer.open({
        type:1,
        area:['400px','300px'],
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