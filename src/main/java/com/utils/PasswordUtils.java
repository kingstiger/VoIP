package com.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class PasswordUtils {

    public static String getPasswordHash(String plainPassword) {
        return Hashing.sha512()
                      .hashString(plainPassword, StandardCharsets.UTF_8)
                      .toString();
    }
}
