package com.kaiwait.utils.common;

/**
 * 数据包工具
 *
 */
public class DataUtil {
	/**
	 * 8字节数组转换为长整形
	 */
	public static long byteArrayToLong(byte[] data) {
		return (((long) data[0] << 56) + ((long) (data[1] & 255) << 48) + ((long) (data[2] & 255) << 40)
				+ ((long) (data[3] & 255) << 32) + ((long) (data[4] & 255) << 24) + ((data[5] & 255) << 16)
				+ ((data[6] & 255) << 8) + ((data[7] & 255) << 0));
	}

	/**
	 * 将长整形值转换为8字节数组
	 */
	public static byte[] longToByteArray(long data) {
		byte[] result = new byte[8];
		result[0] = (byte) (data >>> 56);
		result[1] = (byte) (data >>> 48);
		result[2] = (byte) (data >>> 40);
		result[3] = (byte) (data >>> 32);
		result[4] = (byte) (data >>> 24);
		result[5] = (byte) (data >>> 16);
		result[6] = (byte) (data >>> 8);
		result[7] = (byte) (data >>> 0);
		return result;
	}

	/**
	 * 4字节数组转换为整形值
	 */
	public static int byteArrayToInt(byte[] data) {
		int value = 0;
		int value0 = data[0] & 0xff;
		int value1 = data[1] & 0xff;
		int value2 = data[2] & 0xff;
		int value3 = data[3] & 0xff;
		value1 <<= 8;
		value2 <<= 16;
		value3 <<= 24;
		value = value0 | value1 | value2 | value3;
		return value;
	}

	/**
	 * 整形值转换为4字节数组
	 */
	public static byte[] intToByteArray(long data) {
		byte[] result = new byte[8];
		result[0] = (byte) (data & 0xff);
		result[1] = (byte) ((data >>> 8) & 0xff);
		result[2] = (byte) ((data >>> 16) & 0xff);
		result[3] = (byte) ((data >>> 24) & 0xff);
		return result;
	}

	/**
	 * 将长整形值写入指定的字节数组
	 */
	public static void writeLong(byte[] data, int offset, long value) throws RuntimeException {
		if (data == null || data.length < offset + 8) {
			throw new RuntimeException("数据空间不足");
		}
		final byte[] longToByte = longToByteArray(value);
		for (int i = 0; i < 8; i++) {
			data[offset + i] = longToByte[i];
		}
	}

	/**
	 * 将整形值写入指定的字节数组
	 */
	public static void writeInt(byte[] data, int offset, int value) throws RuntimeException {
		if (data == null || data.length < offset + 4) {
			throw new RuntimeException("数据空间不足");
		}
		final byte[] intToByte = intToByteArray(value);
		for (int i = 0; i < 4; i++) {
			data[offset + i] = intToByte[i];
		}
	}

	/**
	 * 从指定的字节数组中读取整形值
	 */
	public static int readInt(byte[] data, int offset) throws RuntimeException {
		if (data == null || data.length < offset + 4) {
			throw new RuntimeException("数据空间不足");
		}
		byte[] intByte = new byte[4];
		for (int i = 0; i < 4; i++) {
			intByte[i] = data[offset + i];
		}
		return byteArrayToInt(intByte);
	}

	/**
	 * 将点分隔的字符串表示的IP地址转换为长整形
	 */
	public static long ipToLong(String ipaddress) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = ipaddress.indexOf(".");
		int position2 = ipaddress.indexOf(".", position1 + 1);
		int position3 = ipaddress.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(ipaddress.substring(0, position1));
		ip[1] = Long.parseLong(ipaddress.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(ipaddress.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(ipaddress.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 将长整形表示的IP地址转换为点分隔的字符串表示
	 */
	public static String longToIP(long ipaddress) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((ipaddress >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((ipaddress & 0x000000FF)));
		return sb.toString();
	}

}
