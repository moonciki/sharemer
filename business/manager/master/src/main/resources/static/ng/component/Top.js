define(function(require, exports, module) {

	var Template = require('component/tpl/Top.tpl');

	var CookieUtil = require('util/CookieUtil');

	var Top = Backbone.View.extend({
		el: '#top',
		template: Template,

		initialize: function(options) {

			this.$el.html(this.template);
			this.$el.find('[data-toggle="popover"]').popover();

			this.render();
		},

		events: {
			'click .j_sign-out': '_signOutClick',
			'keydown .j_search': '_searchKeyDown'
		},

		render: function() {
			var user = window.STATEMENT.currentUser || {
				id: '1',
				username: 'ADMIN',
				lastLogin: '公元前'
			};
			// "obj" is underscore.js template's context i.e. param passed to compiled function

			this.$el.find('.j_username').html(user.username);

			this._renderSearchInput();
		},

		_renderSearchInput: function() {
			var $input = this.$el.find('.j_search');

			// var permissions = [];
			//
			// if (!STATEMENT.currentUser) {
			// 	return;
			// }
			//
			// if (STATEMENT.currentUser.groups) {
			// 	_.each(STATEMENT.currentUser.groups,function(g){
			// 		permissions = _.compact(_.union(permissions, g.permIds.split('-')));
			// 	})
			// }
			//
			// if (permissions.indexOf('1') > -1) {
			// 	$input.attr({
			// 		"placeholder": "番剧搜索 ('ID', '标题')",
			// 		"data-navi-hash": "statement/list"
			// 	});
			// 	return;
			// }
			//
			// if (permissions.indexOf('101') > -1) { // 番剧编辑
			// 	$input.attr({
			// 		"placeholder": "番剧搜索 ('ID', '标题')",
			// 		"data-navi-hash": "statement/list"
			// 	});
			// 	return;
			// }
			//
			// if (permissions.indexOf('201') > -1) { // 电影编辑
			// 	$input.attr({
			// 		"placeholder": "电影 ('ID', '标题')",
			// 		"data-navi-hash": "movie/page"
			// 	});
			// 	return;
			// }
			//
			// if (permissions.indexOf('203') > -1) { // 电影活动编辑
			// 	$input.attr({
			// 		"placeholder": "活动 ('ID', '标题')",
			// 		"data-navi-hash": "activity/page"
			// 	});
			// 	return;
			// }
			
			// 其他情况下搜索 input 不可见
			this.$el.off('keydown', '.j_search');
			$input.remove();
		},

		_searchKeyDown: function(event) {
			var input = event.currentTarget,
				keyword = input.value.trim();

			if (event.which === 13) {
				event.preventDefault();

				var naviHash = input.getAttribute('data-navi-hash');

				if (keyword) {
					ROUTER.navigate(naviHash + '?keyword=' + keyword, {
						trigger: true
					});
				} else {
					ROUTER.navigate(naviHash, {
						trigger: true
					});
				}

				return false;
			}
		},

		_signOutClick: function() {
			if (confirm('是否退出ShareMer后台？')) {
                $.ajax({
                    url: "logout",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        switch (data.code){
                            case 0 :
                                window.location.reload();
                                break;
                            default :
                                alert("迷之错误！");
                                break;
                        }
                    }
                });
			}
		},

		remove: function() {
			this.$el.find('[data-toggle="popover"]').popover('destroy');

			Backbone.View.prototype.remove.apply(this);
		}
	});

	module.exports = Top;
});
