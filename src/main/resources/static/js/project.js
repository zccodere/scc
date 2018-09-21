var ctx = "";
$(function () {
	
	ctx = $('#ctx').val();
	
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    
    $.modal('#project_edit', {
        width: 550,
        height: 330
    });
    
    // 加载所有项目
    queryProject();
    
    // 查询按钮
    $('#btn_query').click(function(){
    	queryProject();
    });
    // 重置按钮
    $('#btn_reset').click(function(){
    	resetQuery();
    });
    
    // 新增按钮
    $('#btn_add').click(function(){
    	$('#project_edit_oper_type').val('101');
    	$('#project_edit_id').val('');
    	$('#project_edit_code').val('');
    	$('#project_edit_name').val('');
    	$('#project_edit').show();
    });
    
    // 修改按钮
    $('#btn_edit').click(function(){
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您要修改的记录');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('修改功能不支持批量操作');
    		return;
    	}
    	
    	$('#project_edit_oper_type').val('102');
    	$('#project_edit_id').val(projects[0].id);
    	$('#project_edit_code').val(projects[0].project_code);
    	$('#project_edit_name').val(projects[0].project_name);
    	$('#project_edit_status').val(projects[0].status);
//    	$.message.info(projects[0].id);
    	$('#project_edit').show();
    });
    
    // 删除按钮
    $('#btn_delete').click(function(){
    	
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您要删除的记录');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	
    	$.dialog.confirm('确定要删除吗？', null, '删除', '取消',function() {
    		var id = projects[0].id;
        	var oper_type = "103";
//        	$.message.info(projects[0].id);
        	var requestData = {
        			"id": id,
        			"oper_type": oper_type};
        	operProject(requestData,oper_type);
        	//$.message.info(id);
        	$('#table_project').bootstrapTable('removeByUniqueId',id);
    	});
    });
    
    // 预览参数按钮
    $('#btn_show_prop').click(function(){
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您要预览的项目');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	window.open(ctx+'/project/config/'+projects[0].project_code);
    });
    
    // 下载参数按钮
    $('#btn_down_prop').click(function(){
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您要下载参数的项目');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	window.open(ctx+'/download/'+projects[0].project_code);
    })
    // 刷新缓存
    $('#btn_refresh').click(function(){
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您要刷新缓存的项目');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	refreshProject(projects[0].project_code);
    });
    
    // 安全策略初始化
    initSecurityModal();
    $('#btn_security').click(function(){
    	var projects = $('#table_project').bootstrapTable('getSelections');
    	if( projects.length == 0){
    		$.message.error('请选择您需要进行安全策略配置的项目');
    		return;
    	}
    	if( projects.length > 1){
    		$.message.error('抱歉，系统暂时不支持批量操作');
    		return;
    	}
    	var project_id = projects[0].id;
    	$('#security_project_id').val(project_id);
    	queryProjectById(project_id,function(data){
    		$("#securityTokenStr").html(data.respMap.project.security_token);
    		if(data.respMap.project.security_flag == "1"){
    			$("#security_check").bootstrapSwitch('state', true, true);
    			$("#securityModalToken").show();
    		}else{
    			$("#security_check").bootstrapSwitch('state', false, false);
    			$("#securityModalToken").hide();
    		}
    	});
    	
    	$('#securityModal').modal('show');
    });
    
    // 项目编辑 保存按钮
    $('#project_edit_save_btn').click(function(){
    	var oper_type = $('#project_edit_oper_type').val();
    	var id = $('#project_edit_id').val();
    	var code = $('#project_edit_code').val();
    	var name = $('#project_edit_name').val();
    	var status = $('#project_edit_status').val();
    	if(!code){
    		$.message.error('请输入项目号');
    		return;
    	}
    	if(!name){
    		$.message.error('请输入项目名称');
    		return;
    	}
    	if(!status){
    		$.message.error('请选择项目状态');
    		return;
    	}
    	var requestData;
    	if("101" == oper_type){
    		requestData = {
    			"project_code": code,
    			"project_name": name,
    			"status": status,
    			"oper_type": oper_type};
    	}else{
    		requestData = {
    			"id":id,
    			"project_code": code,
    			"project_name": name,
    			"status": status,
    			"oper_type": oper_type};
    	}
    	operProject(requestData,oper_type);
    	$('#project_edit').hide();
    });
    
    // 项目编辑 取消按钮
    $('#project_edit_cancel_btn').click(function(){
    	$('#project_edit').hide();
    });
});

// 项目维护
function operProject(requestData,oper_type){
	var request_url = ctx+"/project/oper";
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
						queryProject();
					}
					$.message.info(data.respMsg);
				} else {
					//$('#table_project').bootstrapTable('removeAll');
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
// 项目安全策略保存
function saveSecurity(id,flag,token,fn){
	var requestData = {
		"id":id,
		"security_flag":flag,
		"security_token":token
	};
	var request_url = ctx+"/project/savesecurity";
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
			fn(data);
		},
		error: function() {
			$.message.error('Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
// 重置查询
function resetQuery(){
	$('#search_project_code').val('');
	$('#search_project_name').val('');
	$('#search_project_status').val('2');
}
// 查询项目
function queryProject(){
	
	var code = $('#search_project_code').val();
	var name = $('#search_project_name').val();
	var status = $('#search_project_status').val();
	if(2 == status || '2' == status){
		status = "";
	}
	var request_data = {
			"project_code":code,
			"project_name":name,
			"status":status
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
				$('#table_project').bootstrapTable('load', project_data);
				//$.message.info(data.respMsg);
			} else {
				$('#table_project').bootstrapTable('removeAll');
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
//查询项目
function queryProjectById(project_id,fn){
	
	var request_data = {
			"id":project_id
		};
	var request_url = ctx+"/project/findone";

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
			fn(data);
			$.loading.hide();
		},
		error: function() {
			$.message.error('加载项目Ajax请求失败!');
		},
		complete: function(){
			$.loading.hide();
		}
	});
}
// 刷新缓存
function refreshProject(project_code){
	
	var request_data = {
			"project_code":project_code
		};
	var request_url = ctx+"/project/refresh";

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
				$.message.info(data.respMsg);
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
 * 安全策略-模态框初始化
 */
function initSecurityModal(){
	$("#security_check").bootstrapSwitch();
	
	// 当更改开关时
	$("#security_check").on('switchChange.bootstrapSwitch', function(event, state) {
	  console.log(this); // DOM element
	  console.log(event); // jQuery event
	  console.log(state); // true | false
	  
	  if(state == true){
			$("#security_check").bootstrapSwitch('state', true, true);
			flag = "1";
			$("#securityModalToken").show();
		}else{
			$("#security_check").bootstrapSwitch('state', false, false);
			flag = "0";
			$("#securityModalToken").hide();
		}
	});
	
	var flag = "0";
	
	// 重置token
	$('#securityModalTokenReset').click(function(){
		$.ajax({
			type: "post",
			url: ctx+"/project/resettoken",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: true,
			data: {},
			dataType: "json",
			crossDomain: true == !(document.all),
			beforeSend: function() {
				$.loading.show("正在加载");
			},
			success: function(data) {
				$("#securityTokenStr").html(data.respMap.token);
			},
			error: function() {
				$.message.error('Ajax请求失败!');
			},
			complete: function(){
				$.loading.hide();
			}
		});
	});
	
	// 保存按钮
	$('#securityModalSave').click(function(){
		var id=$('#security_project_id').val();
		var token=$('#securityTokenStr').html();
		saveSecurity(id,flag,token,function(data){
			$.message.info(data.respMsg);
			$('#securityModal').modal('hide');
    	});
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    
    //初始化Table
    oTableInit.Init = function () {
        $('#table_project').bootstrapTable({
//            url: '/project/query',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
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
                field: 'project_code',
                title: '项目编码'
            }, {
                field: 'project_name',
                title: '项目名称'
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