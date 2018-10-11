define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Upload = function () {
        this.lastPage = null;
    };

    Upload.prototype = {

        constructor: Upload,

        get_up_token: function () {
            return HttpUtil.request({
                async: false,
                url: STATEMENT.root + 'pc_api/get_up_token',
                method: 'GET'
            });
        },

        save_archive: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/save/archive',
                method: 'POST',
                data: param
            });
        }
    };

    module.exports = Upload;

});