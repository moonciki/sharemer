define(function(require, exports, module) {

    seajs.use('./css/module/upload_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/upload/tpl/UploadView.tpl');

    var Upload = require('module/upload/model/Upload');

    require('https://unpkg.com/qiniu-js@2.4.0/dist/qiniu.min.js');

    require('lib/plugin/bootstrap-tagsinput/0.4.2/bootstrap-tagsinput-delta');

    var UploadView = Backbone.View.extend({
        m_template: menuTemplate,
        template: Template,
        $mtrTemplate: $($(Template).html().trim()),
        initialize: function() {
            this.model = new Upload();
            this.$el.append(this.m_template);
            this.$el.append(this.template);
            var view = this;
            //替换样式
            view.$el.find('.menu_unit_point').removeClass("menu_unit_point").addClass("menu_unit");
            view.$el.find('.tg').removeClass("menu_unit").addClass("menu_unit_point");

            $('.j_tags').tagsinput({
                tagClass: 'label label-default',
                containerWidth: '100%'
        });
        },

        events: {
            'change .file-btn': 'upload'
        },

        upload: function(event){

            var view = this;
            var file = this.$el.find(".file-btn").get(0).files[0];

            var fileName = file.name;

            var suffix = this.getFileType(fileName);
            if(suffix === undefined || suffix === "" || suffix === null){
                alert("不支持的文件格式！");
                event.change(false);
                return;
            }

            if(suffix !== ".mp3" && suffix !== ".wma" && suffix !== ".MP3" && suffix !== ".WMA"){
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
            this.model.get_up_token().done(function(resp) {
                if (resp.code === 0) {
                    token = resp.result.token;
                    key = resp.result.key;
                }
            });

            var observable = qiniu.upload(file, key+suffix, token, putExtra, config);

            var observer = {
                next(res){
                    var percent = res.total.percent;
                    view.$el.find('.upload_info_body_loading').html("<div class=\"upload_info_body_loading_body\" style='width: "+
                        (782 * percent/100)
                        +"px'></div>");
                },
                error(err){
                    alert("上传出错！请刷新页面重试~");
                },
                complete(res){
                    view.$el.find("#upload_loading").hide();
                    view.$el.find(".upload_loading_text").text("上传完成！");
                    view.$el.find(".j_file_url").val(res.key);
                }
            };
            observable.subscribe(observer);// 上传开始
            this.$el.find('#upload_tag').hide();
            this.$el.find('#info_tag').show();
        },

        request: function() {

        },

        remove: function () {
            $('.j_tags').tagsinput('destroy');
        },

        getFileType: function (file){
            var filename=file;
            var index1=filename.lastIndexOf(".");
            var index2=filename.length;
            return filename.substring(index1,index2);
        }
    });

    module.exports = UploadView;

});