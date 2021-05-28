package com.zycw.common.util.wxwUtil;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CommonUtil {

	/** 单列市信息list(中存放数组:String[] 索引号 0:六位机构码 1:两位机构码 2:城市名称) */
	// 六位机构码,两位机构码,城市名称
	public static final List<String[]> SPECIAL_CITYS = Arrays.asList(new String[][] {
			{ "210200", "92", "大连" }, { "330200", "93", "宁波" }, { "370200", "94", "青岛" }, { "440200", "95", "深圳" },
			{ "350200", "96", "厦门" } });

	/**
	 * 判断是否不为空, 如果是 ""," ","null",null 则返回false
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNotBlank(Object... obj) {
		return !isBlank(obj);
	}

	/**
	 * 判断数组对象是否为空值 , 如果是 ""," ","null",null 则返回true, 对于Collection.size<1也空
	 * 
	 * @param orignalValue
	 *            需要判断的值
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(Object... orignalValue) {
		if (orignalValue == null) {
            return true;
        }
		for (Object obj : orignalValue) {
			if (obj == null) {
				return true;
			} else if ("".equals(obj.toString().trim())) {
				return true;
			} else if ("null".equalsIgnoreCase(obj.toString().trim())) {
				return true;
			} else if (obj instanceof Collection) {
				Collection objCollection = (Collection) obj;
				if (objCollection == null || objCollection.size() < 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 方法名称: getTran<br>
	 * 
	 * @Description:调用SESB获取全局流水号和交易时间
	 * @author WZY 修改日期：2018-06-27
	 * @return
	 */
	public static synchronized String getTran() {
		// 创建全局唯一流水号 格式如下：YYYYMMDDhhmmssSSS<唯一号6位>
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		SecureRandom random = null;
		String tranNo = "";
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			int x = random.nextInt(999999);
			String a = (x + "000000").substring(0, 6);
			// 唯一流水号
			tranNo = time + a;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tranNo;

	}

	/**
	 * 时间精确到毫秒的流水号 yyyyMMddHHmmssSSS + 6位随机数
	 * */
	public static synchronized String getExactTran() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = format.format(date);
		SecureRandom random = null;
		String tranNo = "";
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			int x = random.nextInt(999999);
			String a = (x + "000000").substring(0, 6);
			// 唯一流水号
			tranNo = time + a;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tranNo;
	}
	
	/**
	 * 从城市中获取省份(如果是单列市,返回该单列市)
	 * 
	 * @param cityNo
	 * @return String 省份id
	 */
	public static String filterSeparateCity(String cityNo) {
		String zero = "0";
		String areaId = "";
		if (zero.equals(cityNo)) {
			areaId = "000000";
		} else if (isSpecialCity(cityNo)) {
			areaId = cityNo;
		} else {
			areaId = cityNo.substring(0, 2) + "0000";
		}
		return areaId;
	}

	/**
	 * @Description:根据(六位或两位)机构码判断是否是单列市
	 * @author partrick
	 * @param cityNo
	 *            城市代码(六位或者两位)
	 * @return
	 */
	public static boolean isSpecialCity(String cityNo) {
		for (int i = 0; i < SPECIAL_CITYS.size(); i++) {
			String[] sc = SPECIAL_CITYS.get(i);
			if ((sc[0].substring(0, 4)).equals(cityNo.substring(0, 4))) {
				return true;
			} else if (sc[1].equals(cityNo)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String a="370268";
		System.out.println(a.substring(0,4));
	}
}
