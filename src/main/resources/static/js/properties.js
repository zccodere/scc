var ctx = "";
var last_choice_prop_group_name='';
var last_choice_prop_group_no='';
$(function () {
	
	ctx = $('#ctx').val();

    // 初始化Table
	initTable();
	initPropGroupModal();
	
    $.modal('#properties_edit', {
        width: 480,
        height: 450
    });
    
    // 加载所有参数
    queryProperties();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryProperties();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	$('#properties_edit_id').val('');
    	$('#properties_edit_group').val(last_choice_prop_group_name);
    	$('#properties_edit_group').attr('no',last_choice_prop_group_no);
    	var name = $('#properties_edit_name').val('');
    	var key = $('#properties_edit_key').val('');
    	var value = $('#properties_edit_value').val('');
    	var desc = $('#properties_edit_desc').val('');
    	var status = $('#properties_edit_status').val('1');

    	$('#properties_edit').show();
    });
    
    // 修改按钮
    $('#btn_edit').click(function(){
    	var properties = $('#table_properties').bootstrapTable('getSelections');
    	if( properties.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( properties.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	
    	var id = $('#properties_edit_id').val(properties[0].id);
    	var group_id = $('#properties_edit_group').attr("no",properties[0].group_id);
    	var group_name = $('#properties_edit_group').val(properties[0].group_name);
//    	$('#properties_edit_project').attr("disabled","disabled");
    	var name = $('#properties_edit_name').val(properties[0].properties_name);
    	var key = $('#properties_edit_key').val(properties[0].properties_key);
    	var value = $('#properties_edit_value').val(properties[0].properties_value);
    	var desc = $('#properties_edit_desc').val(properties[0].properties_desc);
    	var status = $('#properties_edit_status').val(properties[0].status);
    	
    	/*$('#project_edit_oper_type').val('102');
    	$('#project_edit_id').val(projects[0].id);
    	$('#project_edit_code').val(projects[0].project_code);
    	$('#project_edit_name').val(projects[0].project_name);
    	$('#project_edit_status').val(projects[0].status);*/
    	//$.message.info(properties[0].id);
    	
    	$('#properties_edit').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	
    	var properties = $('#table_properties').bootstrapTable('getSelections');
    	if( properties.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( properties.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',
		    function() {
	    		var id = properties[0].id;
	        	var oper_type = "103";
	//        	$.message.info(projects[0].id);
	        	var requestData = {
	        			"id": id,
	        			"oper_type": oper_type};
	        	
	        	operProperties(requestData,oper_type);
	        	//$.message.info(id);
	        	$('#table_properties').bootstrapTable('removeByUniqueId',id);
			}
		);
    });
    
    // 参数编辑 保存按钮
    $('#properties_edit_save_btn').click(function(){
    	var id = $('#properties_edit_id').val();
    	var group_name = $('#properties_edit_group').val();
    	var group_id = $('#properties_edit_group').attr('no');
    	var name = $('#properties_edit_name').val();
    	var key = $('#properties_edit_key').val();
    	var value = $('#properties_edit_value').val();
    	var desc = $('#properties_edit_desc').val();
    	var status = $('#properties_edit_status').val();
    	
    	if(!group_id){
    		$.message.error('请选择项目');
    		return;
    	}
    	if(!name){
    		$.message.error('请输入参数名称');
    		return;
    	}
    	if(!key){
    		$.message.error('请输入参数键');
    		return;
    	}
    	if(!value){
    		$.message.error('请输入参数值');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择参数状态');
    		return;
    	}
    	var requestData;
    	var oper_type = "101";
    	if(!id){
    		requestData = {
    			"group_id": group_id,
    			"group_name": group_name,
    			"properties_name": name,
    			"properties_key": key,
    			"properties_value": value,
    			"properties_desc": desc,
    			"status": status,
    			"oper_type": oper_type};
    	}else{
    		oper_type = "102";
    		requestData = {
    			"id":id,
    			"group_id": group_id,
    			"group_name": group_name,
    			"properties_name": name,
    			"properties_key": key,
    			"properties_value": value,
    			"properties_desc": desc,
    			"status": status,
    			"oper_type": oper_type};
    	}
    	operProperties(requestData,oper_type);
    	$('#properties_edit').hide();
    });
    
    // 参数编辑 取消按钮
    $('#properties_edit_cancel_btn').click(function(){
    	$('#properties_edit').hide();
    });
});

// 参数维护
function operProperties(requestData,oper_type){
	var request_url = ctx+"/rest/properties/oper";
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
						queryProperties();
					}
					$.message.info(data.respMsg);
				} else {
					//$('#table_project').bootstrapTable('removeAll');
					$.message.error(data.respMsg);
				}
			},
			error: function() {
				$.message.error('参数维护Ajax请求失败!');
			},
			complete: function(){
				$.loading.hide();
			}
		});
}
// 重置查询
function resetQuery(){
	$('#search_properties_group').val('');
	$('#search_properties_group').attr('no','');
	$('#search_properties_name').val('');
	$('#search_properties_key').val('');
	$('#search_properties_status').val('2');
}
// 查询参数
function queryProperties(){
	
	var group_id = $('#search_properties_group').attr('no');
	var name = $('#search_properties_name').val();
	var key = $('#search_properties_key').val();
	var status = $('#search_properties_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	var request_data = {
			"group_id":group_id,
			"properties_name":name,
			"properties_key":key,
			"status":status
		};
	var request_url = ctx+"/rest/properties/query";

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
				var properties_data = data.respMap.properties_list;
				$('#table_properties').bootstrapTable('load', properties_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_properties').bootstrapTable('removeAll');
				//$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载参数Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
/**
 * 查询已配置参数的参数组
 */
function queryHasGroup(){
	var request_data = "";
	var request_url = ctx+"/rest/properties/queryHasGroup";
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
				var prop_group_list = data.respMap.prop_group_list;
				$(prop_group_list).each(function(index,item){
					$("#propGroupModalList").append("<button class='btn btn-default btn-sm' no='"+item.group_id+"'>"+item.group_name+"</button>");
				});
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('查询参数组Ajax请求失败!');
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
			"group_name":$('#propGroupModalName').val(),
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
						$("#propGroupModalList").append("<button class='btn btn-default btn-sm' no='"+item.id+"'>"+item.group_name+"</button>");
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
 * 选择参数属组-模态框初始化
 */
function initPropGroupModal(){
	$.modal('#propGroupModal', {
        width: 550,
        height: 350
    });
	$('#properties_edit_group').click(function(){
		queryPropGroup();
		$("#propGroupModalList").empty();
		$('#propGroupModalName').val('');
		$('#propGroupModalList').attr("no",$(this).attr('id'));
		$('#propGroupModal').show();
	});
	$('#search_properties_group').click(function(){
        queryPropGroup();
		$("#propGroupModalList").empty();
		$('#propGroupModalName').val('');
		$('#propGroupModalList').attr("no",$(this).attr('id'));
		$('#propGroupModal').show();
	});
	$('#propGroupModalSearchBtn').click(function(){
		$("#propGroupModalList").empty();
		queryPropGroup();
	});
	$('#propGroupModalList').on('click', 'button', function(e) {
		var $this = $(this);
		var group_id = $this.attr('no');
		var group_name = $this.text();
		var name = $('#propGroupModalList').attr('no');
		var names = '#'+name;
		$(names).val(group_name);
		$(names).attr("no",group_id);
		console.log(name);
		if(name == 'properties_edit_group'){
			last_choice_prop_group_name = group_name;
			last_choice_prop_group_no = group_id;
		}
		$('#propGroupModal').hide();
	});
}
/**
 * 初始化表格
 */
function initTable(){
    $('#table_properties').bootstrapTable({
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
            field: 'group_id',
            title: '参数组编码',
            visible: false
        }, {
            field: 'group_name',
            title: '参数组名称'
        }, {
            field: 'properties_type',
            title: '参数类型',
            visible: false
        }, {
            field: 'properties_name',
            title: '参数名称'
        }, {
            field: 'properties_key',
            title: '参数键'
        }, {
            field: 'properties_value',
            title: '参数值'
        }, {
            field: 'properties_desc',
            title: '参数描述',
            visible: false
        }, {
            field: 'create_author_name',
            title: '创建者',
            visible: false
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