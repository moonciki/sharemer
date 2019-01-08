define(function (require, exports, module) {

    seajs.use('../css/module/blog/resource.css');

    var favlistResourceHtml = require('module/blog/tpl/FavlistResourceView.tpl');
    var FavlistResource = require('module/blog/model/FavlistResource');
    var mydate = null;
    var CommonView = require('module/blog/view/CommonView');

    var FavlistResourceView = Backbone.View.extend({
        favlistResource: favlistResourceHtml,
        current_mid: null,
        initialize: function () {
            this.model = new FavlistResource();
            mydate = new Date();
            this.commonView = new CommonView();
        },

        events: {
            'click .user_header_menu_box': 'router'
        },

        router: function (event) {
            var role = event.currentTarget.getAttribute("data-role");
            this.commonView.router(role);
        },

        request: function (id) {
            this.commonView.initCommon(id, this.$el, this.favlistResource, '.menu_resource');
            this.model.id = id;
        }
    });
    module.exports = FavlistResourceView;
});