define(function(require, exports, module) {

    var Template = require('module/video/tpl/VideoFormView.tpl');

    var Video = require('module/video/model/Video');

    var VideoFormView = Backbone.View.extend({

        template: Template.trim(),

        initialize: function(options) {
            this.model = new Video();
            this.$el.html(this.template);
        },

        events: {
            'click .add_b' :'addBVideo',
            'click .add_a' :'addAVideo',
            'click .add_m' :'addMVideo',
            'click .add_c' :'addCVideo'
        },

        addBVideo: function () {
            var param = {};
            param.start = this.$el.find('.j_b_s_id').val();
            param.end = this.$el.find('.j_b_e_id').val();

            this.model.deepBVideo(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("导入B站视频大成功！");
                }
            });
        },

        addAVideo: function () {
            var param = {};
            param.start = this.$el.find('.j_a_s_id').val();
            param.end = this.$el.find('.j_a_e_id').val();

            this.model.deepAVideo(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("导入B站视频大成功！");
                }
            });
        },

        addMVideo: function () {
            var param = {};
            param.start = this.$el.find('.j_m_s_id').val();
            param.end = this.$el.find('.j_m_e_id').val();

            this.model.deepMVideo(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("导入M站视频大成功！");
                }
            });
        },

        addCVideo: function () {
            var param = {};
            param.start = this.$el.find('.j_c_s_id').val();
            param.end = this.$el.find('.j_c_e_id').val();

            this.model.deepCVideo(param).done(function(resp) {
                if (resp.code == 0) {
                    alert("导入M站视频大成功！");
                }
            });
        },

        request: function(id, options) {

        }
    });

    module.exports = VideoFormView;
});