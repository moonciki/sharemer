define(function(require, exports, module) {

    var BackboneUtil = require('util/BackboneUtil');

    var ContractRouter = Backbone.Router.extend({
        routes: {
            "": "permissionForward",

            "music_list/list(?*queryString)": "musicList",
            "music/list(?*queryString)": "music",
            "video/list(?*queryString)": "video",
            "video_add/add(?*queryString)": "add",
            "user/list(?*queryString)": "userList",

            "*path": "defaultRoute"
        },

        setApp: function(app) {
            this.app = app;
        },

        permissionForward: function (args) {
            ROUTER.navigate('music_list/list', {
                trigger: true
            });
        },

        musicList: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.renderMusicList(options);
        },

        userList: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.renderUserList(options);
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

        add: function (queryString) {
            if(queryString){
                var options = BackboneUtil.queryStringToObject(queryString);
            }
            this.app.addVideo(options);
        },

        defaultRoute: function(args) {
            alert('defaultRoute');
        }
    });

    module.exports = ContractRouter;
})
