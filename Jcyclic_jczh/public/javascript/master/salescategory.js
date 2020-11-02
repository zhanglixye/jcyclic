var init = function(){
	salescateObj = {
	 	editRow : undefined,
	 	save:function(){
	        
			//当条数大于两条时，需要做的判断，并更改第二行的结束时间为第一行的开始时间
	 		if($('#salescategory').datagrid('getData').rows.length>1){
	 		//关闭dategraid编辑器
	 		$('#salescategory').datagrid('endEdit',this.editRow);
	 		//判断第一行的开始时间要比第二号的开始时间大最少两天
			var start_date =$('#salescategory').datagrid('getData').rows[0].start_date;	
			var vat_rate =$('#salescategory').datagrid('getData').rows[0].vat_rate;	
			var two_start_date =$('#salescategory').datagrid('getData').rows[1].start_date;
			var one_start_date =Date.parse(start_date);
			var two_start_date =Date.parse(two_start_date);
			dateSpan = one_start_date - two_start_date;//第一行日期减第二行日期
	        dateSpan = Math.abs(dateSpan);
	       // iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
	         iDays =Math.floor(floatObj.divide(dateSpan,floatObj.multiply(floatObj.multiply(24,3600),1000)));
			//判断税率是否为空
			if(vat_rate==null||vat_rate==''||start_date==''||vat_rate=="0.00"){
				showErrorHandler('NOT_EMPTY','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				if(start_date == null || start_date == ''){
						$('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
						validate($('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
					}
					if(vat_rate==null||vat_rate==''){
						$('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="vat_rate"] div span.textbox').addClass('border_red');
						validate($('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="vat_rate"] div span.textbox'),part_language_change_new('NODATE'));
						$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
							inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
								change: function test(event) {
									var newValue = event.target.value;
									if(/^\d+(\.\d+|\.)?$/.test(newValue)){
										var val = number_va_length(newValue,7,2);
										$(event.target).val(val);
									}else{
										$(event.target).val('');
									}
								},
								keyup: function test(event) {
									var newValue = event.target.value;
									if(/^\d+(\.\d+|\.)?$/.test(newValue)){
										var val = number_va_length(newValue,7,2);
										$(event.target).val(val);
									}else{
										$(event.target).val('');
									}
								},
							})
						});
				}
				return;
			}
			//验证新开始时间大于原开始时间
			else if(one_start_date - two_start_date<0){
				showErrorHandler('TAXTYPE_DATETWO','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			//验证新开始时间大于原开始时间2天以上
			}else if(iDays<1){
				showErrorHandler('TAXTYPE_DATETWO','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			}/*else if(one_start_date<new Date(new Date().format_extend('yyyy-MM-dd'))){
				showErrorHandler('TART_DATE_SYSDATE','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			}*/
			else{
				var row = $('#salescategory').datagrid('getData').rows[0];
				//document.getElementById('vat_start_time').value = row.start_date;
				$("#vat_start_date").val(row.start_date);
				$("#vat_end_date").val(row.end_date);
				$("#vat_rate").val(row.vat_rate);
				$("#p_vat_rate").val(row.vat_rate);			
			var start_date = new Date(start_date);
			start_date.setTime(start_date.getTime()-24*60*60*1000);
			var new_enddate = new Date(start_date).format("yyyy-mm-dd");
				$('#salescategory').datagrid('updateRow',{
					index:1,
					row:{
						end_date:new_enddate
					}
				})
			}
			}else{//若条数少于2不需要判断，直接赋值
					//关闭dategraid编辑器
	 			$('#salescategory').datagrid('endEdit',this.editRow);
	 			var start_date =$('#salescategory').datagrid('getData').rows[0].start_date;	
				var vat_rate =$('#salescategory').datagrid('getData').rows[0].vat_rate;	
				if($('#salescategory').datagrid('getData').rows[0]==null){
					showErrorHandler('TAXTYPE_DATETWO','info','info');
					return;
				}
				//验证时间不能小于系统时间
				/*else if(new Date(start_date)<new Date(new Date().format_extend('yyyy-MM-dd'))){
				showErrorHandler('TART_DATE_SYSDATE','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			    }*/else{
				if(vat_rate==null||vat_rate==''||start_date==''||vat_rate=="0.00"){
					showErrorHandler('NOT_EMPTY','info','info');
					$('#salescategory').datagrid('beginEdit',this.editRow);
					if(start_date == null || start_date == ''){
						$('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
						validate($('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
					}
					if(vat_rate==null||vat_rate==''){
						$('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="vat_rate"] div span.textbox').addClass('border_red');
						validate($('#salescategory').datagrid('getPanel').find('.datagrid-body td[field="vat_rate"] div span.textbox'),part_language_change_new('NODATE'));
                        $('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
                            inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
                                change: function test(event) {
                                    var newValue = event.target.value;
                                    if(/^\d+(\.\d+|\.)?$/.test(newValue)){
                                        var val = number_va_length(newValue,7,2);
                                        $(event.target).val(val);
                                    }else{
                                        $(event.target).val('');
                                    }
                                },
                                keyup: function test(event) {
                                    var newValue = event.target.value;
                                    if(/^\d+(\.\d+|\.)?$/.test(newValue)){
                                        var val = number_va_length(newValue,7,2);
                                        $(event.target).val(val);
                                    }else{
                                        $(event.target).val('');
                                    }
                                },
                            })
                        });
					}
					return;
				}else{
				$('#salescategory').datagrid('endEdit',this.editRow);
				var row = $('#salescategory').datagrid('getData').rows[0];
				//document.getElementById('vat_start_time').value = row.start_date;
				$("#vat_start_date").val(row.start_date);
				$("#vat_end_date").val(row.end_date);
				$("#vat_rate").val(row.vat_rate);
				$("#p_vat_rate").val(row.vat_rate);
				}
			}
			}
			var vat_rate =$('#salescategory').datagrid('getData').rows[0].vat_rate;	
			
			$('#myModal').modal('hide');
		},
		add:function(){
			//this.editRow = 0;
			//添加一行
			if(this.editRow != 0){
				$('#salescategory').datagrid('insertRow',{
					index:0,
					row:{
						end_date:'9999-12-31'
					}
				})
				var e = $("#salescategory").datagrid('getColumnOption', 'end_date');
                e.editor = {};
				//将第一行设置成可编辑的状态
				$('#salescategory').datagrid('beginEdit',0);
				this.editRow = 0;
				$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
			        inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
			            change: function test(event) {
			            	var newValue = event.target.value;
			                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
								var val = number_va_length(newValue,7,2);
								$(event.target).val(val);
							}else{
								$(event.target).val('');
							}
			            },
						keyup: function test(event) {
							var newValue = event.target.value;
							if(/^\d+(\.\d+|\.)?$/.test(newValue)){
								var val = number_va_length(newValue,7,2);
								$(event.target).val(val);
							}else{
								$(event.target).val('');
							}
						},
			        })
			    });
			}
			var langu = localStorage.getItem('language');
			easyUiLanguageDatagrid2(langu);
		}
	 }
	obj_T = {
	 	editRow : undefined,
	 	save:function(){
	 		var start_date="";
	 		var rate2="";
	 		var rate3="";
			//当条数大于两条时，需要做的判断，并更改第二行的结束时间为第一行的开始时间
	 		if($('#salescategory_T').datagrid('getData').rows.length>1){
	 		//关闭dategraid编辑器
	 		$('#salescategory_T').datagrid('endEdit',this.editRow);
	 		//判断第一行的开始时间要比第二号的开始时间大最少两天
			start_date =$('#salescategory_T').datagrid('getData').rows[0].start_date;
			rate2 =$('#salescategory_T').datagrid('getData').rows[0].rate2;
			rate3 =$('#salescategory_T').datagrid('getData').rows[0].rate3;	
			var two_start_date =$('#salescategory_T').datagrid('getData').rows[1].start_date;
			var one_start_date =Date.parse(start_date);
			var two_start_date =Date.parse(two_start_date);
			dateSpan = one_start_date - two_start_date;//第一行日期减第二行日期
	        dateSpan = Math.abs(dateSpan);
	        //iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
	        iDays =Math.floor(floatObj.divide(dateSpan,floatObj.multiply(floatObj.multiply(24,3600),1000)));
			//判断税率不为空
			if(rate2==null||rate2==''||rate3==null||rate3==''||start_date==''||rate2=='0.00'||rate3=='0.00'){
				showErrorHandler('NOT_EMPTY','info','info');
				$('#salescategory_T').datagrid('beginEdit',this.editRow);
				if(rate2 == null || rate2 == ''){
					$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate2"] div span.textbox').addClass('border_red');
					validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate2"] div span.textbox'),part_language_change_new('NODATE'));
					$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
						inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
							change: function test() {
								var newValue = event.target.value;
								if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
							},
							keyup: function test() {
								var newValue = event.target.value;
								if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
							},
						})
					});
				}
				if(rate3 == null || rate3 == ''){
					$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate3"] div span.textbox').addClass('border_red');
					validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate3"] div span.textbox'),part_language_change_new('NODATE'));
					$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
						inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
							change: function test() {
								var newValue = event.target.value;
								if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
							},
							keyup: function test() {
								var newValue = event.target.value;
								if(/^\d+(\.\d+|\.)?$/.test(newValue)){
									var val = number_va_length(newValue,7,2);
									$(event.target).val(val);
								}else{
									$(event.target).val('');
								}
							},
						})
					});
				}
				if(start_date == null || start_date == ''){
					$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
					validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
				}
				return;
			}
			//验证新开始时间大于原开始时间
			else if(one_start_date - two_start_date<0){
				showErrorHandler('TAXTYPE_DATETWO','info','info');
				$('#salescategory_T').datagrid('beginEdit',this.editRow);
				return;
			}
			//验证新开始时间大于原开始时间2天以上
			else if(iDays<1){
				showErrorHandler('TAXTYPE_DATETWO','info','info');
				$('#salescategory_T').datagrid('beginEdit',this.editRow);
				return;
			}/*else if(one_start_date<new Date(new Date().format_extend('yyyy-MM-dd'))){
				showErrorHandler('TART_DATE_SYSDATE','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			}*/
			else{
				var row = $('#salescategory_T').datagrid('getData').rows[0];
				$("#start_date").val(row.start_date);
				$("#end_date").val(row.end_date);
				$("#rate2").val(row.rate2);
				$("#rate3").val(row.rate3);
			var start_date = new Date(start_date);
			start_date.setTime(start_date.getTime()-24*60*60*1000);
			var new_enddate = new Date(start_date).format("yyyy-mm-dd");
				$('#salescategory_T').datagrid('updateRow',{
					index:1,
					row:{
						end_date:new_enddate
					}
				})
			}
			}else{//若条数少于2
				//关闭dategraid编辑器
	 			$('#salescategory_T').datagrid('endEdit',this.editRow);
	 			start_date =$('#salescategory_T').datagrid('getData').rows[0].start_date;
				rate2 =$('#salescategory_T').datagrid('getData').rows[0].rate2;
				rate3 =$('#salescategory_T').datagrid('getData').rows[0].rate3;	
				if($('#salescategory_T').datagrid('getData').rows[0]==null){
					showErrorHandler('TAXTYPE_DATETWO','info','info');
					return;
				}else{
				
				if(rate2==null||rate2==''||rate3==null||rate3==''||start_date==''||rate2=='0.00'||rate3=='0.00'){
					showErrorHandler('NOT_EMPTY','info','info');
					$('#salescategory_T').datagrid('beginEdit',this.editRow);
					if(rate2 == null || rate2 == ''){
						$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate2"] div span.textbox').addClass('border_red');
						validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate2"] div span.textbox'),part_language_change_new('NODATE'));
					}
					if(rate3 == null || rate3 == ''){
						$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate3"] div span.textbox').addClass('border_red');
						validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="rate3"] div span.textbox'),part_language_change_new('NODATE'));
					}
					if(start_date == null || start_date == ''){
						$('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
						validate($('#salescategory_T').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
					}
                    $('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
                        inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
                            change: function test() {
                                var newValue = event.target.value;
                                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
                                    var val = number_va_length(newValue,7,2);
                                    $(event.target).val(val);
                                }else{
                                    $(event.target).val('');
                                }
                            },
                            keyup: function test() {
                                var newValue = event.target.value;
                                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
                                    var val = number_va_length(newValue,7,2);
                                    $(event.target).val(val);
                                }else{
                                    $(event.target).val('');
                                }
                            },
                        })
                    });
					return;
				}
				//验证时间不能小于系统时间
				/*else if(new Date(start_date)<new Date(new Date().format_extend('yyyy-MM-dd'))){
				showErrorHandler('TART_DATE_SYSDATE','info','info');
				$('#salescategory').datagrid('beginEdit',this.editRow);
				return;
			    }*/
				else{
					$('#salescategory_T').datagrid('endEdit',this.editRow);
					var row = $('#salescategory_T').datagrid('getData').rows[0];
					var ss= row.start_date;
					$("#start_date").val(row.start_date);
					$("#end_date").val(row.end_date);
					$("#rate2").val(row.rate2);
					$("#rate3").val(row.rate3);
				}
				}
			}		
		$('#myModal_T').modal('hide');	
		},
		add:function(){
			//添加一行
			if(this.editRow != 0){
				$('#salescategory_T').datagrid('insertRow',{
					index:0,
					row:{
						end_date:'9999-12-31'
					}
				})
				var e = $("#salescategory_T").datagrid('getColumnOption', 'end_date');
                e.editor = {};
				//将第一行设置成可编辑的状态
				$('#salescategory_T').datagrid('beginEdit',0);
				this.editRow = 0;
				$('.datagrid-editable-input.textbox-f').not('.combo-f').textbox({
			        inputEvents: $.extend({}, $.fn.textbox.defaults.inputEvents, {
			            change: function test() {
			                var newValue = event.target.value;
			                if(/^\d+(\.\d+|\.)?$/.test(newValue)){
								var val = number_va_length(newValue,7,2);
								$(event.target).val(val);
							}else{
								$(event.target).val('');
							}
			            },
						keyup: function test() {
							var newValue = event.target.value;
							if(/^\d+(\.\d+|\.)?$/.test(newValue)){
								var val = number_va_length(newValue,7,2);
								$(event.target).val(val);
							}else{
								$(event.target).val('');
							}
						},
			        })
			    });
			}
			var langu = localStorage.getItem('language');
			easyUiLanguageDatagrid2(langu);
		}
	}
}
$(function(){
	 init();
})