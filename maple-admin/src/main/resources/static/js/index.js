layui.use(['form', 'layer', 'jquery'],function(){
    let form = layui.form;
    let layer = layui.layer;
    let $ = layui.jquery;

    let startUrlNum = 1;
    $("#addUrl0").click(function () {
        let str = '<div class="layui-form-item"  style="margin-left: 10px;"   > ' +
            '<div class="layui-input-inline" style="width: 300px;!important;">' +
            '<input type="text" name="startUrls[]" lay-verify="required"' +
            'class="form-control layui-input" style="width: 200%;"   >' +
            '</div>' +
            '<div class="layui-input-block" style="margin-left: 610px">' +
            '<button type="button" class="layui-btn layui-btn-danger layui-btn-sm removeclass"><i class="layui-icon">&#xe67e;</i></button>' +
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
});



