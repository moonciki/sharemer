define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Upload = function () {
        this.lastPage = null;
    };

    Upload.prototype = {

        constructor: Upload,

        /*get_medias_by_key: function (param) {
            return HttpUtil.request({
                url: STATEMENT.root + 'media/query',
                method: 'GET',
                data: param
            });
        }*/
    };

    module.exports = Upload;

});