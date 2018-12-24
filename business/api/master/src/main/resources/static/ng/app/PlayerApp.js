define(function(require, exports, module) {

    var Top = require('component/Top');

    var ContractApp = Backbone.View.extend({

        main$el: $('.main'),
        pageEl: '#page',

        initialize: function() {
            this.top = new Top();
            this.lastPage = null;
            if(bootbox){
                bootbox.setDefaults({title:'请求结果'});
            }
        },

        renderArchivePlayer: function (id) {
            var app = this;

            require.async('module/player/PlayerPage', function (PlayerPage) {
                if (!(app.lastPage instanceof PlayerPage)) {
                    app.reset();
                    app.lastPage = new PlayerPage({
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

    module.exports = ContractApp;
});
