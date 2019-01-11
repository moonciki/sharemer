define(function (require, exports, module) {

    var VideoResourceView = require('module/blog/view/VideoResourceView');

    var VideoResourcePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new VideoResourceView({
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

    module.exports = VideoResourcePage;
})