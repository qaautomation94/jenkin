package com.mscs.emr.automation.functional.calmac.test;

import java.io.IOException;
import java.sql.ResultSet;

import org.testng.annotations.Test;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.TestCaseId;
import com.mscs.emr.automation.functional.TestGroup;
import com.mscs.emr.automation.functional.calmac.pages.core.BookingPage;
import com.mscs.emr.automation.functional.calmac.pages.core.CalmacPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartBoardingPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartApplicationDirectSalesPage;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.OracleDbUtils;

/**
 * @author muhammad.iftikhar
 *
 */
public class CalmacSanityTests extends BaseTestPage {
	CalmacPage calmacPage = new CalmacPage();
	BookingPage bookingpage = new BookingPage();
	BaseTestPage BP = new BaseTestPage();
	String transID = CalmacTestValues.transactionId;
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	SmartBoardingPage smartBoardingPage = new SmartBoardingPage();
	SmartApplicationDirectSalesPage smartApplicationDirectSalesPage = new SmartApplicationDirectSalesPage();

	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Validating B2C login")
	public void verifyB2CLogin() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword)
				.assertLogin(CalmacTestValues.VerifyLogin);

	}

	@TestCaseId(2)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 2, description = "Creation of an account")
	public void createB2CAccount() throws Exception {
		calmacPage.clickCreateAccountLink().enterAccountDetail();

	}

	@TestCaseId(3)
//  @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=3, description = "Creation of an account")
	public void addressLookup() throws Exception {
		calmacPage.clickCreateAccountLink().addresLookup();

	}

	/**
	 * This method is used to create a booking from B2C
	 */
	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 4, description = "Creation of One Way Booking at B2C")
	public void createBookingAtB2C() throws Exception {

		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(5000);
		bookingpage.oneWay().selectDeparturePort().selectArrivalPort().continueButton().selectSailing(false);

	}

	/**
	 * This method is used to Open Sailing
	 */
	@TestCaseId(5)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 5, description = "Open Check-In")
	public void loginSmartBoardingAndOpenCheckIn() throws Exception {
		BP.getDriver().get("http://192.168.1.161:7013/SmartBoarding/");
		smartBoardingPage.setUserName(CalmacTestValues.b2cUsername).setPassword(CalmacTestValues.b2cPassword)
				.clickLoginIn().openTradingUnit().selectTradingUnit("Pax").clickLoginIn().openPortOperationsList()
				.selectPortOperations().goButton().startMenuButton().openSailingStatusPage().selectSailing().checkIn();

	}

	/**
	 * This method is used to create a passenger booking using smart application
	 */
	@TestCaseId(6)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 6, description = "Creation of booking using direct Sales and Pax trading Unit")
	public void createBookingUsingDirectSalesAndPaxTradingUnit() throws Exception {
		BP.getDriver().get("https://192.168.1.161:7113/SmartApplications/#/landing-page");
		smartApplicationDirectSalesPage.setUserName(CalmacTestValues.b2cUsername)
				.setPassword(CalmacTestValues.b2cPassword).clickLoginIn().openTradingUnit().selectTradingUnit("Pax")
				.clickLoginIn().goButton().selectDeparturePort().selectArrivalPort().openSailingCalender()
				.addPassenger().addVehicle().searchSailing().selectSailingTime().addPassengerListInfo()
				.getConfirmedBookingReference();
	}

	/**
	 * This method is used to create a freight booking from Smart Application
	 */
	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 7, description = "Creation of booking using direct Sales and Freight trading Unit")
	public void createBookingUsingDirectSalesAndFreightTradingUnit() throws Exception {
		BP.getDriver().get("https://192.168.1.161:7113/SmartApplications/#/landing-page");
		smartApplicationDirectSalesPage.setUserName(CalmacTestValues.b2cUsername)
				.setPassword(CalmacTestValues.b2cPassword).clickLoginIn().openTradingUnit().selectTradingUnit("Fgt")
				.clickLoginIn().goButton().selectDeparturePort().selectArrivalPort().openSailingCalender()
				.addPassenger().addVehicle().searchSailing().selectSailingTime().addPassengerListInfo();
	}

	/**
	 * This method is used to perform check in operation from Smart boarding
	 * application
	 */
	@TestCaseId(8)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 8, description = "Login Smart Boarding and Perform Check n")
	public void loginSmartBoardingnadPerformCheckIn() throws Exception {
		BP.getDriver().get("http://192.168.1.161:7013/SmartBoarding/");
		smartBoardingPage.setUserName(CalmacTestValues.b2cUsername).setPassword(CalmacTestValues.b2cPassword)
				.clickLoginIn().openTradingUnit().selectTradingUnit("Pax").clickLoginIn().openPortOperationsList()
				.selectPortOperations().goButton().selectSailing().selectBoarding("CHECK-IN").startOperationsButton()
				.performCheckIn();
	}

	/**
	 * This method is used to create a booking using port operation
	 */
	@TestCaseId(9)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 9, description = "Creation of booking using Port Office")
	public void createBookingUsingPortOfficeAndPaxTradingUnit() throws Exception {
		BP.getDriver().get("https://192.168.1.161:7113/SmartApplications/#/landing-page");
		smartApplicationDirectSalesPage.setUserName(CalmacTestValues.b2cUsername)
				.setPassword(CalmacTestValues.b2cPassword).clickLoginIn().openTradingUnit().selectTradingUnit("Pax")
				.clickLoginIn().selectDeparturePort().selectPayPoint().goButtonPortOffice().bookingSpeedButton()
				.navigateToPassengerList().addPassengerDetail().retrieveLastBooking();
	}

	/**
	 * This method is used to check in and Embark using Auto mode from Smart
	 * boarding application
	 */
	@TestCaseId(10)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 10, description = "Login Smart Boarding and Perform Check n")
	public void checkInAndEmbarkUsingAutoMode() throws Exception {
		BP.getDriver().get("http://192.168.1.161:7013/SmartBoarding/");
		smartBoardingPage.setUserName(CalmacTestValues.b2cUsername).setPassword(CalmacTestValues.b2cPassword)
				.clickLoginIn().openTradingUnit().selectTradingUnit("Pax").clickLoginIn().openPortOperationsList()
				.selectPortOperations().goButton().startMenuButton().openSailingStatusPage().selectSailing()
				.startOperationsButton().closeCheckIn().startMenuButton().openOperationSetupPage().selectSailing()
				.selectBoarding("CHECK-IN / EMBARK").startOperationsButton().performCheckInAndEmbark().startMenuButton()
				.openSailingStatusPage().selectSailing().startOperationsButton().completeBoarding("2", "2");

	}
}
