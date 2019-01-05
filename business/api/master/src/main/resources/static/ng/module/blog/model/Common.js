define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Common = function () {
        this.id = null;
        this.lastPage = null;
    };

    Common.prototype = {
        constructor: Common,
        get_user_by_id: function (id) {
            var param = {};
            param.user_id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_user_info',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Common;

})