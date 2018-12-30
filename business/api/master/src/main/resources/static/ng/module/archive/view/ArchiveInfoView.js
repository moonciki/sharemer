define(function (require, exports, module) {

    seajs.use('./css/module/archive_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');
    var Template = require('module/archive/tpl/ArchiveInfoView.tpl');
    var Archive = require('module/archive/model/Archive');
    var ReplyView = require('module/reply/view/ReplyView');
    var FavView = require('module/fav/view/FavView');


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

            this.replyView = new ReplyView({
                oid: this.id,
                otype: 2,
                el: this.$el.find('.info_area_reply')
            });

            this.favView = new FavView({
                oid: this.id,
                otype: 2,
                el: this.$el.find('.a_share_area')
            });
        },

        events: {},

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#archive";
                return;
            }
            this.favView.oid = id;
            this.replyView.oid = id;
            this.model.archive_id = id;
            this.favView.initNotFav();
            /** 获取评论*/
            this.replyView.getReplies();

            var view = this;
            this.model.get_archive_info(id).done(function (resp) {
                if (resp.code == 0) {
                    var tagHtm = "";
                    if (resp.result.source_type == 0) {
                        tagHtm = "<span class='source_type_tag' style='background-color: #00b5e5'>音频</span>"
                    } else {
                        tagHtm = "<span class='source_type_tag' style='background-color: #ff99c4'>视频</span>"
                    }
                    view.$el.find('.a_info_main_page_title_1').html(tagHtm + resp.result.title);
                    if(resp.result.is_faved == 1){
                        view.favView.initAlreadyFav();
                    }
                    view.$el.find('.publish_time').html(resp.result.ctime.replace("T", ' '));
                    if (resp.result.publish_type == 0) {
                        view.$el.find('.publish_type').html("原创");
                        view.$el.find('.origin_title').html("<span style='text-decoration:line-through'>UP主原创作品，无此项</span>");
                        view.$el.find('.origin_author').html("<span style='text-decoration:line-through'>UP主原创作品，无此项</span>");
                    } else {
                        if (resp.result.publish_type == 1) {
                            view.$el.find('.publish_type').html("搬运");
                        } else {
                            view.$el.find('.publish_type').html("翻唱/鬼畜/改编");
                        }
                        view.$el.find('.origin_title').html(resp.result.origin_title);
                        view.$el.find('.origin_author').html(resp.result.origin_author);
                    }

                    var reg = new RegExp("\n","g");
                    view.$el.find('.a_desc').html(resp.result.desc.replace(reg, "<br/>"));

                    if(resp.result.tags != null && resp.result.tags.length > 0){
                        var tagHtm = "";
                        for (var i = 0; i < resp.result.tags.length; i++){
                            tagHtm += "<span class=\"a_tag_style\" onclick=\"location.href='#archive/tag/"+resp.result.tags[i].id+"';\">"+resp.result.tags[i].tag_name+"</span>"
                        }
                        view.$el.find('.a_tags').html(tagHtm);
                    }

                    var user = resp.result.user;
                    if(user != null){
                        view.$el.find('.a_user_info_avater').html("<img src='"+user.avater+"' class='a_avater_img'/>");
                        view.$el.find('.a_user_info_href_1').html(user.name);
                        var btnHtm = "<div class='go_self_space' onclick=\"window.open('/#user/info/"+user.id+"');\"><span class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;去往空间</div>";
                        btnHtm += "<div class='go_self_blog'><span class='glyphicon glyphicon-home'></span>&nbsp;&nbsp;去往博客</div>"
                        view.$el.find('.a_user_info_href_2').html(btnHtm);
                        view.$el.find('.user_desc').html(user.desc);
                    }
                    view.$el.find('.player_content').html("<iframe style=\"width:1060px;height:460px;\" src=\"/player/#archive/player/"+resp.result.id+"\" frameborder=\"0\"></iframe>");
                }
            });
        }
    });

    module.exports = ArchiveInfoView;

});