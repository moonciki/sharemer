<form class="form-horizontal">
	<div class="form-group">

        <label class="col-md-2 control-label">视频标题</label>
		<div class="col-md-2">
			<input type="text" class="j_s_title form-control">
		</div>

        <label class="col-md-1 control-label">类型</label>
        <div class="col-md-2">
			<select class="j_s_type form-control">
			    <option value="0">按名称</option>
				<option value="1">按V_ID</option>
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
    <th style="width: 15rem">视频源</th>
    <th style="width: 20rem">VID</th>
    <th style="width: 5rem">PAGE</th>
    <th style="width: 20rem">标签</th>
    <th style="width: 10rem">机器人</th>
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
    <td class="j_source"></td>
    <td class="j_vid"></td>
    <td class="j_page"></td>
    <td class="j_tags tag"></td>
    <td class="j_robot"></td>
    <input type="hidden" class="j_mv_playurl"/>
    <input type="hidden" class="j_embed_type"/>
    <td>
    <button type="button" class="j_video_play btn btn-default">播放</button>
    </td>
</tr>
</script>