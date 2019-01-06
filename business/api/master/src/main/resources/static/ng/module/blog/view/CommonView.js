define(function (require, exports, module) {

    seajs.use('../css/module/blog/common.css');
    var commonHtml = require('module/blog/tpl/Common.tpl');

    var Common = require('module/blog/model/Common');

    var CommonView = Backbone.View.extend({
        common: commonHtml,
        initialize: function () {
            this.model = new Common();
        },

        router: function (role) {
            switch (role) {
                case "menu_home":
                    window.location.href = "/blog/#home/" + this.commonId;
                    break;
                case "menu_blog":
                    window.location.href = "/blog/#blog/" + this.commonId;
                    break;
                case "menu_timeline":
                    window.location.href = "/blog/#timeline/" + this.commonId;
                    break;
            }
        },

        initCommon: function (id, el, tmp, className) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/blog/#blog/404";
                return;
            }
            this.commonId = id;
            el.append(this.common);
            el.find('#module_html').append(tmp);
            el.find(className).addClass("user_header_menu_box_bit");

            var view = this;
            this.model.get_user_by_id(id).done(function (resp) {
                if (resp.code == 0) {
                    view.renderUser(resp.result.user_info, el);
                }
            });
        },

        renderUser: function (user, el) {
            if (user == null || user == undefined) {
                window.location.href = "/blog/#blog/404";
                return;
            }
            el.find(".user_header_avater").html("<div class='user_header_top_01' style=\"background-size: 100%; background-image: url('"+user.avater+"')\"></div>");
            el.find(".user_header_title").html("<div class='user_header_top_02'>"+user.blog_title+"</div>");
            el.find(".user_header_sign").html("<div class='user_header_top_03'>"+user.desc+"</div>");
        }
    });
    module.exports = CommonView;
});