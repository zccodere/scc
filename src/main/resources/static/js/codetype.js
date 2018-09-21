var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    
    $.modal('#code_type_edit', {
    	width: 480,
        height: 405
    });
    
    // 加载所有项目
    queryCodeType();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryCodeType();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	$('#edit_code_type_oper_type').val('101');
    	$('#edit_code_type_id').val('');
    	$('#edit_code_type_code').val('');
    	$('#edit_code_type_name').val('');
    	$('#edit_code_type_desc').val('');
    	$('#code_type_edit').show();
    });
    
    // 修改按钮
    $('#btn_edit').click(function(){
    	var codetypes = $('#table_code_type').bootstrapTable('getSelections');
    	if( codetypes.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( codetypes.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	
    	$('#edit_code_type_oper_type').val('102');
    	$('#edit_code_type_id').val(codetypes[0].id);
    	$('#edit_code_type_code').val(codetypes[0].type_code);
    	$('#edit_code_type_name').val(codetypes[0].type_name);
    	$('#edit_code_type_desc').val(codetypes[0].type_desc);
    	$('#edit_code_type_status').val(codetypes[0].status);
    	$('#code_type_edit').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	var codetypes = $('#table_code_type').bootstrapTable('getSelections');
    	if( codetypes.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( codetypes.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = codetypes[0].id;
        	var oper_type = "103";
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operCodeType(requestData,oper_type);
        	$('#table_code_type').bootstrapTable('removeByUniqueId',id);
    	});
    });
    
    // 类型编辑 保存按钮
    $('#edit_code_type_save_btn').click(function(){
    	var oper_type = $('#edit_code_type_oper_type').val();
    	var id = $('#edit_code_type_id').val();
    	var code = $('#edit_code_type_code').val();
    	var name = $('#edit_code_type_name').val();
    	var desc = $('#edit_code_type_desc').val();
    	var status = $('#edit_code_type_status').val();
    	if(!code){
    		$.message.error('请输入类型编码');
    		return;
    	}
    	if(!name){
    		$.message.error('请输入类型名称');
    		return;
    	}
    	if(!desc){
    		$.message.error('请输入类型描述');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择类型状态');
    		return;
    	}
    	var requestData;
    	if("101" == oper_type){
    		requestData = {
    			"type_code": code,
    			"type_name": name,
    			"type_desc":desc,
    			"status": status,
    			"oper_type": oper_type};
    	}else{
    		requestData = {
    			"id":id,
    			"type_code": code,
    			"type_name": name,
    			"type_desc":desc,
    			"status": status,
    			"oper_type": oper_type};
    	}
    	operCodeType(requestData,oper_type);
    	$('#code_type_edit').hide();
    });
    
    // 项目编辑 取消按钮
    $('#edit_code_type_cancel_btn').click(function(){
    	$('#code_type_edit').hide();
    });
});

// 项目维护
function operCodeType(requestData,oper_type){
	var request_url = ctx+"/rest/codeType/oper";
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
						queryCodeType();
					}
					$.message.info(data.respMsg);
				} else {
					//$('#table_codetype').bootstrapTable('removeAll');
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
	$('#search_type_code').val('');
	$('#search_type_name').val('');
	$('#search_type_status').val('2');
}
// 查询项目
function queryCodeType(){
	var code = $('#search_type_code').val();
	var name = $('#search_type_name').val();
	var status = $('#search_type_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	var request_data = {
			"type_code": code,
			"type_name": name,
			"status":status
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
				var codetype_data = data.respMap.codetype_list;
				$('#table_code_type').bootstrapTable('load', codetype_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_code_type').bootstrapTable('removeAll');
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

var TableInit = function () {
    var oTableInit = new Object();
    
    //初始化Table
    oTableInit.Init = function () {
        $('#table_code_type').bootstrapTable({
//            url: '/rest/codeType/query',         //请求后台的URL（*）
//            method: 'post',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
           // striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
//            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 25, 50, 100],        //可供选择的每页的行数（*）
//            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
//            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
//            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
//            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
//            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
//            cardView: false,                    //是否显示详细视图
//            detailView: false,                   //是否显示父子表
          
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'ID'
            }, {
                field: 'type_code',
                title: '类型编码'
            }, {
                field: 'type_name',
                title: '类型名称'
            }, {
                field: 'type_desc',
                title: '类型描述'
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
    };
  
    //得到查询的参数
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};
    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };
    return oInit;
};