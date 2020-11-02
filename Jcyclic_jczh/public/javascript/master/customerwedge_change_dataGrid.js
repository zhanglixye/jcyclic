userInfo = {}
$(function(){
	initDataGridHandler("relevancedTab",10,"check","bottom",true,"isHasFn");
	initDataGridHandler("irrelevantTab",10,"check","bottom",true,"isHasFn");
	relevanceClientByUserInit();
	$(".language_conversion").bind('change', function() {
        setlevelNameHandler();
    });
})

function setlevelNameHandler()
{
	var levelName = userInfo["level"+$('#language').val()];
	$('#levelName').html(levelName);
}
function relevanceClientByUserInit()
{
	var userID = "";
	if(urlPars.hasParm("userID"))
	{
		userID = urlPars.parm("userID");
	}
	var pars = {"userCD":userID,"searchFLg":"relevance"};
	var path = $.getAjaxPath()+"employeeInitInfo";
	
	$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	if(data[$.getRequestDataName()] != null)
           	{
           		userInfo = data[$.getRequestDataName()];
           		$('#userCD').html(data[$.getRequestDataName()]['memberID']);
				$('#nickName').html(data[$.getRequestDataName()]['nickname']);
				setlevelNameHandler();
				
           		$('#lockFlg').val(data[$.getRequestDataName()]['lockFlg']);
           		var irrelevantList = data[$.getRequestDataName()]['irrelevantList'];
           		rowleft=data[$.getRequestDataName()]['irrelevantList'];
				var total = irrelevantList.length;
				//注释国际化
				Note = part_language_change_new('note');
				var dataIrrelevantList = {
					total:total,
					rows:irrelevantList
				}
				//排序问题第一阶段不插入
				dataIrrelevantList.rows.unshift({
					'account_cd':'<input placeholder="'+Note+'" id="account_cdL" type="text" class="rng" style="border:0;width:100%"/>',
					'divnm':'<input placeholder="'+Note+'" id="divnmL" type="text" class="rng" style="border:0;width:100%"/>',
					'divname_full':'<input placeholder="'+Note+'" id="divname_fullL" type="text" class="rng" style="border:0;width:100%"/>',
					'moveTableSign':true,
					});
				dataIrrelevantList.total = dataIrrelevantList.total + 1;
				//未关联表格
           		$('#irrelevantTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataIrrelevantList);
           		var relevancedList = data[$.getRequestDataName()]['relevancedList'];
           		rowright=data[$.getRequestDataName()]['relevancedList'];
				var total = relevancedList.length;
				var dataRelevancedList = {
					total:total,
					rows:relevancedList
				}
				//排序问题第一阶段不插入
				dataRelevancedList.rows.unshift({
					'account_cd':'<input placeholder="'+Note+'" id="account_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
					'divnm':'<input placeholder="'+Note+'" id="divnmR" type="text" class="rng right" style="border:0;width:100%"/>',
					'divname_full':'<input placeholder="'+Note+'" id="divname_fullR" type="text" class="rng right" style="border:0;width:100%"/>',
					'moveTableSign':true,
					});
				dataRelevancedList.total = dataRelevancedList.total + 1;
           		//已关联
           		$('#relevancedTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataRelevancedList);
//         		$('#irrelevantTab').datagrid('insertRow',{
//					index: 0,	// index start with 0
//					row: {
//						account_cd: '<input id="account_cdL" type="text" class="rng" style="border:0;width:100%"/>',
//						divnm: '<input id="divnmL" type="text" class="rng" style="border:0;width:100%"/>',
//						divname_full: '<input id="divname_fullL" type="text" class="rng" style="border:0;width:100%"/>'
//						}
//				});
				if (dataIrrelevantList.total > 1) {
					$('#irrelevantTab').datagrid('freezeRow', 0);
				}else {
                    $('#irrelevantTab').datagrid('freezeRow', -1);
                }
				$.irrelevantTab = rowleft;
//         		$('#relevancedTab').datagrid('insertRow',{
//					index: 0,	// index start with 0
//					row: {
//						account_cd: '<input id="account_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
//						divnm: '<input id="divnmR" type="text" class="rng right" style="border:0;width:100%"/>',
//						divname_full: '<input id="divname_fullR" type="text" class="rng right" style="border:0;width:100%"/>'
//						}
//				});
				if (dataRelevancedList.total > 1){
					$('#relevancedTab').datagrid('freezeRow',0);
				}else {
                    $('#relevancedTab').datagrid('freezeRow',-1);
                }
				$.relevancedTab = rowright;
				$('input.rng').on('keyup',function(event){
					if (event.keyCode == "13") {
						var values = this.value;
						var id = this.id;
						if($(this).hasClass('right')){
							var dataFil = rowright
							var gridname ='relevancedTab';
						}else{
							var dataFil = rowleft;
							var gridname ='irrelevantTab';
						}
						var that = this.value;
						search(dataFil,gridname,values,id,that)
					}					
				})
           	}else{
           		showInfoMsgHandler("DATA_IS_NOT_EXIST","ERROR","ERROR","master/employee_list.html","");
           	}
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
           $('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
		   $('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
       }
	});
}

function relevanceClient()
{
	var userID = "";
	if(urlPars.hasParm("userID"))
	{
		userID = urlPars.parm("userID");
	}
	var pars = {"userCD":userID,"lockFlg":$('#lockFlg').val()};
	var path = $.getAjaxPath()+"relevanceClient";
	pars.irrelevantList = $('#relevancedTab').datagrid('getData')['originalRows'].slice(1);
	$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	if(data[$.getRequestDataName()] > 0)
           	{
           		if(data[$.getRequestDataName()] == 999)
           		{
           			showErrorHandler("STATUS_VALIDATEPOWER_ERROR","ERROR","ERROR");
           		}else if(data[$.getRequestDataName()] == 996)
           		{
           			showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","master/employee_list.html");
           		}else{
           			//showErrorHandler("EXECUTE_SUCCESS","info","info");
           			window.history.back(-1);	
           		}
           	}else{
           		showErrorHandler("EXECUTE_FAIL","ERROR","ERROR");
           		location.reload();
           	}
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       }
	});
}

function search(dataFil,gridname,values,id,that){
	var dataFiltwo= new Object();
	dataFiltwo=clone(dataFil);
	if(id=='account_cdL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].account_cd).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	else if(id=='divnmL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].divnm).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='divname_fullL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].divname_full).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='account_cdR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].account_cd).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	else if(id=='divnmR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].divnm).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='divname_fullR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].divname_full).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	delete dataFiltwo.originalRows;
	dataFiltwo.total=dataFiltwo.length;
	//50数据检索页码为51problems
	var dg = $('#'+gridname);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	opts.pageSize = 50;
	opts.pageList = [50];
	pager.pagination('refresh', {
		pageNumber: opts.pageNumber,
		pageSize: opts.pageSize
	});
	////////////////////////
	if(gridname=='irrelevantTab'){
		//分页检索保证在第一页显示
		if ($('#irrelevantTab').datagrid('options').pageNumber > 1){
			$('a.l-btn.l-btn-small.l-btn-plain').find('span.l-btn-left.l-btn-icon-left').eq(0).click();
		}
		$('#irrelevantTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFiltwo);
		$('#irrelevantTab').datagrid('freezeRow',0);
	}else{
		if ($('#relevancedTab').datagrid('options').pageNumber > 1){
			$('a.l-btn.l-btn-small.l-btn-plain').find('span.l-btn-left.l-btn-icon-left').eq(5).click();
		}
		$('#relevancedTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFiltwo);
		$('#relevancedTab').datagrid('freezeRow',0);
	}
	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
	//保留搜索的value
	$('.rng#'+id).val(that);
	$('input.rng').unbind('keyup');
	//重新绑定事件
	$('input.rng').on('keyup',function(event){
		if (event.keyCode == "13") {
			var values = this.value;
			var id = this.id;
			if($(this).hasClass('right')){
				var dataFil = rowright
				var gridname ='relevancedTab';
			}else{
				var dataFil = rowleft;
				var gridname ='irrelevantTab';
			}
			var that = this.value;
			search(dataFil,gridname,values,id,that)
		}
	})
}
