define(function (require, exports, module) {

    seajs.use('../css/module/blog/home.css');
    seajs.use('../css/module/blog/calendar.css');

    var Template = require('module/blog/tpl/BlogView.tpl');
    var Blog = require('module/blog/model/Blog');
    var mydate = null;

    var BlogView = Backbone.View.extend({
        template: Template,
        current_mid: null,
        initialize: function () {
            this.model = new Blog();
            mydate = new Date();
            this.$el.append(this.template);
        },

        events: {
            'click .f-btn-jian': 'btnJian',
            'click .f-btn-jia': 'btnJia',
            'click .f-btn-fhby': 'backThisMonth'
        },

        request: function (id) {
            if (id == null || id == undefined || id == "") {
                window.location.href = "/#blog/404";
                return;
            }
            this.model.id = id;
            this.initCalendar(id);
        },

        initCalendar: function (id) {
            //页面加载初始化年月
            this.$el.find(".f-year").html(mydate.getFullYear());
            this.$el.find(".f-month").html(mydate.getMonth() + 1);
            this.showDate(mydate.getFullYear(), mydate.getMonth() + 1, mydate);
        },

        btnJian: function () {
            var mm = parseInt(this.$el.find(".f-month").html());
            var yy = parseInt(this.$el.find(".f-year").html());
            if (mm == 1) {//返回12月
                this.$el.find(".f-year").html(yy - 1);
                this.$el.find(".f-month").html(12);
                this.showDate(yy - 1, 12, mydate);
            } else {//上一月
                this.$el.find(".f-month").html(mm - 1);
                this.showDate(yy, mm - 1, mydate);
            }
        },

        btnJia: function () {
            var mm = parseInt(this.$el.find(".f-month").html());
            var yy = parseInt(this.$el.find(".f-year").html());
            if (mm == 12) {//返回12月
                this.$el.find(".f-year").html(yy + 1);
                this.$el.find(".f-month").html(1);
                this.showDate(yy + 1, 1, mydate);
            } else {//上一月
                this.$el.find(".f-month").html(mm + 1);
                this.showDate(yy, mm + 1, mydate);
            }
        },

        backThisMonth: function () {
            this.$el.find(".f-year").html(mydate.getFullYear());
            this.$el.find(".f-month").html(mydate.getMonth() + 1);
            this.showDate(mydate.getFullYear(), mydate.getMonth() + 1, mydate);
        },

        showDate: function (yyyy, mm, mydate) {
            //读取年月写入日历  重点算法!!!!!!!!!!!
            var dd = new Date(parseInt(yyyy), parseInt(mm), 0);   //Wed Mar 31 00:00:00 UTC+0800 2010
            var daysCount = dd.getDate();            //本月天数
            var mystr = "";//写入代码
            var monthstart = new Date(parseInt(yyyy) + "/" + parseInt(mm) + "/1").getDay()//本月1日周几
            var lastMonth; //上一月天数
            if (parseInt(mm) == 1) {
                lastMonth = new Date(parseInt(yyyy) - 1, 12, 0).getDate();
            } else {
                lastMonth = new Date(parseInt(yyyy), parseInt(mm) - 1, 0).getDate();
            }
            //计算上月空格数
            for (var j = monthstart; j > 0; j--) {
                mystr += "<div class='f-td f-null f-lastMonth' style='color:#ccc;'>" + (lastMonth - j + 1) + "</div>";
            }
            //本月单元格
            for (var i = 0; i < daysCount; i++) {
                //这里为一个单元格，添加内容在此
                mystr += "<div class='f-td f-number'><span class='f-day'>" + (i + 1) + "</span></div>";
            }
            //计算下月空格数
            for (var k = 0; k < 42 - (daysCount + monthstart); k++) {//表格保持等高6行42个单元格
                mystr += "<div class='f-td f-null f-nextMounth' style='color:#ccc;'>" + (k + 1) + "</div>";
            }
            //写入日历
            this.$el.find(".f-rili-table .f-tbody").html(mystr);
            //给今日加class
            if (mydate.getFullYear() == yyyy) {
                if ((mydate.getMonth() + 1) == mm) {
                    var today = mydate.getDate();
                    var lastNum = $(".f-lastMonth").length;
                    this.$el.find(".f-rili-table .f-td").eq(today + lastNum - 1).addClass("f-today");
                }
            }
            //绑定选择方法
            this.$el.find(".f-rili-table .f-number").off("click");
            this.$el.find(".f-rili-table .f-number").on("click", function () {
                $(".f-rili-table .f-number").removeClass("f-on");
                $(this).addClass("f-on");
            });
        }
    });
    module.exports = BlogView;
});