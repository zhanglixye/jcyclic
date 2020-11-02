/**
 * 方法名 settingPageStyle
 * 方法的说明 设置分页默认样式
 * @param 
 * @return Object
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
function settingPageStyle() {
	obj = {
		pageDefault: function(str) {
			var arr_info_text = str.split(/[^0-9]/ig);
			var arr_info_textNew = [];
			for(var i = 0; i < arr_info_text.length; i++) {
				if(arr_info_text[i] != '') {
					arr_info_textNew.push(arr_info_text[i]);
				}
			}
			//easyui过滤筛选是算筛选格的数量的解决方案
			if($("#label_management").exist()){
				arr_info_textNew[2] = Number(arr_info_textNew[2])-1;
				arr_info_textNew[1] = Number(arr_info_textNew[1])-1;
				if(arr_info_textNew[1] == 0 || arr_info_textNew[2] == 0){
					arr_info_textNew[0] = 0;
				}
				var Note = part_language_change_new('note');
				var rowData = $("#label_management").datagrid("getRows")[0];
				if(rowData.label_text == '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>'){
					arr_info_textNew[0] = arr_info_textNew[0];
				}else{
					arr_info_textNew[0] = Number(arr_info_textNew[0])-1;
				}
			}else if($("#wedgemembersleft").exist()){
				arr_info_textNew[2] = Number(arr_info_textNew[2])-1;
				arr_info_textNew[1] = Number(arr_info_textNew[1])-1;
				if(arr_info_textNew[1] == 0 || arr_info_textNew[2] == 0){
					arr_info_textNew[0] = 0;
				}
				var rowData = $("#wedgemembersleft").datagrid("getRows")[0];
				if(rowData.company_name == '<input placeholder="'+Note+'" id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>'){
					arr_info_textNew[0] = arr_info_textNew[0];
				}else{
					arr_info_textNew[0] = Number(arr_info_textNew[0])-1;
				}
			}else if($("#irrelevantTab").exist()){
				arr_info_textNew[2] = Number(arr_info_textNew[2])-1;
				arr_info_textNew[1] = Number(arr_info_textNew[1])-1;
				if(arr_info_textNew[1] == 0 || arr_info_textNew[2] == 0){
					arr_info_textNew[0] = 0;
				}
				var rowData = $("#irrelevantTab").datagrid("getRows")[0];
				if(rowData.account_cd == '<input placeholder="'+Note+'" id="account_cdL" type="text" class="rng" style="border:0;width:100%"/>'){
					arr_info_textNew[0] = arr_info_textNew[0];
				}else{
					arr_info_textNew[0] = Number(arr_info_textNew[0])-1;
				}
			}
			var info_return = part_language_change_new('table_quan')+arr_info_textNew[2] + part_language_change_new('table_center') + ' ' + arr_info_textNew[0] + part_language_change_new('table_jian') + arr_info_textNew[1] + part_language_change_new('table_intro');			
			return info_return;
		}
	};
	return obj;
}
/**
 * 方法名 initDataGridHandler
 * 方法的说明 datagrid初始化函数
 * @param String tabId 表格ID,int pageSizeJ 每页显示条数,String sortNameJ 排序字段名,
 * @param String pagePositionJ 分页显示位置,Boolean paginationJ 是否分页,String isHasFn 是否有onLoadSuccess,frozen 是否冻结列
 * @return 
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
function initDataGridHandler(tabId, pageSizeJ, sortNameJ, pagePositionJ, paginationJ, isHasFn, dataColumns, fitColumn,frozen) {
	if( tabId != "cost_list_Two" && tabId != "job_login_list" && tabId != "timesheet_list" )
	{
		//如果有分页每页显示50
		if(paginationJ)
		{
			pageSizeJ = 50;
		}
	}
	//datagrid国际化 参数存储
	var parameter_storage = {
		tabId:tabId,
		pageSizeJ:pageSizeJ,
		sortNameJ:sortNameJ,
		pagePositionJ:pagePositionJ,
		paginationJ:paginationJ,
		isHasFn:isHasFn,
		dataColumns:dataColumns,
		fitColumn:fitColumn,
		frozen:frozen
	}
	
	//表格动态切换title语言
	//只有在JOB一览，原价一览，timesheet一览时重新赋值
	//start by wangyan 2018.09.21
	if( tabId == "cost_list_Two"||tabId == "job_login_list"|| tabId == "timesheet_list" )
	{
		dataColumns = getTitleByLanguage(dataColumns);
		if(dataColumns == "")
		{
			showErrorHandler("PAGE_INIT_FAIL","ERROR","ERROR");
			return ;
		}
	}

	//end
	parameter_storage = JSON.stringify(parameter_storage)
	localStorage.setItem('parameter_storage',parameter_storage);
	if(typeof tabId == 'string'){
		var tabIdDom=$('#' + tabId);
	}else{
		var tabIdDom=tabId;
		var tabId = 'cost_list';
	}
	//job一览与原价一览多选
	var selectJobCost = false;
	if(tabId != 'job_login_list' && tabId != 'cost_list_Two' && tabId != 'wedgemembersleft' && tabId != 'wedgemembersright' && tabId != 'label_management' && tabId != 'relevancedTab' && tabId != 'irrelevantTab'){
		selectJobCost = true;
	}
	if(tabId == 'employeeWindowTab'){
		var href = parent.location.href;
		if(href.match(/\/job_registration.html|\/job_update.html/)){
			selectJobCost = false;
		}
	}
	//如果true则将第一列冻结
	var frozenColumns = [];
	if(frozen){
		if(dataColumns.length > 0){
			var deleteArr = dataColumns.splice(0,1);
			deleteArr[0].sortable = true;
			deleteArr[0].halign = 'center';
			deleteArr[0].align = 'left';
			if(tabId == 'job_login_list'){
				var date_type_frozen_arr = ["dlvday","jsflaglanguagejp","assignflglanguagejp","reqflglanguagejp","recflglanguagejp","invflglanguagejp","costendflglanguagezc","adddate","saleadddate","saleadmitdate","dlvmon"];
				var cost_frozen_arr = ["saleamt","vatamt","reqamt","plancostamt","plancostamt","costTotalAmt","costVatTotal","tax2","tax3","taxTotal","profit","profitRate","plansale"];
				if(date_type_frozen_arr.indexOf(deleteArr[0].field) != -1){
					deleteArr[0].align = 'center';
				}else if(cost_frozen_arr.indexOf(deleteArr[0].field) != -1){
					deleteArr[0].align = 'right';
				}
				var check = { field:'ck',checkbox:true};
				deleteArr.unshift(check);
			}
			if(tabId == 'cost_list_Two'){
				var date_type_frozen_arr = ["dalday","statusjp","adddate","payreqdate","confirmdate","outputflg","payflgjp","invoicedate","paydlyday","plandlvday","payhopedate","planpaydate","paydate","lenddate"];
			    var cost_frozen_arr = ["saleamt","amt","vatamt","payamt"];
				if(date_type_frozen_arr.indexOf(deleteArr[0].field) != -1){
					deleteArr[0].align = 'center';
				}else if(cost_frozen_arr.indexOf(deleteArr[0].field) != -1){
					deleteArr[0].align = 'right';
				}
				var check = { field:'ck',checkbox:true};
				deleteArr.unshift(check);
			}			
			frozenColumns.push(deleteArr);	
		}
			
	}
	var columnsJ = getColumnsByDataGridId(tabId, dataColumns,frozen);	
	//这是我新加的测试
	var editIndex = undefined;
	window.editIndex=undefined;
	
	function endEditing(rowIndex) {// 该方法用于关闭上一个焦点的editing状态
		
		if (editIndex == undefined) {
			return true
		}
		if(editIndex==rowIndex){
			if ($('#'+tabId).datagrid('validateRow', editIndex)) {
				$('#'+tabId).datagrid('endEdit', editIndex);
				editIndex = undefined;
				//新加的全局变量 判断是否编辑的索引
				window.editIndex = editIndex;
			}
			return false;
		}
		if ($('#'+tabId).datagrid('validateRow', editIndex)) {
			$('#'+tabId).datagrid('endEdit', editIndex);
			editIndex = undefined;
			//新加的全局变量 判断是否编辑的索引
			window.editIndex = editIndex;
			return true;
		}		
	}
	if(fitColumn == undefined) {
		fitColumn = true;
	}
	var pageList = [];
	var Note = part_language_change_new('note');
	pageList.push(pageSizeJ);
	window.label_ma_order = null;
	//pageSize 默认分页50条，忽略传递过来的参数
	tabIdDom.datagrid({
		striped: true, //是否显示斑马线效果
		fitColumns: fitColumn, // 是否列自适应
		emptyMsg:"<span class='font_color_red'>无数据</span>",
		loadMsg: '努力展开中...',
		checkOnSelect: true,
		columns: columnsJ,
		pagination: paginationJ,
		pageSize: pageSizeJ,
		pageList: pageList,
		pageNumber: 1,
		pagePosition: pagePositionJ, //是否有底部
		//sortName: sortNameJ,
		remoteSort:false,
		frozenColumns:frozenColumns,
		singleSelect: selectJobCost,
		onSortColumn: function (sort, order) {
            if(tabId == 'label_management'){
            	var Note = part_language_change_new('note');
            	//排序问题第二阶段逆序删除插入操作
            	window.label_ma_order= order;
            	if(order == 'desc'){
            		var indexB = $('#' + tabId).datagrid('getData').rows.length - 1;
            		$('#' + tabId).datagrid('deleteRow',indexB);
              		$('#label_management').datagrid('insertRow',{
						index: 0,	// index start with 0
						row: {
							label_text: '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
							addusercd:   '<input placeholder="'+Note+'" maxlength="20" id="addusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
							label_level: '<input placeholder="'+Note+'" maxlength="20" id="label_level" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
							updusercd:   '<input placeholder="'+Note+'" maxlength="20" id="updusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>',
						}
					});
					easyInsertH(tabId);
            	}
            	$('#label_management').datagrid('freezeRow',0);
            	search($.datafill);
            }
            if(tabId == 'wedgemembersleft' || tabId == 'wedgemembersright'){
            	var Note = part_language_change_new('note');
            	//排序问题第二阶段逆序删除插入操作
            	window.label_ma_order= order;
            	if(order == 'desc'){
            		var indexB = $('#' + tabId).datagrid('getData').rows.length - 1;
            		$('#' + tabId).datagrid('deleteRow',indexB);
            		if(tabId == 'wedgemembersleft'){
            			var wRow = {
            				company_name: '<input placeholder="'+Note+'" id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>',
							user_cd: '<input placeholder="'+Note+'" id="user_cdL" type="text" class="rng" style="border:0;width:100%"/>',
							user_name: '<input placeholder="'+Note+'" id="user_nameL" type="text" class="rng" style="border:0;width:100%"/>',
							level: '<input placeholder="'+Note+'" id="levelL" type="text" class="rng" style="border:0;width:100%"/>',
            			}
            		}else{
            			var wRow = {
							company_name: '<input placeholder="'+Note+'" id="company_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
							user_cd: '<input placeholder="'+Note+'" id="user_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
							user_name: '<input placeholder="'+Note+'" id="user_nameR" type="text" class="rng right" style="border:0;width:100%"/>',
							level: '<input placeholder="'+Note+'" id="levelR" type="text" class="rng right" style="border:0;width:100%"/>',
						}
            		}
              		$('#' + tabId).datagrid('insertRow',{
						index: 0,	// index start with 0
						row: wRow
					});
					easyInsertH(tabId);
            	}
            	$('#' + tabId).datagrid('freezeRow',0);
            	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
				$('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
            	$('input.rng').on('keyup',function(event){
					if (event.keyCode == "13") {
						var values = this.value;
						var id = this.id;
						if($(this).hasClass('right')){
							var dataFil = rowright
							var gridname ='wedgemembersright';
							search($.rowright,gridname,values,id)
						}else{
							var dataFil = rowleft;
							var gridname ='wedgemembersleft';
							search($.rowleft,gridname,values,id)
						}						
					}				
				})
            }
            if(tabId == 'irrelevantTab' || tabId == 'relevancedTab'){
            	var Note = part_language_change_new('note');
            	//排序问题第二阶段逆序删除插入操作
            	window.label_ma_order= order;
            	if(order == 'desc'){
            		var indexB = $('#' + tabId).datagrid('getData').rows.length - 1;
            		$('#' + tabId).datagrid('deleteRow',indexB);
            		if(tabId == 'irrelevantTab'){
            			var wRow = {
							account_cd: '<input placeholder="'+Note+'" id="account_cdL" type="text" class="rng" style="border:0;width:100%"/>',
							divnm: '<input placeholder="'+Note+'" id="divnmL" type="text" class="rng" style="border:0;width:100%"/>',
							divname_full: '<input placeholder="'+Note+'" id="divname_fullL" type="text" class="rng" style="border:0;width:100%"/>'
						}
            		}else{
            			var wRow = {
							account_cd: '<input placeholder="'+Note+'" id="account_cdR" type="text" class="rng right" style="border:0;width:100%"/>',
							divnm: '<input placeholder="'+Note+'" id="divnmR" type="text" class="rng right" style="border:0;width:100%"/>',
							divname_full: '<input placeholder="'+Note+'" id="divname_fullR" type="text" class="rng right" style="border:0;width:100%"/>'
						}
            		}
              		$('#' + tabId).datagrid('insertRow',{
						index: 0,	// index start with 0
						row: wRow
					});
					easyInsertH(tabId);
            	}
            	$('#' + tabId).datagrid('freezeRow',0);
            	$('tr[datagrid-row-index="0"] div input[type="checkbox"]').nextAll().remove();
				$('tr[datagrid-row-index="0"] div input[type="checkbox"]').after("<div>─</div>");
            	$('input.rng').on('keyup',function(event){
					if (event.keyCode == "13") {
						var values = this.value;
						var id = this.id;
						if($(this).hasClass('right')){
							//var dataFil = rowright
							var gridname ='relevancedTab';
							search($.relevancedTab,gridname,values,id)
						}else{
							//var dataFil = rowleft;
							var gridname ='irrelevantTab';
							search($.irrelevantTab,gridname,values,id)
						}						
					}				
				})
            }
            
            if(tabId == 'job_login_list' || tabId == 'cost_list_Two'){
            	//筛选时table表格部分隐藏bug
            	if($('.datagrid-view').hasClass('top-40')){
            		var heiTop = $('.datagrid-view').height()+40;
            		$('.datagrid-view').height(heiTop);
            	}
            }
            
        },
		//右键事件
		onRowContextMenu: function(e, rowIndex, rowData) { //右键时触发事件  
			if(tabId == "cost_list_Two") {
				//三个参数：rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
				e.preventDefault(); //阻止浏览器捕获右键事件  
				// $(this).datagrid("clearSelections"); //取消所有选中项  
				$(this).datagrid("selectRow", rowIndex); //根据索引选中该行  
				var rowDataAll = $(this).datagrid('getSelections'); //获取所有选择的数据
				if(rowDataAll.length > 1) {
					menuLoad();
					var menu_height = $('#menu2').height() + 6;
					//选中多条的时候
					$('#menu2').menu('show', {
						//显示右键菜单  
						left: e.pageX, //在鼠标点击处显示菜单  
						top: e.pageY - menu_height
					});					
				} else {
					menuLoad();
					var menu_height = $('#menu').height() + 6;
					//选中单条的时候
					$('#menu').menu('show', {
						//显示右键菜单  
						left: e.pageX, //在鼠标点击处显示菜单  
						top: e.pageY - menu_height
					});
				}
			}
			if(tabId == "job_login_list"){
			  //三个参数：rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
	          e.preventDefault(); //阻止浏览器捕获右键事件  
	          // $(this).datagrid("clearSelections"); //取消所有选中项  
	          $(this).datagrid("selectRow", rowIndex); //根据索引选中该行  
	          var rowDataAll = $(this).datagrid('getSelections');//获取所有选择的数据
	          if(rowDataAll.length > 1){
	          	menuInitMore();//调用job_list_service中的菜单初始化方法
	          	var menu_height = $('#menu2').height() + 6;
				$('#menu2').menu('show', {  
		               //显示右键菜单  
		               left: e.pageX,//在鼠标点击处显示菜单  
		               top: e.pageY - menu_height  
		        });			        
	          }else{
	          	menuInit();//调用job_list_service中的菜单初始化方法
	          	var menu_height = $('#menu').height() + 6;
	          	//选中单条的时候
	          	$('#menu').menu('show', {  
	               //显示右键菜单  
	               left: e.pageX,//在鼠标点击处显示菜单  
	               top: e.pageY	- menu_height
	            }); 	           
	          } 
			}
		},
		//双击事件
		onDblClickRow: function(rowIndex,field,value) {
			if(tabId == "salescategory_T") {
				var row = $('#salescategory_T').datagrid('getData').rows[rowIndex];
				if(row.decide == null || row.decide == "0") {
					if(obj_T.editRow != undefined) {
						$('#salescategory_T').datagrid('endEdit', obj_T.editRow);
						$('#salescategory_T').datagrid('beginEdit', rowIndex);
						obj_T.editRow = rowIndex;
					}
					if(obj_T.editRow == undefined) {
						$('#salescategory_T').datagrid('beginEdit', rowIndex);
						obj_T.editRow = rowIndex;
					}
					$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
				        inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
				            keyup: function test(event) {
				            	var newValue = event.target.value;
				                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
				            }
				        })
				    });
				}
			}
			if(tabId == "salescategory") {
				var row = $('#salescategory').datagrid('getData').rows[rowIndex];
				if(row.decide == null || row.decide == "0") {
					if(salescateObj.editRow != undefined) {
						$('#salescategory').datagrid('endEdit', salescateObj.editRow);
						$('#salescategory').datagrid('beginEdit', rowIndex);
						salescateObj.editRow = rowIndex;
					}
					if(salescateObj.editRow == undefined) {
						$('#salescategory').datagrid('beginEdit', rowIndex);
						salescateObj.editRow = rowIndex;
					}
					$('.datagrid-editable-input.textbox-f').textbox({
				        inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
				            keyup: function test(event) {
				            	var newValue = event.target.value;
				                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
				            }
				        })
				    });
				}
			}
			if(tabId == "registrationUi") {
				if(obj.editRow != undefined) {
					if(rowIndex == 0) {
						$('#registrationUi').datagrid('endEdit', obj.editRow);
						$('#registrationUi').datagrid('beginEdit', rowIndex);
						obj.editRow = rowIndex;
						//测试datebox范围
						//$.rangeDateBox()
					}
				}
//				if(obj.editRow == undefined) {
//					if(rowIndex == 0) {
//						$('#registrationUi').datagrid('beginEdit', rowIndex);
//						obj.editRow = rowIndex;
//						//测试datebox范围
//						//$.rangeDateBox()
//					}
//				}
			}
			if(tabId == "label_management") {
				if(rowIndex > 0){
					if (endEditing(rowIndex)) {
						$(this).datagrid('beginEdit', rowIndex);
						editIndex = rowIndex;
						//新加的全局变量 判断是否编辑的索引
						window.editIndex = editIndex;
					}
				}				
			}
		},
		onLoadSuccess: function() {
			if(tabId == 'clientPopTab' || tabId == 'employeeWindowTab'){
				parent.layer.iframeAuto(parent.layer_index);
				var comDomHei = parent.layer.getChildFrame('html', parent.layer_index)[0].scrollHeight/2 + 'px';
				comDomHei = '-'+comDomHei;
				parent.layer.style(parent.layer_index,{
					top:"50%",
					marginTop:comDomHei
				})
			}
			if(isHasFn != "") {
				//数据为空  无滚动
				var dateGridRow = $("#"+tabId).datagrid("getRows").length;
				if(dateGridRow == 0 && tabId != 'timesheet_list'){
					$('.datagrid-view2 .datagrid-body').css('overflow-x','hidden');
				}
				if(dateGridRow == 0 && tabId != 'saleTypTab'){
					$('.datagrid-view').height('40px');
					$('.time_sheet_info').removeClass('hidden');
					$('.time_sheet_info').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				var obj = settingPageStyle();
				//为easyUi table添加      ----  属性文件
				setDomNameByLanguage(tabId);
				if(tabId != "taxCostListTab" && tabId != "salescategory_T" && tabId != "salescategory" && tabId != "commonListTab" && tabId != "timesheet_list" && tabId != "cost_list"&& tabId != "cost_list_job_details" 
				&& tabId != "ltd_list" && tabId != "registrationUi") {			
					//修改分页的样式
					var pageson = $('#' + tabId).parents('.datagrid-view').siblings('.datagrid-pager.pagination');
					var page = pageson.find('.pagination-num').parent().next().children('span')[0].innerHTML.replace(/[^0-9]/ig, '');
					pageson.css({
						'background-color': 'white',
						'padding': '3px 0 3px 32px'
					});
					$('.panel-title').css({
						'background-color': '#ecf0f1',
						'text-align': 'center'
					});
					$('.panel-header').css({
						'background': '#ecf0f1',
						'border-color': '#dddddd'
					});
					$('.panel-body').css({
						'border-color': '#dddddd'
					});
					pageson.find('.pagination-num').css({
						'padding': 0,
						'margin-right': '5px'
					});
					pageson.find('.pagination-num').parent().prev().css('display', 'none');
					var pageNow = pageson.find('.pagination-num').val();
					$('.panel.datagrid').css('display', 'inline-block');
					pageson.find('.pagination-num').parent().next().children('span')[0].innerHTML = '/' + page;
					var info_text = pageson.find('.pagination-info').text();
					var pageDefault = obj.pageDefault(info_text);
					pageson.find('.pagination-info').html(pageDefault);
				}
				if(tabId == "employee_list") {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi'></div>");
						$('.wrap_pi').children().remove();
						$('.wrap_pi').prepend("<input tabindex='11' id='employeeEditBtn' onclick='jumpPage(\"edit\")' selectname='employeeList_suppliers_information_modify' type= 'button' class = 'btn btn-success btn-xs pull-right pi_btnO marRight i18n-input' value='社員情報変更'/>");
						$('.wrap_pi').prepend("<input tabindex='12' id='employeeRelevanceBtn' onclick='jumpPage(\"relevance\")' selectname='employeeList_associate_information_modify' type= 'button' class = 'btn btn-success btn-xs pull-right pi_btnT marRight i18n-input' value='得意先紐付変更'/>");
						$('.wrap_pi').prepend("<i tabindex='13' class='iconfont icon-xiazai pull-right green marRight'></i>");
						var str_load = "<div class='panel panel-default download_pdf'><div class='panel-body download_pdf_child'><a onclick='exportExcelByTab(\"employee_list\",\"StaffInformation\")' class='i18n' name='member_view'></a><a onclick='exportExcelReport(\"employeesPowerList\")' class='i18n' name='member_permiss'></a></div></div>";
						$('.wrap_pi').prepend(str_load);
						readLanguageFile();
					}
					$('.icon-xiazai').click(function(e){
						e.stopPropagation();
						if($('.download_pdf').css('display') == "none"){
							$('.download_pdf').fadeIn();
						}else{
							$('.download_pdf').fadeOut();
						}
					})
					$(document).click(function(e){
						if (!($(e.target).hasClass('download_pdf_child') || $(e.target).closest('div').hasClass('download_pdf_child'))) {
							$('.download_pdf').fadeOut();
						}
					})
					//layer下载  居中 
					//$.layerShowDiv('icon-xiazai','auto', 'auto', 1, $('.download_pdf'));
				} else if(tabId == "saleTypTab") {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi'></div>");
						$('.wrap_pi').children().remove();
						$('.wrap_pi').prepend("<input style='width: 7%;' selectname='salescategoryList_change' type= 'button' class = 'btn btn-success btn-xs pull-right pi_btnO marRight i18n-input' value='変更' onclick='saleEdit(1)'/>");
						readLanguageFile();
					}
				} else if(tabId == "suppliersListTab") {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi'></div>");
						$('.wrap_pi').children().remove();
						$('.wrap_pi').prepend("<input selectname='suppliersList_suppliers_information_modify' type= 'button' id= 'clmstchange' tabindex='5' class = 'btn btn-success btn-xs pull-right pi_btnO marRight i18n-input' onclick='clmstchange()' value='取引先情報変更'/>");
						$('.wrap_pi').prepend("<input selectname='suppliersList_associate_information_modify' type= 'button' id= 'userchange' tabindex='6' class = 'btn btn-success btn-xs pull-right pi_btnT marRight i18n-input' onclick='userchange()' value='社員紐付変更'/>");
						$('.wrap_pi').prepend("<i onclick=\"exportExcelByTab('suppliersListTab','ClientVendorList')\" class='iconfont icon-xiazai pull-right green marRight' tabindex='7'></i>");
						readLanguageFile();
					}
				} else if(tabId == 'job_login_list') {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi'></div>");
						$('.wrap_pi').children().remove();
						var str = '<i class="iconfont icon-xiazai1 ml-10"'+
						           "onclick=\"exportExcelByTab('job_login_list','JobList')\" ></i>"+
						           "<i class=\"iconfont icon-qiehuan\" onclick=\"urlforcustom('job_login_list')\"></i>"+
						           '<i class="iconfont icon-jobicon- ml-10"'+ 
						           "onclick=\"saveCostWidth('job_login_list')\"></i>"+
						           '</i>'+
						           '<i class="iconfont icon-bianji"'+
						           "onclick=\"datagraidMenu('job_login_list',window.event)\">";
						$('.wrap_pi').prepend(str);
						toolTipLanguage();
					}
				} else if(tabId == 'label_management') {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi'></div>");
						$('.wrap_pi').children().remove();
						$('.wrap_pi').prepend("<input style='width: 7%;' selectname='salescategoryList_change' type= 'button' class = 'btn btn-success btn-xs pull-right pi_btnO marRight i18n-input' onclick='labeladd()' value='変更'/>");
						$('.wrap_pi').prepend("<input style='width: 7%;' selectname='board_clear' type= 'button' class = 'btn btn-success btn-warning btn-xs pull-right pi_btnO marRight i18n-input' onclick='labeldelete()' value='削除'/>");
						//readLanguageFile();
						$('.wrap_pi').find('input[selectname="salescategoryList_change"]').val(part_language_change_new('salescategoryList_change'));
						$('.wrap_pi').find('input[selectname="board_clear"]').val(part_language_change_new('board_clear'));
					}
				} else if(tabId == 'cost_list_Two') {
					var is_exist = $('.wrap_pi').exist();
					if(!is_exist) {
						$('.pagination-info').after("<div class= 'wrap_pi flex-right' style='margin-right:10px'></div>");
						$('.wrap_pi').children().remove();
						var str = '<i class="iconfont icon-xiazai1 ml-10"'+
						           "onclick=\"exportExcelByTab('cost_list_Two','CostList')\" ></i>"+
						           "<i class=\"iconfont icon-qiehuan\" onclick=\"urlforcustom('cost_list_Two')\"></i>"+
						           '<i class="iconfont icon-jobicon- ml-10"'+ 
						           "onclick=\"saveCostWidth('cost_list_Two')\"></i>"+
						           '</i>'+
						           '<i class="iconfont icon-bianji"'+
						           "onclick=\"datagraidMenu('cost_list_Two',window.event)\">";
						$('.wrap_pi').prepend(str);
						toolTipLanguage();
					}
				}
			}
		}
	});
}
/**
 * 方法名 getColumnsByDataGridId
 * 方法的说明 返回表格中表头字段相关设置
 * @param String tabId 表格ID
 * @return Array
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
function getColumnsByDataGridId(tabId, dataColumns) {
	var columnArr = new Array();
	var arr = new Array();

	var languageSelect = $.getLangTyp();
	/*
	if(languageSelect == "" || languageSelect == null) {
		languageSelect = $.getLangTyp();
	}

	*/
	switch(tabId) {
		case "irrelevantTab":
			arr.push({
				field: 'check',
				checkbox: true
			}, {
				field: 'account_cd',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="account_cdL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'divnm',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="divnmL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'divname_full',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="divname_fullL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			});
			columnArr.push(arr);
			break;
		case "relevancedTab":
			arr.push({
				field: 'check',
				checkbox: true
			}, {
				field: 'account_cd',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="account_cdR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'divnm',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="divnmR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'divname_full',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="divname_fullR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			});
			columnArr.push(arr);
			break;
		case "employee_list":
			arr.push({
				name:'employeeList_employee_number',
				field: 'memberID',
				title: '社員番号',
				align: "center",
				rowspan: 1,
				width: 50
			}, {
				name:'employeeList_employee_name',
				field: 'nickname',
				title: '氏名',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'employeeList_sector',
				field: 'departName' + languageSelect,
				title: '部門',
				width: 50,
				align: "center",
				rowspan: 1
			}, {
				name:'employeeList_Businesslevel',
				field: 'level' + languageSelect,
				title: '業務レベル',
				width: 50,
				align: "center",
				rowspan: 1
			}, {
				name:'employeeList_Roll',
				field: 'roleNameByUser' + languageSelect,
				title: 'ロール',
				align: "center",
				width: 100,
				rowspan: 1
			}, {
				name:'employeeList_language',
				field: 'langtyp' + languageSelect,
				title: '言語',
				align: "center",
				width: 50,
				rowspan: 1
			}, {
				name:'employeeList_loginid',
				field: 'loginName',
				title: 'ログインID',
				align: "center",
				width: 50,
				rowspan: 1
			},
			{
				name:'employeeList_Y_N_Effect',
				field: 'delFlg',
				title: '有効/無効',
				align: "center",
				width: 50,
				rowspan: 1,
				formatter: setFlgValue
			});
			columnArr.push(arr);
			break;
		case "commonListTab":
			arr.push({
				name:'commoncodeList_master_class_code',
				field: 'mstcd',
				title: 'マスタ区分コード',
				align: "center",
				rowspan: 1,
				width: 50
			}, {
				name:'commoncodeList_master_segment_name',
				field: 'mstname',
				title: 'マスタ区分名称',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_system_code',
				field: 'itemcd',
				title: 'システムコード',
				width: 50,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_project_name_jp',
				field: 'itemname_jp',
				title: '項目名称(日本語)',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_project_name_zc',
				field: 'itmname',
				title: '項目名称(中国語・簡体字)',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_project_name_zt',
				field: 'itemname_hk',
				title: '項目名称(中国語・繁体字)',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_project_name_en',
				field: 'itemname_en',
				title: '項目名称(英語)',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:'commoncodeList_auxiliary_code',
				field: 'itmvalue',
				title: '補助コード',
				align: "center",
				width: 70,
				rowspan: 1
			}, {
				name:'commoncodeList_numbers',
				field: 'aidcd',
				title: '数値',
				align: "center",
				width: 30,
				rowspan: 1
			}, {
				name:'commoncodeList_conversion',
				field: 'change_utin',
				title: '換算単位',
				align: "center",
				width: 50,
				rowspan: 1
			}, {
				field: 'company_cd',
				title: '公司编号',
				align: "center",
				width: 50,
				rowspan: 1,
				hidden: true
			},
			{
				field:'orderno',
				title: '表示順',
				align: "center",
				width: 50,
				rowspan: 1
			}
			);
			columnArr.push(arr);
			break;
		case "saleTypTab":
			var saleTypArr = new Array();
			var saleTypArr1 = new Array();
			saleTypArr.push({
				field: 'sale_cd',
				title: '売上種目コード',
				align: "center",
				rowspan: 2
			}, {
				field: 'sale_account_cd',
				title: '売上種目会計コード',
				width: 100,
				align: "center",
				rowspan: 2
			}, {
				field: 'sale_name',
				title: '種目名称',
				width: 100,
				align: "center",
				rowspan: 2
			}, {
				field: 'tran_lend'+ languageSelect,
				title: '立替振替',
				width: 100,
				align: "center",
				rowspan: 2
			},
			 {
				field: 'vat_rate',
				title: '增值税率(%)',
				width: 40,
				align: "center",
				rowspan: 2
			}, {
				field: 'start_date',
				title: '开始时间',
				width: 100,
				align: "center",
				rowspan: 2,
				hidden: true,
				editor: {
					type: 'datebox',
					options: {
						required: true
					}
				}
			}, {
				field: 'end_date',
				title: '结束时间',
				width: 100,
				align: "center",
				rowspan: 2,
				hidden: true,
				editor: {
					type: 'datebox',
					options: {
						required: true
					}
				}
			}, {
				title: '税率',
				colspan: 2
			}, {
				field: 'del_flg'+ languageSelect,
				title: '有効/無効',
				align: "center",
				rowspan: 2
			});
			saleTypArr1.push({
				field: 'rate2',
				title: dataColumns[0]['tax2Name'+languageSelect],
				align: "center",
				rowspan: 1,
				width: 40
			}, {
				field: 'rate3',
				title: dataColumns[0]['tax3Name'+languageSelect],
				align: "center",
				rowspan: 1,
				width: 40
			});
			columnArr.push(saleTypArr);
			columnArr.push(saleTypArr1);
			break;
		case "suppliersListTab":
			//var suppArr = new Array();
			//var suppArr1 = new Array();
			arr.push({
				name:"suppliersList_suppliers_number",
				field: 'cldivcd',
				title: '取引先コード',
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_suppliers_system_number",
				field: 'account_cd',
				title: '取引先会計コード',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_suppliers_n",
				field: 'divname_full',
				title: '取引先名称',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_suppliers_a",
				field: 'divnm',
				title: '取引先略称',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_suppliers_address",
				field: 'divadd1',
				title: '住所',
				align: "center",
				width: 100,
				rowspan: 1
			}, 
			{
				name:"suppliersList_customer",
				field: 'client_flg',
				title: '得意先',
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_contractor",
				field: 'contra_flg',
				title: '発注先',
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_payer",
				field: 'pay_flg',
				title: '請求先',
				align: "center",
				rowspan: 1
			}, {
				name:"suppliersList_HDY_group",
				field: 'hdy_flg',
				title: '相手先G会社',
				align: "center",
				rowspan: 1
			},
			{
				name:"suppliersList_Y_N_Effect",
				field: 'del_flg',
				title: '有効/無効',
				align: "center",
				rowspan: 1
			});
//			suppArr1.push(
//			{
//				name:"suppliersList_customer",
//				field: 'client_flg',
//				title: '得意先',
//				align: "center",
//				rowspan: 1
//			}, {
//				name:"suppliersList_contractor",
//				field: 'contra_flg',
//				title: '発注先',
//				align: "center",
//				rowspan: 1
//			}, {
//				name:"suppliersList_payer",
//				field: 'pay_flg',
//				title: '請求先',
//				align: "center",
//				rowspan: 1
//			}, {
//				name:"suppliersList_HDY_group",
//				field: 'hdy_flg',
//				title: '相手先G会社',
//				align: "center",
//				rowspan: 1
//			}
//			);
			columnArr.push(arr);
			//columnArr.push(suppArr1);
			break;
		case "taxCostListTab":
			arr.push({
				field: 'user_tax_type',
				title: '納税者区分',
				width: 100,
				align: "center",
				rowspan: 1,
			}, {
				field: 'invoice_type',
				title: '発票種類',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				field: 'invoice_text',
				title: '発票内容',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				field: 'vat_rate',
				title: '増値税率(%)',
				align: "center",
				width: 50,
				rowspan: 1
			}, {
				field: 'del_flg',
				title: '有効/無効',
				align: "center",
				width: 50,
				rowspan: 1
			},{
				field: 'user_tax_type_code',
				title: '納税者種類',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden:true
			}, {
				field: 'invoice_type_code',
				title: '発票種類',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden:true
			}, {
				field: 'invoice_text_code',
				title: '発票内容',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden:true
			}, {
				field: 'id',
				title: 'id',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden:true
			});
			columnArr.push(arr);
			break;
		case "ltd_list":
			arr.push({
				name:"ltdList_company_name_l",
				field: 'company_name',
				title: '会社略称',
				align: "center",
				rowspan: 1,
				width: 50
			}, {
				name:"ltdList_company_name",
				field: 'company_full_name',
				title: '会社名称',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"ltdList_company_name_en",
				field: 'company_name_en',
				title: '会社名称（英）',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"ltdList_main_language",
				field: 'itemname' + languageSelect,
				title: 'メイン言語',
				width: 50,
				align: "center",
				rowspan: 1
			}, {
				name:"ltdList_time_zone",
				field: 'itemnametz' + languageSelect,
				title: 'タイムゾーン',
				width: 100,
				align: "center",
				rowspan: 1
			}, {
				name:"ltdList_currency_code",
				field: 'itemnamecd' + languageSelect,
				title: '通貨コード',
				align: "center",
				width: 50,
				rowspan: 1
			}, {
				name:"ltdList_group",
				field: 'company_type',
				title: '所属グループ',
				align: "center",
				width: 100,
				rowspan: 1,
				formatter: setCompanyTypeValue
			}, {
				name:"ltdList_numbering_rules",
				field: 'number_rules',
				title: '採番ルール',
				align: "center",
				rowspan: 1,
				width: 50,
				formatter: setNumberRulesValue
			});
			columnArr.push(arr);
			break;
		case "salescategory_T":
			arr.push({
					field: 'start_date',
					title: '適用開始日',
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'datebox',
						options: {
							editable:false
						}
					}
				}, {
					field: 'end_date',
					title: '適用終了日',
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'datebox',
						options: {
							editable:false
						}
					}
				}, {
					field: 'rate2',
					title: dataColumns['tax2Name'+languageSelect],
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'textbox',
						options: {
							//required: true
						}
					}
				}, {
					field: 'rate3',
					title: dataColumns['tax3Name'+languageSelect],
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'textbox',
						options: {
							//required: true
						}
					}
				}, {
					field: 'decide',
					title: '判断',
					width: 100,
					align: "center",
					rowspan: 1,
					hidden: true
				}

			);
			columnArr.push(arr);
			break;
		case "salescategory":
			arr.push({
				field: 'start_date',
				title: '適用開始日',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					type: 'datebox',
					options: {
						editable:false
					}
				}
			}, {
				field: 'end_date',
				title: '適用終了日',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					type: 'datebox',
					options: {
						editable:false
					}
				}
			}, {
				field: 'vat_rate',
				title: '增值税率(%)',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					type: 'textbox',
					options: {
						//required: true
					}
				}
			}, {
				field: 'decide',
				title: '判断',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden: true
			});
			columnArr.push(arr);
			break;
		case "wedgemembersleft":
			arr.push({
				field: 'check',
				checkbox: true
			},{
				field: 'company_name',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="company_nameL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'user_cd',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="user_cdL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'user_name',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="user_nameL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'level',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					type: 'text',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" placeholder="'+Note+'" id="levelL" type="text" class="rng" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			},
			{
				field: 'member_id',
				title: '',
				width: 100,
				align: "center",
				hidden:true,
				rowspan: 1,				
			});
			columnArr.push(arr);
			break;
		case "wedgemembersright":
			arr.push({
				field: 'check',
				checkbox: true
			},{
				field: 'company_name',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="company_nameR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'user_cd',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="user_cdR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'user_name',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					//type: 'datebox',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="user_nameR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'level',
				title: '',
				width: 100,
				align: "center",
				rowspan: 1,
				editor: {
					type: 'text',
					options: {
						required: true
					}
				},
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" id="levelR" type="text" class="rng right" style="border:0;width:100%"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			},
			{
				field: 'member_id',
				title: '',
				width: 100,
				align: "center",
				hidden:true,
				rowspan: 1,				
			}
			);
			columnArr.push(arr);
			break;
		case "registrationUi":
			var tlange = $('#language').val()
			var strFlg = '_'
			var showLangeflag;
			switch(tlange) {
			case "jp":
				showLangeflag = 'itemname' + strFlg + 'jp';;
				break;
			case "zc":
				showLangeflag = 'itmname';
				break;
			case "zt":
				showLangeflag = 'itemname' + strFlg + 'hk';
				break;
			case "en":
				showLangeflag = 'itemname' + strFlg + 'en';
			break;
			}
			arr.push({
					field: 'start_date',
					title: '適用開始日',
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'datebox',
						options: {
							editable:false
						},
					},
				}, {
					field: 'end_date',
					title: '適用終了日',
					width: 100,
					align: "center",
					rowspan: 1,
					editor: {
						type: 'datebox',
						options: {
							//required: true,
							editable:false
						},
					},
				}, {
					field: 'tax_typename',
					title: '納税人種類',
					width: 100,
					editor: {
						type: 'combobox',
						options: {
							valueField: 'itemcd',
							textField: showLangeflag,
							//required: true,
							data: [],
							editable:false
						}
					}
				}
				//			{field:'tax_type',width:0}
			);
			columnArr.push(arr);
			break;
		case "job_login_list":
			arr = formatListTitle(dataColumns,languageSelect);
			columnArr.push(arr);
			break;
		case "label_management":
			arr.push({
				field: 'label_id',
				title: 'ラベルID',
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {				
					var c = undefined;
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'label_text',
				title: 'ラベル名',
				width: 400,
				align: "center",
				rowspan: 1,
				editor:"text",
				sorter:function (a,b) {				
					var c = '<input placeholder="'+Note+'" maxlength="20" id="label_text" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>';
					if(b == c){
	                    return 1;
	                }                   
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'label_level',
				title: '区分',
				width: 200,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {
					var c = '<input placeholder="'+Note+'" maxlength="20" id="label_level" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>';
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'addusercd',
				title: '作成者',
				width: 200,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {
					var c = '<input placeholder="'+Note+'" maxlength="20" id="addusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>';
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			}, {
				field: 'adddate',
				title: '作成時間',
				width: 200,
				align: "center",
				rowspan: 1,
				sorter:function (a,b) {
					var c = undefined;
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			},{
				field: 'updusercd',
				title: '更新者',
				align: "center",
				width: 200,
				rowspan: 1,
				sorter:function (a,b) {
					var c = '<input placeholder="'+Note+'" maxlength="20" id="updusercd" type="text" class="rng" style="border:0;width:100%;padding-left:4px;"/>';
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			},{
				field: 'upddate',
				title: '更新時間',
				align: "center",
				width: 200,
				rowspan: 1,
				sorter:function (a,b) {
					var c = undefined;
                    if(b == c){
	                    return 1;
	                }
                    if(a > b){
                     	return 1;
                    }else{
                     	return -1;
                    }
                }
			},{
				field: 'lock_flg',
				title: '编号',
				width: 100,
				align: "center",
				rowspan: 1,
				hidden:true
			})
			columnArr.push(arr);
			break;
		case "employeeWindowTab":
			arr.push({
				field: 'companyName',
				title: part_language_change_new('ltdList_company_name'),
				width: '30%',
				align: 'center'
			}, {
				field: 'departName' + languageSelect,
				title: part_language_change_new('employeeList_sector'),
				width: '20%',
				align: 'center'
			}, {
				field: 'nickname',
				title: part_language_change_new('employeeList_employee_name'),
				width: '50%',
				align: 'center'
			}, {
				field: 'colorV',
				title: '颜色',
				align: 'center',
				hidden: true
			})
			columnArr.push(arr);
			break;
		case "clientPopTab":
		
			arr.push({
				field: 'cldivcd',
				title: part_language_change_new('customlist_list_of_items_title-23-N'),
				width: '30%',
				align: 'center'
			}, {
				field: 'divnm',
				title: part_language_change_new('joblist_person_name_j'),
				width: '30%',
				align: 'center'
			}, {
				field: 'divname_full',
				title: part_language_change_new('joblist_person_name_q'),
				width: '40%',
				align: 'center'
			})
			columnArr.push(arr);
			break;
		case "timesheet_list":
			arr = formatListTitle(dataColumns,languageSelect);
			columnArr.push(arr);
			break;
		case "cost_list":
				arr.push({
				field: 'costno',
				title: '発注No.',
				align: "center",
				rowspan: 1,
				width: 60
			}, {
				field: 'inputno',
				title: '登録票No.',
				width: 60,
				align: "center",
				rowspan: 1
			}, {
				field: 'dis'+ languageSelect,
				title: '区分',
				width: 30,
				align: "center",
				rowspan: 1,
			}, {
				field: 'payeename',
				title: '発注先',
				width: 80,
				align: "center",
				rowspan: 1,
			}, {
				field: 'name',
				title: '案件名',
				align: "center",
				width: 80,
				rowspan: 1,
			}, {
				field: 'amt',
				title: '原価額',
				align: "center",
				width: 80,
				rowspan: 1,
			}, {
				field: 'status'+ languageSelect,
				title: 'ステータス',
				align: "center",
				width: 50,
				rowspan: 1,
			}, {
				field: 'payreqdate',
				title: '申請日',
				align: "center",
				rowspan: 1,
				width: 60,
			}, {
				field: 'confirmdate',
				title: '承認日',
				align: "center",
				rowspan: 1,
				width: 60,
			})
			columnArr.push(arr);
			break;
		case "cost_list_job_details":
			arr.push({
				field: 'costno',
				title: '発注No.',
				align: "center",
				rowspan: 1,
				width: 60
			}, {
				field: 'inputno',
				title: '登録票No.',
				width: 60,
				align: "center",
				rowspan: 1
			}, {
				field: 'dis'+ languageSelect,
				title: '区分',
				width: 30,
				align: "center",
				rowspan: 1,
			}, {
				field: 'payeename',
				title: '発注先',
				width: 80,
				align: "center",
				rowspan: 1,
			}, {
				field: 'name',
				title: '案件名',
				align: "center",
				width: 80,
				rowspan: 1,
			}, {
				field: 'amt',
				title: '原価額',
				align: "center",
				width: 80,
				rowspan: 1,
			}, {
				field: 'status'+ languageSelect,
				title: 'ステータス',
				align: "center",
				width: 50,
				rowspan: 1,
			}, {
				field: 'payreqdate',
				title: '申請日',
				align: "center",
				rowspan: 1,
				width: 60,
			}, {
				field: 'confirmdate',
				title: '承認日',
				align: "center",
				rowspan: 1,
				width: 60,
			})
			columnArr.push(arr);
			break;
		case "cost_list_Two":
			arr = formatListTitle(dataColumns,languageSelect);
			columnArr.push(arr);
			break;
	}
	addTableSort(columnArr,tabId)
	return columnArr;
}
//循环动态列 原价一览  给需要躲过语言的 字段 后面加上 语言标识
function formatListTitle(arr,languageFlg){
	var newList = [];
	for(var i=0;i<arr.length;i++){
		if(arr[i].field == "saleamt" || arr[i].field == "vatamt" || arr[i].field == "reqamt" || arr[i].field == "plancostamt" || arr[i].field == "costTotalAmt" || arr[i].field == "costVatTotal" || arr[i].field == "tax2" || arr[i].field == "tax3" || arr[i].field == "taxTotal" || arr[i].field == "profit"|| arr[i].field == "amt"|| arr[i].field == "payamt"){
			arr[i]['sorter'] = function (a,b) {
               var a = recoveryNumber(a);
               var b = recoveryNumber(b);
                if(parseFloat(a) > parseFloat(b)){
                 	return 1;
                }else{
                 	return -1;
                }
            }
	}
		if(arr[i]['field']!="diszc"&&arr[i]['field']!="statuszc"&&arr[i]['field']!="payflgzc"&&arr[i]['field']!="outputflgzc"&&arr[i]['field']!='jsflaglanguagezc'&&arr[i]['field']!='reqflglanguagezc'&&arr[i]['field']!='recflglanguagezc'
		&&arr[i]['field']!='invflglanguagezc'&&arr[i]['field']!='costendflglanguagezc'&&arr[i]['field']!='assignflglanguagezc'&&arr[i]['field']!='tranitemnamezc'){
			newList.push(arr[i]);
		}else{			
			arr[i]['field'] = arr[i]['field'].substr(0,arr[i]['field'].length-2)+languageFlg;
			newList.push(arr[i]);				
		}
	}
	return newList;
}
//动态添加排序属性
function addTableSort(columnArr,tabId){
	if(columnArr[0] == undefined || null){
		return null;
	}
	var arrLen = columnArr[0].length;
	var arr = [];
	var date_type_arr=[];
	var cost_arr = [];
	for(var i = 0;i < arrLen;i ++){
		columnArr[0][i].sortable = true;
		if(tabId == 'registrationUi' || tabId == 'salescategory' || tabId == 'salescategory_T'){
			columnArr[0][i].sortable = false;
		}
		columnArr[0][i].halign = 'center';
		columnArr[0][i].align = 'left';
		if(tabId == 'job_login_list'){
			date_type_arr = ["dlvday",
			"jsflaglanguagejp","jsflaglanguagezc","jsflaglanguagezt","jsflaglanguageen",
			"assignflglanguagejp","assignflglanguagezc","assignflglanguagezt","assignflglanguageen",
			"reqflglanguagejp","reqflglanguagezc","reqflglanguagezt","reqflglanguageen",
			"recflglanguagejp","recflglanguagezc","recflglanguagezt","recflglanguageen",
			"invflglanguagejp","invflglanguagezc","invflglanguagezt","invflglanguageen",
			"costendflglanguagejp","costendflglanguagezc","costendflglanguagezt","costendflglanguageen",
			"adddate","saleadddate","saleadmitdate","dlvmon",
			"costendflglanguagejp","costendflglanguagezc","costendflglanguagezt","costendflglanguageen"
			];
			cost_arr = ["saleamt","vatamt","reqamt","plancostamt","plancostamt","costTotalAmt","costVatTotal","tax2","tax3","taxTotal","profit","profitRate","plansale"];
		}
		if(tabId == 'cost_list_Two'){
			date_type_arr = ["disjp","dalday","statusjp","adddate","payreqdate","confirmdate","outputflg","payflgjp","invoicedate","paydlyday","plandlvday","payhopedate","planpaydate","paydate","lenddate"];
			cost_arr = ["saleamt","amt","vatamt","payamt"];
		}
		if(tabId == 'saleTypTab'){
			date_type_arr = ["del_flgjp","del_flgzc","del_flgzt","del_flgen"];
			cost_arr = ["vat_rate"];
			columnArr[1][0].align = 'right';
			columnArr[1][1].align = 'right';
		}
		if(tabId == 'commonListTab'){
			cost_arr = ["aidcd","change_utin"];
			date_type_arr = ["orderno"];
		}
		if(tabId == 'taxCostListTab'){
			date_type_arr = ["del_flg"];
			cost_arr = ['vat_rate'];
		}
		if(tabId == 'salescategory_T'){
			date_type_arr = ["start_date","end_date"];
			cost_arr = ["rate2","rate3"];
		}
		if(tabId == 'salescategory'){
			date_type_arr = ["start_date","end_date"];
			cost_arr = ["vat_rate"];
		}
		if(tabId == 'registrationUi'){
			date_type_arr = ["start_date","end_date"];
		}
		if(tabId == 'label_management'){
			date_type_arr = ["adddate","upddate"];
		}
		if(tabId == 'timesheet_list'){
			date_type_arr = ["dlvday","jsflaglanguagejp","assignflglanguagejp","reqflglanguagejp","recflglanguagejp","invflglanguagejp","costendflglanguagezc","adddate","saleadddate","saleadmitdate","dlvmon"];
			cost_arr = ["saleamt","vatamt","reqamt","plancostamt","costTotalAmt","costVatTotal","tax2","tax3","taxTotal","profit","profitRate","plansale"];
		}
		if(tabId == 'cost_list'){
			date_type_arr = ["disjp","statusjp","payreqdate","confirmdate"];
			cost_arr = ["amt"];
		}
		if(tabId == 'cost_list_job_details'){
			date_type_arr = ["disjp","statusjp","payreqdate","confirmdate"];
			cost_arr = ["amt"];
		}
		if(tabId == 'employee_list'){
			date_type_arr = ["delFlg"];
		}
		if(tabId == 'suppliersListTab'){
			date_type_arr = ['del_flg','client_flg','contra_flg','pay_flg','hdy_flg'];
		}
		if(date_type_arr.indexOf(columnArr[0][i].field) != -1){
			columnArr[0][i].align = 'center';
			continue;
		}
		if(cost_arr.indexOf(columnArr[0][i].field) != -1){
			columnArr[0][i].align = 'right';
		}
	}
	return columnArr;
}

/**
 * 方法名 setDomNameByLanguage
 * 方法的说明 表格初始化后，相关name设置，用于语言切换
 * @param String tabId 表格ID
 * @return 
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
function setDomNameByLanguage(tabId) {
	switch(tabId) {
		case "irrelevantTab":
		case "relevancedTab":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			var domf = $('.datagrid-body .datagrid-btable tr td div.datagrid-cell-check');
			var domin = $('.datagrid-body .datagrid-btable tr td input[type=text]');
			dom.addClass('i18n');
			domin.css({
				'width': '100%',
				'text-align': 'center',
				'height': '40px',
				'border': '1px solid #cccccc',
				'border-radius': '4px;'
			});
			domf.eq(0).addClass('i18n');
			domf.eq(0).attr("name", "customerwedgeChange_-");
			dom.eq(0).attr("name", "customerwedgeChange_accounting_code");
			dom.eq(1).attr("name", "customerwedgeChange_abbreviation");
			dom.eq(2).attr("name", "customerwedgeChange_name");
			dom.eq(3).attr("name", "customerwedgeChange_accounting_code");
			dom.eq(4).attr("name", "customerwedgeChange_abbreviation");
			dom.eq(5).attr("name", "customerwedgeChange_name");
			//readLanguageFile(); 
			dom.eq(0).html(part_language_change_new('customerwedgeChange_accounting_code'))
			dom.eq(1).html(part_language_change_new('customerwedgeChange_abbreviation'))
			dom.eq(2).html(part_language_change_new('customerwedgeChange_name'))
			dom.eq(3).html(part_language_change_new('customerwedgeChange_accounting_code'))
			dom.eq(4).html(part_language_change_new('customerwedgeChange_abbreviation'))
			dom.eq(5).html(part_language_change_new('customerwedgeChange_name'))
			break;
		case "employee_list":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "employeeList_employee_number");
			dom.eq(1).attr("name", "employeeList_employee_name");
			dom.eq(2).attr("name", "employeeList_sector");
			dom.eq(3).attr("name", "employeeList_Businesslevel");
			dom.eq(4).attr("name", "employeeList_Roll");
			dom.eq(5).attr("name", "employeeList_language");
			dom.eq(6).attr("name", "employeeList_loginid");
			dom.eq(7).attr("name", "employeeList_Y_N_Effect");
			//dom.eq(8).attr("name", "employeeList_Y_N_Effect");
			readLanguageFile(); 
			//onsize table表下边线问题
			resizeTableLine('employee_list');
			break;
		case "commonListTab":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "commoncodeList_master_class_code");
			dom.eq(1).attr("name", "commoncodeList_master_segment_name");
			dom.eq(2).attr("name", "commoncodeList_system_code");
			dom.eq(3).attr("name", "commoncodeList_project_name_jp");
			dom.eq(4).attr("name", "commoncodeList_project_name_zc");
			dom.eq(5).attr("name", "commoncodeList_project_name_zt");
			dom.eq(6).attr("name", "commoncodeList_project_name_en");
			dom.eq(7).attr("name", "commoncodeList_auxiliary_code");
			dom.eq(8).attr("name", "commoncodeList_numbers");
			dom.eq(9).attr("name", "commoncodeList_conversion");
			dom.eq(11).attr("name", "commoncodeRegistration_display_order");
			readLanguageFile();
			break;
		case "saleTypTab":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "salescategoryList_sales_line_code");
			dom.eq(1).attr("name", "salescategoryList_sales_line_accounting_code");
			dom.eq(2).attr("name", "salescategoryList_event_name");
			dom.eq(3).attr("name", "salescategoryList_transfer_of_money");
			dom.eq(4).attr("name", "salescategoryList_credit_cost");
			dom.eq(7).attr("name", "salescategoryList_Y_N_Effect");
			readLanguageFile(); 
			//onsize table表下边线问题
			var total = $('#'+tabId).datagrid('getData').total;
			if(total === 0){
				$('.datagrid-view').css('height','80px');
			}
			//添加onwindow事件
			window.onresize = function(){
				var timeoutId;
				clearTimeout(timeoutId);
				timeoutId = setTimeout(function(){
					var total = $('#'+tabId).datagrid('getData').total;
					if(total === 0){
						$('.datagrid-view').css('height','80px');
					}
				}, 100);
			}
			break;
		case "suppliersListTab":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "suppliersList_suppliers_number");
			dom.eq(1).attr("name", "suppliersList_suppliers_system_number");
			dom.eq(2).attr("name", "suppliersList_suppliers_n");
			dom.eq(3).attr("name", "suppliersList_suppliers_a");
			dom.eq(4).attr("name", "suppliersList_suppliers_address");
//			dom.eq(5).attr("name", "suppliersList_suppliers_tel");
			dom.eq(9).attr("name", "suppliersList_Y_N_Effect");
			dom.eq(5).attr("name", "suppliersList_customer");
			dom.eq(6).attr("name", "suppliersList_contractor");
			dom.eq(7).attr("name", "suppliersList_payer");
			dom.eq(8).attr("name", "suppliersList_HDY_group");
			$('.datagrid-cell-group').addClass('i18n');
			$('.datagrid-cell-group').attr("name", "suppliersList_suppliers_type");
			readLanguageFile(); 
			//onsize table表下边线问题
			resizeTableLine('suppliersListTab');
			break;
		case "taxCostListTab":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "taxcostList_type_of_taxpayer");
			dom.eq(1).attr("name", "taxcostList_departure_card_type");			
			dom.eq(2).attr("name", "taxcostList_contents_of_invoice");
			dom.eq(3).attr("name", "salescategoryRegistration_vlue_added_tax_rate_baifen");
			dom.eq(4).attr("name", "taxcostList_effective");
			readLanguageFile(); 
			break;
		case "salescategory_T":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			var domf = $('.datagrid-body .datagrid-btable tr td div.datagrid-cell-check');
			var domin = $('.datagrid-body .datagrid-btable tr td input[type=text]');
			dom.addClass('i18n');
			domin.css({
				'width': '100%',
				'text-align': 'center',
				'height': '40px',
				'border': '1px solid #cccccc',
				'border-radius': '4px;'
			});
			domf.eq(0).addClass('i18n');
			dom.eq(0).attr("name", "salescategoryRegistration_application_start_day");
			dom.eq(1).attr("name", "salescategoryRegistration_end_day");
			dom.eq(2).attr("name", "salescategoryRegistration_tax1");
			dom.eq(3).attr("name", "salescategoryRegistration_tax2");
			$('.panel-title').css({
				'background-color': '#ecf0f1',
				'text-align': 'center'
			});
			$('.panel-header').css({
				'background': '#ecf0f1',
				'border-color': '#dddddd'
			});
			$('.panel-body').css({
				'border-color': '#dddddd'
			});
			readLanguageFile(); 
			break;
		case "salescategory":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			var domf = $('.datagrid-body .datagrid-btable tr td div.datagrid-cell-check');
			var domin = $('.datagrid-body .datagrid-btable tr td input[type=text]');
			dom.addClass('i18n');
			domin.css({
				'width': '100%',
				'text-align': 'center',
				'height': '40px',
				'border': '1px solid #cccccc',
				'border-radius': '4px;'
			});
			domf.eq(0).addClass('i18n');
			dom.eq(0).attr("name", "salescategoryRegistration_application_start_day");
			dom.eq(1).attr("name", "salescategoryRegistration_end_day");
			dom.eq(2).attr("name", "salescategoryRegistration_vlue_added_tax_rate");
			//dom.eq(3).attr("name","salescategoryRegistration_vlue_added_tax_rate");
			$('.panel-title').css({
				'background-color': '#ecf0f1',
				'text-align': 'center'
			});
			$('.panel-header').css({
				'background': '#ecf0f1',
				'border-color': '#dddddd'
			});
			$('.panel-body').css({
				'border-color': '#dddddd'
			});
			readLanguageFile(); 
			break;
		case "wedgemembersleft":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr').first().find('span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "wedgemembersChange_company_name_abbreviation");
			dom.eq(1).attr("name", "wedgemembersChange_employee_number");
			dom.eq(2).attr("name", "wedgemembersChange_name");
			dom.eq(3).attr("name", "wedgemembersChange_business_level");
			//readLanguageFile();
			dom.eq(0).html(part_language_change_new('wedgemembersChange_company_name_abbreviation'))
			dom.eq(1).html(part_language_change_new('wedgemembersChange_employee_number'))
			dom.eq(2).html(part_language_change_new('wedgemembersChange_name'))
			dom.eq(3).html(part_language_change_new('wedgemembersChange_business_level'))
			break;
		case "wedgemembersright":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr').last().find('span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "wedgemembersChange_company_name_abbreviation");
			dom.eq(1).attr("name", "wedgemembersChange_employee_number");
			dom.eq(2).attr("name", "wedgemembersChange_name");
			dom.eq(3).attr("name", "wedgemembersChange_business_level");
			//readLanguageFile();
			dom.eq(0).html(part_language_change_new('wedgemembersChange_company_name_abbreviation'))
			dom.eq(1).html(part_language_change_new('wedgemembersChange_employee_number'))
			dom.eq(2).html(part_language_change_new('wedgemembersChange_name'))
			dom.eq(3).html(part_language_change_new('wedgemembersChange_business_level'))
			break;
		case "registrationUi":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "salescategoryRegistration_application_start_day");
			dom.eq(1).attr("name", "salescategoryRegistration_end_day");
			dom.eq(2).attr("name", "suppliersRegistration_type_taxpayer");
			readLanguageFile();
			break;
		case "label_management":
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "label_id");
			dom.eq(1).attr("name", "label_text");
			dom.eq(2).attr("name", "label_level");
			dom.eq(3).attr("name", "addusercd");
			dom.eq(4).attr("name", "adddate");
			dom.eq(5).attr("name", "updusercd");
			dom.eq(6).attr("name", "upddate");
			var hei = $('.datagrid-wrap.panel-body.panel-body-noheader').height() + 40;
			$('.datagrid-wrap.panel-body.panel-body-noheader').css('height', hei);
			//刷新时判断语言，并给name赋值
			//readLanguageFile(); 
			dom.eq(0).html(part_language_change_new('label_id'))
			dom.eq(1).html(part_language_change_new('label_text'))
			dom.eq(2).html(part_language_change_new('label_level'))
			dom.eq(3).html(part_language_change_new('addusercd'))
			dom.eq(4).html(part_language_change_new('adddate'))
			dom.eq(5).html(part_language_change_new('updusercd'))
			dom.eq(6).html(part_language_change_new('upddate'))
			break;
		case "job_login_list":
			window.onresize = function(){
				var window_wid = $(window).width() - 228;
				$('.datagrid-pager-top').width(window_wid);
			}
			//开始监控滚动栏scroll
			$('#content').on('scroll', function(e) {
				//菜单block则消失
				if($('#menu').css('display') == 'block'){
					$('#menu').css('display','none');
				}
				$('.datagrid-pager-top').width($('.datagrid .panel-body').width()-32);
				//获取当前滚动栏scroll的高度并赋值
				e.stopPropagation();
				var top_job = $('.datagrid-pager-top').offset().top;
				var topTitle = $('.datagrid-header').offset().top;
				var table_cl_top = $('.datagrid-wrap.panel-body.panel-body-noheader').offset().top;
				var CONST_N = 152.66,CONST_T = 150;
				if(topTitle < CONST_N) {
					$('.datagrid-pager-top').addClass('table_fix_top');
					$('.datagrid-header').addClass('table_fix_header');
					$('.datagrid-view').addClass('top-40');	
					$(".pagination-next").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 
					})
					$(".pagination-last").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})	
					$(".l-btn-icon-left").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})		
					$(".l-btn-empty").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})		
				}	
				if(table_cl_top > CONST_T){ //否则清空悬浮
					$('.datagrid-pager-top').removeClass('table_fix_top'); 	
					$('.datagrid-header').removeClass('table_fix_header');
					$('.datagrid-view').removeClass('top-40');
				}
			})
			break;
		case "ltd_list":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "ltdList_company_name_l");
			dom.eq(1).attr("name", "ltdList_company_name");
			dom.eq(2).attr("name", "ltdList_company_name_en");
			dom.eq(3).attr("name", "ltdList_main_language");
			dom.eq(4).attr("name", "ltdList_time_zone");
			dom.eq(5).attr("name", "ltdList_currency_code");
			dom.eq(6).attr("name", "ltdList_group");
			dom.eq(7).attr("name", "ltdList_numbering_rules");
			readLanguageFile(); 
			//onsize table表下边线问题
			resizeTableLine('ltd_list');
			break;
		case "cost_list":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "cost_list_No");
			dom.eq(1).attr("name", "cost_list_tickto");
			dom.eq(2).attr("name", "suppliersRegistration_type");
			dom.eq(3).attr("name", "suppliersList_contractor");
			dom.eq(4).attr("name", "jobregistration_subject");
			dom.eq(5).attr("name", "cost_list_price");
			dom.eq(6).attr("name", "cost_list_type");
			dom.eq(7).attr("name", "cost_list_date");
			break;
		case "cost_list_job_details":
			//为easyUi table添加      ----  属性文件
			var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
			dom.addClass('i18n');
			dom.eq(0).attr("name", "cost_list_No");
			dom.eq(1).attr("name", "cost_list_tickto");
			dom.eq(2).attr("name", "suppliersRegistration_type");
			dom.eq(3).attr("name", "suppliersList_contractor");
			dom.eq(4).attr("name", "jobregistration_subject");
			dom.eq(5).attr("name", "cost_list_price");
			dom.eq(6).attr("name", "cost_list_type");
			dom.eq(7).attr("name", "cost_list_date");
			dom.eq(8).attr("name", "cost_list_admitdate");
			readLanguageFile(); 
			break;
		case "cost_list_Two":
			window.onresize = function(){
				var window_wid = $(window).width() - 228;
				$('.datagrid-pager-top').width(window_wid);
			}
			//开始监控滚动栏scroll
			$('#content').on('scroll', function(e) {
				//菜单block则消失
				if($('#menu').css('display') == 'block'){
					$('#menu').css('display','none');
				}
				$('.datagrid-pager-top').width($('.datagrid .panel-body').width()-32);
				//获取当前滚动栏scroll的高度并赋值
				e.stopPropagation();
				var top = $('.datagrid-pager-top').offset().top;
				var topTitle = $('.datagrid-header').offset().top;
				var table_cl_top = $('.datagrid-wrap.panel-body.panel-body-noheader').offset().top;
				var CONST_N = 152.66,CONST_T = 150;
//				console.log(topTitle);
				//开始判断如果导航栏距离顶部的高度等于当前滚动栏的高度则开启悬浮
				if(topTitle < CONST_N) {
					$('.datagrid-pager-top').addClass('table_fix_top');
					$('.datagrid-header').addClass('table_fix_header');
					$('.datagrid-view').addClass('top-40');
					$(".pagination-next").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 
					})
					$(".pagination-last").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})	
					$(".l-btn-icon-left").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})		
					$(".l-btn-empty").click(function(){
						$('.datagrid-pager-top').removeClass('table_fix_top'); 	
						$('.datagrid-header').removeClass('table_fix_header');
						$('.datagrid-view').removeClass('top-40'); 				
					})		
				}
				if(table_cl_top > CONST_T){ //否则清空悬浮
					$('.datagrid-pager-top').removeClass('table_fix_top');
					$('.datagrid-header').removeClass('table_fix_header');
					$('.datagrid-view').removeClass('top-40');
				}
			})
	}
}

function setFlgValue(value, row, index) {
	return getGridLanguage('del_flg', value);
}

function setNumberRulesValue(value, row, index) {
	return getGridLanguage('number_rules', value);
}

function setCompanyTypeValue(value, row, index) {
	return getGridLanguage('company_type', value);
}

function menuInit(){
	 var rowDataAll = $('#job_login_list').datagrid('getSelected');//获取所有选择的数据
	 var saleaddflg=rowDataAll['saleaddflg'];//壳上登录flag
	 var saleadmitflg=rowDataAll['saleadmitflg'];//壳上承认falg
	 var costfinishflg=rowDataAll['costfinishflg'];//原价终了
	 var assignflg=rowDataAll['assignflg'];//md分配flg
	 var jobendflg=rowDataAll['jobendflg'];//job终了状态 ，0：未终了
	 var account_flg=rowDataAll['accountflg'];//月结，洗壳flag
	 var tranlend=rowDataAll['tranlend'];//代付款和转账 0:不可；1：可;
	 var mdnum=rowDataAll['mdnum'];
	 var reqtimes=rowDataAll['reqtimes'];//請求票發行次數
	 var invoicetimes=rowDataAll['invoicetimes'];//發票發行次數
	 var costnum=rowDataAll['costnum'];//原价条数
	 $('#jobupdate').removeClass('hidden');
	 $('#mdfp').removeClass('hidden');
	 $('#saleEdit').removeClass('hidden');
	 $('#saleUpdate').removeClass('hidden');
	 $('#saleCancel').removeClass('hidden');
	 $('#qqsq').removeClass('hidden');
	 $('#fpfx').removeClass('hidden');
	 $('#rujin').removeClass('hidden');
	 $('#wfdl').removeClass('hidden');
	 $('#liti').removeClass('hidden');
	 $('#zhenti').removeClass('hidden');
	 $('#mmdlp').removeClass('hidden');
	 $('#yjwlqx').removeClass('hidden');
	 $('#yjwl').removeClass('hidden');
	 $('#saleAdmit').removeClass('hidden');
	 $('#saleAdmitCancel').removeClass('hidden');
	 //原价一览
	  if( isHavePower(menuNode,[40,41,42,43])==false){
	  	
	  }else{
	  	//如果原价条数小于0，则隐藏原价一览（2019/7/31修改--宗哥）
	  	if(Number(costnum)==0){
	  		 $('#costlist').addClass('hidden');
	  	}else{
	  		 $('#costlist').removeClass('hidden');	
	  	}
	  }
	 //job 更新
	 if( isHavePower(menuNode,[9,10,11,12])==false){
	  	 $('#jobupdate').addClass('hidden');
	  }else{
	  	 if(saleaddflg==1){
	  	 	$('#jobupdate').addClass('hidden');
	  	 }
	  }
	//MD分配
	if( isHavePower(menuNode,[13])==false){
	  	 $('#mdfp').addClass('hidden');
	  }else{
	  	if(skip['md']==1){
	  	 	$('#mdfp').addClass('hidden');
	  	}
	  	if(jobendflg==1){
	  	 	$('#mdfp').addClass('hidden');
	  	}
	  }
	////壳上登录/更新
	if( isHavePower(menuNode,[18,19,20,21])==false){
	  	     $('#saleEdit').addClass('hidden');
	         $('#saleUpdate').addClass('hidden');
	  }else{
	  	if(account_flg=='1'){//如果计上济了，则不可以壳上更新，或登录
	  		 $('#saleEdit').addClass('hidden');
	         $('#saleUpdate').addClass('hidden');
		}else{
			if(saleaddflg=='0'){//壳上未登录
		       $('#saleUpdate').addClass('hidden');
		      if(assignflg=="0"&&Number(mdnum)<1){
		      	$('#saleEdit').addClass('hidden');
		      }
		      /**********************20181224刘实更改*********/
//		      else{
//		      	 if(Number(mdnum)<1){
//		       	 $('#saleEdit').addClass('hidden');
//		         }
//		      }
		      
	       }else{
		       $('#saleEdit').addClass('hidden');
		       $('#saleUpdate').removeClass('hidden');
	       }  	
		}
	  	   
	  }
	  //売上取消
	 if( isHavePower(menuNode,[22])==false){
	  	     $('#saleCancel').addClass('hidden');
	  }else{
	  	  if(account_flg=='1'||saleaddflg=='0'){//壳上登录/更新
	       $('#saleCancel').addClass('hidden');
		  }else{
		  	//2019/6/24如果請求書，發票任何一個發行過，则只有管理，和财务部门可以取消
			  	if(reqtimes==""||reqtimes<0||invoicetimes==""||invoicetimes<0){
			  		$('#saleCancel').removeClass('hidden');
			  	}else{
			  		if(userinfo['departCD']!="001"&&userinfo['departCD']!="002"){
			  			$('#saleCancel').addClass('hidden');	
			  		}else{
			  			$('#saleCancel').removeClass('hidden');		
			  		}
			  		
			  	}
		  	  $('#saleCancel').removeClass('hidden');
		  }
	}
	  ////請求申請 
	  if( isHavePower(menuNode,[27,28,29,30])==false){
	  	     $('#qqsq').addClass('hidden');
	  }else{
	  	//2019/6/21宗哥确认修改，job终了也可以出
	  		 if(saleaddflg=='0'/*||jobendflg=='1'*/){//壳上承认日为空
		       $('#qqsq').addClass('hidden');
		      }else{
		       $('#qqsq').removeClass('hidden');
		      }
	  } 
	   //发票发行申请
	  if( isHavePower(menuNode,[31,32,33,34])==false){
	  	     $('#fpfx').addClass('hidden');
	  }else{
	  		//2019/6/21宗哥确认修改，job终了也可以出
	  		if(saleaddflg=='0'/*||jobendflg=='1'*/){//壳上承认日为空
	       $('#fpfx').addClass('hidden');
	       }else{
	       $('#fpfx').removeClass('hidden');
	       }

	  }
	  //入金
	  if( isHavePower(menuNode,[35])==false){
	  	     $('#rujin').addClass('hidden');
	  }else{
	  	   if(skip['confirm']==0){
	  	   	   
			    if(saleadmitflg=='0'){//壳上未承認
			       $('#rujin').addClass('hidden');
			    }else{
			       $('#rujin').removeClass('hidden');
			    }
	  	   }else{
	  	   	 	if(saleaddflg=='0'){//壳上未登录
			       $('#rujin').addClass('hidden');
			    }else{
			       $('#rujin').removeClass('hidden');
			    }
	  	   		
	  	   }
	   }
	  //外发登录  
	if( isHavePower(menuNode,[44,45,46,47])==false){
	  	    $('#wfdl').addClass('hidden');
	  }else{
	  	   if(costfinishflg=='0'){//原价未完了
	  	   	  if(assignflg=="1"||Number(mdnum)>1){
	  	   	  	$('#wfdl').removeClass('hidden');
	  	   	  }else{
	  	   	  	$('#wfdl').addClass('hidden');
	  	   	  }
	        }else{
	          $('#wfdl').addClass('hidden');
	       }
	  }
	  //立替
	if( isHavePower(menuNode,[56,57,58,59])==false){
	  	    $('#liti').addClass('hidden');
	  }else{
	  	if(costfinishflg=='0'){//原价未完了
	         if(tranlend=='1'){////代付款和转账 0:不可；1：可;
	         	//已经做割当担当，或割当担当跳过
	         	//if(assignflg=="1"||Number(mdnum)>1){
	  	   	  	  $('#liti').removeClass('hidden');
	  	   	     //}else{
	  	   	  	//	$('#liti').addClass('hidden');
	  	   		//	  }
		        }else{
		       		 $('#liti').addClass('hidden');
			    }
	        }else{
	          $('#liti').addClass('hidden');
	       }
	}
	  //振替
	if( isHavePower(menuNode,[61])==false){
	  	    $('#zhenti').addClass('hidden');
	  }else{
	  	 if(costfinishflg=='0'){//原价未完了
	          if(tranlend=='1'){////代付款和转账 0:不可；1：可;
	          	 //已经做割当担当，或割当担当跳过
	         	// if(assignflg=="1"||Number(mdnum)>1){
	  	   	  	  $('#zhenti').removeClass('hidden');
	  	   	    // }else{
	  	   	  	//	$('#zhenti').addClass('hidden');
	  	   	 	//	}
		        }else{
		       		 $('#zhenti').addClass('hidden');
			    }
	        }else{
	          $('#zhenti').addClass('hidden');
	       }
		
	}
	 //买卖登录票
	 if( isHavePower(menuNode,[36,37,38,39])==false){
	 	$('#mmdlp').addClass('hidden');
	 }
	 else{
	 	  if(saleaddflg=='0'){//壳上登录为空
	       $('#mmdlp').addClass('hidden');
	      }else{
	       $('#mmdlp').removeClass('hidden');	
	      }
	 }
	 
	      
	//原件完了/原价完了取消
	if( isHavePower(menuNode,[14,15,16,17])==false){
	      $('#yjwlqx').addClass('hidden');
	 	  $('#yjwl').addClass('hidden');
	  }else{
	  		if(account_flg==1){//如果洗壳了，全部不惜那是
	  		 $('#yjwlqx').addClass('hidden');
	 	     $('#yjwl').addClass('hidden');
	  	}else{
		  		if(costfinishflg==0){
			 	     $('#yjwlqx').addClass('hidden');
			 	     $('#yjwl').removeClass('hidden');
		     	}else{
			    	$('#yjwl').addClass('hidden');
			    	$('#yjwlqx').removeClass('hidden');
			    	$('#zhenti').addClass('hidden');
			    	$('#liti').addClass('hidden');
			    	$('#wfdl').addClass('hidden');
		    	 }
	  		}
	  }
	 
	//売上承認/取消 
	  if( isHavePower(menuNode,[23,24,25,26])==false){
	  	     $('#saleAdmit').addClass('hidden');
	  	     $('#saleAdmitCancel').addClass('hidden');
	  }else{
		 	  if(saleaddflg=='0'){//壳上未登录
			      $('#saleAdmit').addClass('hidden');
	  	          $('#saleAdmitCancel').addClass('hidden');
		       }else{
			    if(saleadmitflg=='0'){//壳上承认日为空
				  	   $('#saleAdmit').removeClass('hidden');
				       $('#saleAdmitCancel').addClass('hidden');
				}else{
				       $('#saleAdmit').addClass('hidden');
				       $('#saleAdmitCancel').removeClass('hidden');
				       $('#saleEdit').addClass('hidden');
			           $('#saleUpdate').addClass('hidden');
			           $('#saleCancel').addClass('hidden');
				}
		       }
	  }
	 
	   if(account_flg=='1'){
	   	$('#jobupdate').addClass('hidden');
	   	//$('#mdfp').addClass('hidden');///2019.6.20改动，宗哥确认删除
	   	$('#saleUpdate').addClass('hidden');
		$('#saleEdit').addClass('hidden');
		$('#saleCancel').addClass('hidden');
		/*$('#qqsq').addClass('hidden');*///2019.2.15改动，宗哥确认删除
		$('#liti').addClass('hidden');
		$('#zhenti').addClass('hidden');
		$('#saleAdmit').addClass('hidden');
	    $('#saleAdmitCancel').addClass('hidden');
	    $('#yjwlqx').addClass('hidden');
	 	$('#yjwl').addClass('hidden');
	   }
	   
	 if( isHavePower(menuNode,[71,72])==false){
	 	$('#zj').addClass('hidden');
	 }else{
	 	$('#zj').removeClass('hidden');
	 }
	 
	  //売上承認跳过
	if(skip['confirm']==1){
	 	$('#saleAdmit').addClass('hidden');
	 	$('#saleAdmitCancel').addClass('hidden');
	}
	  
	   //判断原价完了跳过
	if(skip['cost']==1){
	 	$('#yjwl').addClass('hidden');
	 	$('#yjwlqx').addClass('hidden');
	}
}


function menuInitMore(){
	var rowDataAll = $('#job_login_list').datagrid('getSelections');//获取所有选择的数据
	 $('#qqsqM').removeClass('hidden');
	 $('#fpfxsqM').removeClass('hidden');
	 $('#rujinM').removeClass('hidden');
	 $('#yjwlqxM').removeClass('hidden');
	 $('#yjwlM').removeClass('hidden');
	var saleaddflgnum =0;
	var saleadmitflgnum =0;
	var costfinishflgnum=0;
	var recflgnum=0;//相同入金flg条数
	var jobendflgnum=0;
	var accountflgnum=0;//洗壳状态；0:未；1：终了
	var saleaddflg= rowDataAll[0]['saleaddflg'];
	var saleadmitflg= rowDataAll[0]['saleadmitflg'];
	var costfinishflg= rowDataAll[0]['costfinishflg'];
	var jobendflg=rowDataAll['jobendflg'];//job终了状态 ，0：未终了
	var recflg= rowDataAll[0]['recflg'];
	for(var i =0;i<rowDataAll.length;i++){
		if(rowDataAll[i]['saleaddflg']=='1'){
			saleaddflgnum +=1;
		}
		if(rowDataAll[i]['jobendflg']=='0'){
			jobendflgnum +=1;
		}
		if(rowDataAll[0]['saleadmitflg']==rowDataAll[i]['saleadmitflg']){
			saleadmitflgnum +=1;
		}
		if(rowDataAll[0]['costfinishflg']==rowDataAll[i]['costfinishflg']){
			costfinishflgnum +=1;
		}
		if(rowDataAll[0]['recflg']==rowDataAll[i]['recflg']){
			recflgnum +=1;
		}
		if(rowDataAll[i]['accountflg']==0){
			accountflgnum +=1;
		}
		
	} 
	if(saleaddflgnum==rowDataAll.length/*&&jobendflgnum==rowDataAll.length*/){//全都壳上登录了并且全都job未终了
	  //請求申請 
	    if( isHavePower(menuNode,[27,28,29,30])==false){
	  	    $('#qqsqM').addClass('hidden');
	  　　　}
	    else{
		    $('#qqsqM').removeClass('hidden');
	  　   } 
	  
	  　　　//发票发行申请
	  　　 if( isHavePower(menuNode,[31,32,33,34])==false){
	  	     $('#fpfxsqM').addClass('hidden');
	  　　　}
	  　　 else{
	       	 $('#fpfxsqM').removeClass('hidden');
	  　　 }
	}else{
		$('#qqsqM').addClass('hidden');
		$('#fpfxsqM').addClass('hidden');
	}

	
	if(recflgnum==rowDataAll.length){
		//入金
		if( isHavePower(menuNode,[35])==false){
	  	     $('#rujinM').addClass('hidden');
	    }else{
			 if(skip['confirm']==0){
			 	if(saleadmitflg=='0'){//壳上未承認
			       $('#rujinM').addClass('hidden');
			    }else{
			       $('#rujinM').removeClass('hidden');
			    }
	  	   	    
	  	   }else{
			    if(saleaddflg=='0'){//壳上未登录
			       $('#rujinM').addClass('hidden');
			    }else{
			       $('#rujinM').removeClass('hidden');
			    }
	  	   }
		}
	}else{
		$('#rujinM').addClass('hidden');
	}
	
	if(costfinishflgnum==rowDataAll.length){
		//原件完了/原价完了取消
		if(isHavePower(menuNode,[14,15,16,17])==false){
		    $('#yjwlqxM').addClass('hidden');
		 	$('#yjwlM').addClass('hidden');
		}else{
			if(accountflgnum==rowDataAll.length){//如果都未洗壳了，继续判断
		  		 if(costfinishflg==0){
			 	    $('#yjwlqxM').addClass('hidden');
			 	    $('#yjwlM').removeClass('hidden');
			    }else{
			    	$('#yjwlM').addClass('hidden');
			    	$('#yjwlqxM').removeClass('hidden');
			    }
	  		}else{//都洗壳了,或有洗壳了,也有未洗壳的,全隐藏
	  			$('#yjwlqxM').addClass('hidden');
			 	$('#yjwlM').addClass('hidden');
	  		}
		}
	}else{
		$('#yjwlqxM').addClass('hidden');
		$('#yjwlM').addClass('hidden');
	}
	/*if(checkStatusForRows(rowDataAll,'costfinishflg')==false)
	{
			$('#yjwlqxM').addClass('hidden');
	 		$('#yjwlM').addClass('hidden');
	}*/
	
	if( isHavePower(menuNode,[71,72])==false){
	 	$('#zjM').addClass('hidden');
	 }else{
	 	$('#zjM').removeClass('hidden');
	 }
}
//菜单加载 原价
function menuLoad() {
	var powerList = JSON.parse($.getNodeList());
	var skip = objStorage.getSkip();
	//是否有 lable追加权限
	var myPower = isHavePower(powerList,[72]);
	var  managerPower = isHavePower(powerList,[71]);
	//是否有job一览权限
	var jobPower =  isHavePower(powerList,[5,6,7,8]);
	if(!myPower&&!managerPower){
		$("#menu2").find(".label-add-T").addClass("hidden");
		$("#menu").find(".lable-add").addClass("hidden");
	}
	if(!jobPower){
		$("#menu").find(".price-detail").addClass("hidden");
	}
	
	//先移除所有hidden 全部显示
	$("#menu").find(".cost-up").removeClass("hidden");
	$("#menu").find(".cost-out").removeClass("hidden");
	$("#menu").find(".pay-msg").removeClass("hidden");
	$("#menu").find(".pay-msg-up").removeClass("hidden");
	$("#menu").find(".pay-req").removeClass("hidden");
	$("#menu").find(".pay-ad").removeClass("hidden");
	$("#menu").find(".pay-cal").removeClass("hidden");
	$("#menu2").find(".pay_handle2").removeClass("hidden");
	$("#menu").find(".pay_handle").removeClass("hidden");
	$("#menu").find(".lend-up").removeClass("hidden");
	$("#menu").find(".lend-ad").removeClass("hidden");
	$("#menu").find(".lend-cal").removeClass("hidden");
	$("#menu").find(".tran-up").removeClass("hidden");
	$("#menu").find(".tran-ad").removeClass("hidden");
	$("#menu").find(".tran-cal").removeClass("hidden");
	var rows = $('#cost_list_Two').datagrid('getSelections'); //获取所有选择的数据
	if(rows.length > 1) { //选中了多条
	   var numT = 0;
		var numF = 0;
		for(var i = 0; i < rows.length; i++) {
			//有一条不是支付原价  菜单中不显示支付处理按钮
			if(rows[i].discd != "001") {
				$("#menu2").find(".pay_handle2").addClass("hidden");
				return ;
			}else{
			var payflg = rows[i]['payflgcd']
			if(payflg == "1") { //已经支付
				numT = numT + 1
			} else  {
				numF = numF + 1
			}
	
			}
			//如果是発注登録済或支払登録済或支払申請済状态 不可以做支付处理
			if(rows[i].statuscd==0||rows[i].statuscd==1||rows[i].statuscd==2){
				$("#menu2").find(".pay_handle2").addClass("hidden");
				return ;
			}
			
		}
		//如果数据支付状态不一致  不可以做支付处理
		 if(numT!=0&&numF!=0){
		 	$("#menu2").find(".pay_handle2").addClass("hidden");
				return ;
		 }
		
	} else {
		var row = $('#cost_list_Two').datagrid('getSelected');
		//根据选中的原价区分 dis 将要隐藏的隐藏
		if(row.discd == "001") {
			//将转账代垫款相关的全部隐藏
			$("#menu").find(".tran-up").addClass("hidden");
			$("#menu").find(".tran-ad").addClass("hidden");
			$("#menu").find(".tran-cal").addClass("hidden");
			$("#menu").find(".lend-up").addClass("hidden");
			$("#menu").find(".lend-ad").addClass("hidden");
			$("#menu").find(".lend-cal").addClass("hidden");
			//终了状态
			if(row.statuscd==4){
				$("#menu").find(".pay-msg-up").addClass("hidden");
				$("#menu").find(".pay-req").addClass("hidden");
				$("#menu").find(".pay-ad").addClass("hidden");
				$("#menu").find(".pay-cal").addClass("hidden");
			   /*$("#menu").find(".cost-up").addClass("hidden");*/
				$("#menu").find(".pay-msg").addClass("hidden");
			}
			//2019/6/24外发更新改成,计上之后不可以,宗哥确认
			if(row.accountflgcd==1){
			$("#menu").find(".cost-up").addClass("hidden");	
			}
		
			//再根据数据走到哪判断显示哪些
			//发注更新按钮  支付情报登录济 不显示，支付情报未登录 显示
			//0：発注登録済、
			if(row.statuscd==0){
				$("#menu").find(".pay-msg-up").addClass("hidden");
				$("#menu").find(".pay-req").addClass("hidden");
				$("#menu").find(".pay-ad").addClass("hidden");
				$("#menu").find(".pay-cal").addClass("hidden");
				$("#menu").find(".pay_handle").addClass("hidden");
			}
			//1：支払登録済
			if(row.statuscd == 1) {
				$("#menu").find(".cost-up").addClass("hidden");
				$("#menu").find(".pay-msg").addClass("hidden");
				$("#menu").find(".pay-cal").addClass("hidden");
				
				
						//判断支付申请跳过
			if(skip.pay == 1) {
				$("#menu").find(".pay-req").addClass("hidden");
			}else{
				$("#menu").find(".pay-ad").addClass("hidden");
			}
			
			$("#menu").find(".pay_handle").addClass("hidden");
			} 
			//2：支払申請済
			if(row.statuscd ==2){
				$("#menu").find(".cost-up").addClass("hidden");
				$("#menu").find(".pay-msg").addClass("hidden");
				$("#menu").find(".pay-msg-up").addClass("hidden");
				$("#menu").find(".pay-cal").addClass("hidden");
				$("#menu").find(".pay-req").addClass("hidden");
				$("#menu").find(".pay_handle").addClass("hidden");
			}
			//3：支払承認済;
			if(row.statuscd ==3){
				$("#menu").find(".cost-up").addClass("hidden");
				$("#menu").find(".pay-msg").addClass("hidden");
				$("#menu").find(".pay-req").addClass("hidden");
				$("#menu").find(".pay-msg-up").addClass("hidden");
				$("#menu").find(".pay-ad").addClass("hidden");
			}
			//支付处理
//			if(row.inputno==""||row.inputno==null){
//				$("#menu").find(".pay_handle").addClass("hidden");
//			}
			if(row.jobendflgcd == 1) {
				$("#menu").find(".cost-out").addClass("hidden");
			} 
            
            
			//判断支付申请跳过
			if(skip.pay == 1) {
				$("#menu").find(".pay-req").addClass("hidden");
				//$("#menu").find(".pay-ad").removeClass("hidden");
				//$("#menu").find(".pay-cal").addClass("hidden");
			} 
            if(row.accountflgcd==1&&row.status==3){
				$("#menu").find(".pay-cal").removeClass("hidden");
            }

			//判断权限
			//是否具有发注登录权限
			if(!isHavePower(powerList, [44, 45, 46, 47])) {
				$("#menu").find(".cost-up").addClass("hidden");
			} 

			//是否具有发注书出力权限
			if(!isHavePower(powerList, [48])) {
				$("#menu").find(".cost-out").addClass("hidden");
			} 

			//是否具有支付情报登录  支付情报更新权限
			if(!isHavePower(powerList, [49, 50, 51, 52])) {
				$("#menu").find(".pay-msg").addClass("hidden");
				$("#menu").find(".pay-msg-up").addClass("hidden");
			}

			//是否具有支付申请 权限
			if(!isHavePower(powerList, [53])) {
				$("#menu").find(".pay-req").addClass("hidden");
			} 

			//是否具有支付承认 支付承认取消权限
			if(!isHavePower(powerList, [54])) {
				$("#menu").find(".pay-ad").addClass("hidden");
				$("#menu").find(".pay-cal").addClass("hidden");
			}

			//是否具有 支付完了支付更新权限
			if(!isHavePower(powerList, [55])) {
				$("#menu").find(".pay_handle").addClass("hidden");
			} 
           
        
		} else if(row.discd == "002") {
			//将振替和 发注的全部隐藏
			$("#menu").find(".cost-up").addClass("hidden");
			$("#menu").find(".cost-out").addClass("hidden");
			$("#menu").find(".pay-msg").addClass("hidden");
			$("#menu").find(".pay-msg-up").addClass("hidden");
			$("#menu").find(".pay-req").addClass("hidden");
			$("#menu").find(".pay-ad").addClass("hidden");
			$("#menu").find(".pay-cal").addClass("hidden");
			$("#menu").find(".pay_handle").addClass("hidden");
			$("#menu").find(".tran-up").addClass("hidden");
			$("#menu").find(".tran-ad").addClass("hidden");
			$("#menu").find(".tran-cal").addClass("hidden");
            //3:終了
             if(row.statuscd==3){
            	$("#menu").find(".lend-up").addClass("hidden");
				$("#menu").find(".lend-ad").addClass("hidden");
				$("#menu").find(".lend-cal").addClass("hidden");
            }
			//根据状态判断显示哪些
			//立替更新 立替承认
			//立替承认 济不显示
			if(row.statuscd == 1) {
				$("#menu").find(".lend-up").addClass("hidden");
				$("#menu").find(".lend-ad").addClass("hidden");
             
			}
			//立替承认取消
			//立替承認前 月结后 不显示
			if(row.statuscd != 1 ) {
				$("#menu").find(".lend-cal").addClass("hidden");
			}
//			if(row.statuscd == 1 && row.accountflgcd == 0) {
//				$("#menu").find(".lend-cal").removeClass("hidden");
//			}
           
			//判断权限
			//立替更新 登录权限
			if(!isHavePower(powerList, [56, 57, 58, 59])) {
				$("#menu").find(".lend-up").addClass("hidden");
			} 
			//立替承认 承认取消权限
			if(!isHavePower(powerList, [60])) {
				$("#menu").find(".lend-ad").addClass("hidden");
				$("#menu").find(".lend-cal").addClass("hidden");
			} 

		} else {
			//将发注与立替全部隐藏
			$("#menu").find(".cost-up").addClass("hidden");
			$("#menu").find(".cost-out").addClass("hidden");
			$("#menu").find(".pay-msg").addClass("hidden");
			$("#menu").find(".pay-msg-up").addClass("hidden");
			$("#menu").find(".pay-req").addClass("hidden");
			$("#menu").find(".pay-ad").addClass("hidden");
			$("#menu").find(".pay-cal").addClass("hidden");
			$("#menu").find(".pay_handle").addClass("hidden");
			$("#menu").find(".lend-up").addClass("hidden");
			$("#menu").find(".lend-ad").addClass("hidden");
			$("#menu").find(".lend-cal").addClass("hidden");

             //2:終了
             if(row.statuscd == 2){
            	$("#menu").find(".tran-ad").addClass("hidden");
				$("#menu").find(".tran-up").addClass("hidden");
				$("#menu").find(".tran-cal").addClass("hidden");
            }
			//根绝数据状态判断显示哪些
			//振替更新 振替承認   振替承認済 不显示
			if(row.statuscd == 1) {
				$("#menu").find(".tran-ad").addClass("hidden");
				$("#menu").find(".tran-up").addClass("hidden");
			} 
			//振替承認取消
			//  振替承認前 月结后 不显示
			if(row.statuscd != 1 ) {
				$("#menu").find(".tran-cal").addClass("hidden");
			}
//			if(row.statuscd == 1 && row.accountflgcd== 0) {
//				$("#menu").find(".tran-cal").removeClass("hidden");
//			}
			//权限 
			//振替登录 更新权限
			if(!isHavePower(powerList, [61])) {
				$("#menu").find(".tran-up ").addClass("hidden");
			} 

			//振替承认 振替取消权限
			if(!isHavePower(powerList, [62])) {
				$("#menu").find(".tran-ad ").addClass("hidden");
				$("#menu").find(".tran-cal ").addClass("hidden");
			} 
		}
	}
}


function datagraidMenu(tabId,e){
	         var rowDataAll = $('#'+tabId).datagrid('getSelections'); //获取所有选择的数据
	         if(rowDataAll.length==0){
	         	showErrorHandler("NOT_CHOOSE_FAIL",'info','info');
	         	return ;
	         }
				if(tabId == "cost_list_Two") {
				//三个参数：rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
				//e.preventDefault(); //阻止浏览器捕获右键事件  
				// $(this).datagrid("clearSelections"); //取消所有选中项  
				
				
				if(rowDataAll.length > 1) {
					//选中多条的时候
					$('#menu2').menu('show', {
						//显示右键菜单  
						/*left: e.pageX, //在鼠标点击处显示菜单  
						top: e.pageY*/
						left: '50%',//在鼠标点击处显示菜单  
		          	    top: '50%'
					});
					menuLoad();
				} else {
					//选中单条的时候
					$('#menu').menu('show', {
						//显示右键菜单  
						/*left: e.pageX, //在鼠标点击处显示菜单  
						top: e.pageY*/
						left: '50%',//在鼠标点击处显示菜单  
		          	    top: '50%'
					});
					menuLoad();
				}
			}
			if(tabId == "job_login_list"){
			  //三个参数：rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
	          // $(this).datagrid("clearSelections"); //取消所有选中项  
	          if(rowDataAll.length > 1){
				$('#menu2').menu('show', {  
		               //显示右键菜单  
		               /*left: e.pageX,//在鼠标点击处显示菜单  
		               top: e.pageY */
		               left: '50%',//在鼠标点击处显示菜单  
		          	   top: '50%'
		        	});
	        	    menuInitMore();//调用job_list_service中的菜单初始化方法
	          }else{
	          	//选中单条的时候
	          	$('#menu').menu('show', {  
	               /*//显示右键菜单  
	               left: e.pageX,//在鼠标点击处显示菜单  
		           top: e.pageY  */
		          left: '50%',//在鼠标点击处显示菜单  
		          top: '50%'
	           }); 
	            menuInit();//调用job_list_service中的菜单初始化方法
	          } 
			}
}
function getTitleByLanguage(dataColumns)
{
	//0:中文简体;1:英文;2:日语;3:繁体;
	var languageSelect = $.getLangTyp();
	var dataColumn = "";
	switch (languageSelect)
	{
	case 'en':
		dataColumn = dataColumns[1];
		break;
	case 'jp':
		dataColumn = dataColumns[2];
		break;
	case 'zc':
		dataColumn = dataColumns[0];
		break;
	case 'zt':
		dataColumn = dataColumns[3];
		break;
	}
	return dataColumn;
}

//job一览跳转table设定
function urlforcustom(tableID){
	var powerList = JSON.parse($.getNodeList());
   var bl = isHavePower(powerList,[70]);
    if(!bl){
     showErrorHandler("SYS_VALIDATEPOWER_ERROR",'info','info');
     return ;
    }else{
     if(tableID=="job_login_list"){
         url = $.getJumpPath()+"master/custom_list.html?flg=0";
        window.location.href = url;
	   }else{
	        url = $.getJumpPath()+"master/custom_list.html?flg=1";
	        window.location.href = url;
	   }
   
    }
}