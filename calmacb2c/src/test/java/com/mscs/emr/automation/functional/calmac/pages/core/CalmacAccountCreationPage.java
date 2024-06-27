package com.mscs.emr.automation.functional.calmac.pages.core;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.ExcelReader;
import com.mscs.emr.automation.utils.OracleDbUtils;

public class CalmacAccountCreationPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
	String currentEmail;
	String updatedEmail;
	String homeLink = "//ion-item[contains(@data-test-automation, 'item.lbl.tickets')]";
	String loginText = "//span[text()='Hi Muhammad']";
	String regVeri = "//*[@id='confirmation-dialog']/div[2]/edea-confirmation-dialog/div/div/div[2]/div[1]";
	String haveAccountLink = "//ion-button[contains(@data-test-automation, 'iHaveAnAccount')]";
	String forgotPasswordLink = "//a[contains(@data-test-automation, 'forgotYourPassword')]";
	String forgotPasswordMessage = "//span[contains(text(), 'The Password Reset email was sent successfully')]";
	String continueAsGuestButton = "//ion-button[contains(@data-test-automation, 'continueAsGuest')]";
	String createAccountLink = "//a[text()='Create account']";
	String titleField = "//input[@placeholder='Title type']";
	String selectTitle = "//div[@data-test-automation='auth/register.edea-select-option.undefined.option']";
	String firstName = "//ion-input[@formcontrolname='firstName']/input";
	String lastName = "//ion-input[@formcontrolname='lastName']/input";
	String gender = "//ion-radio[contains(@slot, 'start')]";
	String day = "//input[contains(@placeholder, 'DD')]";
	String month = "//input[contains(@placeholder, 'MM')]";
	String year = "//input[contains(@placeholder, 'YYYY')]";
	String searchCountry = "//input[contains(@placeholder, 'Country')]";
	String selectCountry = "//div[contains(text(), 'Italy')]";
	String searchNationality = "//input[contains(@placeholder, 'Nationality')]";
	String selectNationality = "//div[contains(text(), 'Italy')]";
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
	
	String continueAsGuest = "//ion-button[contains(@data-test-automation, 'continueAsGuest')]";

	String haveAccount = "//ion-button[contains(@data-test-automation, 'iHaveAnAccount')]";
	String usernameFieldForLogin = "//ion-input[contains(@formcontrolname, 'username')]/input";
	String passwordFieldForLogin = "//ion-input[contains(@formcontrolname, 'password')]/input";
	String signInButton = "//ion-button[contains(@data-test-automation, 'button.signIn')]";
	String forgotPassword = "//a[contains(text(), 'Forgot your password')]";
	String loginToYourAccout = "//a[contains(text(), 'Login into your account')]";
	String relativePath = Config.getResourcesFolderPath();
	String xmlTestDataFileName = Config.getExcelFineNameToReadXMLTestData();
	ExcelReader readCellForXML = new ExcelReader(relativePath + "/" + xmlTestDataFileName);

	public CalmacAccountCreationPage enterAccountDetail(int rowId, String Sheetname) throws InterruptedException {
		BP.setValue(CalmacTestValues.getAccountTitle(rowId, Sheetname), titleField);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getAccountTitle(rowId, Sheetname) + "')]");
		BP.setValue(CalmacTestValues.getFirstName(rowId, Sheetname), firstName);

		BP.clickTab(firstName);
		BP.setValue(CalmacTestValues.getLastName(rowId, Sheetname), lastName);
		BP.shortWait();
		BP.javaScriptClick("//*[text()='" + CalmacTestValues.getGender(rowId, Sheetname) + "']/following-sibling::*");
		BP.shortWait();
		BP.clickTab(gender);
		BP.javaScriptScrollToView(searchNationality);
		BP.setValue(CalmacTestValues.getNationality(rowId, Sheetname), searchNationality);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getNationality(rowId, Sheetname) + "')]");
		BP.javaScriptScrollToView(day);
		BP.setValue(CalmacTestValues.getDay(rowId, Sheetname), day);
		BP.setValue(CalmacTestValues.getMonth(rowId, Sheetname), month);
		BP.setValue(CalmacTestValues.getYear(rowId, Sheetname), year);
		BP.shortWait();
		BP.click("//ion-radio-group[@formcontrolname='concessionCard']/descendant::*[text()='"
				+ CalmacTestValues.getConcessionCard(rowId, Sheetname) + "']/following-sibling::*");
		BP.shortWait();
		if ("Yes".equals(CalmacTestValues.getConcessionCard(rowId, Sheetname))) {
			BP.javaScriptClick("//*[contains(@dataauto, 'typeOfConcession')]");
			BP.setValue(CalmacTestValues.getTypeOfConcession(rowId, Sheetname),
					"//*[contains(@dataauto, 'typeOfConcession')]/descendant::input[contains(@id, 'search-input')]");
			BP.javaScriptClick(
					"//*[contains(text(), '" + CalmacTestValues.getTypeOfConcession(rowId, Sheetname) + "')]");
			BP.shortWait();
			BP.javaScriptClick("//*[contains(@formcontrolname, 'concessionCardNumber')]");
			BP.setValue(CalmacTestValues.getConcessionCardNumber(rowId, Sheetname),
					"//*[contains(@formcontrolname, 'concessionCardNumber')]/input");
		}
		BP.shortWait();
		BP.javaScriptScrollToView("//ion-radio-group[contains(@formcontrolname, 'blueBadge')]/descendant::*[text()='"
				+ CalmacTestValues.getBlueBadge(rowId, Sheetname) + "']/following-sibling::*");
		BP.click("//ion-radio-group[contains(@formcontrolname, 'blueBadge')]/descendant::*[text()='"
				+ CalmacTestValues.getBlueBadge(rowId, Sheetname) + "']/following-sibling::*");
		if ("Yes".equals(CalmacTestValues.getBlueBadge(rowId, Sheetname))) {
			BP.shortWait();
			BP.setValue(CalmacTestValues.getBlueBadgeNumber(rowId, Sheetname),
					"//*[contains(@formcontrolname, 'blueBadgeNumber')]/input");
		}
		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
				+ CalmacTestValues.getAssistance(rowId, Sheetname) + "']/following-sibling::*");

		if ("Yes".equals(CalmacTestValues.getAssistance(rowId, Sheetname))) {

			BP.javaScriptClick("//*[contains(@dataauto, 'specialCare')]");

			BP.setValue(CalmacTestValues.getAssistanceType(rowId, Sheetname),
					"//*[contains(@dataauto, 'specialCare')]/descendant::input[contains(@id, 'search-input')]");

			BP.javaScriptClick("//*[contains(text(), '" + CalmacTestValues.getAssistanceType(rowId, Sheetname) + "')]");

		}
		BP.javaScriptScrollToView(searchCountry);

		BP.setValue(CalmacTestValues.getCountry(rowId, Sheetname), searchCountry);
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCountry(rowId, Sheetname) + "')]");
		BP.setValue(CalmacTestValues.getPostalCode(rowId, Sheetname), postCode);
		BP.mediumWait();

		List<WebElement> list = BP.getDriver()
				.findElements(By.xpath("(//div[contains(@class, 'edea-option-label')])[1]"));
		int size = list.size();
//		boolean bol=BP.isDisplayed("(//div[contains(@class, 'edea-option-label')])[1]");
//		boolean bol=BP.getDriver().findElement(By.xpath("(//div[contains(@class, 'edea-option-label')])[1]")).isDisplayed();
		if (size == 0) {
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADDRESS_LINE1", rowId), addressLine1);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "TOWN", rowId), city);

		} else {
			BP.click("(//div[contains(@class, 'edea-option-label')])[1]");
		}
		BP.javaScriptScrollToView(openMobilePrefix);
		BP.javaScriptClick(openMobilePrefix);
		BP.click("//div[contains(text(), '" + CalmacTestValues.getMobilePrefix(rowId, Sheetname) + "')]");
		BP.shortWait();
		BP.setValue(CalmacTestValues.getMobile(rowId, Sheetname), mobileNo);

		currentEmail = CalmacTestValues.readCellForXML.getCellData("CALMAC_REGISTRATION", "EMAIL", rowId);
		System.out.println("Current Email " + currentEmail);

		String emailCount = CalmacTestValues.readCellForXML.getCellData("CALMAC_REGISTRATION", "EMAIL_COUNT", 2);
		System.out.println("Email Count " + emailCount);

		float floatValue = Float.parseFloat(emailCount);
		int wholeNumber = (int) floatValue;

		int plusIndex = currentEmail.indexOf("+");
		int atIndex = currentEmail.indexOf("@");
		String numericalPart = currentEmail.substring(plusIndex + 1, atIndex);
		int newNumericalValue = Integer.parseInt(numericalPart) + wholeNumber;
		String newNumericalPart = String.valueOf(newNumericalValue);
		updatedEmail = currentEmail.replace("+" + numericalPart, "+" + newNumericalPart);
		System.out.println("New Vaue is " + newNumericalPart);
		CalmacTestValues.readCellForXML.setCellData("CALMAC_REGISTRATION", "EMAIL_COUNT", 2,
				String.valueOf(newNumericalValue));
		CalmacTestValues.readCellForXML.setCellData("CALMAC_REGISTRATION", "EMAIL_COUNT", rowId, newNumericalPart);
//		CalmacTestValues.readCellForXML.setCellData(Sheetname, "EMAIL_COUNT", rowId, newNumericalPart);

		BP.setValue(updatedEmail, email);
		BP.setValue(updatedEmail, confirmEmail);

		BP.setValue(CalmacTestValues.accountPassword, password);
		BP.setValue(CalmacTestValues.accountPassword, confirmPassword);
		BP.javaScriptClick(registerButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed("//div[contains(text(), 'New account created successfully')]"));
		CalmacTestValues.readCellForXML.setCellData("UPDATE_ACCOUNT", "EMAIL", rowId, updatedEmail);
		CalmacTestValues.readCellForXML.setCellData("VEHICLE_DETAIL", "EMAIL", rowId, updatedEmail);
		CalmacTestValues.readCellForXML.setCellData("BOOKING_DETAIL", "EMAIL", rowId, updatedEmail);
		CalmacTestValues.readCellForXML.setCellData("CALMAC_REGISTER_BOOKING", "EMAIL", rowId, updatedEmail);

		return this;
	}

	public CalmacAccountCreationPage setEmailInBookingDetail(int rowId, String Sheetname) throws InterruptedException {
//		CalmacTestValues.readCellForXML.setCellData("BOOKING_DETAIL", "EMAIL", rowId, updatedEmail);
		CalmacTestValues.readCellForXML.setCellData(Sheetname, "EMAIL", rowId, updatedEmail);

		BP.shortWait();

		return this;
	}

	public CalmacAccountCreationPage activateAccount() throws InterruptedException {
		BP.openNewTabWithURL("https://mail.google.com/");
		Thread.sleep(7000);
		BP.switchToChildWindowByIndex(1);
		BP.setValue("automation_test@e-dea.it", "//input[@id='identifierId']");
		BP.click("//span[text()='Next']");
		BP.setValue("!Edeapass1", "//input[@name='Passwd']");
		BP.click("//span[text()='Next']");
		BP.mediumWait();
		BP.javaScriptClick("//tbody/tr/descendant::span[contains(text(), 'Action Required')]");
		BP.shortWait();
		if (BP.isDisplayed("//strong[contains(text(), 'Activate')]")) {
			BP.click("//strong[contains(text(), 'Activate')]");
		} else {
			BP.javaScriptClick("//div[contains(@data-tooltip, 'Show trimmed content')]");
			BP.click("//strong[contains(text(), 'Activate')]");
		}
		BP.shortWait();
		BP.switchToChildWindowByIndex(2);
		BP.click("//a[contains(text(), 'Click here to proceed')]");
		Assert.assertTrue(BP.isDisplayed("//h1[contains(text(), 'Your account has been updated.')]"));
		BP.shortWait();
		Thread.sleep(5000);

		BP.javaScriptClick(loginToYourAccout);

		return this;
	}

	public CalmacAccountCreationPage addresLookup() throws InterruptedException {
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
		Thread.sleep(10000);

		return this;
	}

	/**
	 * This method is used to click on new tab to open the newly created orders
	 * 
	 * @return
	 */
	public CalmacAccountCreationPage haveAccount() {
		BP.click(haveAccountLink);
		return this;
	}

	public CalmacAccountCreationPage findAvailableDateForSailing() throws InterruptedException, AWTException,
			IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		OracleDbUtils oracleDbUtils = new OracleDbUtils();
		oracleDbUtils.executeCalmacQuery("SELECT PRODUCTAVAILABILITY  FROM TESTCFL.IBE_PRODUCTAVAILABILITY \r\n"
				+ "WHERE SAILINGCODE ='035001202403021030'AND PRODUCTCODE ='ADL'\r\n"
				+ "AND ENDDATE ='99991231000000'\r\n" + "");
		Connection conn = null;
		Statement stmt = null;
//        LocalDateTime now = null;
		stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT PRODUCTAVAILABILITY  FROM TESTCFL.IBE_PRODUCTAVAILABILITY \r\n"
				+ "WHERE SAILINGCODE ='035001202403021030'AND PRODUCTCODE ='ADL'\r\n"
				+ "AND ENDDATE ='99991231000000'\r\n" + "");
		System.out.println(resultSet);

		return this;

	}

	public CalmacAccountCreationPage clickCreateAccountLink() throws InterruptedException {
		Thread.sleep(10000);
//		String currentEmail = CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId);
//		CalmacTestValues.readCellForXML.setCellData(Sheetname, "EMAIL", rowId, "iftikhar@gmail.com");
//		CalmacTestValues.readCellForXML.setCellData("UPDATE_ACCOUNT", "EMAIL", rowId, "iftikhar@gmail.com");
//		String emailCount = CalmacTestValues.readCellForXML.getCellData("CALMAC_REGISTRATION", "EMAIL_COUNT", 2);

		BP.javaScriptClick(createAccountLink);
		Thread.sleep(5000);
		return this;
	}

	public CalmacTicketsCreationPage loginCalmac(int rowId) throws InterruptedException {
		Thread.sleep(15000);
		BP.isDisplayed(haveAccount);
		Assert.assertTrue(BP.isDisplayed(haveAccount));
		Assert.assertTrue(BP.isDisplayed(createAccountLink));
		Assert.assertTrue(BP.isDisplayed(continueAsGuestButton));
		BP.javaScriptClick(haveAccount);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(forgotPasswordLink));
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "EMAIL", rowId),
				usernameFieldForLogin);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "PASSWORD", rowId),
				passwordFieldForLogin);
		BP.javaScriptClick(signInButton);
		Thread.sleep(5000);
		return new CalmacTicketsCreationPage();
	}

	public CalmacTicketsCreationPage resetPassword(int rowId) throws InterruptedException {
		Thread.sleep(10000);
		BP.javaScriptClick(haveAccount);
		BP.shortWait();
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "EMAIL", rowId),
				usernameFieldForLogin);
		BP.javaScriptClick(forgotPasswordLink);
//		Assert.assertTrue(BP.isDisplayed(forgotPasswordMessage));
		BP.mediumWait();

		BP.openNewTabWithURL("https://mail.google.com/");
		Thread.sleep(7000);
		BP.switchToChildWindowByIndex(1);
		BP.setValue("automation_test@e-dea.it", "//input[@id='identifierId']");
		BP.click("//span[text()='Next']");
		BP.setValue("!Edeapass1", "//input[@name='Passwd']");
		BP.click("//span[text()='Next']");
		BP.mediumWait();
		BP.javaScriptClick("//tbody/tr/descendant::span[contains(text(), 'Action Required')]");
		BP.mediumWait();
		
		List<WebElement> list = BP.getDriver()
				.findElements(By.xpath("//strong[contains(text(), 'Reset Password')]"));
		int size = list.size();
		if (size>=1) {
			BP.click("//strong[contains(text(), 'Reset Password')]");
		} else {
			BP.javaScriptClick("//div[contains(@data-tooltip, 'Show trimmed content')]");
			BP.shortWait();
			BP.click("//strong[contains(text(), 'Reset password')]");
		}
		BP.shortWait();
		BP.switchToChildWindowByIndex(2);
		BP.click("//a[contains(text(), 'Click here to proceed')]");
		Assert.assertTrue(BP.isDisplayed("//h1[contains(text(), 'Your account has been updated.')]"));

		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "PASSWORD", rowId),
				passwordFieldForLogin);
		BP.javaScriptClick(signInButton);
		Thread.sleep(5000);
		return new CalmacTicketsCreationPage();
	}

	public CalmacTicketsCreationPage loginNewlyCreatedAccount(int rowId, String Sheetname) throws InterruptedException {
		BP.javaScriptClick(haveAccount);
//		BP.setValue(currentEmail,  usernameFieldForLogin);
//		BP.setValue(updatedEmail,  usernameFieldForLogin);
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId), usernameFieldForLogin);
		BP.setValue(updatedEmail, usernameFieldForLogin);

		
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSWORD", rowId), passwordFieldForLogin);
		BP.javaScriptClick(signInButton);
		Thread.sleep(5000);
		return new CalmacTicketsCreationPage();
	}

	public CalmacTicketsCreationPage navigateToHomePage() throws InterruptedException {
//		BP.javaScriptClick(haveAccount);
//		BP.setValue(currentEmail,  usernameFieldForLogin);
//		BP.setValue(updatedEmail,  usernameFieldForLogin);
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId), usernameFieldForLogin);
//		BP.setValue(updatedEmail, usernameFieldForLogin);
//
//		
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSWORD", rowId), passwordFieldForLogin);
		BP.javaScriptClick(homeLink);
		Thread.sleep(5000);
		return new CalmacTicketsCreationPage();
	}

	public CalmacAccountCreationPage verifyTicketsType(int rowId) throws InterruptedException {
		BP.isDisplayed(haveAccount);
		Assert.assertTrue(BP.isDisplayed(haveAccount));
		Assert.assertTrue(BP.isDisplayed(createAccountLink));
		Assert.assertTrue(BP.isDisplayed(continueAsGuestButton));
		BP.javaScriptClick(haveAccount);
		Assert.assertTrue(BP.isDisplayed(forgotPasswordLink));
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "EMAIL", rowId),
				usernameFieldForLogin);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "PASSWORD", rowId),
				passwordFieldForLogin);
		BP.javaScriptClick(signInButton);
		Thread.sleep(5000);
		return this;
	}

	public MyAccountPage loginB2B() throws InterruptedException {
		Thread.sleep(5000);
		BP.javaScriptClick(haveAccount);
		Thread.sleep(2000);
		BP.setValue("automation_test+276@e-dea.it", usernameFieldForLogin);
		BP.setValue("Testing12345!", passwordFieldForLogin);
		BP.javaScriptClick(signInButton);
		Thread.sleep(5000);
		return new MyAccountPage();
	}
	public MyAccountPage guestLink() throws InterruptedException {
		Thread.sleep(30000);
		BP.javaScriptClick(continueAsGuest);
	
		return new MyAccountPage();
	}

	
}
