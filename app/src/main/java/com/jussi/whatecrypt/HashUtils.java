package com.jussi.whatecrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String getSha256(String str){

        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;

    }


    private static final String HEX = "0123456789abcdef";

    public static String byte2Hex(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0)
            return null;

        StringBuilder sb = new StringBuilder(byteArray.length * 2);

        for (byte b : byteArray) {
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }

}
