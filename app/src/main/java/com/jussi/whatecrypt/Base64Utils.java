package com.jussi.whatecrypt;

import android.os.Build;

import java.util.Base64;

public class Base64Utils {

    public static String encode(byte[] byteArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new String(Base64.getEncoder().encode(byteArray));
        }
        return null;
    }

    public static byte[] decode(String base64EncodedString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getDecoder().decode(base64EncodedString);
        }
        return null;
    }
}
