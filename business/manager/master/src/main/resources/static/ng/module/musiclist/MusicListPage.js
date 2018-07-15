define(function (require, exports, module) {
    seajs.use('./css/module/admin.css');

    var MusicListView = require('module/musiclist/view/MusicListView');

    var MusicListPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"/>');

            this.listView = new MusicListView({
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

    module.exports = MusicListPage;
})