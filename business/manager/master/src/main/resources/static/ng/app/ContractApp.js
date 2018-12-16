define(function(require, exports, module) {

    var Top = require('component/Top');
    var Navigator = require('component/Navigator');

    var ContractApp = Backbone.View.extend({

        main$el: $('.main'),
        pageEl: '#page',

        initialize: function(options) {
            this.nav = new Navigator();
            this.top = new Top();
            this.lastPage = null;
            if(bootbox){
                bootbox.setDefaults({title:'请求结果'});
            }
        },

        renderMusicList: function (options) {
            var app = this;

            require.async('module/musiclist/MusicListPage', function (MusicListPage) {
                if (!(app.lastPage instanceof MusicListPage)) {
                    app.reset();
                    app.lastPage = new MusicListPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        renderUserList: function (options) {
            var app = this;

            require.async('module/user/UserListPage', function (UserListPage) {
                if (!(app.lastPage instanceof UserListPage)) {
                    app.reset();
                    app.lastPage = new UserListPage({
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

        renderArchive: function (options) {
            var app = this;

            require.async('module/archive/ArchivePage', function (ArchivePage) {
                if (!(app.lastPage instanceof ArchivePage)) {
                    app.reset();
                    app.lastPage = new ArchivePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        addVideo: function (options) {
            var app = this;

            require.async('module/video/VideoFormPage', function (VideoFormPage) {
                if (!(app.lastPage instanceof VideoFormPage)) {
                    app.reset();
                    app.lastPage = new VideoFormPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(options);
            });
        },

        reset: function() {
            if (this.lastPage) {
                this.lastPage.remove();
                this.main$el.append('<div id="page" />');
            }
        }
    });

    module.exports = ContractApp;
});
