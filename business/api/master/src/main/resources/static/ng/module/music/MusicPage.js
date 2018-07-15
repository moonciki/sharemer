define(function (require, exports, module) {

    seajs.use('./css/module/menu.css');

    var MusicView = require('module/music/view/MusicView');

    var MusicPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new MusicView({
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

    module.exports = MusicPage;
})