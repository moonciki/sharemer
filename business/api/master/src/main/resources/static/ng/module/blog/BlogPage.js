define(function (require, exports, module) {

    var BlogView = require('module/blog/view/BlogView');

    var BlogPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new BlogView({
                el: '#listView'
            });
        },

        go: function (id) {
            this.listView.request(id);
        },

        remove: function () {
            this.listView.remove();

            Backbone.View.prototype.remove.apply(this);
        }
    });

    module.exports = BlogPage;
})