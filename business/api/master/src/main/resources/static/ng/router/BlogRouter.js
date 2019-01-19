define(function (require, exports, module) {

    var BlogRouter = Backbone.Router.extend({
        routes: {
            "blog/(:id)": "blog",
            "timeline/(:id)": "timeline",
            "resource/favlist/(:id)": "favlistResource",
            "resource/music/(:id)": "musicResource",
            "resource/video/(:id)": "videoResource",
            "resource/archive/(:id)": "archiveResource"
        },

        setApp: function (app) {
            this.app = app;
        },

        blog: function (id) {
            this.app.renderBlog(id);
        },

        timeline: function (id) {
            this.app.renderTimeline(id);
        },

        favlistResource: function (id) {
            this.app.renderFavlistResource(id);
        },

        musicResource: function (id) {
            this.app.renderMusicResource(id);
        },

        videoResource: function (id) {
            this.app.renderVideoResource(id);
        },

        archiveResource: function (id) {
            this.app.renderArchiveResource(id);
        },

        defaultRoute: function (args) {
            alert('资源不存在！');
        }
    });

    module.exports = BlogRouter;
});