# OabiPayScriptApp

## Overview

**OabiPayScriptApp** is a Groovy-based application designed to interact with the OabiPay payment gateway. The application constructs a payment payload, encrypts it, and sends it securely to the gateway. Additionally, it decrypts the response for verification and processing. This script uses sensitive fields, such as payment ID, transaction result, and authorization code, to securely handle transaction details.

## Requirements

- **Groovy**: Ensure Groovy is installed on your system (check by running `groovy -version`).
- **Internet Access**: Required for connecting to the OabiPay payment gateway.

## Configuration

### Key Parameters

- **paymentUrl**: The payment gateway URL. Use the certified URL for testing and the secure URL for production:
    - `https://certpayments.oabipay.com/trxns/PaymentHTTP.htm` (testing)
    - `https://securepayments.oabipay.com/trxns/PaymentHTTP.htm` (production)
- **key**: Secret encryption key.
- **tranportalId**: Unique identifier for the merchant portal.
- **pass**: Password for authentication.
- **responseURL**: URL to which the payment gateway will redirect upon successful payment.
- **errorUrl**: URL to which the payment gateway will redirect in case of a payment error.

### Payment Details

The script initializes several key payment details and user-specific fields:

- **amt**: Payment amount.
- **action**: Action type for the payment (e.g., "1" for a direct payment request).
- **trackId**: Unique identifier for tracking the payment request.
- **currencycode**: Currency code for the transaction.
- **langid**: Language ID for transaction messages.
- **udf1 to udf5**: Custom fields for storing additional information (e.g., student name, contact, etc.).

## Code Flow

1. **Initialize Payment Payload**: The script initializes a `PaymentPayload` object with payment and user details.
2. **Encrypt Request Data**: The payment payload is encrypted using the specified `key`.
3. **Display Encrypted Data**: Prints the encrypted `trandata` to the console for reference.
4. **Decrypt and Verify**: The encrypted data is decrypted to verify its contents, ensuring the encryption and decryption processes are functioning correctly.

## Example Encrypted Request Format

After encryption, the request data will look like this:

```plaintext
3F96CB1AB17A2FC7A7BF32FF49521D58A959F5D71A892A5E6DC1E65AD40C97205AB040B059B3137962B538FF43BB29881B26CDB79837C848129A0BBBEC2708394AE0B5444BF035CA2131DA07AB5CD79A81D697D5AE85297B6AC1110A75717291434E88D7F3CD2A996D0E906C179D43F5F6383A875D78214FF7E8ED98BE2925F62B5262C4DF6162E0052DF1BF297CAF9AA05C3E85E48B2257EAFFAE6E0A68008A0D542457B73608317C2DF34B03FB8ED1EFA7F8E6EAE8AFA4B3AF1CF68CCE5AC6C7F8921172ED334D07EB10CD84F7F01C107305BAB75E34424C0C32231F7DFD7FD60B48DAFFE70F6CD60B48DAFFE70F6CD60B48DAFFE70F6CD60B48DAFFE70F6C431AAF758695C3C36764F24AFF9F7E51AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308E0C290B58CA8C7FE49180A29B403F5B5076CF73F517A0B58076CF73F517A0B58076CF73F517A0B58076CF73F517A0B587AE7205C053B029E30539EAA15D47ECF384AFEEA3A996440384AFEEA3A996440384AFEEA3A996440384AFEEA3A996440384AFEEA3A99644004EDF773B17F8639115B3669744C3AE7C7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817ACB0740F1EB03261DD69DD566B30B49D43E8B4568E3C5CBFCDCFE97DF28CFA39C8E5849F8903FFFAFE81E23075A8BBD5B33288B85032C63B8D1563DFD31B4043CFBB896F0CD463F477124E046BB5E1F1B
```

## Example Decrypted Response Format

Once decrypted, the response from the payment gateway will contain key transaction details:

```plaintext
paymentid=202431297518718&result=CAPTURED&auth=061632&ref=664342077837&tranid=202431212484124&tokencustid=4012004093969603290006&trackid=638665615016276617&udf1=Student Name&udf2=Student Contact Number&udf3=Roll No&udf4=Payment Detail&udf5=Hello&amt=0.100&tranDate=07-11-2024+07%3A21%3A19.902&tranRequestDate=07-11-2024+07%3A21%3A17.956&tranResponseDate=07-11-2024+07%3A21%3A22.201
```

### Response Fields

- **paymentid**: Unique payment identifier.
- **result**: Transaction result status (e.g., "CAPTURED" for successful payments).
- **auth**: Authorization code from the payment gateway.
- **ref**: Reference number for the transaction.
- **tranid**: Transaction ID.
- **tokencustid**: Customer token ID.
- **trackid**: Tracking ID for the request.
- **udf1** to **udf5**: Custom fields with user-specific information.
- **amt**: Transaction amount.
- **tranDate**: Date and time of the transaction.
- **tranRequestDate**: Request timestamp.
- **tranResponseDate**: Response timestamp.

## Usage

Run the script using the following command:

```bash
groovy OabiPayScriptApp.groovy
```

Upon execution, you will see both the encrypted transaction data and the decrypted response.

### Sample Output

```plaintext
Encrypted trandata: [Encrypted Data String]
Decrypted PaymentPayload:
Amount: 0.100
Action: 1
Track ID: 72638462479204
Currency Code: 512
Language ID: EN
Response URL: http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpResult.jsp
Error URL: http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpError.jsp
...
```

## Error and Troubleshooting

- **Encryption/Decryption Issues**: Ensure the encryption key matches the settings required by the gateway.
- **Connection Issues**: Verify that the `paymentUrl` is accessible. Use tools like `curl` to test connectivity.
- **Invalid Response/Error Handling**: Ensure `responseURL` and `errorURL` are set to accessible endpoints to handle payment results effectively.
