define(function(require) {
    require.async(['global/BlogVariable', 'global/Context'], function() {
        Backbone.history.start({
            pushState: false,
            root: location.pathname
        });
    });
});