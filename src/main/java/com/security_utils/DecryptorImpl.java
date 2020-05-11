package com.security_utils;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class DecryptorImpl implements Decryptor {
    private String key;

    @Override
    @SneakyThrows
    public DatagramPacket decrypt(DatagramPacket data) {
        byte[] encrypedPwdBytes = data.getData();

        DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance("DES");// cipher is not thread safe
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(encrypedPwdBytes);

        data.setData(bytes);
        data.setLength(bytes.length);

        return data;
    }
}
