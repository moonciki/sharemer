define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Player = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.reply_params = {
            sort: 0,
            pageNo: 1,
            pageSize: 10
        };

        this.id = null;
        this.lastPage = null;
    };

    Player.prototype = {
        constructor: Player,

        get_danmakus: function (aid) {
            var param = {};
            param.aid = aid;
            return HttpUtil.request({
                url: STATEMENT.root + 'danmaku/gets',
                method: 'GET',
                data: param
            });
        },

        get_path: function (aid) {
            var param = {};
            param.archive_id = aid;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_path',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Player;

})