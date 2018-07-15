define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var FavList = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
        this.favlist_id = null;
        this.favlistInfo = null;
    };

    FavList.prototype = {
        constructor: FavList,

        get_favlist_by_tag: function () {
            var model = this;
            this.params.tag_id = this.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_fav_by_tag',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        get_tag: function () {
            var param = {tag_id:this.id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_current_tag',
                method: 'GET',
                data: param
            });
        },

        get_fav_info: function () {
            var model = this;
            var param = {fav_id:this.favlist_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_fav_info',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.favlistInfo = resp.result;
                }
            });
        },

        get_fav_musics:function () {
            var param = {fav_id:this.favlist_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_fav_musics',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = FavList;

});