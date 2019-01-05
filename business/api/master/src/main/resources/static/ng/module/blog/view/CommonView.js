define(function (require, exports, module) {

    seajs.use('../css/module/blog/common.css');
    var commonHtml = require('module/blog/tpl/Common.tpl');
    var CommonView = Backbone.View.extend({
        common: commonHtml,
        initialize: function () {
        },

        router: function (role, id) {
            switch (role) {
                case "menu_home":
                    window.location.href = "/blog/#home/" + this.commonId;
                    break;
                case "menu_blog":
                    window.location.href = "/blog/#blog/" + this.commonId;
                    break;
            }
        },

        initCommon: function (id, el, tmp, className) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#blog/404";
                return;
            }
            this.commonId = id;
            el.append(this.common);
            el.find('#module_html').append(tmp);
            el.find(className).addClass("user_header_menu_box_bit");
        }
    });
    module.exports = CommonView;
});