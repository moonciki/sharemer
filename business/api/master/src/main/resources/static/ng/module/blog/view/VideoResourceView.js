define(function (require, exports, module) {

    seajs.use('../css/module/blog/resource.css');
    seajs.use('../css/module/blog/resource_video.css');

    var videoResourceHtml = require('module/blog/tpl/VideoResourceView.tpl');
    var VideoResource = require('module/blog/model/VideoResource');
    var CommonView = require('module/blog/view/CommonView');
    var PlayView = require('module/common/view/PlayView');

    var VideoResourceView = Backbone.View.extend({
        videoResource: videoResourceHtml,
        current_mid: null,
        initialize: function () {
            this.model = new VideoResource();
            this.commonView = new CommonView();
        },

        events: {
            'click .user_header_menu_box': 'router',

            'click .more_video': 'moreVideo',
            'click .info_media_sort': 'mediaSort',
            'click .resource_path': 'resourcePath',
            'click .common_play': 'commonPlay'
        },

        router: function (event) {
            var role = event.currentTarget.getAttribute("data-role");
            this.commonView.router(role);
        },

        resourcePath: function (event) {
            var role = event.currentTarget.getAttribute("data-role");
            switch (role) {
                case "favlist":
                    window.location.href = "/blog/#resource/favlist/" + this.model.id;
                    break;
                case "music":
                    window.location.href = "/blog/#resource/music/" + this.model.id;
                    break;
                case "video":
                    window.location.href = "/blog/#resource/video/" + this.model.id;
                    break;
                case "archive":
                    window.location.href = "/blog/#resource/archive/" + this.model.id;
                    break;
            }
        },

        request: function (id) {
            this.commonView.initCommon(id, this.$el, this.videoResource, '.menu_resource');
            this.model.id = id;
            this.$el.find(".resource_infos").html("");
            this.playView = new PlayView({
                el: this.$el
            });
            this.tagVideo();
        },

        mediaSort: function () {
            var sort = this.$el.find('.info_media_sort').attr('data-sort');
            if (sort == undefined) {
                sort = 1;
            }
            this.model.current_sort = sort;
            this.model.current_page = 1;
            this.initSortStyle();
            var param = {};
            param.uid = this.model.id;
            param.sort = this.model.current_sort;
            param.c_p = this.model.current_page;
            this.$el.find(".resource_infos").html("");
            this.$el.find('.more_video').removeAttr("disabled");
            this.getVideosByUid(param);
        },

        initSortStyle: function () {
            if (this.model.current_sort == 1) {
                this.$el.find('.time_dx').removeClass('info_media_sort').addClass('info_media_sort_pointer');
                this.$el.find('.time_zx').removeClass('info_media_sort_pointer').addClass('info_media_sort');
            }
            if (this.model.current_sort == 0) {
                this.$el.find('.time_zx').removeClass('info_media_sort').addClass('info_media_sort_pointer');
                this.$el.find('.time_dx').removeClass('info_media_sort_pointer').addClass('info_media_sort');
            }
        },

        moreVideo: function () {
            var param = {};
            param.uid = this.model.id;
            param.sort = this.model.current_sort;
            param.c_p = this.model.current_page;
            this.getVideosByUid(param);
        },

        tagVideo: function () {
            var view = this;
            var param = {};
            param.uid = this.model.id;
            param.sort = 1;
            param.c_p = 1;
            this.model.current_page = 1;
            this.model.current_sort = 1;
            this.$el.find('.more_add').html("<button type=\"button\" class=\"more_video btn btn-info btn-lg btn-block\">加载更多</button>");
            view.$el.find(".resource_body_menu_title").html("<div class=\"u_info_tag u_info_tag_video\"></div><div class='info_media_sort_pointer time_dx' data-sort='1'>按时间倒序</div><div class='info_media_sort time_zx' data-sort='0'>按时间正序</div>");
            this.getVideosByUid(param);
        },

        getVideosByUid: function (param) {
            var view = this;
            this.model.get_video_by_uid(param).done(function (resp) {
                if (resp.code === 0) {
                    var div = [];
                    var mus = resp.result;
                    if (mus != null && mus !== undefined && mus.length > 0) {
                        for (var i = 0; i < mus.length; i++) {
                            div.push(view.renderVideo(mus[i], true));
                        }
                        view.$el.find(".resource_infos").append(div);
                        view.model.current_page += 1;
                    } else {
                        alert("已经拉到底没数据啦ヽ(•̀ω•́ )ゝ");
                        view.$el.find('.more_video').attr("disabled", "disabled");
                    }
                }
            });
        },

        renderVideo: function (mus) {
            var title = mus.title;
            if (title != null && title.length > 11) {
                title = title.substring(0, 11) + "...";
            }
            var ctime = mus.ctime;
            if (ctime != null) {
                ctime = ctime.replace("T", " ");
            }
            var $div = "<div class='resource_video_unit'>" +
                "<div title=\"" + mus.title + "\" class='common_play resource_video_unit_top' style=\"background-image: url('" + mus.cover + "?imageView2/1/w/160/h/110'), url('/image/default_v.jpg')\">" +
                "<div class='resource_video_unit_top_tip' style='background-color: "+mus.net_color+"'>"+mus.net_name+"</div><div class='resource_video_unit_top_play' data-type='1' data-url=\"" + mus.video_url + "\" data-title=\"" + mus.title + "\"></div></div>" +
                "<span style='line-height: 1.8; font-size: 13px'><a href='/#video/info/" + mus.id + "' target='_blank'>" + title + "</a><br/><span style='font-size: 12px; color: #949494'>分享于" + ctime + "</span></span></div>";
            return $div;
        },

        commonPlay: function (event) {
            this.playView.commonPlay(event);
        }
    });
    module.exports = VideoResourceView;
});