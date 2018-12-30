define(function (require, exports, module) {

    seajs.use('../css/module/blog/home.css');

    var Template = require('module/blog/tpl/BlogView.tpl');
    var Blog = require('module/blog/model/Blog');

    var BlogView = Backbone.View.extend({
        template: Template,
        current_mid: null,
        initialize: function () {
            this.model = new Blog();
            this.$el.append(this.template);
        },

        events: {},

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#blog/404";
                return;
            }
            this.model.id = id;
        }
    });
    module.exports = BlogView;
});