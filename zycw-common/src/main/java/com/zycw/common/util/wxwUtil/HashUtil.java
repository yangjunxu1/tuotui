package com.zycw.common.util.wxwUtil;

import java.util.Base64;

public class HashUtil {
    public HashUtil() {
    }

    public static String getStringFormat(String str, int length) {
        byte[] cs = Base64.getEncoder().encode(str.getBytes());
        char[] result = new char[length];

        int i;
        for(i = 0; i < result.length; ++i) {
            result[i] = '0';
        }

        for(i = 0; i < cs.length; ++i) {
            result[i % length] = (char)(result[i % length] + cs[i]);
            result[i % length] = (char)(result[i % length] % 10 + 48);
        }

        return new String(result);
    }

    public static void main(String[] args) {
        int lengh = 8;
        String seed = "jiangliangliang";
        String a = getStringFormat(seed, lengh);
        System.out.println(a);
        System.out.println(getStringFormat(seed, lengh));
    }
}
