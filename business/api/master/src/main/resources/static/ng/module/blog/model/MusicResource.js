define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var MusicResource = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
    };

    MusicResource.prototype = {
        constructor: MusicResource,

        get_musicResource: function (id) {
            var param = {};
            param.id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_musicResource',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = MusicResource;

})