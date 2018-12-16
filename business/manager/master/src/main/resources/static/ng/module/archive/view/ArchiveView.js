define(function (require, exports, module) {

    seajs.use('./css/module/base.css');

    var Template = require('module/archive/tpl/ArchiveView.tpl');
    var Template2 = require('module/archive/tpl/PlayModal.tpl');
    var Template3 = require('module/archive/tpl/InfoModal.tpl');

    var Pagination = require('component/Pagination');

    var Archive = require('module/archive/model/Archive');

    var ArchiveView = Backbone.View.extend({
        template: Template,
        template2: Template2,
        template3: Template3,

        $trTemplate: $($(Template).filter('#trTemplate').html().trim()),

        initialize: function () {
            this.model = new Archive();

            this.$el.append($(this.template).filter(':not(script)'));
            this.$el.append($(this.template2).clone());
            this.$el.append($(this.template3).clone());

            var view = this;

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function (newPageNo) {
                    view.request(newPageNo);
                }
            });

        },

        events: {
            'click .j_archive_play': 'archivePlay',
            'click .j_archive_info': 'archiveInfo',
            'click .j_search': '_search',
            'click .pass': '_pass',
            'click .not_pass': '_notpass'
        },

        _pass() {
            var view = this;
            view.model.pass(1).done(function (resp) {
                if (resp.code == 0) {
                    view.$el.find('#info_archive').modal('hide');
                    view.$el.find('.status_' + view.model.currentId).html("<span class=\"label label-success\">通过</span>");
                }
            });
        },

        _notpass() {
            var view = this;
            view.model.pass(2).done(function (resp) {
                if (resp.code == 0) {
                    view.$el.find('#info_archive').modal('hide');
                    view.$el.find('.status_' + view.model.currentId).html("<span class=\"label label-warning\">未通过</span>");
                }
            });
        },

        _search: function () {
            this.request(1);
        },

        archivePlay: function (event) {
            var file = $(event.currentTarget).parents('tr').find('.j_file').val();
            var title = $(event.currentTarget).parents('tr').find('.j_title').text();
            this.$el.find('#play_archive').modal('show');
            this.$el.find('.modal-title').text(title + "---->稿件放送中...");
            this.$el.find('.modal-body').html("<embed src=\"" + file + "\"></embed>");
        },

        archiveInfo: function (event) {
            this.model.currentId = $(event.currentTarget).parents('tr').find('.j_id').text();
            var title = $(event.currentTarget).parents('tr').find('.j_title').text();
            var status = $(event.currentTarget).parents('tr').find('.j_status').val();
            var desc = $(event.currentTarget).parents('tr').find('.j_desc').val();
            this.$el.find('#info_archive').modal('show');
            this.$el.find('.modal-title').text(title + "---->稿件放送中...");
            this.$el.find('.modal_desc').text(desc);
            if (status == 0 || status == 2) {
                this.$el.find('.modal_btn').html("<button type=\"button\" class=\"pass btn btn-info\">通过</button>&nbsp;&nbsp;<button type=\"button\" class=\"not_pass btn btn-warning\">不通过</button>");
            }

        },

        request: function (pageNo) {
            if (pageNo == undefined || pageNo == null) {
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.model.params.key = this.$el.find('.j_s_title').val();
            this.model.params.type = this.$el.find('.j_s_type').val();
            this.$el.find('tbody').empty();
            var view = this;
            this.model.fetch().done(function (resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }
            });
        },


        render: function () {
            this.$el.find('tbody').empty();
            var page = this.model.lastPage,
                archives = page.records;

            var trs = [];
            for (var i = 0; i < archives.length; i++) {
                trs.push(this._renderOne(archives[i]));
            }

            this.$el.find('tbody').append(trs);

            this.pagination.render(page.pageNo, page.pageCount);
        },

        _renderOne: function (archive) {
            var $tr = this.$trTemplate.clone();
            $tr.find('.j_id').text(archive.id);
            $tr.find('.j_title').html(archive.title);
            $tr.find('.j_status').html(archive.status);
            $tr.find('.j_cover').html("<img src=\"" + archive.cover + "?imageView2/1/w/100/h/60\"/>");
            var pubType = "";
            if (archive.publish_type == 0) {
                pubType = "<span class=\"label label-primary\">原创</span>";
            } else if (archive.publish_type == 1) {
                pubType = "<span class=\"label label-info\">搬运</span>";
            } else if (archive.publish_type == 2) {
                pubType = "<span class=\"label label-success\">翻唱/鬼畜/改编</span>";
            }
            $tr.find('.j_publish_type').html(pubType);
            var archiveStatus = "";
            if (archive.status == 0) {
                archiveStatus = "<span class=\"label label-primary\">锁定</span>";
            } else if (archive.status == 1) {
                archiveStatus = "<span class=\"label label-success\">通过</span>";
            } else if (archive.status == 2) {
                archiveStatus = "<span class=\"label label-warning\">未通过</span>";
            } else if (archive.status == 3) {
                archiveStatus = "<span class=\"label label-danger\">删除</span>";
            }
            $tr.find('.j_archive_status').html("<span class='status_" + archive.id + "'>" + archiveStatus + "</span>");
            $tr.find('.j_origin_author').text(archive.origin_author);
            $tr.find('.j_origin_title').text(archive.origin_title);
            $tr.find('.j_user_id').text(archive.user_id);
            $tr.find('.j_desc').val(archive.desc);
            $tr.find('.j_file').val("http://media.sharemer.com/" + archive.file);
            if (archive.tags != null && archive.tags.length > 0) {
                for (var i = 0; i < archive.tags.length; i++) {
                    $tr.find('.j_tags').append("<span style=\"background-color: #ff99c4\" class=\"label label-info\">"
                        + archive.tags[i].tag_name + "</span>")
                }
            }
            return $tr;
        }
    });

    module.exports = ArchiveView;

})