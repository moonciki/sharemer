define(function (require, exports, module) {

    seajs.use('./css/module/menu.css');

    var ArchiveView = require('module/archive/view/ArchiveInfoView');

    var ArchiveInfoPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new ArchiveView({
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

    module.exports = ArchiveInfoPage;
})