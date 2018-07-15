define(function (require, exports, module) {
    var HttpUtil = require('util/HttpUtil');
    var Reply = function () {

        this.reply_params = {
            sort: 0,
            pageNo: 1,
            pageSize: 10
        };
    };

    Reply.prototype = {
        constructor: Reply,

        get_reply_by_oid: function (oid, otype) {
            var user = window.SHION.currentUser;
            this.reply_params.oid = oid;
            this.reply_params.otype = otype;
            if(user != null && user != undefined){
                this.reply_params.c_id = user.id;
            }
            return HttpUtil.request({
                url: STATEMENT.root + 'pc_api/get_replies',
                method: 'GET',
                data: this.reply_params
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

        save_reply: function (oid, otype, content, reply_id) {
            var replyParam = {};
            replyParam.oid = oid;
            replyParam.otype = otype;
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

        like: function (oid, otype, l_type, reply_id) {
            var replyParam = {};
            replyParam.oid = oid;
            replyParam.otype = otype;
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

    module.exports = Reply;

});