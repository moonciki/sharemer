define(function(require) {
    require.async(['global/PlayerVariable', 'global/Context'], function() {
        Backbone.history.start({
            pushState: false,
            root: location.pathname
        });
    });
});