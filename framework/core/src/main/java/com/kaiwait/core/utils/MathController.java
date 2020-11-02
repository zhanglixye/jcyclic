package com.kaiwait.core.utils;

import java.math.BigDecimal;

public class MathController {
	
	/**
	* 方法名：scaleNumber
	* <br>
	* 方法说明: 四舍五入
	* <br>
	* @param num 要进行四舍五入的数字 position 四舍五入保留位数
	* @return double 四舍五入后的结果
	* @author liuchao
	* 创建日期:2009/08/12
	* <br>
	* 修改者:刘超
	* 修改日期：2009/10/14
	* <br>
	* @version　1.00
	**/
	
	private static final int DEF_DIV_SCALE = 13;
	public static double scaleNumber(Double num,int position)
	{
		if(num.isInfinite()||num.isNaN())//如果传入的Double 值是infinite或者 NaN 直接返回0.0 update by liuchao 2009.10.13
		{
			return .0;
		}
		BigDecimal bd = new BigDecimal(Double.toString(num));
		return bd.setScale(position, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	* 方法名：scaleNumber
	* <br>
	* 方法说明: 四舍五入
	* <br>
	* @return double 四舍五入后的结果(默认保留2位）
	* @author liuchao
	* 创建日期:2009/08/12
	* <br>
	* 修改者:刘超
	* 修改日期：2009/10/14
	* <br>
	* @version　1.00
	**/
	
	public static double scaleNumber(Double num)
	{
		if(num.isInfinite()||num.isNaN())//如果传入的Double 值是infinite或者 NaN 直接返回0.0 by liuchao 2009.10.13
		{
			return .0;
		}
		BigDecimal bd = new BigDecimal(Double.toString(num));//如果遇到类似0.034999999的情况(相乘后的结果)，必须精确到第三位后再四舍五入
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//update by liuchao 2009.10.14
	}
	
	/**
	* 提供精确的加法运算。
	* @param v1 被加数
	* @param v2 加数
	* @return 两个参数的和
	*/

	public static double add(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.add(b2).doubleValue();
	}

	/**
	* 提供精确的减法运算。
	* @param v1 被减数
	* @param v2 减数
	* @return 两个参数的差
	*/

	public static double sub(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.subtract(b2).doubleValue();
	}

	/**
	* 提供精确的乘法运算。
	* @param v1 被乘数
	* @param v2 乘数
	* @return 两个参数的积
	*/

	public static double mul(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.multiply(b2).doubleValue();
	}

	/**
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	* 小数点以后10位，以后的数字四舍五入。
	* @param v1 被除数
	* @param v2 除数
	* @return 两个参数的商
	*/

	public static double div(double v1,double v2){
	    return div(v1,v2,DEF_DIV_SCALE);
	}

	/**
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	* 定精度，以后的数字四舍五入。
	* @param v1 被除数
	* @param v2 除数
	* @param scale 表示表示需要精确到小数点以后几位。
	* @return 两个参数的商
	*/

	public static double div(double v1,double v2,int scale){
	    if(scale<0){
	        throw new IllegalArgumentException(
	          "The scale must be a positive integer or zero");
	    }
	    if(v2 == 0.0)
	    {
	    	return .0;
	    }
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	* 数字转换成百分数(扩大100倍),并且结果保留两位小数
	* @param num
	* @return 结果
	*/
	public static double toPercent(double num){
		return scaleNumber(mul(num,100.0));
	}
	/**
	* 通过营业利润/营业额=营业率,如果营业额为0返回无穷大
	* @param num
	* @return 结果
	*/
	public static double toRate(Double profit,Double sale){
		if(sale==0.0||sale==null){
			return Double.POSITIVE_INFINITY;
		}else{
			return MathController.div(profit,sale);
		}
	}
	
}
