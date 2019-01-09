define(function (require, exports, module) {

    seajs.use('../css/module/blog/resource.css');
    seajs.use('../css/module/blog/resource_music.css');

    var musicResourceHtml = require('module/blog/tpl/MusicResourceView.tpl');
    var MusicResource = require('module/blog/model/MusicResource');
    var mydate = null;
    var CommonView = require('module/blog/view/CommonView');

    var MusicResourceView = Backbone.View.extend({
        musicResource: musicResourceHtml,
        current_mid: null,
        initialize: function () {
            this.model = new MusicResource();
            mydate = new Date();
            this.commonView = new CommonView();
        },

        events: {
            'click .user_header_menu_box': 'router',

            'click .more_music': 'moreMusic',
            'click .info_media_sort': 'mediaSort',
            'click .resource_path': 'resourcePath'
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
            this.commonView.initCommon(id, this.$el, this.musicResource, '.menu_resource');
            this.model.id = id;
            this.$el.find(".resource_infos").html("");
            this.tagMusic();
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
            this.$el.find('.more_music').removeAttr("disabled");
            this.getMusicsByUid(param);
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

        moreMusic: function () {
            var param = {};
            param.uid = this.model.id;
            param.sort = this.model.current_sort;
            param.c_p = this.model.current_page;
            this.getMusicsByUid(param);
        },

        tagMusic: function () {
            var view = this;
            var param = {};
            param.uid = this.model.id;
            param.sort = 1;
            param.c_p = 1;
            this.model.current_page = 1;
            this.model.current_sort = 1;
            this.$el.find('.more_add').html("<button type=\"button\" class=\"more_music btn btn-primary btn-lg btn-block\">加载更多</button>");
            view.$el.find(".resource_body_menu_title").html("<div class=\"u_info_tag u_info_tag_music\"></div><div class='info_media_sort_pointer time_dx' data-sort='1'>按时间倒序</div><div class='info_media_sort time_zx' data-sort='0'>按时间正序</div>");
            this.getMusicsByUid(param);
        },

        getMusicsByUid: function (param) {
            var view = this;
            this.model.get_music_by_uid(param).done(function (resp) {
                if (resp.code === 0) {
                    var div = [];
                    var mus = resp.result;
                    if (mus != null && mus !== undefined && mus.length > 0) {
                        for (var i = 0; i < mus.length; i++) {
                            div.push(view.renderMusic(mus[i], true));
                        }
                        view.$el.find(".resource_infos").append(div);
                        view.model.current_page += 1;
                    } else {
                        alert("已经拉到底没数据啦ヽ(•̀ω•́ )ゝ");
                        view.$el.find('.more_music').attr("disabled", "disabled");
                    }
                }
            });
        },

        renderMusic: function (mus) {
            var $div = mus.title + "<br/>";
            return $div;
        },
    });
    module.exports = MusicResourceView;
});