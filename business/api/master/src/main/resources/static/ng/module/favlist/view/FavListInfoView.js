define(function(require, exports, module) {

    seajs.use('./css/module/favlist_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/favlist/tpl/FavListInfoView.tpl');

    var FavList = require('module/favlist/model/FavList');

    var ReplyView = require('module/reply/view/ReplyView');

    var Pagination = require('component/Pagination');

    var FavListInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        $mtrTemplate: $($(Template).filter('#fl_m_tr_template').html().trim()),
        initialize: function() {
            this.model = new FavList();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.favlist').removeClass("menu_unit").addClass("menu_unit_point");

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.transferPage(newPageNo);
                }
            });

            this.replyView = new ReplyView({
                oid: this.id,
                otype: 0,
                el: this.$el.find('.info_area_reply')
            });
        },

        events: {
            'click .fl_tag': 'flTags',
            'click .j_fl_m_play':'fmPlay',
            'click .j_fl_m_stop':'fmStop',

            'click .music_list':'initMusicList',
            'click .video_list': 'initVideoList'
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#favlist";
                return;
            }
            this.replyView.oid = id;
            this.model.favlist_id = id;
            var view = this;
            this.model.get_fav_info().done(function (resp) {
                if(resp.code == 0){
                    if(resp.result != null && resp.result != undefined){
                        var info = resp.result;
                        if(info.fav.id == null || info.fav.id == undefined){
                            view.$el.find('.fl_main_data_area_info').html("<h1>-未找到相关信息-</h1>");
                            return false;
                        }
                        view.$el.find('.fl_main_data_head_cover').html("<img src='"+info.fav.cover+"' class='cover_img'/>");
                        view.$el.find('.fl_main_data_head_title').text(info.fav.title.substring(0, 40));
                        var tagHtml = "<div class='fl_tag_info fl_tag_bq'></div><span class='fl_tag_text'>标签：</span>";
                        if(info.tags != null && info.tags.length > 0){
                            for(var i = 0; i < info.tags.length; i++){
                                tagHtml+="<span class=\"fl_tag\" data-tag-id='"+info.tags[i].id+"'>"+info.tags[i].tag_name+"</span>"
                            }
                        }else{
                            tagHtml+="暂无任何标签"
                        }
                        var infoHtml = "<div class='fl_tag_info fl_tag_user'></div><span class='fl_tag_text'>创建人：</span><a href='#user/info/"+info.user.id+"' target='_blank'>"+info.user.name+"</a>";
                        infoHtml+="<div class='fl_tag_info fl_tag_time'></div><span class='fl_tag_text'>创建时间：</span>"+info.fav.ctime.replace("T", " ");
                        view.$el.find('.fl_main_data_head_tag').html(tagHtml);
                        view.$el.find('.fl_main_data_head_info').html(infoHtml);
                        view.$el.find('.music_num').text(info.m_count);
                        view.$el.find('.video_num').text(info.v_count);
                        view.$el.find('.gaojian_num').text(info.g_count);
                    }
                }else{
                    view.$el.find('.fl_main_data_area_info').html("<h1>-内部错误-</h1>");
                }
            });

            this.musicList(1);

            this.replyView.getReplies();
        },

        transferPage(pageNo){
            if(this.model.current_type == 0){
                this.musicList(pageNo);
            }else{
                this.videoList(pageNo);
            }
        },

        initMusicList: function(){
            this.musicList(1);
        },

        musicList:function(pageNo){

            this.model.current_type = 0;
            this.$el.find('.fl_main_data_body_menu_unit_pointer').removeClass("fl_main_data_body_menu_unit_pointer").addClass("fl_main_data_body_menu_unit");
            this.$el.find('.music_list').removeClass("fl_main_data_body_menu_unit").addClass("fl_main_data_body_menu_unit_pointer");

            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;

            var view = this;
            this.model.get_fav_musics().done(function (resp) {
                var page = view.model.lastPage,
                    musics = page.records;
                if(resp.code == 0) {
                    var trs = [];
                    if(musics.length > 0){
                        for (var i = 0; i < musics.length; i++) {
                            trs.push(view._renderOne(musics[i]));
                        }
                        view.$el.find('tbody').html(trs);
                    }else{
                        view.$el.find('tbody').html("");
                    }
                    view.pagination.render(page.pageNo, page.pageCount, page.totalCount);
                }
            });
        },

        initVideoList: function(){
            this.videoList(1);
        },

        videoList:function(pageNo){

            this.model.current_type = 1;
            this.$el.find('.fl_main_data_body_menu_unit_pointer').removeClass("fl_main_data_body_menu_unit_pointer").addClass("fl_main_data_body_menu_unit");
            this.$el.find('.video_list').removeClass("fl_main_data_body_menu_unit").addClass("fl_main_data_body_menu_unit_pointer");

            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;

            var view = this;
            this.model.get_fav_videos().done(function (resp) {
                var page = view.model.lastPage,
                    videos = page.records;
                if(resp.code == 0) {
                    var trs = [];
                    if(videos.length > 0){
                        for (var i = 0; i < videos.length; i++) {
                            trs.push(view._renderVideoOne(videos[i]));
                        }
                        view.$el.find('tbody').html(trs);
                    }else{
                        view.$el.find('tbody').html("");
                    }
                    view.pagination.render(page.pageNo, page.pageCount, page.totalCount);
                }
            });
        },

        _renderOne: function(music) {
            var $tr = this.$mtrTemplate.clone();
            $tr.find('.j_fl_m_id').text(music.id);
            $tr.find('.j_fl_m_cover').html("<img src='"+(music.cover+"?imageView2/1/w/50/h/50")+"' width='50px'/>")
            $tr.find('.j_fl_m_title').html("<a href='/#music/info/"+music.id+"' target='_blank'>"+music.title+"</a>");
            $tr.find('.j_fl_m_songer').text(music.songer);
            $tr.find('.j_fl_m_rname').html("<a href='/#user/info/"+music.uid+"' target='_blank'>"+music.uname+"</a>")
            $tr.find('.j_fl_m_cz').attr("data-song-id", music.song_id).attr("data-cover", music.cover).html(this.playHtml());
            return $tr;
        },

        _renderVideoOne: function(video) {
            var $tr = this.$mtrTemplate.clone();
            $tr.find('.j_fl_m_id').text(video.id);
            $tr.find('.j_fl_m_cover').html("<img src='"+(video.cover+"?imageView2/1/w/80/h/50")+"' width='50px'/>")
            $tr.find('.j_fl_m_title').html("<a href='/#video/info/"+video.id+"' target='_blank'>"+video.title+"</a>");
            $tr.find('.j_fl_m_songer').html("<img src='"+video.logo_url+"' height='25px'/>&nbsp;&nbsp;<span style='color: "+video.net_color+"'>"+video.net_name+"</span>");
            $tr.find('.j_fl_m_rname').html("<a href='/#user/info/"+video.r_id+"' target='_blank'>"+video.r_name+"</a>")
            $tr.find('.j_fl_m_cz').attr("data-song-id", video.v_id).attr("data-cover", video.cover).html(this.playHtml());
            return $tr;
        },




















        fmStop: function (event) {
            var $div = $(event.target).parents('.j_fl_m_cz');
            var song_id = $div.attr("data-song-id");
            var cover = $div.attr("data-cover");
            this.putPlayHtml(cover, song_id, 0);
            this.$el.find('.j_fl_m_stop').parent().html(this.playHtml());
            $div.html(this.playHtml());
        },

        fmPlay: function (event) {
            var $div = $(event.target).parents('.j_fl_m_cz');
            var song_id = $div.attr("data-song-id");
            var cover = $div.attr("data-cover");
            this.putPlayHtml(cover, song_id, 1);
            this.$el.find('.j_fl_m_stop').parent().html(this.playHtml());
            $div.html(this.playingHtml());
        },

        playHtml: function () {
            return "<div title='去详情页点击分享到社区吧~' class=\"fl_player_btn_group fl_share_btn\"><span class=\"glyphicon glyphicon-share-alt\"/> 去分享</div>&nbsp;&nbsp;<div title='点击播放' class=\"fl_player_btn_group fl_play_btn j_fl_m_play\"><span class=\"glyphicon glyphicon-play-circle\"/> 播放</div>";
        },

        playingHtml: function () {
            return "<div title='去详情页点击分享到社区吧~' class=\"fl_player_btn_group fl_share_btn\"><span class=\"glyphicon glyphicon-share-alt\"/> 去分享</div>&nbsp;&nbsp;<div title='点击停止播放' class=\"fl_player_btn_group fl_playing_btn j_fl_m_stop\"><span class=\"glyphicon glyphicon-volume-up\"/> 放送中...</div>";
        },

        flTags: function (event) {
            var $div = $(event.target);
            var tag_id = $div.attr("data-tag-id");
            window.location.href="/#favlist/tag/"+tag_id;
        },
    });

    module.exports = FavListInfoView;

});