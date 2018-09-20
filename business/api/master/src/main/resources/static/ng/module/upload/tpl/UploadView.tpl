<div class="upload_main_page">
    <div class="upload_main" id="upload_tag">
        <div class="upload_body">
            <form method="post" enctype="multipart/form-data" id="form" action="upload">
                <div class="upload_btn">
                    <input type="file" class="file-btn"/>
                    <div class="upload_btn_01">
                        <span class="glyphicon glyphicon-upload"></span>
                    </div>
                    <div class="upload_btn_02">
                        <span style="font-size: 18px">上传音乐</span><br/>
                        <span style="font-size: 14px">请上传MP3文件</span>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="upload_main" id="info_tag" style="display: none">
        <div class="upload_info_body">
            <div class="upload_info_body_top" style="text-align: center">
                <div class="upload_info_body_top_tip"></div>
                <span style="font-size: 18px; line-height: 3.5">稿件信息单</span>
            </div>
            <div class="upload_info_body_main">
                <div class="upload_info_body_top" style="margin-bottom: 20px">
                    <img id="upload_loading" src="/image/loading.gif" height="18px">&nbsp;&nbsp;&nbsp;&nbsp;<span class="upload_loading_text" style="font-size: 18px">上传中...</span>
                    <div class="upload_info_body_loading">

                    </div>
                </div>

                <!-- 表单-->
                <form class="form-horizontal j_basic-form">

                    <input type="hidden" class="j_file_url"/>
                    <div class="col-md-11 extra-parent">

                        <div class="form-group col-md-12">
                            <label class="col-sm-3 control-label">标题</label>
                            <div class="col-sm-9 extra-auto">
                                <input type="text" class="j_title form-control" maxlength="255">
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-sm-3 control-label extra-title">标签</label>
                            <div class="col-sm-9 extra-auto">
                                <input type="text" class="j_tags form-control" maxlength="255"
                                       placeholder="输入标签后回车...">
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <label class="col-sm-3 control-label extra-title">简介</label>
                            <div class="col-sm-9 extra-auto">
                            <textarea class="j_staff form-control" placeholder="请为你的稿件做个简短的介绍吧~" rows="6"></textarea>
                            </div>
                        </div>

                        <div id="add_btn">
                            <div class="form-group log-button">
                                <div class="col-sm-offset-3 col-sm-11">
                                    <button type="button" class="j_add btn btn-primary"><span class="glyphicon glyphicon-send"></span>&nbsp;&nbsp;提交稿件</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
