package com.trackgenesis.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encrypt {

    private static SecretKey secretKey;
    private static final String KEY_FILE_PATH = "/Users/henryburbridge/CVAnalyser/src/main/resources/key.txt";

    public static void createKey() {
        try {
            Path keyPath = Paths.get(KEY_FILE_PATH);

            if (Files.exists(keyPath)) {
                try {
                    String encodedKey = Files.readString(keyPath, StandardCharsets.UTF_8);
                    if (encodedKey.isBlank()) {
                        System.err.println("Key file is empty or contains only whitespace. Generating new key.");
                        generateAndStoreKey(keyPath);
                    } else {
                        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                        secretKey = new javax.crypto.spec.SecretKeySpec(decodedKey, "AES");
                        System.out.println("Key loaded from file.");
                    }
                } catch (IOException innerEx) {
                    System.err.println("Error reading key file: " + innerEx.getMessage());
                    innerEx.printStackTrace();
                    generateAndStoreKey(keyPath);
                }
            } else {
                generateAndStoreKey(keyPath);
            }

        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("General error handling key: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error handling key.", e);
        }
    }

    private static void generateAndStoreKey(Path keyPath) throws NoSuchAlgorithmException, IOException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            Path parentDir = keyPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
                System.out.println("Created parent directory: " + parentDir);
            }

            Files.writeString(keyPath, encodedKey, StandardCharsets.UTF_8);
            System.out.println("New key generated and stored at: " + keyPath);
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Error generating or storing key: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static String encrypt(String text) throws Exception {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key is not initialized. Call createKey() first.");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String text) throws Exception {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key is not initialized. Call createKey() first.");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(text);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}