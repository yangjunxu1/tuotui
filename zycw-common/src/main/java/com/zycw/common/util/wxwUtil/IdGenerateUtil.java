package com.zycw.common.util.wxwUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class IdGenerateUtil {
    private static final String SEED_COULD_NOT_BE_EMPTY = "种子字符串不可为空";

    public IdGenerateUtil() {
    }

    public static String getRandomID(String seed) {
        if (StringUtils.isEmpty(seed)) {
            throw new IllegalArgumentException("种子字符串不可为空");
        } else {
            StringBuilder str = new StringBuilder();
            str.append(DateUtil.parseDateToStr(new Date(), "yyyyMMddHHmmssss"));
            str.append(HashUtil.getStringFormat(seed, 6));
            str.append(RandomNumberGenerateUtil.getRandomNumberGenerate(8));
            return str.toString();
        }
    }

    public static String getBase64UUID() {
        return Base64.encodeBase64String(getUUID().getBytes());
    }

    public static String getUUID() {
        String seed = DateUtil.parseDateToStr(new Date(), "yyyyMMddHHmmssss");
        return getRandomID(seed);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap(500);

        for(int t = 0; t < 500; ++t) {
            String uuid = getBase64UUID();
            System.out.println(uuid);
            if (map.containsKey(uuid)) {
                System.out.println("=============遇到重复的了");
            } else {
                map.put(uuid, uuid);
            }
        }

    }
}
