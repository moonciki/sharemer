define(function (require, exports, module) {

    var ArchiveResourceView = require('module/blog/view/ArchiveResourceView');

    var ArchiveResourcePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new ArchiveResourceView({
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

    module.exports = ArchiveResourcePage;
})