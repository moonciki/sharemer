define(function(require, exports, module) {

    var Top = require('component/Top');

    var BlogApp = Backbone.View.extend({

        main$el: $('.main'),
        pageEl: '#page',

        initialize: function() {
            this.top = new Top();
            this.lastPage = null;
            if(bootbox){
                bootbox.setDefaults({title:'请求结果'});
            }
        },

        renderHome: function (id) {
            var app = this;

            require.async('module/blog/BlogPage', function (BlogPage) {
                if (!(app.lastPage instanceof BlogPage)) {
                    app.reset();
                    app.lastPage = new BlogPage({
                        el: app.pageEl
                    });
                }
                app.lastPage.go(id);
            });
        },

        reset: function() {
            if (this.lastPage) {
                this.lastPage.remove();
                this.main$el.append('<div id="page" style="margin-top: 160px"/>');
            }
        }
    });

    module.exports = BlogApp;
});