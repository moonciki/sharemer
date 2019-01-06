define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Timeline = function () {
        this.params = {
            pageNo: 1,
            pageSize: 20
        };

        this.id = null;
        this.lastPage = null;
    };

    Timeline.prototype = {
        constructor: Timeline,

        get_timeline: function (id) {
            var param = {};
            param.id = id;
            return HttpUtil.request({
                url: STATEMENT.root + 'get_timeline',
                method: 'GET',
                data: param
            });
        }
    };

    module.exports = Timeline;

})