package com.zycw.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: 字符串处理对象
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:19:56	
 */

public final class StringUtil {

	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

	public static final String RANDOM_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/** 默认字符编码集 */
	public static String ENCODING_DEFAULT = "UTF-8";

	public static String GET_ENCODING_DEFAULT = "UTF-8";

	public static String FILE_WRITING_ENCODING = "GBK";

	public StringUtil() {
		super();
	}
	// 常用字符串函数

	/**
	 * 判断指定字符串是否为空
	 * 
	 * @param _string
	 *            指定的字符串
	 * @return 若字符串为空对象（_string==null）或空串（长度为0），则返回true；否则，返回false.
	 */
	public static boolean isEmpty(String _string) {
		return ((_string == null) || (_string.trim().length() == 0));
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @see showNull( String _sValue, String _sReplaceIfNull )
	 */
	public static String showObjNull(Object p_sValue) {
		return showObjNull(p_sValue, "");
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param _sValue
	 *            指定的字符串
	 * @param _sReplaceIfNull
	 *            当_sValue==null时的替换显示字符串；可选参数，缺省值为空字符串（""）
	 * @return 处理后的字符串
	 */
	public static String showObjNull(Object _sValue, String _sReplaceIfNull) {
		if (_sValue == null)
			return _sReplaceIfNull;
		return _sValue.toString();
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @see showNull( String _sValue, String _sReplaceIfNull )
	 */
	public static String showNull(String p_sValue) {
		return showNull(p_sValue, "");
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param _sValue
	 *            指定的字符串
	 * @param _sReplaceIfNull
	 *            当_sValue==null时的替换显示字符串；可选参数，缺省值为空字符串（""）
	 * @return 处理后的字符串
	 */
	public static String showNull(String _sValue, String _sReplaceIfNull) {
		return (_sValue == null ? _sReplaceIfNull : _sValue);
	}

	/**
	 * 扩展字符串长度；若长度不足，则是用指定的字符串填充
	 * 
	 * @param _string
	 *            要扩展的字符串
	 * @param _length
	 *            扩展后的字符串长度。
	 * @param _chrFill
	 *            扩展时，用于填充的字符。
	 * @param _bFillOnLeft
	 *            扩展时，是否为左填充（扩展）；否则，为右填充
	 * @return 长度扩展后的字符串
	 */
	public static String expandStr(String _string, int _length, char _chrFill,
			boolean _bFillOnLeft) {
		int nLen = _string.length();
		if (_length <= nLen)
			return _string; // 长度已够

		// else,扩展字符串长度
		StringBuffer sRet = new StringBuffer(_string);
		for (int i = 0; i < _length - nLen; i++) {
			if ( _bFillOnLeft ) {
				sRet.insert(0, _chrFill);
			} else {
				sRet.append(_chrFill);
			}
		}
		return sRet.toString();
	}

	/**
	 * 设置字符串最后一位为指定的字符
	 * 
	 * @param _string
	 *            指定的字符串
	 * @param _chrEnd
	 *            指定字符，若字符串最后一位不是该字符，则在字符串尾部追加该字符
	 * @return 处理后的字符串 如果<code>isEmpty(_string)</code>返回true,则原样返回
	 * @see #isEmpty(String)
	 */
	public static String setStrEndWith(String _string, char _chrEnd) {
		return setStrEndWith0(_string, _chrEnd);
	}

	private static String setStrEndWith0(String _str, char _charEnd) {
		if (isEmpty(_str) || _str.endsWith(String.valueOf(_charEnd))) {
			return _str;
		}

		return _str + _charEnd;
	}

	/**
	 * 构造指定长度的空格字符串
	 * 
	 * @param _length
	 *            指定长度
	 * @return 指定长度的空格字符串
	 */
	public static String makeBlanks(int _length) {
		if (_length < 1)
			return "";
		StringBuffer buffer = new StringBuffer(_length);
		for (int i = 0; i < _length; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	// =============================================================================
	// 字符串替换

	/**
	 * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串。
	 * 
	 * @param _strSrc
	 *            源字符串。
	 * @param _strOld
	 *            被替换的旧字符串
	 * @param _strNew
	 *            用来替换旧字符串的新字符串
	 * @return 替换处理后的字符串
	 */
	public static String replaceStr(String _strSrc, String _strOld,
			String _strNew) {
		if (_strSrc == null || _strNew == null || _strOld == null)
			return _strSrc;

		// 提取源字符串对应的字符数组
		char[] srcBuff = _strSrc.toCharArray();
		int nSrcLen = srcBuff.length;
		if (nSrcLen == 0)
			return "";

		// 提取旧字符串对应的字符数组
		char[] oldStrBuff = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc;

		StringBuffer retBuff = new StringBuffer((nSrcLen * (1 + _strNew
				.length()
				/ nOldStrLen)));

		int i, j, nSkipTo;
		boolean bIsFound = false;

		i = 0;
		while (i < nSrcLen) {
			bIsFound = false;

			// 判断是否遇到要找的字符串
			if (srcBuff[i] == oldStrBuff[0]) {
				for (j = 1; j < nOldStrLen; j++) {
					if (i + j >= nSrcLen)
						break;
					if (srcBuff[i + j] != oldStrBuff[j])
						break;
				}
				bIsFound = (j == nOldStrLen);
			}

			// 若找到则替换，否则跳过
			if (bIsFound) {
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else {
				if (i + nOldStrLen >= nSrcLen) {
					nSkipTo = nSrcLen - 1;
				} else {
					nSkipTo = i;
				}
				for (; i <= nSkipTo; i++) {
					retBuff.append(srcBuff[i]);
				}
			}
		}
		return retBuff.toString();
	}

	/**
	 * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串。
	 * 
	 * @param _strSrc
	 *            源字符串。
	 * @param _strOld
	 *            被替换的旧字符串
	 * @param _strNew
	 *            用来替换旧字符串的新字符串
	 * @return 替换处理后的字符串
	 */
	public static String replaceStr(StringBuffer _strSrc, String _strOld,
			String _strNew) {
		if (_strSrc == null)
			return null;

		// 提取源字符串对应的字符数组
		int nSrcLen = _strSrc.length();
		if (nSrcLen == 0)
			return "";

		// 提取旧字符串对应的字符数组
		char[] oldStrBuff = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc.toString();

		StringBuffer retBuff = new StringBuffer((nSrcLen * (1 + _strNew
				.length()
				/ nOldStrLen)));

		int i, j, nSkipTo;
		boolean bIsFound = false;

		i = 0;
		while (i < nSrcLen) {
			bIsFound = false;

			// 判断是否遇到要找的字符串
			if (_strSrc.charAt(i) == oldStrBuff[0]) {
				for (j = 1; j < nOldStrLen; j++) {
					if (i + j >= nSrcLen)
						break;
					if (_strSrc.charAt(i + j) != oldStrBuff[j])
						break;
				}
				bIsFound = (j == nOldStrLen);
			}

			// 若找到则替换，否则跳过
			if (bIsFound) { // 找到
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else { // 没有找到
				if (i + nOldStrLen >= nSrcLen) {
					nSkipTo = nSrcLen - 1;
				} else {
					nSkipTo = i;
				}
				for (; i <= nSkipTo; i++) {
					retBuff.append(_strSrc.charAt(i));
				}
			}
		}// end while
//		oldStrBuff = null;
		return retBuff.toString();
	}

	// ==============================================================================
	// 字符编码处理函数

	/**
	 * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串
	 * 
	 * @see getStr( String _strSrc, String _encoding )
	 */
	public static String getStr(String _strSrc) {
		return getStr(_strSrc, ENCODING_DEFAULT);
	}

	/**
	 * 字符转换函数，处理中文问题
	 * 
	 * @param _strSrc
	 *            源字符串
	 * @param _bPostMethod
	 *            提交数据的方式（Get方式采用GET_ENCODING_DEFAULT字符集，Post方式采用ENCODING_DEFAULT字符集）
	 * @return
	 */
	public static String getStr(String _strSrc, boolean _bPostMethod) {
		return getStr(_strSrc, (_bPostMethod ? ENCODING_DEFAULT
				: GET_ENCODING_DEFAULT));
	}

	/**
	 * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串
	 * <p>
	 * Purpose: 转换字符串内码，用于解决中文显示问题
	 * </p>
	 * <p>
	 * Usage： 在页面切换时，获取并显示中文字符串参数时可用。
	 * </p>
	 * 
	 * @param _strSrc
	 *            需要转换的字符串
	 * @param _encoding
	 *            指定字符串（_strSrc）的编码方式；可选参数，缺省值为ENCODING_DEFAULT
	 * @return
	 */
	public static String getStr(String _strSrc, String _encoding) {
		if (_encoding == null || _encoding.length() == 0)
			return _strSrc;

		try {
			byte[] byteStr = new byte[_strSrc.length()];
			char[] charStr = _strSrc.toCharArray();
			for (int i = byteStr.length - 1; i >= 0; i--) {
				byteStr[i] = (byte) charStr[i];
			}
			/*
			 * 如上的实现和下面的方法调用的实现是等价的，同样地丢弃了16位字符的高8位。 _strSrc.getBytes(0,
			 * _strSrc.length(), byteStr, 0);
			 * 之所以这样写，而不是String类型的方法调用（如上），是要明确这种丢弃高8位行为。
			 */
			return new String(byteStr, _encoding);
		} catch (Exception ex) {
			return _strSrc; // 出错时，返回源字符串
		}
	}

	/**
	 * 将指定的字符串转化为ISO-8859-1编码的字符串
	 * 
	 * @param _strSrc
	 *            指定的源字符串
	 * @return 转化后的字符串
	 */
	public static String toISO_8859(String _strSrc) {
		if (_strSrc == null)
			return null;

		try {
			return new String(_strSrc.getBytes(), "ISO-8859-1");
		} catch (Exception ex) {
			return _strSrc;
		}
	}

	/**
	 * 将指定的字符串转化为ISO-8859-1编码的字符串
	 * 
	 * @param _strSrc
	 *            指定的源字符串
	 * @return 转化后的字符串
	 * @deprecated 含义模糊，已经使用toISO_8859替换
	 */
	public static String toUnicode(String _strSrc) {
		return toISO_8859(_strSrc);
	}

	/**
	 * 提取字符串UTF8编码的字节流
	 * <p>
	 * 说明：等价于 <code>_string.getBytes("UTF8")</code>
	 * </p>
	 * 
	 * @param _string
	 *            源字符串
	 * @return UTF8编码的字节数组
	 */
	public static byte[] getUTF8Bytes(String _string) {
		char[] c = _string.toCharArray();
		int len = c.length;
		int count = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 0x7f) {
				count++;
			} else if (ch <= 0x7ff) {
				count += 2;
			} else {
				count += 3;
			}
		}

		// Now return the encoded bytes...
		byte[] b = new byte[count];
		int off = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 0x7f) {
				b[off++] = (byte) ch;
			} else if (ch <= 0x7ff) {
				b[off++] = (byte) ((ch >> 6) | 0xc0);
				b[off++] = (byte) ((ch & 0x3f) | 0x80);
			} else {
				b[off++] = (byte) ((ch >> 12) | 0xe0);
				b[off++] = (byte) (((ch >> 6) & 0x3f) | 0x80);
				b[off++] = (byte) ((ch & 0x3f) | 0x80);
			}
		}
		return b;
	}

	// come from java.util.ZipInputStream
	public static String getUTF8String(byte[] b) {
		return getUTF8String(b, 0, b.length);
	}

	/**
	 * 从指定的字节数组中提取UTF8编码的字符串
	 * <p>
	 * 说明：函数等价于 <code> new String(b,"UTF8") </code>
	 * </p>
	 * 
	 * @param b
	 *            指定的字节数组（UTF8编码）
	 * @param off
	 *            开始提取的字节起始位置；可选参数，缺省值为0；
	 * @param len
	 *            提取的字节数；可选择书，缺省值为全部。
	 * @return 提取后得到的字符串。
	 */
	public static String getUTF8String(byte[] b, int off, int len) {
		// First, count the number of characters in the sequence
		int count = 0;
		int max = off + len;
		int i = off;
		while (i < max) {
			int c = b[i++] & 0xff;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				count++;
				break;
			case 12:
			case 13:
				// 110xxxxx 10xxxxxx
				if ((b[i++] & 0xc0) != 0x80) {
					throw new IllegalArgumentException();
				}
				count++;
				break;
			case 14:
				// 1110xxxx 10xxxxxx 10xxxxxx
				if (((b[i++] & 0xc0) != 0x80) || ((b[i++] & 0xc0) != 0x80)) {
					throw new IllegalArgumentException();
				}
				count++;
				break;
			default:
				// 10xxxxxx, 1111xxxx
				throw new IllegalArgumentException();
			}
		}
		if (i != max) {
			throw new IllegalArgumentException();
		}
		// Now decode the characters...
		char[] cs = new char[count];
		i = 0;
		while (off < max) {
			int c = b[off++] & 0xff;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				cs[i++] = (char) c;
				break;
			case 12:
			case 13:
				// 110xxxxx 10xxxxxx
				cs[i++] = (char) (((c & 0x1f) << 6) | (b[off++] & 0x3f));
				break;
			case 14:
				// 1110xxxx 10xxxxxx 10xxxxxx
				int t = (b[off++] & 0x3f) << 6;
				cs[i++] = (char) (((c & 0x0f) << 12) | t | (b[off++] & 0x3f));
				break;
			default:
				// 10xxxxxx, 1111xxxx
				throw new IllegalArgumentException();
			}
		}
		return new String(cs, 0, count);
	}

	// ==============================================================================
	// 字符串显示处理函数

	/**
	 * 将字节数据输出为16进制数表示的字符串
	 * 
	 * @see byteToHexString( byte[] _bytes, char _delim )
	 */
	public static String byteToHexString(byte[] _bytes) {
		return byteToHexString(_bytes, ',');
	}

	/**
	 * 将字节数据输出为16进制无符号数表示的字符串
	 * 
	 * @param _bytes
	 *            字节数组
	 * @param _delim
	 *            字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
	 * @return 16进制无符号数表示的字节数据
	 */
	public static String byteToHexString(byte[] _bytes, char _delim) {
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0) {
				sRet.append(_delim);
			}
			sRet.append(Integer.toHexString(_bytes[i]));
		}
		return sRet.toString();
	}

	/**
	 * 将字节数据输出为指定进制数表示的字符串（注意：负数带有负号）
	 * 
	 * @param _bytes
	 *            字节数组
	 * @param _delim
	 *            字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
	 * @param _radix
	 *            进制数（如16进制）
	 * @return 指定进制数表示的字节数据（负数带由负号）
	 */
	public static String byteToString(byte[] _bytes, char _delim, int _radix) {
		StringBuffer sRet = new StringBuffer();
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0) {
				sRet.append(_delim);
			}
			sRet.append(Integer.toString(_bytes[i], _radix));
		}
		return sRet.toString();
	}

	/**
	 * 用于在Html中显示文本内容
	 * 
	 * @see <code>transDisplay( String _sContent, boolean _bChangeBlank )</code>
	 */
	public static String transDisplay(String _sContent) {
		return transDisplay(_sContent, true);
	}

	/**
	 * 用于在Html中显示文本内容。将空格等转化为html标记。
	 * <p>
	 * 说明：处理折行时，若使用 <code>style="WORD_WRAP:keepall"</code> ，则不能将空格转换为
	 * <code>&amp;nbsp;</code>
	 * </p>
	 * 
	 * @param _sContent
	 *            要显示的内容
	 * @param _bChangeBlank
	 *            是否转换空格符；可选参数，默认值为true.
	 * @return 转化后的Html文本
	 */
	public static String transDisplay(String _sContent, boolean _bChangeBlank) {
		if (_sContent == null)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer(nSrcLen * 2);

		int i;
		char cTemp;
		for (i = 0; i < nSrcLen; i++) {
			cTemp = srcBuff[i];
			switch (cTemp) {
			case ' ':
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '\n':
				// 再处理段首和段尾的时候处理
				if (_bChangeBlank)
					retBuff.append("<br>");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			case '&':
				// 处理unicode代码
				boolean bUnicode = false;
				for (int j = (i + 1); j < nSrcLen && !bUnicode; j++) {
					cTemp = srcBuff[j];
					if (cTemp == '#' || cTemp == ';') {
						retBuff.append("&");
						bUnicode = true;
					}
				}
				if (!bUnicode)
					retBuff.append("&amp;");
				break;
			case 9: // Tab
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;"
						: "    ");
				break;

			default:
				retBuff.append(cTemp);
			}// case
		}

		// 如果替换了空格，直接返回，否则还需要
		if (_bChangeBlank)
			return retBuff.toString();

		// 需要特殊处理段首和段尾
		return replaceStartEndSpaces(retBuff.toString());

	}// END:transDisplay

	// 将指定文本内容，格式化为bbs风格的Html文本字符串。
	// 参数：p_strContent：p_sQuoteColor：
	// _bChangeBlank
	//
	public static String transDisplay_bbs(String _sContent, String p_sQuoteColor) {
		return transDisplay_bbs(_sContent, p_sQuoteColor, true);
	}

	/**
	 * 将指定文本内容，格式化为bbs风格的Html文本字符串。
	 * <p>
	 * 说明：[1]处理折行时，若使用 style="WORD_WRAP:keepall"，则不能将空格转换为
	 * <code>&amp;nbsp;</code>
	 * </p>
	 * <p>
	 * [2]该函数格式化时，如果遇到某一行以":"开始，则认为是引用语(quote)，
	 * </p>
	 * <p 并用参数p_sQuoteColor指定的颜色显示。
	 * </p>
	 * 
	 * @param _sContent
	 *            文本内容；
	 * @param p_sQuoteColor
	 *            引用语的显示颜色。
	 * @param _bChangeBlank
	 *            是否转换空格符，可省略，默认值为true
	 * @return 转化后的Html文本
	 */
	public static String transDisplay_bbs(String _sContent,
			String p_sQuoteColor, boolean _bChangeBlank) {
		if (_sContent == null)
			return "";

		int i;
		char cTemp;
		boolean bIsQuote = false; // 是否是引用语
		boolean bIsNewLine = true; // 是否是新的一行

		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer((int) (nSrcLen * 1.8));

		for (i = 0; i < nSrcLen; i++) {
			cTemp = srcBuff[i];
			switch (cTemp) {
			case ':': {
				if (bIsNewLine) {
					bIsQuote = true;
					retBuff.append("<font color=" + p_sQuoteColor + ">:");
				} else {
					retBuff.append(":");
				}
				bIsNewLine = false;
				break;
			}

			case ' ': {
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				bIsNewLine = false;
				break;
			}
			case '<': {
				retBuff.append("&lt;");
				bIsNewLine = false;
				break;
			}
			case '>': {
				retBuff.append("&gt;");
				bIsNewLine = false;
				break;
			}
			case '"': {
				retBuff.append("&quot;");
				bIsNewLine = false;
				break;
			}
			case '&': { 
				retBuff.append("&amp;");
				bIsNewLine = false;
				break;
			}

			case 9: {// Tab
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;"
						: "    ");
				bIsNewLine = false;
				break;
			}

			case '\n': {
				if (bIsQuote) {
					bIsQuote = false;
					retBuff.append("</font>");
				}
				retBuff.append("<br>");
				bIsNewLine = true;
				break;
			}
			default: {
				retBuff.append(cTemp);
				bIsNewLine = false;
			}
			}// end case
		}// end for
		if (bIsQuote) {
			retBuff.append("</font>");
		}
		return retBuff.toString();
	}// END: transDisplay_bbs

	/**
	 * javascript显示处理，用于处理javascript中的文本字符串显示
	 * 
	 * @param _sContent
	 *            javascript文本
	 * @return 处理后的javascript文本
	 */
	public static String transJsDisplay(String _sContent) {
		if (_sContent == null)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer((int) (nSrcLen * 1.5));

		int i;
		char cTemp;
		for (i = 0; i < nSrcLen; i++) {
			cTemp = srcBuff[i];
			switch (cTemp) {
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case 34: // "
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}
		return retBuff.toString();
	}// END:transJsDisplay

	/**
	 * 字符串的掩码显示：用指定的掩码构造与指定字符串相同长度的字符串
	 * <p>
	 * 用于：密码显示等需要掩码处理的场合
	 * </p>
	 * 
	 * @param _strSrc
	 *            源字符串
	 * @param p_chrMark
	 *            指定的掩码
	 * @return 用掩码处理后的字符串
	 */
	public static String transDisplayMark(String _strSrc, char p_chrMark) {
		if (_strSrc == null)
			return "";

		// else
		char[] buff = new char[_strSrc.length()];
		for (int i = 0; i < buff.length; i++) {
			buff[i] = p_chrMark;
		}
		return new String(buff);
	}

	// ==============================================================================
	// 字符串过滤函数

	/**
	 * SQL语句特殊字符过滤处理函数
	 * <p>
	 * 用于：构造SQL语句时，填充字符串参数时使用
	 * </p>
	 * <p>
	 * 如：
	 * <code>String strSQL = "select * from tbName where Name='"+CMyString.filterForSQL("a'bc")+"'" </code>
	 * </p>
	 * <p>
	 * 说明：需要处理的特殊字符及对应转化规则：如： <code> ' ---&gt;''</code>
	 * </p>
	 * <p>
	 * 不允许使用的特殊字符： <code> !@#$%^&*()+|-=\\;:\",./&lt;&gt;? </code>
	 * </p>
	 * 
	 * @param _sContent
	 *            需要处理的字符串
	 * @return 过滤处理后的字符串
	 */
	public static String filterForSQL(String _sContent) {
		if (_sContent == null)
			return "";

		int nLen = _sContent.length();
		if (nLen == 0)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.5));

		// 各个应用都需要不去除特殊字符，特修改
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '\'': {
				retBuff.append("''");
				break;
			}
			case ';':// 为了查询Unicode字符
				boolean bSkip = false;
				for (int j = (i + 1); j < nLen && !bSkip; j++) {
					char cTemp2 = srcBuff[j];
					if (cTemp2 == ' ')
						continue;
					if (cTemp2 == '&')
						retBuff.append(';');
					bSkip = true;
				}
				if (!bSkip)
					retBuff.append(';');
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	/**
	 * XML文本过滤处理函数：将 <code> & &lt; &gt;\ </code> 等特殊字符做转化处理
	 * 
	 * @param _sContent
	 *            指定的XML文本内容
	 * @return 处理后的文本内容
	 */
	public static String filterForXML(String _sContent) {
		if (_sContent == null)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";

		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&': // 转化：& -->&amp;
				retBuff.append("&amp;");
				break;
			case '<': // 转化：< --> &lt;
				retBuff.append("&lt;");
				break;
			case '>': // 转化：> --> &gt;
				retBuff.append("&gt;");
				break;
			case '\"': // 转化：" --> &quot;
				retBuff.append("&quot;");
				break;
			case '\'': // 转化：' --> &apos;
				retBuff.append("&apos;");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	/**
	 * HTML元素value值过滤处理函数：将 <code> & &lt; &gt;\ </code> 等特殊字符作转化处理
	 * 
	 * @sample <code>
	 *    &lt;input type="text" name="Name" value="<%=CMyString.filterForHTMLValue(sContent)%>"&gt;
	 * </code>
	 * @param _sContent
	 *            指定的文本内容
	 * @return 处理后的文本内容
	 */
	public static String filterForHTMLValue(String _sContent) {
		if (_sContent == null)
			return "";
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";

		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&': // 转化：& -->&amp;
				// 处理unicode代码
				if ((i + 1) < nLen) {
					cTemp = srcBuff[i + 1];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else
					retBuff.append("&amp;");
				break;
			case '<': // 转化：< --> &lt;
				retBuff.append("&lt;");
				break;
			case '>': // 转化：> --> &gt;
				retBuff.append("&gt;");
				break;
			case '\"': // 转化：" --> &quot;
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	/**
	 * URL过滤处理函数：将 <code> # & </code> 等特殊字符作转化处理
	 * 
	 * @param _sContent
	 *            指定的URL内容
	 * @return 处理后的字符串
	 */
	public static String filterForUrl(String _sContent) {
		if (_sContent == null)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";

		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '%':
				retBuff.append("%25");
				break;
			case '?':
				retBuff.append("%3F");
				break;
			case '#': // 转化：# --> %23
				retBuff.append("%23");
				break;
			case '&': // 转化：& --> %26
				retBuff.append("%26");
				break;
			case ' ': // 转化：空格 --> %20
				retBuff.append("%20");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	// 修正转换错误
	/**
	 * JavaScript过滤处理函数：将指定文本中的 <code> " \ \r \n</code> 等特殊字符做转化处理
	 * 
	 * @sample <code>
	 *      <br>&lt;script language="javascript"&gt;
	 *      <br>     document.getElementById("id_txtName").value = "<%=CMyString.filterForJs(sValue)%>";
	 *      <br>&lt;/script&gt;
	 * </code>
	 * @param _sContent
	 *            指定的javascript文本
	 * @return 转化处理后的字符串
	 */
	public static String filterForJs(String _sContent) {
		if (_sContent == null)
			return "";

		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";

		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '"': // 转化：" --> \"
				retBuff.append("\\\"");
				break;
			case '\'': // 转化：' --> \'
				retBuff.append("\\\'");
				break;
			case '\\': // 转化：\ --> \\
				retBuff.append("\\\\");
				break;
			case '\n':
				retBuff.append("\\n");
				break;
			case '\r':
				retBuff.append("\\r");
				break;
			case '\f':
				retBuff.append("\\f");
				break;
			case '\t':
				retBuff.append("\\t");
				break;
			case '/':
				retBuff.append("\\/");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	// ==============================================================================
	// 数字转化为字符串

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @see numberToStr( int _nValue, int _length, char _chrFill )
	 */
	public static String numberToStr(int _nValue) {
		return numberToStr(_nValue, 0);
	}

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @see numberToStr( int _nValue, int _length, char _chrFill )
	 */
	public static String numberToStr(int _nValue, int _length) {
		return numberToStr(_nValue, _length, '0');
	}

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @param _nValue
	 *            指定整数
	 * @param _length
	 *            转化后字符串长度；若实际长度小于该长度，则使用_chrFill左填充; 可选参数，缺省值0，表示按照实际长度，不扩展。
	 * @param _chrFill
	 *            当整数的实际位数小于指定长度时的填充字符；可选参数，缺省值'0'
	 * @return 转化后的字符串
	 */
	public static String numberToStr(int _nValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_nValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	// 重载：使用long型数值
	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @see <code> numberToStr( long _lValue, int _length, char _chrFill ) </code>
	 */
	public static String numberToStr(long _lValue) {
		return numberToStr(_lValue, 0);
	}

	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @see <code> numberToStr( long _lValue, int _length, char _chrFill ) </code>
	 */
	public static String numberToStr(long _lValue, int _length) {
		return numberToStr(_lValue, _length, '0');
	}

	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @param _lValue
	 *            指定长整数
	 * @param _length
	 *            转化后字符串长度；若实际长度小于该长度，则使用_chrFill左填充; 可选参数，缺省值0，表示按照实际长度，不扩展。
	 * @param _chrFill
	 *            当整数的实际位数小于指定长度时的填充字符；可选参数，缺省值'0'
	 * @return 转化后的字符串
	 */
	public static String numberToStr(long _lValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_lValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	// ==============================================================================
	// 其他字符串处理函数

	/**
	 * 字符串翻转：对于给定的字符串，按相反的顺序输出
	 * 
	 * @param _strSrc
	 *            指定的字符串
	 * @return 翻转后的字符串
	 */
	public static String circleStr(String _strSrc) {
		if (_strSrc == null)
			return null; // 错误保护
		
		StringBuffer sResult = new StringBuffer();
		int nLength = _strSrc.length();
		for (int i = nLength - 1; i >= 0; i--) {
			sResult.append(_strSrc.charAt(i));
		}// end for
		return sResult.toString();
	}

	/**
	 * 判断指定的字符是不是汉字，目前是通过判断其值是否大于7FH实现的。
	 * 
	 * @param c
	 *            指定的字符
	 * @return 是否汉字
	 */
	public final static boolean isChineseChar(int c) {
		return c > 0x7F;
	}

	/**
	 * 返回指定字符的显示宽度，在目前的实现中，认为一个英文字符的显示宽度是1，一个汉字的显示宽度是2。
	 * 
	 * @param c
	 *            指定的字符
	 * @return 指定字符的显示宽度
	 */
	public final static int getCharViewWidth(int c) {
		return isChineseChar(c) ? 2 : 1;
	}

	/**
	 * 返回指定字符串的显示宽度
	 * 
	 * @param s
	 *            指定的字符串
	 * @return 指定字符串的显示宽度
	 */
	public final static int getStringViewWidth(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		int iWidth = 0;
		int iLength = s.length();

		for (int i = 0; i < iLength; i++) {
			iWidth += getCharViewWidth(s.charAt(i));
		}

		return iWidth;
	}

	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串； 说明：英文和数字字符长度记1；中文字符长度记2。
	 * 
	 * @param _string
	 *            要截断的字符串。
	 * @param _maxLength
	 *            截断长度。
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 */
	public static String truncateStr(String _string, int _maxLength) {
		return truncateStr(_string, _maxLength, "..");
	}

	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串； 说明：英文和数字字符长度记1；中文字符长度记2。
	 * 
	 * @param _string
	 *            要截断的字符串。
	 * @param _maxLength
	 *            截断长度。
	 * @param _sExt
	 *            在截断后的字符串上的附加的字符串
	 * @return 截断后的字符串
	 */
	public static String truncateStr(String _string, int _maxLength,
			String _sExt) {
		if (_string == null) {
			return null;
		}

		if (_sExt == null) {
			_sExt = "..";
		}

		int nSrcLen = getStringViewWidth(_string);
		if (nSrcLen <= _maxLength) {
			// 源字符串太短，不需要截断
			return _string;
		}

		int nExtLen = getStringViewWidth(_sExt);
		if (nExtLen >= _maxLength) {
			// 目标长度太短（小于了附加字符串的长度），无法截断。
			return _string;
		}

		int iLength = _string.length();
		int iRemain = _maxLength - nExtLen;
		StringBuffer sb = new StringBuffer(_maxLength + 2); // 附加的“2”是没有意义的，只是为了容错

		for (int i = 0; i < iLength; i++) {
			char aChar = _string.charAt(i);
			int iNeed = getCharViewWidth(aChar);
			if (iNeed > iRemain) {
				sb.append(_sExt);
				break;
			}
			sb.append(aChar);
			iRemain = iRemain - iNeed;
		}

		return sb.toString();
	}

	/**
	 * 过滤掉XML不接受的字符
	 * 
	 * @param _string
	 *            源字符串
	 * @return
	 */
	public static String filterForJDOM(String _string) {
		if (_string == null)
			return null;

		char[] srcBuff = _string.toCharArray();
		int nLen = srcBuff.length;

		StringBuffer dstBuff = new StringBuffer(nLen);

		for (int i = 0; i < nLen; i++) {
			char aChar = srcBuff[i];
			if (!isValidCharOfXML(aChar))
				continue;

			dstBuff.append(aChar); // 检查是否还有字符
		}// end for
		return dstBuff.toString();

	}

	/**
	 * 校验当前字符是否是合法的XML字符
	 * 
	 * @param _char
	 *            需要校验的字符
	 * @return
	 */
	public static boolean isValidCharOfXML(char _char) {
		if (_char == 0x9 || _char == 0xA || _char == 0xD
				|| (0x20 <= _char && _char <= 0xD7FF)
				|| (0xE000 <= _char && _char <= 0xFFFD)
				|| (0x10000 <= _char && _char <= 0x10FFFF)) {
			return true;
		}
		return false;
	}

	/**
	 * 计算字符串所占的字节数；
	 * <p>
	 * 说明：英文和数字字符长度记1；中文字符长度记2。
	 * </p>
	 * 
	 * @param _string
	 *            要截断的字符串。
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 */
	public static int getBytesLength(String _string) {
		if (_string == null)
			return 0;

		char[] srcBuff = _string.toCharArray();

		int nGet = 0; // 已经取得的字符串长度（长度：英文字符记1，中文字符记2）
		for (int i = 0; i < srcBuff.length; i++) {
			char aChar = srcBuff[i];
			nGet += (aChar <= 0x7f ? 1 : 2); // （长度：英文字符记1，中文字符记2）
		}// end for
		return nGet;
	}

	// 新增接口：截取规定长度的字符串
	// 程序说明
	// 给定一个字符串，不管是英文还是中文，还是中英文混合的，只取前面的n个英文字母占位的宽度。
	// 如果字符串本身的长度小于需要截取的长度，则直接取该字符串，否则：
	// 当最后一个字为中文，并且前面已经取得 n-1 位时，就不再取这个字，在最后位置补"..."。
	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串；
	 * <p>
	 * 说明：英文和数字字符长度记1；中文字符长度记2。
	 * </p>
	 * 
	 * @param _string
	 *            要截断的字符串。
	 * @param _length
	 *            截断长度。
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 * @deprecated 已经由函数truncateStr替代
	 */
	public static String cutStr(String _string, int _length) {
		return truncateStr(_string, _length);

	}

	public static String[] split(String _str, String _sDelim) {
		if (_str == null || _sDelim == null) {
			return new String[0];
		}
		java.util.StringTokenizer stTemp = new java.util.StringTokenizer(_str,
				_sDelim);
		int nSize = stTemp.countTokens();
		if (nSize == 0) {
			return new String[0];
		}

		String[] str = new String[nSize];
		int i = 0;
		while (stTemp.hasMoreElements()) {
			str[i] = stTemp.nextToken().trim();
			i++;
		}// endwhile
		return str;
	}

	/**
	 * 获取按照指定的分隔符截取到的字符个数
	 * 
	 * @param _str
	 *            指定的字符数
	 * @param _sDelim
	 *            指定的分隔符
	 * @return 分隔的字符个数（int）
	 */
	public static int countTokens(String _str, String _sDelim) {
		java.util.StringTokenizer stTemp = new java.util.StringTokenizer(_str,
				_sDelim);
		return stTemp.countTokens();
	}

	/**
	 * @param _str
	 *            if <code>null</code> or empty string return an array with
	 *            zero length.
	 * @param _sDelim
	 *            if <code>null</code> or empty string then this will set to
	 *            <code>,</code>
	 * @return
	 */
	public static int[] splitToInt(String _str, String _sDelim) {
		//  add comment:如果是空串,返回长度为0的数组
		if (isEmpty(_str)) {
			return new int[0];
		}
		if (isEmpty(_sDelim)) {
			_sDelim = ",";
		}

		java.util.StringTokenizer stTemp = new java.util.StringTokenizer(_str,
				_sDelim);
		int[] arInt = new int[stTemp.countTokens()];
		int nIndex = 0;
		String sValue;
		while (stTemp.hasMoreElements()) {
			sValue = (String) stTemp.nextElement();
			arInt[nIndex] = Integer.parseInt(sValue.trim());
			nIndex++;
		}
		return arInt;
	}

	/**
	 * 处理XML内容时 <BR>
	 * 如果有CDATA嵌套则替换
	 * 
	 * @param _str
	 * @return
	 */
	public static final String encodeForCDATA(String _str) {
		if (_str == null || _str.length() < 1) {
			return _str;
		}

		return replaceStr(_str, CDATA_END, CDATA_END_REPLACER);
	}

	/**
	 * 处理XML内容 <BR>
	 * 如果有经过@see #encodeForCDATA(String)替换的CDATA嵌套则还原
	 * 
	 * @param _str
	 * @return
	 */
	public static final String decodeForCDATA(String _str) {
		if (_str == null || _str.length() < 1) {
			return _str;
		}

		return replaceStr(_str, CDATA_END_REPLACER, CDATA_END);
	}

	private static final String CDATA_END = "]]>";

	private static final String CDATA_END_REPLACER = "(TRSWCM_CDATA_END_HOLDER_TRSWCM)";

	//add comment:添加判断字符串中是否有中文字符的方法

	/**
	 * 判断字符串中是否包含中文字符 <BR>
	 * 如果包含,则返回 <code>true<code>
	 * 
	 * @param _str 指定的字符串
	 * @return
	 */
	public static final boolean isContainChineseChar(String _str) {
		if (_str == null) {
			return false;
		}

		return (_str.getBytes().length != _str.length());
	}

	/**
	 * 将一个数组按照给定的连接符联结起来
	 * 
	 * @param _arColl
	 *            进行操作的数组
	 * @param _sSeparator
	 *            连接符
	 * @return 连接后的字符串
	 */
	public static String join(ArrayList<?> _arColl, String _sSeparator) {
		// check parameters
		if (_arColl == null)
			return null;

		// invoke reload-method and return
		return join(_arColl.toArray(), _sSeparator);
	}
	/**
	 * 将一个数组按照给定的连接符联结起来
	 * 
	 * @param _arColl
	 *            进行操作的数组
	 * @param _sSeparator
	 *            连接符
	 * @return 连接后的字符串
	 */
	public static String join(Object[] _arColl, String _sSeparator) {
		// check parameters
		if (_arColl == null || _arColl.length == 0 || _sSeparator == null)
			return null;

		if (_arColl.length == 1)
			return _arColl[0].toString();

		// resolve the demiter into the string
		StringBuffer result = new StringBuffer(_arColl[0].toString());
		for (int i = 1; i < _arColl.length; i++) {
			result.append(_sSeparator);
			result.append(_arColl[i].toString());
		}

		// return the result
		return result.toString();
	}

	public static boolean containsCDATAStr(String _sValue) {
		if (_sValue == null)
			return false;

		return _sValue.matches("(?ism).*<!\\[CDATA\\[.*|.*\\]\\]>.*");
	}

	public static String transPrettyUrl(String _sUrl, int _nMaxLen) {
		return transPrettyUrl(_sUrl, _nMaxLen, null);
	}

	public static String transPrettyUrl(String _sUrl, int _nMaxLen,
			String _sSkimWord) {
		int nDemPos = 0;
		if (_sUrl == null || _nMaxLen <= 0 || _sUrl.length() <= _nMaxLen
				|| (nDemPos = _sUrl.lastIndexOf('/')) == -1) {
			return _sUrl;
		}
		// else
		int nFirstPartDemPos = _sUrl.lastIndexOf("://") + 3;
		String sFirstPart = _sUrl.substring(0, nFirstPartDemPos);
		String sMidPart = _sUrl.substring(nFirstPartDemPos, nDemPos);
		if (sMidPart.length() < 3) {
			return _sUrl;
		}
		int nMidLen = (_nMaxLen + sMidPart.length() - _sUrl.length());
		if (nMidLen <= 3) {
			nMidLen = 3;
		}
		sMidPart = sMidPart.substring(0, nMidLen);
		sMidPart += (_sSkimWord != null ? _sSkimWord : "....");

		String sLastPart = _sUrl.substring(nDemPos);
		return sFirstPart + sMidPart + sLastPart;
	}

	/**
	 * 替换段首和段尾的空格
	 * 
	 * @param _strValue
	 * @return
	 */
	public static String replaceStartEndSpaces(String _strValue) {
		Pattern pattern = Pattern.compile("(?m)^(\\s*)(.*?)(\\s*)$");
		Matcher matcher = pattern.matcher(_strValue);
		int nLineCount = 30;
		StringBuffer sbResult = new StringBuffer(nLineCount * 100
				+ _strValue.length());
		while (matcher.find()) {
			// 替换段首的空格
			String sStartSpaces = matcher.group(1);
			for (int i = 0; i < sStartSpaces.length(); i++) {
				char c = sStartSpaces.charAt(i);
				if (c == ' ')
					sbResult.append("&nbsp;");
				else {
					sbResult.append(c);
					// 追加<BR>
					if (c == '\n' || c == '\r')
						sbResult.append("<BR>");
				}
			}

			// 追加正文
			sbResult.append(matcher.group(2));

			// 替换结尾的空格
			String sEndSpaces = matcher.group(3);
			char c = 0;
			for (int i = 0; i < sEndSpaces.length(); i++) {
				c = sEndSpaces.charAt(i);
				if (c == ' ')
					sbResult.append("&nbsp;");
				else {
					sbResult.append(c);
					// 追加<BR>
					if (c == '\n' || c == '\r')
						sbResult.append("<BR>");
				}
			}

		}

		return sbResult.toString();
	}
	
	/**
	 * 替换掉回车换行符号
	 * @return
	 */
	public static String replaceEnter(String _param){
		String returnValue="";
		Pattern aReplace=Pattern.compile("(\\r|\\n|\\r\\n)+"); //空格的正则
		Matcher aMatcher=aReplace.matcher(_param);
		returnValue=aMatcher.replaceAll("<br>");
		return returnValue;
	}
	
	/**
	 * 替换掉br
	 * @return
	 */
	public static String replaceBr(String _param){
		String returnValue="";
		Pattern aReplace=Pattern.compile("(<br>|</br>|<br />)+"); //空格的正则
		Matcher aMatcher=aReplace.matcher(_param);
		returnValue=aMatcher.replaceAll("");
		return returnValue;
	}

	public static boolean convertBoolean(String str){
		Boolean Bl = Boolean.valueOf(str);
		boolean bl = Bl.booleanValue();
		return bl;
	}
	
	
	public static final Random random = new Random();
	/**
	 * 转码
	 * @param input
	 * @return
	 */
	public static String encodeURIComponent(String input) throws UnsupportedEncodingException{
		if (isEmpty(input)) {
			return input;
		}

		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);
		for (int i = 0; i < l; i++) {
			String e = input.substring(i, i + 1);
			if (ALLOWED_CHARS.indexOf(e) == -1) {
				byte[] b = e.getBytes("utf-8");
				o.append(getHex(b));
				continue;
			}
			o.append(e);
		}
		return o.toString();
	}

	/**
	 * 解码
	 * @param encodedURI
	 * @return
	 */
	public static String decodeURIComponent(String encodedURI) {
		char actualChar;

		StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				int hb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				int lb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = (hb << 4) | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}

	private static String getHex(byte buf[]) {
		StringBuilder o = new StringBuilder(buf.length * 3);
		for (int i = 0; i < buf.length; i++) {
			int n = (int) buf[i] & 0xff;
			o.append("%");
			if (n < 0x10) {
				o.append("0");
			}
			o.append(Long.toString(n, 16).toUpperCase());
		}
		return o.toString();
	}

	/**
	 * 判断某个对象是否为空 集合类、数组做特殊处理
	 * 
	 * @param obj
	 * @return 如为空，返回true,否则false
	 * @author YZH
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;

		// 如果不为null，需要处理几种特殊对象类型
		if (obj instanceof String) {
			return obj.equals("");
		} else if (obj instanceof Collection) {
			// 对象为集合
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		} else if (obj instanceof Map) {
			// 对象为Map
			Map map = (Map) obj;
			return map.size() == 0;
		} else if (obj.getClass().isArray()) {
			// 对象为数组
			return Array.getLength(obj) == 0;
		} else {
			// 其他类型，只要不为null，即不为empty
			return false;
		}
	}

	/**
     * 使用StringTokenizer类将字符串按分隔符转换成字符数组
     * @param string 字符串 
     * @param divisionChar 分隔符
     * @return 字符串数组
     * @see [类、类#方法、类#成员]
     */
    public static String[] stringAnalytical(String string, String divisionChar)
    {
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);
        
        String[] str = new String[tokenizer.countTokens()];
        
        while (tokenizer.hasMoreTokens())
        {
            str[i] = new String();
            str[i] = tokenizer.nextToken();
            i++;
        }
        
        return str;
    }
    
    /**
     * 字符串解析，不使用StringTokenizer类和java.lang.String的split()方法
     * 将字符串根据分割符转换成字符串数组
     * @param string 字符串
     * @param c 分隔符
     * @return 解析后的字符串数组
     */
    public static String[] stringAnalytical(String string, char c)
    {
        //字符串中分隔符的个数
        int count = 0;
        
        //如果不含分割符则返回字符本身
        if (string.indexOf(c) == -1)
        {
            return new String[]{string};
        }
        
        char[] cs = string.toCharArray();
        
        //过滤掉第一个和最后一个是分隔符的情况
        for (int i = 1; i < cs.length -1; i++)
        {
            if (cs[i] == c)
            {
                count++; //得到分隔符的个数
            }
        }
        
        String[] strArray = new String[count + 1];
        int k = 0, j = 0;
        String str = string;
        
        //去掉第一个字符是分隔符的情况
        if ((k = str.indexOf(c)) == 0)
        {
            str = string.substring(k + 1);
        }
        
        //检测是否包含分割符，如果不含则返回字符串
        if (str.indexOf(c) == -1)
        {
            return new String[]{str};
        }
        
        while ((k = str.indexOf(c)) != -1)
        {
            strArray[j++] = str.substring(0, k);
            str = str.substring(k + 1);
            if ((k = str.indexOf(c)) == -1 && str.length() > 0)
            {
                strArray[j++] = str.substring(0);
            }
        }
        
        return strArray;
    }

    /**
     * 去除null以及空格串
     * @return:
     * @author: YZH
     */
    public static String noNull(Object s) {
        if (s == null)
            return "";
        else
            return s.toString().trim();
    }
    
	/**
     * 
     * {将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名}
     * @param s 原文件名
     * @return  重新编码后的文件名
     * @author: YZH
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 0) && (c <= 255)) {
                sb.append(c);
            }
            else {
                byte[] b;

                try {
                    b = Character.toString(c).getBytes("UTF-8");
                }
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static boolean arryContain(String[]arr,String s2){
    	for(int i=0;i<arr.length;i++){
    		String si=arr[i];
    		if(si.equals(s2)){
    			return true;
    		}
    	}
    	return false;
    }
    //格式化时间
    public static String formatNum(Object num,String pattern){
    	DecimalFormat df = new DecimalFormat(pattern);
    	String db = df.format(num);
    	return db;
    }

    //字符串转换成日期(包括年月日时分秒)
    public static Date stringToDateTime(String str,String pattern){
    	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
    	Date dt=null;
    	try {
    		dt = sdf.parse(str);
    		return dt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    public static String checkTips(String tips) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">").append("alert('").append(tips)
                .append("');window.history.back();").append("</script>");
        return sb.toString();
    }
    
    /**
     * 隐藏最后几位手机号
     * @return
     */
    public static String hidePhoneNum(String phone,int num){
    	if(isEmpty(phone)){
    		throw new NullPointerException("手机号码不能为空");
    	}
    	if(phone.length()<num){
    		throw new IllegalArgumentException("手机号码长度不能小于隐藏长度");
    	}
    	return phone.substring(0, phone.length()-num) + getRepeatStr("*", num);
    }
    
    public static String getRepeatStr(String str,int num){
    	StringBuilder sb = new StringBuilder(num+10);
    	for(int i=0;i<num;i++){
    		sb.append(str);
    	}
    	return sb.toString();
    }
    
    /**
     * 获取长度为length的随机字符串
     * @param lenth
     * @return
     */
    public static String getRandomString(int length){
    	StringBuilder sb = new StringBuilder(length);
    	for(int i=0;i<length;i++){
    		sb.append(RANDOM_STR.charAt(random.nextInt(62)));
    	}
    	return sb.toString();
    }
    
    public static String getRandomNum(int length){
    	StringBuilder sb = new StringBuilder(length);
    	for(int i=0;i<length;i++){
    		sb.append(random.nextInt(10));
    	}
    	return sb.toString();
    }
    
}