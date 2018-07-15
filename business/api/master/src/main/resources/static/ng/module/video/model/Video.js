define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Video = function () {
        this.params = {
            pageNo: 1,
            pageSize: 12
        };

        this.reply_params = {
            sort: 0,
            pageNo: 1,
            pageSize: 10
        };

        this.id = null;
        this.lastPage = null;
        this.lastReply = null;
        this.video_id = null;
        this.videoInfo = null;
    };

    Video.prototype = {
        constructor: Video,
        get_tag: function () {
            var param = {tag_id:this.id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_current_tag',
                method: 'GET',
                data: param
            });
        },

        get_video_by_tag: function () {
            var model = this;
            this.params.tag_id = this.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_video_by_tag',
                method: 'GET',
                data: this.params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastPage = resp.result;
                }
            });
        },

        get_video_info: function () {
            var model = this;
            var param = {video_id:this.video_id};
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_video_info',
                method: 'GET',
                data: param
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.musicInfo = resp.result;
                }
            });
        },

        get_reply_by_oid: function () {
            var model = this;
            var user = window.SHION.currentUser;
            this.reply_params.oid = this.video_id;
            this.reply_params.otype = 1;
            this.reply_params.c_id = user.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_replies',
                method: 'GET',
                data: this.reply_params
            }).done(function (resp) {
                if (resp.code == 0) {
                    model.lastReply = resp.result;
                }
            });
        },

        get_child_reply_by_rid: function (param) {
            var user = window.SHION.currentUser;
            param.c_id = user.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_child_replies',
                method: 'GET',
                data: param
            });
        },

        get_favs_by_uid: function (param) {
            var user = window.SHION.currentUser;
            param.c_id = user.id;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/fav/list',
                method: 'GET',
                data: param
            });
        },

        quick_save_favs: function (param) {
            param.csrf_token = window.SHION.w_token;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/w/fav/save',
                method: 'POST',
                data: param
            });
        },

        save_media_favs: function (param) {
            var user = window.SHION.currentUser;
            param.user_id = user.id;
            param.csrf_token = window.SHION.w_token;
            param.o_id = this.video_id;
            param.o_type = 1;
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/w/fav/action',
                method: 'POST',
                data: param
            });
        },

        save_reply: function (content, reply_id) {
            var replyParam = {};
            replyParam.oid = this.video_id;
            replyParam.otype = 1;
            var user = window.SHION.currentUser;
            var token = window.SHION.w_token;
            replyParam.user_id = user.id;
            replyParam.content = content;
            replyParam.csrf_token = token;
            if(reply_id != null && reply_id != undefined){
                replyParam.reply_id = reply_id;
            }
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/w/reply/save',
                method: 'POST',
                data: replyParam
            });
        },

        like: function (l_type, reply_id) {
            var replyParam = {};
            replyParam.oid = this.video_id;
            replyParam.otype = 1;
            var user = window.SHION.currentUser;
            var token = window.SHION.w_token;
            replyParam.user_id = user.id;
            replyParam.l_type = l_type;
            replyParam.csrf_token = token;
            if(reply_id != null && reply_id != undefined){
                replyParam.reply_id = reply_id;
            }
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/r/reply/action',
                method: 'POST',
                data: replyParam
            });
        }
    };

    module.exports = Video;

})