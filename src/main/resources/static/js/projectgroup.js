var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();

	initTable();
	initGroupModal();
	initProjectModal();
	initProjectGroupModal();
	
    // 加载所有项目
	queryProjectGroup();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryProjectGroup();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	clearProjectGroupEdit();
    	$('#project_group_edit').show();
    });
    
    // 修改按钮
    $('#btn_edit').click(function(){
    	clearProjectGroupEdit();
    	var project_groups = $('#table_project_group').bootstrapTable('getSelections');
    	if( project_groups.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( project_groups.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	
    	$('#project_group_edit_id').val(project_groups[0].id);
    	$('#project_group_edit_project').attr('no',project_groups[0].project_code);
    	$('#project_group_edit_project').val(project_groups[0].project_name);
    	$('#project_group_edit_group').attr('no',project_groups[0].group_id);
    	$('#project_group_edit_group').val(project_groups[0].group_name);
    	$('#project_group_edit_status').val(project_groups[0].status);
    	
    	$('#project_group_edit').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	
    	var project_groups = $('#table_project_group').bootstrapTable('getSelections');
    	if( project_groups.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( project_groups.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = project_groups[0].id;
        	var oper_type = "103";
//        	$.message.info(project_groups[0].id);
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operProjectGroup(requestData,oper_type);
        	//$.message.info(id);
        	$('#table_project_group').bootstrapTable('removeByUniqueId',id);
    	});
    });
    
});
/**
 * 项目属组清空数据
 */
function clearProjectGroupEdit(){
	$('#project_group_edit_id').val('');
	$('#project_group_edit_project').attr('no','');
	$('#project_group_edit_project').val('');
	$('#project_group_edit_group').attr('no','');
	$('#project_group_edit_group').val('');
}
/**
 * 项目属组维护
 */
function operProjectGroup(requestData,oper_type){
	var request_url = ctx+"/rest/projectgroup/oper";
	  $.ajax({
			type: "post",
			url: request_url,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data: requestData,
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				$.loading.show("正在加载");
			},
			success: function(data) {
				if ("200" == data.respCode) {
					if("103" != oper_type){
						queryProjectGroup();
					}
					$.message.info(data.respMsg);
				} else {
					//$('#table_project_group').bootstrapTable('removeAll');
					$.message.error(data.respMsg);
				}
			},
			error: function() {
				$.message.error('项目维护Ajax请求失败!');
			},
			complete: function(){
				$.loading.hide();
			}
		});
}
// 重置查询
function resetQuery(){
	$('#search_project_group_project').attr('no','');
	$('#search_project_group_project').val('');
	$('#search_project_group_group').attr('no','');
	$('#search_project_group_group').val('');
	$('#search_project_group_status').val('2');
}
/**
 * 查询项目属组
 */
function queryProjectGroup(){
	var project_code = $('#search_project_group_project').attr('no');
	var group_id = $('#search_project_group_group').attr('no');
	var status = $('#search_project_group_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	var request_data = {
			"project_code":project_code,
			"group_id":group_id,
			"status":status
		};
	var request_url = ctx+"/rest/projectgroup/query";

	$.ajax({
		type: "post",
		url: request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("200" == data.respCode) {
				var project_group_data = data.respMap.project_group_list;
				$('#table_project_group').bootstrapTable('load', project_group_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_project_group').bootstrapTable('removeAll');
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载项目Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

/**
 * 查询参数组
 */
function queryPropGroup(){
	var request_data = {
			"id":"",
			"group_name":$('#groupModalName').val(),
			"code_id":"",
			"status":""
		};
	var request_url = ctx+"/rest/propertiesgroup/query";
	$.ajax({
		type: "post",
		url: request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("200" == data.respCode) {
				var properties_group_list = data.respMap.properties_group_list;
				$(properties_group_list).each(function(index,item){
					if("1" == item.status){
						$("#groupModalList").append("<button class='btn btn-default btn-sm' no='"+item.id+"'>"+item.group_name+"</button>");
					}
				});
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载参数组Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
/**
 * 查询所有已配置参数组的项目
 */
function querHasProject(){
	var request_data = {
			"project_code":"",
			"project_name":$('#projectModalName').val(),
			"status":""
		};
	var request_url = ctx+"/rest/projectgroup/has";
	$.ajax({
		type: "post",
		url: request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("200" == data.respCode) {
				var project_data = data.respMap.project_has_list;
				$(project_data).each(function(index,item){
					$("#projectModalList").append("<button class='btn btn-default btn-sm' no='"+item.project_code+"'>"+item.project_name+"</button>");
				});
				//$.message.info(data.respMsg);
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载项目Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
/**
 * 查询所有有效项目
 */
function querProject(){
	var request_data = {
			"project_code":"",
			"project_name":$('#projectModalName').val(),
			"status":""
		};
	var request_url = ctx+"/project/query";
	$.ajax({
		type: "post",
		url: request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: request_data,
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("200" == data.respCode) {
				var project_data = data.respMap.project_list;
				$(project_data).each(function(index,item){
					if("1" == item.status){
						$("#projectModalList").append("<button class='btn btn-default btn-sm' no='"+item.project_code+"'>"+item.project_name+"</button>");
					}
				});
				//$.message.info(data.respMsg);
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载项目Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
/**
 * 项目属组-模态框初始化
 */
function initProjectGroupModal(){
	$.modal('#project_group_edit', {
        width: 460,
        height: 330
    });
    // 项目属组-保存按钮
    $('#project_group_edit_save_btn').click(function(){
    	var id = $('#project_group_edit_id').val();
    	var project_code = $('#project_group_edit_project').attr('no');
    	var project_name = $('#project_group_edit_project').val();
    	var group_id = $('#project_group_edit_group').attr('no');
    	var group_name = $('#project_group_edit_group').val();
    	var status = $('#project_group_edit_status').val();
    	if(!project_code){
    		$.message.error('请选择项目');
    		return;
    	}
    	if(!group_id){
    		$.message.error('请选择参数属组');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择项目状态');
    		return;
    	}
    	var requestData;
    	if(!id){
    		requestData = {
    			"project_code": project_code,
    			"project_name": project_name,
    			"group_id": group_id,
    			"group_name": group_name,
    			"status": status,
    			"oper_type": "101"};
    		operProjectGroup(requestData,"101");
    	}else{
    		requestData = {
    			"id":id,
    			"project_code": project_code,
    			"project_name": project_name,
    			"group_id": group_id,
    			"group_name": group_name,
    			"status": status,
    			"oper_type": "102"};
    		operProjectGroup(requestData,"102");
    	}
    	$('#project_group_edit').hide();
    });
    
    // 项目属组-取消按钮
    $('#project_group_edit_cancel_btn').click(function(){
    	$('#project_group_edit').hide();
    });
}
/**
 * 选择参数属组-模态框初始化
 */
function initGroupModal(){
	$.modal('#groupModal', {
        width: 550,
        height: 350
    });
	$('#project_group_edit_group,#search_project_group_group').click(function(){
		$("#groupModalList").empty();
		$('#groupModalName').val('');
		$('#groupModalList').attr("no",$(this).attr('id'));
		queryPropGroup();
		$('#groupModal').show();
	});
	$('#groupModalSearchBtn').click(function(){
		$("#groupModalList").empty();
		queryPropGroup();
	});
	$('#groupModalList').on('click', 'button', function(e) {
		var $this = $(this);
		var group_id = $this.attr('no');
		var group_name = $this.text();
		var name = $('#groupModalList').attr('no');
		var names = '#'+name;
		$(names).val(group_name);
		$(names).attr("no",group_id);
		console.log(name);
		$('#groupModal').hide();
	});
}
/**
 * 选择项目-模态框初始化
 */
function initProjectModal(){
	$.modal('#projectModal', {
        width: 550,
        height: 350
    });
	$('#project_group_edit_project,#search_project_group_project').click(function(){
		$("#projectModalList").empty();
		$('#projectModalName').val('');
		$('#projectModalList').attr("no",$(this).attr('id'));
		if("search_project_group_project"==$(this).attr('id')){
			// 查询已配置参数组的项目
			querHasProject();
		}else{
			// 查询所有项目
			querProject();
		}
		$('#projectModal').show();
	});
	$('#projectModalSearchBtn').click(function(){
		$("#projectModalList").empty();
		querProject();
	});
	$('#projectModalList').on('click', 'button', function(e) {
		var $this = $(this);
		var group_id = $this.attr('no');
		var group_name = $this.text();
		var name = $('#projectModalList').attr('no');
		var names = '#'+name;
		$(names).val(group_name);
		$(names).attr("no",group_id);
		console.log(name);
		$('#projectModal').hide();
	});
}
/**
 * 初始化数据表格
 */
function initTable(){
	$('#table_project_group').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [5, 10, 25, 50, 100],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        columns: [{
            checkbox: true
        }, {
            field: 'id',
            title: 'ID'
        }, {
            field: 'project_code',
            title: '项目编码'
        }, {
            field: 'project_name',
            title: '项目名称'
        }, {
            field: 'group_id',
            title: '参数组ID'
        }, {
            field: 'group_name',
            title: '参数组名称'
        }, {
            field: 'create_author_name',
            title: '创建者'
        }, {
            field: 'create_time',
            title: '创建时间'
        }, {
            field: 'last_modify_time',
            title: '上一次修改时间'
        }, {
            field: 'status_name',
            title: '状态'
        },{
            field: 'status',
            title: '状态编码',
        	visible: false
        }]
    });
}

