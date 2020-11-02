/**
 * 方法名 calculateSaleHandler
 * 方法的说明  计算卖上金额、卖上增值税、请求金额。使用saleCurCode参数来判断是否为外货
 * 
 * @param saleMonet 卖上入力金额
 * @param saleCurCode 卖上换算CODE
 * @param saleCode 卖上外货code
 * @param saleVatRate 卖上增值税率
 * @param salePoint 本国货币小数点位数
 * @param saleIsHave 税入税拔，0：税拔；1：税入
 * * ***********add params by wy 2018.09.04
 * @param foreignFormatFlg 外货端数flg，0051 001
 * @param saleVatFormatFlg 卖上增值税端数flg 0052 001
 * 
 * @return Object
 * @author 王岩
 * @date  2018.06.13
 */
/*
function calculateSaleHandler(saleMonet,saleCurCode,saleCode,saleVatRate,salePoint,saleIsHave,
		foreignFormatFlg,saleVatFormatFlg)
{
	//卖上金额
	var saleBase = 0.0;
	//卖上增值税
	var saleVatBase = 0.0;
	//请求金额
	var reqAmtBase = 0.0;
	
	//卖上非外货
	if(saleCurCode == "")
	{
		//税拔
		if(saleIsHave == 0)
		{
			//卖上金额=入力金额
			saleBase = pointFormatHandler(parseFloat(saleMonet),foreignFormatFlg,salePoint);
			//卖上增值税=入力金额*税率
			saleVatBase = parseFloat(saleMonet) * parseFloat(saleVatRate);
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//请求金额=入力金额+卖上增值税
			reqAmtBase = parseFloat(saleMonet) + parseFloat(saleVatBase);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
		}else{
		//税入
			//请求金额=入力金额
			reqAmtBase = parseFloat(saleMonet);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
			//卖上增值税=请求金额*税率/（1+税率）
			saleVatBase = parseFloat(saleMonet) * parseFloat(saleVatRate) / (1+parseFloat(saleVatRate));
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//卖上金额=请求金额-卖上增值税
			saleBase = parseFloat(saleMonet) - parseFloat(saleVatBase);
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
		}
	}else{
		//入力金额*code/换算code
		var money = parseFloat(saleMonet) * parseFloat(saleCurCode) / parseFloat(saleCode);
		//税拔
		if(saleIsHave == 0)
		{
			//卖上金额=入力金额*code/换算code
			saleBase = parseFloat(money);
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
			//卖上增值税=卖上金额*税率
			saleVatBase = parseFloat(money) * parseFloat(saleVatRate);
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//请求金额=卖上金额+卖上增值税
			reqAmtBase = parseFloat(money) + parseFloat(saleVatBase);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
		}else{
		//税入
			//请求金额=入力金额*code/换算code
			reqAmtBase = parseFloat(money);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
			//卖上增值税=请求金额*税率/（1+税率）
			saleVatBase = parseFloat(money) * parseFloat(saleVatRate) / (1+parseFloat(saleVatRate));
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//卖上金额=请求金额-卖上增值税
			saleBase = parseFloat(money) - parseFloat(saleVatBase);
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
		}
	}
	return {"saleBase":saleBase,"saleVatBase":saleVatBase,"reqAmtBase":reqAmtBase};
}
*/
function calculateSaleHandler(saleMonet,saleCurCode,saleCode,saleVatRate,salePoint,saleIsHave,
		foreignFormatFlg,saleVatFormatFlg)
{
	//卖上金额
	var saleBase = 0.0;
	//卖上增值税
	var saleVatBase = 0.0;
	//请求金额
	var reqAmtBase = 0.0;
	
	//卖上非外货
	if(saleCurCode == "")
	{
		//税拔
		if(saleIsHave == 0)
		{
			//卖上金额=入力金额
			saleBase = pointFormatHandler(parseFloat(saleMonet),foreignFormatFlg,salePoint);
			//卖上增值税=入力金额*税率
			saleVatBase = floatObj.multiply(parseFloat(saleMonet),parseFloat(saleVatRate));
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//请求金额=入力金额+卖上增值税
			reqAmtBase = floatObj.add(parseFloat(saleMonet),parseFloat(saleVatBase));
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
		}else{
		//税入
			//请求金额=入力金额
			reqAmtBase = parseFloat(saleMonet);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
			//卖上增值税=请求金额*税率/（1+税率）
			saleVatBase = floatObj.divide(floatObj.multiply(parseFloat(saleMonet),parseFloat(saleVatRate)),floatObj.add(1,parseFloat(saleVatRate)))
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//卖上金额=请求金额-卖上增值税
			saleBase = floatObj.subtract(parseFloat(saleMonet) , parseFloat(saleVatBase));
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
		}
	}else{
		//入力金额*code/换算code
		var money = floatObj.divide(floatObj.multiply(parseFloat(saleMonet),parseFloat(saleCurCode)),parseFloat(saleCode));
		//税拔
		if(saleIsHave == 0)
		{
			//卖上金额=入力金额*code/换算code
			saleBase = parseFloat(money);
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
			//卖上增值税=卖上金额*税率
			saleVatBase = floatObj.multiply(parseFloat(money),parseFloat(saleVatRate));
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//请求金额=卖上金额+卖上增值税
			reqAmtBase = floatObj.add(parseFloat(money) , parseFloat(saleVatBase));
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
		}else{
		//税入
			//请求金额=入力金额*code/换算code
			reqAmtBase = parseFloat(money);
			reqAmtBase = pointFormatHandler(reqAmtBase,foreignFormatFlg,salePoint);
			//卖上增值税=请求金额*税率/（1+税率）
			saleVatBase = floatObj.divide(floatObj.multiply(parseFloat(money),parseFloat(saleVatRate)),floatObj.add(1,parseFloat(saleVatRate))); 
			saleVatBase = pointFormatHandler(saleVatBase,saleVatFormatFlg,salePoint);
			//卖上金额=请求金额-卖上增值税
			saleBase = floatObj.subtract(parseFloat(money) , parseFloat(saleVatBase))
			saleBase = pointFormatHandler(saleBase,foreignFormatFlg,salePoint);
		}
	}
	return {"saleBase":saleBase,"saleVatBase":saleVatBase,"reqAmtBase":reqAmtBase};
}
/**
 * 方法名 calculateCostHandler
 * 方法的说明  计算原价金额、支付增值税、支付金额。使用costCurCode参数来判断是否为外货
 * 
 * @param costMonet 入力金额
 * @param costCurCode 换算CODE
 * @param costCode 外货code
 * @param costVatRate 成本增值税率
 * @param costPoint 本国货币小数点位数
 * @param costIsHave 税入税拔，0：税拔；1：税入
 * * ***********add params by wy 2018.09.04
 * @param foreignFormatFlg 外货端数flg，0051 001
 * @param costVatFormatFlg 仕入增值税端数flg 0052 002
 * 
 * @return Object
 * @author 王岩
 * @date  2018.06.13
 */
/*
function calculateCostHandler(costMonet,costCurCode,costCode,costVatRate,costPoint,costIsHave,
	foreignFormatFlg,costVatFormatFlg)
{
	var costBase = 0.0;
	var costVatBase = 0.0;
	var payBase = 0.0;
	//成本非外货
	if(costCurCode == "")
	{
		//税拔
		if(costIsHave == 0)
		{
			//原价金额=入力金额
			costBase = parseFloat(costMonet);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
			//支付增值税=原价金额*税率
			costVatBase = parseFloat(costMonet) * parseFloat(costVatRate);
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//支付金额=原价金额+支付增值税
			payBase = parseFloat(costMonet) + parseFloat(costVatBase);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
		}else{
		//税入
			//支付金额=入力金额
			payBase = parseFloat(costMonet);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
			//支付增值税=支付金额*税率/（1+税率）
			costVatBase = parseFloat(costMonet) * parseFloat(costVatRate) / (1+parseFloat(costVatRate));
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//原价金额=支付金额-支付增值税
			costBase = parseFloat(costMonet) - parseFloat(costVatBase);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
		}
	}else{
		//入力金额*code/换算code
		var money = parseFloat(costMonet) * parseFloat(costCurCode) / parseFloat(costCode);
		//税拔
		if(costIsHave == 0)
		{
			//原价金额=入力金额*code/换算code
			costBase = parseFloat(money);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
			//支付增值税=原价金额*税率
			costVatBase = parseFloat(money) * parseFloat(costVatRate);
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//支付金额=原价金额+支付增值税
			payBase = parseFloat(money) + parseFloat(costVatBase);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
		}else{
		//税入
			//支付金额=入力金额*code/换算code
			payBase = parseFloat(money);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
			//支付增值税=支付金额*税率/（1+税率）
			costVatBase = parseFloat(money) * parseFloat(costVatRate) / (1+parseFloat(costVatRate));
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//原价金额=支付金额-支付增值税
			costBase = parseFloat(money) - parseFloat(costVatBase);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
		}
	}
	return {"costBase":costBase,"costVatBase":costVatBase,"payBase":payBase};
}
*/
function calculateCostHandler(costMonet,costCurCode,costCode,costVatRate,costPoint,costIsHave,
	foreignFormatFlg,costVatFormatFlg)
{
	var costBase = 0.0;
	var costVatBase = 0.0;
	var payBase = 0.0;
	//成本非外货
	if(costCurCode == "")
	{
		//税拔
		if(costIsHave == 0)
		{
			//原价金额=入力金额
			costBase = parseFloat(costMonet);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
			//支付增值税=原价金额*税率
			costVatBase = floatObj.multiply(parseFloat(costMonet) , parseFloat(costVatRate)); 
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//支付金额=原价金额+支付增值税
			payBase = floatObj.add(parseFloat(costMonet) , parseFloat(costVatBase));
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
		}else{
		//税入
			//支付金额=入力金额
			payBase = parseFloat(costMonet);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
			//支付增值税=支付金额*税率/（1+税率）
			costVatBase = floatObj.divide(floatObj.multiply(parseFloat(costMonet),parseFloat(costVatRate)),floatObj.add(1,parseFloat(costVatRate)));
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//原价金额=支付金额-支付增值税
			costBase = floatObj.subtract(parseFloat(costMonet) , parseFloat(costVatBase));
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
		}
	}else{
		//入力金额*code/换算code
		var money = floatObj.divide(floatObj.multiply(parseFloat(costMonet) , parseFloat(costCurCode)),parseFloat(costCode))
		//税拔
		if(costIsHave == 0)
		{
			//原价金额=入力金额*code/换算code
			costBase = parseFloat(money);
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
			//支付增值税=原价金额*税率
			costVatBase = floatObj.multiply(parseFloat(money),parseFloat(costVatRate));
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//支付金额=原价金额+支付增值税
			payBase = floatObj.add(parseFloat(money) , parseFloat(costVatBase));
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
		}else{
		//税入
			//支付金额=入力金额*code/换算code
			payBase = parseFloat(money);
			payBase = pointFormatHandler(payBase,foreignFormatFlg,costPoint);
			//支付增值税=支付金额*税率/（1+税率）
			costVatBase = floatObj.divide(floatObj.multiply(parseFloat(money) , parseFloat(costVatRate)),floatObj.add(1,parseFloat(costVatRate)));
			costVatBase = pointFormatHandler(costVatBase,costVatFormatFlg,costPoint);
			//原价金额=支付金额-支付增值税
			costBase = floatObj.subtract(parseFloat(money) , parseFloat(costVatBase));
			costBase = pointFormatHandler(costBase,foreignFormatFlg,costPoint);
		}
	}
	return {"costBase":costBase,"costVatBase":costVatBase,"payBase":payBase};
}
/**
 * 方法名 calculateTaxHandler
 * 方法的说明  计算文化税，附加税，税金合计，营收，营收率
 * 
 * @param planSale		预计卖上金额
 * @param saleAmt		实际卖上金额
 * @param planCost		预计成本合计
 * @param costTotal		实际成本合计
 * @param reqAmt		请求金额
 * @param payAmt		支付金额
 * @param saleVatAmt	卖上增值税
 * @param costVatAmt	仕入增值税（成本增值税）
 * @param rate			文化税率
 * @param rate1			附加税率
 * @param isCostFinsh	是否成本录入终止，0：未终止；1：终止
 * @param costCountNums	成本条数
 * ***********add params by wy 2018.09.04
 * @param point	本国货币小数点位数
 * @param foreignFormatFlg 外货端数flg，0051 001
 * @param taxFormatFlg	0052 003
 * 
 * @return Object
 * @author 王岩
 * @date  2018.06.13
 */
/*
function calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,point,taxFormatFlg,foreignFormatFlg)
{
	var tax = 0.0;
	var tax1 = 0.0;
	var taxTotal = 0.0;
	var profit = 0.0;
	var profitRate = 0.0;
	
	var saleBaseAmt = planSale;
	var costTotalAmt = planCost;
	//实际卖上不为空，使用实际
	if(saleAmt != "")
	{
		saleBaseAmt = parseFloat(saleAmt);
	}
	//是否有成本
	if(costCountNums > 0)
	{
		//使用实际成本
		costTotalAmt = costTotal;
	}else{
		//没有成本并且成本终止录入，实际成本为0
		if(isCostFinsh == 1)
		{
			costTotalAmt = 0.0;
		}
	}
	//文化建设税 = （请求金额-支付金额）*税率1
	tax = (parseFloat(reqAmt) - parseFloat(payAmt)) * parseFloat(rate);
	tax = pointFormatHandler(tax,taxFormatFlg,point);
	//增值附加税 = （卖上增值税-支付增值税）*税率2
	tax1 = (parseFloat(saleVatAmt) - parseFloat(costVatAmt)) * parseFloat(rate1);
	tax1 = pointFormatHandler(tax1,taxFormatFlg,point);
	//税金合计 = 文化+增值税附加
	taxTotal = parseFloat(tax) + parseFloat(tax1);
	taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,point);
	//营收 = 卖上金额-（原价金额+税金合计）
	profit = parseFloat(saleBaseAmt) - (parseFloat(costTotalAmt) + parseFloat(taxTotal));
	profit = pointFormatHandler(profit,foreignFormatFlg,point);
	//营收率=营收/卖上金额 * 100
	profitRate = parseFloat(profit) / parseFloat(saleBaseAmt) * 100;
	profitRate = pointFormatHandler(profitRate,foreignFormatFlg,point);
	return {"tax":tax,"tax1":tax1,"taxTotal":taxTotal,"profit":profit,"profitRate":profitRate};
}
*/
function calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,point,taxFormatFlg,foreignFormatFlg)
{
	var tax = 0.0;
	var tax1 = 0.0;
	var taxTotal = 0.0;
	var profit = 0.0;
	var profitRate = 0.0;
	
	var saleBaseAmt = planSale;
	var costTotalAmt = planCost;
	//实际卖上不为空，使用实际
	if(saleAmt != "")
	{
		saleBaseAmt = parseFloat(saleAmt);
	}
	//是否有成本
	if(costCountNums > 0)
	{
		//使用实际成本
		costTotalAmt = costTotal;
	}else{
		//没有成本并且成本终止录入，实际成本为0
		if(isCostFinsh == 1)
		{
			costTotalAmt = 0.0;
		}
	}
	//文化建设税 = （请求金额-支付金额）*税率1
	tax = floatObj.multiply(floatObj.subtract(parseFloat(reqAmt) , parseFloat(payAmt)),parseFloat(rate))
	tax = pointFormatHandler(tax,taxFormatFlg,point);
	//增值附加税 = （卖上增值税-支付增值税）*税率2
	tax1 = floatObj.multiply(floatObj.subtract(parseFloat(saleVatAmt),parseFloat(costVatAmt)),parseFloat(rate1));
	tax1 = pointFormatHandler(tax1,taxFormatFlg,point);
	//税金合计 = 文化+增值税附加
	
	taxTotal = floatObj.add(parseFloat(tax) , parseFloat(tax1));
	taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,point);
	//营收 = 卖上金额-（原价金额+税金合计）
	profit = floatObj.subtract(parseFloat(saleBaseAmt),floatObj.add(parseFloat(costTotalAmt) , parseFloat(taxTotal)));
	profit = pointFormatHandler(profit,foreignFormatFlg,point);
	//营收率=营收/卖上金额 * 100
	if(saleBaseAmt == 0)
	{
		profitRate = "INF";
	}else{
		profitRate = floatObj.multiply(floatObj.divide(parseFloat(profit),parseFloat(saleBaseAmt)),100);
		profitRate = pointFormatHandler(profitRate,3,2);
	}
	return {"tax":tax,"tax1":tax1,"taxTotal":taxTotal,"profit":profit,"profitRate":profitRate};
}
/**
 * 方法名 calculateTaxHandler
 * 方法的说明  计算文化税，附加税，税金合计，营收，营收率
 * 
 * @param saleBaseAmt	卖上金额
 * @param costTotalAmt	成本金额
 * @param reqAmt		请求金额
 * @param payAmt		支付金额
 * @param saleVatAmt	卖上增值税
 * @param costVatAmt	仕入增值税（成本增值税）
 * @param rate			文化税率
 * @param rate1			附加税率
 * @param point	本国货币小数点位数
 * @param foreignFormatFlg 外货端数flg，0051 001
 * @param taxFormatFlg	0052 003
 * 
 * @return Object
 * @author 王岩
 * @date  2018.06.13
 */
function calculateTaxBySaleHandler(saleBaseAmt,costTotalAmt,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,point,taxFormatFlg,foreignFormatFlg)
{
	var tax = 0.0;
	var tax1 = 0.0;
	var taxTotal = 0.0;
	var profit = 0.0;
	var profitRate = 0.0;
	
	//文化建设税 = （请求金额-支付金额）*税率1
	tax = floatObj.multiply(floatObj.subtract(parseFloat(reqAmt) , parseFloat(payAmt)),parseFloat(rate))
	tax = pointFormatHandler(tax,taxFormatFlg,point);
	//增值附加税 = （卖上增值税-支付增值税）*税率2
	tax1 = floatObj.multiply(floatObj.subtract(parseFloat(saleVatAmt),parseFloat(costVatAmt)),parseFloat(rate1));
	tax1 = pointFormatHandler(tax1,taxFormatFlg,point);
	//税金合计 = 文化+增值税附加
	
	taxTotal = floatObj.add(parseFloat(tax) , parseFloat(tax1));
	taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,point);
	//营收 = 卖上金额-（原价金额+税金合计）
	profit = floatObj.subtract(parseFloat(saleBaseAmt),floatObj.add(parseFloat(costTotalAmt) , parseFloat(taxTotal)));
	profit = pointFormatHandler(profit,foreignFormatFlg,point);
	//营收率=营收/卖上金额 * 100
	if(saleBaseAmt == 0)
	{
		profitRate = "INF";
	}else{
		profitRate = floatObj.multiply(floatObj.divide(parseFloat(profit),parseFloat(saleBaseAmt)),100);
		profitRate = pointFormatHandler(profitRate,3,2);
	}
	return {"tax":tax,"tax1":tax1,"taxTotal":taxTotal,"profit":profit,"profitRate":profitRate};
}
/**
 * 方法名 pointFormatHandler
 * 方法的说明  小数点处理
 * 
 * @param amt		金额
 * @param calFlg	换算方式 1:且上;2:切下;3:45入;
 * @param point		小数点位数
 * 
 * @return parseFloat
 * @author 王岩
 * @date  2018.06.13
 */
/*
function pointFormatHandler(amt,calFlg,point)
{
	if(Math.abs(amt) == Infinity)
	{
		return amt;
	}
	var moneyAmt = parseFloat(amt);
	//var cr = 10 * parseInt(point);
	var cr = Math.pow(10,parseInt(point));
	
	var calFlg = parseFloat(calFlg);
	var amt = 0.0;
	if(parseInt(point) == 0)
	{
		cr = 1;
	}
	switch (calFlg){
		//且上
		case 1:
			amt = Math.floor(moneyAmt * 100) / 100;
			moneyAmt = Math.ceil(amt * cr) / cr;
			break;
			//切下
		case 2:
			amt =floatObj.divide(floatObj.multiply(moneyAmt,100),100);
			moneyAmt = Math.floor(amt * cr) / cr;
			break;
			//45入
		case 3:
			moneyAmt = Math.round(moneyAmt * cr) / cr;
			break;
	}
	return formatNumber(moneyAmt,point,false);
}
*/
function pointFormatHandler(amt,calFlg,point)
{
	if(Math.abs(amt) == Infinity)
	{
		//return amt;
		return "INF";
	}
	var moneyAmt = parseFloat(amt);
	//var cr = 10 * parseInt(point);
	var cr = Math.pow(10,parseInt(point));
	
	var calFlg = parseFloat(calFlg);
	var amt = 0.0;
	if(parseInt(point) == 0)
	{
		cr = 1;
	}
	switch (calFlg){
		//且上
		case 1:
			//amt = floatObj.divide(Math.floor(floatObj.multiply(moneyAmt , 100)),100);
			moneyAmt = floatObj.divide(Math.ceil(floatObj.multiply(moneyAmt , cr)) , cr);
			break;
			//切下
		case 2:
			//amt =floatObj.divide(floatObj.multiply(moneyAmt,100),100);
			moneyAmt = floatObj.divide(Math.floor(floatObj.multiply(moneyAmt , cr)) , cr);
			break;
			//45入
		case 3:
			moneyAmt = floatObj.divide(Math.round(floatObj.multiply(moneyAmt , cr)) , cr);
			break;
	}
	return formatNumber(moneyAmt,point,false);
}
/**
 * 方法名 calculateMoneyByVatChangeHandler
 * 方法的说明  增值税变化时重新加算
 * 
 * @param haveVatFlg	0：税拔；1：税入
 * @param saleCostAmt	卖上金额/原价金额
 * @param reqPayAmt		请求金额/支付金额
 * @param vatAmt		卖上增值税/仕入增值税
 * *
 * @return object saleCostAmt 卖上金额/原价金额，vatAmt 卖上增值税/仕入增值税，reqPayAmt 请求金额/支付金额
 * @author 王岩
 * @date  2018.06.13
 */
/*
function calculateMoneyByVatChangeHandler(haveVatFlg,saleCostAmt,reqPayAmt,vatAmt)
{
	
	if(haveVatFlg == 0)
	{
		var amt = parseFloat(saleCostAmt) + parseFloat(vatAmt);
		return {"saleCostAmt":parseFloat(saleCostAmt),"vatAmt":parseFloat(vatAmt),"reqPayAmt":amt};
	}else{
		var amt = parseFloat(reqPayAmt) - parseFloat(vatAmt);
		return {"saleCostAmt":amt,"vatAmt":parseFloat(vatAmt),"reqPayAmt":parseFloat(reqPayAmt)};
	}
}
*/
function calculateMoneyByVatChangeHandler(haveVatFlg,saleCostAmt,reqPayAmt,vatAmt)
{
	
	if(haveVatFlg == 0)
	{
		var amt = floatObj.add(parseFloat(saleCostAmt) , parseFloat(vatAmt));
		return {"saleCostAmt":parseFloat(saleCostAmt),"vatAmt":parseFloat(vatAmt),"reqPayAmt":amt};
	}else{
		var amt = floatObj.subtract(parseFloat(reqPayAmt) , parseFloat(vatAmt));
		return {"saleCostAmt":amt,"vatAmt":parseFloat(vatAmt),"reqPayAmt":parseFloat(reqPayAmt)};
	}
}

