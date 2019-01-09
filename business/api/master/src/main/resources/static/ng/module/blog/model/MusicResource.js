define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var MusicResource = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
        this.current_page = null;
        this.current_sort = null;
    };

    MusicResource.prototype = {
        constructor: MusicResource,

        get_music_by_uid: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'get_music_by_uid',
                method: 'GET',
                data: param
            });
        },
    };

    module.exports = MusicResource;

})