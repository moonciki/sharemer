define(function(require, exports, module) {

    //seajs.use('./css/module/homelist.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/home/tpl/HomeView.tpl');

    var Home = require('module/home/model/Home');

    var HomeView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,

        initialize: function() {
            this.model = new Home();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.home').removeClass("menu_unit").addClass("menu_unit_point");
        },

        events: {

        },

        request: function() {
        }
    });

    module.exports = HomeView;

});