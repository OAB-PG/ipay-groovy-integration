Here’s the updated **README** reflecting the change from **PaymentRequest** to **PaymentPayload**.

---

# OabiPayScriptApp

## Overview

**OabiPayScriptApp** is a Groovy script designed to integrate with the OabiPay payment gateway. It constructs a payment request, encrypts it, and sends it securely to the gateway. The script also decrypts the response for verification and processing. The script securely handles sensitive fields such as payment ID, transaction result, authorization code, and other transaction details.

## Requirements

- **Groovy**: Ensure Groovy is installed (`groovy -version`).
- **Internet Access**: Required for accessing the OabiPay payment gateway.

## Configuration

### Key Parameters

- **paymentUrl**: The payment gateway URL (use the certified URL for testing and the secure URL for production).
- **key**: Secret encryption key.
- **tranportalId**: Merchant portal identifier.
- **pass**: Password for authentication.
- **responseURL**: URL for successful payment responses.
- **errorUrl**: URL for error responses.

### Payment Details

- **amt**: Payment amount.
- **action**: Transaction type (e.g., "1" for a direct payment).
- **trackId**: Unique identifier for tracking.
- **currencycode**: Currency code (e.g., "512").
- **langid**: Language ID (e.g., "EN").
- **udf1** to **udf5**: Custom fields for additional details (e.g., user name, contact).

## Script Flow

1. **Construct Payment Payload**: Builds a plaintext request string with all required payment parameters.
2. **Encrypt Request Data**: Encrypts the plaintext request for secure transmission.
3. **Display Encrypted Data**: Prints encrypted transaction data (`trandata`).
4. **Decrypt and Verify**: Decrypts the encrypted response data from OabiPay to verify and process transaction details.

## Request Encryption Format

The request to be encrypted is a plaintext string that includes all required payment parameters, formatted as key-value pairs:

```groovy
String plaintext = "amt=.100&action=1&responseURL=http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpResult.jsp&errorURL=http://localhost:8080/merchantPlugin/shopping/bankhosted/HostedHttpError.jsp&trackId=596184582&udf1=udf111111111111111111111111111111111111111111&udf2=udf2222222222222222222222222222222222222222222&udf3=udf3333333333333333333333333333333333333333333&udf4=udf444444444444444444444444444444444444444444444&udf5=udf55555555555555555555555555555555555555555555555&currencycode=512&langid=USA&id=ipay707336589914&password=Shopping@123&"
```

This `plaintext` string is then encrypted using the secret `key` to form the secure `trandata` that will be sent to the payment gateway.

### Encrypted Request Format

The encrypted version of the request will look like this:

```plaintext
3F96CB1AB17A2FC7A7BF32FF49521D58A959F5D71A892A5E6DC1E65AD40C97205AB040B059B3137962B538FF43BB29881B26CDB79837C848129A0BBBEC2708394AE0B5444BF035CA2131DA07AB5CD79A81D697D5AE85297B6AC1110A75717291434E88D7F3CD2A996D0E906C179D43F5F6383A875D78214FF7E8ED98BE2925F62B5262C4DF6162E0052DF1BF297CAF9AA05C3E85E48B2257EAFFAE6E0A68008A0D542457B73608317C2DF34B03FB8ED1EFA7F8E6EAE8AFA4B3AF1CF68CCE5AC6C7F8921172ED334D07EB10CD84F7F01C107305BAB75E34424C0C32231F7DFD7FD60B48DAFFE70F6CD60B48DAFFE70F6CD60B48DAFFE70F6CD60B48DAFFE70F6C431AAF758695C3C36764F24AFF9F7E51AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308AA0DB1207B296308E0C290B58CA8C7FE49180A29B403F5B5076CF73F517A0B58076CF73F517A0B58076CF73F517A0B58076CF73F517A0B587AE7205C053B029E30539EAA15D47ECF384AFEEA3A996440384AFEEA3A996440384AFEEA3A996440384AFEEA3A996440384AFEEA3A99644004EDF773B17F8639115B3669744C3AE7C7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817AC7BD586AEF4E817ACB0740F1EB03261DD69DD566B30B49D43E8B4568E3C5CBFCDCFE97DF28CFA39C8E5849F8903FFFAFE81E23075A8BBD5B33288B85032C63B8D1563DFD31B4043CFBB896F0CD463F477124E046BB5E1F1B
```

This encrypted `trandata` is transmitted to the payment gateway.

## Encrypted Response Format

After initiating a transaction, the encrypted response from the OabiPay gateway contains transaction details in a specific format. The decrypted response includes key-value pairs for each transaction detail:

```plaintext
paymentid=202431297518718&result=CAPTURED&auth=061632&ref=664342077837&tranid=202431212484124&tokencustid=4012004093969603290006&trackid=638665615016276617&udf1=46&udf2=ReportsDownload&udf3=Udf3&udf4=Udf4&udf5=Udf5&amt=5.0&tranDate=07-11-2024+07%3A21%3A19.902&tranRequestDate=07-11-2024+07%3A21%3A17.956&tranResponseDate=07-11-2024+07%3A21%3A22.201
```

### Response Fields

- **paymentid**: Unique identifier for the payment.
- **result**: Status of the transaction (e.g., "CAPTURED" for successful payments).
- **auth**: Authorization code from the payment gateway.
- **ref**: Reference number for the transaction.
- **tranid**: Transaction ID.
- **tokencustid**: Customer token ID.
- **trackid**: Tracking ID for identifying the request.
- **udf1** to **udf5**: Custom fields as configured in the request.
- **amt**: Transaction amount.
- **tranDate**: Transaction date and time.
- **tranRequestDate**: Timestamp of the request.
- **tranResponseDate**: Timestamp of the response.

## Usage

Run the script using:

```bash
groovy OabiPayScriptApp.groovy
```

The output will display both the encrypted transaction data and the decrypted response for verification.

### Sample Output

```plaintext
Encrypted trandata: [Encrypted Data String]
Decrypted PaymentPayload:
Amount: 5.0
Transaction ID: 202431212484124
Tracking ID: 638665615016276617
Result: CAPTURED
Authorization Code: 061632
...
```

## Error and Troubleshooting

- **Encryption/Decryption Issues**: Ensure the encryption key matches the gateway’s decryption settings.
- **Network Connectivity**: Confirm that `paymentUrl` is accessible.
- **Invalid Response/Error Handling**: Ensure valid URLs for `responseURL` and `errorURL` to handle results effectively.

---

This README now reflects the change from **PaymentRequest** to **PaymentPayload**. Let me know if there are any further adjustments needed!