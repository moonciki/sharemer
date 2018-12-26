define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Video = function () {
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
        this.videoInfo = null;
    };

    Video.prototype = {
        constructor: Video,
        get_tag: function () {
            var param = {tag_id:this.id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_current_tag',
                method: 'GET',
                data: param
            });
        },

        get_video_by_tag: function () {
            var model = this;
            this.params.tag_id = this.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_video_by_tag',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        get_video_info: function () {
            var model = this;
            var param = {video_id:this.video_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_video_info',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.musicInfo = resp.result;
                }
            });
        }
    };

    module.exports = Video;

})