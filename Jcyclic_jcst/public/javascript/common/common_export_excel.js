var idTmr;
/**
 * 方法名 getExplorer
 * 方法的说明 判断浏览器
 * @param 
 * @return String
 * @author作者 王岩
 * @date 日期 2018.05.18
 */
function getExplorer() {
	var explorer = window.navigator.userAgent;
	//ie  
	if(explorer.indexOf("MSIE") >= 0) {
		return 'ie';
	}
	//firefox  
	else if(explorer.indexOf("Firefox") >= 0) {
		return 'Firefox';
	}
	//Chrome  
	else if(explorer.indexOf("Chrome") >= 0) {
		return 'Chrome';
	}
	//Opera  
	else if(explorer.indexOf("Opera") >= 0) {
		return 'Opera';
	}
	//Safari  
	else if(explorer.indexOf("Safari") >= 0) {
		return 'Safari';
	}
}
/**
 * 方法名 exportExcelByTab
 * 方法的说明 列表导出excel
 * @param tabID 表格ID,excelName 导出的excel名称
 * @return 
 * @author作者 王岩
 * @date 日期 2018.05.18
 */
function exportExcelByTab(tabID, excelName) {
	if(getExplorer() == 'ie') {
		//获取Datagride的所有数据集合
		var rows = $('#' + tabID).datagrid("getRows");
		//var rows = $('#'+tabID).datagrid("getData")['originalRows']
		var columns = $('#' + tabID).datagrid("options").columns[0];
		//创建AX对象excel
		var oXL = new ActiveXObject("Excel.Application");
		//获取workbook对象
		var oWB = oXL.Workbooks.Add();
		//激活当前sheet
		var oSheet = oWB.ActiveSheet;
		//设置工作薄名称  
		oSheet.name = excelName;
		for(var i = 0; i < columns.length; i++) {
			oSheet.Cells(1, i + 1).value = columns[i].title;
			oSheet.Columns.AutoFit();
			oSheet.Cells(1, i + 1).HorizontalAlignment = 3;
			oSheet.Cells(1, i + 1).VerticalAlignment = 2;
			//oSheet.Columns(1+":"+(i + 1)).ColumnWidth = columns[i].width;
		}
		for(var i = 0; i < rows.length; i++) {
			for(var j = 0; j < columns.length; j++) {
				if(rows[i][columns[j].field] != null) {
					oSheet.Cells(i + 2, j + 1).value = rows[i][columns[j].field].toString();
				} else {
					oSheet.Cells(i + 2, j + 1).value = "";
				}
				//水平对齐方式枚举* (1-常规，2-靠左，3-居中，4-靠右，5-填充 6-两端对齐，7-跨列居中，8-分散对齐)  
				oSheet.Cells(i + 2, j + 1).HorizontalAlignment = 2;
				//垂直对齐方式枚举*(1-靠上，2-居中，3-靠下，4-两端对齐，5-分散对齐)  
				oSheet.Cells(i + 2, j + 1).VerticalAlignment = 2;
				oSheet.Columns.AutoFit();
			}
		}
		//设置excel可见属性
		oXL.Visible = true;
		try {
			var fname = oXL.Application.GetSaveAsFilename(excelName + ".xlsx", "Excel Spreadsheets (*.xlsx), *.xlsx");
		} catch(e) {
			print("Nested catch caught " + e);
		} finally {
			oWB.SaveAs(fname);

			oWB.Close(savechanges = false);
			//xls.visible = false;
			oXL.Quit();
			oXL = null;
			//结束excel进程，退出完成
			//window.setInterval("Cleanup();",1);
			idTmr = window.setInterval("Cleanup();", 1);

		}
	} else {
		tableToExcel(tabID, excelName);
	}
}
/**
 * 方法名 Cleanup
 * 方法的说明 IE导出后，清除对象
 * @param 
 * @return 
 * @author作者 王岩
 * @date 日期 2018.05.18
 */
function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
}
/**
 * 方法名 tableToExcel
 * 方法的说明 除IE，其他浏览器导出excel
 * @param table 表格ID,name 导出的excel名称
 * @return 
 * @author作者 王岩
 * @date 日期 2018.05.18
 */
//var tableToExcel = (function() {  
//  var uri = 'data:application/vnd.ms-excel;base64,',  
//          template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',  
//          base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },  
//          format = function(s, c) {  
//              return s.replace(/{(\w+)}/g,  
//                      function(m, p) { return c[p]; }) }  
//  return function(table, name) {  
//  	//var tabInnerHtml = $('#'+table).datagrid('getPanel')[0].lastChild.innerHTML;
//  	var tabInnerHtml = ChangeToTable(table);
//  	$('#'+table).datagrid('getData');
//      if (!table.nodeType) table = document.getElementById(table)  ;
//      var ctx = {worksheet: name || 'Worksheet', table: tabInnerHtml};  
//      window.location.href = uri + base64(format(template, ctx))  ;
//  }  
//});
var tableToExcel = (function() {
	var uri = 'data:application/vnd.ms-excel;base64,',
		template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';
	//template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',
	base64 = function(s) {
			return window.btoa(unescape(encodeURIComponent(s)))
		},
		format = function(s, c) {
			return s.replace(/{(\w+)}/g,
				function(m, p) {
					return c[p];
				})
		}
	return function(table, name) {
		var tabInnerHtml = ChangeToTable(table);
		$('#' + table).datagrid('getData');
		if(!table.nodeType) table = document.getElementById(table);
		var ctx = {
			worksheet: name || 'Worksheet',
			table: tabInnerHtml
		};
		//window.location.href = uri + base64(format(template, ctx));
		var para = document.createElement("a");
		para.href = uri + base64(format(template, ctx));
		para.download = name + ".xls";
		para.click();
	}
})()

/**
 * 方法名 ChangeToTable
 * 方法的说明 其他浏览器导出excel时，表格数据转换为字符串
 * @param tabID 表格ID
 * @return String
 * @author作者 王岩
 * @date 日期 2018.05.18
 */
function ChangeToTable(tabID) {
	var tableString = '<table cellspacing="0" class="pb">';
	var frozenColumns = $('#' + tabID).datagrid("options").frozenColumns; // 得到frozenColumns对象  
	var columns = $('#' + tabID).datagrid("options").columns; // 得到columns对象  
	var fields = $('#' + tabID).datagrid('getColumnFields');
	var nameList = new Array();

	// 载入title  
	if(typeof columns != 'undefined' && columns != '') {
		$(columns).each(function(index) {
			tableString += '\n<tr>';
			if(typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
				for(var i = 0; i < frozenColumns[index].length; ++i) {
					if(frozenColumns[index][i].field == "ck") {
						continue;
					}
					if(!frozenColumns[index][i].hidden) {
						tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
						if(typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
							tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
						}
						if(typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
							tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
						}
						if(typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
							nameList.push(frozenColumns[index][i]);
						}
						tableString += '>' + frozenColumns[0][i].title + '</th>';
					}
				}
			}
			for(var i = 0; i < columns[index].length; ++i) {
				if(!columns[index][i].hidden) {
					tableString += '\n<th width="' + columns[index][i].width + '"';
					if(typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
						tableString += ' rowspan="' + columns[index][i].rowspan + '"';
					}
					if(typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
						tableString += ' colspan="' + columns[index][i].colspan + '"';
					}
					if(typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
						nameList.push(columns[index][i]);
					}
					if(tabID != "cost_list_Two" && tabID != "job_login_list" && tabID != "timesheet_list")
                    {
                    	tableString += '>' + part_language_change_new(columns[index][i].name) + '</th>';	
                    }else{
                    	tableString += '>' + columns[index][i].title + '</th>';
                    }
				}
			}
			tableString += '\n</tr>';
		});
	}
	// 载入内容
	var rows = $('#' + tabID).datagrid("getData")['originalRows'];
	if(rows == undefined) {
		rows = $('#' + tabID).datagrid("getRows");
	}
	for(var i = 0; i < rows.length; ++i) {
		tableString += '\n<tr>';
		for(var j = 0; j < nameList.length; ++j) {
			if(nameList[j].field == "ck") {
				continue;
			}
			var e = nameList[j].field.lastIndexOf('_0');

			tableString += '\n<td';
			if(nameList[j].align != 'undefined' && nameList[j].align != '') {
				if(nameList[j].field == "saleamt" || nameList[j].field == "vatamt" || nameList[j].field == "reqamt" ||
					nameList[j].field == "plancostamt" || nameList[j].field == "costTotalAmt" || nameList[j].field == "costVatTotal" ||
					nameList[j].field == "tax3" || nameList[j].field == "taxTotal" || nameList[j].field == "profit" ||
					nameList[j].field == "payamt" || nameList[j].field == "amt") {
					tableString += ' style="vnd.ms-excel.numberformat:#,##0.00;text-align:' + nameList[j].align + ';"';
				} else {
					tableString += ' style="vnd.ms-excel.numberformat:@;text-align:' + nameList[j].align + ';"';
				}

			}
			tableString += '>';
			if(e + 2 == nameList[j].field.length) {
				tableString += rows[i][nameList[j].field.substring(0, e)];
			} else {

				if(nameList[j].field == "delFlg") {
					var delStr = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('del_flg', delStr);
				} else if(nameList[j].field == 'company_type') {
					var companyType = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('company_type', companyType);
				} else if(nameList[j].field == 'number_rules') {
					var numberRules = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('number_rules', numberRules);
				} else {
					tableString += rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
				}
			}
			tableString += '</td>';
		}
		tableString += '\n</tr>';
	}
	tableString += '\n</table>';
	return tableString;
}
/**
 * 方法名 downLoadPdf
 * 方法的说明 接收base64编码格式，转换成需要导出的文件，进行下载
 * @param baseData base64编码格式数据
 * @param firstFileName 文件名
 * @param lastFileName 文件后缀名
 * @author作者 王岩
 * @date 日期 2018.10.22
 */
function downLoadPdf(baseData, firstFileName, lastFileName) {
	var fileName = firstFileName + "." + lastFileName;
	var bytes = window.atob(baseData); //去掉url的头，并转换为byte  
	//处理异常,将ascii码小于0的转换为大于0  
	var content = new ArrayBuffer(bytes.length);
	var ia = new Uint8Array(content);
	for(var i = 0; i < bytes.length; i++) {
		ia[i] = bytes.charCodeAt(i);
	}
	var blob;
	if(lastFileName == "pdf") {
		blob = new Blob([content], {
			"type": "application/pdf"
		});
	} else {
		blob = new Blob([content], {
			"type": "application/excel"
		});
	}

	if(window.navigator.msSaveBlob) {
		window.navigator.msSaveBlob(blob, fileName);
		// msSaveOrOpenBlobの場合はファイルを保存せずに開ける
		window.navigator.msSaveOrOpenBlob(blob, fileName);
	} else {
		var itemA = document.createElement('a');
		itemA.href = window.URL.createObjectURL(blob);
		itemA.download = fileName;
		itemA.click();
	}
}
/**
 * 方法名 OutPutPdfHandler
 * 方法的说明 连接后台接口，返回Pdf数据
 * @param jobNo job编号
 * @param costNo 外发编号
 * @param inputNo 支付编号/立替编号/振替编号
 * @param fName 文件名
 * @param jumpflg 是否执行成功后，返回上级页面。1：不返回。
 * @param str 卖上登录更新页面两个pdf全部打钩 传过来的另一个文件名
 * @param jobDtFlg 传到后台用 请求书与发票书是否是在job详细页面出的   详细页面出的PDF 回数不增加。
 * @param listFlg 1 跳转原价一览 0 job一览
 * @author作者 王岩
 * @date 日期 2018.10.22
 */
function OutPutPdfHandler(jobNo, costNo, inputNo, fName, jumpflg, str, jobDtFlg, listFlg) {
	var langT = localStorage.getItem('language');
	var pdfInfo = {
		jobNo: jobNo,
		costNo: costNo,
		inputNo: inputNo,
		fileName: fName,
		langTyp: langT,
		jobDtFlg: jobDtFlg
	}
	$.ajax({
		url: $.getAjaxPath() + "jobLogOutPut",
		data: JSON.stringify(pdfInfo),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					if(data[$.getRequestDataName()] == 'STATUS_VALIDATEPOWER_ERROR') {
						showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR", "jcst/cost_list.html?view=6");
						return;
					}
					var dfName = getFileNameByExcel(fName);
					//if(dfName != "Closing Sheet") {
						dfName+="_";
						if(inputNo != "" || costNo != "") {
							if(costNo != "") {
								dfName += costNo;
							} else {
								dfName += inputNo;
							}
						} else {
							dfName += jobNo;
						}
					//}

					downLoadPdf(data[$.getRequestDataName()], dfName, "pdf");
					if(str == "invoiceApplication") {
						OutPutPdfHandler(jobNo, "", "", "invoiceApplication", "", "", "", 0);
					} else {
						if(jumpflg != "1") {
							if(fName == "jobCreate") {
								var url = "jcst/job_registration.html";
								window.location.href = $.getJumpPath() + url;
							} else {
								//window.history.back();
								if(listFlg == 1) {
								window.location.href = $.getJumpPath() + "jcst/cost_list.html?view=6";
								} else {
								window.location.href = $.getJumpPath() + "jcst/job_registration_list.html?view=init&menu=se";
								}
							}
						} else {
							if(fName == "invoiceApplication"&&jobDtFlg != "jobDetails") {
								var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
								if($("#invpd").val() == '003') {
									$('#job_login_list').datagrid('deleteRow', rowIndex);
								} else {
									$('#job_login_list').datagrid('updateRow', {
										index: rowIndex,
										row: {
											invflg: "1"
										}
									})
								}

								var rowAll = $('#job_login_list').datagrid("getData")['rows'];
								var total = rowAll.length;
								if(total == 0) {
									$('.switch_table').css('display', 'none');
									$('.switch_table + .switch_table_none').removeClass('hidden');
									$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
								} else {
									var joblist = loopFun(rowAll, [invflglanguage], ['invflg']);
									var dataFil = {
										total: total,
										rows: joblist
									}
									$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
									tableHidden();
								}

							} else if(fName == "billOrder"&&jobDtFlg != "jobDetails") {
								var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
								if($("#invpd").val() == '001') {
									$('#job_login_list').datagrid('deleteRow', rowIndex);
								} else {
									$('#job_login_list').datagrid('updateRow', {
										index: rowIndex,
										row: {
											reqflg:"1"
										}
									})
								}
 								//$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData', dataFil);

								var rowAll = $('#job_login_list').datagrid("getData")['rows'];
								var total = rowAll.length;
								if(total == 0) {
									$('.switch_table').css('display', 'none');
									$('.switch_table + .switch_table_none').removeClass('hidden');
									$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
								} else {
									
									var joblist = loopFun(rowAll, [reqflglanguage], ['reqflg']);
									var dataFil = {
										total: total,
										rows: joblist
									}
									$('#job_login_list').datagrid({
										loadFilter: pagerFilter
									}).datagrid('loadData', dataFil);
									tableHidden();
								}

							}else if(fName == "reportCreate"&&jobDtFlg != "jobDetails"){
								var rowIndex = $('#cost_list_Two').datagrid('getRowIndex', $('#cost_list_Two').datagrid('getSelected'));
								
									$('#cost_list_Two').datagrid('updateRow', {
										index: rowIndex,
										row: {
											outputflgcd: "1"
										}
									})
									
									var rowAll = $('#cost_list_Two').datagrid("getData")['rows'];
								var total = rowAll.length;
								if(total == 0) {
									$('.switch_table').css('display', 'none');
									$('.switch_table + .switch_table_none').removeClass('hidden');
									$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
								} else {
									var costlist = loopFun(rowAll, [objStorage.getOutPutStatus()], ['outputflgcd']);
									var dataFil = {
										total: total,
										rows: costlist
									}
									$('#cost_list_Two').datagrid({
										loadFilter: pagerFilter
									}).datagrid('loadData', dataFil);
								}
								
							}
						}
					}

				} else {
					showErrorHandler("OUT_PDF_ERROR", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
//一览也面 多个PDF出力
function OutPutPdfHandlerMore(jobNo, costNo, inputNo, fName, jumpflg, str, jobDtFlg) {
	var langT = localStorage.getItem('language');
	var pdfInfo = {
		jobList: jobNo,
		costNo: costNo,
		inputNo: inputNo,
		fileName: fName,
		langTyp: langT,
		jobDtFlg: jobDtFlg
	}
	$.ajax({
		url: $.getAjaxPath() + "jobLogOutPut",
		data: JSON.stringify(pdfInfo),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					for(var i = 0; i < data[$.getRequestDataName()].length; i++) {
						var dfName = getFileNameByExcel(fName)+"_";
						dfName += jobNo[i];
						downLoadPdf(data[$.getRequestDataName()][i], dfName, "pdf");
					}

					if(jumpflg != "1") {
						if(fName == "jobCreate") {
							var url = "jcst/job_registration.html";
							window.location.href = $.getJumpPath() + url;
						} else {
							window.history.back();
						}
					} else {
						if(fName == "invoiceApplication") {
							var rows = $('#job_login_list').datagrid("getSelections");  //获取你选择的所有行 
							  //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
							 
							for(var i = 0; i < rows.length; i++) {
								var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
								if($("#invpd").val() == '003') {
									$('#job_login_list').datagrid('deleteRow', rowIndex);
								} else {
									$('#job_login_list').datagrid('updateRow', {
										index: rowIndex,
										row: {
											invflg: "1"
										}
									})
								}
							}
							var rowAll = $('#job_login_list').datagrid("getData")['rows'];
							var total = rowAll.length;
							if(total == 0) {
								$('.switch_table').css('display', 'none');
								$('.switch_table + .switch_table_none').removeClass('hidden');
								$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
							} else {
								var joblist = loopFun(rowAll, invflglanguage, ['invflg']);
								var dataFil = {
									total: total,
									rows: joblist
								}
								$('#job_login_list').datagrid({
									loadFilter: pagerFilter
								}).datagrid('loadData', dataFil);
							}

						} else if(fName == "billOrder") {

							var rows = $('#job_login_list').datagrid("getSelections");  //获取你选择的所有行 
							  //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
							 
							for(var i = 0; i < rows.length; i++) {
								var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
								if($("#invpd").val() == '001') {
									$('#job_login_list').datagrid('deleteRow', rowIndex);
								} else {
									$('#job_login_list').datagrid('updateRow', {
										index: rowIndex,
										row: {
											reqflg: "1"
										}
									})
								}
							}
							var rowAll = $('#job_login_list').datagrid("getData")['rows'];
							var total = rowAll.length;
							if(total == 0) {
								$('.switch_table').css('display', 'none');
								$('.switch_table + .switch_table_none').removeClass('hidden');
								$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
							} else {
								var joblist = loopFun(rowAll, reqflglanguage, ['reqflg']);
								var dataFil = {
									total: total,
									rows: joblist
								}
								$('#job_login_list').datagrid({
									loadFilter: pagerFilter
								}).datagrid('loadData', dataFil);
							}

						}else if(fName=="reportCreate"){
							    
						}
					}

				} else {
					showErrorHandler("OUT_PDF_ERROR", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}

function exportExcelReport(fName, endDate,startDate , cldivID) {
	var langT = localStorage.getItem('language');
	//var fName = "roleListReport";
	var empPars = $.getInputVal();
	var pdfInfo = {
		fileName: fName,
		langTyp: langT,
		startDate: startDate,
		endDate: endDate,
		cldivID: cldivID,
		nickname:empPars.nickname,
		departCD:empPars.departCD,
		level:empPars.level,
		delFlg:empPars.delFlg,
		userCD:empPars.userCD,
		roleID:empPars.roleID
	}
	$.ajax({
		url: $.getAjaxPath() + "exportExcel",
		data: JSON.stringify(pdfInfo),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					if(data[$.getRequestDataName()] == "error") {
						showErrorHandler("OUT_EXCEL_ERROR", "ERROR", "ERROR");
					}else if (data[$.getRequestDataName()] == "DATE_RANGE_ERROR"){
						showErrorHandler("DATE_RANGE_ERROR", "ERROR", "ERROR");
					}else {
						var fileName = getFileNameByExcel(fName);
						downLoadPdf(data[$.getRequestDataName()], fileName, "xls");
					}
				} else {
					showErrorHandler("SYSTEM_ERROR", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}

function uploadCsvFile(actionName, fileID) {
	if($('#'+fileID).val() == "")
	{
		showErrorHandler("CSV_UPLOAD_NOT_FOUND_FILE");
		return;
	}
	var path = $.getUploadFilePath() + actionName;
	var formData = new FormData();
	formData.append("jcCsvFile", document.getElementById(fileID).files[0]);
	$.ajax({
		url: path,
		type: "POST",
		contentType: false,
		processData: false,
		data: formData,
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				if(data.data.status == "error") {
					$.messager.alert("Error", data.data.msg, "error",function(){
						$('#files').filestyle('clear');
					});
				} else {
					var msg = part_language_change_new("CSV_UPLOAD_SUCCESS_MSG");
					var msgNo = part_language_change_new("CSV_UPLOAD_SUCCESS_MSG_No");
					$.messager.alert("Information", msg + data.data.msg + msgNo, "info",function(){
						$('#files').filestyle('clear');
						var href = window.location.href;
						if(href.match(/\/employee_list.html/) !== null) {
							employeeInitHandler('searchFlg');
						} else {
							queryform();
						}
					});
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorFunHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

function getFileNameByExcel(fName)
{
	var fileName = fName;
	switch (fName){
		case "employeesPowerList":
			fileName = "StaffAccessSecurity";
			break;
		case "jobList":
			fileName = "JobStatusList";
			break;
		case "jobLabelsReport":
			fileName = "JobStatusList(Label)";
			break;
		case "monthBalance":
			fileName = "SalesReport";
			break;
		case "predestineCostList":
			fileName = "EstimatedCostReport";
			break;
		//pdf
		case "jobCreate":
			fileName = "JobEntry";
			break;
		case "jobEdit":
			fileName = "JobUpdate";
			break;
		case "billOrder":
			fileName = "InvoiceRequest";
			break;
		case "invoiceApplication":
			fileName = "ReceiptRequest";
			break;
		case "saleAdminPdf":
			fileName = "SalesApproval";
			break;
		case "jobDetailSale":
			fileName = "Closing Sheet";
			break;
		case "payCreate":
			fileName = "ICS";
			break;
		case "confirmPay":
			fileName = "PaymentApproval";
			break;
		case "payRequest":
			fileName = "PaymentRequest";
			break;
		case "reportCreate":
			fileName = "PurchaseOrder";
			break;
		case "deboursCreate":
			fileName = "ICS";
			break;
		case "confirmDebours":
			fileName = "ReimbursedCostApproval";
			break;
		case "onCostCreate":
			fileName = "ICS";
			break;
		case "confirmOncost":
			fileName = "TransferredCostApproval";
			break;
	}
	return fileName;
}

