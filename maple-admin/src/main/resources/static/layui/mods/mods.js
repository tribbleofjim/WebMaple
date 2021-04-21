(function(root,factroy){
    typeof root.layui === 'object' && layui.define ? layui.define(function(mods){mods('mods',factroy(layui))}) : null;
}(this,function(layui){
    'use strict';

    // 预定义插件列表
    var list = {
        jsanNotice:'extend/notice/jsan-notice'
    };

    // 插件加载器
    var mods = function(mod_name,callback){
        var extend = {};

        // 如果是官方模块
        // 引入单个插件
        if(typeof mod_name === 'string'){
            if(!isLayui(mod_name)){
                if(typeof list[mod_name] !== 'string') throw new Error('引入的插件'+mod_name+'不在预定义列表中');
                extend[mod_name] = list[mod_name];
            }
        }

        // 批量引入插件
        else if(Array.isArray(mod_name)){
            for(var i=0,item;item = mod_name[i++];){
                if(!isLayui(item)){
                    if(typeof list[item] !== 'string') throw new Error('引入的插件'+item+'不在预定义列表中');
                    extend[item] = list[item];
                }
            }
        }
        else{
            throw new Error('mods()中，传入了无效的参数');
        }

        if(typeof callback !== 'function') throw Error('第二个参数必须是函数');
        layui.extend(extend).use(mod_name,function(){
            var arg = [];
            for(var i=0,item;item = arguments[i++];){
                arg.push(item);
            }
            callback.apply(layui,arg);
        });
    }

    var isLayui = function(mod){
        return typeof layui_mods[mod] === 'string' ? true : false;
    };

    if(typeof Array.isArray !== 'function'){
        Array.isArray = function(val){
            return Object.prototype.toString.call(val) === '[object Array]' ? true : false;
        }
    }

    var layui_mods = {
        layer:'layer',
        laydate:'laydate',
        layedit:'layedit',
        laypage:'laypage',
        laytpl:'laytpl',
        table:'table',
        form:'form',
        upload:'upload',
        jquery:'jquery',
        code:'code',
        carousel:'carousel',
        element:'element',
        flow:'flow',
        mobile:'mobile',
        rate:'rate',
        tree:'tree',
        util:'util',
    };

    return mods;
}));