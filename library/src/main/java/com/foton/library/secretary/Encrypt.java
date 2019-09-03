package com.foton.library.secretary;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加解密
 */
public class Encrypt {
    public static void main(String[] arts) {
    }

    /**
     * SHA256加密
     *
     * @param source
     * @return
     */
    public static String encryptSHA256(String source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] sourceBytes = source.getBytes();
            messageDigest.update(sourceBytes);
            String targetString = bytes2Hex(messageDigest.digest());
            return targetString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * MD5
     *
     * @param source
     * @return
     */
    public static String encryptMD5(String source) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(source.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
