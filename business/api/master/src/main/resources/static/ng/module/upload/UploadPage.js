define(function (require, exports, module) {

    seajs.use('./css/module/menu.css');

    var UploadView = require('module/upload/view/UploadView');

    var UploadPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new UploadView({
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

    module.exports = UploadPage;
});