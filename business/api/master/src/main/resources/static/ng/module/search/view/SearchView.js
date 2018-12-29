define(function(require, exports, module) {

    seajs.use('./css/module/search_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/search/tpl/SearchView.tpl');

    var Search = require('module/search/model/Search');

    var SearchView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        $mtrTemplate: $($(Template).html().trim()),
        initialize: function() {
            this.model = new Search();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.search').removeClass("menu_unit").addClass("menu_unit_point");
        },

        events: {
            'click .s_btn': 'query',
            'click .s_main_more_btn': 'more',
            'click .s_music': 'music',
            'click .s_video': 'video',
            'click .s_gj': 'gj',
            'click .s_fav': 'fav'
        },

        music: function () {
            this.model.media = "music";
            this.model.c_p = 1;
            this.$el.find('.s_main_data_content').html("");
            window.location.href = "/#search/"+this.model.media+"?key="+(this.model.key==null ? "":this.model.key);
        },

        video: function () {
            this.model.media = "video";
            this.model.c_p = 1;
            this.$el.find('.s_main_data_content').html("");
            window.location.href = "/#search/"+this.model.media+"?key="+(this.model.key==null ? "":this.model.key);
        },

        gj: function () {
            this.model.media = "gj";
            this.model.c_p = 1;
            this.$el.find('.s_main_data_content').html("");
            window.location.href = "/#search/"+this.model.media+"?key="+(this.model.key==null ? "":this.model.key);
        },

        fav: function () {
            this.model.media = "fav";
            this.model.c_p = 1;
            this.$el.find('.s_main_data_content').html("");
            window.location.href = "/#search/"+this.model.media+"?key="+(this.model.key==null ? "":this.model.key);
        },

        more: function () {
            this.model.c_p = this.model.c_p+1;
            this.requestCore(this.model.media, this.model.key);
        },

        query: function () {
            var queryKey = this.$el.find('.s_txt').val();
            if(queryKey != null && queryKey != undefined && queryKey != this.model.key){
                this.$el.find('.s_main_data_content').html("");
                this.model.c_p = 1;
                window.location.href = "/#search/"+this.model.media+"?key="+queryKey;
            }
        },

        request: function(key, queryKey) {
            var view = this;
            view.reset_btn(key);
            if(queryKey != undefined){
                this.$el.find('.s_txt').val(queryKey.key);
                this.requestCore(key, queryKey.key);
            }else {
                this.$el.find('.s_txt').val("");
                this.$el.find('.s_no_data').show();
                this.$el.find('.s_main_more').hide();
                this.$el.find('.s_main_data_content').html("");
            }
        },

        requestCore: function (key, keyword) {
            var view = this;

            if(keyword != undefined && keyword != null && keyword != ""){
                if(keyword.length > 15){
                    alert("搜索关键词不可以超过15个字符！")
                }else{
                    view.model.key = keyword;
                    var param = {};
                    param.c_p = view.model.c_p;
                    param.key = view.model.key;
                    param.sort = view.model.sort;
                    param.type = view.model.type;
                    this.model.get_medias_by_key(param).done(function(resp) {
                        if (resp.code == 0) {
                            view.render(resp);
                        }else{
                            view.$el.find('.s_main_data_content').html("<h1>迷之错误</h1>");
                        }
                    });
                }
            }
        },

        render: function (resp) {
            var medias = resp.result;
            var html = "";
            if(medias != undefined) {
                var length = medias.length;
                this.$el.find('.s_no_data').hide();
                this.$el.find('.s_main_more').show();
                var link = "";
                switch (this.model.type){
                    case 0:
                        link = "/#music/info/";
                        break;
                    case 1:
                        link = "/#video/info/";
                        break;
                    case 2:
                        link = "/#archive/info/";
                        break;
                    case 4:
                        link = "/#favlist/info/";
                        break;
                }
                for (var i = 0; i < length; i++) {

                    html += "<div class='s_content_unit'><div class='s_content_unit_1' style=\"background-image: url('"+(medias[i].cover+"?imageView2/1/w/160/h/160")+"'), url('/image/s_default.jpg')\">" +
                        "</div><div class='s_content_unit_2'><a href='"+(link+medias[i].id)+"' target='_blank' title='"+medias[i].title+"'>"+(medias[i].title.length > 29 ? medias[i].title.substring(0, 29).replace(this.model.key, "<span style='color: red'>"+this.model.key+"</span>") : medias[i].title.replace(this.model.key, "<span style='color: red'>"+this.model.key+"</span>"))+"</a></div>" +
                        "<div class='s_content_unit_3'><span class='glyphicon glyphicon-time'></span>&nbsp;&nbsp;分享于"+medias[i].ctime.substring(0,10)+"</div></div>";
                }
                this.$el.find('.s_main_data_content').append(html);

            }else{
                this.$el.find('.s_no_data').show();
                this.$el.find('.s_main_more').hide();
            }
        },

        reset_btn: function (key) {
            this.$el.find(".s_main_data_menu_area_unit_pointer").removeClass("s_main_data_menu_area_unit_pointer");
            this.model.media = key;
            switch (key){
                case "music":
                    this.$el.find('.s_music').addClass("s_main_data_menu_area_unit_pointer");
                    this.model.type = 0;
                    break;
                case "video":
                    this.$el.find('.s_video').addClass("s_main_data_menu_area_unit_pointer");
                    this.model.type = 1;
                    break;
                case "gj":
                    this.$el.find('.s_gj').addClass("s_main_data_menu_area_unit_pointer");
                    this.model.type = 2;
                    break;
                case "fav":
                    this.$el.find('.s_fav').addClass("s_main_data_menu_area_unit_pointer");
                    this.model.type = 3;
                    break;
                default:
                    this.model.media = "music";
                    this.$el.find('.s_music').addClass("s_main_data_menu_area_unit_pointer");
                    this.model.type = 0;
                    break;
            }
        }
    });

    module.exports = SearchView;

});