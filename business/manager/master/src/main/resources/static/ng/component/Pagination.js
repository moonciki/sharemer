/**
 * 分页组件，不持有任何 Model 只负责渲染 Page 对象；每次渲染时重新构造分页组件 (nav) 的 DOM；
 *
 * @requires backbone.js
 * 
 * usage: 
 *   initlize phase : this.pagination = new Pagination({ el : '#pageNav' });
 *   render phase   : this.pagination.render(page);
 * 
 */
define(function(require, exports, module) {

	var Template = require('component/tpl/Pagination.tpl');

	var BackboneUtil = require('util/BackboneUtil');
	var StringUtil = require('util/StringUtil');

	/**
	 * 分页组件
	 * usage
	 *
	 * @param   {[type]}  options)     [description]
	 * @param   {[type]}  events:      [description]
	 * @param   {[type]}  _specClick:  [description]
	 *
	 * @return  {[type]}               [description]
	 */
	var Pagination = Backbone.View.extend({
		template: Template,
		groupSize: 6,

		initialize: function(options) {

			this.$el.html(this.template);

			this.changed = options.changed; // callback function 
		},

		events: {
			'click a.pointer': '_specClick'
		},

		_specClick: function(event) {
			var a = event.currentTarget,
				$a = $(a);
			var pageNo = Number($a.attr('data-page-no'));
			if(!pageNo || !StringUtil.isValidId(pageNo)){
				return;
			}
			this.changed(pageNo);
		},

		render: function(pageNo, pageCount, totalCount ) {
			var $el = this.$el;

			var group = this.currentPageGroup(pageNo, pageCount);

			var lis = [], liTpl, liStr;
			for (var index = group[0]; index <= group[1]; index++) {
				if (index !== pageNo) {
					liTpl = '<li><a class="pointer" data-page-no="{{pageNo}}">{{pageNo}}</a></li>';
				} else {
					liTpl = '<li class="active"><a>{{pageNo}}</a></li>';
				}

				liStr = liTpl/*.replace(/{{hash}}/g, this._hashString(index))*/.replace(/{{pageNo}}/g, index);

				lis.push(liStr);
			}
			
			if(pageCount-4>pageNo) {
				liStr = '<li><a class="pointer" data-page-no="{{pageNo}}">{{pageNo}}</a></li>'/*.replace(/{{hash}}/g, this._hashString(index))*/.replace(/{{pageNo}}/g, "...");
				lis.push(liStr);
				liStr = '<li><a class="pointer" data-page-no="{{pageNo}}">{{pageNo}}</a></li>'/*.replace(/{{hash}}/g, this._hashString(index))*/.replace(/{{pageNo}}/g, pageCount);
				lis.push(liStr);
			}else if(pageCount-3>pageNo){
				liStr = '<li><a class="pointer" data-page-no="{{pageNo}}">{{pageNo}}</a></li>'/*.replace(/{{hash}}/g, this._hashString(index))*/.replace(/{{pageNo}}/g, pageCount);
				lis.push(liStr);
			}
			

			$el.find('li').not(':first').not(':last').remove();
			$(lis.join('')).insertAfter(this.$el.find('li:first'));

			this.renderPrevNext(pageNo, pageCount);
		},

		currentPageGroup: function(pageNo, pageCount) {
			var groupSize = this.groupSize;

			// pageGroup 为一个长度为2的数组，分别表示这一组的 begin pageNo 和 end pageNo
			var firstHalfGroup = [1, Math.min((groupSize / 2).floor(), pageCount)],
				firstGroup = [1, Math.min(groupSize, pageCount)],
				lastHalfGroup = [(pageCount < groupSize) ? 1 : (pageCount - groupSize) + (groupSize / 2).floor(), pageCount],
				lastGroup = [(pageCount < groupSize) ? 1 : (pageCount - groupSize) + 1, pageCount];

			if (_between(pageNo, firstHalfGroup)) {
				return firstGroup;
			} else if (_between(pageNo, lastHalfGroup)) {
				return lastGroup;
			} else {
				var first = (pageNo - Math.floor(groupSize / 2) <= 0) ? 1 : (pageNo - Math.floor(groupSize / 2)),
					last = Math.min(pageCount, first + groupSize - 1);
				return [first, last];
			}

			function _between(pageNo, pageGroup) { // 判断该 pageNo 是否在这一组中
				return pageNo >= pageGroup[0] && pageNo <= pageGroup[1];
			}
		},

		renderPrevNext: function(pageNo, pageCount) {
			var $prev = this.$el.find('.j_prev'),
				$next = this.$el.find('.j_next');

			$prev.attr('data-page-no', Math.max(1, pageNo - 1))	;
			$prev.toggleClass('pointer', !Boolean(pageNo == 1));	

			$next.attr('data-page-no', Math.min(pageNo + 1, pageCount));
			$next.toggleClass('pointer', !Boolean(pageNo == pageCount || pageCount === 0));
		}
	});

	module.exports = Pagination;
});