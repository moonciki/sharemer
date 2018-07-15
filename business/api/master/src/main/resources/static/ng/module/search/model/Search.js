define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Search = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.c_p = 1;
        this.type = 0;
        this.key = null;
        this.sort = 0;
        this.id = null;
        var media = null;
        this.lastPage = null;
    };

    Search.prototype = {

        constructor: Search,

        get_medias_by_key: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'media/query',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Search;

});