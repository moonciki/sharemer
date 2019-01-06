define(function (require, exports, module) {

    var TimelineView = require('module/blog/view/TimelineView');

    var TimelinePage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new TimelineView({
                el: '#listView'
            });
        },

        go: function (id) {
            this.listView.request(id);
        },

        remove: function () {
            this.listView.remove();

            Backbone.View.prototype.remove.apply(this);
        }
    });

    module.exports = TimelinePage;
})