define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var FavlistResource = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
    };

    FavlistResource.prototype = {
        constructor: FavlistResource,

        get_fav_list_by_uid: function (id) {
            var param = {};
            param.c_id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'fav/fav_list',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = FavlistResource;

})