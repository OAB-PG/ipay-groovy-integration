package com.oab.pg

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class EncryptionManager {
    private KeyManager keyManager
    private HexConverter hexConverter

    EncryptionManager(KeyManager keyManager, HexConverter hexConverter) {
        this.keyManager = keyManager
        this.hexConverter = hexConverter
    }

    // Encrypt text with 3DES
    String encryptText(String plaintext, String key) throws Exception {
        byte[] keyBytes = keyManager.processKey(key)
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DESede")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        byte[] plaintextBytes = plaintext.getBytes("UTF-8")
        byte[] ciphertext = cipher.doFinal(plaintextBytes)

        return hexConverter.byteArrayToHex(ciphertext)
    }

    // Decrypt text with 3DES
    String decryptText(String ciphertextHex, String key) throws Exception {
        byte[] keyBytes = keyManager.processKey(key)
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DESede")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        byte[] ciphertextBytes = hexConverter.hexStringToByteArray(ciphertextHex)
        byte[] plaintextBytes = cipher.doFinal(ciphertextBytes)

        return new String(plaintextBytes, "UTF-8")
    }
}
