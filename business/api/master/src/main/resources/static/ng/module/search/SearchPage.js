define(function (require, exports, module) {

    seajs.use('./css/module/menu.css');

    var SearchView = require('module/search/view/SearchView');

    var SearchPage = Backbone.View.extend({
        initialize: function () {
            this.$el.html('<div id="listView"></div>');

            this.listView = new SearchView({
                el: '#listView'
            });
        },

        go: function (key, queryKey) {
            this.listView.request(key, queryKey);
        },

        remove: function () {
            this.listView.remove();

            Backbone.View.prototype.remove.apply(this);
        }
    });

    module.exports = SearchPage;
});