<form class="form-horizontal">
	<div class="form-group">

        <label class="col-md-2 control-label">网易分页偏移量</label>
		<div class="col-md-2">
			<input type="text" class="j_offset form-control">
		</div>

        <label class="col-md-1 control-label">类型</label>
        <div class="col-md-2">
			<select class="j_type form-control">
				<option value="ACG">ACG</option>
				<option value="日语">日语</option>
				<option value="电子">电子</option>
				<option value="轻音乐">轻音乐</option>
				<option value="器乐">器乐</option>
				<option value="吉他">吉他</option>
			</select>
		</div>

		<div class="col-md-2">
			<button class="j_add btn btn-default" type="button">摄入</button>
		</div>
	</div>
</form>

<table class="table table-striped table-condensed admin">
<thead>
<tr>
    <th style="width: 10rem">USER_ID</th>
    <th style="width: 30rem">用户名</th>
    <th style="width: 10rem">性别</th>
    <th style="width: 20rem">邮箱</th>
    <th style="width: 15rem">身份</th>
    <th style="width: 10rem">操作</th>
</tr>
</thead>
<tbody></tbody>
</table>
<nav id="pageNav" class="center"></nav>
<script id="trTemplate" type="text/template">
<tr>
    <td class="j_id"></td>
    <td class="j_name"></td>
    <td class="j_sex"></td>
    <td class="j_email"></td>
    <td class="j_role"></td>
    <td class="cz"></td>
</tr>
</script>