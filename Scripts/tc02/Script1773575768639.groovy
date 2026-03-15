import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.google.utils.ReadGoogleSheets as ReadGoogleSheets
// 1. PASTE YOUR COPIED ID HERE
// 2. DEFINE YOUR RANGE (Sheet Name ! Start : End)
// 3. FETCH & PRINT
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
String sheetID = '106gLU0uleZHHY-qIu4wi6I--YfYq4K9I5tBn34_suXU'
String range = 'Sheet1!A2:B'
List<List> data = ReadGoogleSheets.getSpreadSheetRecords(sheetID, range)
for (def row : data) {
   String user = row.get(0)
   String pass = row.get(1)
   println((('User: ' + user) + ' | Pass: ') + pass)
}


//Test case implementation
for (def row : data) {
   String user = row.get(0)
   String pass = row.get(1)
   WebUI.openBrowser('')
   WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com')
   WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/a_btn-make-appointment'))
   // Using the data from Google Sheets
   WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Username'), user)
   WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Password'), pass)
   WebUI.click(findTestObject('Page_CURA Healthcare Service/button_btn-login'))
   // Add validation or logout here
   WebUI.closeBrowser()
}
