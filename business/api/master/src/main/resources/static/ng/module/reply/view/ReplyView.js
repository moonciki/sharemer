define(function(require, exports, module) {

    seajs.use('./css/module/reply.css');

    var Reply = require('module/reply/model/Reply');

    var ReplyView = Backbone.View.extend({
        current_mid : null,
        oid:null,
        otype:null,
        main_el:null,
        initialize: function(options) {
            this.model = new Reply();
            this.oid = options.oid;
            this.otype = options.otype;
            this.main_el = options.el;
            var view = this;
        },

        events: {
            'click .reply_send_btn' :'replySend',
            'click .reply_event': 'replyStyle',
            'click .reply_event_s': 'replyStyleS',
            'click .save_r_r': 'replySendC',
            'click .prev_c' : 'prevC',
            'click .next_c' : 'nextC',
            'click .prev' : 'prev',
            'click .next' : 'next',
            'click .zx' : 'zx',
            'click .zz' : 'zz',
            'click .rp_like': 'rpLike',
            'click .rp_like_qx': 'rpQxLike',
            'click .rp_dislike': 'rpDisLike',
            'click .rp_dislike_qx': 'rpQxDisLike'
        },

        rpLike: function (event) {
            var $div = $(event.target);
            var $btn = $div.parent();
            $div.removeClass("rp_like");
            $div.addClass("rp_like_qx");
            $btn.removeClass("dz");
            $btn.addClass("dz_point");
            var numC = $btn.children(".dz_num");
            var num = parseInt(numC.text())+1;
            numC.html(num);

            var reply_id = $btn.attr("data-rid");
            this.model.like(this.oid, this.otype, 1, reply_id).done();
        },

        rpDisLike: function (event) {
            var $div = $(event.target);
            var $btn = $div.parent();
            $div.removeClass("rp_dislike");
            $div.addClass("rp_dislike_qx");
            $btn.removeClass("dz");
            $btn.addClass("dz_point");
            var numC = $btn.children(".dz_num");
            var num = parseInt(numC.text())+1;
            numC.html(num);

            var reply_id = $btn.attr("data-rid");
            this.model.like(this.oid, this.otype, -1, reply_id).done();
        },

        rpQxLike:function (event) {
            var $div = $(event.target);
            var $btn = $div.parent();
            $div.removeClass("rp_like_qx");
            $div.addClass("rp_like");
            $btn.removeClass("dz_point");
            $btn.addClass("dz");
            var numC = $btn.children(".dz_num");
            var num = parseInt(numC.text())-1;
            numC.html(num);
            var reply_id = $btn.attr("data-rid");
            this.model.like(this.oid, this.otype, 0, reply_id).done();
        },

        rpQxDisLike: function (event) {
            var $div = $(event.target);
            var $btn = $div.parent();
            $div.removeClass("rp_dislike_qx");
            $div.addClass("rp_dislike");
            $btn.removeClass("dz_point");
            $btn.addClass("dz");
            var numC = $btn.children(".dz_num");
            var num = parseInt(numC.text())-1;
            numC.html(num);

            var reply_id = $btn.attr("data-rid");
            this.model.like(this.oid, this.otype, -2, reply_id).done();
        },

        zx: function () {
            this.model.reply_params.sort = 0;
            this.model.reply_params.pageNo = 1;
            var view = this;
            this.model.get_reply_by_oid(this.oid, this.otype).done(function (resp) {
                if(resp.code == 0){
                    view.main_el.find('.zx').addClass("info_area_reply_page_01_point").removeClass("info_area_reply_page_01");
                    view.main_el.find('.zz').addClass("info_area_reply_page_01").removeClass("info_area_reply_page_01_point");
                    view.renderReply(resp);
                }
            });
        },

        zz: function () {
            this.model.reply_params.sort = 1;
            this.model.reply_params.pageNo = 1;
            var view = this;
            this.model.get_reply_by_oid(this.oid, this.otype).done(function (resp) {
                if(resp.code == 0){
                    view.main_el.find('.zz').addClass("info_area_reply_page_01_point").removeClass("info_area_reply_page_01");
                    view.main_el.find('.zx').addClass("info_area_reply_page_01").removeClass("info_area_reply_page_01_point");
                    view.renderReply(resp);
                }
            });
        },

        prevC: function (event) {
            var $btn = $(event.target);
            var $div = $btn.parent();
            var $div2 = $btn.parent().parent().parent();
            var c_p = $div.attr("data-p");
            var father_id = $div2.attr("data-reply-id");
            this.getChildReplies(parseInt(c_p)-1, father_id);
        },

        nextC: function () {
            var $btn = $(event.target);
            var $div = $btn.parent();
            var $div2 = $btn.parent().parent().parent();
            var c_p = $div.attr("data-p");
            var father_id = $div2.attr("data-reply-id");
            this.getChildReplies(parseInt(c_p)+1, father_id);
        },

        prev: function () {
            var $btn = $(event.target);
            var $div = $btn.parent();
            var c_p = $div.attr("data-p");
            this.model.reply_params.pageNo = parseInt(c_p) - 1;
            /** 重新获取评论*/
            this.getReplies();
        },

        next: function () {
            var $btn = $(event.target);
            var $div = $btn.parent();
            var c_p = $div.attr("data-p");
            this.model.reply_params.pageNo = parseInt(c_p) + 1;
            /** 重新获取评论*/
            this.getReplies();
        },

        replySendC: function (event) {
            var $btn = $(event.target);
            var $div = $btn.parent().parent().parent().parent();
            var reply_id = $div.attr("data-reply-id");
            var content = this.main_el.find('.v_reply_content_'+reply_id).val();
            if(content == null || content.length == 0){
                alert("请输入评论内容！");
                return false;
            }
            var view = this;
            this.model.save_reply(this.oid, this.otype, content, reply_id).done(function (resp) {
                if(resp.code == 0){
                    var html = "";
                    var reply = resp.result;
                    if(reply != null){
                        view.main_el.find('.fbnr').hide();
                        html+=view.getCREplyHtml(reply);
                        view.main_el.find('.info_area_reply_cs_'+reply_id).prepend(html);
                    }
                }
            });
        },

        replyStyle: function (event) {
            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                alert("要先登录哦~");
                return false;
            }
            this.main_el.find('.fbnr').hide();
            var $btn = $(event.target);
            var $div = $btn.parent().parent().parent();
            var reply_id = $div.attr("data-reply-id");
            var $data = $btn.parent();
            var uname = $data.attr("data-uname");
            this.current_mid = $data.attr("data-mid");
            var html = this.getReplyStyle(reply_id, "@"+uname+":");
            this.main_el.find('.c_r_'+reply_id).html(html);
        },

        replyStyleS: function (event) {
            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                alert("要先登录哦~");
                return false;
            }
            this.main_el.find('.fbnr').hide();
            var $btn = $(event.target);
            var $data = $btn.parent();
            var $div = $btn.parent().parent().parent().parent().parent().parent();
            var reply_id = $div.attr("data-reply-id");
            var uname = $data.attr("data-uname");
            this.current_mid = $data.attr("data-mid");
            var html = this.getReplyStyle(reply_id, "@"+uname+":");
            this.main_el.find('.c_r_'+reply_id).html(html);
        },

        getReplyStyle: function (reply_id, uname) {
            return "<div class=\"fbnr info_area_reply_c\">" +
                "<div class='info_area_reply_c_c1'><textarea class=\"v_reply_content_"+reply_id+" form-control\" style=\"border:solid 1px #00b5e5\" rows=\"4\" placeholder=\"请输入内容\">"+uname+"</textarea></div>"+
                "<div class='save_r_r info_area_reply_c_c2'>发表<br/>内容</div>"+
                "</div>";
        },

        replySend: function () {
            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                alert("要先登录哦~");
                return false;
            }
            var content = this.main_el.find('.v_reply_content').val();
            if(content == null || content.length == 0){
                alert("请输入评论内容！");
                return false;
            }
            var view = this;
            this.model.save_reply(this.oid, this.otype, content, null).done(function (resp) {
                if(resp.code == 0){
                    var reply = resp.result;
                    var html = view.getReplyHtml(reply);
                    view.main_el.find('.v_main_reply_area').prepend(html);
                    view.main_el.find('.v_reply_content').val("");
                    view.main_el.find('.no_reply').hide();
                }
            });
        },

        getReplies: function () {
            var view = this;
            this.model.get_reply_by_oid(this.oid, this.otype).done(function (resp) {
                view.renderReply(resp);
            });
        },

        getChildReplies: function (page, father_id) {
            var param = {};
            param.pageNo = page;
            param.oid = this.oid;
            param.otype = this.otype;
            param.reply_id = father_id;

            var view = this;
            this.model.get_child_reply_by_rid(param).done(function (resp) {
                view.main_el.find(".info_area_reply_cs_"+father_id)
                    .html(view.getChildHtml(resp.result));
            });
        },

        renderReply: function(resp) {
            this.main_el.find('.v_main_reply_area').empty();
            var page = resp.result,
                replys = page.records;
            if(replys == null || replys.length == 0){
                this.main_el.find('.page_total').text(0);
                this.main_el.find('.v_main_reply_area').html("<div class='no_reply'></div>");
                return;
            }
            this.main_el.find('.page_total').text(page.totalCount);
            var html = "";
            for (var i = 0; i < replys.length; i++) {
                html+=this.getReplyHtml(replys[i]);
            }
            this.main_el.find('.v_main_reply_area').append(html);

            var pageHtml = this.getPageHtml(page, 0);
            this.main_el.find('.zrp').html(pageHtml);
        },

        getReplyHtml: function (reply) {
            var html ="<div class=\"info_area_reply_03\" data-reply-id='"+reply.real_id+"'><div class=\"info_area_reply_03_01\">"+
                "<img src=\""+reply.user.avater+"\" class=\"v_reply_user\"/>"+
                "</div><div class=\"info_area_reply_03_02\"></div><div class=\"info_area_reply_03_03\">"+
                "<div class=\"info_area_reply_03_03_01\">"+
                "<a class=\"r_u\" href=\"#user/info/"+reply.user.id+"\" target='_blank'><b>"+reply.user.name+"</b></a>&nbsp;&nbsp;"+
                "<span class=\"badge\" style=\"background-color:"+reply.user.level_color+"\">LV "+reply.user.level+"</span></div>"+
                "<div class=\"info_area_reply_03_03_02\">"+reply.content+"</div>"+
                "<div class=\"info_area_reply_03_03_01\" data-mid='"+reply.user.id+"' data-uname='"+reply.user.name+"'>#1 &nbsp;&nbsp;&nbsp;&nbsp;"+
                "<span class=\"glyphicon glyphicon-time\"></span>&nbsp;&nbsp;"+reply.ctime.replace("T", " ")+"&nbsp;&nbsp;&nbsp;&nbsp;";
            if(reply.is_like == 1){
                html+="<span class='dz_point' data-rid = '"+reply.real_id+"'><span class=\"rp_like_qx glyphicon glyphicon-thumbs-up\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.like+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }else{
                html+="<span class='dz' data-rid = '"+reply.real_id+"'><span class=\"rp_like glyphicon glyphicon-thumbs-up\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.like+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }
            if(reply.is_dislike == 1){
                html+="<span class='dz_point' data-rid = '"+reply.real_id+"'><span class=\"rp_dislike_qx glyphicon glyphicon-thumbs-down\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.dislike+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }else {
                html+="<span class='dz' data-rid = '"+reply.real_id+"'><span class=\"rp_dislike glyphicon glyphicon-thumbs-down\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.dislike+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }
            html+="<span class='reply_event rpl'><span class=\"glyphicon glyphicon-comment\"></span>&nbsp;&nbsp;回复</div></span><span class='c_r_"+reply.real_id+"'></span><span class='info_area_reply_cs_"+reply.real_id+"' data-reply-id = '"+reply.real_id+"'>";

            if(reply.child_replies != null
                && reply.child_replies != null
                && reply.child_replies.records != null
                && reply.child_replies.records.length > 0){
                html+=this.getChildHtml(reply.child_replies);
            }
            html+="</span></div></div>";
            return html;
        },

        getChildHtml: function (child_replies) {
            var child = child_replies.records;
            var html="";
            for(var j = 0; j < child.length; j++){
                html+=this.getCREplyHtml(child[j]);
            }
            html+="<div class='info_area_reply_c'>"
            html+=this.getPageHtml(child_replies, 1);
            html+="</div>";
            return html;
        },

        getPageHtml: function (child_reply, type) {
            var html = "<span data-p='"+child_reply.pageNo+"'>";
            if(child_reply.pageNo != 1){
                if(type == 1){
                    html+="<span class='pg prev_c'>上一页</span>"
                }else{
                    html+="<span class='pg prev'>上一页</span>"
                }
            }
            html+="&nbsp;&nbsp;&nbsp;&nbsp;第 < "+child_reply.pageNo+" > 页&nbsp;&nbsp;&nbsp;&nbsp;";
            if(child_reply.hasNextPage == true){
                if(type == 1){
                    html+="<span class='pg next_c'>下一页</span>"
                }else{
                    html+="<span class='pg next'>下一页</span>"
                }
            }
            html+="&nbsp;&nbsp;&nbsp;&nbsp;共"+child_reply.pageCount+"页</span>";
            return html;
        },

        getCREplyHtml: function (reply) {
            var html = "<div class=\"info_area_reply_c\">"+
                "<div class='info_area_reply_c_01'><img src='"+reply.user.avater+"' class='v_reply_user_c'/></div>" +
                "<div class='info_area_reply_c_02'><a class=\"r_u\" href=\"#user/info/"+reply.user.id+"\" target='_blank'><b>"+reply.user.name+"</b></a>" +
                "&nbsp;&nbsp;<span class=\"badge\" style=\"background-color:"+reply.user.level_color+"\">LV "+reply.user.level+"</span>&nbsp;&nbsp;"+
                reply.content+
                "<br/><span style='color:#c9c9c9; line-height: 2.1' data-mid='"+reply.user.id+"' data-uname='"+reply.user.name+"'><span class=\"glyphicon glyphicon-time\"></span>&nbsp;&nbsp;"+reply.ctime.replace("T", " ")+"&nbsp;&nbsp;&nbsp;&nbsp;";
            if(reply.is_like == 1){
                html+="<span class='dz_point' data-rid = '"+reply.real_id+"'><span class=\"rp_like_qx glyphicon glyphicon-thumbs-up\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.like+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }else{
                html+="<span class='dz' data-rid = '"+reply.real_id+"'><span class=\"rp_like glyphicon glyphicon-thumbs-up\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.like+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }
            if(reply.is_dislike == 1){
                html+="<span class='dz_point' data-rid = '"+reply.real_id+"'><span class=\"rp_dislike_qx glyphicon glyphicon-thumbs-down\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.dislike+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }else {
                html+="<span class='dz' data-rid = '"+reply.real_id+"'><span class=\"rp_dislike glyphicon glyphicon-thumbs-down\"></span>&nbsp;&nbsp;<span class='dz_num'>"+reply.dislike+"</span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
            }
            html+="<span class='reply_event_s rpl'><span class=\"glyphicon glyphicon-comment\"></span>&nbsp;&nbsp;回复</span></span></div></div>";
            return html;
        }
    });

    module.exports = ReplyView;

});