define(function (require, exports, module) {

    seajs.use('../css/module/blog/timeline.css');

    var timelineHtml = require('module/blog/tpl/TimelineView.tpl');
    var Timeline = require('module/blog/model/Timeline');
    var mydate = null;
    var CommonView = require('module/blog/view/CommonView');

    var TimelineView = Backbone.View.extend({
        timeline: timelineHtml,
        current_mid: null,
        initialize: function () {
            this.model = new Timeline();
            mydate = new Date();
            this.commonView = new CommonView();
        },

        events: {
            'click .user_header_menu_box': 'router'
        },

        router: function (event) {
            var role = event.currentTarget.getAttribute("data-role");
            this.commonView.router(role);
        },

        request: function (id) {
            this.commonView.initCommon(id, this.$el, this.timeline, '.menu_timeline');
            this.model.id = id;
        }
    });
    module.exports = TimelineView;
});