define(function (require, exports, module) {

    var FavlistResourceView = require('module/blog/view/FavlistResourceView');

    var FavlistResourcePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new FavlistResourceView({
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

    module.exports = FavlistResourcePage;
})