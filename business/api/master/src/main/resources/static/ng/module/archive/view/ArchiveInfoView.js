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
            this.$el.find('#archive_id').val(id);
            this.model.archive_id = id;

            this.$el.find('#danmup').DanmuPlayer({
                src:"https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo/304_ca4780ca5c749d7670a3cae64df77d52.mp4",
                height: "401px", //区域的高度
                width: "700px", //区域的宽度
                urlToPostDanmu:"/pc_api/r/danmaku/save"
            });

            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                this.$el.find(".send-btn").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("placeholder", "请先登录/注册");
            }

            var view = this.$el;
            <!--弹幕加载-->
            this.model.get_danmakus(id).done(function (resp) {
                if(resp.code == 0){
                    if(resp.result != null && resp.result.length > 0){
                        view.find('#danmup .danmu-cover').danmu("addDanmu",resp.result);
                        var appendHtm = "";
                        for(var i=0; i<resp.result.length; i++){
                            var danmu_text;
                            if(resp.result[i].text.length >= 11){
                                danmu_text = resp.result[i].text.substring(0, 11)+"...";
                            }else{
                                danmu_text = resp.result[i].text;
                            }
                            appendHtm += "<div class=\"danmaku_list_tr\">"
                                + "<div class=\"danmaku_list_tb1\">"+resp.result[i].time+"</div>"
                                + "<div class=\"danmaku_list_tb2\">"+danmu_text+"</div>"
                                + "<div class=\"danmaku_list_tb3\">"+resp.result[i].ctime.replace("T", ' ')+"</div></div>";
                        }

                        view.find('.danmaku_num').text(resp.result.length);
                        view.find('.danmaku_content').html(appendHtm);
                    }
                }
            });
        }
    });

    module.exports = ArchiveInfoView;

});