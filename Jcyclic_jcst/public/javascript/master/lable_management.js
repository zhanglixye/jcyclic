$(function(){
	$('ul.change-btn li').on('click',function(){
		$(this).addClass('active');
		$(this).siblings('li').removeClass('active');
		//解除tooltip绑定
		$('#labeltest').removeClass('border_red');
		$('#labeltest').next('span').removeClass('border_red_left');
		$('#labeltest').closest('div').tooltip('destroy');
		//////////////////////////////////////////////////
	})
	$('.labeltypeul li').on('click',function(e){
		//解除tooltip绑定
		$('#labeltest').removeClass('border_red');
		$('#labeltest').next('span').removeClass('border_red_left');
		$('#labeltest').closest('div').tooltip('destroy');
		//////////////////////////////////////////////////
		var th = $(this);
		th.addClass('active');
		th.siblings('li').removeClass('active');
		var label_type=$(this).attr('value');
		initLabelList(label_type);
		
	})
	var label_type=$('.labeltypeul  li.active').attr('value');
	initDataGridHandler('label_management',50,'job_cd','top',true,'isHasFn');
	initLabelList(label_type);
})

function initLabelList(label_type){
	var path = $.getAjaxPath()+"LabelQuery";
	var label_level=$("ul.labellevel li.active").attr('value');//成本标签获取;
 	var Data = {
 		label_type:label_type,
 		label_level:label_level
 	};
	keep(path,Data,'1');//提交
}
function add(){
	//验证追加验证
	if($('#labeltest').val() == '' || $('#labeltest').val() == null){
		validate($('#labeltest').closest('div'),part_language_change_new('NODATE'));
		$('#labeltest').addClass('border_red');
		$('#labeltest').next('span').addClass('border_red_left');
		showErrorHandler("NOT_EMPTY","ERROR","ERROR");
		return;
	}else{
		//解除tooltip
		$('#labeltest').removeClass('border_red');
		$('#labeltest').next('span').removeClass('border_red_left');
		$('#labeltest').closest('div').tooltip('destroy');
	}
	if(!validataRequired())
	{
		return ;
	}
	var label_text=$("#labeltest").val();
	var label_type=$('.labeltypeul  li.active').attr('value');
	var label_level=$("ul.labellevel li.active").attr('value');//成本标签获取;
	var path = $.getAjaxPath()+"LabelAdd";
 	var Data = {
 		"labelmst": {
             	"label_level": label_level,
              	"label_text": label_text,
              	"label_type": label_type
              }
 	};
	keep(path,Data,null)//提交
}
function labeldelete() {
	var row = $('#label_management').datagrid('getSelections');
		//var id = row[0].label_id;
		if (row.length==0){ 
			showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
			return false;
		}

	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("SURE_DELETE");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
		var row = $('#label_management').datagrid('getSelections');
		var id = row[0].label_id;
		if (typeof(id) == "undefined"){ 
		showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
			return false;
		}
//		if(!showConfirmMsgHandler("SURE_DELETE")){
//			return false;
//		}
	  	var row = $('#label_management').datagrid('getSelections');
	  	var label_id=[];
	if(row == null || row == '') {
		showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
	} else {
		for(var i=0;i<row.length;i++){
			var labelid = {
             	"label_id": row[i].label_id,
             	"lock_flg": row[i].lock_flg
 			};
			label_id.push(labelid);
		}
	
		var path = $.getAjaxPath()+"LabelDelete";
	 	var Data = {
	        "lableList": label_id	              
	 	};
		keep(path,Data,null);//提交
		}
	}
	});
}
		

function labeladd(){
		var row = $('#label_management').datagrid('getSelections');
		if (row.length==0){ 
			showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
			return false;
		}
		var id = row[0].label_id;
		if (typeof(id) == "undefined"){ 
		showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
		return false;
		}
	  	var label_id=[];
	  	var selectRFlag = true;
		if((row == null || row == '') && editIndex == undefined) {
			showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
		} else {
			if(editIndex != undefined){
				$('#label_management').datagrid('endEdit', editIndex);
				for(var i=0;i<row.length;i++){
					var rowIndex = $('#label_management').datagrid('getRowIndex',row[i]);
					$('#label_management').datagrid('endEdit', rowIndex);
					var label_text = row[i].label_text;
					if(typeof label_text == "undefined" || label_text == null || label_text == ""){
						//showErrorHandler("LABEL_TEXT_NULL","ERROR","ERROR");
						$('#label_management').datagrid('beginEdit', rowIndex);
						$('#label_management').datagrid('getPanel').find('.datagrid-body td[field="label_text"] input.datagrid-editable-input').addClass('border_red');
						validate($('#label_management').datagrid('getPanel').find('.datagrid-body td[field="label_text"] input.datagrid-editable-input'),part_language_change_new('NODATE'));
						selectRFlag = false;
						//return;
					}
				}
				if(!selectRFlag){
					showErrorHandler("NOT_EMPTY","ERROR","ERROR");
					return;
				}
//				var dataEdit = $('#label_management').datagrid('getRows')[editIndex];
//				var o = {
//					"label_id": dataEdit.label_id,
//	             	"label_text": dataEdit.label_text
//				}
//				label_id.push(o);
			}
			for(var i=0;i<row.length;i++){
				var labelid = {
	             	"label_id": row[i].label_id,
	             	"label_text": row[i].label_text,
	             	"lock_flg": row[i].lock_flg
	 			};
				label_id.push(labelid);
			}
		
			var path = $.getAjaxPath()+"LabelUpdate";
		 	var Data = {
		             	"lableList": label_id	              
		 		};
			keep(path,Data,null);//提交
			}

}

function keep(path,Data,flag){
	var keeppath = path;
 	var keepData = Data;
 	$.ajax({
		  type: "POST",
		  url:path,
		  contentType:"application/json",
		  data:JSON.stringify(Data),
		  dataType:"JSON",
		  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		  success:function(data){
		           window.editIndex=undefined;
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           if(data.data==-1){
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/master/label_management.html");
		           	return false;
		           }
		           	//初始化页面
		           	if(flag=='1'){
				        var total = data[$.getRequestDataName()].lableList.length;
//						if(total<=0){
//							showErrorHandler('NO_FOUND_CLMST','info','info');
//						}
						//标签类型 2个人 1管理
						var level = data[$.getRequestDataName()].label_level;
						if(level!='1'){
							$('#admin_lable').addClass('hidden');
							$('#my_lable').addClass('active');
						}
						for(var i =0;i<total;i++){
						  var label_level =getGridLanguage("label_level",data[$.getRequestDataName()].lableList[i]["label_level"]);	
						  data[$.getRequestDataName()].lableList[i]["label_level"]=label_level;
						}
						//注释国际化
						Note = part_language_change_new('note');
						var dataFil = {
							total:total,
							rows:data[$.getRequestDataName()].lableList
						}
						//排序问题第一阶段不插入
						dataFil.rows.unshift(
							{
								'label_text':  '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
								'addusercd':   '<input placeholder="'+Note+'" maxlength="20" id="addusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
								'label_level': '<input placeholder="'+Note+'" maxlength="20" id="label_level" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
								'updusercd':   '<input placeholder="'+Note+'" maxlength="20" id="updusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
							}
						);
//						$('#label_management').datagrid('insertRow',{
//							index: 0,	// index start with 0
//							row: {
//								label_text: '<input maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%"/>',
//								}
//						});
						dataFil.total = dataFil.total + 1;
						$('#label_management').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
						//lable_management更改bug  -- 逆序问题
						if(label_ma_order == 'desc'){
							var indexB = $('#label_management').datagrid('getData').rows.length - 1;
							$('#label_management').datagrid('deleteRow',indexB);
					  		$('#label_management').datagrid('insertRow',{
								index: 0,	// index start with 0
								row: {
									label_text:  '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
									addusercd:   '<input placeholder="'+Note+'" maxlength="20" id="addusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
									label_level: '<input placeholder="'+Note+'" maxlength="20" id="label_level" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
									updusercd:   '<input placeholder="'+Note+'" maxlength="20" id="updusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
								}
							});
							easyInsertH('label_management');
						}
						///////////////////////////////////////////
						$('#label_management').datagrid('freezeRow',0);
						$.datafill = dataFil;
						search(dataFil);
						if(dataFil.total == 1){
							$('.switch_table_none').removeClass('hidden');
							$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
						}else{
							$('.switch_table_none').addClass('hidden');
						}
					}
		           	//提交和删除
		           	else{
		           		
		           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           		var fla = data[$.getRequestDataName()];
		           		if(fla==-2){
		           			showErrorHandler("ALREAD_HAVE_SAME_TEXT","info","info");
		           			$('#labeltest').addClass('border_red');
							$('#labeltest').next('span').addClass('border_red_left');
		           			return false;
		           		}
		           		if(fla==-3){
		           			showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		           			return false;
		           		}
		           		showErrorHandler('EXECUTE_SUCCESS','info','info');
		           		var label_level=$('.labeltypeul  li.active').attr('value');
		           		initLabelList(label_level);
		           		
		           	}
		           		

		           }else{
		            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
		           }
		           $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		        },
		       error:function(data){
		       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		        }
		       
		 		});
}
function search(dataFil){
	$('input.rng').on('keyup',function(event){
	if (event.keyCode == "13") {
		var values = this.value;
		var id = this.id;
	
		var dataFiltwo= new Object();
		dataFiltwo=clone(dataFil);
		//将dataFiltwo.rows设置为分页前数据
		dataFiltwo.rows=dataFiltwo.originalRows;
		if(id=='label_id'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].label_id).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}
		else if(id=='label_level'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].label_level).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}else if(id=='label_text'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].label_text).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}else if(id=='upddate'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].upddate).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}else if(id=='adddate'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].adddate).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}else if(id=='addusercd'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].addusercd).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}
		}else if(id=='updusercd'){
			for(var i=1;i<dataFiltwo.rows.length;i++){
				if(String(dataFiltwo.rows[i].updusercd).indexOf(values)==-1){
			        dataFiltwo.rows.splice(i,1);
			        i--;
			    }
			}			
		}
		delete dataFiltwo.originalRows;
		dataFiltwo.total=dataFiltwo.rows.length;
		$('#label_management').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFiltwo);
		//排序问题第三阶段检索修理
		if(label_ma_order == 'desc'){
			var indexB = $('#label_management').datagrid('getData').rows.length - 1;
			$('#label_management').datagrid('deleteRow',indexB);
	  		$('#label_management').datagrid('insertRow',{
				index: 0,	// index start with 0
				row: {
					label_text:  '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
					addusercd:   '<input placeholder="'+Note+'" maxlength="20" id="addusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
					label_level: '<input placeholder="'+Note+'" maxlength="20" id="label_level" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
					updusercd:   '<input placeholder="'+Note+'" maxlength="20" id="updusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
				}
			});
			easyInsertH('label_management');
		}		
		if(dataFiltwo.total == 1){
			$('.switch_table_none').removeClass('hidden');
			$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
		}else{
			$('.switch_table_none').addClass('hidden');
		}
		//保留搜索的value
		$('.rng#'+id).val(this.value)
		$('#label_management').datagrid('freezeRow',0);
		search(dataFil);
	}
})
}
 