define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Blog = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
    };

    Blog.prototype = {
        constructor: Blog,

        get_blog: function (id) {
            var param = {};
            param.id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_blog',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Blog;

})