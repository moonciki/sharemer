define(function(require, exports, module) {

    seajs.use('./css/module/upload_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/upload/tpl/UploadView.tpl');

    var Upload = require('module/upload/model/Upload');

    var UploadView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        $mtrTemplate: $($(Template).html().trim()),
        initialize: function() {
            this.model = new Upload();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.tg').removeClass("menu_unit").addClass("menu_unit_point");
        },

        events: {

        },

        request: function() {

        }
    });

    module.exports = UploadView;

});