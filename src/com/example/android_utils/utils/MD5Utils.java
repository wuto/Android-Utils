package com.example.android_utils.utils;

import java.security.MessageDigest;

/**
 * Md5工具类
 * 
 */
public class MD5Utils {

    public static String getMD5String(String key) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };
        try {
            byte[] input = key.getBytes();
            // MD5算法的 MessageDigest 对象
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            // 转换
            md5Digest.update(input);
            // 密文
            byte[] md5byte = md5Digest.digest();
            // string 转 十六进制
            int j = md5byte.length;
            char md5char[] = new char[j * 2];
            int k = 0;
            for (byte b : md5byte) {
                md5char[k++] = hexDigits[b >>> 4 & 0xf];
                md5char[k++] = hexDigits[b & 0xf];
            }
            return new String(md5char);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
