package com.sophos.ws.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    public static SecretKey getSecretEncryptionKey(String keyText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(getKey(keyText), "AES");
        return keySpec;
    }

    @SuppressWarnings({"ImplicitArrayToString", "UseSpecificCatch", "CallToPrintStackTrace"})
    public static byte[] getKey(String keyStr) {
        byte[] key = null;
        try {
            key = (keyStr).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static byte[] encryptText(String plainText, SecretKey secKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return byteCipherText;
    }

    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }

    public static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
    public static byte[] parseHexBinary(String hash) {
        return DatatypeConverter.parseHexBinary(hash);
    }
}
