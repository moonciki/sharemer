define(function (require, exports, module) {

    seajs.use('./css/module/upload_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/upload/tpl/UploadView.tpl');

    var Upload = require('module/upload/model/Upload');

    var FileUploader = require('component/FileUploader');

    require('https://unpkg.com/qiniu-js@2.4.0/dist/qiniu.min.js');

    require('lib/plugin/bootstrap-tagsinput/0.4.2/bootstrap-tagsinput-delta');

    var UploadView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        $mtrTemplate: $($(Template).html().trim()),
        initialize: function () {
            this.model = new Upload();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.tg').removeClass("menu_unit").addClass("menu_unit_point");

            this.uploader = new FileUploader({
                el: '.j_upload-cover',
                imageWidth: 680,
                imageHeight: 500,
                uploaded: function (resp) {
                    var pic = resp;
                    view.$el.find('.j_picture-cover').attr('src', pic.url);
                },
                xiuxiu: {
                    id: 'xiuxiuepcover'
                }
            });

            $('.j_tags').tagsinput({
                tagClass: 'label label-default',
                containerWidth: '100%'
            });
        },

        events: {
            'change .file-btn': 'upload',
            'click .yuanchuang': 'yuanchuang',
            'click .banyun': 'banyun',
            'click .j_add': 'add'
        },

        add: function () {
            var title = this.$el.find('.j_title').val();
            if(title === null || title.trim() === ''){
                alert("标题不能为空！");
                return;
            }
            var cover = this.$el.find(".j_picture-cover").attr('src');
            if(cover === null || cover === ''){
                alert("封面不能为空！");
                return;
            }
            var publish_type = this.$el.find('input[name="publish-type"]:checked').val();
            if(publish_type === null || publish_type === undefined){
                alert("请选择上传性质！");
                return;
            }
            var file = this.$el.find('.j_file_url').val();
            if(file === null || file === ''){
                alert("文件还未上传完成，请耐心等待~");
                return;
            }

            var origin_title = this.$el.find('.j_origin_title ').val();
            var origin_author = this.$el.find('.j_origin_author').val();

            if(publish_type != 0){
                if(origin_title === null || origin_title.trim() === ''
                || origin_author === null || origin_author.trim() === ''){
                    alert("当上传性质为非原创时，原作者和原作品名不能为空！");
                    return;
                }
            }

            var style_tag = [];
            this.$el.find('[name=style-type]').each(function (index, ele) {
                if (ele.checked) {
                    style_tag.add(ele.value);
                }
            });
            if(style_tag.length === 0){
                alert("为了使您的作品更容易被发现，请至少选择一个分类！");
                return;
            }
            var tags = this.$el.find('.j_tags').val();
            var staff = this.$el.find('.j_staff').val();
            var csrf = window.SHION.w_token;

            var param = {};
            param.title = title;
            param.cover = cover;
            param.publish_type = publish_type;
            param.file = file;
            param.origin_title = origin_title;
            param.origin_author = origin_author;
            param.style_tag = style_tag;
            param.tags = tags;
            param.staff = staff;
            param.csrf = csrf;

            var view = this.$el;
            this.model.save_archive(param).done(function (resp) {
                if (resp.code === 0) {
                    view.find('#info_tag').hide();
                    view.find('#success_tag').show();
                }
            });
        },

        upload: function (event) {

            var view = this;
            var file = this.$el.find(".file-btn").get(0).files[0];

            var fileName = file.name;

            var suffix = this.getFileType(fileName);
            if (suffix === undefined || suffix === "" || suffix === null) {
                alert("不支持的文件格式！");
                event.change(false);
                return;
            }

            if (suffix !== ".mp3" && suffix !== ".wma" && suffix !== ".MP3" && suffix !== ".WMA") {
                alert("不支持的文件格式，请刷新页面后上传正确的mp3或wma文件！");
                this.$el.find('.file-btn').unbind();
                return;
            }

            var config = {
                useCdnDomain: true,
                region: null
            };

            var putExtra = {
                fname: fileName,
                params: {},
                mimeType: null
            };

            var token = "";
            var key = "";
            this.model.get_up_token().done(function (resp) {
                if (resp.code === 0) {
                    token = resp.result.token;
                    key = resp.result.key;
                }
            });

            var observable = qiniu.upload(file, key + suffix, token, putExtra, config);

            var observer = {
                next(res) {
                    var percent = res.total.percent;
                    view.$el.find(".upload_loading_text").html("上传完成 <span style='color: #ff617e; font-weight: bold'>" + Math.round(percent) + "%</span>");
                    view.$el.find(".loading-body").attr("style", "width: " + percent + "%");
                },
                error(err) {
                    alert("上传出错！请刷新页面重试~");
                },
                complete(res) {
                    view.$el.find("#upload_loading").hide();
                    view.$el.find(".progress-striped").removeClass("active")
                    view.$el.find(".j_file_url").val(res.key);
                }
            };
            observable.subscribe(observer);// 上传开始
            this.$el.find('#upload_tag').hide();
            this.$el.find('#info_tag').show();
        },

        yuanchuang: function () {
            this.$el.find('.j_origin_title').val("").attr("disabled", true);
            this.$el.find('.j_origin_author').val("").attr("disabled", true);
        },

        banyun: function () {
            this.$el.find('.j_origin_title').attr("disabled", false);
            this.$el.find('.j_origin_author').attr("disabled", false);
        },

        request: function () {

        },

        remove: function () {
            $('.j_tags').tagsinput('destroy');
        },

        getFileType: function (file) {
            var filename = file;
            var index1 = filename.lastIndexOf(".");
            var index2 = filename.length;
            return filename.substring(index1, index2);
        }
    });

    module.exports = UploadView;

});