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

public class SmartApplicationDirectSalesPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
	String userName = "//input[contains(@id, 'email')]";
	String usersPassword = "//input[contains(@id, 'password')]";
	String departurePort = "//input[contains(@formcontrolname, 'departurePort')]";
	String payPoint = "//input[contains(@formcontrolname, 'paypoint')]";
	String arrivalPort = "//input[contains(@formcontrolname, 'arrivalPortCmb')]";
	String openCalenderIcon = "//button[contains(@aria-label, 'Open calendar')]";
	String selectDay = "//div[contains(text(), '25')]";
	String addPassenger = "//button[contains(@data-test-automation, 'add-product.ADL_1')]";
	String openVehicleType = "//mat-select[contains(@formcontrolname, 'selectVehicle')]";
	String selectVehicle = "//mat-option/SPAN[contains(text(), 'CAR')]";
	String searchButton = "//button/SPAN[contains(text(), 'Search')]";
//	String getPostion ="//*[contains(text(), '16:30')]/ancestor::th/preceding-sibling::th";

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
	public SmartApplicationDirectSalesPage PlaceOrder(String URI, Object xml, HashMap headers) {
		CalmacTestValues.response = restAssured.postPantheon(URI, xml, headers);
		return this;
	}

	/**
	 * Click on the Login Button
	 */
	public SmartApplicationDirectSalesPage clickLoginIn() {
		BP.javaScriptClick("//span[contains(text(), 'Sign in')]");

		return this;
	}

	/**
	 * Enter the username in the user Field
	 */
	public SmartApplicationDirectSalesPage setUserName(String usersName) {
		if (BP.isDisplayed("//button[@id='details-button']")) {

			BP.javaScriptClick("//button[@id='details-button']");
			BP.javaScriptClick("//a[@id='proceed-link']");

		}
		BP.setValue(BP.getMaxWaitTimeOut(), usersName, userName);
		BP.shortWait();
		return this;
	}

	/**
	 * Enter the username in the user Field
	 */
	public SmartApplicationDirectSalesPage setPassword(String password) {
		BP.setValue(BP.getMaxWaitTimeOut(), password, usersPassword);
		return this;
	}

	public SmartApplicationDirectSalesPage openTradingUnit() {
		BP.shortWait();
		BP.javaScriptClick("//div[contains(@id, 'mat-select')]");

		return this;
	}

	public SmartApplicationDirectSalesPage selectTradingUnit(String unit) {
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + unit + "')]");
		return this;
	}

	public SmartApplicationDirectSalesPage openPortOperationsList() {
		BP.shortWait();
		BP.javaScriptClick("//mat-select[contains(@formcontrolname, 'portOperation')]");

		return this;
	}

	public SmartApplicationDirectSalesPage selectPortOperations() {
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cDeparturePort + "')]");

		return this;
	}

	public SmartApplicationDirectSalesPage goButton() {
		BP.shortWait();
		BP.javaScriptClick("//div[contains(@class, 'justify-between')]/descendant::span[contains(text(), 'GO')]");
		return this;
	}

	public SmartApplicationDirectSalesPage goButtonPortOffice() {
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), 'GO')]");
		return this;
	}

	public SmartApplicationDirectSalesPage bookingSpeedButton() {
		BP.mediumWait();
		BP.javaScriptClick("//div[contains(@class, 'bookingSpeedBtnV2Item')]");
		BP.mediumWait();
		String getDateTime = BP.getText("//p[contains(text(), 'Departure Date/Time')]/following-sibling::p");
		if (!getDateTime.contains(CalmacTestValues.b2cSailingDate)
				| !getDateTime.contains(CalmacTestValues.b2cSailingTime)) {

			BP.javaScriptClick("//route-information-terminal//descendant::span[contains(text(), 'Change')]");
			BP.javaScriptClick("//button[contains(@aria-label, 'Open calendar')]");
			BP.javaScriptClick("//div[contains(text(), '" + CalmacTestValues.b2cSailingDate + "')]");
			BP.javaScriptClick("//route-information-terminal//descendant::span[contains(text(), 'Search')]");
			BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cSailingDate + "')][contains(text(), '"
					+ CalmacTestValues.b2cSailingTime + "')]");

			BP.javaScriptClick("//route-information-terminal//descendant::span[contains(text(), 'Confirm')]");

		}

		return this;
	}

	public SmartApplicationDirectSalesPage navigateToPassengerList() {
		BP.mediumWait();
		BP.javaScriptClick("//div[contains(text(), 'Passengers List')]");
		return this;
	}

	public SmartApplicationDirectSalesPage addPassengerDetail() {
		BP.shortWait();
		BP.setValue("Ali", "//input[@formcontrolname='name']");
		BP.setValue("Hassan", "//input[@formcontrolname='surname']");
		BP.setValue("1/4/1987", "//input[@formcontrolname='dateBirth']");
		BP.javaScriptClick("//input[@formcontrolname='passengerNationality']");
		BP.javaScriptClick("//span[contains(text(), 'UNITED KINGDOM')]");
		BP.javaScriptClick("//input[@formcontrolname='sex']");
		BP.javaScriptClick("//span[contains(text(), 'Male')]");
		BP.setValue("982173987", "//input[@formcontrolname='phonenumber']");
		BP.setValue("test@gmail.com", "//input[@formcontrolname='email']");
		BP.javaScriptClick("//span[contains(text(), 'BH')]");
		BP.javaScriptClick("//div[text()='Booking']");
		BP.mediumWait();
		BP.javaScriptClick("//span[contains(text(), 'Cash')]");
		return this;
	}

	public SmartApplicationDirectSalesPage retrieveLastBooking() {
		BP.longWait();
		BP.javaScriptClick("//span[contains(text(), 'Tickets')]");
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), 'Retrieve Last')]");
		BP.mediumWait();
		BP.javaScriptClick("//span[text()=' Information']");
		BP.mediumWait();
		CalmacTestValues.portOfficebookingReference = BP
				.getText("//div[contains(@data-test-automation, 'label-booking-reference')]");
		System.out.println(CalmacTestValues.portOfficebookingReference);
		CalmacTestValues.portOfficeSailingDateTime = BP
				.getText("//p[contains(text(), 'Departure Date/Time')]/following-sibling::p");
		return this;
	}

	public SmartApplicationDirectSalesPage selectDeparturePort() {
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), departurePort);
		BP.setValue(CalmacTestValues.b2cDeparturePort, departurePort);
		BP.click("//span[contains(text(), '" + CalmacTestValues.b2cDeparturePort + "')]");
		return this;
	}

	public SmartApplicationDirectSalesPage selectPayPoint() {
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), payPoint);

		BP.click("//div[contains(@role, 'listbox')]/descendant::span");

//        BP.click("//span[contains(text(), '192.168.1.75')]");
		return this;
	}

	public SmartApplicationDirectSalesPage selectArrivalPort() {
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), arrivalPort);
		BP.setValue(CalmacTestValues.b2cArrivalPort, arrivalPort);
		BP.click("//span[contains(text(), '" + CalmacTestValues.b2cArrivalPort + "')]");
		return this;
	}

	public SmartApplicationDirectSalesPage openSailingCalender() {
		BP.click(BP.getLongWaitTime(), openCalenderIcon);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), "//div[contains(text(), '" + CalmacTestValues.b2cSailingDate + "')]");
		return this;
	}

	public SmartApplicationDirectSalesPage addPassenger() {
		BP.shortWait();
		BP.javaScriptClick(addPassenger);

		return this;
	}

	public SmartApplicationDirectSalesPage addVehicle() {
		BP.shortWait();
		BP.javaScriptClick(openVehicleType);
		BP.javaScriptClick(selectVehicle);
		return this;
	}

	public SmartApplicationDirectSalesPage searchSailing() {
		BP.shortWait();
		BP.javaScriptClick(searchButton);
		return this;
	}

	public SmartApplicationDirectSalesPage selectSailingTime() {
		int size = BP.getCountOfElements("//*[contains(text(), '" + CalmacTestValues.b2cSailingTime
				+ "')]/preceding-sibling::div[contains(text(), '" + CalmacTestValues.b2cSailingDate
				+ "')]/ancestor::th/preceding-sibling::th") + 1;
		BP.mediumWait();
		BP.javaScriptClick("//*[contains(text(), '" + CalmacTestValues.b2cSailingTime
				+ "')]/ancestor::table/tbody/tr/td[" + size + "]/div/input");
//Next
		BP.javaScriptClick("//span[contains(text(), 'Next')]");
		return this;
	}

	public SmartApplicationDirectSalesPage addPassengerListInfo() {
		BP.javaScriptClick("//span[contains(text(), 'Passengers List')]");
		BP.setValue("Ali", "//input[@formcontrolname='name'][contains(@data-test-automation, 'DS.input-number')]");
		BP.setValue("AliAA", "//input[@formcontrolname='surname'][contains(@data-test-automation, 'DS.input-number')]");
		BP.javaScriptClick("//input[@formcontrolname='passengerNationality']");
		BP.javaScriptClick("//span[contains(text(), 'UNITED KINGDOM')]");
		BP.javaScriptClick("//input[@formcontrolname='sex']");
		BP.javaScriptClick("//span[contains(text(), 'Male')]");
		BP.setValue("982173987", "//input[@formcontrolname='phonenumber']");
		BP.setValue("test@gmail.com", "//input[@formcontrolname='email']");
		BP.javaScriptClick("//span[contains(text(), 'BH')]");
		BP.javaScriptClick("//*[@id]//span[contains(text(), 'Booking')]");
		BP.javaScriptClick("//span[contains(text(), 'Cash')]");
		return this;
	}

	public SmartApplicationDirectSalesPage getConfirmedBookingReference() {
		BP.mediumWait();
		CalmacTestValues.bookingReference = BP.getText("//td[contains(@data-test-automation, 'confirm-booking')]");
		System.out.println(CalmacTestValues.bookingReference);
		return this;
	}

	public SmartApplicationDirectSalesPage openSailingStatusPage() {
		BP.mediumWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Sailing Status')]");
		BP.shortWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Sailing Status')]");

		return this;
	}

	public SmartApplicationDirectSalesPage selectSailing() {
		BP.javaScriptClick("//button[contains(@aria-label, 'Open calendar')]");
		BP.javaScriptClick("//div[contains(text(), '" + CalmacTestValues.b2cSailingDate + "')]");
		BP.javaScriptClick("//mat-select[contains(@formcontrolname, 'sailing')]");
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cSailingTime + "')]");

		return this;
	}

	public SmartApplicationDirectSalesPage checkIn() {
//    	if(BP.isDisplayed("//mat-slide-toggle[@formcontrolname='footPassenger']/label/div/input")) {
//            BP.javaScriptClick("//mat-slide-toggle[@formcontrolname='footPassenger']/label/div/input");
//
//    	}
//        else {
//        	
//            BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'boarding')]");
//            BP.javaScriptClick("//mat-slide-toggle[@formcontrolname='footPassenger']/label/div/input");
//
//        }
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'boarding')]");
		BP.mediumWait();
		BP.javaScriptClick("//mat-slide-toggle[@formcontrolname='footPassenger']/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");

		BP.javaScriptClick("//mat-slide-toggle[@formcontrolname='vehicles']/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");

		BP.javaScriptClick("//mat-slide-toggle[@formcontrolname='freight']/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");

		return this;
	}

}
