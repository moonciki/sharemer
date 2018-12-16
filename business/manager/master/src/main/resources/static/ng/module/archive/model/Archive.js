define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Archive = function () {
        this.currentId = null;
        this.params = {
            pageNo: 1,
            pageSize: 15,
            key: null,
            type: null
        };

        this.lastPage = null;
    };

    Archive.prototype = {
        constructor: Archive,

        fetch: function () {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'archive/page',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        pass: function (status) {
            var model = this;
            var param = {};
            param.archive_id = model.currentId;
            param.status = status;
            return HttpUtil.request({
                url: STATEMENT.root + 'archive/pass',
                method: 'GET',
                data: param
            });
        }

    };

    module.exports = Archive;

})