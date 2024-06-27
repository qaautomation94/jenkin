package com.mscs.emr.automation.functional.calmac.pages.core;

import static io.restassured.RestAssured.given;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.InvokedMethodListener;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.ExcelReader;
import com.mscs.emr.automation.utils.OracleDbUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CalmacBookingCreationPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	OracleDbUtils oracleDbUtils = new OracleDbUtils();

	BaseTestPage BP = new BaseTestPage();
	String lastDownloadedFileName;
	String departureSailingCode;
	String arrivalSailingCode;
	String checkinStatus;
	String operatingStatus;


	String BookingRef;

	String getBookingReference = "//div[contains(text(), ' Booking number: ')]/span";
	String addvehicleButton = "//ion-button[contains(@class, 'ion-color ion-color-primary md button button-solid ion-activatable ion-focusable hydrated')]";

	String myBookings = "//ion-item[contains(@data-test-automation, 'myJourneys')]";
	String returnLink = "(//div[contains(@class, 'trip-type-btn')])[2]";
//	String multiLegLink = "(//div[contains(@class, 'trip-type-btn')])[3]";
	
	String oneWayButton = "//*[contains(text(), 'One way')]";
	String returnButton = "//*[contains(text(), 'Return')]";
	String multiIslandButton = "//*[contains(text(), 'Multi-island')]";
	String journeyButton = "//*[contains(text(), '10 Journey')]";

	String oneWayLink = "(//div[contains(@class, 'trip-type-content dark-color')]/div)[1]";
	String downloadFile = "//button[contains(@id, 'download')]";
	String passengerLastName = "//ion-input[contains(@formcontrolname, 'lastName')]/input";
	String passengerFirstName = "//ion-input[contains(@formcontrolname, 'firstName')]/input";
	String addInfantDetail = "//div[contains(text(), 'Infant under 5')]";
	String addChildDetail = "//div[contains(text(), 'Child')]";

 	String addAdult = "//div[contains(@class, 'step2-choose-product')]/descendant::div[contains(text(), 'Adult 16')]";
 	String country = "//input[contains(@placeholder, 'Country')]";

	
	String addAdultDetail = "//div[@class='counter-value no-border']";

	String PostCode = "//edea-select[contains(@dataAuto, 'postCode')]/descendant::input";

	String addressLine1 = "//ion-input[@formcontrolname='addressLineOne']/input";
	String town = "//ion-input[@formcontrolname='city']/input";
	String email = "//input[@type='email']";	

	String openMobilePrefix = "//input[@placeholder='Prefix']";
	String mobileNo = "//input[@type='tel']";
	
	String searchNationality = "//input[contains(@placeholder, 'Nationality')]";
	String day = "//input[contains(@placeholder, 'DD')]";
	String month = "//input[contains(@placeholder, 'MM')]";
	String year = "//input[contains(@placeholder, 'YYYY')]";
	String selectSailingDayTime = "//span[contains(@class, 'sailing-not-selected')]";
	String selectDay = "//div[contains(@data-test-automation, '2024-03-05')]";
	String selectSailingTime = "//ion-button[contains(text(), 'Select sailing times')]";
	String DontAddJourney = "//p[contains(text(), 'Do you want to add another journey?')]/parent::div/div[contains(@class, 'trip-type-content')]/div[2]";
	
	String goToCheckout = "//*[contains(text(), 'Go to check out')]";

	String addJourney = "//p[contains(text(), 'Do you want to add another journey?')]/parent::div/div[contains(@class, 'trip-type-content')]/div";
	String doneButton = "//div[contains(@class, 'step-product-form-button')]/ion-button";
	String addInfantButton = "//div[contains(text(), 'Infant under 5 Years')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String choosePassengerToAdd = "//div[contains(@class, 'step2-choose-product')]";
	String addPassengerDetail = "//div[contains(@class, 'step2-choose-product')]/descendant::div[contains(text(), 'Ferry')]";

	String choosePetsToAdd = "(//div[contains(@class, 'step2-choose-product')])[2]";
	String chooseVehicleAndBycyleToAdd = "(//div[contains(@class, 'step2-choose-product')])[3]";
	String addCompSptFerryCompanyButton = "//div[contains(text(), 'Comp SPT Ferry Card')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	
	String addPetInPassengerLounge = "//div[contains(text(), 'Pet in Pax Lounge')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String addPetRemainInCar = "//div[contains(text(), 'Pet Remain in Car')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String addAssistanceDog = "//div[contains(text(), 'Assistance Dog')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String showMoreButton = "//div[contains(@class, 'edea-show-more')]/ion-button";
	String continueButton = "//ion-button[contains(@color, 'primary')][text()=' Continue ']";
	String arrivalPort = "//input[contains(@placeholder, 'To')]";
	String departurePort = "//input[contains(@placeholder, 'From')]";
	String addCar = "//div[text()=' Car ']/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String addBlueBadgeMcycleButton = "//div[contains(text(), 'Blue Badge Mcycle')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]";
	String loginToYourAccout = "//a[contains(text(), 'Login into your account')]";
	String findVehicleButton = "//div[contains(@class, 'find-address')]/ion-button";
	String relativePath = Config.getResourcesFolderPath();
	String xmlTestDataFileName = Config.getExcelFineNameToReadXMLTestData();
	ExcelReader readCellForXML = new ExcelReader(relativePath + "/" + xmlTestDataFileName);

	public CalmacBookingCreationPage oneWay() {
		BP.click(BP.getLongWaitTime(), oneWayLink);

		return this;
	}

	public CalmacBookingCreationPage returnBooking() {
		BP.click(BP.getLongWaitTime(), returnLink);
		return this;
	}

	public CalmacBookingCreationPage multiLegBooking() {
		BP.javaScriptClick(multiIslandButton);
		return this;
	}
	public CalmacBookingCreationPage selectDeparturePort(int rowId, String Sheetname) {
		BP.shortWait();
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "DEPARTURE_PORT", rowId), departurePort);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), "//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "DEPARTURE_PORT", rowId) + "')]");
		BP.shortWait();
		return this;
	}

	public CalmacBookingCreationPage selectArrivalPort(int rowId, String Sheetname) {
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ARRIVAL_PORT", rowId), arrivalPort);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), "//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "ARRIVAL_PORT", rowId) + "')]");
		return this;
	}

	public CalmacBookingCreationPage selectMultiLegDeparturePort(String Sheetname, String columnName, int rowId) {
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, columnName, rowId), departurePort);

		BP.click(BP.getLongWaitTime(), "//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, columnName, rowId) + "')]");

		return this;
	}
	public CalmacBookingCreationPage selectMultiLegArrivalPort(String Sheetname, String columnName, int rowId)  {
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, columnName, rowId), arrivalPort);

		BP.click(BP.getLongWaitTime(), "//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, columnName, rowId) + "')]");

		return this;
	}
	public CalmacBookingCreationPage continueBooking() {
		BP.shortWait();
		BP.javaScriptScrollToView(continueButton);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), continueButton);
		BP.shortWait();
		BP.javaScriptScrollToView(continueButton);
		BP.click(BP.getLongWaitTime(), continueButton);
		BP.shortWait();

		return this;
	}
	public CalmacBookingCreationPage addJourney(boolean bol) {

		if(bol) {
			BP.javaScriptClick(addJourney);
		}
		else {
			BP.javaScriptClick(DontAddJourney);

		}
		BP.shortWait();

		return this;
	}
	public CalmacBookingCreationPage continueAfterAddingProduct() {


		BP.javaScriptScrollToView(continueButton);
		BP.javaScriptClick(continueButton);
		BP.shortWait();

		return this;
	}

	public CalmacBookingCreationPage choosePassengerType(int rowId, String Sheetname) {
		BP.javaScriptClick(choosePassengerToAdd);
		BP.shortWait();
		String PType = CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE", rowId);
		BP.javaScriptClick("//div[contains(text(), '" + PType
				+ "')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]");
		BP.shortWait();
		BP.javaScriptClick(doneButton);
		BP.shortWait();
		return this;
	}

	public CalmacBookingCreationPage choosePassenger(String passenger) {
		BP.click(BP.getLongWaitTime(), choosePassengerToAdd);
		BP.shortWait();
		BP.javaScriptClick("//div[contains(text(), '" + passenger
				+ "')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]");
		BP.shortWait();
		BP.javaScriptClick(doneButton);
		BP.shortWait();
		return this;
	}

	public CalmacBookingCreationPage addPetType(int rowId, String Sheetname) {
	
		BP.click(BP.getLongWaitTime(), choosePetsToAdd);

		String PetType = CalmacTestValues.readCellForXML.getCellData(Sheetname, "PET_TYPE", rowId);
		BP.javaScriptClick("//div[contains(text(), '" + PetType
				+ "')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]");
		BP.shortWait();
		BP.javaScriptClick(doneButton);
		return this;
		
	
	}

	public CalmacBookingCreationPage addPet(String PetType) {
		
		BP.click(BP.getLongWaitTime(), choosePetsToAdd);

		BP.javaScriptClick("//div[contains(text(), '" + PetType
				+ "')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]");
		BP.shortWait();
		BP.javaScriptClick(doneButton);
		return this;
		
	
	}

	
	public CalmacBookingCreationPage removePassengerType(int rowId, String Sheetname) {
		BP.click(BP.getLongWaitTime(), choosePassengerToAdd);

		String PType = CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE", rowId);
		BP.click(BP.getLongWaitTime(),
				"//div[contains(text(), 'Adult')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.remove')]");
		BP.shortWait();
        BP.javaScriptClick(doneButton);
//        BP.shortWait();
		return this;
	}

	public CalmacBookingCreationPage selectSailingDateTime() {
		BP.shortWait();
		BP.javaScriptClick(selectSailingTime);
		return this;
	}


	public CalmacBookingCreationPage chooseSailingDateTimeForReturn(int rowId, String Sheetname) {

		BP.javaScriptClick(selectSailingTime);

		BP.javaScriptClick("//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForDeparture + "')]");
		BP.mediumWait();
		BP.javaScriptClick("//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForArrival + "')]");
		BP.mediumWait();
		BP.click(BP.getLongWaitTime(), selectSailingTime);
		BP.mediumWait();

		List<WebElement> li=BP.getDriver().findElements(By.xpath("//span[contains(text(), 'Available')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]"));
				
		if (li.size()>=1) {
			

			BP.javaScriptClick(
					"//span[contains(text(), 'Available')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");
			BP.shortWait();
			BP.javaScriptClick(
					"//span[contains(text(), 'Available')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");

			
		} else {
	
			BP.javaScriptScrollToView("//span[contains(text(), '" + CalmacTestValues.sailingTimeForDeparture
					+ "')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");
			BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.sailingTimeForDeparture
					+ "')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");
			
			BP.javaScriptScrollToView("//span[contains(text(), '" + CalmacTestValues.sailingTimeForArrival
					+ "')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");
			BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.sailingTimeForArrival
					+ "')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");

		}
		BP.longWait();
		BP.javaScriptClick(continueButton);

		BP.longWait();
		BP.javaScriptClick(continueButton);
		BP.longWait();

		return this;
	}

	public CalmacBookingCreationPage addPassengerDetail(int rowId, String Sheetname, String passengerType) {
		if (passengerType.equals("Child")) {
			BP.click(BP.getLongWaitTime(), addChildDetail);

		}
		if (passengerType.equals("Infant")) {
			BP.click(BP.getLongWaitTime(), addInfantDetail);
			

		}
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_FIRST_NAME", rowId),
				passengerFirstName);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_LAST_NAME", rowId),
				passengerLastName);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_NATIONALITY", rowId),
				searchNationality);
		BP.shortWait();
		BP.javaScriptClick("//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_NATIONALITY", rowId) + "')]");
		BP.javaScriptScrollToView(day);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_DAY", rowId), day);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_MONTH", rowId), month);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_YEAR", rowId), year);

		BP.shortWait();
		BP.javaScriptClick(
				"//*[text()='" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_GENDER", rowId)
						+ "']/following-sibling::*");

		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
				+ "']/following-sibling::*");

		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}


	public CalmacBookingCreationPage addGuestPassengerDetailS(int rowId, String Sheetname, String passengerType) {
		if (passengerType.equals("Child")) {
			BP.click(BP.getLongWaitTime(), addChildDetail);

		}
		if (passengerType.equals("Infant")) {
			BP.click(BP.getLongWaitTime(), addInfantDetail);
			BP.mediumWait();
			List<WebElement> li = BP.getDriver().findElements(By.xpath(passengerFirstName));
			if(li.size()>=1) {

				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_FIRST_NAME", rowId),
						passengerFirstName);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_LAST_NAME", rowId),
						passengerLastName);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_NATIONALITY", rowId),
						searchNationality);
				BP.shortWait();
				BP.javaScriptClick("//div[contains(text(), '"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_NATIONALITY", rowId) + "')]");
				BP.javaScriptScrollToView(day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_DAY", rowId), day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_MONTH", rowId), month);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_YEAR", rowId), year);
				BP.shortWait();
				BP.javaScriptClick(
						"//*[text()='" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_GENDER", rowId)
								+ "']/following-sibling::*");
	
			}

			BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "INFANT_ASSISTANCE", rowId)
					+ "']/following-sibling::*");

		}

		if (passengerType.equals("Adult")) {
			BP.javaScriptClick(addAdult);

			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_FIRST_NAME", rowId),
					passengerFirstName);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_LAST_NAME", rowId),
					passengerLastName);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId),
					country);
			BP.shortWait();
			BP.javaScriptClick("//div[contains(text(), '"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId) + "')]");

			BP.javaScriptScrollToView(PostCode);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_POST_CODE", rowId), PostCode);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADDRESS_Line1", rowId), addressLine1);
			
			BP.javaScriptScrollToView(town);

			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "TOWN", rowId), town);
			
			List<WebElement> li = BP.getDriver().findElements(By.xpath(searchNationality));
			if(li.size()>=1) {
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId),
						searchNationality);
				BP.shortWait();
				BP.javaScriptClick("//div[contains(text(), '"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId) + "')]");	
				BP.javaScriptScrollToView(day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_DAY", rowId), day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_MONTH", rowId), month);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_YEAR", rowId), year);

				BP.shortWait();
				BP.javaScriptClick(
						"//*[text()='" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "GENDER", rowId)
								+ "']/following-sibling::*");

				BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
						+ "']/following-sibling::*");
			}			
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId), email);

			BP.javaScriptScrollToView(openMobilePrefix);
			BP.javaScriptClick(openMobilePrefix);
			BP.click("//div[contains(text(), '" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "MOBILE_PREFIX", rowId) + "')]");
			BP.shortWait();
			BP.setValue(CalmacTestValues.getMobile(rowId, Sheetname), mobileNo);

		}

		if (passengerType.equals("SPT Ferry Card")) {
			BP.javaScriptClick(addPassengerDetail);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_FIRST_NAME", rowId),
					passengerFirstName);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_LAST_NAME", rowId),
					passengerLastName);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId),
					country);
			BP.shortWait();
			BP.javaScriptClick("//div[contains(text(), '"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId) + "')]");

			BP.javaScriptScrollToView(PostCode);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_POST_CODE", rowId), PostCode);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADDRESS_Line1", rowId), addressLine1);
			
			BP.javaScriptScrollToView(town);

			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "TOWN", rowId), town);
			
			List<WebElement> li = BP.getDriver().findElements(By.xpath(searchNationality));
			if(li.size()>=1) {
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId),
						searchNationality);
				BP.shortWait();
				BP.javaScriptClick("//div[contains(text(), '"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId) + "')]");	
				BP.javaScriptScrollToView(day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_DAY", rowId), day);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_MONTH", rowId), month);
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_YEAR", rowId), year);

				BP.shortWait();
				BP.javaScriptClick(
						"//*[text()='" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "GENDER", rowId)
								+ "']/following-sibling::*");

				BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
						+ "']/following-sibling::*");
			}			
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId), email);

			BP.javaScriptScrollToView(openMobilePrefix);
			BP.javaScriptClick(openMobilePrefix);
			BP.click("//div[contains(text(), '" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "MOBILE_PREFIX", rowId) + "')]");
			BP.shortWait();
			BP.setValue(CalmacTestValues.getMobile(rowId, Sheetname), mobileNo);
			
			BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
					+ "']/following-sibling::*");

			BP.click("//ion-radio-group[@formcontrolname='concessionCard']/descendant::*[text()='"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD", rowId)
					+ "']/following-sibling::*");
			BP.shortWait();
			if ("Yes".equals(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD", rowId))) {
				BP.javaScriptScrollToView("//*[contains(text(), 'Type of concession')]/parent::div/descendant::input[contains(@data-test-automation, 'undefined.input.portlist')]");
				BP.mediumWait();
				BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE_OF_CONCESSION", rowId),
						"//*[contains(text(), 'Type of concession')]/parent::div/descendant::input[contains(@data-test-automation, 'undefined.input.portlist')]");
				BP.shortWait();

				BP.javaScriptClick("//*[contains(text(), '"
						+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE_OF_CONCESSION", rowId)
						+ "')][@class='edea-option-label']");
				BP.shortWait();
				BP.setValue(
						CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD_NUMBER", rowId),
						"//*[contains(@formcontrolname, 'concessionNumber')]/input");
			}


		}


		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}

/*
	public CalmacBookingCreationPage addGuestPassengerDetail(int rowId, String Sheetname, String passengerType) {

		BP.javaScriptClick(addAdult);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_FIRST_NAME", rowId),
				passengerFirstName);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_LAST_NAME", rowId),
				passengerLastName);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId),
				country);
		BP.shortWait();
		BP.javaScriptClick("//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_COUNTRY", rowId) + "')]");

		BP.javaScriptScrollToView(PostCode);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT_POST_CODE", rowId), PostCode);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADDRESS_Line1", rowId), addressLine1);
		
		BP.javaScriptScrollToView(town);

		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "TOWN", rowId), town);
		
		List<WebElement> li = BP.getDriver().findElements(By.xpath(searchNationality));
		if(li.size()>=1) {
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId),
					searchNationality);
			BP.shortWait();
			BP.javaScriptClick("//div[contains(text(), '"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "NATIONALITY", rowId) + "')]");			
		}
	

		
		
		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "EMAIL", rowId), email);

		BP.javaScriptScrollToView(openMobilePrefix);
		BP.javaScriptClick(openMobilePrefix);
		BP.click("//div[contains(text(), '" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "MOBILE_PREFIX", rowId) + "')]");
		BP.shortWait();
		BP.setValue(CalmacTestValues.getMobile(rowId, Sheetname), mobileNo);

		
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT__DAY", rowId), day);
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT__MONTH", rowId), month);
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT__YEAR", rowId), year);
		BP.shortWait();
//		BP.javaScriptClick(
//				"//*[text()='" + CalmacTestValues.readCellForXML.getCellData(Sheetname, "GENDER", rowId)
//						+ "']/following-sibling::*");
//		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
//				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "ADULT__ASISSTANCE", rowId)
//				+ "']/following-sibling::*");
		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}
*/

	
	public CalmacBookingCreationPage addPassengerAssistanceNo(int rowId, String Sheetname) {

		BP.click(BP.getLongWaitTime(), addAdultDetail);
		BP.shortWait();

		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
				+ "']/following-sibling::*");
		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}

	public CalmacBookingCreationPage addInfantAssistanceNo(int rowId, String Sheetname) {

		BP.click(BP.getLongWaitTime(), addAdultDetail);
		BP.shortWait();

		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
				+ "']/following-sibling::*");
		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}

	public CalmacBookingCreationPage findAvailableSailingForDeparture(int rowId, String Sheetname)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		String legCode = CalmacTestValues.readCellForXML.getCellData(Sheetname, "LEG_CODE", rowId);
		oracleDbUtils.executeCalmacQuery("SELECT DISTINCT  ms.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY ip JOIN MSP_SAILINGSTRUCTUREDEPARTURE ms ON ip.SAILINGCODE = ms.SAILINGCODE WHERE ms.SAILINGCODE NOT IN (SELECT A.SAILINGCODE FROM MSP_SAILINGSTRUCTUREDEPARTURE A WHERE A.STATENAME IN ('OPEN PRE CHECK-IN', 'OPEN CHECK-IN', 'OPEN GATE', 'CLOSE CHECK-IN', 'CLOSE SAILING', 'CANCELLED', 'CLOSE GATE', 'LOCKED','LOCKED FOR ALL CHANGE', 'LOCKED FOR NEW BOOKING') UNION SELECT B.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY B WHERE B.PRODUCTAVAILABILITY < '10' AND B.PRODUCTCODE IN ('BIKE', 'ADL', 'PET_CAR', 'BBCAR', 'ADL_SPTC', 'ADL_SPT', 'MOTORHOME', 'CAR')) AND ms.SAILINGCODE LIKE '"+legCode+"%"+"' AND ms.DEPARTURETIME > CURRENT_DATE + INTERVAL '1' DAY AND ms.SAILINGCODE IN (SELECT SAILINGCODE FROM MSP_SAILINGSALESCHOPERATION WHERE RESTRICTEDSALES NOT IN ('Y')) ORDER BY ms.SAILINGCODE ASC");

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;
			if (resultSet.next()) {
				String columnValue = (String) resultSet.getObject(1);
				departureSailingCode = columnValue;
				System.out.println("Departure Sailing Code is" + departureSailingCode);

				CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14); // Extracting characters at
																							// index 8 and 9
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
						+ CalmacTestValues.sailingDayForDeparture.substring(2);

				CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
				CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
						+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);

				// Do something with the value, such as printing
				System.out.println("First column value of the first row: " + columnValue);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
/*
		oracleDbUtils.executeCalmacQuery("SELECT RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY WHERE SAILINGCODE ='"+departureSailingCode+"'  AND PRODUCTCODE ='ADL'");

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
				BigDecimal columnValue = (BigDecimal) resultSet.getObject(1);
				CalmacTestValues.soldPassengerCapacityBeforeBooking = columnValue.toString();

				// Do something with the value, such as printing
				System.out.println("Available Capacity row: " + CalmacTestValues.soldPassengerCapacityBeforeBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
		return this;

	}

	public CalmacBookingCreationPage validSailingForBooking(String sailingCode)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {


				departureSailingCode = sailingCode;
				System.out.println("Departure Sailing Code is" + departureSailingCode);

				CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14); // Extracting characters at
																							// index 8 and 9
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
						+ CalmacTestValues.sailingDayForDeparture.substring(2);

				CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
				CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
						+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);

				// Do something with the value, such as printing
				System.out.println("First column value of the first row: " + sailingCode);
		return this;

	}

	
	
	public CalmacBookingCreationPage getAvailableSailingCodeForMultiDeparture(String sheetname, String depPort, String arrivalPort, int rowId, int sailing) throws Exception {
		System.out.println(calmacTestValues.priceAvailabilityPayload().replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId)).replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId)));
		
		Response postResponse = given()
	             .contentType(ContentType.JSON)
	             .header("Authorization", "Bearer " + calmacTestValues.token)
	             .body(calmacTestValues.priceAvailabilityPayload()
	            		 .replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, depPort, rowId))
	            		 .replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, arrivalPort, rowId)))

	             .when()
	             .post("https://testcfl.e-dea.it/EBookingRestServices/ebooking/booking/priceAvailability") // replace with your actual endpoint for the POST request
	             .then()
	             .statusCode(200) // replace with the expected status code
	             .extract()
	             .response();
		 System.out.println("Response status code: " + postResponse.getStatusCode());
	     System.out.println("Response body: " + postResponse.getBody().asString());
	             
	     JSONObject jsonObject = new JSONObject(postResponse.getBody().asString());
	     JSONArray sailingPrices = jsonObject.getJSONArray("sailingprice");         

	     for (int i = 0; i < sailingPrices.length(); i++) {
	         JSONObject sailingPrice = sailingPrices.getJSONObject(i);
	         JSONObject routeDateTimeResponse = sailingPrice.getJSONObject("c_RDR_RouteDateTimeResponse");
	         JSONObject travelRoute = routeDateTimeResponse.getJSONObject("m_ROUT_TravelRoute");

	         System.out.println("Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));

	         boolean isAvailable = travelRoute.getBoolean("c_C081_IsAvailable");
	         JSONObject sailingPriceObj = sailingPrices.getJSONObject(i);
	         JSONObject routeDateTimeResponseObj = sailingPriceObj.getJSONObject("c_RDR_RouteDateTimeResponse");
	         
	         JSONArray sailingLegsArray = routeDateTimeResponseObj.getJSONObject("m_LEG_SailingLegs").getJSONArray("m_LEG_SailingLeg");

	         JSONObject sailingLegObj = sailingLegsArray.getJSONObject(0);
	         checkinStatus = sailingLegObj.getString("c_C391_checkInStatus");
	         System.out.println("Operating Status: " + checkinStatus);
	         boolean isDummyShip = routeDateTimeResponse.getJSONObject("c_SHNM_ShipName").getBoolean("c_C316_DummyShip");

	         
	         	System.out.println("Is Available: " + isAvailable);
	         if(isAvailable==true && checkinStatus.contains("OPEN SAILING") && isDummyShip==false) {
	        	 CalmacTestValues.availableDepartureSailingCode=travelRoute.getString("c_C276_SailingCode");
	        	 System.out.println("Valid Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));
	             
	             break;

	         }
	         
	     }
	     departureSailingCode=CalmacTestValues.availableDepartureSailingCode;
			if(sailing==1) {
				CalmacTestValues.departureSailingCode1=departureSailingCode;
			}
			if(sailing==2) {
				CalmacTestValues.departureSailingCode2=departureSailingCode;
			}
			if(sailing==3) {
				CalmacTestValues.departureSailingCode3=departureSailingCode;
			}

			CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14); // Extracting characters at
																						// index 8 and 9
			CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
					+ CalmacTestValues.sailingDayForDeparture.substring(2);

			CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
			CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
					+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);

	 	return this;

	}

	
	
	public CalmacBookingCreationPage findMultiLegAvailableSailing(int rowId, String Sheetname, String LEG_CODE, int sailing)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		String legCode = CalmacTestValues.readCellForXML.getCellData(Sheetname, LEG_CODE, rowId);
//		oracleDbUtils.executeCalmacQuery("SELECT ms.SAILINGCODE FROM IBE_RESOURCEAVAILABILITY ir JOIN MSP_SAILINGSTRUCTUREDEPARTURE ms ON ir.SAILINGCODE = ms.SAILINGCODE WHERE ir.RESOURCEAVAILABILITY >= 2  AND ir.PRODUCTCODE IN ('BIKE', 'ADL', 'PET_CAR')  AND ms.SAILINGCODE NOT IN (SELECT SAILINGCODE  FROM MSP_SAILINGSTRUCTUREDEPARTURE WHERE STATENAME  IN ('OPEN PRE CHECK-IN', 'OPEN CHECK-IN', 'OPEN GATE', 'CLOSE CHECK-IN', 'CLOSE SAILING', 'CANCELLED', 'CLOSE GATE', 'LOCKED','LOCKED FOR ALL CHANGE', 'LOCKED FOR NEW BOOKING')  AND SAILINGCODE = ms.SAILINGCODE) AND ms.SAILINGCODE LIKE '"+legCode+"%"+"' AND ms.DEPARTURETIME > CURRENT_DATE AND ms.SAILINGCODE IN (SELECT SAILINGCODE FROM MSP_SAILINGSALESCHOPERATION WHERE RESTRICTEDSALES NOT IN ('Y')) ORDER BY SAILINGCODE ASC");
		oracleDbUtils.executeCalmacQuery("SELECT DISTINCT  ms.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY ip JOIN MSP_SAILINGSTRUCTUREDEPARTURE ms ON ip.SAILINGCODE = ms.SAILINGCODE WHERE ms.SAILINGCODE NOT IN (SELECT A.SAILINGCODE FROM MSP_SAILINGSTRUCTUREDEPARTURE A WHERE A.STATENAME IN ('OPEN PRE CHECK-IN', 'OPEN CHECK-IN', 'OPEN GATE', 'CLOSE CHECK-IN', 'CLOSE SAILING', 'CANCELLED', 'CLOSE GATE', 'LOCKED','LOCKED FOR ALL CHANGE', 'LOCKED FOR NEW BOOKING') UNION SELECT B.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY B WHERE B.PRODUCTAVAILABILITY < '10' AND B.PRODUCTCODE IN ('BIKE', 'ADL', 'PET_CAR', 'BBCAR', 'ADL_SPTC', 'MOTORHOME')) AND ms.SAILINGCODE LIKE '"+legCode+"%"+"' AND ms.DEPARTURETIME > CURRENT_DATE + INTERVAL '2' DAY AND ms.SAILINGCODE IN (SELECT SAILINGCODE FROM MSP_SAILINGSALESCHOPERATION WHERE RESTRICTEDSALES NOT IN ('Y')) ORDER BY ms.SAILINGCODE ASC");

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;
			if (resultSet.next()) {
				String columnValue = (String) resultSet.getObject(1);
				departureSailingCode = columnValue;

				if(sailing==1) {
					CalmacTestValues.departureSailingCode1=departureSailingCode;
				}
				if(sailing==2) {
					CalmacTestValues.departureSailingCode2=departureSailingCode;
				}
				if(sailing==3) {
					CalmacTestValues.departureSailingCode3=departureSailingCode;
				}
				departureSailingCode = columnValue;
				System.out.println("Departure Sailing Code is" + departureSailingCode);

				CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14); // Extracting characters at
																							// index 8 and 9
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
						+ CalmacTestValues.sailingDayForDeparture.substring(2);

				CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
				CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
						+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);

				// Do something with the value, such as printing
				System.out.println("First column value of the first row: " + columnValue);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	public CalmacBookingCreationPage findAvailableSailingForArrival(int rowId, String Sheetname)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		String legCode = CalmacTestValues.readCellForXML.getCellData(Sheetname, "RETURN_LEG_CODE", rowId);
		oracleDbUtils.executeCalmacQuery("SELECT DISTINCT  ms.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY ip JOIN MSP_SAILINGSTRUCTUREDEPARTURE ms ON ip.SAILINGCODE = ms.SAILINGCODE WHERE ms.SAILINGCODE NOT IN (SELECT A.SAILINGCODE FROM MSP_SAILINGSTRUCTUREDEPARTURE A WHERE A.STATENAME IN ('OPEN PRE CHECK-IN', 'OPEN CHECK-IN', 'OPEN GATE', 'CLOSE CHECK-IN', 'CLOSE SAILING', 'CANCELLED', 'CLOSE GATE', 'LOCKED','LOCKED FOR ALL CHANGE', 'LOCKED FOR NEW BOOKING') UNION SELECT B.SAILINGCODE FROM IBE_PRODUCTAVAILABILITY B WHERE B.PRODUCTAVAILABILITY < '10' AND B.PRODUCTCODE IN ('BIKE', 'ADL', 'PET_CAR', 'BBCAR', 'ADL_SPTC', 'ADL_SPT', 'MOTORHOME', 'CAR')) AND ms.SAILINGCODE LIKE '"+legCode+"%"+"' AND ms.DEPARTURETIME > CURRENT_DATE + INTERVAL '2' DAY AND ms.SAILINGCODE IN (SELECT SAILINGCODE FROM MSP_SAILINGSALESCHOPERATION WHERE RESTRICTEDSALES NOT IN ('Y')) ORDER BY ms.SAILINGCODE ASC");
	
		
		try {
			ResultSet resultSet = OracleDbUtils.resultSet;
			if (resultSet.next()) {
				String columnValue = (String) resultSet.getObject(1);
				arrivalSailingCode = columnValue;
				System.out.println("Arrival Sailing Code is" + arrivalSailingCode);
				CalmacTestValues.sailingDayForArrival = arrivalSailingCode.substring(10, 14); // Extracting characters at index
																						// 8 and 9
				CalmacTestValues.sailingDayForArrival = CalmacTestValues.sailingDayForArrival.substring(0, 2) + "-"
						+ CalmacTestValues.sailingDayForArrival.substring(2);
				CalmacTestValues.sailingTimeForArrival = arrivalSailingCode.substring(14, 18);
				CalmacTestValues.sailingTimeForArrival = CalmacTestValues.sailingTimeForArrival.substring(0, 2) + ":"
						+ CalmacTestValues.sailingTimeForArrival.substring(2);

				// Do something with the value, such as printing
				System.out.println("First column value of the first row: " + columnValue);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return this;

	}

	public CalmacBookingCreationPage findSoldCpacityOfPetBeforeBooking() throws InterruptedException, AWTException,
			IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

//		oracleDbUtils.executeCalmacQuery("SELECT ir.RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY ir , MSP_SAILINGSTRUCTUREDEPARTURE ms WHERE  ir.RESOURCEAVAILABILITY >='2' AND ir.PRODUCTCODE ='ADL' AND ir.SAILINGCODE LIKE '"+CalmacTestValues.getLegCode(rowId)+"%'AND ir.SAILINGCODE  > '"+CalmacTestValues.getLegCode(rowId)+"' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  AND ir.ENDDATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AND ms.STATENAME IN('OPEN SAILING') AND ir.SAILINGCODE  NOT LIKE  '%0250'");
		oracleDbUtils
				.executeCalmacQuery("SELECT SOLDCAPACITY FROM MSP_PHRESOURCETYPECAPACITIES mp WHERE SAILINGCODE = '"
						+ departureSailingCode + "'AND PHYSICALRESOURCETYPE ='PET'");

//		oracleDbUtils.executeCalmacQuery(CalmacTestValues.readCellForXML.getCellData("BOOKING_DETAIL", "FIND_RESOURCE_AVAILABILITY", rowId));

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
				// Retrieve the value from the first column of the first row
				String columnValue = resultSet.getString(1);
				// Convert the string to BigDecimal if needed
				BigDecimal decimalValue = new BigDecimal(columnValue);
				CalmacTestValues.soldPetCapacityBeforeBooking = decimalValue.toString();

				// Do something with the value, such as printing
				System.out.println("Sold Pet Capacity row: " + CalmacTestValues.soldPetCapacityBeforeBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	public CalmacBookingCreationPage findSoldOfPetCpacityAfterBooking() throws InterruptedException, AWTException,
			IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

//		oracleDbUtils.executeCalmacQuery("SELECT ir.RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY ir , MSP_SAILINGSTRUCTUREDEPARTURE ms WHERE  ir.RESOURCEAVAILABILITY >='2' AND ir.PRODUCTCODE ='ADL' AND ir.SAILINGCODE LIKE '"+CalmacTestValues.getLegCode(rowId)+"%'AND ir.SAILINGCODE  > '"+CalmacTestValues.getLegCode(rowId)+"' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  AND ir.ENDDATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AND ms.STATENAME IN('OPEN SAILING') AND ir.SAILINGCODE  NOT LIKE  '%0250'");
		oracleDbUtils
				.executeCalmacQuery("SELECT SOLDCAPACITY FROM MSP_PHRESOURCETYPECAPACITIES mp WHERE SAILINGCODE = '"
						+ departureSailingCode + "'AND PHYSICALRESOURCETYPE ='PET'");

//		oracleDbUtils.executeCalmacQuery(CalmacTestValues.readCellForXML.getCellData("BOOKING_DETAIL", "FIND_RESOURCE_AVAILABILITY", rowId));

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
//		    	BigDecimal columnValue = (BigDecimal) resultSet.getObject(1);
				String columnValue = resultSet.getString(1);
				// Convert the string to BigDecimal if needed
				BigDecimal decimalValue = new BigDecimal(columnValue);

				CalmacTestValues.soldPetCapacityAfterBooking = decimalValue.toString();

				// Do something with the value, such as printing
				System.out.println("Sold Capacity row: " + CalmacTestValues.soldPetCapacityAfterBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	public CalmacBookingCreationPage verifyPetCapacityAfterSold(int booking) throws InterruptedException, AWTException, IOException,
			SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		Assert.assertEquals(Integer.parseInt(CalmacTestValues.soldPetCapacityBeforeBooking)+booking, Integer.parseInt(CalmacTestValues.soldPetCapacityAfterBooking));
		return this;

	}

	public CalmacBookingCreationPage VerifyPassengerSoldCapacity(int booking) throws InterruptedException, AWTException, IOException,
			SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

//		Assert.assertEquals(Integer.parseInt(CalmacTestValues.soldPassengerCapacityBeforeBooking)+booking, Integer.parseInt(CalmacTestValues.soldPassengerCapacityAfterBooking));
		return this;

	}

	public CalmacBookingCreationPage findAvailableCapacityAfterBooking(int rowId, String Sheetname)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		oracleDbUtils.executeCalmacQuery("SELECT RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY WHERE SAILINGCODE ='"+departureSailingCode+"'  AND PRODUCTCODE ='ADL'");

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
				BigDecimal columnValue = (BigDecimal) resultSet.getObject(1);
				CalmacTestValues.soldPassengerCapacityAfterBooking = columnValue.toString();

				// Do something with the value, such as printing
				System.out.println("Available Capacity row After: " + CalmacTestValues.soldPassengerCapacityAfterBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

//		Assert.assertEquals(Integer.parseInt(CalmacTestValues.availableCapacityBeforeBooking)-2, Integer.parseInt(CalmacTestValues.availableCapacityAfterBooking));
//		Assert.assertEquals(CalmacTestValues.availableCapacityBeforeBooking, CalmacTestValues.availableCapacityAfterBooking-2);
		return this;
	}

	public CalmacBookingCreationPage addCompSptFerryCard() {

		BP.click(BP.getLongWaitTime(), continueButton);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), continueButton);
		BP.shortWait();
		BP.click(BP.getLongWaitTime(), choosePassengerToAdd);
		BP.mediumWait();
		BP.javaScriptClick(addCompSptFerryCompanyButton);

		BP.shortWait();
		BP.javaScriptClick(doneButton);
		BP.shortWait();
		return this;
	}



	public CalmacBookingCreationPage addMultiplePets(String Dog, String PetInCar, String PetInPassengerLounge) {

		BP.click(BP.getLongWaitTime(), choosePetsToAdd);
		BP.shortWait();
        int dogCount = Integer.parseInt(Dog);
		if (dogCount>0) {
			for(int count=1; count<=dogCount; count++) {
				BP.click(BP.getLongWaitTime(), addAssistanceDog);
				BP.shortWait();
			}
		}
		
        int petCount = Integer.parseInt(PetInCar);
		if (petCount>0) {
			for(int count=1; count<=petCount; count++) {
				BP.click(BP.getLongWaitTime(), addPetRemainInCar);
				BP.shortWait();
			}
		}
		
		  int petInLoungeCount = Integer.parseInt(PetInPassengerLounge);
			if (petInLoungeCount>0) {
				for(int count=1; count<=petInLoungeCount; count++) {
					BP.click(BP.getLongWaitTime(), addPetInPassengerLounge);
					BP.shortWait();
				}
			}

		BP.shortWait();
		BP.javaScriptClick(doneButton);
		return this;
	}

	public CalmacBookingCreationPage addVehiclesAndBycycle(String vehicleType) {

		BP.click(BP.getLongWaitTime(), chooseVehicleAndBycyleToAdd);
		BP.shortWait();
		BP.javaScriptClick(showMoreButton);
		BP.javaScriptClick(addBlueBadgeMcycleButton);

		BP.javaScriptScrollToView(
				"(//ion-label[contains(text(), 'Are you towing any vehicles?')]/parent::form/div/ion-radio-group)[1]/ion-item[2]");
		BP.javaScriptClick(
				"(//ion-label[contains(text(), 'Are you towing any vehicles?')]/parent::form/div/ion-radio-group)[1]/ion-item[2]");
		BP.javaScriptScrollToView(
				"(//*[contains(text(), 'Does this vehicle have a bike rack?')]/parent::form/div/ion-radio-group)[2]/ion-item[2]");

		BP.javaScriptClick(
				"(//*[contains(text(), 'Does this vehicle have a bike rack?')]/parent::form/div/ion-radio-group)[2]/ion-item[2]");
		BP.javaScriptScrollToView(
				"//*[contains(text(), 'Is this vehicle UK registered?')]/parent::div/ion-radio-group/ion-item[2]/ion-radio");

		BP.javaScriptClick(
				"//*[contains(text(), 'Is this vehicle UK registered?')]/parent::div/ion-radio-group/ion-item[2]/ion-radio");

		BP.javaScriptClick("//ion-checkbox");
		BP.javaScriptClick(
				"//*[@id=\"multistep-dialog\"]/div[2]/app-vehicle-product-form/div/div[2]/div[2]/div[2]/div/ion-button");
		BP.shortWait();
		return this;
	}

	public CalmacBookingCreationPage addVehiclesAndBycycles(String vehicleType) {

		BP.click(BP.getLongWaitTime(), chooseVehicleAndBycyleToAdd);
		BP.shortWait();
		BP.javaScriptClick(showMoreButton);
		BP.javaScriptClick("//div[contains(text(), '" + vehicleType
				+ "')]/ancestor::edea-product/descendant::ion-button[contains(@data-test-automation, 'button.add')]");

		if (vehicleType.equals("Car")) {
			BP.setValue("DE23 HLF", "//ion-input[contains(@formcontrolname, 'regNumber')]/input");
			BP.javaScriptClick(findVehicleButton);

		}
		
		 if (vehicleType.equals("Blue Badge MCycle")) {
			BP.javaScriptClick(addBlueBadgeMcycleButton);

		}
		

		BP.javaScriptScrollToView(
				"(//ion-label[contains(text(), 'Are you towing any vehicles?')]/parent::form/div/ion-radio-group)[1]/ion-item[2]");
		BP.javaScriptClick(
				"(//ion-label[contains(text(), 'Are you towing any vehicles?')]/parent::form/div/ion-radio-group)[1]/ion-item[2]");
		BP.javaScriptScrollToView(
				"(//*[contains(text(), 'Does this vehicle have a bike rack?')]/parent::form/div/ion-radio-group)[2]/ion-item[2]");

		BP.javaScriptClick(
				"(//*[contains(text(), 'Does this vehicle have a bike rack?')]/parent::form/div/ion-radio-group)[2]/ion-item[2]");
		BP.javaScriptScrollToView(
				"//*[contains(text(), 'Is this vehicle UK registered?')]/parent::div/ion-radio-group/ion-item[2]/ion-radio");

		BP.javaScriptClick(
				"//*[contains(text(), 'Is this vehicle UK registered?')]/parent::div/ion-radio-group/ion-item[2]/ion-radio");
		BP.javaScriptScrollToView("//ion-checkbox");
		BP.javaScriptClick("//ion-checkbox");
		BP.shortWait();
		BP.javaScriptScrollToView(
				"//*[@id=\"multistep-dialog\"]/div[2]/app-vehicle-product-form/div/div[2]/div[2]/div[2]/div/ion-button");
		BP.longWait();
//		BP.moveToElementAndClickByActions(addvehicleButton);
		BP.javaScriptClick(addvehicleButton);

		BP.shortWait();
		return this;
	}


	public CalmacBookingCreationPage chooseSailingDateTime() {
		BP.shortWait();
		System.out.println("Iftikhar Sailing Day is"+ CalmacTestValues.sailingDayForDeparture);
//		BP.waitUntilElementToBeClickable("//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForDeparture + "')]");
//		BP.javaScriptScrollToView(null);
		BP.javaScriptScrollToView("//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForDeparture + "')]");
		BP.shortWait();

		String attr=BP.getAttributeValue("class", "//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForDeparture + "')]");
		System.out.println("Attribue value is Iftikhar"+ attr);
		if(!attr.contains("active")) {
			BP.moveToElementAndClickByActions("//div[contains(@data-test-automation, '" + CalmacTestValues.sailingDayForDeparture + "')]");			
		}
		BP.mediumWait();
		BP.javaScriptClick(selectSailingTime);
		BP.mediumWait();
		List<WebElement> li=BP.getDriver().findElements(By.xpath("//span[contains(text(), 'Available')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]"));

		if (li.size()>=1) {
		

			BP.javaScriptClick(
					"//span[contains(text(), 'Available')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");

		} else {
			BP.javaScriptClick("//span[contains(text(), '" + CalmacTestValues.sailingTimeForDeparture
					+ "')]/ancestor::edea-sailing-card/descendant::span[contains(@class, 'sailing-not-selected')]");		
		}
		BP.shortWait();
		BP.javaScriptClick(continueButton);

		BP.shortWait();
		BP.click(BP.getLongWaitTime(), continueButton);
		BP.longWait();
		
		return this;
	}
	public CalmacBookingCreationPage viewLeadPassenger() {

		BP.click(BP.getLongWaitTime(), choosePassengerToAdd);
		BP.longWait();
		BP.javaScriptClick(doneButton);
		BP.mediumWait();
		return this;
	}
	public CalmacBookingCreationPage goToCheckout() {


		BP.javaScriptClick(goToCheckout);
		BP.shortWait();
		return this;
	}


	public CalmacBookingCreationPage addCompSptFerryCardDetail(int rowId, String Sheetname) {
		BP.click(BP.getLongWaitTime(), addPassengerDetail);
		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_ASISSTANCE", rowId)
				+ "']/following-sibling::*");

		BP.click("//ion-radio-group[@formcontrolname='concessionCard']/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD", rowId)
				+ "']/following-sibling::*");
		BP.shortWait();
		if ("Yes".equals(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD", rowId))) {
			BP.javaScriptScrollToView("//input[contains(@data-test-automation, 'undefined.input.portlist')]");
			BP.mediumWait();
			BP.setValue(CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE_OF_CONCESSION", rowId),
					"//input[contains(@data-test-automation, 'undefined.input.portlist')]");
			BP.shortWait();

			BP.javaScriptClick("//*[contains(text(), '"
					+ CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE_OF_CONCESSION", rowId)
					+ "')][@class='edea-option-label']");
			BP.shortWait();
			BP.setValue(
					CalmacTestValues.readCellForXML.getCellData(Sheetname, "PASSENGER_CONCESSION_CARD_NUMBER", rowId),
					"//*[contains(@formcontrolname, 'concessionNumber')]/input");
		}
		BP.shortWait();
		BP.javaScriptClick(doneButton);
		BP.mediumWait();

		return this;
	}


	public CalmacBookingCreationPage acceptTermsAndCondition() {
//		BP.javaScriptClick(continueButton);
		BP.mediumWait();
		BP.javaScriptClick("(//ion-checkbox)[1]");
		BP.javaScriptClick("(//ion-checkbox)[2]");
		List<WebElement> li=BP.getDriver().findElements(By.xpath("//ion-button[contains(text(), 'Pay now')]"));
		if(li.size()==1) {
			BP.javaScriptClick("//ion-button[contains(text(), 'Pay now')]");
		}
		else {
			BP.javaScriptClick("//ion-button[contains(text(), 'Modify')]");

		}
		BP.mediumWait();
		return this;
	}

	public CalmacBookingCreationPage makePayment(int rowId, String Sheetname) {
		BP.longWait();
		BP.getDriver().switchTo().frame("wp-cl-custom-html-iframe");
		BP.setValue("4444333322221111", "//input[@id='cardNumber']");
		BP.setValue("A Test", "//input[@id='cardholderName']");
		BP.setValue("01", "//input[@id='expiryMonth']");
		BP.setValue("25", "//input[@id='expiryYear']");
		BP.setValue("123", "//input[@id='securityCode']");
		BP.click("//input[@type='submit']");

		return this;
	}
	public CalmacBookingCreationPage downloadFile() {

	BP.longWait();

	BP.javaScriptClick(downloadFile);
	BP.longWait();

//	File downloadDirectory = new File("C://Users//muhammad.iftikhar//Downloads");

	File downloadDirectory = new File(InvokedMethodListener.filePath.replaceAll("/", "\\\\"));

//	InvokedMethodListener.filePath = InvokedMethodListener.filePath.replaceAll("/", "\\\\"); // Replace "/" with "\\" to escape backslashes

//	File downloadDirectory = new File(InvokedMethodListener.filePath);

//	File downloadDirectory = new File(InvokedMethodListener.filePath);
	File[] files = downloadDirectory.listFiles();
	if (files != null && files.length > 0) {
		Arrays.sort(files, Comparator.comparingLong(File::lastModified));
		File latestFile = files[files.length - 1];
		lastDownloadedFileName = latestFile.getName();
		System.out.println("Last downloaded file: " + lastDownloadedFileName);
	} else {
		System.out.println("No files found in the download directory.");
	}

// 	String pdfUrl="file:///C:/Users/muhammad.iftikhar/Downloads/"+lastDownloadedFileName;
	System.out.println("Download Iftikhar" + downloadDirectory);
	String pdfUrl = downloadDirectory + "\\" + lastDownloadedFileName;

//	BP.getDriver().get(pdfUrl);

	BP.openNewTabWithURL(pdfUrl);
//	BP.switchToChildWindowByIndex(1);
	BP.mediumWait();

	try {
		URL url = new URL(pdfUrl);
		URLConnection connection = url.openConnection();
		PDDocument document = PDDocument.load(connection.getInputStream());
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		System.out.println(text);
		document.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

	 BookingRef = BP.getText(getBookingReference);

	BP.javaScriptClick(myBookings);
	BP.mediumWait();

//	Assert.assertTrue(BP.isDisplayed("//span[contains(text(), '" + BookingRef + "')]"));

	return this;
}
	
	public CalmacBookingCreationPage findSoldCapcityBeforeBookingForPassenger()
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

//		oracleDbUtils.executeCalmacQuery("SELECT ir.RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY ir , MSP_SAILINGSTRUCTUREDEPARTURE ms WHERE  ir.RESOURCEAVAILABILITY >='2' AND ir.PRODUCTCODE ='ADL' AND ir.SAILINGCODE LIKE '"+CalmacTestValues.getLegCode(rowId)+"%'AND ir.SAILINGCODE  > '"+CalmacTestValues.getLegCode(rowId)+"' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  AND ir.ENDDATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AND ms.STATENAME IN('OPEN SAILING') AND ir.SAILINGCODE  NOT LIKE  '%0250'");
		oracleDbUtils
				.executeCalmacQuery("SELECT SOLDCAPACITY FROM MSP_PHRESOURCETYPECAPACITIES mp WHERE SAILINGCODE = '"
						+ departureSailingCode + "'AND PHYSICALRESOURCETYPE ='PASSENGER'");

//		oracleDbUtils.executeCalmacQuery(CalmacTestValues.readCellForXML.getCellData("BOOKING_DETAIL", "FIND_RESOURCE_AVAILABILITY", rowId));

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
				// Retrieve the value from the first column of the first row
				String columnValue = resultSet.getString(1);
				// Convert the string to BigDecimal if needed
				BigDecimal decimalValue = new BigDecimal(columnValue);
				CalmacTestValues.soldPassengerCapacityBeforeBooking = decimalValue.toString();

				// Do something with the value, such as printing
				System.out.println("Sold Capacity Before Creating Booking: " + CalmacTestValues.soldPassengerCapacityBeforeBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	public CalmacBookingCreationPage getDateAndTimeForDeparture()
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {		
		departureSailingCode = CalmacTestValues.availableDepartureSailingCode;
		System.out.println("Departure Sailing Code is" + departureSailingCode);
																				
		CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14);
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
				+ CalmacTestValues.sailingDayForDeparture.substring(2);
					
		CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
		CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
				+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);
	return this;
	}
	
	public CalmacBookingCreationPage getDateAndTimeForArrival()
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {		
		arrivalSailingCode = CalmacTestValues.availableArrivalSailingCode;
		System.out.println("Departure Sailing Code is" + arrivalSailingCode);
		System.out.println("Arrival Sailing Code is" + arrivalSailingCode);
		CalmacTestValues.sailingDayForArrival = arrivalSailingCode.substring(10, 14); // Extracting characters at index
																				// 8 and 9
		CalmacTestValues.sailingDayForArrival = CalmacTestValues.sailingDayForArrival.substring(0, 2) + "-"
				+ CalmacTestValues.sailingDayForArrival.substring(2);
		CalmacTestValues.sailingTimeForArrival = arrivalSailingCode.substring(14, 18);
		CalmacTestValues.sailingTimeForArrival = CalmacTestValues.sailingTimeForArrival.substring(0, 2) + ":"
				+ CalmacTestValues.sailingTimeForArrival.substring(2);

	return this;
	}
		public CalmacBookingCreationPage findAvailableCapcityBeforeBookingForPassengerForDeparture(String sailingCode, String sheetname, int rowId)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {		
		departureSailingCode = sailingCode;
		System.out.println("Departure Sailing Code is" + departureSailingCode);
		CalmacTestValues.sailingDayForDepartureAPI = departureSailingCode.substring(8, 14); // Extracting characters at
																				
		CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14);
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
				+ CalmacTestValues.sailingDayForDeparture.substring(2);
				CalmacTestValues.sailingTimeForDepartureAPI = departureSailingCode.substring(14, 18);
					
		CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
		CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
				+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);
		
		Response postResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + calmacTestValues.token)
//                .body(CalmacTestValues.readCellForXML.getCellData("API", "PRICEAVAILABILITY", 2))
                .body(CalmacTestValues.capacity.replace("DEPARTURE_PORT_CODE", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId))
        				.replace("ARRIVAL_PORT_CODE", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId))
                		.replace("DateForDeparture", CalmacTestValues.sailingDayForDepartureAPI)
                		.replace("TimeForDeparture", CalmacTestValues.sailingTimeForDepartureAPI))
                .when()
                .post("https://testcfl.e-dea.it/EBookingRestServices/ebooking/booking/timetableweb") // replace with your actual endpoint for the POST request
                .then()
                .statusCode(200) // replace with the expected status code
                .extract()
                .response();
   	 	System.out.println("Response status code: " + postResponse.getStatusCode());
        System.out.println("Response body: " + postResponse.getBody().asString());
          
        JSONObject response = new JSONObject(postResponse.getBody().asString());

        
        
        // Extracting m_C004_Availability for PassengerTypeCode "ADL"
        String availability = null;
        JSONArray passengerDetailsResponse = response.getJSONArray("c_PAR_PassengerDetailsResponse");
       
        
          JSONObject passengerDetail = passengerDetailsResponse.getJSONObject(0);
            JSONArray passengerSet = passengerDetail.getJSONArray("m_PAXS_PassengerSet");
            for (int j = 0; j < passengerSet.length(); j++) {
                JSONObject passenger = passengerSet.getJSONObject(j);
                if (passenger.getString("m_U257_PassengerTypeCode").equals("ADL")) {
                    availability = passenger.getString("m_C004_Availability");
                    break;
                }
            }
              
        // Printing the extracted availability
        System.out.println("m_C004_Availability for ADL: " + availability);
      		return this;         

	}


	public CalmacBookingCreationPage findAvailableCapcityBeforeBookingForCar(String sailingCode, String sheetname, int rowId)
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		
		departureSailingCode = sailingCode;
		System.out.println("Departure Sailing Code is" + departureSailingCode);
		CalmacTestValues.sailingDayForDepartureAPI = departureSailingCode.substring(8, 14); // Extracting characters at
																				
		CalmacTestValues.sailingDayForDeparture = departureSailingCode.substring(10, 14);
				CalmacTestValues.sailingDayForDeparture = CalmacTestValues.sailingDayForDeparture.substring(0, 2) + "-"
				+ CalmacTestValues.sailingDayForDeparture.substring(2);
				CalmacTestValues.sailingTimeForDepartureAPI = departureSailingCode.substring(14, 18);
			
				
		CalmacTestValues.sailingTimeForDeparture = departureSailingCode.substring(14, 18);
		CalmacTestValues.sailingTimeForDeparture = CalmacTestValues.sailingTimeForDeparture.substring(0, 2)
				+ ":" + CalmacTestValues.sailingTimeForDeparture.substring(2);
		
		Response postResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + calmacTestValues.token)
//                .body(CalmacTestValues.readCellForXML.getCellData("API", "PRICEAVAILABILITY", 2))
                .body(CalmacTestValues.capacity.replace("DEPARTURE_PORT_CODE", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId))
        				.replace("ARRIVAL_PORT_CODE", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId))
                		.replace("DateForDeparture", CalmacTestValues.sailingDayForDepartureAPI)
                		.replace("TimeForDeparture", CalmacTestValues.sailingTimeForDepartureAPI))
                .when()
                .post("https://testcfl.e-dea.it/EBookingRestServices/ebooking/booking/timetableweb") // replace with your actual endpoint for the POST request
                .then()
                .statusCode(200) // replace with the expected status code
                .extract()
                .response();
   	 	System.out.println("Response status code: " + postResponse.getStatusCode());
        System.out.println("Response body: " + postResponse.getBody().asString());
        JSONObject jsonObject = new JSONObject(postResponse.getBody().asString());
        // Extract the c_VER_VehicleResponse array
        JSONArray vehicleResponseArray = jsonObject.getJSONArray("c_VER_VehicleResponse");

        // Create a JSON array to hold the BBCAR details
        JSONArray bbcarDetailsArray = new JSONArray();

        // Loop through each item in the vehicle response array
        for (int i = 0; i < vehicleResponseArray.length(); i++) {
            JSONObject vehicleDetails = vehicleResponseArray.getJSONObject(i).getJSONObject("m_VEHL_VehicleDetails");
            String vehicleTypeCode = vehicleDetails.getString("m_U294_VehicleTypeCode");

            // Check if the vehicle type code is BBCAR
            if (vehicleTypeCode.equals("BBCAR")) {
                // Add the BBCAR details to the bbcarDetailsArray
                bbcarDetailsArray.put(vehicleDetails);
            }
        }

        // Output the BBCAR details
        System.out.println(bbcarDetailsArray.toString(2)); // Pretty print with an indentation of 2
   
		return this;         

	}

	public CalmacBookingCreationPage findSoldCpacityAfterBookingForPassenger()
			throws InterruptedException, AWTException, IOException, SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

//		oracleDbUtils.executeCalmacQuery("SELECT ir.RESOURCEAVAILABILITY  FROM IBE_RESOURCEAVAILABILITY ir , MSP_SAILINGSTRUCTUREDEPARTURE ms WHERE  ir.RESOURCEAVAILABILITY >='2' AND ir.PRODUCTCODE ='ADL' AND ir.SAILINGCODE LIKE '"+CalmacTestValues.getLegCode(rowId)+"%'AND ir.SAILINGCODE  > '"+CalmacTestValues.getLegCode(rowId)+"' || TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  AND ir.ENDDATE > TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AND ms.STATENAME IN('OPEN SAILING') AND ir.SAILINGCODE  NOT LIKE  '%0250'");
		oracleDbUtils
				.executeCalmacQuery("SELECT SOLDCAPACITY FROM MSP_PHRESOURCETYPECAPACITIES mp WHERE SAILINGCODE = '"
						+ departureSailingCode + "'AND PHYSICALRESOURCETYPE ='PASSENGER'");

//		oracleDbUtils.executeCalmacQuery(CalmacTestValues.readCellForXML.getCellData("BOOKING_DETAIL", "FIND_RESOURCE_AVAILABILITY", rowId));

		try {
			ResultSet resultSet = OracleDbUtils.resultSet;

			// Check if there are rows in the result set
			if (resultSet.next()) {
				// Retrieve the value from the first column of the first row
//		    	BigDecimal columnValue = (BigDecimal) resultSet.getObject(1);
				String columnValue = resultSet.getString(1);
				// Convert the string to BigDecimal if needed
				BigDecimal decimalValue = new BigDecimal(columnValue);

				CalmacTestValues.soldPassengerCapacityAfterBooking = decimalValue.toString();

				// Do something with the value, such as printing
				System.out.println("Sold Capacity After Creating Booking: " + CalmacTestValues.soldPassengerCapacityAfterBooking);
			} else {
				// Handle the case where the result set is empty
				System.out.println("Result set is empty");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	public CalmacBookingCreationPage journeyBooking(int rowId, String Sheetname) {
		BP.javaScriptClick(journeyButton);
		BP.mediumWait();
		BP.javaScriptClick("//p[contains(text(), '"+CalmacTestValues.readCellForXML.getCellData(Sheetname, "Route From", rowId)+"')]/parent::div/following-sibling::div/p[contains(text(), '"+CalmacTestValues.readCellForXML.getCellData(Sheetname, "Route To", rowId)+"')]/ancestor::div[contains(@class, 'journey-port-cards')]");
		BP.mediumWait();
		BP.javaScriptClick("//div[contains(text(), '"+CalmacTestValues.readCellForXML.getCellData(Sheetname, "Booking Code", rowId)+"')]/ancestor::div[contains(@class, 'step2-choose-product')]");

		return this;
	}

////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////

public CalmacBookingCreationPage openMyJourney() {
	BP.shortWait();
	BP.click("//ion-item[contains(@data-test-automation, '.edea-side-menu..item.lbl.myJourneys')]");
	return this;
}

public CalmacBookingCreationPage getBokingReference() {
	BP.shortWait();
	 BookingRef = BP.getText(getBookingReference);
return this;
}


public CalmacBookingCreationPage modifyBooking() {
	BP.shortWait();
	
	BP.javaScriptScrollToView(
			"//span[contains(text(), '"+BookingRef+"')]/ancestor::div[@class='edea-journey-reference']/descendant::ion-button[contains(@data-test-automation, 'home/my-journeys.edea-journey-reference.undefined.button.edit')]");
	BP.shortWait();

	BP.javaScriptClick(
			"//span[contains(text(), '"+BookingRef+"')]/ancestor::div[@class='edea-journey-reference']/descendant::ion-button[contains(@data-test-automation, 'home/my-journeys.edea-journey-reference.undefined.button.edit')]");

	return this;
}
@SuppressWarnings("rawtypes")
public CalmacBookingCreationPage placeUSBankOrder(String URI, Object xml, HashMap headers) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, InterruptedException {
	CalmacTestValues.response = restAssured.post(URI, xml, headers);
//	CalmacTestValues.OrderloanNumber =CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.LoanNumber");
//	CalmacTestValues.docID =CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.DocId");
//	CalmacTestValues.folderID =CalmacTestValues.response.xmlPath().getString("CreateOrderRs.Tracking.FolderId");
    return this;
}
public CalmacBookingCreationPage getAvailableSailingCodeForDeparture(String sheetname, String depPort, String arrivalPort, int rowId, List<String> products) throws Exception {
	System.out.println(calmacTestValues.priceAvailabilityPayload().replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId)).replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId)));
	
//	for (String product : products) {
//        // Example operation with each product
//        System.out.println("Processing product: " + product);
//        calmacTestValues.priceAvailabilityPayload().replace("", 0)
//    }

	/*
	 ObjectMapper objectMapper = new ObjectMapper();
     ObjectNode rootNode = (ObjectNode) objectMapper.readTree(calmacTestValues.priceAvailabilityPayload());

     // Create a new passenger object
     ObjectNode newPassenger = objectMapper.createObjectNode();
//     newPassenger.put("m_U257_PassengerTypeCode", "INF");
     
     newPassenger.put("passengerIndex", "0");
     newPassenger.put("m_U257_PassengerTypeCode", "INF");
     newPassenger.put("m_U258_NumberOfPassengers", "1");
     // Get the array of passenger detail requests
     ArrayNode passengerDetailRequests = (ArrayNode) rootNode.get("c_PAQ_PassengerDetailRequest");

     // Append the new passenger to the array
     ObjectNode passengerRequest = (ObjectNode) passengerDetailRequests.get(0);
     ArrayNode passengerSet = (ArrayNode) passengerRequest.get("m_PAXS_PassengerSet");
     passengerSet.add(newPassenger);

     // Convert the updated JSON object back to string
     String updatedPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

     // Print the updated payload
     System.out.println(updatedPayload);
*/

	Response postResponse = given()
             .contentType(ContentType.JSON)
             .header("Authorization", "Bearer " + CalmacTestValues.token)
//             .body(calmacTestValues.priceAvailabilityPayload()
//            		 .replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId))
//            		 .replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId)))
             .body(calmacTestValues.priceAvailabilityPayload()
            		 .replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, depPort, rowId))
            		 .replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, arrivalPort, rowId)))

             .when()
             .post("https://testcfl.e-dea.it/EBookingRestServices/ebooking/booking/priceAvailability") // replace with your actual endpoint for the POST request
             .then()
             .statusCode(200) // replace with the expected status code
             .extract()
             .response();
	 System.out.println("Response status code: " + postResponse.getStatusCode());
     System.out.println("Response body: " + postResponse.getBody().asString());
             
     JSONObject jsonObject = new JSONObject(postResponse.getBody().asString());
     JSONArray sailingPrices = jsonObject.getJSONArray("sailingprice");         

     for (int i = 0; i < sailingPrices.length(); i++) {
         JSONObject sailingPrice = sailingPrices.getJSONObject(i);
         JSONObject routeDateTimeResponse = sailingPrice.getJSONObject("c_RDR_RouteDateTimeResponse");
         JSONObject travelRoute = routeDateTimeResponse.getJSONObject("m_ROUT_TravelRoute");

         System.out.println("Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));

         boolean isAvailable = travelRoute.getBoolean("c_C081_IsAvailable");
         JSONObject sailingPriceObj = sailingPrices.getJSONObject(i);
         JSONObject routeDateTimeResponseObj = sailingPriceObj.getJSONObject("c_RDR_RouteDateTimeResponse");
         
         JSONArray sailingLegsArray = routeDateTimeResponseObj.getJSONObject("m_LEG_SailingLegs").getJSONArray("m_LEG_SailingLeg");

         JSONObject sailingLegObj = sailingLegsArray.getJSONObject(0);
         checkinStatus = sailingLegObj.getString("c_C391_checkInStatus");
         System.out.println("Operating Status: " + checkinStatus);
         boolean isDummyShip = routeDateTimeResponse.getJSONObject("c_SHNM_ShipName").getBoolean("c_C316_DummyShip");

         
         /*
         for (int j = 0; j < sailingLegsArray.length(); j++) {
             JSONObject sailingLegObj = sailingLegsArray.getJSONObject(j);
             checkinStatus = sailingLegObj.getString("c_C391_checkInStatus");
             System.out.println("Operating Status: " + checkinStatus);
         }
        */ 
         
         
         	System.out.println("Is Available: " + isAvailable);
         if(isAvailable==true && checkinStatus.contains("OPEN SAILING") && isDummyShip==false) {
        	 CalmacTestValues.availableDepartureSailingCode=travelRoute.getString("c_C276_SailingCode");
        	 System.out.println("Valid Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));
             
             break;

         }
         
     }
 	return this;

}

public CalmacBookingCreationPage getAvailableSailingCodeForArrival(String sheetname, String depPort, String arrivalPort, int rowId, List<String> products) throws Exception {
	System.out.println(calmacTestValues.priceAvailabilityPayload().replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId)).replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId)));
	
//	for (String product : products) {
//        // Example operation with each product
//        System.out.println("Processing product: " + product);
//        calmacTestValues.priceAvailabilityPayload().replace("", 0)
//    }
	/*
	 ObjectMapper objectMapper = new ObjectMapper();
     ObjectNode rootNode = (ObjectNode) objectMapper.readTree(calmacTestValues.priceAvailabilityPayload());

     // Create a new passenger object
     ObjectNode newPassenger = objectMapper.createObjectNode();
     
     newPassenger.put("passengerIndex", "0");
     newPassenger.put("m_U257_PassengerTypeCode", "INF");
     newPassenger.put("m_U258_NumberOfPassengers", "1");
     // Get the array of passenger detail requests
     ArrayNode passengerDetailRequests = (ArrayNode) rootNode.get("c_PAQ_PassengerDetailRequest");

     // Append the new passenger to the array
     ObjectNode passengerRequest = (ObjectNode) passengerDetailRequests.get(0);
     ArrayNode passengerSet = (ArrayNode) passengerRequest.get("m_PAXS_PassengerSet");
     passengerSet.add(newPassenger);

     // Convert the updated JSON object back to string
     String updatedPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

     // Print the updated payload
     System.out.println(updatedPayload);

	*/
	Response postResponse = given()
             .contentType(ContentType.JSON)
             .header("Authorization", "Bearer " + CalmacTestValues.token)
//             .body(calmacTestValues.priceAvailabilityPayload()
//            		 .replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "DEPARTURE_PORT_CODE", rowId))
//            		 .replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, "ARRIVAL_PORT_CODE", rowId)))
             .body(calmacTestValues.priceAvailabilityPayload()
            		 .replace("DEPARTURE_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, depPort, rowId))
            		 .replace("ARRIVAL_PORT", CalmacTestValues.readCellForXML.getCellData(sheetname, arrivalPort, rowId)))

             .when()
             .post("https://testcfl.e-dea.it/EBookingRestServices/ebooking/booking/priceAvailability") // replace with your actual endpoint for the POST request
             .then()
             .statusCode(200) // replace with the expected status code
             .extract()
             .response();
	 System.out.println("Response status code: " + postResponse.getStatusCode());
     System.out.println("Response body: " + postResponse.getBody().asString());
             
     JSONObject jsonObject = new JSONObject(postResponse.getBody().asString());
     JSONArray sailingPrices = jsonObject.getJSONArray("sailingprice");         

     for (int i = 0; i < sailingPrices.length(); i++) {
         JSONObject sailingPrice = sailingPrices.getJSONObject(i);
         JSONObject routeDateTimeResponse = sailingPrice.getJSONObject("c_RDR_RouteDateTimeResponse");
         JSONObject travelRoute = routeDateTimeResponse.getJSONObject("m_ROUT_TravelRoute");

         System.out.println("Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));

         boolean isAvailable = travelRoute.getBoolean("c_C081_IsAvailable");
         JSONObject sailingPriceObj = sailingPrices.getJSONObject(i);
         JSONObject routeDateTimeResponseObj = sailingPriceObj.getJSONObject("c_RDR_RouteDateTimeResponse");
         
         JSONArray sailingLegsArray = routeDateTimeResponseObj.getJSONObject("m_LEG_SailingLegs").getJSONArray("m_LEG_SailingLeg");

         JSONObject sailingLegObj = sailingLegsArray.getJSONObject(0);
         checkinStatus = sailingLegObj.getString("c_C391_checkInStatus");
         operatingStatus = sailingLegObj.getString("c_C392_operatingStatus");

         System.out.println("Operating Status: " + checkinStatus);
         boolean isDummyShip=true;
               if(routeDateTimeResponse.getJSONObject("c_SHNM_ShipName").toString().contains("c_C316_DummyShip")){
                   isDummyShip = routeDateTimeResponse.getJSONObject("c_SHNM_ShipName").getBoolean("c_C316_DummyShip");
         }

         
         /*
         for (int j = 0; j < sailingLegsArray.length(); j++) {
             JSONObject sailingLegObj = sailingLegsArray.getJSONObject(j);
             checkinStatus = sailingLegObj.getString("c_C391_checkInStatus");
             System.out.println("Operating Status: " + checkinStatus);
         }
        */ 
         
         
         	System.out.println("Is Available: " + isAvailable);
         if(isAvailable==true && checkinStatus.contains("OPEN SAILING") && isDummyShip==false) {
        	 CalmacTestValues.availableArrivalSailingCode=travelRoute.getString("c_C276_SailingCode");
        	 System.out.println("Valid Sailing Code is: " + travelRoute.getString("c_C276_SailingCode"));
             
             break;

         }
		

     }
     System.out.println("Arrival Sailing Code is" + CalmacTestValues.availableArrivalSailingCode);
		CalmacTestValues.sailingDayForArrival = CalmacTestValues.availableArrivalSailingCode.substring(10, 14); // Extracting characters at index
																				// 8 and 9
		CalmacTestValues.sailingDayForArrival = CalmacTestValues.sailingDayForArrival.substring(0, 2) + "-"
				+ CalmacTestValues.sailingDayForArrival.substring(2);
		CalmacTestValues.sailingTimeForArrival = CalmacTestValues.availableArrivalSailingCode.substring(14, 18);
		CalmacTestValues.sailingTimeForArrival = CalmacTestValues.sailingTimeForArrival.substring(0, 2) + ":"
				+ CalmacTestValues.sailingTimeForArrival.substring(2);
	



return this;
}
}