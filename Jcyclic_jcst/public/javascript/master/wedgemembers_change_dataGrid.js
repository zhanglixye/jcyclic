$(function(){
	initDataGridHandler("wedgemembersleft",10,"check","bottom",true,"isHasFn");
	initDataGridHandler("wedgemembersright",10,"check","bottom",true,"isHasFn");
	initform();
})
var rowright = [];
var rowrightlist = [];
var rowleft;
var locknum;
function initform(){
		$("#cldivcd").text(urlPars.parm('cldivcd'));
		$("#divname_full").text(sessionStorage.getItem("divname_full"));
		var cldivcd  = urlPars.parm('cldivcd');
		//正则验证不能输入日语
		var reg = /^[0-9]*$/;
        var r = cldivcd.match(reg);     
        if(r==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/suppliers_list.html");
			return false;    
        }
		var path = $.getAjaxPath()+"wedgemembersInit";
		var pars = {"cldivcd":cldivcd};
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
		   $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		//初始化转圈问题
	            if(data.data==null){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/suppliers_list.html");
		           	return false;
	            }
				var totalone = data[$.getRequestDataName()].wedgemembersleft.length;
				locknum = data[$.getRequestDataName()].lock_flg;
				rowleft=data[$.getRequestDataName()].wedgemembersleft;
				//注释国际化
				Note = part_language_change_new('note');
				var wedgemembersleft = {
					total:totalone,
					rows:data[$.getRequestDataName()].wedgemembersleft
				}
				//排序问题第一阶段不插入
				wedgemembersleft.rows.unshift({
					'company_name':'<input placeholder="'+Note+'" id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>',
					'user_cd':'<input placeholder="'+Note+'" id="user_cdL" type="text" class="rng" style="border:0;width:100%"/>',
					'user_name':'<input placeholder="'+Note+'" id="user_nameL" type="text" class="rng" style="border:0;width:100%"/>',
					'level':'<input placeholder="'+Note+'" id="levelL" type="text" class="rng" style="border:0;width:100%"/>'
					});
				wedgemembersleft.total = wedgemembersleft.total + 1;
				$('#wedgemembersleft').datagrid({loadFilter: pagerFilter}).datagrid('loadData',wedgemembersleft);// 				$('#wedgemembersleft').datagrid('insertRow',{
//							index: 0,	// index start with 0
//							row: {
//								company_name: '<input id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>',
//								user_cd: '<input id="user_cdL" type="text" class="rng" style="border:0;width:100%"/>',
//								user_name: '<input id="user_nameL" type="text" class="rng" style="border:0;width:100%"/>',
//								level: '<input id="levelL" type="text" class="rng" style="border:0;width:100%"/>',
//								}
//						});
				$('#wedgemembersleft').datagrid('freezeRow',0);
				$.rowleft = rowleft;
				//search(wedgemembersleft,'wedgemembersleft');
				//右侧表格
				var totaltwo = data[$.getRequestDataName()].wedgemembersright.length;
				rowright=data[$.getRequestDataName()].wedgemembersright;
				rowrightlist =rowright.slice(0);
				var wedgemembersright = {
					total:totaltwo,
					rows:data[$.getRequestDataName()].wedgemembersright
				}
				//排序问题第一阶段不插入
				wedgemembersright.rows.unshift({
					'company_name':'<input placeholder="'+Note+'" id="company_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
					'user_cd':'<input placeholder="'+Note+'" id="user_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
					'user_name':'<input placeholder="'+Note+'" id="user_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
					'level':'<input placeholder="'+Note+'" id="levelR" type="text" class="rng right" style="border:0;width:100%"/>'
					});
				wedgemembersright.total = wedgemembersright.total + 1;
				$('#wedgemembersright').datagrid({loadFilter: pagerFilter}).datagrid('loadData',wedgemembersright);
//				$('#wedgemembersright').datagrid('insertRow',{
//							index: 0,	// index start with 0
//							row: {
//								company_name: '<input id="company_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
//								user_cd: '<input id="user_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
//								user_name: '<input id="user_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
//								level: '<input id="levelR" type="text" class="rng right" style="border:0;width:100%"/>',
//								}
//						});
				$('#wedgemembersright').datagrid('freezeRow',0);
				$.rowright = rowright;
				$('input.rng').on('keyup',function(event){
					if (event.keyCode == "13") {
						var values = this.value;
						var id = this.id;
						if($(this).hasClass('right')){
							var dataFil = rowright
							var gridname ='wedgemembersright';
						}else{
							var dataFil = rowleft;
							var gridname ='wedgemembersleft';
						}
						var that = this.value;
						search(dataFil,gridname,values,id,that)
					}				
				})
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
           $('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
           $('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
}
function back() {
		window.location.href = '../master/suppliers_list.html';
	};
		
function change(){

		var cldivcd = urlPars.parm('cldivcd')
		var client_flg  = sessionStorage.getItem("client_flg");
		if(client_flg!='◯'){
			showErrorHandler("ORDER_CREATE_ERROR","info","info");
			return;
		}
		var userID = "";
		if(urlPars.hasParm("userID"))
		{
			userID = urlPars.parm("userID");
		}
		var pars = {"cldivcd":cldivcd,"lock_flg":locknum};
		var right = rowrightlist;
//		//过滤掉第一行筛选
//		if(right.length>0){
//			for(var i=0;i<right.length;i++){
//				if(String(right[i].company_name).indexOf('<input')!=-1){
//			        right.splice(i,1);
//			        i--;
//		    	}
//			}
//		}
		pars.wedgemembersright = right;
	//	return;
		var path = $.getAjaxPath()+"wedgememberschange";
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	if(data.data==-1){
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/master/suppliers_list.html");
	           	return false;
		    }
           	if(data[$.getRequestDataName()] > 0)
           	{
//         		showErrorHandler("EXECUTE_SUCCESS","info","info");
           		back();
           	}else{
           		showErrorHandler("EXECUTE_FAIL","ERROR","ERROR");
           	}
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       }
	});
//	sessionStorage.clear();
			sessionStorage.removeItem('cldivcd');
			sessionStorage.removeItem('account_cd');
			sessionStorage.removeItem('divnm');
			sessionStorage.removeItem('client_flg');
			sessionStorage.removeItem('contra_flg');
			sessionStorage.removeItem('pay_flg');
			sessionStorage.removeItem('hdy_flg');
			sessionStorage.removeItem('clientall');
			sessionStorage.removeItem('del_flg0');
			sessionStorage.removeItem('del_flg1');
			sessionStorage.removeItem('del_flgall');
}
function lefttoright(){//右侧表格被添加
	var rowselect = $('#wedgemembersleft').datagrid('getSelections');
	if(isNaN(rowselect[0].member_id) == true){
		var rowselect = rowselect.slice(1);
	}
	if(String(rowselect[0].company_name).indexOf("<")!=-1){
		showErrorHandler("WEDGEMEMBERSNO","info","info");
		return;
	}
	if(rowselect.length>0){
		for(var i=0;i<rowselect.length;i++){
			rowrightlist.push(rowselect[i]);
		}		
	}
	changeClientLeftOrRight('left','wedgemembersleft','wedgemembersright')
	easyInsertH('wedgemembersleft');
	easyInsertH('wedgemembersright');
}
function righttoleft(){//右侧表格被减少
	var rowselect = $('#wedgemembersright').datagrid('getSelections');	
	if(isNaN(rowselect[0].member_id) == true){
		var rowselect = rowselect.slice(1);
	}
	if(String(rowselect[0].company_name).indexOf("<")!=-1){
		showErrorHandler("WEDGEMEMBERSNO","info","info");
		return;
	}
	if(rowselect.length>0){
		for(var j=0;j<rowselect.length;j++){
			for(var i=0;i<rowrightlist.length;i++){
				var uscd = rowselect[j].user_cd;
				if(String(rowrightlist[i].user_cd).indexOf(uscd)!=-1){
			        rowrightlist.splice(i,1);
			        i--;
			    }
			}	
		}
	}
	var ss = rowrightlist;
	changeClientLeftOrRight('right','wedgemembersleft','wedgemembersright')
	easyInsertH('wedgemembersleft');
	easyInsertH('wedgemembersright');
}

function search(dataFil,gridname,values,id,that){
//	$('input.rng').on('change',function(){
//	var values = this.value;
//	var id = this.id;

	var dataFiltwo= new Object();
	dataFiltwo=clone(dataFil);
	if(id=='company_nameL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].company_name).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	else if(id=='user_cdL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].user_cd).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='user_nameL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].user_name).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='levelL'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].level).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='company_nameR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].company_name).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	else if(id=='user_cdR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].user_cd).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='user_nameR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].user_name).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}else if(id=='levelR'){
		for(var i=1;i<dataFiltwo.length;i++){
			if(String(dataFiltwo[i].level).indexOf(values)==-1){
		        dataFiltwo.splice(i,1);
		        i--;
		    }
		}
	}
	delete dataFiltwo.originalRows;
	dataFiltwo.total=dataFiltwo.length;
	if(gridname=='wedgemembersleft'){
		$('#wedgemembersleft').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFiltwo);
		//排序问题第三阶段检索修理
		if(label_ma_order == 'desc'){
			var indexB = $('#wedgemembersleft').datagrid('getData').rows.length - 1;
			$('#wedgemembersleft').datagrid('deleteRow',indexB);
	  		$('#wedgemembersleft').datagrid('insertRow',{
				index: 0,	// index start with 0
				row: {
					company_name: '<input placeholder="'+Note+'" id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>',
					user_cd: '<input placeholder="'+Note+'" id="user_cdL" type="text" class="rng" style="border:0;width:100%"/>',
					user_name: '<input placeholder="'+Note+'" id="user_nameL" type="text" class="rng" style="border:0;width:100%"/>',
					level: '<input placeholder="'+Note+'" id="levelL" type="text" class="rng" style="border:0;width:100%"/>',
				}
			});
			easyInsertH('wedgemembersleft');
		}
		$('#wedgemembersleft').datagrid('freezeRow',0);
	}else{
		$('#wedgemembersright').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFiltwo);
		//排序问题第三阶段检索修理
		if(label_ma_order == 'desc'){
			var indexB = $('#wedgemembersright').datagrid('getData').rows.length - 1;
			$('#wedgemembersright').datagrid('deleteRow',indexB);
	  		$('#wedgemembersright').datagrid('insertRow',{
				index: 0,	// index start with 0
				row: {
					company_name: '<input placeholder="'+Note+'" id="company_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
					user_cd: '<input placeholder="'+Note+'" id="user_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
					user_name: '<input placeholder="'+Note+'" id="user_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
					level: '<input placeholder="'+Note+'" id="levelR" type="text" class="rng right" style="border:0;width:100%"/>',
				}
			});
			easyInsertH('wedgemembersright');
		}
		$('#wedgemembersright').datagrid('freezeRow',0);
	}
	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
	//保留搜索的value
	$('.rng#'+id).val(that)
	//事件重新绑定
	$('input.rng').on('keyup',function(event){
		if (event.keyCode == "13") {
			var values = this.value;
			var id = this.id;
			if($(this).hasClass('right')){
				var dataFil = rowright
				var gridname ='wedgemembersright';
			}else{
				var dataFil = rowleft;
				var gridname ='wedgemembersleft';
			}
			var that = this.value;
			search(dataFil,gridname,values,id,that)
		}				
	})
}