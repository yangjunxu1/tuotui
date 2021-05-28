package com.zycw.common.util.wxwUtil;

import java.security.SecureRandom;

public class RandomNumberGenerateUtil {
    public static final String LENGTH_IS_NOT_ZERO = "length is not zero";
    public static final int INT_MAX_VALUE = 9;
    private static final SecureRandom secureRandom = new SecureRandom();
    public static final int INT_BASE_VALUE = 10;

    public RandomNumberGenerateUtil() {
    }

    public static String getRandomNumberGenerate(int length) {
        if (length == 0) {
            throw new IllegalArgumentException("length is not zero");
        } else {
            return length > 9 ? getRandomNumberGenerateMin(length) : getRandomNumberGenerateMax(length);
        }
    }

    private static String getRandomNumberGenerateMax(int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            stringBuilder.append(secureRandom.nextInt(10));
        }

        return stringBuilder.toString();
    }

    private static String getRandomNumberGenerateMin(int length) {
        return String.valueOf((double)secureRandom.nextInt((int)Math.pow(10.0D, (double)(length - 1))) + Math.pow(10.0D, (double)length));
    }
}