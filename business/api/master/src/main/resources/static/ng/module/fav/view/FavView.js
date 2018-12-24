define(function(require, exports, module) {

    seajs.use('./css/module/t_model.css');
    seajs.use('./css/module/share.css');

    var Template = require('module/fav/tpl/FavModal.tpl');

    var Fav = require('module/fav/model/Fav');

    var FavView = Backbone.View.extend({
        template: Template,
        current_mid : null,
        oid:null,
        otype:null,
        main_el:null,
        initialize: function(options) {
            this.$el.append(this.template);
            this.model = new Fav();
            this.oid = options.oid;
            this.otype = options.otype;
            this.main_el = options.el;
            var view = this;
        },

        events: {
            'click .v_fav_btn': 'favClick',
            'click .v_f_add_list_btn': 'quickAddFavList',
            'click .v_f_final_sub': 'vFinalSub'
        },

        initNotFav: function () {
            this.main_el.html(this.getNotFavHtml());
        },

        initAlreadyFav: function () {
            this.main_el.find('.v_y_fav_text').text(" 已收藏 ");
            this.main_el.find('.v_fav_btn').removeClass("v_fav_btn").addClass("v_y_fav_btn");
        },

        vFinalSub: function () {
            var ids = [];
            this.$el.find('.fav_list_body input[type=checkbox]:checked').each(function(index, ele) {
                ids.push(ele.getAttribute('data-fav-id'));
            });
            if(ids.length == 0){
                alert("请至少选择一个~");
                return false;
            }
            var param = {};
            param.fav_ids = ids;
            param.o_id = this.oid;
            param.o_type = this.otype;
            var view = this;
            this.model.save_media_favs(param).done(function (resp) {
                if(resp.code == 0){
                    view.main_el.html(view.initAlreadyFav());
                    view.$el.find('#fav_community_list').modal('hide');
                }
            });
        },

        quickAddFavList: function () {
            var user = window.SHION.currentUser;
            var title = this.$el.find(".v_f_fav_title").val();
            if(title == null || title.trim().length == 0){
                alert("请务必填写标题~");
                return false;
            }
            var is_private = this.$el.find('.v_f_is_private').prop('checked');
            var param = {};
            param.title = title;
            param.user_id = user.id;
            if(is_private){
                param.is_hide = 1;
            }else{
                param.is_hide = 0;
            }

            var view = this;
            this.model.quick_save_favs(param).done(function (resp) {
                if(resp.code == 0){
                    var html = "";
                    var fav = resp.result;
                    if(fav != null){
                        view.$el.find('.no_list_text').hide();
                        view.$el.find('.v_f_fav_title').val("");
                        view.$el.find('.v_f_is_private').prop('checked', false);
                        html+=view.getFavListOneHtml(fav);
                        view.$el.find('.fav_list_body').append(html);
                    }
                }
            });

        },

        favClick: function () {
            var user = window.SHION.currentUser;
            if(user == null || user == undefined){
                alert("要先登录哦~");
                return false;
            }
            var view = this;
            var param = {};
            param.o_type = this.otype;
            param.o_id = this.oid;
            this.model.get_favs_by_uid(param).done(function (resp) {
                if(resp.code == 0){
                    view.$el.find('#fav_community_list').modal('show');
                    view.$el.find('.modal-fc-title').text("请选择收藏夹");
                    view.$el.find('.fav_list_body').html("");
                    var favs = resp.result;
                    if(favs.length == 0){
                        view.$el.find('.no_list_text').html("您还未创建过收藏夹呢~在下面快速创建一个吧~");
                    }else{
                        view.$el.find('.no_list_text').hide();
                        var html = "";
                        for(var i = 0; i < favs.length; i++){
                            html+=view.getFavListOneHtml(favs[i]);
                        }
                        view.$el.find('.fav_list_body').append(html);
                    }
                }
            });

        },

        /** 传入一个收藏夹对象，生成对应的单个html代码块*/
        getFavListOneHtml: function (fav) {
            var html = "<div class=\"tm_big_unit\">"+
                "<div class=\"tm_small_unit\"><input class=\"v_f_pointer\" data-fav-id='"+fav.id+"' type=\"checkbox\"/> "+fav.title+"</div>"+
                "<div class=\"tm_small_unit\">";

            if(fav.is_hide == 1){
                html+="<span class=\"label label-danger\">私有</span>"
            }else{
                html+="<span class=\"label label-primary\">公开</span>"
            }

            html+="</div></div>";
            return html;
        },

        getAlReadyFavHtml: function () {
            return "<div class=\"v_player_btn_group v_y_fav_btn\"><span class=\"glyphicon glyphicon-star\"> 已收藏 </span></div>"
        },
        getNotFavHtml: function () {
            return "<div class=\"v_player_btn_group v_fav_btn\"><span class=\"glyphicon glyphicon-star\"/><span class='v_y_fav_text'> 收藏 </span></div>"
        }
    });

    module.exports = FavView;

});