define(function(require, exports, module) {

    seajs.use('./css/module/music_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/music/tpl/MusicInfoView.tpl');

    var Music = require('module/music/model/Music');

    var ReplyView = require('module/reply/view/ReplyView');

    var FavView = require('module/fav/view/FavView');

    var PlayView = require('module/common/view/PlayView');

    var MusicInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,


        initialize: function() {
            this.model = new Music();
            this.$el.append(this.m_template);
            this.$el.append(this.template);

            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.music').removeClass("menu_unit").addClass("menu_unit_point");

            this.replyView = new ReplyView({
                oid: this.id,
                otype: 0,
                el: this.$el.find('.info_area_reply')
            });
            this.favView = new FavView({
                oid: this.id,
                otype: 0,
                el: this.$el.find('.v_share_area')
            });

            this.playView = new PlayView({
                el: this.$el
            });
        },

        events: {
            'click .common_play': 'commonPlay'
        },

        commonPlay: function (event) {
            this.playView.commonPlay(event);
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#music";
                return;
            }
            this.favView.oid = id;
            this.replyView.oid = id;
            this.model.music_id = id;

            this.getMusicInfo();
            this.getRelateVideo();
            this.favView.initNotFav();
            this.replyView.getReplies();

        },

        getMusicInfo: function () {
            var view = this;
            this.model.get_music_info().done(function(resp) {
                if (resp.code == 0) {
                    view.renderMusic(resp.result);
                }else{
                    this.$el.find('.main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        getRelateVideo: function () {
            var view = this;
            this.model.get_relate_video().done(function (resp) {
                if(resp.code == 0){
                    view.renderVideos(resp.result);
                }else{
                    view.$el.find('.data_main_video_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        renderMusic: function (music) {
            if(music == null || music == undefined){
                window.location.href="/#music";
                return;
            }
            if(music.is_faved == 1){
                this.favView.initAlreadyFav();
            }
            var view = this.$el;
            view.find('.title').text(music.title);
            view.find('.singer').html("<span class='tag_base tag_songer'></span>&nbsp;&nbsp;<b>音乐人：</b>"+music.songer);
            if(music.publish_time != null && music.publish_time != undefined && music.publish_time != ""){
                view.find('.publish_time').html("<span class='tag_base tag_time'></span>&nbsp;&nbsp;<b>发行时间：</b>"+music.publish_time.replace("T00:00", ""));
            }else{
                view.find('.publish_time').html("<span class='tag_base tag_time'></span>&nbsp;&nbsp;<b>发行时间：</b>未知");
            }

            view.find('.album').html("<span class='tag_base tag_album'></span>&nbsp;&nbsp;<b>专辑：</b>"+music.album_title);

            if(music.mv_id != null && music.mv_id != undefined && music.mv_id != ""){
                view.find('.mv_data').html("<span class='tag_base tag_mv'></span>&nbsp;&nbsp;<b>MV：</b><a href='http://music.163.com/#/mv?id="+music.mv_id+"' target='_blank'>去分享源站观看</a>");
            }else{
                view.find('.mv_data').html("<span class='tag_base tag_mv'></span>&nbsp;&nbsp;<b>MV：</b>暂无MV资源");
            }

            view.find('.uname').html("<span class='tag_base tag_share'></span>&nbsp;&nbsp;<b>分享提供者：</b><a href=\"#user/info/"+music.r_id+"\" target='_blank'>"+music.r_name+"</a>")
            view.find('.source_url').html("<a href=\""+music.source_url+"\" target='_blank'>可以点我去源站听哦(｡･ω･｡)</a>")
            view.find('.data_info_msg').text(music.other_msg);
            view.find('.img_data_info').attr("style", "background-image:url('"+(music.cover+"?imageView2/1/w/327/h/327")+"'),url('/image/default_cover.jpg')");
            view.find('.player_data_info').html("<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=330 height=86 src=\"//music.163.com/outchain/player?type=2&id="+music.song_id+"&auto=0&height=66\"></iframe>");

            if(music.tags != null && music.tags.length > 0){
                for(var i = 0; i < music.tags.length; i++){
                    view.find('.tags').append("<span class='tag_style' onclick=\"location.href='#music/tag/"+music.tags[i].id+"';\">"+music.tags[i].tag_name+"</span>")
                }
            }
        },

        renderVideos: function (video) {
            var view = this;
            if(video != null && video != undefined && video.length > 0){
                var html = "";
                for(var i = 0; i < video.length; i++){
                    html+="<div class=\"data_main_video_area_unit\">" +
                    "<div class=\"data_main_video_area_unit_01\" title='点击播放' style=\"background-size: 100% 100%;background-image: url('"+(video[i].cover+"?imageView2/1/w/227/h/150")+"'), url('/image/default_v.jpg')\"><div class='common_play data_video_play_on' data-type='1' data-url='"+video[i].video_url+"' data-title='"+video[i].title+"'></div></div>"+
                            "<div class='data_main_video_area_unit_02'><a href='/#video/info/"+video[i].id+"' target='_blank'>"+video[i].title+"</a><br/><br/>"+
                            "<span class='tag_base' style=\"background-size: 100%, 100%; background-image: url('"+video[i].logo_url+"')\"></span>&nbsp;&nbsp;<b>来源：</b> "+video[i].net_name+
                        "</div></div>"
                }
                view.$el.find('.data_main_video_area').html(html);
            }else{
                view.$el.find('.data_main_video_area').html("<h1>暂无关联视频</h1>")
            }
        }
    });

    module.exports = MusicInfoView;

});