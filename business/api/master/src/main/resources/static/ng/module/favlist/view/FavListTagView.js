define(function(require, exports, module) {

    seajs.use('./css/module/favlist_tag.css');
    seajs.use('./css/module/fav.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/favlist/tpl/FavListTagView.tpl');

    var FavTemplate = require('module/fav/tpl/FavListUserPageView.tpl')

    var FavList = require('module/favlist/model/FavList');

    var Pagination = require('component/Pagination');

    var FavListTagView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        fav_need_template: $($(FavTemplate).filter("#need_tag_mr").html().trim()),
        fav_not_template: $($(FavTemplate).filter("#not_tag_mr").html().trim()),

        initialize: function() {
            this.model = new FavList();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.favlist').removeClass("menu_unit").addClass("menu_unit_point");

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.getFavListsByTag(newPageNo);
                }
            });
        },

        events: {
            'click .info_tag_fav_unit': 'favListClick'
        },

        favListClick: function (event) {
            var $div = $(event.target);
            var fav_id = $div.parents(".info_tag_fav_unit").attr("data-id");
            window.open("/#favlist/info/"+fav_id);
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#favlist";
                return;
            }else{
                var className = ".f_tag_"+id;
                this.$el.find(".f_child_menu_pointer").removeClass("f_child_menu_pointer").addClass("f_child_menu");
                this.$el.find(className).removeClass("f_child_menu").addClass("f_child_menu_pointer");
            }
            this.model.id = id;
            var view = this;
            this.model.get_tag().done(function (resp) {
                if(resp.code == 0){
                    if(resp.result != null && resp.result != undefined){
                        view.$el.find('.f_tag_name_text').text(resp.result.tag_name);
                    }else{
                        view.$el.find('.f_tag_name_text').text("未找到");
                    }
                }else{
                    view.$el.find('.f_tag_name_text').text("未知");
                }
            });

            this.getFavListsByTag(1);
        },

        getFavListsByTag: function (pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('.f_f_main_data_area').empty();
            var view = this;

            this.model.get_favlist_by_tag().done(function(resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }else{
                    view.$el.find('.f_main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        render: function() {
            this.$el.find('.f_main_data_area').empty();
            var page = this.model.lastPage,
                favlists = page.records;
            if(favlists == null || favlists.length == 0){
                this.$el.find('.f_main_data_area').html("<h1>再怎么找也没有啦~</h1>");
                return;
            }
            var view = this;
            var html = "";
            var div = [];
            var isMr = false;
            for (var i = 1; i <= favlists.length; i++) {
                if(i%5 == 0){
                    isMr = false;
                }else{
                    isMr = true;
                }
                div.push(view.renderOne(favlists[i-1], isMr));
            }
            this.$el.find('.f_main_data_area').html(div);
            this.pagination.render(page.pageNo, page.pageCount);
        },

        renderOne: function (fav, isMr) {
            var $div;
            if(!isMr){
                $div = this.fav_not_template.clone();
            }else{
                $div = this.fav_need_template.clone();
            }
            $div.attr("style","background-image:url('"+fav.cover+"'),url('/image/default_cover.jpg')");
            $div.attr("data-id", fav.id);
            $div.find(".info_fav_title").text(fav.title);
            $div.find(".info_fav_down_text_point").text(0);
            return $div;
        },
    });

    module.exports = FavListTagView;

});