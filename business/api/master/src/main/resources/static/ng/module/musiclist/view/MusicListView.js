define(function(require, exports, module) {

    //seajs.use('./css/module/musiclist.css');

    var Template = require('module/musiclist/tpl/MusicListView.tpl');

    var Pagination = require('component/Pagination');

    var MusicList = require('module/musiclist/model/MusicList');

    var MusicListView = Backbone.View.extend({
        template: Template,

        $trTemplate: $($(Template).filter('#trTemplate').html().trim()),

        initialize: function() {
            this.model = new MusicList();

            this.$el.append($(this.template).filter(':not(script)'));

            var view = this;

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.request(newPageNo);
                }
            });

        },

        events: {
            'click .j_add': '_Add',
            'click .j_music_add' :'spider_music'
        },

        _Add: function () {
            var param = {};
            param.type = this.$el.find(".j_type").val();
            param.offset = this.$el.find(".j_offset").val();
            this.model.spider(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("落地成功！");
                }
            });
        },

        spider_music: function(event) {
            var id = $(event.currentTarget).parents('tr').find('.j_wy_id').text();
            var param = {};
            param.list_id = id;
            this.model.spider_music(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("导入音乐大成功！！");
                }
            });
        },

        request: function(pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('tbody').empty();
            var view = this;
            this.model.fetch().done(function(resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }
            });
        },


        render: function() {
            this.$el.find('tbody').empty();
            var page = this.model.lastPage,
                musiclists = page.records;

            var trs = [];
            for (var i = 0; i < musiclists.length; i++) {
                trs.push(this._renderOne(musiclists[i]));
            }

            this.$el.find('tbody').append(trs);

            this.pagination.render(page.pageNo, page.pageCount, page.totalCount);
        },

        _renderOne: function(musiclist) {
            var $tr = this.$trTemplate.clone();
            $tr.find('.j_id').text(musiclist.id);
            $tr.find('.j_title').html("<a href='http://music.163.com/#/playlist?id="
                +musiclist.wy_id+"' target='_blank'>"+musiclist.title+"</a>");
            $tr.find('.j_wy_id').text(musiclist.wy_id);
            $tr.find('.j_wy_type').text(musiclist.wy_type);
            $tr.find('.j_ctime').text(musiclist.ctime);
            $tr.find('.j_is_done').html(musiclist.is_done == 0 ?
                "<span class=\"label label-default\">未导入</span>":
                "<span class=\"label label-success\">已经导入</span>")
            return $tr;
        }
    });

    module.exports = MusicListView;

})