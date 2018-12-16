define(function(require, exports, module) {

    var Top = require('component/Top');

    var ContractApp = Backbone.View.extend({

        main$el: $('.main'),
        pageEl: '#page',

        initialize: function(options) {
            this.top = new Top();
            this.lastPage = null;
            if(bootbox){
                bootbox.setDefaults({title:'请求结果'});
            }
        },

        renderHome: function (options) {
            var app = this;

            require.async('module/home/HomePage', function (HomePage) {
                if (!(app.lastPage instanceof HomePage)) {
                    app.reset();
                    app.lastPage = new HomePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        renderMusic: function (options) {
            var app = this;

            require.async('module/music/MusicPage', function (MusicPage) {
                if (!(app.lastPage instanceof MusicPage)) {
                    app.reset();
                    app.lastPage = new MusicPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        renderVideo: function (options) {
            var app = this;

            require.async('module/video/VideoPage', function (VideoPage) {
                if (!(app.lastPage instanceof VideoPage)) {
                    app.reset();
                    app.lastPage = new VideoPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        renderMusicTag: function (id) {
            var app = this;

            require.async('module/music/MusicTagPage', function (MusicTagPage) {
                if (!(app.lastPage instanceof MusicTagPage)) {
                    app.reset();
                    app.lastPage = new MusicTagPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderFavTag: function (id) {
            var app = this;

            require.async('module/favlist/FavListTagPage', function (FavListTagPage) {
                if (!(app.lastPage instanceof FavListTagPage)) {
                    app.reset();
                    app.lastPage = new FavListTagPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderVideoTag: function (id) {
            var app = this;

            require.async('module/video/VideoTagPage', function (VideoTagPage) {
                if (!(app.lastPage instanceof VideoTagPage)) {
                    app.reset();
                    app.lastPage = new VideoTagPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderArchiveTag: function (id) {
            var app = this;

            require.async('module/archive/ArchiveTagPage', function (ArchiveTagPage) {
                if (!(app.lastPage instanceof ArchiveTagPage)) {
                    app.reset();
                    app.lastPage = new ArchiveTagPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderMusicInfo: function (id) {
            var app = this;

            require.async('module/music/MusicInfoPage', function (MusicInfoPage) {
                if (!(app.lastPage instanceof MusicInfoPage)) {
                    app.reset();
                    app.lastPage = new MusicInfoPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderFavInfo: function (id) {
            var app = this;

            require.async('module/favlist/FavListInfoPage', function (FavListInfoPage) {
                if (!(app.lastPage instanceof FavListInfoPage)) {
                    app.reset();
                    app.lastPage = new FavListInfoPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        search: function (key, options) {
            var app = this;

            require.async('module/search/SearchPage', function (SearchPage) {
                if (!(app.lastPage instanceof SearchPage)) {
                    app.reset();
                    app.lastPage = new SearchPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(key, options);
            });
        },

        upload: function () {
            var app = this;

            require.async('module/upload/UploadPage', function (UploadPage) {
                if (!(app.lastPage instanceof UploadPage)) {
                    app.reset();
                    app.lastPage = new UploadPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go();
            });
        },

        renderUserInfo: function (id) {
            var app = this;

            require.async('module/user/UserInfoPage', function (UserInfoPage) {
                if (!(app.lastPage instanceof UserInfoPage)) {
                    app.reset();
                    app.lastPage = new UserInfoPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderVideoInfo: function (id) {
            var app = this;

            require.async('module/video/VideoInfoPage', function (VideoInfoPage) {
                if (!(app.lastPage instanceof VideoInfoPage)) {
                    app.reset();
                    app.lastPage = new VideoInfoPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        reset: function() {
            if (this.lastPage) {
                this.lastPage.remove();
                this.main$el.append('<div id="page" style="margin-top: 160px"/>');
            }
        }
    });

    module.exports = ContractApp;
});
