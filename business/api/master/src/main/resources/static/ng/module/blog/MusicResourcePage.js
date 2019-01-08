define(function (require, exports, module) {

    var MusicResourceView = require('module/blog/view/MusicResourceView');

    var MusicResourcePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new MusicResourceView({
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

    module.exports = MusicResourcePage;
})