define(function(require, exports, module) {

    seajs.use('./css/module/video_tag.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/video/tpl/VideoTagView.tpl');

    var Video = require('module/video/model/Video');

    var Pagination = require('component/Pagination');

    var PlayView = require('module/common/view/PlayView');

    var VideoTagView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,

        initialize: function() {
            this.model = new Video();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.video').removeClass("menu_unit").addClass("menu_unit_point");

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.getVideosByTag(newPageNo);
                }
            });

            this.playView = new PlayView({
                el: this.$el
            });
        },

        events: {
            'click .common_play': 'commonPlay'
        },

        commonPlay: function (event) {
            this.playView.commonPlay(event);
        },

        request: function(id) {
            if(id == null || id == undefined || id == ""){
                window.location.href="/#video";
                return;
            }else{
                var className = ".v_tag_"+id;
                this.$el.find(".v_child_menu_pointer").removeClass("v_child_menu_pointer").addClass("v_child_menu");
                this.$el.find(className).removeClass("v_child_menu").addClass("v_child_menu_pointer");
            }
            this.model.id = id;
            var view = this;
            this.model.get_tag().done(function (resp) {
                if(resp.code == 0){
                    if(resp.result != null && resp.result != undefined){
                        view.$el.find('.v_tag_name_text').text(resp.result.tag_name);
                    }else{
                        view.$el.find('.v_tag_name_text').text("未找到");
                    }
                }else{
                    view.$el.find('.v_tag_name_text').text("未知");
                }
            });

            this.getVideosByTag(1);
        },

        getVideosByTag: function (pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('.v_main_data_area').empty();
            var view = this;

            this.model.get_video_by_tag().done(function(resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }else{
                    view.$el.find('.v_main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        render: function() {
            this.$el.find('.v_main_data_area').empty();
            var page = this.model.lastPage,
                videos = page.records;
            if(videos == null || videos.length == 0){
                this.$el.find('.v_main_data_area').html("<h1>再怎么找也没有啦~</h1>");
                return;
            }
            var html = "";
            for (var i = 0; i < videos.length; i++) {
                if((i+1)%5 != 0){
                    html += "<div class='v_main_data_area_unit v_main_data_area_unit_jx'>";
                }else{
                    html += "<div class='v_main_data_area_unit'>";
                }

                html+="<div class='v_main_data_area_unit_cover'><div class=\"v_data_01\" style=\"background-size: 100%;background-image: url('"+videos[i].cover+"'), url('/image/default_v.jpg')\">" +
                    "<div class='v_net_tag' style='background-color: "+videos[i].net_color+"' title='来源'>"+videos[i].net_name+"</div>"+
                    "<div class='common_play v_img_back_play' data-title='"+videos[i].title+"' data-type='1' data-url='"+videos[i].video_url+"'></div></div>" +
                    "<div class='v_main_data_area_unit_txt'><a href=\"/#video/info/"+videos[i].id+"\" target='_blank' title='"+videos[i].title+"'>";

                if(videos[i].title.length > 30){
                    html+=videos[i].title.substring(0, 30);
                }else{
                    html+=videos[i].title;
                }
                html+="</a></div></div></div>";
            }
            this.$el.find('.v_main_data_area').append(html);
            this.pagination.render(page.pageNo, page.pageCount, page.totalCount);
        }
    });

    module.exports = VideoTagView;

});