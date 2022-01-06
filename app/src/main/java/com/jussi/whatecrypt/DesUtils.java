package com.jussi.whatecrypt;

import android.os.Build;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class DesUtils {

    /**
     * 算法名称
     */
    private static final String ALGORITHM_DES = "DES";

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 注意：
     * 1.CBC 模式下加解密 Cipher 初始化必须传入同一个 IvParameterSpec 实例参数
     * 2.IvParameterSpec 构造函数中参数字节数组长度必须是 8 位
     */
    private static final IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec(RANDOM.generateSeed(8));

    /**
     * CBC 模式加密
     *
     * @param key     密钥
     * @param content 加密原文
     * @param padding 填充模式
     * @return 加密后字符数组
     */
    public static byte[] encryptCbc(byte[] key, byte[] content, Padding padding) {
        try {
            // 生成密钥
            Key desKey = keyGenerator(key);
            // 实例化一个 Cipher 对象用于完成加密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES + "/CBC/" + padding.value);
            // 初始化 Cipher 对象，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, desKey, IV_PARAMETER_SPEC);
            byte[] encrypt_byte = cipher.doFinal(content);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encode(encrypt_byte);
            }
            return encrypt_byte;
        } catch (InvalidKeyException e) {
            throw new UnsupportedOperationException("Invalid Key");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("No such algorithm");
        } catch (InvalidKeySpecException e) {
            throw new UnsupportedOperationException("Invalid key spec");
        } catch (NoSuchPaddingException e) {
            throw new UnsupportedOperationException("No such padding");
        } catch (BadPaddingException e) {
            throw new UnsupportedOperationException("Bad padding");
        } catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("Illegal block size");
        } catch (InvalidAlgorithmParameterException e) {
            throw new UnsupportedOperationException("Illegal algorithm parameter");
        }
    }

    /**
     * CBC 模式解密
     *
     * @param key     密钥
     * @param content 密文
     * @param padding 填充模式
     * @return 原文字符数组
     */
    public static byte[] decryptCbc(byte[] key, byte[] content, Padding padding) {
        try {
            // 生成密钥
            Key desKey = keyGenerator(key);
            // 实例化一个 Cipher 对象用于完成解密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES + "/CBC/" + padding.value);
            // 初始化 Cipher 对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, desKey, IV_PARAMETER_SPEC);
            return cipher.doFinal(content);
        } catch (NoSuchPaddingException e) {
            throw new UnsupportedOperationException("No such padding");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("No such algorithm");
        } catch (InvalidKeyException e) {
            throw new UnsupportedOperationException("Invalid Key");
        } catch (InvalidKeySpecException e) {
            throw new UnsupportedOperationException("Invalid key spec");
        } catch (BadPaddingException e) {
            throw new UnsupportedOperationException("Bad padding");
        } catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("Illegal block size");
        } catch (InvalidAlgorithmParameterException e) {
            throw new UnsupportedOperationException("Illegal algorithm parameter");
        }
    }

    /**
     * 生成 DES 密钥对象
     *
     * @param key 密钥字节数组
     * @return 密钥对象
     * @throws InvalidKeyException      Key 无效
     * @throws NoSuchAlgorithmException 无相关算法
     * @throws InvalidKeySpecException  KeySepc 无效
     */
    private static Key keyGenerator(byte[] key)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec desKeySpec = new DESKeySpec(key);
        // 创建一个密钥工厂，用于转换 DESKeySpec
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
        // 生成一个密钥并返回
        return secretKeyFactory.generateSecret(desKeySpec);
    }

    public static enum Padding {
        NO_PADDING("NoPadding"),
        PKCS5_PADDING("PKCS5Padding");

        private String value;

        Padding(String value) {
            this.value = value;
        }
    }
}
