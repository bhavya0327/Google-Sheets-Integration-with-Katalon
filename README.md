# Google-Sheets-Integration-with-Katalon
A custom Google Sheets integration for Katalon Studio v11 (Java 21). Bypasses plugin limitations using manual Google API implementation and GSON.


# Katalon Studio: Google Sheets Integration (Java 21)

This repository provides a robust, manual integration between **Katalon Studio v11** and the **Google Sheets API**. It is specifically designed to work with **Java 21**, solving common "Unable to resolve class" and "NoClassDefFoundError" issues found with older plugins.

## 🚀 Key Features
- **Java 21 Compatible:** Uses modern Google API libraries (v2.0.0+).
- **No Plugin Required:** Bypasses the Katalon Store for more control and stability.
- **Dynamic Authorization:** Automatically handles OAuth2 browser popups and token storage.
- **Optimized for GSON:** Uses `GsonFactory` for better performance on modern JVMs.

## 📋 Prerequisites
- Katalon Studio v11+ (Java 21 environment).
- A Google Cloud Project with the **Google Sheets API** enabled.
- A `client_secret.json` file from your Google Cloud Credentials (Desktop App type).

## 🛠️ Setup Instructions

### 1. Credentials
Place your `client_secret.json` in the root folder of this project. 
> **Note:** This file is ignored by Git for security.

### 2. External Libraries
This project requires 12 specific JAR files. Ensure they are added under **Project > Settings > Library Management**:
- `google-api-client-2.2.0`
- `google-api-services-sheets-v4-rev20230815-2.0.0`
- `google-oauth-client-1.34.1`
- `google-oauth-client-jetty-1.34.1`
- `google-oauth-client-java6-1.34.1`
- `google-http-client-1.43.3`
- `google-http-client-gson-1.43.3`
- `gson-2.10.1`
- `opencensus-api-0.31.1`
- `opencensus-contrib-http-util-0.31.1`
- `grpc-context-1.27.2`
- `guava-32.1.3-jre`

### 3. Usage
Call the custom keyword in your test cases:
```groovy
String sheetID = "YOUR_SHEET_ID"
String range = "Sheet1!A2:B"
def data = CustomKeywords.'com.google.utils.ReadGoogleSheets.getSpreadSheetRecords'(sheetID, range)

for (row in data) {
    println "Row data: " + row
}
