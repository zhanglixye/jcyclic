package com.kaiwait.utils.common;

import java.math.BigDecimal;
import java.security.MessageDigest;

/**
 * 
 * 字符工具类
 *
 */
public class StringUtil {
	/** 空字符串。 */
	public static final String EMPTY_STRING = "";
	/** 字符串0 */
	public static final String ZERO_STRING = "0";

	/**
	 * 如果输入的value是null,返回convert值,否则返回value
	 * @param value
	 * @param convert
	 * @return
	 */
	public static String ifNull(String value, String convert) {
	    if (value == null) {
	        return convert;
	    }
	    return value;
	}
	/**
	 * 字符串转换为基于utf-8字符集的Url编码
	 * @param s 需转换的字符串
	 * @return Url编码表示的UTF8文字列
	 */
	public static String toUtf8String(String s){

		StringBuffer sb = new StringBuffer();
	    for (int i=0;i<s.length();i++) {

	    	char c = s.charAt(i);
	        if (c >= 0 && c <= 255) {
	        	sb.append(c);
        	} else {
	        	byte[] b;
	        	try {
	        		b = Character.toString(c).getBytes("utf-8");
	        	} catch (Exception ex) {
		        	System.out.println(ex);
		        	b = new byte[0];
	        	}
		        for (int j = 0; j < b.length; j++) {
		        	int k = b[j];
		        	if (k < 0) {
		        		k += 256;
		        		sb.append("%" + Integer.toHexString(k).toUpperCase());
	        		}
		        }
        	}
	    }
	    return sb.toString();
	}
	/**
	 * 比较两个字符串（大小写敏感）。
	 * 
	 * <pre>
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, "abc")  = false
	 * StringUtil.equals("abc", null)  = false
	 * StringUtil.equals("abc", "abc") = true
	 * StringUtil.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 *            要比较的字符串1
	 *            要比较的字符串2
	 * 
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/**
	 * 比较两个字符串（大小写不敏感）。
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, "abc")  = false
	 * StringUtil.equalsIgnoreCase("abc", null)  = false
	 * StringUtil.equalsIgnoreCase("abc", "abc") = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 * 
	 *            要比较的字符串1
	 *            要比较的字符串2
	 * 
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank("")        = true
	 * StringUtil.isBlank(" ")       = true
	 * StringUtil.isBlank("bob")     = false
	 * StringUtil.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 *            要检查的字符串
	 * 
	 */
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = false
	 * StringUtil.isBlank("")        = false
	 * StringUtil.isBlank(" ")       = false
	 * StringUtil.isBlank("bob")     = true
	 * StringUtil.isBlank("  bob  ") = true
	 * </pre>
	 * 
	 *            要检查的字符串
	 * 
	 */
	public static boolean isNotBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 *            要检查的字符串
	 * 
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = false
	 * StringUtil.isEmpty("")        = false
	 * StringUtil.isEmpty(" ")       = true
	 * StringUtil.isEmpty("bob")     = true
	 * StringUtil.isEmpty("  bob  ") = true
	 * </pre>
	 * 
	 *            要检查的字符串
	 * 
	 */
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *)          = -1
	 * StringUtil.indexOf(*, null)          = -1
	 * StringUtil.indexOf("", "")           = 0
	 * StringUtil.indexOf("aabaabaa", "a")  = 0
	 * StringUtil.indexOf("aabaabaa", "b")  = 2
	 * StringUtil.indexOf("aabaabaa", "ab") = 1
	 * StringUtil.indexOf("aabaabaa", "")   = 0
	 * </pre>
	 * 
	 *            要扫描的字符串
	 *            要查找的字符串
	 * 
	 */
	public static int indexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/**
	 * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *, *)          = -1
	 * StringUtil.indexOf(*, null, *)          = -1
	 * StringUtil.indexOf("", "", 0)           = 0
	 * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtil.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtil.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtil.indexOf("abc", "", 9)        = 3
	 * </pre>
	 * 
	 *            要扫描的字符串
	 *            要查找的字符串
	 *            开始搜索的索引值，如果小于0，则看作0
	 * 
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		// JDK1.3及以下版本的bug：不能正确处理下面的情况
		if ((searchStr.length() == 0) && (startPos >= str.length())) {
			return str.length();
		}

		return str.indexOf(searchStr, startPos);
	}

	/**
	 * 取指定字符串的子串。
	 * 
	 * <p>
	 * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring("", * ,  *)    = "";
	 * StringUtil.substring("abc", 0, 2)   = "ab"
	 * StringUtil.substring("abc", 2, 0)   = ""
	 * StringUtil.substring("abc", 2, 4)   = "c"
	 * StringUtil.substring("abc", 4, 6)   = ""
	 * StringUtil.substring("abc", 2, 2)   = ""
	 * StringUtil.substring("abc", -2, -1) = "b"
	 * StringUtil.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 * 
	 * </p>
	 * 
	 *            字符串
	 *            起始索引，如果为负数，表示从尾部计算
	 *            结束索引（不含），如果为负数，表示从尾部计算
	 * 
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY_STRING;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)     = false
	 * StringUtil.contains(*, null)     = false
	 * StringUtil.contains("", "")      = true
	 * StringUtil.contains("abc", "")   = true
	 * StringUtil.contains("abc", "a")  = true
	 * StringUtil.contains("abc", "z")  = false
	 * </pre>
	 * 
	 *            要扫描的字符串
	 *            要查找的字符串
	 * 
	 */
	public static boolean contains(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return false;
		}

		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not
	 * a unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * </pre>
	 * 
	 *            the String to check, may be null
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析字符串<br>
	 * 将字符串中的'{}'符号替换成具体内容
	 * 
	 */
	public static String parseString(String str, String... params) {
		int i = 0;
		int length = params.length;

		while (str.indexOf("{}") > -1 && i < length) {
			str = str.replaceFirst("\\{}", String.valueOf(params[i]));
			i++;
		}

		return str;
	}

	/**
	 * 将字符串首字母大写
	 * 
	 *            字符串
	 */
	public static String firstUpperCase(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char char0 = s.charAt(0);
		if (Character.isUpperCase(char0))
			return s.toString();
		return new StringBuilder(len).append(Character.toUpperCase(char0))
				.append(s.subSequence(1, len)).toString();
	}

	/**
	 * 将字符串首字母小写
	 * 
	 *            字符串
	 */
	public static String firstLowerCase(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char char0 = s.charAt(0);
		if (Character.isLowerCase(char0))
			return s.toString();
		return new StringBuilder(len).append(Character.toLowerCase(char0))
				.append(s.subSequence(1, len)).toString();
	}

	public static String null2String(String str) {

		if (null == str) {
			return "";
		}

		return str.trim();
	}

	/****
	 * 判断六位字符是否为数字
	 */
	public static boolean IsNum(String str) {
		try {

			if ((isNotEmpty(str) && str.length() != 6) || isEmpty(str)) {
				return false;// 格式不正确
			}
			// 把字符串强制转换为数字
			@SuppressWarnings("unused")
			int num = Integer.valueOf(str);
			// 如果是数字，返回True
			return true;
		} catch (Exception e) {
			// 如果抛出异常，返回False
			return false;
		}
	}


	/**
	 * 判断一个字符串是否超过规定长度
	 * 
	 *            : 指定的字符串
	 *            : 规定的字符串长度
	 * 
	 *            <pre>
	 * str == null       : false
	 * str.length()>len  : true
	 * </pre>
	 */
	public static boolean istooLong(String str, int len) {
		if (str == null) {
			return false;
		}
		if (str.length() >= len) {
			return true;
		} else {
			return false;
		}
	}

	/****
	 * 截取字符串后几位
	 */
	public static String subStr4Last(String str, int index) {
		if (isNotEmpty(str)) {
			return str.substring(str.length() - index, str.length());
		}
		return "";
	}

	/****
	 * 判断一个bigdecimal是否是整数
	 */
	public static boolean isInterger(BigDecimal dec) {
		if (isEmpty(dec.toString())) {
			return false;
		}
		String str = dec.toString();
		int index = str.indexOf(".");
		if (index == -1) {
			return true;
		}
		String last = str.substring(index + 1);
		boolean isNum = last.matches("[0]+");
		return isNum;
	}

	/**
	 * 指定长度插入字符
	 * 
	 */
	public static String formatString(String msg, int length,String ch) {
		String finalmsg = "";
		if (msg.trim().length() > length) {
			int index = (msg.trim().length() / length) == 0 ? 1
					: (msg.trim().length() / length);
			for (int i = 0; i < index; i++) {
				if (i > 0) {
					finalmsg += msg.substring(i * length, (i + 1) * length)+ch;
				} else {
					finalmsg += msg.substring(0, length)+ch;
				}
			}
		}
		
		return finalmsg;
	}
	/******
	 * 验证金额是否大于0
	 */
	public static  int compare2Zero(String str){
		if(isNotEmpty(str)){
			int r=new BigDecimal(str).compareTo(BigDecimal.ZERO);
			return r;
		}
		return 0;
	}
	
	/*******
	 * 字符是否包含数字
	 */
	public static boolean IsContainNum(String str){
		 boolean  b=false;
		 if(isEmpty(str)){
			 return b;
		 }
		  for(int i=0;i<str.length();i++){ 
			if(Character.isDigit(str.charAt(i)))
		     {
				b=true;  
				break;
		     }
		   }
		    return  b;
		
	}
	
	/** 左补0 */
	public static String leftAppendZero(String str, int strLength) {
		if(str == null) str = "";
		str = str.trim();
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	
    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。   
     */      
    public static String bytesToHexString(byte[] src){   
        StringBuilder stringBuilder = new StringBuilder("");   
        if (src == null || src.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    }   
    /**  
     * Convert hex string to byte[]  
     */  
    public static byte[] hexStringToBytes(String hexString) {   
        if (hexString == null || hexString.equals("")) {   
            return null;   
        }   
        hexString = hexString.toUpperCase();   
        int length = hexString.length() / 2;   
        char[] hexChars = hexString.toCharArray();   
        byte[] d = new byte[length];   
        for (int i = 0; i < length; i++) {   
            int pos = i * 2;   
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
        }   
        return d;   
    }  
    
    /**  
     * Convert char to byte  
     */  
     private static byte charToByte(char c) {   
        return (byte) "0123456789ABCDEF".indexOf(c);   
    }  
    /**
     * 
     */
 	public static String substringBetween(String str, String open, String close) {
		if ((str == null) || (open == null) || (close == null)) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}     
	
 	/**
 	 * MD5
 	 */
 	public static String md5(String str) {
 		try {
 		    MessageDigest md = MessageDigest.getInstance("MD5");
 		    byte[] md5 = md.digest(str.getBytes());
 		    return bytesToHexString(md5);
 		} catch (Exception e) {
 		    throw new RuntimeException(e);
 		}
 	}
 	
 	/**
 	 * 替换Unicode非法字符(用￭替换所有CJK字符集可见字符以外的字符)
 	 */
 	public static String replaceIncorrectChar(String str) {
 		if (isEmpty(str)) {
 			return str;
 		}
		final char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder(charArray.length);
		for(char c : charArray) {
			if (
					//ascii 可见字符
					(c==0 || c==9 || c==10 || c==13) || (c >= 32 && c <=126)
					//标准CJK文字
					|| (c >=0x4E00 && c <=0x9FA5) 
					//全角ASCII、全角中英文标点、半宽片假名、半宽平假名、半宽韩文字母：FF00-FFEF
					|| (c >=0xFF00 && c <=0xFFEF) 
					//CJK部首补充：2E80-2EFF
					|| (c >=0x2E80 && c <=0x2EFF) 
					//CJK标点符号：3000-303F
					|| (c >=0x3000 && c <=0x303F) 
					//CJK笔划：31C0-31EF
					|| (c >=0x31C0 && c <=0x31EF) 
					//康熙部首：2F00-2FDF
					|| (c >=0x2F00 && c <=0x2FDF) 
					//汉字结构描述字符：2FF0-2FFF
					|| (c >=0x2FF0 && c <=0x2FFF) 
					//注音符号：3100-312F
					|| (c >=0x3100 && c <=0x312F) 
					//注音符号（闽南语、客家语扩展）：31A0-31BF
					|| (c >=0x31A0 && c <=0x31BF) 
					//日文平假名：3040-309F
					|| (c >=0x3040 && c <=0x309F) 
					//日文片假名：30A0-30FF
					|| (c >=0x30A0 && c <=0x30FF) 
					//日文片假名拼音扩展：31F0-31FF
					|| (c >=0x31F0 && c <=0x31FF) 
					//韩文拼音：AC00-D7AF
					|| (c >=0xAC00 && c <=0xD7AF) 
					//韩文字母：1100-11FF
					|| (c >=0x1100 && c <=0x11FF) 
					//韩文兼容字母：3130-318F
					|| (c >=0x3130 && c <=0x318F) 
					//太玄经符号：1D300-1D35F
					|| (c >=0x1D300 && c <=0x1D35F) 
					//易经六十四卦象：4DC0-4DFF
					|| (c >=0x4DC0 && c <=0x4DFF) 
					//彝文音节：A000-A48F
					|| (c >=0xA000 && c <=0xA48F) 
					//彝文部首：A490-A4CF
					|| (c >=0xA490 && c <=0xA4CF) 
					//盲文符号：2800-28FF
					|| (c >=0x2800 && c <=0x28FF) 
					//CJK字母及月份：3200-32FF
					|| (c >=0x3200 && c <=0x32FF) 
					//CJK特殊符号（日期合并）：3300-33FF
					|| (c >=0x3300 && c <=0x33FF) 
					//装饰符号（非CJK专用）：2700-27BF
					|| (c >=0x2700 && c <=0x27BF) 
					//杂项符号（非CJK专用）：2600-26FF
					|| (c >=0x2600 && c <=0x26FF) 
					//中文竖排标点：FE10-FE1F
					|| (c >=0xFE10 && c <=0xFE1F) 
					//CJK兼容符号（竖排变体、下划线、顿号）：FE30-FE4F
					|| (c >=0xFE30 && c <=0xFE4F) 
				) {
				sb.append(c);
			} else {
				//非法字符,用￭替换
				sb.append((char)0xFFED);
				//sb.append("[0x");
				//sb.append(Integer.toHexString(c));
				//sb.append("]");
			}
		}
		return sb.toString();
 	}
}
