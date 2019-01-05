define(function(require, exports, module) {

    var PlayerRouter = Backbone.Router.extend({
        routes: {
            "blog/(:id)":"blog"
        },

        setApp: function(app) {
            this.app = app;
        },

        blog:function(id) {
            this.app.renderBlog(id);
        },

        defaultRoute: function(args) {
            alert('资源不存在！');
        }
    });

    module.exports = PlayerRouter;
});