package com.mscs.emr.automation.functional.calmac.pages.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.testData.MockDataUtils;

public class SmartBoardingPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
	String userName = "//input[contains(@formcontrolname, 'username')]";
	String usersPassword = "//input[contains(@formcontrolname, 'password')]";

	/**
	 * Click on the Login Button
	 */
	public SmartBoardingPage clickLoginIn() {
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'login')]");

		return this;
	}

	/**
	 * Enter the username in the user Field
	 */
	public SmartBoardingPage setUserName(String usersName) {
		BP.javaScriptScrollToView(userName);
		BP.setValue(BP.getMaxWaitTimeOut(), usersName, userName);
		BP.shortWait();
		return this;
	}

	/**
	 * Enter the username in the user Field
	 */
	public SmartBoardingPage setPassword(String password) {
		BP.setValue(BP.getMaxWaitTimeOut(), password, usersPassword);
		BP.shortWait();
		return this;
	}

	public SmartBoardingPage openTradingUnit() {
		BP.shortWait();
		BP.javaScriptClick("//div[contains(@class, 'mat-select-arrow')]");
		return this;
	}

	public SmartBoardingPage selectTradingUnit(String unit) {
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + unit + "')]");
		return this;
	}

	public SmartBoardingPage openPortOperationsList() {
		BP.shortWait();
		BP.javaScriptClick("//mat-select[contains(@formcontrolname, 'portOperation')]");

		return this;
	}

	public SmartBoardingPage selectPortOperations() {
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cDeparturePort + "')]");

		return this;
	}

	public SmartBoardingPage goButton() {
		BP.shortWait();
		BP.javaScriptClick("//*[contains(text(), 'Go')]");

		return this;
	}

	public SmartBoardingPage startMenuButton() {
		BP.shortWait();
		BP.javaScriptClick("//ion-buttons[contains(@slot, 'start')]");

		return this;
	}

	public SmartBoardingPage openSailingStatusPage() {
		BP.mediumWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Sailing Status')]");
		BP.shortWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Sailing Status')]");

		return this;
	}

	public SmartBoardingPage openOperationSetupPage() {
		BP.mediumWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Operation Setup')]");
		BP.shortWait();
		BP.javaScriptClick("//ion-label[contains(text(), 'Operation Setup')]");

		return this;
	}

	public SmartBoardingPage selectSailing() {
		BP.javaScriptClick("//button[contains(@aria-label, 'Open calendar')]");
		BP.javaScriptClick("//div[contains(text(), '" + CalmacTestValues.b2cSailingDate + "')]");
		BP.shortWait();
		BP.javaScriptClick("//mat-select[contains(@formcontrolname, 'sailing')]");
		BP.mediumWait();
		BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.b2cSailingTime + "')][contains(text(), '"
				+ CalmacTestValues.b2cSailingDate + "')]");
		BP.mediumWait();
		return this;
	}

	public SmartBoardingPage selectBoarding(String operation) {
		BP.javaScriptClick("//*[contains(@formcontrolname, 'deviceMode')]");
		BP.shortWait();
		BP.javaScriptClick("//span[contains(text(), '" + operation + "')]");
		return this;
	}

	public SmartBoardingPage startOperationsButton() {
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'button-start')]");
		return this;
	}

	public SmartBoardingPage completeBoarding(String crewMember, String footPassenger) {
		BP.setValue(crewMember, "//input[contains(@formcontrolname, 'crewMember')]");
		BP.setValue(footPassenger,
				"//input[contains(@formcontrolname, 'footPassenger')][contains(@placeholder, 'Foot Passenger')]");
		BP.setValue(BP.getMaxWaitTimeOut(), "Test Comment", "//textarea[contains(@class, 'ng-touched')]");
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'boarding-complete')]");

		return this;
	}

	public SmartBoardingPage performCheckIn() {
		BP.setValue(BP.getMaxWaitTimeOut(), CalmacTestValues.bookingReference,
				"//input[contains(@placeholder, 'Booking Reference')]");
		BP.shortWait();
		BP.javaScriptClick("//ion-icon[contains(@name, 'search-outline')]");
		BP.shortWait();
		BP.javaScriptClick("//button[contains(text(), 'Check-in')]");
		return this;
	}

	public SmartBoardingPage performCheckInAndEmbark() {
		BP.shortWait();
		BP.javaScriptClick("//input[contains(@role, 'switch')]");
		BP.shortWait();
		BP.setValue(BP.getMaxWaitTimeOut(), CalmacTestValues.portOfficebookingReference,
				"//input[contains(@placeholder, 'Booking Reference')]");

		return this;
	}

	public SmartBoardingPage checkIn() {

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

	public SmartBoardingPage closeCheckIn() {
		BP.shortWait();

		BP.javaScriptClick(
				"//div[contains(@formgroupname, 'boardingManagement')]//mat-slide-toggle[contains(@formcontrolname, 'footPassenger')]/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");
		BP.shortWait();
		BP.javaScriptClick(
				"//div[contains(@formgroupname, 'boardingManagement')]//mat-slide-toggle[contains(@formcontrolname, 'vehicles')]/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");
		BP.shortWait();
		BP.javaScriptClick(
				"//div[contains(@formgroupname, 'boardingManagement')]//mat-slide-toggle[contains(@formcontrolname, 'freight')]/label/div/input");
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");
		BP.shortWait();
		BP.javaScriptClick("//ion-button[contains(@data-test-automation, 'close-checkin')]");
		BP.shortWait();
		BP.click(BP.getMaxWaitTimeOut(), "//button[contains(text(), 'OK')]");

		return this;
	}

}
