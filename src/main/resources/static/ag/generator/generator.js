var generator = {
    baseUrl: "/base/generator",
    tableId: "generatorTable",
    toolbarId: "toolbar",
    unique: "id",
    order: "asc",
    currentItems: {}
};
generator.columns = function () {
    return [{
        checkbox: true
    }, {
        field: 'sqlName',
        title: '表名'
    }, {
        field: 'remarks',
        title: '表备注'
    }];
};
generator.queryParams = function (params) {
   
	 if (!params)
	        return {
			   jdbcUrl: $("#jdbcUrl").val(),
		        jdbcUsername: $("#jdbcUsername").val(),
		        jdbcPasswordText: $("#jdbcPasswordText").val()
	        };
	        
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit, //页面大小
        offset: params.offset, //页码
        jdbcUrl: $("#jdbcUrl").val(),
        jdbcUsername: $("#jdbcUsername").val(),
        jdbcPasswordText: $("#jdbcPasswordText").val()
    };
    return temp;
};

generator.init = function () {

	var jdbcUrl = $("#jdbcUrl").val();
	var jdbcUsername = $("#jdbcUsername").val();
	var jdbcPasswordText = $("#jdbcPasswordText").val();
	var tableStr = $("#tableStr").val();
	var basepackageStr = $("#basepackageStr").val();
	var template = $("#template").val();
	
	var queryUrl = generator.baseUrl + "/page";
	
    generator.table = $('#' + generator.tableId).bootstrapTable({
        url: queryUrl, //请求后台的URL（*）
        method: 'get', //请求方式（*）
        toolbar: '#' + generator.toolbarId, //工具按钮用哪个容器
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        sortOrder: generator.order, //排序方式
        queryParams: generator.queryParams,//传递参数（*）
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
        search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false, //是否显示所有的列
        showRefresh: true, //是否显示刷新按钮
        minimumCountColumns: 2, //最少允许的列数
        clickToSelect: true, //是否启用点击选中行
        uniqueId: generator.unique, //每一行的唯一标识，一般为主键列
        showToggle: true, //是否显示详细视图和列表视图的切换按钮
        cardView: false, //是否显示详细视图
        detailView: false, //是否显示父子表
        columns: generator.columns(),
        responseHandler: function(res) {
        	
            return {
                "total": res.total,//总页数
                "rows": res.rows   //数据
            };
        }
    });
};
generator.select = function (layerTips) {
    var rows = generator.table.bootstrapTable('getSelections');
    if (rows.length >0) {
        generator.currentItems = rows;
        return true;
    } else {
        alert("请至少选中一行");
        return false;
    }
};

generator.getInitConfig = function () {
	  $.ajax({
          url: "/base/generator/getInitConfig",
          data: {},
          type: "GET",
          dataType: "json",
          async: false, 
          
          success: function (data) {
        	  
        	  $("#jdbcUrl").val(data['jdbc.url']);
        	  $("#jdbcUsername").val(data['jdbc.username']);
        	  $("#jdbcPasswordText").val(data['jdbc.password']);
        	  $("#tableStr").val(data.tableStr);
        	  $("#basepackageStr").val(data.basepackage);
        	  $("#template").val(data.template);
          }
          
      });
};

layui.use(['form', 'layedit', 'laydate'], function () {
	generator.getInitConfig();
    generator.init();

    var editIndex;
    var layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
        layer = layui.layer, //获取当前窗口的layer对象
        form = layui.form(),
        layedit = layui.layedit,
        laydate = layui.laydate;
    var addBoxIndex = -1;
    //初始化页面上面的按钮事件
    $('#btn_query').on('click', function () {
        generator.table.bootstrapTable('refresh', generator.queryParams());
    });
    
    $('#btn_generate').on('click',function(){
    		
    		var jdbcUrl = $("#jdbcUrl").val();
    		var jdbcUsername = $("#jdbcUsername").val();
    		var jdbcPasswordText = $("#jdbcPasswordText").val();
    		var tableStr = $("#tableStr").val();
    		var basepackageStr = $("#basepackageStr").val();
    		var template = $("#template").val();
    		
    		 if(generator.select()){
    	            var tableNames = [];
    	            for(var i=0;i<generator.currentItems.length;i++){
    	                tableNames.push(generator.currentItems[i].sqlName);
    	                tableStr = generator.currentItems[i].sqlName;
    	            }
    	            
    	            var postData = {
        					"jdbcUrl":jdbcUrl,
        					 "jdbcUsername":jdbcUsername,
        					 "jdbcPasswordText":jdbcPasswordText,
        					 "tableStr":tableStr,
        					 "basepackageStr":basepackageStr,
        					 "template":template
        			 };
        			 
        			 
                location.href = "/base/generator/code?jdbcUrl="+jdbcUrl+"&jdbcUsername="+jdbcUsername+"&jdbcPasswordText="+jdbcPasswordText+"&tableStr="+tableStr+"&basepackageStr="+basepackageStr+"&template="+template;
        
    	     }
    });
    
});
