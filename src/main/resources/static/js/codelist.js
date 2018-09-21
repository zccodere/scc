var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();

	// 初始化表格
	tableInit();
    
	// 初始化编辑框
    $.modal('#edit_code_list', {
        width: 500,
        height: 405
    });
    
    queryCodeList();
    
    initCodeTypeModal();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryCodeList();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	$('#edit_code_list_oper_type').val('101');
    	$('#edit_code_list_id').val('');
    	$('#edit_code_list_code_type').val('');
    	$('#edit_code_list_code_id').val('');
    	$('#edit_code_list_code_name').val('');
    	$('#edit_code_list').show();
    });
    
    // 修改按钮
    $('#btn_edit').click(function(){
    	var codelists = $('#table_code_list').bootstrapTable('getSelections');
    	if( codelists.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( codelists.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	$('#edit_code_list_oper_type').val('102');
    	$('#edit_code_list_id').val(codelists[0].id);
    	$('#edit_code_list_code_type').val(codelists[0].type_code);
    	$('#edit_code_list_code_id').val(codelists[0].code_id);
    	$('#edit_code_list_code_name').val(codelists[0].code_name);
    	$('#edit_code_list_status').val(codelists[0].status);
    	$('#edit_code_list').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	var codelists = $('#table_code_list').bootstrapTable('getSelections');
    	if( codelists.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( codelists.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = codelists[0].id;
        	var oper_type = "103";
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operProject(requestData,oper_type);
        	$('#table_code_list').bootstrapTable('removeByUniqueId',id);
    	});
    });
    
    // 刷新缓存
    $('#btn_refresh').click(function(){
    	refresh();
    });
    
    // 参数编辑 保存按钮
    $('#edit_code_list_save_btn').click(function(){
    	var oper_type = $('#edit_code_list_oper_type').val();
    	var id = $('#edit_code_list_id').val();
    	var code_type_name = $('#edit_code_list_code_type').val();
    	var code_type = $('#edit_code_list_code_type').attr('no');
    	var code_id = $('#edit_code_list_code_id').val();
    	var code_name = $('#edit_code_list_code_name').val();
    	var status = $('#edit_code_list_status').val();
    	if(!code_type){
    		$.message.error('请选择类型编码');
    		return;
    	}
    	if(!code_id){
    		$.message.error('请输入参数取值');
    		return;
    	}
    	if(!code_name){
    		$.message.error('请输入参数名称');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择项目状态');
    		return;
    	}
    	var requestData;
    	var oper_type = "";
    	if(!id){
    		oper_type = "101";
    		requestData = {
				"type_code": code_type,
    			"code_id": code_id,
    			"code_name": code_name,
    			"status": status,
    			"oper_type": oper_type};
    	}else{
    		oper_type = "102";
    		requestData = {
    			"id":id,
    			"type_code": code_type,
    			"code_id": code_id,
    			"code_name": code_name,
    			"status": status,
    			"oper_type":oper_type};
    	}
    	operProject(requestData,oper_type);
    	$('#edit_code_list').hide();
    });
    
    // 参数编辑 取消按钮
    $('#edit_code_list_cancel_btn').click(function(){
    	$('#edit_code_list').hide();
    });
});

//刷新缓存
function refresh(){
	var request_url = ctx+"/rest/codeType/refresh";
  $.ajax({
		type: "post",
		url: request_url,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: true,
		data: "",
		dataType: "json",
		crossDomain: true == !(document.all),
		beforeSend: function() {
			$.loading.show("正在加载");
		},
		success: function(data) {
			if ("200" == data.respCode) {
				$.message.info(data.respMsg);
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('刷新缓存Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

// 项目维护
function operProject(requestData,oper_type){
	var request_url = ctx+"/rest/codelist/oper";
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
						queryCodeList();
					}
					$.message.info(data.respMsg);
				} else {
					//$('#table_code_list').bootstrapTable('removeAll');
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
	$('#search_code_type').val('');
	$('#search_code_name').val('');
	$('#search_code_status').val('2');
}
// 查询参数
function queryCodeList(){
	
	var code = $('#search_code_type').attr('no');
	var name = $('#search_code_name').val();
	var status = $('#search_code_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	var request_data = {
			"type_code":code,
			"code_name":name,
			"status":status
		};
	var request_url = ctx+"/rest/codelist/query";

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
				var codelist_data = data.respMap.codelist_list;
				$('#table_code_list').bootstrapTable('load', codelist_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_code_list').bootstrapTable('removeAll');
//				$.message.error(data.respMsg);
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
 * 选择类型属组-模态框初始化
 */
function initCodeTypeModal(){
	$.modal('#codeTypeModal', {
        width: 550,
        height: 350
    });
	$('#search_code_type').click(function(){
		$("#codeTypeModalList").empty();
		$('#codeTypeModalCode').val('');
		$('#codeTypeModalName').val('');
		$('#codeTypeModalList').attr("no",$(this).attr('id'));
		$('#codeTypeModal').show();
	});
	$('#edit_code_list_code_type').click(function(){
		$("#codeTypeModalList").empty();
		$('#codeTypeModalCode').val('');
		$('#codeTypeModalName').val('');
		$('#codeTypeModalList').attr("no",$(this).attr('id'));
		$('#codeTypeModal').show();
	});
	$('#codeTypeModalSearchBtn').click(function(){
		$("#codeTypeModalList").empty();
		queryCodeType();
	});
	$('#codeTypeModalList').on('click', 'button', function(e) {
		var $this = $(this);
		var group_id = $this.attr('no');
		var group_name = $this.text();
		var name = $('#codeTypeModalList').attr('no');
		var names = '#'+name;
		$(names).val(group_name);
		$(names).attr("no",group_id);
		console.log(name);
		$('#codeTypeModal').hide();
	});
}

//查询类型
function queryCodeType(code,name){
//	var code = $('#search_type_code').val();
//	var name = $('#search_type_name').val();
//	var status = $('#search_type_status').val();
//	if(2 == status || '2' == status){
//		status = "";
//	}
	var request_data = {
			"type_code": code,
			"type_name": name,
			"status":"1"
		};
	var request_url = ctx+"/rest/codeType/query";

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
				var codetype_list = data.respMap.codetype_list;
				$(codetype_list).each(function(index,item){
					if("1" == item.status){
						$("#codeTypeModalList").append("<button class='btn btn-default btn-sm' no='"+item.type_code+"'>"+item.type_name+"</button>");
					}
				});
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

function tableInit(){
    $('#table_code_list').bootstrapTable({
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
            field: 'type_code',
            title: '类型编码'
        }, {
            field: 'code_id',
            title: '参数取值'
        }, {
            field: 'code_name',
            title: '参数名称'
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
  