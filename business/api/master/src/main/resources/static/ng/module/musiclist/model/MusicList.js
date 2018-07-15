define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var MusicList = function () {
        this.params = {
            pageNo: 1,
            pageSize: 15
        };

        this.lastPage = null;
    }

    MusicList.prototype = {
        constructor: MusicList,

        fetch: function () {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'music_list/page',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        spider: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'music_list/spider',
                method: 'GET',
                data: param
            });
        },

        spider_music: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'music/spider',
                method: 'GET',
                data: param
            });
        }

    };

    module.exports = MusicList;

})