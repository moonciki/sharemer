define(function(require, exports, module) {

    seajs.use('./css/module/upload_info.css');

    var menuTemplate = require('module/common/tpl/menu.tpl');

    var Template = require('module/upload/tpl/UploadView.tpl');

    var Upload = require('module/upload/model/Upload');

    var qiniu = require('https://unpkg.com/qiniu-js@2.4.0/dist/qiniu.min.js');

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

        upload: function(){
            var file = this.$el.find(".file-btn").get(0).files[0];

            var config = {
                useCdnDomain: true,
                region: "qiniu.region.z2"
            };

            var putExtra = {
                fname: "",
                params: {},
                mimeType: [] || null
            };
            
            var token = "";
            var observable = qiniu.upload(file, key, token, putExtra, config);

            var observer = {
                next(res){
                    //接收上传进度信息，res 参数是一个带有 total 字段的 object，包含loaded、total、percent三个属性，提供上传进度信息
                    //total.percent: number，当前上传进度，范围：0～100
                },
                error(err){
                    //参数 err 为一个包含 code、message、isRequestError 三个属性的 object
                    //err.isRequestError: 用于区分是否 xhr 请求错误；当 xhr 请求出现错误并且后端通过 HTTP 状态码返回了错误信息时，该参数为 true；否则为 undefined
                    //err.message: string，错误信息，包含错误码，当后端返回提示信息时也会有相应的错误信息
                },
                complete(res){
                    //接收上传完成后的后端返回信息，res 参数为一个 object， 为上传成功后后端返回的信息，具体返回结构取决于后端sdk的配置，可参考上传策略
                }
            };
            var subscription = observable.subscribe(observer);// 上传开始

            this.$el.find('#upload_tag').hide();
            this.$el.find('#info_tag').show();
        },

        request: function() {

        },

        remove: function () {
            $('.j_tags').tagsinput('destroy');
        }
    });

    module.exports = UploadView;

});