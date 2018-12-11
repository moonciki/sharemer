define(function(require, exports, module) {

    seajs.use('./css/module/base.css');

    var Template = require('module/video/tpl/VideoView.tpl');
    var Template2 = require('module/video/tpl/PlayModal.tpl');

    var Pagination = require('component/Pagination');

    var Video = require('module/video/model/Video');

    var VideoView = Backbone.View.extend({
        template: Template,
        template2: Template2,

        $trTemplate: $($(Template).filter('#trTemplate').html().trim()),

        initialize: function() {
            this.model = new Video();

            this.$el.append($(this.template).filter(':not(script)'));
            this.$el.append($(this.template2).clone());

            var view = this;

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.request(newPageNo);
                }
            });

        },

        events: {
            'click .j_video_play' :'videoPlay',
            'click .j_search' :'_search'
        },

        _search:function () {
            this.request(1);
        },

        videoPlay: function (event) {
            var v_url = $(event.currentTarget).parents('tr').find('.j_mv_playurl').val();
            var embed_type = $(event.currentTarget).parents('tr').find('.j_embed_type').val();
            var title = $(event.currentTarget).parents('tr').find('.j_title').text();
            this.$el.find('#play_video').modal('show');
            this.$el.find('.modal-title').text(title+"---->视频播放中...");
            switch(embed_type){
                case "0" :
                    this.$el.find('.modal-body').html("<embed type=\"application/x-shockwave-flash\" class=\"edui-faked-video\" " +
                        "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" src=\""+v_url+"\" " +
                        "width=\"840px\" height=\"440px\" wmode=\"transparent\" play=\"true\" loop=\"false\" menu=\"false\" allowscriptaccess=\"never\" allowfullscreen=\"true\">");
                    break;
                case "1":
                    break;
                case "2":
                    this.$el.find('.modal-body').html("<iframe style=\"width:840px;height:440px;\" src=\""+v_url+"\" frameborder=\"0\"></iframe>");
                    break;
            }

        },

        request: function(pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.model.params.key = this.$el.find('.j_s_title').val();
            this.model.params.type = this.$el.find('.j_s_type').val();
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
                videos = page.records;

            var trs = [];
            for (var i = 0; i < videos.length; i++) {
                trs.push(this._renderOne(videos[i]));
            }

            this.$el.find('tbody').append(trs);

            this.pagination.render(page.pageNo, page.pageCount);
        },

        _renderOne: function(video) {
            var $tr = this.$trTemplate.clone();
            $tr.find('.j_id').text(video.id);
            $tr.find('.j_title').html(video.title);
            $tr.find('.j_cover').html("<img src=\""+video.cover+"?imageView2/1/w/50/h/50\"/>");
            $tr.find('.j_source').html("<img src=\""+video.logo_url+"\" height='20px'/>&nbsp;&nbsp;"+video.net_name)
            $tr.find('.j_vid').text(video.v_id);
            $tr.find('.j_page').text(video.page);
            $tr.find('.j_mv_playurl').val(video.video_url);
            $tr.find('.j_embed_type').val(video.embed_type);
            $tr.find('.j_robot').text(video.r_name);
            if(video.tags != null && video.tags.length > 0){
                for(var i = 0; i < video.tags.length; i++){
                    $tr.find('.j_tags').append("<span style=\"background-color: #ff80a6\" class=\"label label-info\">"
                        +video.tags[i].tag_name+"</span>")
                }
            }
            return $tr;
        }
    });

    module.exports = VideoView;

})