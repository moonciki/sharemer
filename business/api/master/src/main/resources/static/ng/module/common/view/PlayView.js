define(function(require, exports, module) {

    var playTemplate = require('module/common/tpl/PlayModal.tpl');

    var PlayView = Backbone.View.extend({
        p_template: playTemplate,
        initialize: function(options) {
            this.main_el = options.el;
            this.main_el.append(this.p_template);
        },

        events: {
            'click #all_common_play': 'close'
        },

        commonPlay: function (event) {
            var $div = $(event.target);
            var type = $div.attr("data-type");
            var url = $div.attr("data-url");
            var title = $div.attr("data-title");
            this.main_el.find('#all_common_play').modal('show');
            this.main_el.find('.common-modal-title').text(title);
            if(type == 0){
                this.main_el.find('.common-modal-body').html("<iframe style=\"width:840px;height:86px;\" src=\""+url+"\" frameborder=\"0\"></iframe>")
            }
            if(type == 1){
                this.main_el.find('.common-modal-body').html("<iframe style=\"width:840px;height:440px;\" src=\""+url+"\" frameborder=\"0\"></iframe>")
            }
        },
        close: function () {
            var status = this.main_el.find('#all_common_play').attr("aria-hidden");
            if(status != null && (status == true || status == 'true')){
                this.main_el.find('.common-modal-body').html("");
            }
        }
    });

    module.exports = PlayView;

});