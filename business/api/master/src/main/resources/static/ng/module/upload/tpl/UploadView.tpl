<div class="upload_main_page">
    <div class="upload_main" id="upload_tag" style="display: none">
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
    <div class="upload_main" id="info_tag" >
        <div class="upload_info_body">
            <div class="upload_info_body_top" style="text-align: center">
                <span style="font-size: 18px; line-height: 3.5">稿件信息单</span>
            </div>
            <div class="upload_info_body_main">
                <div class="upload_info_body_top" style="margin-bottom: 20px">
                    <img id="upload_loading" src="/image/loading.gif" height="18px">&nbsp;&nbsp;&nbsp;&nbsp;<span class="upload_loading_text" style="font-size: 18px"></span>
                    <div class="progress progress-striped active" style="width: 790px">
                        <div class="loading-body progress-bar progress-bar-info" role="progressbar"
                             aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
                        </div>
                    </div>
                </div>

                <!-- 表单-->
                <form class="form-horizontal j_basic-form">

                    <input type="hidden" class="j_file_url"/>
                    <div class="col-md-15 extra-parent">

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">封面</label>
                            <div class="col-sm-11 extra-auto">
                                <div class="thumbnail" style="padding-top:20px">
                                    <img class="j_picture-cover" src="" width="550px"/>
                                    <div class="caption pink">
                                        <h5>680 x 500</h5>
                                        <div class="j_upload-cover"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">标题</label>
                            <div class="col-sm-11 extra-auto">
                                <input type="text" class="j_title form-control" placeholder="请输入作品标题" maxlength="255">
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">性质</label>
                            <div class="col-sm-11">
                                <label class="radio-inline"><input type="radio" class="yuanchuang" name="publish-type" value="0">原创</label>
                                <label class="radio-inline"><input type="radio" class="banyun" name="publish-type" value="1">搬运</label>
                                <label class="radio-inline"><input type="radio" class="banyun" name="publish-type" value="2">翻唱/鬼畜/改编</label>
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">原作</label>
                            <div class="col-sm-11 extra-auto">
                                <input type="text" class="j_origin_title form-control" placeholder="请输入原作品名" maxlength="255">
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">作者</label>
                            <div class="col-sm-11 extra-auto">
                                <input type="text" class="j_author form-control" placeholder="请输入原作者名" maxlength="255">
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label">分类</label>
                            <div class="col-sm-11">
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="钢琴">钢琴</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="电子">电子</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="轻音乐">轻音乐</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="影视原声">影视原声</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="ACG">ACG</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="游戏">游戏</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="治愈">治愈</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="日语">日语</label>
                                <label class="checkbox-inline"><input type="checkbox" name="style-type" value="欧美">欧美</label>
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label extra-title">标签</label>
                            <div class="col-sm-11 extra-auto">
                                <input type="text" class="j_tags form-control" maxlength="255"
                                       placeholder="输入标签后回车...">
                            </div>
                        </div>

                        <div class="form-group col-md-15">
                            <label class="col-sm-1 control-label extra-title">简介</label>
                            <div class="col-sm-11 extra-auto">
                            <textarea class="j_staff form-control" placeholder="请为你的稿件做个简短的介绍吧~" rows="6"></textarea>
                            </div>
                        </div>

                        <div id="add_btn">
                            <div class="form-group log-button">
                                <div class="col-sm-offset-1 col-sm-11">
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
