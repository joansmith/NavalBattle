package com.jpii.navalbattle.util;

import java.util.Arrays;

public class OSUtil {
	public static <T> T[] memcpy(T[] src) {
		if (src == null)
			return null;
		return Arrays.copyOf(src, src.length);
	}
	public static <T> int memchr(T[] ptr, T value) {
		for (int c = 0; c < ptr.length; c++) {
			if (value.equals(ptr[c]))
				return c;
			if (value.hashCode() == ptr[c].hashCode())
				return c;
		}
		return -1;
	}
	public static <T> T[] memset(T[] src, T value) {
		if (src == null)
			return null;
		Arrays.fill(src, 0, src.length, value);
		return src;
	}
	public static String strncpy(String str) {
		StringBuilder b = new StringBuilder();
		for (int c = 0; c < str.length(); c++) {
			b.append(str.charAt(c));
		}
		return b.toString();
	}
	public static <T> void delete(T[] array) {
		if (array == null)
			return;
		for (int c = 0; c < array.length;) {
			array[c] = null;
		}
		array = null;
		Runtime.getRuntime().gc();
	}
	public static String xorEncode(String data, String key) {
		byte m_cData[] = data.getBytes();
		byte m_cKey [] = key.getBytes();
		int keyPointer = 0;
		for(int i = 0; i < m_cData.length; i++)
		{
			m_cData[i] ^= m_cKey[keyPointer];
			keyPointer += m_cData[i];
			keyPointer %= m_cKey.length;
		}
		return new String(m_cData);
	}
	public static String xorDecode(String data, String key) {
		byte m_cData[] = data.getBytes();
		byte m_cKey [] = key.getBytes();
		int keyPointer = 0;
		byte keyPointerAdd = 0;
		for(int i = 0; i < m_cData.length; i++)
		{
			keyPointerAdd = m_cData[i];
			m_cData[i] ^= m_cKey[keyPointer];
			keyPointer += keyPointerAdd;
			keyPointer %= m_cKey.length;
		}
		return new String(m_cData);
	}
}
