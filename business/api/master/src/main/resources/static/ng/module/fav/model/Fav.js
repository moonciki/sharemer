define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Fav = function () {};

    Fav.prototype = {
        constructor: Fav,

        get_favs_by_uid: function (param) {
            var user = window.SHION.currentUser;
            param.c_id = user.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/fav/list',
                method: 'GET',
                data: param
            });
        },

        quick_save_favs: function (param) {
            param.csrf_token = window.SHION.w_token;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/w/fav/save',
                method: 'POST',
                data: param
            });
        },

        save_media_favs: function (param) {
            var user = window.SHION.currentUser;
            param.user_id = user.id;
            param.csrf_token = window.SHION.w_token;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/w/fav/action',
                method: 'POST',
                data: param
            });
        }
    };

    module.exports = Fav;

});