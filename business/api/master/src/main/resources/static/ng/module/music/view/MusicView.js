define(function(require, exports, module) {

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/music/tpl/MusicView.tpl');

    var Music = require('module/music/model/Music');

    var MusicView = Backbone.View.extend({
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
        },

        events: {

        },

        request: function() {
            this.$el.find('.haha').text("wocaonima");
        }
    });

    module.exports = MusicView;

});