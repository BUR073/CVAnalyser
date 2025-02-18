package com.example.utils;

import java.util.Base64;

public class Encrypt {

    public static String encrypt(String text) {
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(text.getBytes());


    };
    public static String decrypt(String text) {
        Base64.Decoder dec = Base64.getDecoder();
        return new String(dec.decode(text));
    };

}