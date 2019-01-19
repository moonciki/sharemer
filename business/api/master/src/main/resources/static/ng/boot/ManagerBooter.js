define(function(require) {
    require.async(['global/ManagerVariable', 'global/Context'], function() {
        Backbone.history.start({
            pushState: false,
            root: location.pathname
        });
    });
});