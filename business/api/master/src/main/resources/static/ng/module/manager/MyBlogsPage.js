define(function (require, exports, module) {

    var MyBlogsView = require('module/manager/view/MyBlogsView');

    var MyBlogsPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new MyBlogsView({
                el: '#listView'
            });
        },

        go: function () {
            this.listView.request();
        },

        remove: function () {
            this.listView.remove();

            Backbone.View.prototype.remove.apply(this);
        }
    });

    module.exports = MyBlogsPage;
})