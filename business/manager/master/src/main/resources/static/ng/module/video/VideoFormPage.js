define(function(require, exports, module) {

    var VideoFormView = require('module/video/view/VideoFormView');

    var VideoEditPage = Backbone.View.extend({
        initialize: function() {
            this.$el.append('<h4 class="j_page-title">摄入视频信息</h4><div id="formView" />');

            this.formView = new VideoFormView({
                el: '#formView'
            });
        },

        go: function(id) {
            this.formView.request(id);
        },

        remove: function() {
            this.formView.remove();
            Backbone.View.prototype.remove.apply(this);
        }
    });

    module.exports = VideoEditPage;
});