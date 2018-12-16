define(function (require, exports, module) {

    seajs.use('./css/module/video_tag.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/archive/tpl/ArchiveTagView.tpl');

    var Archive = require('module/archive/model/Archive');

    var Pagination = require('component/Pagination');

    var ArchiveTagView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,

        initialize: function () {
            this.model = new Archive();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.archive').removeClass("menu_unit").addClass("menu_unit_point");

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function (newPageNo) {
                    view.getArchivesByTag(newPageNo);
                }
            });
        },

        events: {},

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#archive";
                return;
            } else {
                var className = ".v_tag_" + id;
                this.$el.find(".v_child_menu_pointer").removeClass("v_child_menu_pointer").addClass("v_child_menu");
                this.$el.find(className).removeClass("v_child_menu").addClass("v_child_menu_pointer");
            }
            this.model.id = id;
            var view = this;
            this.model.get_tag().done(function (resp) {
                if (resp.code == 0) {
                    if (resp.result != null && resp.result != undefined) {
                        view.$el.find('.v_tag_name_text').text(resp.result.tag_name);
                    } else {
                        view.$el.find('.v_tag_name_text').text("未找到");
                    }
                } else {
                    view.$el.find('.v_tag_name_text').text("未知");
                }
            });

            this.getArchivesByTag(1);
        },

        getArchivesByTag: function (pageNo) {
            if (pageNo == undefined || pageNo == null) {
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('.v_main_data_area').empty();
            var view = this;

            this.model.get_archive_by_tag().done(function (resp) {
                if (resp.code == 0) {
                    view.render(resp);
                } else {
                    view.$el.find('.v_main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        render: function () {
            this.$el.find('.v_main_data_area').empty();
            var page = this.model.lastPage,
                archives = page.records;
            if (archives == null || archives.length == 0) {
                this.$el.find('.v_main_data_area').html("<h1>再怎么找也没有啦~</h1>");
                this.$el.find('.page_area').hide();
                return;
            }
            this.$el.find('.page_area').show();
            var html = "";
            for (var i = 0; i < archives.length; i++) {
                if ((i + 1) % 5 != 0) {
                    html += "<div class='v_main_data_area_unit v_main_data_area_unit_jx'>";
                } else {
                    html += "<div class='v_main_data_area_unit'>";
                }

                var publish_txt = "";
                var publish_color = "#bbbbbb";
                if (archives[i].publish_type == 0) {
                    publish_txt = "原创";
                    publish_color = "#ff7f93";
                } else if (archives[i].publish_type == 1) {
                    publish_txt = "搬运";
                    publish_color = "#8cbdff";
                } else if (archives[i].publish_type == 2) {
                    publish_txt = "翻唱/鬼畜/改编";
                    publish_color = "#54c4d0";
                }

                html += "<div class='v_main_data_area_unit_cover'><div class=\"v_data_01\" style=\"background-size: 100% 100%;background-image: url('" + (archives[i].cover + "?imageView2/1/w/186/h/116") + "'), url('/image/default_v.jpg')\">" +
                    "<div class='common_play v_img_back_play'></div></div>" +
                    "<div class='v_main_data_area_unit_txt'><span class='v_before_tag' style='border: 1px solid " + publish_color + "; color: " + publish_color + ";'>" + publish_txt + "</span><a href=\"/#archive/info/" + archives[i].id + "\" target='_blank' title='" + archives[i].title + "'>";

                if (archives[i].title.length > 30) {
                    html += archives[i].title.substring(0, 30);
                } else {
                    html += archives[i].title;
                }
                html += "</a></div></div></div>";
            }
            this.$el.find('.v_main_data_area').append(html);
            this.pagination.render(page.pageNo, page.pageCount, page.totalCount);
        }
    });

    module.exports = ArchiveTagView;

});