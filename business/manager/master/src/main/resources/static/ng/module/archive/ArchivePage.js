define(function (require, exports, module) {
    seajs.use('./css/module/admin.css');

    var ArchiveView = require('module/archive/view/ArchiveView');

    var ArchivePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"/>');

            this.listView = new ArchiveView({
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

    module.exports = ArchivePage;
})