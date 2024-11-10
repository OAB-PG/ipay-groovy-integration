package com.oab.pg.unique

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException

class OabiPayScript {

    Cipher des = null
    SecretKeySpec desKey = null

    String encryptText(final String plaintext, final String key) throws Exception {
        try {
            byte[] keyBytes = key.getBytes("UTF-8")
            if (keyBytes.length == 16) {
                keyBytes = Arrays.copyOf(keyBytes, 24)
                System.arraycopy(keyBytes, 0, keyBytes, 16, 8)  // Repeat the first 8 bytes
            }
            if (keyBytes.length != 24) {
                throw new InvalidKeyException("Invalid key size for 3DES. Key must be 16 or 24 bytes.")
            }
            Cipher des = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            SecretKeySpec desKey = new SecretKeySpec(keyBytes, "DESede")
            des.init(Cipher.ENCRYPT_MODE, desKey)
            byte[] plaintextBytes = plaintext.getBytes("UTF-8")
            byte[] ciphertext = des.doFinal(plaintextBytes)
            return byteArrayToHex(ciphertext)
        } catch (Exception e) {
            throw e
        }
    }

    String decryptText(final String ciphertextHex, final String key) throws Exception {
        try {
            byte[] keyBytes = key.getBytes("UTF-8")
            if (keyBytes.length == 16) {
                keyBytes = Arrays.copyOf(keyBytes, 24)
                System.arraycopy(keyBytes, 0, keyBytes, 16, 8)  // Repeat the first 8 bytes
            }
            if (keyBytes.length != 24) {
                throw new InvalidKeyException("Invalid key size for 3DES. Key must be 16 or 24 bytes.")
            }
            Cipher des = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            SecretKeySpec desKey = new SecretKeySpec(keyBytes, "DESede")
            des.init(Cipher.DECRYPT_MODE, desKey)
            byte[] ciphertextBytes = hexStringToByteArray(ciphertextHex)
            byte[] plaintextBytes = des.doFinal(ciphertextBytes)
            return new String(plaintextBytes, "UTF-8")
        } catch (Exception e) {
            throw e
        }
    }

    byte[] hexStringToByteArray(String hex) {
        int len = hex.length()
        byte[] data = new byte[len / 2]
        for (int i = 0; i < len; i += 2) {
            data[(int)(i / 2)] = ((byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16)))
        }
        return data
    }

    String byteArrayToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length)
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b))
        }
        return hexString.toString()
    }

    static void main(String[] args) throws Exception {
        String key = "707360962352707360962352"  // 24-byte key for 3DES

        OabiPayScript script = new OabiPayScript()

        // Encrypt text
        String plaintext = "amt=.100&action=1&responseURL=http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpResult.jsp&errorURL=http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpError.jsp&trackId=596184582&udf1=udf111111111111111111111111111111111111111111&udf2=udf2222222222222222222222222222222222222222222&udf3=udf3333333333333333333333333333333333333333333&udf4=udf444444444444444444444444444444444444444444444&udf5=udf55555555555555555555555555555555555555555555555&currencycode=512&langid=USA&id=ipay707336589914&password=Shopping@123&"
        String encryptedText = script.encryptText(plaintext, key)
        println "Encrypted: " + encryptedText

        // Decrypt text
        String decryptedText = script.decryptText(encryptedText, key)
        println "Decrypted: " + decryptedText
    }
}
