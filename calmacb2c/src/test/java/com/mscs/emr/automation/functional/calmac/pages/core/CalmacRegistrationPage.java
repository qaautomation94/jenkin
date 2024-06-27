package com.mscs.emr.automation.functional.calmac.pages.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.testData.MockDataUtils;

public class CalmacRegistrationPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
	String loginText = "//span[text()='Hi Muhammad']";
	String regVeri = "//*[@id='confirmation-dialog']/div[2]/edea-confirmation-dialog/div/div/div[2]/div[1]";
	String haveAccountLink = "//ion-button[contains(@data-test-automation, 'iHaveAnAccount')]";
	String createAccountLink = "//a[text()='Create account']";
	String titleField = "//input[@placeholder='Title type']";
	String selectTitle = "//div[@data-test-automation='auth/register.edea-select-option.undefined.option.MR']";
	String firstName = "//ion-input[@formcontrolname='firstName']/input";
	String lastName = "//ion-input[@formcontrolname='lastName']/input";
	String gender = "//ion-radio[contains(@slot, 'start')]";
	String day = "//input[contains(@placeholder, 'DD')]";
	String month = "//input[contains(@placeholder, 'MM')]";
	String year = "//input[contains(@placeholder, 'YYYY')]";
	String searchCountry = "//input[contains(@placeholder, 'Country')]";
	String selectCountry = "//div[contains(text(), 'Italy')]";
	String postCode = "//edea-select[@dataauto='postCode']/descendant::input[@data-test-automation]";
	String addressLine1 = "//ion-input[@formcontrolname='addressLineOne']/input";
	String city = "//ion-input[@formcontrolname='city']/input";
	String openMobilePrefix = "//input[@placeholder='Prefix']";
	String selectMobileCode = "//div[contains(text(), 'United Kingdom')]";
	String mobileNo = "//input[@type='tel']";
	String email = "//input[@type='email']";
	String confirmEmail = "//ion-input[contains(@formcontrolname, 'email_c')]/input";
	String password = "//input[contains(@type, 'password')]";
	String confirmPassword = "//ion-input[contains(@formcontrolname, 'password_c')]/input";
	String registerButton = "//ion-button[contains(@data-test-automation, 'auth/register.register-page..button.register')]";

	/**
	 * placeTitlePortOrder method is used to Place Order for Pantheon. This method
	 * calls the "post" method from the RESTAssured class. It takes three
	 * parameters: URL, XML and API headers. The provided parameters are then
	 * processed by the post method and the response is returned. From response
	 * extracting the Loan Number and storing in a String
	 * 
	 * @param URI
	 * @param xml
	 * @param headers
	 */
	public CalmacRegistrationPage PlaceOrder(String URI, Object xml, HashMap headers) {
		CalmacTestValues.response = restAssured.postPantheon(URI, xml, headers);
		return this;
	}

	/**
	 * Click on the Login Button
	 */
	public CalmacRegistrationPage clickLoginIn() {
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'auth/login.login-page..button.signIn')]");
//        BP.click("//ion-button[contains(@data-test-automation, 'auth/login.login-page..button.signIn')]");
		return this;
	}

	/**
	 * Enter the username in the user Field
	 */
	public CalmacRegistrationPage setUserName(String userName) {
		BP.setValue(userName, "//ion-input[contains(@formcontrolname, 'username')]/input");
		return this;
	}

	/**
	 * Enter the Password in the password Field
	 */
	public CalmacRegistrationPage setUserPassword(String password) {
		BP.setValue(password, "//ion-input[contains(@formcontrolname, 'password')]/input");
		return this;
	}

	public CalmacRegistrationPage loginRealEC(String username, String password) {
		setUserName(username).setUserPassword(password).clickLoginIn();
		return this;
	}

	/**
	 * Click at app link
	 */
	public CalmacRegistrationPage clickAppsButton() {
		BP.click("a", "id", "MainContent_lbApps");
		return this;
	}

	/**
	 * Click at Activity Link
	 */
	public CalmacRegistrationPage clickActivityButton() {
		BP.click("//a[contains(@id, 'MainContent_lbActivity')]");
		return this;
	}

	/**
	 * Click at Activity Link
	 */
	public CalmacRegistrationPage clickSearchyButton() {
		BP.click("//a[contains(@id, 'MainContent_ModActivity_LinkButton')]/span[text()='Search']");
		return this;
	}

	/**
	 * Click at HTTP request button
	 */
	public CalmacRegistrationPage clickHttpPost() {
		BP.click("a", "id", "MainContent_ModApplication_LinkButton7");
		return this;
	}

	/**
	 * setting post data and getting transaction id from response using starting and
	 * ending index.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	public CalmacRegistrationPage setPostData(String xml, String url) {
		copyToClipboardAndPaste(xml);
		BP.setValue(url, "input", "id", "MainContent_ModApplication_AppHttpPost_UrlText");
		BP.click("//span[text()='Post']/ancestor::a");
		String attr = BP.getAttributeValue("value", "textarea", "id",
				"MainContent_ModApplication_AppHttpPost_MessageText");
		int startIndex = attr.indexOf("<TransactionID>");
		int endIndex = attr.indexOf("</TransactionID>");
		CalmacTestValues.transactionId = attr.substring(startIndex + 15, endIndex);
		System.out.println(CalmacTestValues.transactionId);
		return this;
	}

	/**
	 * setting post data and getting transaction id from response using starting and
	 * ending index.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	public CalmacRegistrationPage verifyOrder(String code) {
		BP.setValue(CalmacTestValues.transactionId, "//input[contains(@id, 'MainContent_ModActivity_txtWorkFlowID')]");
		clickSearchIcon(code);
		Assert.assertTrue(
				BP.isDisplayed("//td[contains(text(), '" + code + "')]/following-sibling::td[text()='Completed']"));
		return this;
	}

	/**
	 * setting post data and getting transaction id from response using starting and
	 * ending index.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	public CalmacRegistrationPage searchWorkflowID(String id) {
		BP.setValue(CalmacTestValues.transactionId, "//input[contains(@id, 'MainContent_ModActivity_txtWorkFlowID')]");
		BP.click(BP.getMaxWaitTimeOut(),
				"//td[contains(text(), 'Work Flow ID:')]/parent::tr/following-sibling::tr/descendant::a");
		return this;
	}

	/**
	 * This method is used to copy data to clipboard and then paste
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	public CalmacRegistrationPage copyToClipboardAndPaste(String xml) {
		StringSelection data = new StringSelection(xml);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(data, null);
		BP.click("textarea", "id", "MainContent_ModApplication_AppHttpPost_MessageText");
		Actions act = new Actions(BP.getDriver());
		act.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
		return this;
	}

	/**
	 * search method is recursive and will continue click on search button until
	 * find required result
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	public CalmacRegistrationPage clickSearchIcon(String comment) {
		for (int a = 0; a < 20; a++) {
			List<WebElement> list = BP.getElementsWithOutWait(
					"//td[contains(text(), '" + comment + "')]/following-sibling::td[text()='Completed']");
			if (list.size() == 0) {
				BP.mediumWait();
				BP.click(BP.getMaxWaitTimeOut(),
						"//td[contains(text(), 'Work Flow ID:')]/parent::tr/following-sibling::tr/descendant::a");
			} else
				break;
		}

		return this;
	}

	public CalmacRegistrationPage verifyComment() {
		clickSearchIcon(CalmacTestValues.PANTHEON_COMMENT_STATUS_CODE);
		Assert.assertTrue(BP.isDisplayed("//td[contains(text(), '" + CalmacTestValues.PANTHEON_COMMENT_STATUS_CODE
				+ "')]/following-sibling::td[text()='Completed']"));
		return this;
	}

	public CalmacRegistrationPage verifyDocument() {
		clickSearchIcon(CalmacTestValues.PANTHEON_DOCUMENT_STATUS_CODE);
		Assert.assertTrue(BP.isDisplayed("//td[contains(text(), '" + CalmacTestValues.PANTHEON_DOCUMENT_STATUS_CODE
				+ "')]/following-sibling::td[text()='Completed']"));
		return this;
	}

	public CalmacRegistrationPage verifyCancelled() {
		clickSearchIcon(CalmacTestValues.PANTHEON_CANCELLED_ORDER_STATUS_CODE);
		Assert.assertTrue(
				BP.isDisplayed("//td[contains(text(), '" + CalmacTestValues.PANTHEON_CANCELLED_ORDER_STATUS_CODE
						+ "')]/following-sibling::td[text()='Completed']"));
		return this;
	}

	/**
	 * placeTitlePortOrder method is used to Place Order for titlePort. This method
	 * calls the "post" method from the RESTAssured class. It takes three
	 * parameters: URL, XML and API headers. The provided parameters are then
	 * processed by the post method and the response is returned. From response
	 * extracting the Loan Number and storing in a String
	 * 
	 * @param URI
	 * @param xml
	 * @param headers
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public CalmacRegistrationPage placeUSBankOrder(String URI, Object xml, HashMap headers) throws IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, InterruptedException {
		CalmacTestValues.response = restAssured.post(URI, xml, headers);
//    	CalmacTestValues.OrderloanNumber =CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.LoanNumber");
		CalmacTestValues.docID = CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.DocId");
		CalmacTestValues.folderID = CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.FolderId");
		return this;
	}

	public CalmacRegistrationPage loginB2C(String username, String password) throws InterruptedException {

		setUserName(username).setUserPassword(password).clickLoginIn();

		return this;
	}

	/**
	 * This method is used to click on new tab to open the newly created orders
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CalmacRegistrationPage clickCreateAccountLink() throws InterruptedException {
		Thread.sleep(5000);

		BP.javaScriptClick(createAccountLink);
		Thread.sleep(5000);
		return this;
	}

	/**
	 * This method is used to click on new tab to open the newly created orders
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CalmacRegistrationPage enterAccountDetail() throws InterruptedException {
		BP.setValue("MR", titleField);
		BP.click(selectTitle);
		BP.setValue("Muhammad", firstName);
		BP.javaScriptClick(gender);
		BP.setValue("Iftikhar", lastName);
		BP.setValue("01", day);
		BP.setValue("04", month);
		BP.setValue("1987", year);
		BP.setValue("Italy", searchCountry);
		BP.click(selectCountry);
		BP.setValue("54000", postCode);
		BP.setValue("House 10", addressLine1);
		BP.setValue("Lahore", city);
		BP.javaScriptClick(openMobilePrefix);
		BP.click(selectMobileCode);
		BP.setValue("765765765", mobileNo);
		String randomEmail = MockDataUtils.generateRandomEmailAddress();
		BP.setValue(randomEmail, email);
		BP.setValue(randomEmail, confirmEmail);
		BP.setValue("!edeaUser1", password);
		BP.setValue("!edeaUser1", confirmPassword);
		BP.javaScriptClick(registerButton);
		Thread.sleep(30000);

		return this;
	}

	/**
	 * This method is used to click on new tab to open the newly created orders
	 * 
	 * @return
	 */
	public CalmacRegistrationPage haveAccount() {
		BP.click(haveAccountLink);
		return this;
	}

	/**
	 * This method is used to click on new tab to open the newly created orders
	 * 
	 * @return
	 */
	public CalmacRegistrationPage clickNew() {
		BP.click("tr", "id", "tourDashAnalyticsOrdersRowNewTR");
		return this;
	}

	public CalmacRegistrationPage searchLoanNumber(String password) {
		BP.setValue(password, "input", "id", "ordersSearchTextBox");
		return this;
	}

	/**
	 * This method is used find the newly created LoanNumber in title port It waits
	 * until find the newly created Loan Number
	 * 
	 * @return
	 */
	public CalmacRegistrationPage clickSearchIcon() {
		BP.click(BP.getMaxWaitTimeOut(), "i", "class", "icon-search");
		for (int a = 0; a < 30; a++) {
			List<WebElement> list = BP.getElementsWithOutWait(loginText);
			if (list.size() > 1 || list.size() == 0) {
				BP.mediumWait();
				BP.click(BP.getMaxWaitTimeOut(), "i", "class", "icon-search");
			} else
				break;
		}

		return this;
	}

	/**
	 * @param expectedLoanNumber :Getting loan number from the API response
	 * @return This method is used to validate the Loan Number from API response
	 *         with title port UI
	 */
	public CalmacRegistrationPage assertLogin(String expectedLoanNumber) {
		Assert.assertEquals(BP.getElement(loginText).getText(), expectedLoanNumber);

//    	BP.openNewTabWithURL("https://www.eclipse.org/setups/sponsor/?scope=Eclipse%20IDE%20for%20Java%20Developers%20(includes%20Incubating%20components)&version=4.24.0.20220609-1200&campaign=2023-12");
		return this;
	}

	public CalmacRegistrationPage assertRegister(String expectedLoanNumber) {
		Assert.assertTrue(BP.getElement(regVeri).isDisplayed());
		return this;
	}

}
