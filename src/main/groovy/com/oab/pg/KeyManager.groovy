package com.oab.pg

import java.security.InvalidKeyException
import java.util.Arrays

class KeyManager {

    byte[] processKey(String key) throws InvalidKeyException {
        byte[] keyBytes = key.getBytes("UTF-8")

        if (keyBytes.length == 16) {
            keyBytes = Arrays.copyOf(keyBytes, 24)
            System.arraycopy(keyBytes, 0, keyBytes, 16, 8)  // Repeat the first 8 bytes
        }

        if (keyBytes.length != 24) {
            throw new InvalidKeyException("Invalid key size for 3DES. Key must be 16 or 24 bytes.")
        }
        return keyBytes
    }
}
