package com.oab.pg

class HexConverter {
    byte[] hexStringToByteArray(String hex) {
        int len = hex.length()
        byte[] data = new byte[len / 2]
        for (int i = 0; i < len; i += 2) {
            data[(int) (i / 2)] = ((byte) ((Character.digit(hex.charAt(i), 16) << 4)
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
}
