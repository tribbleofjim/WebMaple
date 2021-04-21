// noinspection ThisExpressionReferencesGlobalObjectJS
/**
 * 基于layui v-2.5.4 版本，封装的消息组件
 * 作者：jsan <287222643@qq.com>
 * 日期：2019-07-28
 */
(function (root, factroy) {
    typeof root.layui === "object" && layui.define ? layui.define(["layer"], function(mods){mods("jsanNotice", factroy(layui.layer))}) : factroy(root.layer);
}(this, function (layer) {
    // //引入css
    layui.link(layui.cache.base+"extend/notice/jsan-notice.css");

    /**
     * 消息盒子方法
     * @param option 参数对象：
     * elem 元素选择器，如：#test
     * positionX 盒子左右定位位置[right,left]，默认right
     * positionY 盒子相对位置，可以选择不同的单位长度，如：100px
     * lowKey true隐藏，false显示
     * noticeWindow 详细消息窗口属性
     */
    layer.noticeMarker = function (option) {
        const $ = layui.$,
            that = {},
            POSITION_X_STYLE = typeof option["positionX"] === "undefined" || option["positionX"] === "right" ? "right: 1px;" : option["positionX"]+" 1px;",
            POSITION_Y_STYPE = typeof option["positionY"] === "undefined" ? "top: 0;" : "top: "+option["positionY"]+";",
            MARKER = "lay-jsan-notice-marker",
            MARKER_BOX = "lay-jsan-notice-marker-box",
            MARKER_BOX_NEWS = "lay-jsan-notice-marker-news",
            MARKER_BOX_ICON = "layui-icon layui-icon-speaker lay-jsan-notice-marker-icon",
            MARKER_BOX_BTN = "lay-jsan-notice-marker-btn",
            MARKER_BOX_HIDE_BTN_ICON = "layui-icon layui-icon-"+(typeof option["positionX"] === "undefined" || option["positionX"] === "right" ? "right" : "left"),
            MARKER_BOX_SHOW_BTN_ICON = "layui-icon layui-icon-"+(typeof option["positionX"] === "undefined" || option["positionX"] === "right" ? "left" : "right");

        that.properties = {};   //初始化属性

        $(option.elem).hide();  //隐藏初始化元素
        that.properties.index = NOTICE_MARKER_INDEX++;  //消息框唯一标识
        that.properties.isOpen = option["lowKey"];  //初始化是否显示
        that.properties.option = option;    //录入初始化配置
        that.properties.option.positionX = typeof option["positionX"] === "undefined" ? "right" : option["positionX"];  //初始化定位
        that.properties.option.positionY = typeof option["positionY"] === "undefined" ? "right" : option["positionY"];  //初始化定位
        that.properties.marker = $("<div id='notice-marker-"+that.properties.index+"' class='"+MARKER+"' style='"+POSITION_X_STYLE+POSITION_Y_STYPE+"'></div>");    //容器
        that.properties.markerBoxBtn = $("<div class='"+MARKER_BOX_BTN+"'></div>");    //提示显示角标
        that.properties.markerBoxBtnIcon = $("<i class='"+MARKER_BOX_HIDE_BTN_ICON+"'></i>");    //提示显示角标
        that.properties.markerBox = $("<div class='"+MARKER_BOX+"'></div>");    //消息盒子
        that.properties.markerBoxIcon = $("<i class='"+MARKER_BOX_ICON+"'></i>");   //消息盒子图标
        //初始化默认方法
        /**
         * 消息提醒方法
         * @param option 参数对象
         * lowKey true隐藏，false显示
         */
        that.remind = function(option) {
            if(option["lowKey"] && that.properties.isOpen) {
                //提醒时不弹出
                this.hideBox();
            }else if(!option["lowKey"] && !that.properties.isOpen) {
                this.showBox();
            }
            this.properties.markerBox.addClass(MARKER_BOX_NEWS);
            this.properties.markerBoxBtn.addClass(MARKER_BOX_NEWS);
        };

        /**
         * 隐藏消息盒子方法
         */
        that.hideBox = function() {
            if(this.properties.isOpen) {
                this.properties.markerBox.hide();
                this.properties.markerBoxBtnIcon.removeClass(MARKER_BOX_HIDE_BTN_ICON);
                this.properties.markerBoxBtnIcon.addClass(MARKER_BOX_SHOW_BTN_ICON);
                this.properties.isOpen = false;
            }
        };

        /**
         * 显示消息盒子方法
         */
        that.showBox = function() {
            console.log("showbox");
            if(!this.properties.isOpen) {
                this.properties.markerBox.show();
                this.properties.markerBoxBtnIcon.removeClass(MARKER_BOX_SHOW_BTN_ICON);
                this.properties.markerBoxBtnIcon.addClass(MARKER_BOX_HIDE_BTN_ICON);
                this.properties.isOpen = true;
            }
        };

        //封装渲染
        that.properties.markerBoxBtn.html(that.properties.markerBoxBtnIcon);
        that.properties.markerBox.html(that.properties.markerBoxIcon);
        that.properties.marker.html(that.properties.option["positionX"] === "left" ? that.properties.markerBox : that.properties.markerBoxBtn);
        that.properties.marker.append(that.properties.option["positionX"] === "left" ? that.properties.markerBoxBtn : that.properties.markerBox);

        $("body").append(that.properties.marker);

        //隐藏/显示事件
        that.properties.markerBoxBtn.unbind().on("click", that, function (event) {
            event.data.properties.isOpen ? event.data.hideBox() : event.data.showBox();
        });

        //初始化详细消息窗口
        /**
         * type 1：组件自带消息窗口，2：打开用户自定义窗口，默认是1
         * title 消息窗口标题
         * classType 消息类型（type=1时生效） 属于Object类型 {"id": "name"} 如：{"notice": "通知", "alerted": "预警", "other": "其他"}
         * url 自定义消息窗口时打开的链接
         * width 消息窗口宽度 可以选择不同的单位长度，如：100px
         * height 消息窗口高度 可以选择不同的单位长度，如：100px
         * contentWidth 消息内容窗口宽度
         * contentHeight 消息内容窗口高度
         */
        if(typeof that.properties.option.noticeWindow === "object" && that.properties.option.noticeWindow["type"] !== 2) {

            that.noticeWindow = {};
            that.noticeWindow.index = that.properties.index;
            that.noticeWindow.width = typeof that.properties.option.noticeWindow["width"] === "string" ? that.properties.option.noticeWindow["width"] : "150px";
            that.noticeWindow.height = typeof that.properties.option.noticeWindow["height"] === "string" ? that.properties.option.noticeWindow["height"] : "560px";
            that.noticeWindow.contentWidth = typeof that.properties.option.noticeWindow["contentWidth"] === "string" ? that.properties.option.noticeWindow["contentWidth"] : "650px";
            that.noticeWindow.contentHeight = typeof that.properties.option.noticeWindow["contentHeight"] === "string" ? that.properties.option.noticeWindow["contentHeight"] : "560px";
            that.noticeWindow.window = $("<div id='notice-marker-window-"+that.noticeWindow.index+"' class='layui-tab' lay-filter='notice-marker-window-"+that.noticeWindow.index+"'></div>");
            that.noticeWindow.tabTitle = $("<ul id='notice-marker-window-title-"+that.noticeWindow.index+"' class='layui-tab-title'></ul>");
            that.noticeWindow.tabContent = $("<div id='notice-marker-window-content-"+that.noticeWindow.index+"' class='layui-tab-content'></div>");

            const classType = {};
            if(typeof that.properties.option.noticeWindow["classType"] === "object") {
                for(let tab in that.properties.option.noticeWindow["classType"]) {
                    classType[tab] = ["<li id='notice-marker-window-title-"+tab+"'>"+that.properties.option.noticeWindow["classType"][tab]+"<span class='layui-badge-dot layui-hide'></span></li>", "<div id='notice-marker-window-content-"+tab+"' lay-id='notice-marker-window-content-"+tab+"' class='layui-tab-item'></div>"];
                }
            }else {
                classType['notice'] = ["<li id='notice-marker-window-title-notice'>消息</li>", "<div id='notice-marker-window-content-notice' lay-id='notice-marker-window-content-\"+tab+\"' class='layui-tab-item'></div>"];
            }

            that.noticeWindow.classType = classType;

            //详细消息窗口渲染
            that.noticeWindow.window.html(that.noticeWindow.tabTitle);
            that.noticeWindow.window.append(that.noticeWindow.tabContent);
            for(let tab in that.noticeWindow.classType) {
                that.noticeWindow.tabTitle.append(that.noticeWindow.classType[tab][0]);
                that.noticeWindow.tabContent.append(that.noticeWindow.classType[tab][1]);
            }
            that.noticeWindow.window.hide();
            that.noticeWindow.tabTitle.find("li").eq(0).addClass("layui-this");
            that.noticeWindow.tabContent.find(".layui-tab-item").eq(0).addClass("layui-show");
            $("body").append(that.noticeWindow.window);

            layui.use('element', function(){});

            that.properties.markerBox.unbind().on("click", that, function (event) {
                event.data.hideBox();
                layer.open({
                    type: 1,
                    id: "notice-marker-window-layer-"+that.noticeWindow.index,
                    title: typeof event.data.properties.option.noticeWindow["title"] === "string" ? event.data.properties.option.noticeWindow["title"] : "<i class='layui-icon layui-icon-friends'></i>",
                    area: [event.data.noticeWindow.width, event.data.noticeWindow.height],
                    offset: [event.data.properties.option.positionY, (that.properties.option["positionX"] === "right" ? ($("body").width()-16-Number(that.noticeWindow.width.replace("px", "")))+"px" : "16px")],
                    shade: 0,
                    maxmin: true,
                    content: $("#"+event.data.noticeWindow.window.attr("id"))
                });
                event.data.properties.markerBox.removeClass(MARKER_BOX_NEWS);
                event.data.properties.markerBoxBtn.removeClass(MARKER_BOX_NEWS);
            });

            /**
             * 向消息窗口推送消息
             * @param option 参数对象
             * lowKey 是否使用盒子提醒 true不提醒，false提醒
             * classTypeId 消息所属消息类型的id
             * content 需要推送的类型集合 Array，每组数据包括：
             *      title 消息标题。最大27位长度，大于27会自动省略
             *      content 消息内容。最大44位长度，大于44位自动省略
             *      date 消息发布时间 yyyy-MM-dd HH:mm:ss
             *      url 点击消息后跳转位置
             */
            that.addNews = function (option) {
                for(let i in option.content) {
                    const item = $("<div class='lay-jsan-notice-marker-item' notice-url = '" + option.content[i]["url"] + "'></div>");
                    item.append("<div class='lay-jsan-notice-marker-item-title lay-jsan-notice-marker-item-title-new'>"+(option.content[i]["title"].length > 28 ? option.content[i]["title"].substring(0, 25)+"..." : option.content[i]["title"] )+"</div>");
                    item.append("<div class='lay-jsan-notice-marker-item-date'>"+option.content[i]["date"]+"</div>");
                    item.append("<div class='lay-jsan-notice-marker-item-content'>"+(option.content[i]["content"].length > 45 ? option.content[i]["content"].substring(0, 43)+"..." : option.content[i]["content"])+"</div>");
                    $("#notice-marker-window-content-"+option["classTypeId"]).prepend(item);
                }
                $("#notice-marker-window-title-"+option["classTypeId"]).find(".layui-badge-dot").removeClass("layui-hide");
                noticeMarkerItemEvent(option, this);
                const lowKey = typeof option["lowKey"] === "undefined" ? false : option["lowKey"];
                this.remind({"lowKey": true});
            };

            var noticeMarkerItemEvent = function (option, that) {
                $("#notice-marker-window-content-"+option["classTypeId"]+" .lay-jsan-notice-marker-item").unbind().on("click", function (event) {
                    $(this).find(".lay-jsan-notice-marker-item-title").eq(0).removeClass("lay-jsan-notice-marker-item-title-new");
                    if($("#notice-marker-window-content-"+option["classTypeId"]).find(".lay-jsan-notice-marker-item-title-new").length === 0) {
                        $("#notice-marker-window-title-"+option["classTypeId"]).find(".layui-badge-dot").addClass("layui-hide");
                    }
                    $(this).attr("notice-url");
                    layer.open({
                        type: 2,
                        title: that.properties.option.noticeWindow["title"],
                        area: [that.noticeWindow.contentWidth, that.noticeWindow.contentHeight],
                        shade: 0,
                        maxmin: true,
                        content: $(this).attr("notice-url")
                    });
                });
            }
        }else if(typeof that.properties.option.noticeWindow === "object" && that.properties.option.noticeWindow["type"] === 2) {

            that.noticeWindow = {};
            that.noticeWindow.index = that.properties.index;
            that.noticeWindow.url = that.properties.option.noticeWindow["url"];
            that.noticeWindow.width = typeof that.properties.option.noticeWindow["width"] === "string" ? that.properties.option.noticeWindow["width"] : "150px";
            that.noticeWindow.height = typeof that.properties.option.noticeWindow["height"] === "string" ? that.properties.option.noticeWindow["height"] : "560px";

            that.properties.markerBox.unbind().on("click", that, function (event) {
                event.data.hideBox();
                layer.open({
                    type: 2,
                    id: "notice-marker-window-layer-"+that.noticeWindow.index,
                    title: typeof event.data.properties.option.noticeWindow["title"] === "string" ? event.data.properties.option.noticeWindow["title"] : "<i class='layui-icon layui-icon-friends'></i>",
                    area: [event.data.noticeWindow.width, event.data.noticeWindow.height],
                    offset: [event.data.properties.option.positionY, (that.properties.option["positionX"] === "right" ? ($("body").width()-16-Number(that.noticeWindow.width.replace("px", "")))+"px" : "16px")],
                    shade: 0,
                    maxmin: true,
                    content: event.data.noticeWindow.url
                });
            });
        }

        //初始显示设置
        // that.properties.isOpen ? that.hideBox() : that.showBox();
        that.showBox();

        return that;
    };

    return {mod: "jsanNotice", v: "1.0.11"};
}));

NOTICE_MARKER_INDEX = 1;