package com.oab.pg

import java.net.URLEncoder
import java.net.URLDecoder

class PaymentPayload {
    String amt
    String action
    String responseURL
    String errorURL
    String trackId
    String udf1
    String udf2
    String udf3
    String udf4
    String udf5
    String currencycode
    String langid
    String id
    String password
    String paymentId
    String result
    String auth
    String ref
    String transId
    String error
    String error_text
    String tranDate
    String tranRequestDate
    String tranResponseDate
    String custid
    String tokenFlag
    String tokenNumber
    String tokenCustomerId

    // Encryption utilities as static instances to avoid redundant initialization
    private static final KeyManager keyManager = new KeyManager()
    private static final HexConverter hexConverter = new HexConverter()
    private static final EncryptionManager encryptionManager = new EncryptionManager(keyManager, hexConverter)

    // Constructor to initialize fields using Groovy's metaClass properties dynamically
    PaymentPayload(Map params = [:]) {
        params.each { key, value ->
            if (this.hasProperty(key)) {
                this."$key" = value
            }
        }
    }

    // Convert request data to an encrypted string
    String toRequestTranData(String key) {
        def params = [
                amt: amt, action: action, responseURL: responseURL, errorURL: errorURL,
                trackId: trackId, udf1: udf1, udf2: udf2, udf3: udf3, udf4: udf4, udf5: udf5,
                currencycode: currencycode, langid: langid, id: id, password: password,
                tokenFlag: tokenFlag, tokenNumber: tokenNumber
        ]
        String urlParams = encodeParams(params)
        return encryptionManager.encryptText(urlParams, key)
    }

    // Decodes URL parameters and populates a PaymentRequest object
    static PaymentPayload fromUrlParams(String urlParams) {
        def paramsMap = decodeParams(urlParams)
        return new PaymentPayload(paramsMap)
    }

    // Decrypts and parses the response to populate a PaymentRequest object
    static PaymentPayload parseResponse(String trandata, String key) {
        String decryptedData = encryptionManager.decryptText(trandata, key)
        def paramsMap = decodeParams(decryptedData)
        return new PaymentPayload(paramsMap)
    }

    // Helper method to encode parameters for URL format
    private static String encodeParams(Map params) {
        params.findAll { it.value != null }
                .collect { k, v -> "${URLEncoder.encode(k, 'UTF-8')}=${(v)}" }
                .join('&')
    }

    private static Map decodeParams(String urlParams) {
        urlParams.split('&').collectEntries { param ->
            def parts = param.split('=')
            if (parts.size() == 2) {
                [(URLDecoder.decode(parts[0], 'UTF-8')): (parts[1])]
            } else {
                println "Warning: Skipping malformed parameter: $param"
                [:]
            }
        }
    }

}
