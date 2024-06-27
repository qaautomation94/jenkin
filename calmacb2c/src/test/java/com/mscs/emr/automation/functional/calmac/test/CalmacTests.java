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
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.OracleDbUtils;

public class CalmacTests extends BaseTestPage {
	CalmacPage calmacPage = new CalmacPage();
	BookingPage bookingpage = new BookingPage();

	String transID = CalmacTestValues.transactionId;
	CalmacTestValues calmacTestValues = new CalmacTestValues();

	/**
	 * The purpose of this method is used to Place Chase order through API and
	 * validate in TitlePort
	 * 
	 * @throws IOException
	 */
	// @TestCaseId(1)
	// @Test(groups = { TestGroup.REGRESSION, TestGroup.TITLEPORT }, priority=1,
	// description = "Validating login")
	public void loginB2C() throws Exception {
//        OracleDbUtils.getRandomActiveLocation("t");

//    	calmacPage
//        .placeUSBankOrder(CalmacTestValues.TTITLEPORT_PLACE_ORDER_US_BANK_URL, calmacTestValues.TitlePortPlaceUSBankOrderXML(), calmacTestValues.TitlePortHeaders);
//    	calmacPage.haveAccount()
//    	.loginB2C(CalmacTestValues.usbankuserName, CalmacTestValues.usbankpassword);
//       		.clickNew()
//       		.searchLoanNumber(CalmacTestValues.OrderloanNumber)
//       		.clickSearchIcon()
//       		.assertLoanNumberInTitlePort(CalmacTestValues.OrderloanNumber);
	}

//    @TestCaseId(1)
//    @Test(groups = { TestGroup.REGRESSION, TestGroup.TITLEPORT }, priority=1, description = "Validating login")
	public void verifyLogin() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword)
				.assertLogin(CalmacTestValues.VerifyLogin);
	}

	@TestCaseId(3)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 2, description = "Creation of an account")
	public void createAccount() throws Exception {
		calmacPage.clickCreateAccountLink().enterAccountDetail();

	}

	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 3, description = "Verification of One Way Booking")
	public void createBooking() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(15000);
		bookingpage.oneWay().selectDeparturePort().selectArrivalPort().continueButton()
//    	.addPassenger()
				.selectSailing(false);
		Thread.sleep(15000);
	}

	@TestCaseId(5)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 4, description = "Verification of Multi Islan Booking")
	public void createMultiIslandBooking() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(15000);
		bookingpage.multiIsland()
//    	.addPassenger()
				.confirmAddPassenger().selectDeparturePort().selectArrivalPort().continueButton().selectSailing(true);
		Thread.sleep(15000);
	}

	@TestCaseId(6)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 2, description = "Verification of Address Lookup")
	public void addressLookup() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(15000);
		bookingpage.oneWay().selectDeparturePort().selectArrivalPort().continueButton().addPassenger().addressLookup();
	}

	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 5, description = "Verification of View Booking")
	public void viewBooking() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(15000);
		bookingpage.oneWay().selectDeparturePort().selectArrivalPort().continueButton().selectSailing(false)
				.getBookingRef().openMyJourney().viewTicket();

	}

	@TestCaseId(8)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 6, description = "Verification of Modification of Booking")
	public void modifyBooking() throws Exception {
		calmacPage.haveAccount().loginB2C(CalmacTestValues.calmacb2cusername, CalmacTestValues.calmacb2cpassword);
		Thread.sleep(15000);
		bookingpage.oneWay().selectDeparturePort().selectArrivalPort().continueButton().selectSailing(false)
				.getBookingRef().openMyJourney().viewTicket().modifyBooking().continueButton()
				.selectSailingWithModificatin(false);

	}

	@TestCaseId(9)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 9, description = "Creation of booking with guest user")
	public void bookingWithGuest() throws Exception {
		bookingpage.continueAsGuest().oneWay().selectDeparturePort().selectArrivalPort().continueButton().addPassenger()
				.guestUser(false);
		Thread.sleep(15000);
	}

	/**
	 * The purpose of this method is used to Place order through UI and validate in
	 * Pantheon
	 * 
	 * @throws IOException
	 */
	@TestCaseId(125934)
	// @Test(groups = { TestGroup.REGRESSION, TestGroup.PANTHEON }, priority=3)
	public void PlacePantheonOrder() throws Exception {
//    	getDriver().get(Config.getPantheonPageURL());
//		calmacPage
//		.loginRealEC(CalmacTestValues.pantheonuserName, CalmacTestValues.pantheonpassword)
//		.clickAppsButton()
//		.clickHttpPost()
//		.clickActivityButton()
//		.clickSearchyButton()
//		.verifyOrder(CalmacTestValues.PANTHEON_PLACED_ORDER_STATUS_CODE);
	}

}
