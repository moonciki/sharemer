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
            this.tagSc();
        },

        tagSc: function () {
            var view = this;
            this.model.get_fav_list_by_uid(this.model.id).done(function (resp) {
                if (resp.code == 0) {
                    view.$el.find(".resource_body_menu_title").html("<span style=\"font-size: 16px\">共有<span style=\"color: #ff81a1; font-weight: bold\">" + resp.result.count + "</span>个收藏单</span>")
                    var favs = resp.result.favs;
                    var htm = "";
                    for (var i = 0; i < favs.length; i++) {
                        var title = favs[i].title;
                        if (title != null && title.length > 13) {
                            title = title.substring(0, 13) + "...";
                        }
                        var ctime = favs[i].ctime;
                        if (favs[i].ctime != null) {
                            ctime = ctime.replace("T", " ");
                        }

                        htm += "<div class=\"favlist_unit\">" +
                            "                <div onclick='' class=\"favlist_unit_top\" title='" + favs[i].title + "'>" +
                            "                    <div class=\"favlist_unit_top_cover\" style=\"background-image: url('" + favs[i].cover + "'), url('/image/default_cover.jpg')\"></div>" +
                            "                </div>" +
                            "                <div class=\"favlist_unit_bottom\">" +
                            "                    <a href=\"/#favlist/info/" + favs[i].id + "\" target=\"_blank\">" + title + "</a>" +
                            "                    <br/>" +
                            "                    <span style=\"font-size: 12px; color: #949494\">创建于" + ctime + "</span>" +
                            "                </div></div>";
                    }
                    view.$el.find(".resource_body_body").html(htm);
                }
            });
        }
    });
    module.exports = FavlistResourceView;
});