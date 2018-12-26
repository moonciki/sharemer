define(function(require, exports, module) {

    seajs.use('./css/module/video_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/video/tpl/VideoInfoView.tpl');

    var Video = require('module/video/model/Video');

    var FavView = require('module/fav/view/FavView');

    var ReplyView = require('module/reply/view/ReplyView');

    var VideoInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        current_mid : null,
        initialize: function() {
            this.model = new Video();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.video').removeClass("menu_unit").addClass("menu_unit_point");
            this.replyView = new ReplyView({
                oid: this.id,
                otype: 1,
                el: this.$el.find('.info_area_reply')
            });

            this.favView = new FavView({
                oid: this.id,
                otype: 1,
                el: this.$el.find('.v_share_area')
            });
        },

        events: {},

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#video";
                return;
            }
            this.favView.oid = id;
            this.replyView.oid = id;
            this.model.video_id = id;
            this.getVideoInfo();
            this.favView.initNotFav();
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
            var reg = new RegExp("\n","g");
            view.find('.j_desc').html(video.msg.replace(reg, "<br/>"));

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
                this.favView.initAlreadyFav();
            }

        }
    });

    module.exports = VideoInfoView;

});