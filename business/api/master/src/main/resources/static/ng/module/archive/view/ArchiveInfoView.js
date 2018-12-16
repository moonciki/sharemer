define(function(require, exports, module) {

    seajs.use('./css/module/danmaku/scojs.css');
    seajs.use('./css/module/danmaku/colpick.css');
    seajs.use('./css/module/danmaku/main.css');
    seajs.use('./css/module/archive_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');
    var Template = require('module/archive/tpl/ArchiveInfoView.tpl');
    var Archive = require('module/archive/model/Archive');
    require('module/danmaku/sh_circle_loader');
    require('module/danmaku/sco.tooltip');
    require('module/danmaku/colpick');
    require('module/danmaku/jquery.danmu');
    require('module/danmaku/danmu_main');



    var ArchiveInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        current_mid : null,
        initialize: function() {
            this.model = new Archive();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.archive').removeClass("menu_unit").addClass("menu_unit_point");
        },

        events: {

        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#archive";
                return;
            }
            this.model.archive_id = id;

            this.$el.find('#danmup').DanmuPlayer({
                src:"https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo/304_ca4780ca5c749d7670a3cae64df77d52.mp4",
                height: "400px", //区域的高度
                width: "700px", //区域的宽度
                urlToPostDanmu:"/damaku/save"
            });
        }
    });

    module.exports = ArchiveInfoView;

});