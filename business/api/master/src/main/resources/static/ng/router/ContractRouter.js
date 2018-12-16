define(function(require, exports, module) {

    var BackboneUtil = require('util/BackboneUtil');

    var ContractRouter = Backbone.Router.extend({
        routes: {
            "": "permissionForward",
            "home":"home",

            "music":"music",
            "music/tag/(:id)":"musicTag",
            "music/info/(:id)":"musicInfo",

            "video":"video",
            "video/tag/(:id)":"videoTag",
            "video/info/(:id)":"videoInfo",

            "archive/tag/(:id)":"archiveTag",
            "archive/info/(:id)":"archiveInfo",

            "user/info/(:id)":"userInfo",

            "favlist/tag/(:id)":"favTag",
            "favlist/info/(:id)":"favInfo",

            "upload":"upload",
            "search/(:key)(?*queryString)":"search",

            "*path": "home"
        },

        setApp: function(app) {
            this.app = app;
        },

        permissionForward: function (args) {
            ROUTER.navigate('home', {
                trigger: true
            });
        },

        home: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.renderHome(options);
        },

        music: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.renderMusic(options);
        },

        video: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.renderVideo(options);
        },

        musicTag: function (id) {
            this.app.renderMusicTag(id);
        },

        favTag: function (id) {
            this.app.renderFavTag(id);
        },

        videoTag: function (id) {
            this.app.renderVideoTag(id);
        },

        archiveTag: function (id) {
            this.app.renderArchiveTag(id);
        },

        musicInfo: function (id) {
            this.app.renderMusicInfo(id);
        },

        favInfo: function (id) {
            this.app.renderFavInfo(id);
        },

        search: function (key, queryString) {
            if (queryString) {
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.search(key, options);
        },

        upload: function (){
            this.app.upload();
        },

        userInfo: function (id) {
            this.app.renderUserInfo(id);
        },

        videoInfo:function (id) {
            this.app.renderVideoInfo(id);
        },

        archiveInfo:function (id) {
            this.app.renderArchiveInfo(id);
        },

        defaultRoute: function(args) {
            alert('路由路径不存在！');
        }
    });

    module.exports = ContractRouter;
})
