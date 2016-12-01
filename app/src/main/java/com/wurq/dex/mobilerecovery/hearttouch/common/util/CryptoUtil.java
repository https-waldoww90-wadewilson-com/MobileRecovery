package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ht-template
 **/
public class CryptoUtil {
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static byte[] seed;

    static {
        System.loadLibrary("CryptoSeed");
    }

    //声明的本地方法
    public static native String getSeed(String id);

    /**
     * @param clearText : the clear text that will be encrypted.
     * @return the encrypted text.
     * @throws Exception : the exception.
     */
    public static String encryptText(String clearText)
            throws CryptoException {
        updateSeed();
        try {
            byte[] rawKey = getRawKey(seed);
            byte[] result = encrypt(rawKey, clearText.getBytes());
            return base64Encode(result);
        } catch (Exception e) {
            CryptoException exception = new CryptoException();
            exception.initCause(e);
            throw exception;
        }
    }

    // Base64加密
    private static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    // Base64解密
    private static byte[] base64Decode(String s) {
        return Base64.decode(s, Base64.DEFAULT);
    }

    /**
     * @param encrypted : the encrypted text.
     * @return the clear text.
     * @throws Exception : the exception.
     */
    public static String decryptText(String encrypted)
            throws CryptoException {
        updateSeed();
        try {
            byte[] rawKey = getRawKey(seed);
            byte[] enc = base64Decode(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result);
        } catch (Exception e) {
            CryptoException exception = new CryptoException();
            exception.initCause(e);
            throw exception;
        }
    }

    /**
     * @param seed : the seed will be used to produce raw key.
     * @return the key bytes
     * @throws Exception : the exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        SecretKeySpec key = new SecretKeySpec(seed, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    /**
     * @param raw   : the raw key.
     * @param clear : the clear bytes.
     * @return the encrypted bytes.
     * @throws Exception : the exception.
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * @param raw       : the raw key.
     * @param encrypted : the encrypted bytes.
     * @return the clear bytes.
     * @throws Exception : the exception.
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String getMD5(String s) {
        return toMd5(s.getBytes());
    }

    private static String toMd5(byte[] bytes) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), true, "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHexString(byte[] bytes, boolean lowerCase, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(byteHEX(b, lowerCase ? DIGITS_LOWER : DIGITS_UPPER)).append(separator);
        }
        return hexString.toString();
    }

    public static String getMD5(File file) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest(), true, "");
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteHEX(byte ib, char[] digit) {
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0F];
        ob[1] = digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    /**
     * Convert hex string to byte[]   把为字符串转化为字节数组
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String encryptToHexString(String key, String text)
            throws Exception {
        byte[] keyByte = hexStringToBytes(key);
        byte[] result = encrypt(keyByte, text.getBytes());
        return toHexString(result, false, "");
    }

    public static String decryptHexString(String key, String text) {
        byte[] keyByte = hexStringToBytes(key);
        try {
            byte[] result = decrypt(keyByte, hexStringToBytes(text));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void updateSeed() {
        //TODO zhengwen 应该和Installation解耦
        if (seed == null) {
            byte[] sourceSeedBytes = getSeed("").getBytes();
            seed = new byte[16];
            for (int i = 0; i < 16; i++) {
                seed[i] = sourceSeedBytes[i];
            }
        }
    }

    public static class CryptoException extends Exception {
    }
}
