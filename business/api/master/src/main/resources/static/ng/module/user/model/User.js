define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var User = function () {
        this.lastPage = null;
        this.user_id = null;
        this.current_page = null;
        this.current_sort = null;
    };

    User.prototype = {
        constructor: User,

        get_user_by_id: function () {
            var param = {};
            param.user_id = this.user_id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_user_info',
                method: 'GET',
                data: param
            });
        },

        get_fav_list_by_uid: function (param) {
            param.c_id = this.user_id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/fav/fav_list',
                method: 'GET',
                data: param
            });
        },

        get_music_by_uid: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_music_by_uid',
                method: 'GET',
                data: param
            });
        },

        get_video_by_uid: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_video_by_uid',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = User;

});