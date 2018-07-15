define(function(require, exports, module) {

    //seajs.use('./css/module/userlist.css');

    var Template = require('module/user/tpl/UserListView.tpl');

    var Pagination = require('component/Pagination');

    var UserList = require('module/user/model/UserList');

    var UserListView = Backbone.View.extend({
        template: Template,

        $trTemplate: $($(Template).filter('#trTemplate').html().trim()),

        initialize: function() {
            this.model = new UserList();

            this.$el.append($(this.template).filter(':not(script)'));

            var view = this;

            this.pagination = new Pagination({
                el: '#pageNav',
                changed: function(newPageNo) {
                    view.request(newPageNo);
                }
            });

        },

        events: {
            'click .j_add': '_Add',
            'click .j_user_add' :'spider_user'
        },

        _Add: function () {

        },

        request: function(pageNo) {
            if(pageNo == undefined || pageNo == null){
                pageNo = 1;
            }
            this.model.params.pageNo = pageNo;
            this.$el.find('tbody').empty();
            var view = this;
            this.model.fetch().done(function(resp) {
                if (resp.code == 0) {
                    view.render(resp);
                }
            });
        },


        render: function() {
            this.$el.find('tbody').empty();
            var page = this.model.lastPage,
                userlists = page.records;

            var trs = [];
            for (var i = 0; i < userlists.length; i++) {
                trs.push(this._renderOne(userlists[i]));
            }

            this.$el.find('tbody').append(trs);

            this.pagination.render(page.pageNo, page.pageCount);
        },

        _renderOne: function(userlist) {
            var $tr = this.$trTemplate.clone();
            $tr.find('.j_id').text(userlist.id);
            $tr.find('.j_name').text(userlist.name);
            if(userlist.sex == 1){
                $tr.find('.j_sex').html("<span class=\"label label-primary\">♂ 男生</span>");
            }else if(userlist.sex == 0){
                $tr.find('.j_sex').html("<span class=\"label label-danger\">♀ 女生</span>");
            }else{
                $tr.find('.j_sex').html("<span class=\"label label-default\">迷之性别</span>");
            }
            $tr.find('.j_email').text(userlist.email);
            if(userlist.is_robot == 0){
                $tr.find('.j_role').html("<span class=\"label label-success\">普通用户</span>");
            }else if(userlist.is_robot == 1){
                $tr.find('.j_role').html("<span class=\"label label-warning\">机器人</span>");
            }

            $tr.find('.cz').html("封禁");
            return $tr;
        }
    });

    module.exports = UserListView;

})