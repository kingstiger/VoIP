package com.security_utils;

import com.models.MicrophoneData;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class EncryptorImpl implements Encryptor {
    private String key;

    @Override
    @SneakyThrows
    public MicrophoneData encrypt(MicrophoneData data) {
        DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);

        byte[] cleartext = data.getData();

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encoded = cipher.doFinal(cleartext);
        data.setData(encoded);
        data.setNumBytesRead(encoded.length);

        return data;
    }
}
