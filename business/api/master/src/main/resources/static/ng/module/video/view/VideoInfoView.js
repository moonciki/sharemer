define(function(require, exports, module) {

    seajs.use('./css/module/video_info.css');
    seajs.use('./css/module/reply.css');
    seajs.use('./css/module/t_model.css');
    seajs.use('./css/module/share.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/video/tpl/VideoInfoView.tpl');

    var Template2 = require('module/video/tpl/FavModal.tpl');

    var Video = require('module/video/model/Video');

    var ReplyView = require('module/reply/view/ReplyView');

    var VideoInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        template2: Template2,
        current_mid : null,
        initialize: function() {
            this.model = new Video();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            this.$el.append(this.template2);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.video').removeClass("menu_unit").addClass("menu_unit_point");
            this.replyView = new ReplyView({
                oid: this.id,
                otype: 1,
                el: this.$el.find('.info_area_reply')
            });
        },

        events: {
            'click .v_fav_btn': 'favClick',
            'click .v_f_add_list_btn': 'quickAddFavList',
            'click .v_f_final_sub': 'vFinalSub'
        },

        vFinalSub: function () {
            var ids = [];
            this.$el.find('.fav_list_body input[type=checkbox]:checked').each(function(index, ele) {
                ids.push(ele.getAttribute('data-fav-id'));
            });
            if(ids.length == 0){
                alert("请至少选择一个~");
                return false;
            }
            var param = {};
            param.fav_ids = ids;

            var view = this;
            this.model.save_media_favs(param).done(function (resp) {
                if(resp.code == 0){
                    view.$el.find('.v_share_area').html(view.getAlReadyFavHtml());
                    view.$el.find('#fav_community_list').modal('hide');
                }
            });
        },

        quickAddFavList: function () {
            var user = window.SHION.currentUser;
            var title = this.$el.find(".v_f_fav_title").val();
            if(title == null || title.trim().length == 0){
                alert("请务必填写标题~");
                return false;
            }
            var is_private = this.$el.find('.v_f_is_private').prop('checked');
            var param = {};
            param.title = title;
            param.user_id = user.id;
            if(is_private){
                param.is_hide = 1;
            }else{
                param.is_hide = 0;
            }

            var view = this;
            this.model.quick_save_favs(param).done(function (resp) {
                if(resp.code == 0){
                    var html = "";
                    var fav = resp.result;
                    if(fav != null){
                        view.$el.find('.no_list_text').hide();
                        view.$el.find('.v_f_fav_title').val("");
                        view.$el.find('.v_f_is_private').prop('checked', false);
                        html+=view.getFavListOneHtml(fav);
                        view.$el.find('.fav_list_body').append(html);
                    }
                }
            });

        },

        favClick: function () {
            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                alert("要先登录哦~");
                return false;
            }
            var view = this;
            var param = {};
            param.o_type = 1;
            param.o_id = this.model.video_id;
            this.model.get_favs_by_uid(param).done(function (resp) {
                if(resp.code == 0){
                    view.$el.find('#fav_community_list').modal('show');
                    view.$el.find('.modal-fc-title').text("请选择收藏夹");
                    view.$el.find('.fav_list_body').html("");
                    var favs = resp.result;
                    if(favs.length == 0){
                        view.$el.find('.no_list_text').html("您还未创建过收藏夹呢~在下面快速创建一个吧~");
                    }else{
                        view.$el.find('.no_list_text').hide();
                        var html = "";
                        for(var i = 0; i < favs.length; i++){
                            html+=view.getFavListOneHtml(favs[i]);
                        }
                        view.$el.find('.fav_list_body').append(html);
                    }
                }
            });

        },

        /** 传入一个收藏夹对象，生成对应的单个html代码块*/
        getFavListOneHtml: function (fav) {
            var html = "<div class=\"tm_big_unit\">"+
            "<div class=\"tm_small_unit\"><input class=\"v_f_pointer\" data-fav-id='"+fav.id+"' type=\"checkbox\"/> "+fav.title+"</div>"+
            "<div class=\"tm_small_unit\">";

            if(fav.is_hide == 1){
                html+="<span class=\"label label-danger\">私有</span>"
            }else{
                html+="<span class=\"label label-primary\">公开</span>"
            }

            html+="</div></div>";
            return html;
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#video";
                return;
            }
            this.replyView.oid = id;
            this.model.video_id = id;
            this.getVideoInfo();
            /** 获取评论*/
            this.replyView.getReplies();
        },

        getVideoInfo: function () {
            var view = this;
            this.model.get_video_info().done(function(resp) {
                if (resp.code == 0) {
                    view.renderVideo(resp.result);
                }else{
                    this.$el.find('.main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        renderVideo: function (video) {
            if(video == null || video == undefined){
                window.location.href="/#video";
                return;
            }

            var view = this.$el;

            view.find('.v_info_title').text(video.title);
            switch(2){
                case 0 :
                    view.find('.v_info_player').html("<embed type=\"application/x-shockwave-flash\" class=\"edui-faked-video\" " +
                        "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" src=\""+video.video_url+"\" " +
                        "width=\"1050px\" height=\"500px\" wmode=\"transparent\" play=\"true\" loop=\"false\" menu=\"false\" allowfullscreen=\"true\">");
                    break;
                case 1:
                    break;
                case 2:
                    view.find('.v_info_player').html("<iframe style=\"width:1050px;height:500px;\" src=\""+video.video_url+"\" frameborder=\"0\"></iframe>");
                    break;
            }
            view.find('.j_desc').html(video.msg);

            view.find('.v_source').html("<img src='"+video.logo_url+"' height='15px'/>&nbsp;&nbsp;"+video.net_name);
            view.find('.v_source_url').html("<img src='"+video.logo_url+"' height='25px' style='border: 2px #ff8ab1 solid;border-radius: 100px'/>&nbsp;&nbsp;<a href='"+video.source_url+"' target='_blank'>这里播不了的话，就点我去源站观看吧(°∀°)ﾉ</a>");
            if(video.user != null){
                view.find('.user_info_area_01').html("<div class=\"user_info_area_02\" style=\"background-image: url('"+video.user.avater+"'); background-size: 100% 100%\"></div>");
                view.find('.v_info_uname').text(video.user.name);
                view.find('.lv').attr("style", "background-color:"+video.user.level_color).text("LV "+video.user.level);
                if(video.user.sex == 1){
                    view.find('.sex').addClass("tag_man");
                }else if(video.user.sex == 0){
                    view.find('.sex').addClass("tag_woman");
                }
                if(video.user.address != null){
                    view.find(".j_addr").text(video.user.address);
                }else{
                    view.find(".j_addr").text("未填写");
                }

                if(video.user.ctime != null){
                    view.find(".j_ctime").text(video.user.ctime.substring(0,10));
                }else {
                    view.find(".j_ctime").text("未填写");
                }

                view.find(".u_desc").text(video.user.desc);
            }

            var tag_htm = "";
            if(video.tags != null && video.tags.length > 0){
                for(var i = 0; i<video.tags.length; i++){
                    tag_htm+="<span class=\"v_tag_style\" onclick=\"location.href='#video/tag/"+video.tags[i].id+"';\">"+video.tags[i].tag_name+"</span>"
                }
            }

            view.find('.v_tags').html(tag_htm);

            if(video.is_faved == 1){
                view.find('.v_share_area').html(this.getAlReadyFavHtml());
            }

        },

        getAlReadyFavHtml: function () {
            return "<div class=\"v_player_btn_group v_y_fav_btn\"><span class=\"glyphicon glyphicon-star\"/> 已收藏 </div>"
        },
        getNotFavHtml: function () {
            return "<div class=\"v_player_btn_group v_fav_btn\"><span class=\"glyphicon glyphicon-star\"/> 收藏 </div>"
        }
    });

    module.exports = VideoInfoView;

});