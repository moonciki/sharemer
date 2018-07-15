define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Music = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
        this.music_id = null;
        this.musicInfo = null;
    };

    Music.prototype = {
        constructor: Music,

        get_music_by_tag: function () {
            var model = this;
            this.params.tag_id = this.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_music_by_tag',
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

        get_relate_video:function () {
            var param = {music_id:this.music_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_relate_video',
                method: 'GET',
                data: param
            });
        },

        get_music_info: function () {
            var model = this;
            var param = {music_id:this.music_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_music_info',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.musicInfo = resp.result;
                }
            });
        }
    };

    module.exports = Music;

});