package com.google.utils

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory  // UPDATED: Using Gson for Java 21
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader

public class ReadGoogleSheets {

	private static final String APPLICATION_NAME = "Katalon Google Sheets Integration"
	private static final String TOKENS_DIRECTORY_PATH = "tokens"
	private static final String CREDENTIALS_FILE_PATH = "client_secret.json"
	
	// UPDATED: Use GsonFactory instead of JacksonFactory
	private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance()
	
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)

	/**
	 * Main Keyword to fetch data from Google Sheet
	 */
	@Keyword
	public static List<List<Object>> getSpreadSheetRecords(String spreadsheetId, String range) {
		try {
			Sheets service = getSheetsService()
			
			// Execute the Request
			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, range)
					.execute()

			List<List<Object>> values = response.getValues()
			
			if (values == null || values.isEmpty()) {
				System.out.println("No data found.")
				return []
			} else {
				return values
			}
		} catch (Exception e) {
			System.err.println("Error reading Google Sheet: " + e.getMessage())
			e.printStackTrace()
			return []
		}
	}

	/**
	 * Internal helper to handle Authorization
	 */
	private static Sheets getSheetsService() throws IOException {
		String projectDir = RunConfiguration.getProjectDir()
		File credFile = new File(projectDir, CREDENTIALS_FILE_PATH)
		
		if (!credFile.exists()) {
			 throw new FileNotFoundException("CRITICAL ERROR: client_secret.json not found! Please check path: " + credFile.getAbsolutePath())
		}

		InputStream keyStream = new FileInputStream(credFile)
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(keyStream))

		// Set up the Flow (Login Logic)
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new File(projectDir, TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build()

		// Trigger the User Auth (Browser Popup) on port 8888
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build()
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user")

		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build()
	}
}
