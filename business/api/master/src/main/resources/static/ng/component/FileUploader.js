define(function(require, exports, module) {

	var Template = require('component/tpl/FileUploader.tpl');

	require('lib/plugin/plupload/2.1.8/plupload-with-moxie');

	var FileUploader = Backbone.View.extend({
		template: Template,

		uploadUri: location.pathname + 'picture/upload',

		/**
		 * 构造函数
		 *
		 * @param   {Object}  options  {
		 *                        "el": 		{String}	id 表达式
		 *                        "imgWidth": 	{Number}	最终宽度，指定了会走校验
		 *                        "imgHeight": 	{Number}	最终高度，指定了会走校验
		 *                        "uploaded": 	{Function}	当上传成功后的调用 callback 函数
		 *                        "xiuxiu": 	{Object}	是否启用美图秀秀裁剪, 
		 *                    }
		 */
		initialize: function(options) {
			this.uploaded = options.uploaded || $.noop;

			this.$el.html(this.template.trim());

			this.imageWidth = options.imageWidth || null;
			this.imageHeight = options.imageHeight || null;

			this._initPlUploader();

			if (options.xiuxiu) {
				this._initXiuxiu(options.xiuxiu);
			}
		},

		addFile: function() {
			this.$el.find('.j_file-picker').trigger('click');
		},

		/**
		 * init plupload plugin core
		 *
		 * @return  {[type]}  [description]
		 */
		_initPlUploader: function() {
			var view = this;

			this.image = new Image();
			this.uploader = new plupload.Uploader({
				runtimes: 'html5',
				browse_button: view.$el.find('.j_file-picker').get(0),
				container: view.$el.find('.j_file-container').get(0),
				url: this.uploadUri,
				multi_selection: false,
				headers: {
					"Accept": "text/html;charset=utf-8,application/json;charset=utf-8;q=0.9,*/*;q=0.8"
				},
				filters: {
					max_file_size: '1mb',
					mime_types: [{
						title: "Image files",
						extensions: "jpg,gif,png"
					}]
				}
			});

			this.uploader.init();
			this._bindImageEvents();
			this._bindPlUploaderEvents();
		},

		_bindImageEvents: function() {
			var view = this,
				uploader = this.uploader;

			this.image.onload = function(event) {
				var tip = view.xiuxiuId ? '图片尺寸不符，可以使用裁剪修改\n\n' : '';

				if (view.imageWidth) {
					if (Math.abs(this.width - view.imageWidth) >= 10) {
						tip += '宽度实际值: ' + this.width + ', 期望值为: ' + view.imageWidth;
						bootbox.alert(tip);
						return;
					}
				}

				if (view.imageHeight) {
					if (Math.abs(this.height - view.imageHeight) >= 10) {
						tip += '高度实际值: ' + this.height + ', 期望值为: ' + view.imageHeight;
						bootbox.alert(tip);
						return;
					}
				}

				if (view.imageWidth && view.imageHeight) {
					if (Math.abs(this.width / this.height - view.imageWidth / view.imageHeight) >= 0.1) {
						tip += '长宽比并不正确';
						bootbox.alert(tip);
						return;
					}
				}

				uploader.start();
			};
		},

		_bindPlUploaderEvents: function() {
			var view = this;

			this.uploader.bind('FilesAdded', function(up, files) {
				while (up.files.length > 1) {
					up.removeFile(up.files[0]);
				}

				if (STATEMENT.imageTypes.indexOf(files[0].type) > -1) {
					view.image.src = URL.createObjectURL(files[0].getNative());
				} else {
					bootbox.alert('文件类型限制为 jpg gif png');
				}
			});

			this.uploader.bind('FileUploaded', function(up, files, result) {

				if (result.status == 200) {
					view.uploaded(JSON.parse(result.response));
				}
			});

			this.uploader.bind('Error', function(up, err) {
				var msg;

				switch (err.code) {
					case -600:
						{
							msg = '文件大小不能超过 1MB';
							break;
						}
					case -601:
						{
							msg = '文件类型限制为 jpg gif png';
							break;
						}
					default:
						{
							msg = 'code: ' + err.code + ', message: ' + err.message;
						}
				}
				bootbox.alert(msg);
			});
		},

		/**
		 * 初始化美图秀秀相关
		 *
		 * @return  {[type]}  [description]
		 */
		_initXiuxiu: function(options) {
			var view = this,
				xiuxiuId = options.id,
				cropSize = (view.imageWidth && (view.imageWidth + 'x' + view.imageHeight)) || options.cropSize;

			this.xiuxiuId = xiuxiuId;

			this.$el.children('.btn-group').append('<button type="button" class="j_xiuxiu-toggle btn btn-default"><span class="glyphicon glyphicon-scissors"></span></button>');

			this.$xiuxiuModal = this.$el.find('.j_xiuxiu-modal');

			var $xiuxiuContainer = $('<div id="' + xiuxiuId + '"></div>');

			this.$xiuxiuModal.find('.j_body').append($xiuxiuContainer);

			xiuxiu.setLaunchVars("customMenu", ["edit"], xiuxiuId);
			xiuxiu.setLaunchVars("titleVisible", 0, xiuxiuId);
			xiuxiu.setLaunchVars("nav", "edit", xiuxiuId);
			xiuxiu.setLaunchVars("cropPresets", cropSize, xiuxiuId);

			/**
			 * container	是	string	要被flash替换的容器的id
			 * editorType	否	int	要嵌入的编辑器类型（1为轻量编辑，2为轻量拼图，3为完整版，5为头像编辑器，默认值1）
			 * width		否	string	编辑器宽度（可以为数字或者百分比，对轻量编辑和完整版有效）
			 * height		否	string	编辑器高度（可以为数字或者百分比，对轻量编辑和完整版有效）
			 * id			否	string	编辑器在页面中的id，默认值为" xiuxiuEditor"（当需要在同一个页面内嵌入一个以上编辑器
			 * 					时需要以不同的id来区分，这时候就很有用，在接下去的几个接口中便可通过不同的id调用不
			 * 		   			同编辑器的接口(demo)）
			 */
			xiuxiu.embedSWF(xiuxiuId, 1, "100%", "500", xiuxiuId);
			xiuxiu.setUploadURL("http://www.sharemer.com/pc_api/img/upload");
			xiuxiu.setUploadType(2);
			xiuxiu.setUploadDataFieldName("file");

			xiuxiu.setLaunchVars('maxFinalWidth', view.imageWidth || 0, xiuxiuId);
			xiuxiu.setLaunchVars('maxFinalHeight', view.imageHeight || 0, xiuxiuId);

			this._bindXiuxiuEvents();
		},

		_bindXiuxiuEvents: function() {
			var view = this,
				$modal = this.$xiuxiuModal;

			this.$el.on('click.FileUploader', '.j_xiuxiu-toggle', function(event) {
				$modal.modal('show');

				xiuxiu.onUploadResponse = function(data) {
					view.$xiuxiuModal.modal('hide');

					view.uploaded(JSON.parse(data));
				};

				xiuxiu.onClose = function(id) {
					$modal.modal('hide');
				};
			});
		},

		remove: function() {
			this.uploader.unbindAll();
			this.uploader.destroy();

			this.image.onload = null;
			this.image = null;

			if (this.xiuxiuId) {
				this.$el.off('click.FileUploader', '.j_xiuxiu-toggle');
				xiuxiu.onUploadResponse = null;
				xiuxiu.onClose = null;
			}

			Backbone.View.prototype.remove.apply(this);
		}

	});

	module.exports = FileUploader;
});