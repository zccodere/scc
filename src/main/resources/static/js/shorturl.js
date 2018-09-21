var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();

	initTable();
	initUrlEidtModal();
	
	queryUrlConfig();
    // 查询按钮
    $('#btn_query').click(function(){
    	queryUrlConfig();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	clearUrlConfigEdit();
    	$('#url_config_edit').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	
    	var url_configs = $('#table_url_config').bootstrapTable('getSelections');
    	if( url_configs.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( url_configs.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = url_configs[0].id;
        	var oper_type = "103";
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operUrlConfig(requestData,oper_type);
        	$('#table_url_config').bootstrapTable('removeByUniqueId',id);
    	});
    });
    
    // 刷新缓存
    $('#btn_refresh').click(function(){
    	refresh();
    });
});

/**
 * 清空数据
 */
function clearUrlConfigEdit(){
	$('#long_url').val('');
	$('#short_url').val('');
}

//重置查询
function resetQuery(){
	$('#search_shorturl_code').val('');
	$('#search_longurl_code').val('');
	$('#search_url_status').val('2');
}

//刷新缓存
function refresh(){
	var request_url = ctx+"/rest/url/refresh";
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

function queryUrlConfig(){
	var shorturl = $('#search_shorturl_code').val();
	var longurl = $('#search_longurl_code').val();
	var status = $('#search_url_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	
	var request_data = {
			"shorturl":shorturl,
			"longurl":longurl,
			"status":status
		};
	var request_url = ctx+"/rest/url/query";
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
				var url_data = data.respMap.url_list;
				$('#table_url_config').bootstrapTable('load', url_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_url_config').bootstrapTable('removeAll');
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
 * 初始化数据表格
 */
function initTable(){
	$('#table_url_config').bootstrapTable({
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
            field: 'belong_system',
            title: '来源系统',
            visible: false
        }, {
            field: 'short_url',
            title: '短地址'
        }, {
            field: 'long_url',
            title: '原网址',
            width: '20%'
        },{
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

function initUrlEidtModal(){
	$.modal('#url_config_edit', {
        width: 460,
        height: 300
    });
	//保存按钮
	$('#url_edit_save_btn').click(function(){
    	var long_url = $('#long_url').val();
    	var status = $('#url_status').val();
    	if(!long_url){
    		$.message.error('请输入长网址');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择状态');
    		return;
    	}
    	var requestData = {
    			"long_url": long_url,
    			"status": status,
    			"oper_type": "101"
    			};
    	operUrlConfig(requestData,"101");
    	$('#url_config_edit').hide();
	});
	// 取消按钮
    $('#url_edit_cancel_btn').click(function(){
    	$('#url_config_edit').hide();
    });
}

function operUrlConfig(requestData,oper_type){
	var request_url = ctx+"/rest/url/oper";
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
						queryUrlConfig();
					}
					$.message.info(data.respMsg);
				} else {
					$.message.error(data.respMsg);
				}
			},
			error: function() {
				$.message.error('Ajax请求失败!');
			},
			complete: function(){
				$.loading.hide();
			}
		});
}