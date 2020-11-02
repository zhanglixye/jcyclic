function getDataByDataGrid(tabID) {
	var tableString = '<table cellspacing="0" class="pb">';
	var frozenColumns = $('#' + tabID).datagrid("options").frozenColumns; // 得到frozenColumns对象  
	var columns = $('#' + tabID).datagrid("options").columns; // 得到columns对象  
	var fields = $('#' + tabID).datagrid('getColumnFields');
	var nameList = new Array();
	var datas = new Array();
	var dataRows = new Array();
	var dataWidths = new Array();
	var datasArray = new Array();
	var datasStyle = new Array();
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
						dataWidths.push({"wpx":frozenColumns[index][i].width});
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
						dataRows.push(frozenColumns[0][i].title);
					}
				}
			}
			for(var i = 0; i < columns[index].length; ++i) {
				if(!columns[index][i].hidden) {
					tableString += '\n<th width="' + columns[index][i].width + '"';
					dataWidths.push({"wpx":columns[index][i].width});
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
                    	dataRows.push(part_language_change_new(columns[index][i].name));
                    }else{
                    	tableString += '>' + columns[index][i].title + '</th>';
                    	dataRows.push(columns[index][i].title);
                    }
				}
			}
			tableString += '\n</tr>';
		});
	}
	datas.push(dataRows);
	// 载入内容
	var rows = $('#' + tabID).datagrid("getData")['originalRows'];
	if(rows == undefined) {
		rows = $('#' + tabID).datagrid("getRows");
	}
	for(var i = 0; i < rows.length; ++i) {
		if(tabID == "suppliersListTab" && (rows[i].cldivcd == "99998" || rows[i].cldivcd == "99999"))
		{
			continue;
		}
		dataRows = new Array();
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
					datasStyle.push({'text-align':nameList[j].align});
				} else {
					tableString += ' style="vnd.ms-excel.numberformat:@;text-align:' + nameList[j].align + ';"';
					datasStyle.push({'text-align':nameList[j].align})
				}

			}
			tableString += '>';
			if(e + 2 == nameList[j].field.length) {
				tableString += rows[i][nameList[j].field.substring(0, e)];
				dataRows.push(rows[i][nameList[j].field.substring(0, e)]);
			} else {

				if(nameList[j].field == "delFlg") {
					var delStr = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('del_flg', delStr);
					dataRows.push(getGridLanguage('del_flg', delStr));
				} else if(nameList[j].field == 'company_type') {
					var companyType = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('company_type', companyType);
					dataRows.push(getGridLanguage('company_type', companyType));
				} else if(nameList[j].field == 'number_rules') {
					var numberRules = rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					tableString += getGridLanguage('number_rules', numberRules);
					dataRows.push(getGridLanguage('number_rules', numberRules));
				} else {
					tableString += rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field];
					dataRows.push(rows[i][nameList[j].field] == undefined ? "" : rows[i][nameList[j].field]);
				}
			}
			tableString += '</td>';
		}
		tableString += '\n</tr>';
		datas.push(dataRows);
	}
	tableString += '\n</table>';
	//return tableString;
	datasArray.push(datas);
	datasArray.push(dataWidths);
	datasArray.push(datasStyle);
	datasArray.push(nameList);
	return datasArray;
}
function exportExcelByTab(tabID, excelName) {
	var datas = getDataByDataGrid(tabID);
	//var sheet = XLSX.utils.aoa_to_sheet(datas[0]);
	var sheet = sheet_from_array_of_arrays(datas);
	sheet['!cols'] =  datas[1];
	sheet['!rows'] = datas[4];
    openDownloadDialog(sheet2blob(sheet,excelName),excelName+'.xlsx');
}

// 将一个sheet转成最终的excel文件的blob对象，然后利用URL.createObjectURL下载
function sheet2blob(sheet, sheetName) {
    sheetName = sheetName || 'sheet1';
    var workbook = {
        SheetNames: [sheetName],
        Sheets: {}
    };
    workbook.Sheets[sheetName] = sheet; // 生成excel的配置项

    var wopts = {
        bookType: 'xlsx', // 要生成的文件类型
        bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
        type: 'binary'
    };
    var wbout = XLSX.write(workbook, wopts);
    var blob = new Blob([s2ab(wbout)], {
        type: "application/octet-stream"
    }); // 字符串转ArrayBuffer
    function s2ab(s) {
        var buf = new ArrayBuffer(s.length);
        var view = new Uint8Array(buf);
        for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
        return buf;
    }
    return blob;
}

function openDownloadDialog(url, saveName) {
    if (typeof url == 'object' && url instanceof Blob) {
        url = URL.createObjectURL(url); // 创建blob地址
    }
    var aLink = document.createElement('a');
    aLink.href = url;
    aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
    var event;
    if (window.MouseEvent) event = new MouseEvent('click');
    else {
        event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
    }
    aLink.dispatchEvent(event);
}

function sheet_from_array_of_arrays(data) {
	var ws = {};
	var dataHeight = [];
	var range = {s: {c:10000000, r:10000000}, e: {c:0, r:0 }};
	for(let R = 0; R !== data[0].length; ++R) {
		dataHeight.push({hpx: 50});
		for(let C = 0; C !== data[0][R].length; ++C) {
			if(range.s.r > R) range.s.r = R;
			if(range.s.c > C) range.s.c = C;
			if(range.e.r < R) range.e.r = R;
			if(range.e.c < C) range.e.c = C;
			var cell_ref = XLSX.utils.encode_cell({c:C,r:R});
			var defaultCellStyle,fmt;
			if (R > 0){
				if (data[3][C]['field'] == 'saleamt' || data[3][C]['field'] == "vatamt" || data[3][C]['field'] == "reqamt" ||
				data[3][C]['field'] == "plancostamt" || data[3][C]['field'] == "costTotalAmt" || data[3][C]['field'] == "costVatTotal" ||
				data[3][C]['field'] == "tax3" || data[3][C]['field'] == "taxTotal" || data[3][C]['field'] == "profit" ||
				data[3][C]['field'] == "payamt" || data[3][C]['field'] == "amt" || data[3][C]['field'] == "plansale" || data[3][C]['field'] == 'tax2'){
					if ($.getDefaultPoint() == '2'){
						 fmt = '#,##0.00';
					}else if ($.getDefaultPoint() == '1'){
						 fmt = '#,##0.0';
					}else {
						 fmt = '#,##0';
					}
					defaultCellStyle = defaultStyle(data[2][C]['text-align'],false,fmt);
					//千分符数字还原
                    data[0][R][C] = Number(recoveryNumber(data[0][R][C]));
				}else if (data[3][C]['field'] == 'client_flg' || data[3][C]['field'] == 'contra_flg' || data[3][C]['field'] == 'pay_flg' || data[3][C]['field'] == 'hdy_flg'){
					defaultCellStyle = defaultStyle(data[2][C]['text-align'],false);
				} else {
					fmt = '@';
					defaultCellStyle = defaultStyle(data[2][C]['text-align'],false,fmt);
				}
				if (data[3][C]['field'] == 'lable'){
                    data[0][R][C] = $(data[0][R][C]).text();
				}
				//字符串是否为日期格式判断
				if(isNaN(data[0][R][C])&&!isNaN(Date.parse(data[0][R][C]))){
					data[0][R][C] = data[0][R][C].replace(/-/g,'/');
				}
			}else {
				defaultCellStyle = defaultStyle('center',true);
			}
			/// 这里生成cell的时候，使用上面定义的默认样式
			var cell = {v: data[0][R][C], s: defaultCellStyle};
			if(cell.v == null) continue;
			/* TEST: proper cell types and value handling */
			if(typeof cell.v === 'number') cell.t = 'n';
			else if(typeof cell.v === 'boolean') cell.t = 'b';
			else if(cell.v instanceof Date) {
				cell.t = 'n'; cell.z = XLSX.SSF._table[14];
				cell.v = this.dateNum(cell.v);
			}
			else cell.t = 's';
			ws[cell_ref] = cell;
        }
	}

	/* TEST: proper range */
	if(range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
	/*test row height add*/
	data.push(dataHeight);
	return ws;
}

function defaultStyle(horizontal,bold,numFmtString) {
	var defaultCellStyle = {
		font: { name: "Meiryo",sz: 10,bold:bold},
		border: {
			color: { auto: 1 }
		},
		alignment:{
			horizontal: "center",
			vertical: "center",
			wrap_text: true
		},
		numFmt:numFmtString,
        border: { // 设置边框
            top: { style: 'thin' },
            bottom: { style: 'thin' },
            left: { style: 'thin' },
            right: { style: 'thin' }
        }
	};
	defaultCellStyle['alignment']['horizontal'] = horizontal;
	return defaultCellStyle;
}
