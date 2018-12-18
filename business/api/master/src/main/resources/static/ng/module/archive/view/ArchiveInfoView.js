define(function (require, exports, module) {

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
        current_mid: null,
        initialize: function () {
            this.model = new Archive();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.archive').removeClass("menu_unit").addClass("menu_unit_point");
        },

        events: {},

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#archive";
                return;
            }
            this.model.archive_id = id;
            this.$el.find('#danmup').html("");
            this.$el.find('.danmaku_content').html("");
            this.$el.find('#danmup .danmu-cover').danmu("addDanmu", null);
            this.$el.find('#archive_id').val(id);

            var view = this;
            this.model.get_archive_info(id).done(function (resp) {
                if (resp.code == 0) {
                    var tagHtm = "";
                    if (resp.result.source_type == 0) {
                        tagHtm = "<span class='source_type_tag' style='background-color: #41c0d0'>音频</span>"
                    } else {
                        tagHtm = "<span class='source_type_tag' style='background-color: #ff99c4'>视频</span>"
                    }
                    view.$el.find('.a_info_main_page_title_1').html(tagHtm + resp.result.title);
                    view.$el.find('.publish_time').html(resp.result.ctime.replace("T", ' '));
                    view.initDanmuPlayer(resp.result);
                }
            });
        },

        initDanmuPlayer: function (archive) {
            var path = archive.file;
            if (archive.source_type == 0) {
                path = ("http://media.sharemer.com/" + path);
            }
            this.$el.find('#danmup').DanmuPlayer({
                src: path,
                height: "401px", //区域的高度
                width: "700px", //区域的宽度
                urlToPostDanmu: "/pc_api/r/danmaku/save"
            });

            if (archive.source_type == 0) {
                this.$el.find(".danmu-video")
                    .css("background-image", "url(" + archive.cover + "?imageView2/1/w/700/h/400)");
            }

            var user = window.SHION.currentUser;
            if (user == null || user == undefined) {
                this.$el.find(".send-btn").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("placeholder", "请先登录/注册");
            }

            var view = this.$el;
            <!--弹幕加载-->
            this.model.get_danmakus(archive.id).done(function (resp) {
                if (resp.code == 0) {
                    if (resp.result != null && resp.result.length > 0) {
                        view.find('#danmup .danmu-cover').danmu("addDanmu", resp.result);
                        var appendHtm = "";
                        for (var i = 0; i < resp.result.length; i++) {
                            var danmu_text;
                            if (resp.result[i].text.length >= 11) {
                                danmu_text = resp.result[i].text.substring(0, 11) + "...";
                            } else {
                                danmu_text = resp.result[i].text;
                            }
                            appendHtm += "<div class=\"danmaku_list_tr\">"
                                + "<div class=\"danmaku_list_tb1\">" + resp.result[i].time + "</div>"
                                + "<div class=\"danmaku_list_tb2\">" + danmu_text + "</div>"
                                + "<div class=\"danmaku_list_tb3\">" + resp.result[i].ctime.replace("T", ' ') + "</div></div>";
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