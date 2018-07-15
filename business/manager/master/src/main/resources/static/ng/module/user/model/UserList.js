define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var UserList = function () {
        this.params = {
            pageNo: 1,
            pageSize: 15
        };

        this.lastPage = null;
    };

    UserList.prototype = {
        constructor: UserList,

        fetch: function () {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'user/page',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        }
    };

    module.exports = UserList;

})