$(function() {
	
	var salemst = $.getAccountDate();
	/**上传文件input样式  -- 按需调用*/
	file_i18n();
	//初始化表格
	isCheckLogin(1);
	var pars = JSON.parse(sessionStorage.getItem('salepars'));
	//var pers =  {"sale_cd":pars,"pd": salemst};
	var pers =  pars;
	var sideBar = urlPars.parm("sideBar");
	if(sideBar=='sideBar'||pars==null){
		var pers =  {"pd": salemst,"del_flg":"0"};
		var pars = {"del_flg":"0"};
		sessionStorage.removeItem('salepars');
		
	}
	//初始化检索
	var path =$.getAjaxPath()+ "salemstSel";
	$.ajax({
		url:path,
		data:JSON.stringify(pers),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var total = data[$.getRequestDataName()].length;
				for(var i = 0;i<data[$.getRequestDataName()].length;i++) {
			    data[$.getRequestDataName()][i]['rate2']=floatObj.multiply(data[$.getRequestDataName()][i]['rate2'],100);
			    data[$.getRequestDataName()][i]['rate3']=floatObj.multiply(data[$.getRequestDataName()][i]['rate3'],100);
		        data[$.getRequestDataName()][i]['vat_rate']=floatObj.multiply(data[$.getRequestDataName()][i]['vat_rate'],100);
				}
				
				var joblist = loopFun(data[$.getRequestDataName()],[[],[]],['del_flg','tran_lend']);
				if(total==1){
				  if(data[$.getRequestDataName()][0]['sale_cd']==null||data[$.getRequestDataName()][0]['sale_cd']==""){
				  	joblist=[];
				  	total=0;
				  }
				}
				var dataFil = {
					total:total,
					rows:joblist
				}
				initDataGridHandler("saleTypTab",10,"sale_cd","top",true,"isHasFn",data[$.getRequestDataName()]);
				if(dataFil.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				$('#saleTypTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
 				
 				$('#sale_cd').val(pars["sale_cd"])
 				$('#sale_account_cd').val(pars["sale_account_cd"])
 				$('#sale_name').val(pars["sale_name"])
 				$("input[name='radio'][value="+pars["del_flg"]+"]").attr("checked",true); 
              }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});

});


/*检索框的ajax提交*/

function salemstSel()
{
	var pd = $.getAccountDate();

	//获取检索框radio值
	var del_flg = $("input[name='radio']:checked").val();
	var path =$.getAjaxPath()+ "salemstSel";
	var pars = {"sale_cd":$('#sale_cd').val(),"sale_account_cd":$('#sale_account_cd').val(),"sale_name":$('#sale_name').val(),"del_flg":del_flg,"pd":pd};
	sessionStorage.setItem('salepars',JSON.stringify(pars));
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	    $.resetRequestID(data[$.getRequestUserInfoName()].requestID); 
				var total = data[$.getRequestDataName()].length;
				for(var i = 0;i<data[$.getRequestDataName()].length;i++) {
			    data[$.getRequestDataName()][i]['rate2']=floatObj.multiply(data[$.getRequestDataName()][i]['rate2'],100);
			    data[$.getRequestDataName()][i]['rate3']=floatObj.multiply(data[$.getRequestDataName()][i]['rate3'],100);
			    data[$.getRequestDataName()][i]['vat_rate']=floatObj.multiply(data[$.getRequestDataName()][i]['vat_rate'],100);
		        }
				var joblist = loopFun(data[$.getRequestDataName()],[[],[]],['del_flg','tran_lend']);
				if(total==1){
				  if(data[$.getRequestDataName()][0]['sale_cd']==null||data[$.getRequestDataName()][0]['sale_cd']==""){
				  	joblist=[];
				  	total=0;
				  	
				  }
				}
				var dataFil = {
					total:total,
					rows:joblist
				}
				if(dataFil.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
				}
			    $('#saleTypTab').datagrid('options').pageNumber = 1;
				$('#saleTypTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
 
              }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
}

//共通新增/修改的跳转
function saleEdit(pd){
	var mstcd ="";
	if(pd=="0"){//参数pd为0时，为追加
		url = $.getJumpPath()+"master/salescategory_registration.html";//此处拼接内容
        window.location.href = url;
	}else{//当参数pd为1的时候为变更。
		var row = $('#saleTypTab').datagrid('getSelected');
		if(row!=null&&row!=""){//判断参数row,row为表单选中的数据，有值才允许变更
			sale_cd =row.sale_cd;
			satrt_date=row.satrt_date;
			end_date=row.end_date;
			url = $.getJumpPath()+"master/salescategory_registration.html?sale_cd="+sale_cd;//此处拼接内容
            window.location.href = url;
		}else{
			showErrorHandler('NOT_CHOOSE_FAIL','info','info');
		}
	}
}

   
function loopFun(data, listArr, arr) {
	var data = data;
	for(var i = 0; i < arr.length; i++) {
		data = formatStatus(data, listArr[i], arr[i]);
	}
	return data;
}
//对表格中的状态位进行处理
function formatStatus(data, list, colname) {

	var val = "";
	var valEn = "";
	var valJp = "";
	var valHk = "";
	switch(colname) {
		case 'del_flg': //请求状态
			for(var i = 0; i < data.length; i++) {
					switch(data[i][colname]) {
						case "0":
							val =   '有效';
							valEn = 'effective';
							valJp = '有効';
							valHk = '有效';
							break;
						case "1":
							val = '无效';
							valEn = 'invalid';
							valJp = '無効';
							valHk = '无效';
							break;
					}
				data[i].del_flgzc = val;
				data[i].del_flgen = valEn;
				data[i].del_flgjp = valJp;
				data[i].del_flgzt = valHk;
			}
			break;
		case 'tran_lend': //请求状态
			for(var i = 0; i < data.length; i++) {
					switch(data[i][colname]) {
						case "0":
							val =   '不可';
							valEn = 'NO';
							valJp = '不可';
							valHk = '不可';
							break;
						case "1":
							val = '可';
							valEn = 'YES';
							valJp = '可';
							valHk = '可';
							break;
					}
				data[i].tran_lendzc = val;
				data[i].tran_lenden = valEn;
				data[i].tran_lendjp = valJp;
				data[i].tran_lendzt = valHk;
			}
			break;
	}
	//objStorage.reData = data;
	return data;
}
function clearSearch(){
	$("#sale_cd").val('');
	$("#sale_account_cd").val('');
	$("#sale_name").val('');
	$('#effective').prop("checked",true);
}
 