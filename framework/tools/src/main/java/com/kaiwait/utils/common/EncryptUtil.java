package com.kaiwait.utils.common;

import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 对称加密工具类
 *
 */
public class EncryptUtil {

	/** 加密算法,支持[AES/DES/DESede]*/
	private static final String CIPHER_ALGORITHM = "AES"; 
	/** 字符编码*/
	private static final String CHARSET = "UTF-8";
	/** 字节数组转换为字符串表示时使用的字符,需要设置为16位不重复的字符*/
	private static final String HEX_CHARS_STR = "01fae36827c54db9";
	/** 十六进制转换时使用的字符数组*/
	private static final char[] HEX_CHARS;
	/** 十六进制转换时使用的字符Map*/
	private static final Map<Character, Byte> HEX_INDEX = new HashMap<Character, Byte>(15);

	static {
		//构造十六进制转换时使用的字符数组
		HEX_CHARS = HEX_CHARS_STR.toCharArray();
		//构造十六进制转换时使用的字符Map
		int i=0;
		for(char c : HEX_CHARS) {
			HEX_INDEX.put(c, (byte)i++);
		}
	}
	
	/**
	 * 生成密钥
	 */
	public static Key getSecretKey(String key) throws Exception {
		SecretKey securekey = null;
		if (key == null) {
			key = "";
		}
		KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER_ALGORITHM);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");   
        secureRandom.setSeed(key.getBytes());
		keyGenerator.init(secureRandom);
		securekey = keyGenerator.generateKey();
		return securekey;
	}

	/**
	 * 加密
	 */
	public static String encrypt(String data, String key) throws Exception {
		if (StringUtil.isEmpty(data)) {
			return data;
		}
		SecureRandom sr = new SecureRandom();
		Key securekey = getSecretKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		byte[] bt = cipher.doFinal(data.getBytes(CHARSET));
		
		return byteToHexStr(bt);
	}

	/**
	 * 解密
	 */
	public static String decrypt(String message, String key) throws Exception {
		if (StringUtil.isEmpty(message)) {
			return message;
		}
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		Key securekey = getSecretKey(key);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		byte[] res = hexStrToByte(message);
		res = cipher.doFinal(res);
		return new String(res, CHARSET);
	}

	/**
	 * 字节数组转换为字符串表示
	 */
	private static String byteToHexStr(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1) {
			return "";
		}
		StringBuilder sb = new StringBuilder(byteArray.length*2);
		for(byte b : byteArray) {
			sb.append(HEX_CHARS[b & 0x0f]);
			sb.append(HEX_CHARS[(b>>>4) & 0x0f]);
		}
		return sb.toString();
	}
	
	/**
	 * 字符串表示的字节数组转换为字节数组
	 */
	private static byte[] hexStrToByte(String hexStr) {
		if (hexStr == null || hexStr.length() == 0) {
			return null;
		}
		final int byteCount = hexStr.length()/2;
		byte[] returnByte = new byte[byteCount];
		int charPoint = 0;
		for(int i=0; i<byteCount; i++) {
			returnByte[i] = 0;
			char c = hexStr.charAt(charPoint++);
			if (!HEX_INDEX.containsKey(c)) {
				throw new RuntimeException("密文被修改过");
			}
			returnByte[i]  |= HEX_INDEX.get(c);
			c = hexStr.charAt(charPoint++);
			if (!HEX_INDEX.containsKey(c)) {
				throw new RuntimeException("密文被修改过");
			}
			returnByte[i] |= (HEX_INDEX.get(c) << 4);
		}
		return returnByte;
	}

}
