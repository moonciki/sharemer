define(function (require, exports, module) {

    seajs.use('../css/module/manager/my_blogs.css');

    var myBlogsHtml = require('module/manager/tpl/MyBlogsView.tpl');
    var MyBlogs = require('module/manager/model/MyBlogs');

    var MyBlogsView = Backbone.View.extend({
        myBlogs: myBlogsHtml,
        current_mid: null,
        initialize: function () {
            this.$el.append(this.myBlogs);
            this.model = new MyBlogs();
        },

        events: {

        },

        request: function (id) {

        }
    });
    module.exports = MyBlogsView;
});