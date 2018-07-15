define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Video = function () {
        this.params = {
            pageNo: 1,
            pageSize: 15,
            key: null,
            type: null
        };

        this.lastPage = null;
    }

    Video.prototype = {
        constructor: Video,

        fetch: function () {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'video/page',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        deepBVideo: function (param) {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'video/deepBVideo',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        deepAVideo: function (param) {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'video/deepAVideo',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        deepMVideo: function (param) {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'video/deepMVideo',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        deepCVideo: function (param) {
            var model = this;

            return HttpUtil.request({
                url: STATEMENT.root + 'video/deepCVideo',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        }

    };

    module.exports = Video;

})