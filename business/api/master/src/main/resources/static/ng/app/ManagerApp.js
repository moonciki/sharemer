
define(function (require, exports, module) {

    var Top = require('component/Top');

    var ManagerApp = Backbone.View.extend({

        main$el: $('.main'),
        pageEl: '#page',

        initialize: function () {
            this.top = new Top();
            this.lastPage = null;
            if (bootbox) {
                bootbox.setDefaults({title: '请求结果'});
            }
        },

        renderMyBlogs: function () {
            var app = this;

            require.async('module/manager/MyBlogsPage', function (MyBlogsPage) {
                if (!(app.lastPage instanceof MyBlogsPage)) {
                    app.reset();
                    app.lastPage = new MyBlogsPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go();
            });
        },

        renderTimeline: function (id) {
            var app = this;

            require.async('module/manager/TimelinePage', function (TimelinePage) {
                if (!(app.lastPage instanceof TimelinePage)) {
                    app.reset();
                    app.lastPage = new TimelinePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderFavlistResource: function (id) {
            var app = this;

            require.async('module/manager/FavlistResourcePage', function (FavlistResourcePage) {
                if (!(app.lastPage instanceof FavlistResourcePage)) {
                    app.reset();
                    app.lastPage = new FavlistResourcePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderMusicResource: function (id) {
            var app = this;

            require.async('module/manager/MusicResourcePage', function (MusicResourcePage) {
                if (!(app.lastPage instanceof MusicResourcePage)) {
                    app.reset();
                    app.lastPage = new MusicResourcePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderVideoResource: function (id) {
            var app = this;

            require.async('module/manager/VideoResourcePage', function (VideoResourcePage) {
                if (!(app.lastPage instanceof VideoResourcePage)) {
                    app.reset();
                    app.lastPage = new VideoResourcePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        renderArchiveResource: function (id) {
            var app = this;

            require.async('module/manager/ArchiveResourcePage', function (ArchiveResourcePage) {
                if (!(app.lastPage instanceof ArchiveResourcePage)) {
                    app.reset();
                    app.lastPage = new ArchiveResourcePage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        reset: function () {
            if (this.lastPage) {
                this.lastPage.remove();
                this.main$el.append('<div id="page"/>');
            }
        }
    });

    module.exports = ManagerApp;
});