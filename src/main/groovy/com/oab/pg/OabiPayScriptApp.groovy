package com.oab.pg

class OabiPayScriptApp {
    static void main(String[] args) throws Exception {
        // Define the key and other essential parameters
        String paymentUrl = "https://certpayments.oabipay.com/trxns/PaymentHTTP.htm"
        //String paymentUrl = "https://securepayments.oabipay.com/trxns/PaymentHTTP.htm"
        String key = "7073*****************"
        String tranportalId = "ipay*********"
        String pass = "*********"

        String param = "paymentInit"
        String responseURL = "http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpResult.jsp"
        String errorUrl = "http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpError.jsp"

        // Payment details and user-specific information
        String amt = "0.100"
        String action = "1"
        String trackId = "72638462479204"
        String udf1 = "Student Name"
        String udf2 = "Student Contact Number"
        String udf3 = "Roll No"
        String udf4 = "Payment Detail"
        String udf5 = "Helllo"
        String currencycode = "512"
        String langid = "EN"

        // Create and initialize a PaymentRequest object
        PaymentPayload paymentLoad = new PaymentPayload(
                amt: amt,
                action: action,
                responseURL: responseURL,
                errorURL: errorUrl,
                trackId: trackId,
                udf1: udf1,
                udf2: udf2,
                udf3: udf3,
                udf4: udf4,
                udf5: udf5,
                currencycode: currencycode,
                langid: langid,
                id: tranportalId,
                password: pass,
                tokenFlag : "0"
        )

        // Encrypt the request data
        String trandata = paymentLoad.toRequestTranData(key)
        println "Encrypted trandata: $trandata"





        // Decrypt the data to verify the contents
        PaymentPayload decryptedRequest = PaymentPayload.parseResponse(trandata, key)
        println "Decrypted PaymentRequest:"
        println "Amount: ${decryptedRequest.amt}"
        println "Action: ${decryptedRequest.action}"
        println "Track ID: ${decryptedRequest.trackId}"
        println "Currency Code: ${decryptedRequest.currencycode}"
        println "Language ID: ${decryptedRequest.langid}"
        println "Response URL: ${decryptedRequest.responseURL}"
        println "Error URL: ${decryptedRequest.errorURL}"
    }
}