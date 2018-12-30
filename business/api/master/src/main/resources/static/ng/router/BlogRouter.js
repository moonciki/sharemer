define(function(require, exports, module) {

    var PlayerRouter = Backbone.Router.extend({
        routes: {
            "home/(:id)":"home"
        },

        setApp: function(app) {
            this.app = app;
        },

        home:function(id) {
            this.app.renderHome(id);
        },

        defaultRoute: function(args) {
            alert('资源不存在！');
        }
    });

    module.exports = PlayerRouter;
});