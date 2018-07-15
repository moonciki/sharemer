define(function(require, exports, module) {

    seajs.use('./css/module/favlist_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/favlist/tpl/FavListInfoView.tpl');

    var FavList = require('module/favlist/model/FavList');

    var ReplyView = require('module/reply/view/ReplyView');

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

            this.replyView = new ReplyView({
                oid: this.id,
                otype: 0,
                el: this.$el.find('.info_area_reply')
            });
        },

        events: {
            'click .fl_tags': 'flTags',
            'click .j_fl_m_play':'fmPlay',
            'click .j_fl_m_stop':'fmStop'
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

        putPlayHtml: function (cover, song_id, auto) {
            this.$el.find('.fl_main_info_player').html("<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"1050\" height=\"90\" src=\"//music.163.com/outchain/player?type=2&amp;id="+song_id+"&amp;auto="+auto+"&amp;height=66\"></iframe>");
            if(auto == 1){
                this.$el.find('.fl_main_info_area_main_2_1_base').attr("style", "background-image:url('"+cover+"'),url('/image/default_cover.jpg')").addClass("fl_main_info_area_main_2_1_cp");
            }else{
                this.$el.find('.fl_main_info_area_main_2_1_base').attr("style", "background-image:url('"+cover+"'),url('/image/default_cover.jpg')").removeClass("fl_main_info_area_main_2_1_cp");
            }
        },

        flTags: function (event) {
            var $div = $(event.target);
            var tag_id = $div.attr("data-tag-id");
            window.location.href="/#favlist/tag/"+tag_id;
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
                        view.$el.find('.fl_main_info_area').attr("style", "background-image: url('"+info.fav.cover+"');");
                        view.$el.find('.fl_main_info_area_1_1').html("<img src='"+info.fav.cover+"' class='cover_img'/>");
                        view.$el.find('.fl_title_font').text(info.fav.title);
                        var tagHtml = "";
                        if(info.tags != null && info.tags.length > 0){
                            for(var i = 0; i < info.tags.length; i++){
                                tagHtml+="<span class=\"fl_tag\" data-tag-id='"+info.tags[i].id+"'>"+info.tags[i].tag_name+"</span>"
                            }
                        }
                        view.$el.find('.fl_tags').html(tagHtml);
                        view.$el.find('.fl_tags').html(tagHtml);
                        view.$el.find('.fl_m_count').text(info.m_count);
                        view.$el.find('.fl_v_count').text(info.v_count);
                        view.$el.find('.fl_g_count').text(info.g_count);
                    }
                }else{
                    view.$el.find('.fl_main_info_area').html("<h1>-内部错误-</h1>");
                }
            });

            var view = this;
            this.model.get_fav_musics().done(function (resp) {
                if(resp.code == 0) {
                    var trs = [];
                    if(resp.result.length > 0){
                        view.putPlayHtml(resp.result[0].cover, resp.result[0].song_id, 0);
                        for (var i = 0; i < resp.result.length; i++) {
                            trs.push(view._renderOne(resp.result[i]));
                        }
                        view.$el.find('tbody').html(trs);
                    }
                }
            });

            this.replyView.getReplies();
        },

        _renderOne: function(music) {
            var $tr = this.$mtrTemplate.clone();
            $tr.find('.j_fl_m_id').text(music.id);
            $tr.find('.j_fl_m_title').html("<a href='/#music/info/"+music.id+"' target='_blank'>"+music.title+"</a>");
            $tr.find('.j_fl_m_songer').text(music.songer);
            $tr.find('.j_fl_m_rname').html("<a href='/#user/info/"+music.uid+"' target='_blank'>"+music.uname+"</a>")
            $tr.find('.j_fl_m_cz').attr("data-song-id", music.song_id).attr("data-cover", music.cover).html(this.playHtml());
            return $tr;
        }
    });

    module.exports = FavListInfoView;

});