layui.define(['form', 'layer', 'table', 'jquery', 'element'], function (exports) {
    let form = layui.form;
    let layer = layui.layer;
    let table = layui.table;
    let element = layui.element;
    let $ = layui.jquery;

    delHtml();

    table.on('tool(templates)', function(obj) {
        let data = obj.data;//获取该行数据
        let layEvent = obj.event;//获取该点击事件
        if (layEvent === 'seeForm') {
            $.ajax({
                type: 'get',
                url: 'getTemplate',
                data: {
                    templateName: data.templateName
                },
                success: function (res) {
                    var showTemplate = document.getElementById('showTemplate');
                    showTemplate.insertAdjacentHTML("beforeEnd", res.model.html);
                    layer.open({
                        type:1,
                        area:['600px','400px'],
                        title: '查看模板',
                        content: $("#showTemplate"),
                        shade: 0,
                        cancel: function(layero,index){
                            $("#showTemplate").html("\n");
                            layer.closeAll();
                        }
                    });
                }
            });
        } else {
            layer.msg('点击事件错误');
        }
    });


    $('button').on('click',function() {
      	var _this = $(this),
      		 size = _this.data('size'),
      		 type = _this.data('type'),
             html =  '';

      	switch (type) {
            case 'text':
                html = input(type,size);
                break;
            case 'select':
                html = select(size);
                break;
            case 'submit':
                html = submits(size);
                break;
            case 'sub':
                submitHtml();
            break;
            case 'del':
                $('form').html("\n")
                delHtml()
                $('.code-show').text('')
                return false
                break;
            default:
                layer.msg('类型错误',{icon:2})
        }
        $('form').append(html);
        form.render();
        setHtml(html);
    });

    function delHtml() {
        layui.data('form_html', {
            key: 'html'
            ,remove: true
        });
    }

    function setHtml(html) {
        var h = layui.data('form_html');
        if(h && h.html ){
          var _d = h.html+html
        }else{
          var _d = html
        }
        layui.data('form_html',{
            key:'html',
            value:_d
        })
        // $('.code-show').text('<form class="layui-form" action="" onsubmit="return false">\n' +_d+ '</form>')
    }

    function submitHtml() {
        var h = layui.data('form_html');
        var html = '';
        var formUrl = getFormUrl();
        var templateName = getTemplateName();
        if (h && h.html) {
            html = h.html;
            console.log(html);
            $.ajax({
                type: 'post',
                url: 'submitTemplate',
                data: {
                    html: html,
                    formUrl: formUrl,
                    templateName: templateName
                },
                success: function (res) {
                    layer.msg(res.message);
                }
            });
        }
    }

    function randStrName() {
        return Math.random().toString(36).substr(8);
    }

    function getTitle() {
        var title = document.getElementById('title').value;
        return title;
    }

    function getName() {
        var name = document.getElementById('name').value;
        return name;
    }

    function getFormUrl() {
        var formUrl = document.getElementById('formUrl').value;
        return formUrl;
    }

    function getTemplateName() {
        var templateName = document.getElementById('templateName').value;
        return templateName;
    }

    function jscode() {
        var html = '<script>\n' +
            '  layui.use(\'form\', function(){\n' +
            '    var form = layui.form;\n' +
            '    form.on(\'submit(formDemo)\', function(data){\n' +
            '      layer.msg(JSON.stringify(data.field));\n' +
            '      return false;\n' +
            '    });\n' +
            '  });\n' +
            '</script>';
        return html;
    }

    function input(type,size) {
        var title = getTitle();
        var name = getName();
        var html = '  <div class="layui-form-item">\n' +
          '    <label class="layui-form-label">'+title+'</label>\n' +
          '    <div class="layui-input-'+size+'">\n' +
          '      <input type="'+type+'" name="'+name+'" required  lay-verify="required" placeholder="请输入'+title+'" autocomplete="off" class="layui-input">\n' +
          '    </div>\n' +
          '  </div>\n';
        return html;
    }
    function select(size) {
        var title = getTitle();
        var name = getName();
        var html = '  <div class="layui-form-item">\n' +
          '    <label class="layui-form-label">' + title + '</label>\n' +
          '    <div class="layui-input-'+size+'">\n' +
          '      <select name="'+name+'" lay-verify="required" lay-search>\n' +
          '        <option value="">请选择' + title + '</option>\n' +
          '      </select>\n' +
          '    </div>\n' +
          '  </div>\n';
        return html;
    }
    function submits(size) {
        var html = '  <div class="layui-form-item">\n' +
            '    <div class="layui-input-'+size+'">\n' +
            '      <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>\n' +
            '      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
            '    </div>\n' +
            '  </div>\n';
        return html;
    }

    $('.click-but button').click()
    var jscodehtml = jscode();
    $('.js-show').text(jscodehtml)
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });

    exports('index', {});
});
