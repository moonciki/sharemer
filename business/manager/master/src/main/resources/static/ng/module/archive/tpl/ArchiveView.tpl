<form class="form-horizontal">
	<div class="form-group">

        <label class="col-md-2 control-label">稿件索引</label>
		<div class="col-md-2">
			<input type="text" class="j_s_title form-control">
		</div>

        <label class="col-md-1 control-label">类型</label>
        <div class="col-md-2">
			<select class="j_s_type form-control">
			    <option value="0">按名称</option>
				<option value="1">按ID</option>
			</select>
		</div>
		<div class="col-md-2">
			<button class="j_search btn btn-default" type="button">检索</button>
		</div>
	</div>
</form>

<table class="table table-striped table-condensed admin">
<thead>
<tr>
    <th style="width: 10rem">ID</th>
    <th style="width: 20rem">标题</th>
    <th style="width: 10rem">封面</th>
    <th style="width: 15rem">类型</th>
    <th style="width: 10rem">上传人</th>
    <th style="width: 10rem">原作者</th>
    <th style="width: 10rem">稿件状态</th>
    <th style="width: 20rem">原标题</th>
    <th style="width: 20rem">标签</th>
    <th style="width: 10rem">操作</th>
</tr>
</thead>
<tbody></tbody>
</table>
<nav id="pageNav" class="center"></nav>
<script id="trTemplate" type="text/template">
<tr>
    <td class="j_id"></td>
    <td class="j_title"></td>
    <td class="j_cover"></td>
    <td class="j_publish_type"></td>
    <td class="j_user_id"></td>
    <td class="j_origin_author"></td>
    <td class="j_archive_status"></td>
    <td class="j_origin_title"></td>
    <td class="j_tags tag"></td>
    <input type="hidden" class="j_desc"/>
    <input type="hidden" class="j_status"/>
    <input type="hidden" class="j_file"/>
    <td>
        <button type="button" class="j_archive_play btn btn-default">播放</button>
        <button type="button" class="j_archive_info btn btn-default">详情</button>
    </td>
</tr>
</script>