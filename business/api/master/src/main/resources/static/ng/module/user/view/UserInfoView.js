define(function(require, exports, module) {

    seajs.use('./css/module/user_info.css');
    seajs.use('./css/module/fav.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/user/tpl/UserInfoView.tpl');

    var FavTemplate = require('module/fav/tpl/FavListUserPageView.tpl');

    var musTemplate = require('module/user/tpl/MusicList.tpl');

    var vTemplate = require('module/user/tpl/VideoList.tpl');

    var User = require('module/user/model/User');

    var PlayView = require('module/common/view/PlayView');

    var UserInfoView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        fav_need_template: $($(FavTemplate).filter("#need_mr").html().trim()),
        fav_not_template: $($(FavTemplate).filter("#not_mr").html().trim()),

        init_mus: $($(musTemplate).filter('#init_mus').html().trim()),
        mus_content: $($(musTemplate).filter('#musics_info_unit').html().trim()),

        init_video: $($(vTemplate).filter('#init_video').html().trim()),
        video_content: $($(vTemplate).filter('#videos_info_unit').html().trim()),

        initialize: function() {
            this.model = new User();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.user').removeClass("menu_unit").addClass("menu_unit_point");
            this.playView = new PlayView({
                el: this.$el
            });
        },

        events: {
            'click .info_home': 'tagHome',
            'click .info_fav': 'tagSc',
            'click .info_music': 'tagMusic',
            'click .more_music': 'moreMusic',
            'click .info_video': 'tagVideo',
            'click .more_video': 'moreVideo',
            'click .info_media_sort': 'mediaSort',
            'click .common_play': 'commonPlay'
        },

        commonPlay: function (event) {
            this.playView.commonPlay(event);
        },

        mediaSort: function () {
            var sort = this.$el.find('.info_media_sort').attr('data-sort');
            var type = this.$el.find('.info_media_sort').attr('data-type');
            this.model.current_sort = sort;
            this.model.current_page = 1;
            this.initSortStyle();
            var param = {};
            param.uid=this.model.user_id;
            param.sort=this.model.current_sort;
            param.c_p=this.model.current_page;

            if(type == 0){
                this.$el.find(".mus_list_content").html("");
                this.getMusicsByUid(param);
            }
            if(type == 1){
                this.$el.find(".video_list_content").html("");
                this.getVideosByUid(param);
            }
        },

        initSortStyle: function () {
           if(this.model.current_sort == 1){
               this.$el.find('.time_dx').removeClass('info_media_sort').addClass('info_media_sort_pointer');
               this.$el.find('.time_zx').removeClass('info_media_sort_pointer').addClass('info_media_sort');
           }
           if(this.model.current_sort == 0){
               this.$el.find('.time_zx').removeClass('info_media_sort').addClass('info_media_sort_pointer');
               this.$el.find('.time_dx').removeClass('info_media_sort_pointer').addClass('info_media_sort');
           }
        },

        tagHome: function () {
            var view = this;
            view.menuChange(view.$el.find(".info_home"));
            view.$el.find(".u_info_home_top").html("<div class=\"u_info_tag u_info_tag_home\"></div>首页")
            view.$el.find(".u_info_all_main").html("");
            view.$el.find(".more_add").html("");
        },

        tagSc: function () {
            this.$el.find(".more_add").html("");
            var view = this;
            var param = {};
            this.model.get_fav_list_by_uid(param).done(function(resp) {
                if (resp.code == 0) {
                    view.$el.find(".u_info_home_top").html("<div class=\"u_info_tag u_info_tag_sc\"></div>收藏单<div class='info_fav_num'>共有<span class='info_fav_down_text_point'> "+resp.result.count+" </span>个收藏单</div>");
                    view.menuChange(view.$el.find(".info_fav"));
                    var div = [];
                    var isMr = false;
                    var favs = resp.result.favs;
                    for(var i = 1; i <= favs.length; i++){
                        if(i%4 === 0){
                            isMr = false;
                        }else{
                            isMr = true;
                        }
                        div.push(view.renderOne(favs[i-1], isMr));
                    }
                    view.$el.find(".u_info_all_main").html(div);
                }
            });
        },

        tagMusic: function () {
            var view = this;
            var param = {};
            param.uid=this.model.user_id;
            param.sort=1;
            param.c_p=1;
            this.model.current_page = 1;
            this.model.current_sort = 1;
            var $div = this.init_mus.clone();
            this.$el.find('.more_add').html("<button type=\"button\" class=\"more_music btn btn-primary btn-lg btn-block\">加载更多</button>");
            view.menuChange(view.$el.find(".info_music"));
            view.$el.find(".u_info_home_top").html("<div class=\"u_info_tag u_info_tag_music\"></div>音乐分享<div class='info_media_sort_pointer time_dx' data-type='0' data-sort='1'>按时间倒序</div><div class='info_media_sort time_zx' data-type='0' data-sort='0'>按时间正序</div>");
            view.$el.find(".u_info_all_main").html($div);
            this.getMusicsByUid(param);
        },

        tagVideo: function () {
            var view = this;
            var param = {};
            param.uid=this.model.user_id;
            param.sort=1;
            param.c_p=1;
            this.model.current_page = 1;
            this.model.current_sort = 1;
            var $div = this.init_video.clone();
            this.$el.find('.more_add').html("<button type=\"button\" class=\"more_video btn btn-primary btn-lg btn-block\">加载更多</button>")
            view.menuChange(view.$el.find(".info_video"));
            view.$el.find(".u_info_home_top").html("<div class=\"u_info_tag u_info_tag_video\"></div>视频分享<div class='info_media_sort_pointer time_dx' data-type='1' data-sort='1'>按时间倒序</div><div class='info_media_sort time_zx' data-type='1' data-sort='0'>按时间正序</div>");
            view.$el.find(".u_info_all_main").html($div);
            this.getVideosByUid(param);
        },

        moreMusic: function () {
            var param = {};
            param.uid=this.model.user_id;
            param.sort=this.model.current_sort;
            param.c_p=this.model.current_page;
            this.getMusicsByUid(param);
        },

        moreVideo: function () {
            var param = {};
            param.uid=this.model.user_id;
            param.sort=this.model.current_sort;
            param.c_p=this.model.current_page;
            this.getVideosByUid(param);
        },

        getMusicsByUid: function (param) {
            var view = this;
            this.model.get_music_by_uid(param).done(function(resp) {
                if (resp.code === 0) {
                    var div = [];
                    var mus = resp.result;
                    if(mus != null && mus !== undefined && mus.length > 0){
                        for(var i = 0; i < mus.length; i++){
                            if((i+1)%4 !== 0){
                                div.push(view.renderMusic(mus[i], true));
                            }else{
                                div.push(view.renderMusic(mus[i], false));
                            }

                        }
                        view.$el.find(".mus_list_content").append(div);
                        view.model.current_page+=1;
                    }else{
                        alert("已经拉到底没数据啦ヽ(•̀ω•́ )ゝ");
                        view.$el.find('.more_music').attr("disabled", "disabled");
                    }
                }
            });
        },

        getVideosByUid: function (param) {
            var view = this;
            this.model.get_video_by_uid(param).done(function(resp) {
                if (resp.code === 0) {
                    var div = [];
                    var vs = resp.result;
                    if(vs != null && vs !== undefined && vs.length > 0){
                        for(var i = 0; i < vs.length; i++){
                            if((i+1)%4 !== 0) {
                                div.push(view.renderVideo(vs[i], true));
                            }else {
                                div.push(view.renderVideo(vs[i], false));
                            }
                        }
                        view.$el.find(".video_list_content").append(div);
                        view.model.current_page+=1;
                    }else{
                        alert("已经拉到底没数据啦ヽ(•̀ω•́ )ゝ");
                        view.$el.find('.more_video').attr("disabled", "disabled");
                    }
                }
            });
        },

        renderMusic: function (mus, right) {
            var $div = this.mus_content.clone();

            if(right){
                $div.addClass("u_media_unit_jx");
            }

            $div.find('.u_info_mus_unit_1').html("<div class='common_play u_timeline_music_cover' style=\"background-image: url('"+mus.cover+"')\"><div class='u_timeline_music_back_play' data-type='0' data-url='//music.163.com/outchain/player?type=2&id="+mus.song_id+"&auto=1&height=66' data-title='"+mus.title+"'></div></div>");
            $div.find('.u_info_mus_unit_2').text(mus.title);

            return $div;
        },

        renderVideo: function (vs, right) {
            var $div = this.video_content.clone();

            if(right){
                $div.addClass("u_media_unit_jx");
            }

            $div.find('.u_info_video_unit_1').html("<div class='common_play u_timeline_video_cover' style=\"background-image: url('"+vs.cover+"'), url('/image/default_v.jpg')\"><div class='u_timeline_video_back_play' data-type='1' data-url='"+vs.video_url+"' data-title='"+vs.title+"'></div></div>");
            if(vs.title.length > 35){
                $div.find('.u_info_video_unit_2').text(vs.title.substring(0, 35));
            }else{
                $div.find('.u_info_video_unit_2').text(vs.title);
            }

            return $div;
        },

        renderOne: function (fav, isMr) {
            var $div;
            if(!isMr){
                $div = this.fav_not_template.clone();
            }else{
                $div = this.fav_need_template.clone();
            }
            $div.attr("style","background-image:url('"+fav.cover+"'),url('/image/default_cover.jpg')");
            $div.attr("onclick", "window.open('#favlist/info/"+fav.id+"')");
            $div.find(".info_fav_title").text(fav.title);
            $div.find(".info_fav_down_text_point").text(0);
            return $div;
        },

        menuChange: function ($div) {
            this.$el.find(".u_info_bottom_menu_unit_pointer").removeClass("u_info_bottom_menu_unit_pointer").addClass("u_info_bottom_menu_unit");
            $div.removeClass("u_info_bottom_menu_unit").addClass("u_info_bottom_menu_unit_pointer");
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#";
                return;
            }
            this.model.user_id = id;
            this.tagHome();
            this.getUserInfo();
        },

        getUserInfo: function () {
            var view = this;
            this.model.get_user_by_id().done(function(resp) {
                if (resp.code == 0) {
                    view.renderUser(resp.result.user_info);
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

        renderUser: function (user) {
            if(user == null || user == undefined){
                window.location.href="/#";
                return;
            }

            var view = this.$el;
            var avaterHtm = "";
            var lvHtm = "";
            view.find('.j_info_uname').text(user.name);
            if(user.sex == 0){
                avaterHtm+="<div class=\"u_info_avater u_g\"";
                lvHtm+="&nbsp;&nbsp; <div class=\"u_tag_sex u_tag_sex_g\"></div>";
            }else{
                avaterHtm+="<div class=\"u_info_avater u_b\"";
                lvHtm+="&nbsp;&nbsp; <div class=\"u_tag_sex u_tag_sex_b\"></div>";
            }
            avaterHtm+="style=\"background-image: url('"+user.avater+"');\"></div>";

            view.find('.j_info_avater').html(avaterHtm);

            lvHtm+="&nbsp;&nbsp; <span class=\"badge lv\" style=\"background-color:"+user.level_color+"\">LV "+user.level+"</span>"

            view.find(".j_lv_sex").html(lvHtm);

            view.find(".u_info_msg_text").text(user.desc);

            view.find('.j_u_addr').text(user.address);

            view.find('.j_u_ctime').text(user.ctime.replace("T", " ").substring(0, 16));
            if(user.born != null && user.born != undefined){
                view.find('.j_u_born').text(user.born.substring(0, 10));
            }else{
                view.find('.j_u_born').text("未知");
            }
            view.find('.j_u_uid').text(user.id);
        }
    });

    module.exports = UserInfoView;

});