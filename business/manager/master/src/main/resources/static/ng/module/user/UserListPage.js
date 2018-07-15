define(function (require, exports, module) {
    seajs.use('./css/module/admin.css');

    var UserListView = require('module/user/view/UserListView');

    var UserListPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"/>');

            this.listView = new UserListView({
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

    module.exports = UserListPage;
})