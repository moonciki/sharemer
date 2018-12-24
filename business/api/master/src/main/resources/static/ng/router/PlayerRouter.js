define(function(require, exports, module) {

    var PlayerRouter = Backbone.Router.extend({
        routes: {
            "archive/player/(:id)":"archivePlayer"
        },

        setApp: function(app) {
            this.app = app;
        },

        archivePlayer:function(id) {
            this.app.renderArchivePlayer(id);
        },

        defaultRoute: function(args) {
            alert('资源不存在！');
        }
    });

    module.exports = PlayerRouter;
});