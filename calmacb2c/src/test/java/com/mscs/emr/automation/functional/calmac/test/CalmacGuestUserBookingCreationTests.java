package com.mscs.emr.automation.functional.calmac.test;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.functional.TestCaseId;
import com.mscs.emr.automation.functional.TestGroup;
import com.mscs.emr.automation.functional.calmac.pages.core.CalmacAccountCreationPage;
import com.mscs.emr.automation.functional.calmac.pages.core.CalmacBookingCreationPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartApplicationDirectSalesPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartBoardingPage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author muhammad.iftikhar
 *
 */
public class CalmacGuestUserBookingCreationTests extends BaseTestPage {

	public static RequestSpecification request;
	public static JSONObject json;
	public static Response resp;
	String checkinStatus;
    List<String> products = Arrays.asList("prod1", "prod2", "prod3");

	CalmacBookingCreationPage calmacBookingCreationPage = new CalmacBookingCreationPage();
	CalmacAccountCreationPage calmacAccountCreationPage = new CalmacAccountCreationPage();
	BaseTestPage BP = new BaseTestPage();
	String transID = CalmacTestValues.transactionId;
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	SmartBoardingPage smartBoardingPage = new SmartBoardingPage();
	SmartApplicationDirectSalesPage smartApplicationDirectSalesPage = new SmartApplicationDirectSalesPage();
	RESTAssured restAssured = new RESTAssured();
//	public void getAuthentication() throws Exception {
//		RestAssured.baseURI = "https://testcfl.e-dea.it/EBookingRestServices/ebooking/authentication/login"; // replace with your actual endpoint
//        String jsonBody = CalmacTestValues.readCellForXML.getCellData("API", "LOGIN", 2);
//	
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(jsonBody)
//                .when()
//                .post(RestAssured.baseURI) // replace with your actual login endpoint
//                .then()
//                .extract()
//                .response();
//
//        // Print the response
//        System.out.println("Response status code: " + response.getStatusCode());
//        System.out.println("Response body: " + response.getBody().asString());
//        calmacTestValues.token = response.jsonPath().getString("token");
//    	System.out.println("TOKEN IS "+calmacTestValues.token);
//
//		
//	}

	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009a() throws Exception {
		
    	calmacAccountCreationPage
    	.guestLink();
    	restAssured.getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 2, products)
		.getDateAndTimeForDeparture()
				.oneWay()
				.selectDeparturePort(2, "Sheet1")
				.selectArrivalPort(2, "Sheet1")
				.continueBooking()
				.choosePassenger("Adult")
				.choosePassenger("Infant")
//				.addVehiclesAndBycycles("Blue Badge Car")
				.selectSailingDateTime()		
				.chooseSailingDateTime()
				.addGuestPassengerDetailS(2, "Sheet1", "Adult")
				.addGuestPassengerDetailS(2, "Sheet1", "Infant")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(2, "Sheet1")
				.downloadFile();
		
	}


	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009b() throws Exception {
// fields misisng and test case product different		
    	calmacAccountCreationPage
    	.guestLink();
    	
    	restAssured.getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 3, products)
		.getDateAndTimeForDeparture()
				.oneWay()
				.selectDeparturePort(3, "Sheet1")
				.selectArrivalPort(3, "Sheet1")
				.continueBooking()
				.choosePassenger("Adult")
				.addVehiclesAndBycycles("Blue Badge Car")
				.selectSailingDateTime()
				
				.chooseSailingDateTime()
				.addGuestPassengerDetailS(3, "Sheet1", "Adult")

				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(3, "Sheet1")
				.downloadFile();
		
	}


	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009c() throws Exception {
		
    	calmacAccountCreationPage
    	.guestLink();
    	
    	restAssured.getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 4, products)
		.getDateAndTimeForDeparture()
				.oneWay()
				.selectDeparturePort(4, "Sheet1")
				.selectArrivalPort(4, "Sheet1")
				.continueBooking()
				.choosePassenger("Adult")
				.choosePassenger("Adult")
				.choosePassenger("Child")
				.addPet("Pet in Pax Lounge")

				.selectSailingDateTime()
				.chooseSailingDateTime()
				.addGuestPassengerDetailS(4, "Sheet1", "Adult")
				.addPassengerAssistanceNo(4, "Sheet1")	
				.addPassengerAssistanceNo(4, "Sheet1")	
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(4, "Sheet1")
				.downloadFile();
		
	}
	
	

	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009d() throws Exception {

		calmacAccountCreationPage
		.guestLink();
		
		restAssured.getAuthentication();
		calmacBookingCreationPage
		.getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 5, products)
		.getAvailableSailingCodeForArrival("Sheet1", "RETURN_ARRIVAL_PORT_CODE", "RETURN_DEPARTURE_PORT_CODE", 5, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()	
				.returnBooking()
				.selectDeparturePort(5, "Sheet1")
				.selectArrivalPort(5, "Sheet1")
				.continueBooking()
				.choosePassenger("Adult")
				.choosePassenger("Child")
				.chooseSailingDateTimeForReturn(5, "Sheet1")				
				.addGuestPassengerDetailS(5, "Sheet1", "Adult")
				.addPassengerDetail(5, "Sheet1", "Child")

				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(5, "Sheet1");
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(2)
//				.downloadFile();

	}



	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009e() throws Exception {

		calmacAccountCreationPage
		.guestLink();
		
		
		restAssured.getAuthentication();
		calmacBookingCreationPage
		.getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 6, products)
		.getAvailableSailingCodeForArrival("Sheet1", "RETURN_ARRIVAL_PORT_CODE", "RETURN_DEPARTURE_PORT_CODE", 6, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()	
				.returnBooking()
				.selectDeparturePort(6, "Sheet1")
				.selectArrivalPort(6, "Sheet1")
				.continueBooking()
				.choosePassenger("Adult")
				.choosePassenger("Adult SPT Ferry Card")
//				.choosePassenger("Child")
//				Assistance Dog
				.addPet("Assistance Dog")
				.addVehiclesAndBycycles("Blue Badge Car")
				.chooseSailingDateTimeForReturn(6, "Sheet1")				
				.addGuestPassengerDetailS(6, "Sheet1", "Adult")
				.addCompSptFerryCardDetail(6, "Sheet1")
//				.addGuestPassengerDetailS(6, "Sheet1", "Child")

//				.addPassengerDetail(4, "Sheet1", "Child")
//
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(5, "Sheet1");
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(2)
//				.downloadFile();

	}

	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void guestreturnREG_06_009f() throws Exception {

		calmacAccountCreationPage
		.guestLink();
		
		
		restAssured.getAuthentication();
		calmacBookingCreationPage
		.getAvailableSailingCodeForDeparture("Sheet1", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 7, products)
		.getAvailableSailingCodeForArrival("Sheet1", "RETURN_ARRIVAL_PORT_CODE", "RETURN_DEPARTURE_PORT_CODE", 7, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()	
				.returnBooking()
				.selectDeparturePort(7, "Sheet1")
				.selectArrivalPort(7, "Sheet1")
				.continueBooking()
//				.choosePassenger("Adult")
				.choosePassenger("Adult SPT Ferry Card")
				.choosePassenger("Infant")


				.chooseSailingDateTimeForReturn(7, "Sheet1")				
				.addGuestPassengerDetailS(6, "Sheet1", "SPT Ferry Card")
				.addGuestPassengerDetailS(7, "Sheet1", "Infant")

				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(5, "Sheet1");
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(2)
//				.downloadFile();

	}

}