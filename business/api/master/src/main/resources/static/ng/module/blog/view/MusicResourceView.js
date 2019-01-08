define(function (require, exports, module) {

    seajs.use('../css/module/blog/resource.css');

    var musicResourceHtml = require('module/blog/tpl/MusicResourceView.tpl');
    var MusicResource = require('module/blog/model/MusicResource');
    var mydate = null;
    var CommonView = require('module/blog/view/CommonView');

    var MusicResourceView = Backbone.View.extend({
        musicResource: musicResourceHtml,
        current_mid: null,
        initialize: function () {
            this.model = new MusicResource();
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
            this.commonView.initCommon(id, this.$el, this.musicResource, '.menu_resource');
            this.model.id = id;
        }
    });
    module.exports = MusicResourceView;
});