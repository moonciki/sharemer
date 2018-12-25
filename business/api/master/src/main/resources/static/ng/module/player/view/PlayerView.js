define(function (require, exports, module) {

    seajs.use('../css/module/danmaku/scojs.css');
    seajs.use('../css/module/danmaku/colpick.css');
    seajs.use('../css/module/danmaku/main.css');

    var Template = require('module/player/tpl/PlayerView.tpl');
    var Player = require('module/player/model/Player');
    require('module/danmaku/sh_circle_loader');
    require('module/danmaku/sco.tooltip');
    require('module/danmaku/colpick');
    require('module/danmaku/jquery.danmu');
    require('module/danmaku/danmu_main');


    var PlayerView = Backbone.View.extend({
        template: Template,
        current_mid: null,
        initialize: function () {
            this.model = new Player();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
        },

        events: {},

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#player";
                return;
            }
            this.model.player_id = id;

            this.$el.find('#danmup').html("");
            this.$el.find('.danmaku_content').html("");
            this.$el.find('#archive_id').val(id);

            var view = this;
            this.model.get_path(id).done(function (resp) {
                if (resp.code == 0) {
                    view.initDanmuPlayer(resp.result);
                }
            });
        },

        initDanmuPlayer: function (player) {
            var path = player.file;
            if (player.source_type == 0) {
                path = ("http://media.sharemer.com/" + path);
            }
            this.$el.find('#danmup').DanmuPlayer({
                src: path,
                height: "401px", //区域的高度
                width: "700px", //区域的宽度
                urlToPostDanmu: "/pc_api/r/danmaku/save"
            });

            if (player.source_type == 0) {
                this.$el.find('.danmu-div').append("<div class='danmaku_record_down'></div><div class='danamku_record' style=\"background-image: url('" + player.cover + "?imageView2/1/w/250/h/250')\"><div class='danmaku_record_cover'></div></div>")
                this.$el.find(".danmu-video")
                    .css("background-image", "url(" + player.cover + "?imageView2/1/w/700/h/400)").css("filter", "blur(10px)");
            }

            var user = window.SHION.currentUser;
            if (user == null || user == undefined) {
                this.$el.find(".send-btn").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("disabled", "disabled");
                this.$el.find(".danmu-input").attr("placeholder", "请先登录/注册");
            }

            var view = this.$el;
            <!--弹幕加载-->
            this.model.get_danmakus(player.id).done(function (resp) {
                if (resp.code == 0) {
                    if (resp.result != null && resp.result.length > 0) {
                        view.find('#danmup .danmu-cover').danmu("addDanmu", resp.result);
                        var appendHtm = "";
                        var danmaku_size = resp.result.length;
                        for (var i = 0; i < danmaku_size; i++) {
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
                    } else {
                        view.find('.danmaku_content').html("<div class='no_danmaku' style='padding: 100px 85px'>还没有人发弹幕，快来抢前排！</div>");
                    }
                }
            });
        }
    });

    module.exports = PlayerView;

});