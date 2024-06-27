package com.mscs.emr.automation.functional.calmac.pages.core;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;

public class BookingPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
//	String oneWayLink = "//*[contains(text(), 'One way')]";
	String oneWayLink = "(//div[contains(@class, 'trip-type-content dark-color')]/div)[1]";
	String multiIslandLink = "//*[contains(text(), 'Multi-island')]";

	String guestLink = "//*[@id='main-content']/app-welcoming/ion-content/div/edea-steps-sides/div/div[1]/div/div/div/div/div[3]/ion-button[2]";

	String from = "//input[contains(@placeholder, 'From')]";
	String to = "//input[contains(@placeholder, 'To')]";
	String loginText = "//span[text()='Hi Muhammad']";
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

	public BookingPage oneWay() {
		BP.click(BP.getLongWaitTime(), oneWayLink);
		return this;
	}

	public BookingPage continueAsGuest() {
		BP.click(BP.getLongWaitTime(), guestLink);
		return this;
	}

	public BookingPage multiIsland() {
		BP.javaScriptClick(multiIslandLink);
		return this;
	}

	public BookingPage selectDeparturePort() {
		BP.click(BP.getLongWaitTime(), from);
		BP.setValue(CalmacTestValues.b2cDeparturePort, from);
		BP.click("//div[contains(text(), '" + CalmacTestValues.b2cDeparturePort + "')]");
		return this;
	}

	public BookingPage selectArrivalPort() {
		BP.click(BP.getLongWaitTime(), to);
		BP.setValue(CalmacTestValues.b2cArrivalPort, to);
		BP.click("//div[contains(text(), '" + CalmacTestValues.b2cArrivalPort + "')]");
		return this;
	}

	public BookingPage addPassenger() {
		BP.shortWait();
		BP.javaScriptClick("//div[contains(@class, 'step2-choose-product')]");
		BP.shortWait();
		BP.click("//ion-button[contains(@data-test-automation, 'desktop/step2.edea-counter.undefined.button.add')]");
		BP.click(
				"//ion-button[contains(@class, 'ion-color-secondary md button button-solid ion-activatable ion-focusable')]");
		return this;
	}

	public BookingPage confirmAddPassenger() {

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button");
		return this;
	}

	public BookingPage continueButton() {
		BP.shortWait();
		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step1/edea-steps-sides/div/div[1]/div/div/div/app-multistep-destinations/div/ion-button[2]");
		BP.shortWait();
		BP.click(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step1/edea-steps-sides/div/div[1]/div/div/div/app-multistep-review-route/div/ion-button[2]");

		return this;
	}

	public BookingPage getBookingRef() {
		BP.shortWait();
		CalmacTestValues.bookingReference = BP.getText("//div[contains(text(), ' Booking number: ')]/span");
		return this;
	}

	public BookingPage openMyJourney() {
		BP.shortWait();
		BP.click("//ion-item[contains(@data-test-automation, '.edea-side-menu..item.lbl.myJourneys')]");
		return this;
	}

	public BookingPage viewTicket() {
		BP.shortWait();
		BP.click(
				"//ion-button[contains(@data-test-automation, 'home/my-journeys.edea-journey-reference.undefined.button.viewTicket')]");
		BP.longWait();
		return this;
	}

	public BookingPage modifyBooking() {
		BP.shortWait();
		BP.click(
				"//*[@id='main-content']/app-journey-details/ion-content/div/div/div[2]/div[2]/edea-journey-reference/div/div[2]/ion-button[2]");
		return this;
	}

	public BookingPage selectSailing(Boolean isMultiTrue) {
		BP.shortWait();
//        List <WebElement> list =   BP.getDriver().findElements(By.xpath("//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button"));
		if (isMultiTrue == false) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button");
			BP.shortWait();

		}
		BP.javaScriptClick("//div[contains(text(), 'January 2024')]/parent::div/descendant::div[text()="
				+ CalmacTestValues.b2cSailingDate + "]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/app-step3-calendar/div/div/ion-button");
		BP.mediumWait();
		BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cSailingTime
				+ "')]/ancestor::div[contains(@class, 'edea-sailing-card')]/descendant::span[contains(text(), 'Select')]");
		BP.shortWait();

		BP.javaScriptClick("//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[2]/ion-button");

		BP.mediumWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/step3-route-review/div/div[2]/ion-button");
		BP.shortWait();
		
		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step3-multitrip-summary/edea-steps-sides/div/div[1]/div/div/div/div/div[2]/div[1]");
			BP.shortWait();

		}

		BP.javaScriptClick("//div[contains(@class, 'step2-choose-product')]");
		BP.shortWait();
//        BP.setValue("Muhammad", firstName);
//        BP.javaScriptClick(gender);
//
//        BP.setValue("Iftikhar", lastName);
//        BP.setValue("PA191NR", postCode);
//        BP.setValue("House 10", addressLine1);
//        BP.setValue("Lahore", city);
//        BP.javaScriptClick(openMobilePrefix);
//        BP.click(selectMobileCode);
//        BP.setValue("765765765", mobileNo);
//        BP.setValue("test@gmail.com", email);
//
		BP.javaScriptClick(
				"//ion-button[contains(@class, 'ion-color ion-color-secondary md button button-solid ion-activatable ion-focusable hydrated')]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step4/edea-steps-sides/div/div[1]/div/div/div/ion-button");
		BP.shortWait();
//Terms and Policy
		BP.shortWait();

		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");
			BP.shortWait();
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[3]/ion-checkbox");

			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[3]/ion-button");

		} else {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");

			BP.shortWait();

			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[3]/ion-button");

		}

		// Make Payment
		BP.getDriver().switchTo().frame("wp-cl-custom-html-iframe");
		BP.setValue("5454545454545454", "//input[@id='cardNumber']");
		BP.setValue("Muhammad", "//input[@id='cardholderName']");
		BP.setValue("12", "//input[@id='expiryMonth']");
		BP.setValue("26", "//input[@id='expiryYear']");
		BP.setValue("123", "//input[@id='securityCode']");
		BP.click("//input[@type='submit']");

		return this;
	}

	public BookingPage selectSailingWithModificatin(Boolean isMultiTrue) {
		BP.shortWait();
//        List <WebElement> list =   BP.getDriver().findElements(By.xpath("//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button"));
		if (isMultiTrue == false) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button");
			BP.shortWait();

		}
		BP.javaScriptClick("//div[contains(text(), 'December 2023')]/parent::div/descendant::div[text()=21]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/app-step3-calendar/div/div/ion-button");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[1]/div/div[3]/div/div[2]/edea-sailing-card/div");
		BP.shortWait();

		BP.javaScriptClick("//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[2]/ion-button");

		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/step3-route-review/div/div[2]/ion-button");
		BP.shortWait();

//        List <WebElement> list1 =   BP.getDriver().findElements(By.xpath("//*[@id='main-content']/app-desktop/ion-content/div/app-step3-multitrip-summary/edea-steps-sides/div/div[1]/div/div/div/div/div[2]/div[1]"));
		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step3-multitrip-summary/edea-steps-sides/div/div[1]/div/div/div/div/div[2]/div[1]");
			BP.shortWait();

		}

		BP.javaScriptClick("//div[contains(@class, 'step2-choose-product')]");
		BP.shortWait();
//        BP.setValue("Muhammad", firstName);
//        BP.javaScriptClick(gender);
//
//        BP.setValue("Iftikhar", lastName);
//        BP.setValue("PA191NR", postCode);
//        BP.setValue("House 10", addressLine1);
//        BP.setValue("Lahore", city);
//        BP.javaScriptClick(openMobilePrefix);
//        BP.click(selectMobileCode);
//        BP.setValue("765765765", mobileNo);
//        BP.setValue("test@gmail.com", email);
//
		BP.javaScriptClick(
				"//ion-button[contains(@class, 'ion-color ion-color-secondary md button button-solid ion-activatable ion-focusable hydrated')]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step4/edea-steps-sides/div/div[1]/div/div/div/ion-button");
		BP.shortWait();
//Terms and Policy
		BP.shortWait();

		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");
			BP.shortWait();
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[3]/ion-checkbox");

			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[3]/ion-button");

		} else {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");

			BP.shortWait();

			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[3]/ion-button");

		}

		return this;
	}

	public BookingPage addressLookup() {
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button");
		BP.shortWait();

		BP.javaScriptClick("//div[contains(text(), 'December 2023')]/parent::div/descendant::div[text()=20]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/app-step3-calendar/div/div/ion-button");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[1]/div/div[3]/div/div[2]/edea-sailing-card/div");
		BP.shortWait();

		BP.javaScriptClick("//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[2]/ion-button");

		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/step3-route-review/div/div[2]/ion-button");
		BP.shortWait();

		BP.javaScriptClick("//div[contains(@class, 'step2-choose-product')]");
		BP.shortWait();
		// ion-button[contains(@class, 'ion-color ion-color-secondary md button
		// button-solid ion-activatable ion-focusable hydrated')]
		BP.setValue("Muhammad", firstName);
		BP.javaScriptClick(gender);

		BP.setValue("Iftikhar", lastName);

		BP.setValue("PA191NR", postCode);
		return this;

	}

	public BookingPage guestUser(Boolean isMultiTrue) {
		BP.shortWait();
//        List <WebElement> list =   BP.getDriver().findElements(By.xpath("//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button"));
		if (isMultiTrue == false) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step2/edea-steps-sides/div/div[1]/div/div/div/ion-button");
			BP.shortWait();

		}
		BP.javaScriptClick("//div[contains(text(), 'December 2023')]/parent::div/descendant::div[text()=20]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/app-step3-calendar/div/div/ion-button");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[1]/div/div[3]/div/div[2]/edea-sailing-card/div");
		BP.shortWait();

		BP.javaScriptClick("//*[@id='multistep-dialog']/div[2]/app-step3-select-sailings/div/div[2]/div[2]/ion-button");

		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step3/edea-steps-sides/div/div[1]/div/div/step3-route-review/div/div[2]/ion-button");
		BP.shortWait();

//        List <WebElement> list1 =   BP.getDriver().findElements(By.xpath("//*[@id='main-content']/app-desktop/ion-content/div/app-step3-multitrip-summary/edea-steps-sides/div/div[1]/div/div/div/div/div[2]/div[1]"));
		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step3-multitrip-summary/edea-steps-sides/div/div[1]/div/div/div/div/div[2]/div[1]");
			BP.shortWait();

		}

		BP.javaScriptClick("//div[contains(@class, 'step2-choose-product')]");
		BP.shortWait();
		BP.setValue("Muhammad", firstName);
		BP.javaScriptClick(gender);
		BP.setValue("Iftikhar", lastName);
		BP.setValue("PA191NR", postCode);
		BP.setValue("House 10", addressLine1);
		BP.setValue("Lahore", city);
		BP.javaScriptClick(openMobilePrefix);
		BP.click(selectMobileCode);
		BP.setValue("765765765", mobileNo);
		BP.setValue("test@gmail.com", email);
		BP.javaScriptClick(
				"//ion-button[contains(@class, 'ion-color ion-color-secondary md button button-solid ion-activatable ion-focusable hydrated')]");
		BP.shortWait();

		BP.javaScriptClick(
				"//*[@id='main-content']/app-desktop/ion-content/div/app-step4/edea-steps-sides/div/div[1]/div/div/div/ion-button");
		BP.shortWait();
//Terms and Policy
		BP.shortWait();

		if (isMultiTrue) {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");
			BP.shortWait();
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[2]/ion-item[3]/ion-checkbox");

			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5-review-multitrip/div/div/div[3]/div[3]/ion-button");

		} else {
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[1]/ion-checkbox");

			BP.shortWait();

			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[2]/ion-item[2]/ion-checkbox");
			// Pay now button
			BP.javaScriptClick(
					"//*[@id='main-content']/app-desktop/ion-content/div/app-step5/div/div/div[3]/div[3]/ion-button");

		}

		// Make Payment
		BP.getDriver().switchTo().frame("wp-cl-custom-html-iframe");
		BP.setValue("5454545454545454", "//input[@id='cardNumber']");
		BP.setValue("Muhammad", "//input[@id='cardholderName']");
		BP.setValue("12", "//input[@id='expiryMonth']");
		BP.setValue("26", "//input[@id='expiryYear']");
		BP.setValue("123", "//input[@id='securityCode']");
		BP.click("//input[@type='submit']");

		return this;
	}
}
