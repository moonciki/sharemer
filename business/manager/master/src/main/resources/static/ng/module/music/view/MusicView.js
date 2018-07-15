define(function(require, exports, module) {

    seajs.use('./css/module/base.css');

    var Template = require('module/music/tpl/MusicView.tpl');
    var Template2 = require('module/music/tpl/PlayModal.tpl');
    var Template3 = require('module/music/tpl/PlayVideoModal.tpl');

    var Pagination = require('component/Pagination');

    var Music = require('module/music/model/Music');

    var MusicView = Backbone.View.extend({
        template: Template,
        template2: Template2,
        template3: Template3,

        $trTemplate: $($(Template).filter('#trTemplate').html().trim()),

        initialize: function() {
            this.model = new Music();

            this.$el.append($(this.template).filter(':not(script)'));
            this.$el.append($(this.template2).clone());
            this.$el.append($(this.template3).clone());

            var view = this;

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.request(newPageNo);
                }
            });

        },

        events: {
            'click .j_music_play' :'musicPlay',
            'click .play_video' : 'videoPlay',
            'click .video_shuaxin': 'videoShuaxin',
            'click .j_video_play': 'videoList',
            'click .j_search' :'_search'
        },

        _search:function () {
            this.request(1);
        },

        musicPlay: function (event) {
            var song_id = $(event.currentTarget).parents('tr').find('.j_song_id').val();
            var title = $(event.currentTarget).parents('tr').find('.j_title').text();
            this.$el.find('#play_music').modal('show');
            this.$el.find('.modal-title').text(title+"---->播放中...");
            this.$el.find('.modal-body').html("<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" "+
                "width=860 height=86 src=\"//music.163.com/outchain/player?type=2&id="+song_id+"&auto=1&height=66\"></iframe>");
        },

        videoPlay: function (event) {
            var v_url = $(event.currentTarget).parents('tr').find('.j_v_url').val();
            var title = $(event.currentTarget).parents('tr').find('.j_v_title').text();
            this.$el.find('#play_video').modal('show');
            this.$el.find('.modal-v-title').text(title+"---->视频播放中...");
            this.$el.find('.modal-v-body').html("<embed type=\"application/x-shockwave-flash\" class=\"edui-faked-video\" " +
                "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" src=\""+v_url+"\" " +
                "width=\"840px\" height=\"440px\" wmode=\"transparent\" play=\"true\" loop=\"false\" menu=\"false\" allowscriptaccess=\"never\" allowfullscreen=\"true\">");
        },

        videoShuaxin: function (event) {
            var id = $(event.currentTarget).attr("data-mus-id");
            var view = this;
            this.model.musicVideoRel(id).done(function(resp) {
                if (resp.code === 0) {
                    view.videoListHandler(id);
                }
            });
        },

        videoList: function (event) {
            var id = $(event.currentTarget).parents('tr').find('.j_id').text();
            var title = $(event.currentTarget).parents('tr').find('.j_title').text();
            this.$el.find('#play_music').modal('show');
            this.$el.find('.modal-title').text(title+"の関連動画");
            this.videoListHandler(id);
        },

        videoListHandler: function(id){
            var view = this;
            this.model.getVideoByMusicId(id).done(function(resp) {
                if (resp.code === 0) {
                    var videos = resp.result;
                    var html = "<table class=\"table table-striped table-condensed admin\">"+
                        "<thead><tr><th style=\"width: 10rem\">ID</th>"+
                        "<th style=\"width: 20rem\">标题</th><th style=\"width: 10rem\">封面</th>"+
                        "<th style=\"width: 20rem\">来源</th><th style=\"width: 15rem\">操作</th>"+
                        "</tr></thead><tbody>";

                    for(var i = 0; i < videos.length; i++){
                        html+="<tr><td class=\"j_v_id\">"+videos[i].id+"</td>"+
                            "<td class=\"j_v_title\">"+videos[i].title+"</td>"+
                            "<td class=\"j_v_cover\"><img src = \""+videos[i].cover+"\" width='50px'/></td>"+
                            "<input type='hidden' class='j_v_url' value='"+videos[i].video_url+"'/>"+
                            "<td class=\"j_v_source\">";
                        switch(videos[i].type){
                            case 0:
                                html+="bilibli";
                                break;
                            case 1:
                                html+="acfun";
                                break;
                            case 12:
                                html+="音悦台";
                                break;
                            default:
                                html+="其他";
                                break;
                        }
                        html += "</td>"+
                            "<td><button type=\"button\" class=\"play_video btn btn-default\">播放</button>"+
                            "</td></tr>"
                    }

                    html += "</tbody></table><br/>" +
                        "<button type=\"button\" data-mus-id=\""+id+"\" class=\"video_shuaxin btn btn-primary\">重新关联</button>";
                    view.$el.find('.modal-body').html(html);
                }
            });
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
                musics = page.records;

            var trs = [];
            for (var i = 0; i < musics.length; i++) {
                trs.push(this._renderOne(musics[i]));
            }

            this.$el.find('tbody').append(trs);

            this.pagination.render(page.pageNo, page.pageCount);
        },

        _renderOne: function(music) {
            var $tr = this.$trTemplate.clone();
            $tr.find('.j_id').text(music.id);
            $tr.find('.j_title').html("<a href='http://music.163.com/#/song?id="
                +music.song_id+"' target='_blank'>"+music.title+"</a>");
            $tr.find('.j_cover').html("<img src=\""+music.cover+"\" width = \"50px\"/>");
            $tr.find('.j_album_title').text(music.album_title);
            $tr.find('.j_songer').text(music.songer);
            $tr.find('.j_r_name').text(music.r_name);
            $tr.find('.j_song_id').val(music.song_id);
            $tr.find('.j_mv_playurl').val(music.mv_playurl);
            if(music.tags != null && music.tags.length > 0){
                for(var i = 0; i < music.tags.length; i++){
                    $tr.find('.j_tags').append("<span class=\"label label-info\">"
                        +music.tags[i].tag_name+"</span>")
                }
            }
            return $tr;
        }
    });

    module.exports = MusicView;

})