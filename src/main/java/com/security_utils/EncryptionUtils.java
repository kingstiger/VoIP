package com.security_utils;

import com.utils.PasswordUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
    private String cipherInstance;
    private String key;
    private SecretKeySpec secretKey;
    private Cipher myCipher;

    public EncryptionUtils(String key) throws
                                       NoSuchPaddingException,
                                       NoSuchAlgorithmException {
        this.cipherInstance = "AES/ECB/PKCS5Padding";
        key = PasswordUtils.getSha128(key);
        this.secretKey = new SecretKeySpec(key.getBytes(), "AES");

        this.myCipher = Cipher.getInstance(cipherInstance);
    }

    public byte[] encrypt(byte[] message) throws
                                          InvalidKeyException,
                                          BadPaddingException,
                                          IllegalBlockSizeException {
        myCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return myCipher.doFinal(message);
    }

    public byte[] decrypt(byte[] message) throws
                                          InvalidKeyException,
                                          BadPaddingException,
                                          IllegalBlockSizeException {
        myCipher.init(Cipher.DECRYPT_MODE, secretKey);
        return myCipher.doFinal(message);
    }
}
