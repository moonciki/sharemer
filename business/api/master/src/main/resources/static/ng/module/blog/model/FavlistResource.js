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

        get_favlistResource: function (id) {
            var param = {};
            param.id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_favlistResource',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = FavlistResource;

})