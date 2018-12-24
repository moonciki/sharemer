define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Archive = function () {
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
        this.lastReply = null;
        this.archive_id = null;
        this.archiveInfo = null;
    };

    Archive.prototype = {
        constructor: Archive,
        get_tag: function () {
            var param = {tag_id:this.id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_current_tag',
                method: 'GET',
                data: param
            });
        },

        get_archive_by_tag: function () {
            var model = this;
            this.params.tag_id = this.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_archive_by_tag',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        get_archive_info: function (aid) {
            var param = {};
            param.archive_id = aid;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_archive_info',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Archive;

})