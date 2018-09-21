var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();
	
    $.modal('#edit_prop_group', {
        width: 480,
        height: 402
    });
    
    tableInit();
    loadPropGroupType();
    queryPropGroup();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryPropGroup();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    // 新增按钮
    $('#btn_add').click(function(){
    	$('#edit_prop_group_oper_type').val('101');
    	$('#edit_prop_group_id').val('');
    	$('#edit_prop_group_name').val('');
    	$('#edit_prop_group_desc').val('');
    	$('#edit_prop_group').show();
    });
    // 修改按钮
    $('#btn_edit').click(function(){
    	var propgroups = $('#table_prop_group').bootstrapTable('getSelections');
    	if( propgroups.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( propgroups.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	
    	$('#edit_prop_group_oper_type').val('102');
    	$('#edit_prop_group_id').val(propgroups[0].id);
    	$('#edit_prop_group_name').val(propgroups[0].group_name);
    	$('#edit_prop_group_type').val(propgroups[0].code_id);
    	$('#edit_prop_group_desc').val(propgroups[0].group_desc);
    	$('#edit_prop_group_status').val(propgroups[0].status);
    	$('#edit_prop_group').show();
    });
    // 删除按钮
    $('#btn_delete').click(function(){
    	
    	var propgroups = $('#table_prop_group').bootstrapTable('getSelections');
    	if( propgroups.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( propgroups.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = propgroups[0].id;
        	var oper_type = "103";
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operPropGroup(requestData,oper_type);
        	$('#table_prop_group').bootstrapTable('removeByUniqueId',id);
    	});
    });
    // 属组编辑 保存按钮
    $('#edit_prop_group_save_btn').click(function(){
    	var oper_type = $('#edit_prop_group_oper_type').val();
    	var id = $('#edit_prop_group_id').val();
    	var name = $('#edit_prop_group_name').val();
    	var type_id = $('#edit_prop_group_type').val();
    	//var type_name = $('#edit_prop_group_type').attr('checked').html();
    	var type_name = $("#edit_prop_group_type").find("option:selected").text();
    	var desc = $('#edit_prop_group_desc').val();
    	var status = $('#edit_prop_group_status').val();
    	if(!name){
    		$.message.error('请输入参数组名称');
    		return;
    	}
    	if(!desc){
    		$.message.error('请输入参数组描述');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择项目状态');
    		return;
    	}
    	var requestData;
    	if("101" == oper_type){
    		requestData = {
    			"group_name": name,
    			"code_id": type_id,
    			"code_name": type_name,
    			"group_desc": desc,
    			"status": status,
    			"oper_type": oper_type};
    	}else{
    		requestData = {
    			"id":id,
    			"group_name": name,
    			"code_id": type_id,
    			"code_name": type_name,
    			"group_desc": desc,
    			"status": status,
    			"oper_type": oper_type};
    	}
    	operPropGroup(requestData,oper_type);
    	$('#edit_prop_group').hide();
    });
    
    // 属组编辑 取消按钮
    $('#edit_prop_group_cancel_btn').click(function(){
    	$('#edit_prop_group').hide();
    });
});

/**
 * 参数组维护
 * @param requestData
 * @param oper_type
 */
function operPropGroup(requestData,oper_type){
	var request_url = ctx+"/rest/propertiesgroup/oper";
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
						queryPropGroup();
					}
					$.message.info(data.respMsg);
				} else {
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
/**
 * 重置查询参数
 */
function resetQuery(){
	$('#prop_group_id').val('');
	$('#prop_group_name').val('');
	$('#prop_group_type').val('2');
	$('#prop_group_status').val('2');
}

/**
 * 查询参数组数据
 */
function queryPropGroup(){
	
	var id = $('#prop_group_id').val();
	var name = $('#prop_group_name').val();
	var code_id = $('#prop_group_type').val();
	var status = $('#prop_group_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	if(2 == code_id || '2' == code_id){
		code_id = "";
	}
	var request_data = {
			"id":id,
			"group_name":name,
			"code_id":code_id,
			"status":status
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
				$('#table_prop_group').bootstrapTable('load', properties_group_list);
				//$.message.info(data.respMsg);
			} else {
				$('#table_prop_group').bootstrapTable('removeAll');
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
 * 加载属组类型
 */
function loadPropGroupType(){
	var request_data = {
			"type_code":"prop_group_type",
		};
	var request_url = ctx+"/rest/codelist/querybyredis";
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
				var codelist_list = data.respMap.codelist_list;
				$(codelist_list).each(function(index,item){
					if("0"==item.status){
						$("#prop_group_type").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
					}else{
						$("#edit_prop_group_type").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
						$("#prop_group_type").append("<option value='"+item.code_id+"'>"+item.code_name+"</option>");
					}
				});
				//$.message.info(data.respMsg);
			} else {
				$.message.error(data.respMsg);
			}
		},
		error: function() {
			$.message.error('加载属组类型Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}

/**
 * 初始化表格
 */
function tableInit() {
    $('#table_prop_group').bootstrapTable({
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
            field: 'code_id',
            title: '参数组类型编码',
            visible: false
        }, {
            field: 'code_name',
            title: '参数组类型名称'
        }, {
            field: 'group_name',
            title: '参数组名称'
        }, {
            field: 'group_desc',
            title: '参数组描述'
        }, {
            field: 'create_time',
            title: '创建时间'
        }, {
            field: 'create_author_name',
            title: '创建者'
        }, {
            field: 'create_author_code',
            title: '创建人编码',
        	visible: false
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
