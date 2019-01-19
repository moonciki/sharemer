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

        my_blogs: function () {
            return HttpUtil.request({
                url: STATEMENT.root + 'my_blogs',
                method: 'GET'
            });
        }
    };

    module.exports = Blog;

})