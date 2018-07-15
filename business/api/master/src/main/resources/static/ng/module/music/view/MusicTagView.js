define(function(require, exports, module) {

    seajs.use('./css/module/music_tag.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/music/tpl/MusicTagView.tpl');

    var Music = require('module/music/model/Music');

    var Pagination = require('component/Pagination');

    var PlayView = require('module/common/view/PlayView');

    var MusicTagView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,

        initialize: function() {
            this.model = new Music();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.music').removeClass("menu_unit").addClass("menu_unit_point");

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.getMusicsByTag(newPageNo);
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
                window.location.href="/#music";
                return;
            }else{
                var className = ".tag_"+id;
                this.$el.find(".child_menu_pointer").removeClass("child_menu_pointer").addClass("child_menu");
                this.$el.find(className).removeClass("child_menu").addClass("child_menu_pointer");
            }
            this.model.id = id;
            var view = this;
            this.model.get_tag().done(function (resp) {
                if(resp.code == 0){
                    if(resp.result != null && resp.result != undefined){
                        view.$el.find('.tag_name_text').text(resp.result.tag_name);
                    }else{
                        view.$el.find('.tag_name_text').text("未找到");
                    }
                }else{
                    view.$el.find('.tag_name_text').text("未知");
                }
            });

            this.getMusicsByTag(1);
        },

        getMusicsByTag: function (pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('.main_data_area').empty();
            var view = this;

            this.model.get_music_by_tag().done(function(resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }else{
                    view.$el.find('.main_data_area').html("<h1>迷之错误</h1>");
                }
            });
        },

        render: function() {
            this.$el.find('.main_data_area').empty();
            var page = this.model.lastPage,
                musics = page.records;
            if(musics == null || musics.length == 0){
                this.$el.find('.main_data_area').html("<h1>再怎么找也没有啦~</h1>");
                return;
            }
            var html = "";
            for (var i = 0; i < musics.length; i++) {
                if(i % 2 != 0){
                    html += "<div class='data_unit_left'>";
                }else{
                    html += "<div class='data_unit_right'>";
                }
                html+="<div class='data_img'><div class='img_back' style=\"background-image: url("+musics[i].cover+");\"><div class='common_play img_back_play' data-title='"+musics[i].title+"' data-type='0' data-url='//music.163.com/outchain/player?type=2&id="+musics[i].song_id+"&auto=1&height=66'></div></div></div>"+
                    "<div class='data_c1'><div class='data_c1_1'><a href='/#music/info/"+musics[i].id+"' target='_blank'>"+
                    musics[i].title
                    +"</a></div>" +
                    "<div class='data_c2'><div class='data_c2_1'><span class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;音乐人：" +
                        musics[i].songer +
                    "</div><div class='data_c2_1'><span class='glyphicon glyphicon-time'></span>&nbsp;&nbsp;发行时间：";
                    if(musics[i].publish_time != null && musics[i].publish_time != undefined) {
                        html += musics[i].publish_time.replace("T00:00","");
                    }
                    html+="</div></div>"+
                    "<div class='data_c3'><span class='glyphicon glyphicon-info-sign'></span>&nbsp;&nbsp;音乐信息："+ musics[i].desc+"</div>"+
                    "</div>";
                html+="</div>";
            }
            this.$el.find('.main_data_area').append(html);
            this.pagination.render(page.pageNo, page.pageCount);
        }
    });

    module.exports = MusicTagView;

});