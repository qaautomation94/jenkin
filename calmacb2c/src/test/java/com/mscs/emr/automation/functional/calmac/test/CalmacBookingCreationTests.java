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
public class CalmacBookingCreationTests extends BaseTestPage {

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

	//	
	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007a() throws Exception {
//		calmacAccountCreationPage
//				.clickCreateAccountLink()
//				.enterAccountDetail(2, "CALMAC_REGISTRATION")
//				.setEmailInBookingDetail(2, "CALMAC_REGISTER_BOOKING")
//				.activateAccount()
//				.loginNewlyCreatedAccount(2, "CALMAC_REGISTER_BOOKING");
    	

		calmacAccountCreationPage
    	.loginB2B();
		restAssured.getAuthentication();
//		getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 2, products);
		
		calmacBookingCreationPage
//				.findAvailableSailingForDeparture(2, "CALMAC_REGISTER_BOOKING");
//			.validSailingForBooking(sailingCode)
//		.findSoldCapcityBeforeBookingForPassenger()		;
			.findAvailableCapcityBeforeBookingForPassengerForDeparture(CalmacTestValues.availableDepartureSailingCode, "CALMAC_REGISTER_BOOKING", 2)		
			.getDateAndTimeForDeparture()
	
			.oneWay()
				.selectDeparturePort(2, "CALMAC_REGISTER_BOOKING")
				.selectArrivalPort(2, "CALMAC_REGISTER_BOOKING")
				.continueBooking()
				.choosePassengerType(2, "CALMAC_REGISTER_BOOKING")
				.selectSailingDateTime()
				.chooseSailingDateTime()
				.addPassengerDetail(2, "CALMAC_REGISTER_BOOKING", "Infant")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(2, "CALMAC_REGISTER_BOOKING")
				.findSoldCpacityAfterBookingForPassenger()
				.findAvailableCapacityAfterBooking(2,"CALMAC_REGISTER_BOOKING")				
				.VerifyPassengerSoldCapacity(2)
				.downloadFile();

	}

	@TestCaseId(2)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007b() throws Exception {

//		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(3, "CALMAC_REGISTER_BOOKING")
//				.setEmailInBookingDetail(3, "CALMAC_REGISTER_BOOKING").activateAccount()
//				.loginNewlyCreatedAccount(3, "CALMAC_REGISTER_BOOKING");
		
    	calmacAccountCreationPage
    	.loginB2B();

		restAssured.getAuthentication();
		calmacBookingCreationPage.

		getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 3, products);
		
		calmacBookingCreationPage
		.findAvailableCapcityBeforeBookingForPassengerForDeparture(CalmacTestValues.availableDepartureSailingCode, "CALMAC_REGISTER_BOOKING", 3)		
		.findAvailableCapcityBeforeBookingForPassengerForDeparture(CalmacTestValues.availableDepartureSailingCode, "CALMAC_REGISTER_BOOKING", 3)		

//				.findAvailableSailingForDeparture(2, "CALMAC_REGISTER_BOOKING");
//			.validSailingForBooking(sailingCode)
//		.findSoldCapcityBeforeBookingForPassenger()		;
			.findAvailableCapcityBeforeBookingForPassengerForDeparture(CalmacTestValues.availableDepartureSailingCode, "CALMAC_REGISTER_BOOKING", 3)
			.getDateAndTimeForDeparture()
	
			.oneWay()
				.selectDeparturePort(3, "CALMAC_REGISTER_BOOKING")
				.selectArrivalPort(3, "CALMAC_REGISTER_BOOKING")
				.continueBooking()
				.choosePassengerType(3, "CALMAC_REGISTER_BOOKING")
//				.addCompSptFerryCard() replaced with passenger
//				.continueBooking()
				.addPetType(3, "CALMAC_REGISTER_BOOKING")
				.addVehiclesAndBycycles("Blue Badge Car")
				.selectSailingDateTime()
				.chooseSailingDateTime()
				.viewLeadPassenger()
				.addCompSptFerryCardDetail(3, "CALMAC_REGISTER_BOOKING")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(3, "CALMAC_REGISTER_BOOKING")
				.findSoldOfPetCpacityAfterBooking()
				.findSoldCpacityAfterBookingForPassenger()
//				.verifyPetCapacityAfterSold(1)
//				.VerifyPassengerSoldCapacity(2)
				.downloadFile();


	}
	@TestCaseId(3)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007c() throws Exception {

//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(4, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(4, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(4, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		restAssured.getAuthentication();
		calmacBookingCreationPage.

		getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 4, products);
		
		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.oneWay()
				.selectDeparturePort(4, "CALMAC_REGISTER_BOOKING")
				.selectArrivalPort(4, "CALMAC_REGISTER_BOOKING")
				.continueBooking()
				.choosePassengerType(4, "CALMAC_REGISTER_BOOKING")
				.selectSailingDateTime()
				.chooseSailingDateTime()
		
				.addPassengerAssistanceNo(4, "CALMAC_REGISTER_BOOKING")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(4, "CALMAC_REGISTER_BOOKING")
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(2)
				
				.downloadFile();


	}

	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007d() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(5, "CALMAC_REGISTRATION")
//    	.setEmailInBookingDetail(5, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(5, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		restAssured.getAuthentication();
		calmacBookingCreationPage.

		getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 5, products)
		.getAvailableSailingCodeForArrival("CALMAC_REGISTER_BOOKING", "RETURN_DEPARTURE_PORT_CODE", "RETURN_ARRIVAL_PORT_CODE", 5, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()		
				.returnBooking()
				.selectDeparturePort(5, "CALMAC_REGISTER_BOOKING")
				.selectArrivalPort(5, "CALMAC_REGISTER_BOOKING")
				.continueBooking()
//				.selectSailingDateTime()
				.choosePassengerType(5, "CALMAC_REGISTER_BOOKING")
				.addPetType(5, "CALMAC_REGISTER_BOOKING")
		//		.addVehiclesAndBycycles("Car") CAR is not availabl for this route
				
				
				.chooseSailingDateTimeForReturn(5, "CALMAC_REGISTER_BOOKING")
				.addPassengerDetail(5, "CALMAC_REGISTER_BOOKING", "Child")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(5, "CALMAC_REGISTER_BOOKING")
				
//				.findAvailableCapacityAfterBooking(2,"CALMAC_REGISTER_BOOKING");	
				
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(2)
				.downloadFile();


	}

	@TestCaseId(5)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007e() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(5, "CALMAC_REGISTRATION")
//    	.setEmailInBookingDetail(5, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(5, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		restAssured.getAuthentication();
		calmacBookingCreationPage
		.getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 6, products)
		.getAvailableSailingCodeForArrival("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 6, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()	
//		calmacBookingCreationPage
//		.findAvailableSailingForDeparture(6, "CALMAC_REGISTER_BOOKING")
//		.findSoldCapcityBeforeBookingForPassenger()
//				.findAvailableSailingForArrival(6, "CALMAC_REGISTER_BOOKING")
				.returnBooking()
				.selectDeparturePort(6, "CALMAC_REGISTER_BOOKING")
				.selectArrivalPort(6, "CALMAC_REGISTER_BOOKING")
				.continueBooking()
				.choosePassengerType(6, "CALMAC_REGISTER_BOOKING")
				.removePassengerType(6, "CALMAC_REGISTER_BOOKING")

				.addPetType(6, "CALMAC_REGISTER_BOOKING")
				.addVehiclesAndBycycles("Motorhome") // Not available for this route
				.chooseSailingDateTimeForReturn(6, "CALMAC_REGISTER_BOOKING")
				
//				.viewLeadPassenger()
				
				
				.addCompSptFerryCardDetail(6, "CALMAC_REGISTER_BOOKING");
//				.continueAfterAddingProduct()
//				.acceptTermsAndCondition()
//				.makePayment(6, "CALMAC_REGISTER_BOOKING")
				
				
//				.findSoldCpacityAfterBookingForPassenger()
//				.VerifyPassengerSoldCapacity(1)
				
				
//				.downloadFile();

//    	.findAvailableCapacityAfterBooking(2,"CALMAC_REGISTER_BOOKING");

	}

	@TestCaseId(6)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007f() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(5, "CALMAC_REGISTRATION")
//    	.setEmailInBookingDetail(5, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(5, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		restAssured.getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForDeparture("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 7, products)
		.getAvailableSailingCodeForArrival("CALMAC_REGISTER_BOOKING", "DEPARTURE_PORT_CODE", "ARRIVAL_PORT_CODE", 7, products);

		calmacBookingCreationPage
		.getDateAndTimeForDeparture()
		.getDateAndTimeForArrival()	
//		calmacBookingCreationPage
//		.findAvailableSailingForDeparture(7, "CALMAC_REGISTER_BOOKING")
//		.findSoldCapcityBeforeBookingForPassenger()
//		.findAvailableSailingForArrival(7, "CALMAC_REGISTER_BOOKING")
		.returnBooking()
		.selectDeparturePort(7, "CALMAC_REGISTER_BOOKING")
		.selectArrivalPort(7, "CALMAC_REGISTER_BOOKING")
		.continueBooking()
		.choosePassengerType(7, "CALMAC_REGISTER_BOOKING")
		.chooseSailingDateTimeForReturn(7, "CALMAC_REGISTER_BOOKING")
		.addPassengerAssistanceNo(7, "CALMAC_REGISTER_BOOKING")
		.continueAfterAddingProduct()
		.acceptTermsAndCondition()
		.makePayment(7, "CALMAC_REGISTER_BOOKING")
//		.findSoldCpacityAfterBookingForPassenger()
//		.VerifyPassengerSoldCapacity(2)
		.downloadFile();

	}

	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007g() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		restAssured.getAuthentication();
		calmacBookingCreationPage.
		getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE1", "MULTI_ARRIVAL_PORT_CODE1", 8, 1)
		
		
//		calmacBookingCreationPage
//		.findAvailableSailingForDeparture(8, "CALMAC_REGISTER_BOOKING")
//		getAvailableSailingCodeForMultiDeparture
//		.findMultiLegAvailableSailing(8, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE1", 1)

//		.findSoldCapcityBeforeBookingForPassenger()
//		.findAvailableSailingForArrival(8, "CALMAC_REGISTER_BOOKING")
		.multiLegBooking()

//		.continueBooking(8, "CALMAC_REGISTER_BOOKING")
//		.choosePassengerType(8, "CALMAC_REGISTER_BOOKING")
		.addMultiplePets("3", "2", "1")
		.addVehiclesAndBycycles("Car") // PRODUCT CAR IS NOT available for this route
		.continueAfterAddingProduct()
//		
		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT1", 8)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT1", 8)
		.continueBooking()
		.chooseSailingDateTime()
		
		.addJourney(true)
//		.findMultiLegAvailableSailing(8, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE2", 2)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE2", "MULTI_ARRIVAL_PORT_CODE2", 8, 2)

		
		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT2", 8)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT2", 8)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(true)
	//	.findMultiLegAvailableSailing(8, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE3", 3)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE3", "MULTI_ARRIVAL_PORT_CODE3", 8, 3)

		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT3", 8)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT3", 8)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(false)
		.viewLeadPassenger()
		.goToCheckout()
		.acceptTermsAndCondition()
		.makePayment(8, "CALMAC_REGISTER_BOOKING")
		.findSoldCpacityAfterBookingForPassenger()
		.VerifyPassengerSoldCapacity(2);
//		.chooseSailingDateTimeForReturn(7, "CALMAC_REGISTER_BOOKING");
//		.addInfant(7, "CALMAC_REGISTER_BOOKING", "Child")
		/*	.addPassenger(7, "CALMAC_REGISTER_BOOKING")
		.makePayment(7, "CALMAC_REGISTER_BOOKING")
		.findSoldCpacityAfterBookingForPassenger()
//		.VerifyPassengerSoldCapacity(2)
		.downloadFile();
*/
	}
	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007h() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		restAssured.getAuthentication();
		calmacBookingCreationPage
//		.findAvailableSailingForDeparture(8, "CALMAC_REGISTER_BOOKING")
//		.findMultiLegAvailableSailing(9, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE1", 1)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE1", "MULTI_ARRIVAL_PORT_CODE1", 9, 1)


//		.findSoldCapcityBeforeBookingForPassenger()
//		.findAvailableSailingForArrival(8, "CALMAC_REGISTER_BOOKING")
		.multiLegBooking()

//		.continueBooking(8, "CALMAC_REGISTER_BOOKING")
//		.choosePassengerType(8, "CALMAC_REGISTER_BOOKING")
		.choosePassengerType(9, "CALMAC_REGISTER_BOOKING")

//		.addMultiplePets("3", "2", "1")
//		.addVehiclesAndBycycles("Car")
		.continueAfterAddingProduct()
//		
		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT1", 9)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT1", 9)
		.continueBooking()
		.chooseSailingDateTime()
		
		.addJourney(true)
//		.findMultiLegAvailableSailing(9, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE2", 2)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE2", "MULTI_ARRIVAL_PORT_CODE2", 9, 2)

		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT2", 9)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT2", 9)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(true)
//		.findMultiLegAvailableSailing(9, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE3", 3)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE3", "MULTI_ARRIVAL_PORT_CODE3", 9, 3)

		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT3", 9)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT3", 9)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(false)
		.viewLeadPassenger()
		.addCompSptFerryCardDetail(9, "CALMAC_REGISTER_BOOKING")
		.goToCheckout()
		.acceptTermsAndCondition()
		.makePayment(8, "CALMAC_REGISTER_BOOKING")
//		.findSoldCpacityAfterBookingForPassenger()
//		.VerifyPassengerSoldCapacity(2);
		.downloadFile();
		
	}
	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void createBooking_REG_06_007i() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		restAssured.getAuthentication();
		calmacBookingCreationPage
//		.findAvailableSailingForDeparture(8, "CALMAC_REGISTER_BOOKING")
//		.findMultiLegAvailableSailing(10, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE1", 1)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE1", "MULTI_ARRIVAL_PORT_CODE1", 10, 1)

		.findSoldCapcityBeforeBookingForPassenger()
//		.findAvailableSailingForArrival(8, "CALMAC_REGISTER_BOOKING")
		.multiLegBooking()

//		.addMultiplePets("3", "2", "1")
//		.addVehiclesAndBycycles("Car")
		.choosePassengerType(10, "CALMAC_REGISTER_BOOKING")

//		.addInfant(10, "CALMAC_REGISTER_BOOKING", "Infant")

		.continueAfterAddingProduct()
//		
		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT1", 10)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT1", 10)
		.continueBooking()
		.chooseSailingDateTime()
		
		.addJourney(true)
//		.findMultiLegAvailableSailing(10, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE2", 2)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE2", "MULTI_ARRIVAL_PORT_CODE2", 10, 2)

		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT2", 10)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT2", 10)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(true)
//		.findMultiLegAvailableSailing(10, "CALMAC_REGISTER_BOOKING", "MULTI_LEG_CODE3", 3)
		.getAvailableSailingCodeForMultiDeparture("CALMAC_REGISTER_BOOKING", "MULTI_DEPT_PORT_CODE3", "MULTI_ARRIVAL_PORT_CODE3", 10, 3)

		.selectMultiLegDeparturePort("CALMAC_REGISTER_BOOKING", "MULTI_DEPARTURE_PORT3", 10)
		.selectMultiLegArrivalPort("CALMAC_REGISTER_BOOKING", "MULTI_ARRIVAL_PORT3", 10)
		.continueBooking()
		.chooseSailingDateTime()
		.addJourney(false)
		.viewLeadPassenger()
		.addPassengerDetail(10, "CALMAC_REGISTER_BOOKING", "Infant")
		.goToCheckout()
		.acceptTermsAndCondition()
		.makePayment(8, "CALMAC_REGISTER_BOOKING")
		.findSoldCpacityAfterBookingForPassenger()
		.VerifyPassengerSoldCapacity(2)
		.downloadFile();

	}

	
	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void journey_REG_06_007j() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		calmacBookingCreationPage
		.journeyBooking(2, "10Journey")
		.acceptTermsAndCondition()
		.makePayment(7, "CALMAC_REGISTER_BOOKING");

	}

	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void journey_REG_06_007k() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		calmacBookingCreationPage
		.journeyBooking(3, "10Journey")
		.acceptTermsAndCondition()
		.makePayment(7, "CALMAC_REGISTER_BOOKING");

	}

	@TestCaseId(7)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void journey_REG_06_007l() throws Exception {
//    	calmacAccountCreationPage
//    	.clickCreateAccountLink()
//    	.enterAccountDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.setEmailInBookingDetail(8, "CALMAC_REGISTER_BOOKING")
//    	.activateAccount()
//    	.loginNewlyCreatedAccount(8, "CALMAC_REGISTER_BOOKING");
		
		calmacAccountCreationPage
		.loginB2B();
		
		calmacBookingCreationPage
		.journeyBooking(4, "10Journey")
		.acceptTermsAndCondition()
		.makePayment(7, "CALMAC_REGISTER_BOOKING");

	}

	
	
	
	////////////////////////////////////////////////////////

	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void modifynONApiRouteBooking_REG_06_008a() throws Exception {
		
//		calmacAccountCreationPage
//				.clickCreateAccountLink()
//				.enterAccountDetail(2, "CALMAC_REGISTRATION")
//				.setEmailInBookingDetail(2, "CALMAC_REGISTER_BOOKING")
//				.activateAccount()
//				.loginNewlyCreatedAccount(2, "CALMAC_REGISTER_BOOKING");
		
		
    	calmacAccountCreationPage
    	.loginB2B();
    	
		calmacBookingCreationPage
				.findAvailableSailingForDeparture(2, "MODIFY_BOOKING")
				.findSoldCapcityBeforeBookingForPassenger()
				.oneWay()
				.selectDeparturePort(2, "MODIFY_BOOKING")
				.selectArrivalPort(2, "MODIFY_BOOKING")
				.continueBooking()
//				.choosePassengerType(2, "CALMAC_REGISTER_BOOKING")
				.addVehiclesAndBycycles("Car") 
				.selectSailingDateTime()
				.chooseSailingDateTime()
//				.addPassengerDetail(2, "CALMAC_REGISTER_BOOKING", "Infant")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(2, "MODIFY_BOOKING")
//				.findSoldCpacityAfterBookingForPassenger()
				
//				.findAvailableCapacityAfterBooking(2,"CALMAC_REGISTER_BOOKING")
				
				.VerifyPassengerSoldCapacity(2)
				.downloadFile();
		
		calmacBookingCreationPage.openMyJourney()
		.modifyBooking()
		.continueBooking()
		.choosePassengerType(2, "MODIFY_BOOKING")
		.selectSailingDateTime()
		.selectSailingDateTime()
		.continueBooking()
		.addPassengerAssistanceNo(2, "MODIFY_BOOKING")
		.continueAfterAddingProduct()
		.acceptTermsAndCondition()
		.makePayment(2, "MODIFY_BOOKING");


		

	}

	
	
	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 1, description = "Creation of an Booking")
	public void modifyApiRouteBooking_REG_06_008b() throws Exception {
		
//		calmacAccountCreationPage
//				.clickCreateAccountLink()
//				.enterAccountDetail(2, "CALMAC_REGISTRATION")
//				.setEmailInBookingDetail(2, "CALMAC_REGISTER_BOOKING")
//				.activateAccount()
//				.loginNewlyCreatedAccount(2, "CALMAC_REGISTER_BOOKING");
		
		
    	calmacAccountCreationPage
    	.loginB2B();
    	
		calmacBookingCreationPage
				.findAvailableSailingForDeparture(3, "MODIFY_BOOKING")
				.findSoldCapcityBeforeBookingForPassenger()
				.oneWay()
				.selectDeparturePort(3, "MODIFY_BOOKING")
				.selectArrivalPort(3, "MODIFY_BOOKING")
				.continueBooking()
//				.choosePassengerType(2, "CALMAC_REGISTER_BOOKING")
				.selectSailingDateTime()
				.chooseSailingDateTime()
//				.addPassengerDetail(2, "CALMAC_REGISTER_BOOKING", "Infant")
				.continueAfterAddingProduct()
				.acceptTermsAndCondition()
				.makePayment(3, "MODIFY_BOOKING")
				.findSoldCpacityAfterBookingForPassenger()
				
//				.findAvailableCapacityAfterBooking(2,"CALMAC_REGISTER_BOOKING")
				
				.VerifyPassengerSoldCapacity(3)
				.downloadFile();
		
		calmacBookingCreationPage.openMyJourney()
		.modifyBooking()
		
		.continueBooking()
		.choosePassengerType(3, "MODIFY_BOOKING")
		.selectSailingDateTime()
		.selectSailingDateTime()
		.continueBooking()
		.addPassengerDetail(3, "MODIFY_BOOKING", "Child")
		.continueAfterAddingProduct()
		.acceptTermsAndCondition()
		.makePayment(3, "MODIFY_BOOKING");



	}

}