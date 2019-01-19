define(function (require, exports, module) {

    var ManagerRouter = Backbone.Router.extend({
        routes: {
            "myblog": "myblog"
        },

        setApp: function (app) {
            this.app = app;
        },

        myblog: function () {
            this.app.renderMyBlogs();
        },

        defaultRoute: function (args) {
            alert('资源不存在！');
        }
    });

    module.exports = ManagerRouter;
});