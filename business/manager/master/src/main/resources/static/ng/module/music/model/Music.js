define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Music = function () {
        this.params = {
            pageNo: 1,
            pageSize: 15,
            key: null,
            type: null
        };

        this.lastPage = null;
    }

    Music.prototype = {
        constructor: Music,

        fetch: function () {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'music/page',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        getVideoByMusicId: function (music_id) {
            var param = {};
            param.music_id = music_id;
            return HttpUtil.request({
                url: STATEMENT.root + 'video/getVideoByMusicId',
                method: 'GET',
                data: param
            })
        },

        musicVideoRel: function (id) {
            var param = {};
            param.id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'music/relation',
                method: 'GET',
                data: param
            })
        }

    };

    module.exports = Music;

})