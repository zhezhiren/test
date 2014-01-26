package com.plj.common.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.plj.common.error.MyError;

/**
 * 数组辅助�?
 * 
 * @author bin
 * 
 */
public class ArrayUtils {
	/**
	 * 将数组转换为List
	 * 
	 * @param ints
	 * @return
	 */
	public static <T> List<T> toList(T[] array) {
		if (array == null)
			return null;
		List<T> result = new ArrayList<T>(array.length);
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}

	/**
	 * 将字符串数组转换为整型数�?
	 * 
	 * @param strings
	 * @return
	 */
	public static Integer[] ToIntegerArray(String[] strings) {
		if (strings == null)
			return null;
		Integer[] result = new Integer[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = Integer.parseInt(strings[i]);
		}
		return result;
	}

	/**
	 * 将错误信息拼接为字符�?
	 * 
	 * @param errors
	 * @return
	 */
	public static String join(List<MyError> errors) {
		StringBuffer buffer = new StringBuffer();
		for (MyError err : errors) {
			buffer.append(MessageFormat.format("����[����:{0}, ��Ϣ:{1}],",
					err.getErrorCode(), err.getErrorMsg()));
		}
		if (buffer.length() > 0) {
			buffer.setLength(buffer.length() - 1);
		}
		return buffer.toString();
	}

	/**
	 * 将空格分隔的数据转换为数组（多个空格，视作为1个空格）
	 * @param str
	 * @param arrayLen
	 * @return
	 */
	public static String[] splitWithSpaces(String str) {
		List<String> result = new ArrayList<String>();
		int pos = 0;
		int state = 0;// 读取状�?
		// 状�?转换关系
		// 0（遇到非空字�? 1
		// 0 ( 空字�? 0
		// 1 ( 空字符）0
		// 1 ( 非空字符) 1
		char[] buffer = new char[str.length()];
		int bufferPos = 0;
		while (pos < str.length()) {
			if (str.charAt(pos) == ' ') {
				if (state == 1) {// 获得�?���?
					result.add(new String(buffer, 0, bufferPos));
					bufferPos = 0;
					state = 0;
				}
			} else {
				if (state == 0) {
					state = 1;
				}
				buffer[bufferPos] = str.charAt(pos);
				bufferPos++;
			}
			pos++;
		}
		result.add(new String(buffer, 0, bufferPos));
		String[] arr = new String[result.size()];
		return result.toArray(arr);
	}

	/**
	 * 指定的字符串�?分隔是否包含在数组中
	 * @param code
	 * @param strs
	 * @return
	 */
	public static boolean elementOf(String elements, String[] strs) {
		String[] split = elements.split(",");
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < strs.length; j++) {
				if (split[i].equalsIgnoreCase(strs[j]))
					return true;
			}
		}
		return false;
	}

	/**
	 * 获取第一个非空的字串
	 * 
	 * @param str
	 * @return
	 */
	public static String firstToken(String str) {
		if (str == null) {
			return str;
		}
		int pos = 0;
		int startPos = -1;
		while (pos < str.length()) {
			if (str.charAt(pos) == ' ') {
				if (startPos != -1) {// 获得�?���?
					return str.substring(startPos, pos);
				}
			} else if (startPos == -1) {
				startPos = pos;
			}
			pos++;
		}
		return str;
	}
}
