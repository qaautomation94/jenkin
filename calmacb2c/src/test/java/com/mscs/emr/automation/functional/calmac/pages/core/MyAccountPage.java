package com.mscs.emr.automation.functional.calmac.pages.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.formula.atp.AnalysisToolPak;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.RESTAssured;
import com.mscs.emr.automation.testData.MockDataUtils;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.ExcelReader;

public class MyAccountPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();

	String myAccountLink = "//ion-item[contains(@data-test-automation, 'myAccount')]";
	String personalDetailSection = "//p[contains(text(), 'Personal Details')]";
	String contactInformationSection = "//p[contains(text(), 'Contact information')]";
	String travelCompanionsSection = "//p[contains(text(), 'Travel Companions')]";
	String savedVehicleSection = "//p[contains(text(), 'Saved vehicles')]";
	String savedRouteSection = "//p[contains(text(), 'Saved route')]";
	String viewVehicleLink = "//*[contains(text(), 'View vehicle')]";
	String viewRouteLink = "//*[contains(text(), 'View route')]";

	String viewPersonalDetailLink = "//*[contains(text(), 'View personal details')]";
	String viewContactInformationLink = "//*[contains(text(), 'View contact information')]";
	String viewCompanionLink = "//*[contains(text(), 'View companions')]";
	String addCompanionButton = "//ion-button[contains(@data-test-automation, 'add-element')]";
	String addNewCompanion = "//div[contains(@class, 'add-new')]/ion-button";
	String addVehicleButton = "//ion-button[contains(@data-test-automation, 'acc-vehicles.edea-add')]";
	String addPrefferedVehicleButton = "//ion-button[contains(@class, 'lower-border-radius ion-color ion-color-primary md button button-solid ion-activatable ion-focusable hydrated')]";
	String registrationField = "//ion-input[contains(@formcontrolname, 'registrationNumber')]/input";
	String findVehicleButton = "//div[contains(@class, 'find-address')]/ion-button";
	String vehicleNotExist = "//span[contains(text(), 'The service is not available please insert manually the vehicle data')]";

	String searchVehicleType = "//input[contains(@data-test-automation, 'vehicles')]";
	String vehicleModel = "//ion-input[contains(@formcontrolname, 'model')]/input";
	String vehicleMake = "//ion-input[contains(@formcontrolname, 'make')]/input";
	String vehiclePrefferedCheckbox = "//ion-checkbox";
	String vehicleAddButton = "//ion-button[contains(@class, 'form-action-buttons lower-border-radius ion-color ion-color-primary md button button-solid ion-activatable ion-focusable hydrated')]";
	String vehicleAddSuccessMessage = "//span[contains(text(), 'The vehicle successfully added')]";
	String modifyVehicle = "//ion-button[contains(@data-test-automation, 'home/my-account/acc-vehicles.edea-counter.undefined.button.edit')]";
	String deleteVehiclebutton = "(//ion-button[contains(@class, 'ion-color ion-color-primary md button button-solid ion-activatable ion-focusable hydrated')])[3]";
	String deleteVehicleConfirm = "//span[contains(text(), 'Are you sure you want to delete this vehicle?')]";

	String modifyRouteButton = "//ion-button[contains(@data-test-automation, 'button.edit')]";
	String removeRouteButton = "//ion-button[contains(@class, 'ion-color ion-color-primary md button button-clear ion-activatable ion-focusable hydrated')]";
	String removeRouteConfirmButton = "(//ion-button[contains(@data-test-automation, 'confirm')])[2]";
	String removeRouteConfirmMessage = "//span[contains(text(), 'Are you sure you want to delete this route?')]";

	String removeRouteDeletedSuccessMessage = "//span[contains(text(), 'The routes successfully deleted')]";

	String deletedVehicleMessage = "//span[contains(text(), 'The vehicle successfully deleted')]";
	String vehicleMultiplePreffered = "//*[contains(text(), 'Just one preferred vehicle is allowed!')]";
//	String vehicleMultiplePreffered = "//*[contains(text(), 'Just one preferred vehicle is allowed! Please to make this vehicle as preferred, you must unselect the other already preferred vehicle')]";

	String addNewRouteButton = "//ion-button[contains(@data-test-automation, 'add-element')]";
	String routeFrom = "//input[contains(@placeholder, 'From')]";
	String routeTo = "//input[contains(@placeholder, 'To')]";
	String selectFromRoute = "//div[contains(text(), 'Gourock (Dunoon)')]";
	String selectToRoute = "//div[contains(text(), 'Dunoon')]";
	String addRouteButton = "//ion-button[contains(@class, 'form-action-buttons lower-border-radius ion-color ion-color-primary md button button-solid ion-activatable ion-focusable hydrated')]";

	String routeSuccessMessage = "//*[contains(text(), 'The routes successfully added')]";

	String successMessage = "//span[contains(text(), 'The personal details successfully updated')]";
	String searchNationality = "//input[contains(@placeholder, 'Nationality')]";
	String successAddressMessage = "//span[contains(text(), 'The address successfully updated')]";
	String successPhoneMessage = "//span[contains(text(), 'The phone number successfully updated')]";

	String successCompanionMessage = "//span[contains(text(), 'The companion successfully added')]";
	String companionDeleteConfirmMessage = "//span[contains(text(), 'Are you sure you want to delete this companion?')]";

	String companionDeleteSuccessMessage = "//span[contains(text(), 'The companion successfully deleted')]";
	String companionPage = "//div[contains(text(), 'You have no companions saved')]";
	String removeCompanionButton = "//ion-button[contains(@fill, 'clear')][@color='primary']";
	
	String removeCompanionConfirmButton = "//ion-button[contains(@data-test-automation, 'edea-confirmation-dialog.undefinedbutton.confirm')]";
	String contactTitle = "(//input[contains(@id, 'search-input')])[1]";
	String firstnameLabel = "//span[contains(text(), 'First name')]";
	String surnameLabel = "//span[contains(text(), 'Surname')]";
	String titleTypeLabel = "//span[contains(text(), 'Title type')]";
	String genderLabel = "//span[contains(text(), 'Gender')]";
	String dateOfBirthLabel = "//span[contains(text(), 'Date of birth')]";
	String concessionCardHolderLabel = "//span[contains(text(), 'Concession Card Holder')]";
	String assistanceRequiredLabel = "//span[contains(text(), 'Assistance Required')]";
	String modifyButton = "//ion-button[contains(@data-test-automation, 'button.edit')]";

	String firstName = "//ion-input[@formcontrolname='firstName']/input";
	String lastName = "//ion-input[@formcontrolname='lastName']/input";
	String email = "//input[@type='email']";
	String gender = "//ion-radio[contains(@slot, 'start')]";
	String day = "//input[contains(@placeholder, 'DD')]";
	String month = "//input[contains(@placeholder, 'MM')]";
	String year = "//input[contains(@placeholder, 'YYYY')]";
	String searchCountry = "//input[contains(@placeholder, 'Country')]";
	String updateButton = "//*[contains(text(), 'Update')]";
	String cancelButton = "//*[contains(text(), 'Cancel')]";
	String addresSection = "//span[contains(text(), 'RESIDENCE address')]";

	String modifyAddressButton = "(//ion-button[contains(@data-test-automation, 'button.edit')])[1]";
	String modifyMobileButton = "(//ion-button[contains(@data-test-automation, 'button.edit')])[2]";

	String addressLineOne = "//ion-input[contains(@formcontrolname, 'addressLineOne')]/input";
	String mobileNumber = "//ion-input[contains(@formcontrolname, 'number')]/input";

	String verifyRemovedConcessionCard = "//p[contains(., 'No')]/span[contains(text(), 'Concession Card Holder')]";

	String contactNameList = "(//input[contains(@data-test-automation, 'input.portlist')])[1]";
	String passengerTypeList = "(//input[contains(@data-test-automation, 'input.portlist')])[2]";
	String titleList = "(//input[contains(@data-test-automation, 'input.portlist')])[3]";
	String assistanceTypeList = "(//input[contains(@data-test-automation, 'input.portlist')])[5]";

	String prefferedCheckbox = "//ion-checkbox[contains(@formcontrolname, 'preferred')]";
	String companionAddButton = "(//ion-button[contains(@class, 'form-action-button')])[1]";

	String confirmButton = "(//ion-button[contains(@data-test-automation, 'create.edea-confirmation')])[2]";

	// span[contains(text(), 'First name')]
	String relativePath = Config.getResourcesFolderPath();
	String xmlTestDataFileName = Config.getExcelFineNameToReadXMLTestData();
	ExcelReader readCellForXML = new ExcelReader(relativePath + "/" + xmlTestDataFileName);

	public MyAccountPage updateLastName() throws InterruptedException {
		BP.javaScriptClick(myAccountLink);
		Assert.assertTrue(BP.isDisplayed(personalDetailSection));
		Assert.assertTrue(BP.isDisplayed(contactInformationSection));
		Assert.assertTrue(BP.isDisplayed(travelCompanionsSection));
//		Assert.assertTrue(BP.isDisplayed(savedVehicleSection));
//		Assert.assertTrue(BP.isDisplayed(savedRouteSection));
		BP.javaScriptClick(viewPersonalDetailLink);
		Thread.sleep(5000);
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		BP.javaScriptClick(modifyButton);
		BP.mediumWait();
		String getLastName = BP.getAttributeValue("value", lastName);
		BP.setValue(getLastName + CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "LAST_NAME", 2),
				lastName);
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successMessage));
		String getLastNameAfter = BP.getAttributeValue("value", lastName);
		Assert.assertEquals(getLastName + "a", getLastNameAfter);
		BP.javaScriptClick(cancelButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		return this;
	}

	public MyAccountPage updateGender(int rowId, String Sheetname) throws InterruptedException {
		BP.javaScriptClick(myAccountLink);
		Assert.assertTrue(BP.isDisplayed(personalDetailSection));
		Assert.assertTrue(BP.isDisplayed(contactInformationSection));
		Assert.assertTrue(BP.isDisplayed(travelCompanionsSection));
//		Assert.assertTrue(BP.isDisplayed(savedVehicleSection));
//		Assert.assertTrue(BP.isDisplayed(savedRouteSection));
		BP.javaScriptClick(viewPersonalDetailLink);
		Thread.sleep(5000);
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		BP.javaScriptClick(modifyButton);
		BP.mediumWait();
		BP.javaScriptClick("//*[text()='" + CalmacTestValues.getGender(rowId, Sheetname) + "']/following-sibling::*");
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successMessage));
//		Assert.assertTrue(BP.isRadioButtonSelected("//*[text()='" + CalmacTestValues.getGender(3) + "']/following-sibling::*"));
		BP.javaScriptClick(cancelButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		return this;
	}

	public MyAccountPage updateNationality() throws InterruptedException {
		BP.javaScriptClick(myAccountLink);
		Assert.assertTrue(BP.isDisplayed(personalDetailSection));
		Assert.assertTrue(BP.isDisplayed(contactInformationSection));
		Assert.assertTrue(BP.isDisplayed(travelCompanionsSection));
//		Assert.assertTrue(BP.isDisplayed(savedVehicleSection));
//		Assert.assertTrue(BP.isDisplayed(savedRouteSection));
		BP.javaScriptClick(viewPersonalDetailLink);
		Thread.sleep(5000);
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		BP.javaScriptClick(modifyButton);
		BP.mediumWait();
		BP.javaScriptScrollToView(searchNationality);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "NATIONALITY", 3), searchNationality);
		BP.shortWait();
		BP.click("//div[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "NATIONALITY", 3) + "')]");
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successMessage));
//		Assert.assertTrue(BP.isRadioButtonSelected("//*[text()='" + CalmacTestValues.getGender(3) + "']/following-sibling::*"));
		BP.javaScriptClick(cancelButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		return this;
	}

	public MyAccountPage deleteCompanion() throws InterruptedException {
		BP.javaScriptClick(removeCompanionButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed(companionDeleteConfirmMessage));
		BP.mediumWait();
		BP.javaScriptClick(removeCompanionConfirmButton);

//		Assert.assertTrue(BP.isDisplayed(companionDeleteSuccessMessage));
//		BP.mediumWait();
//
//		Assert.assertTrue(BP.isDisplayed(companionPage));

		return this;
	}

	public MyAccountPage removeConcession() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		Assert.assertTrue(BP.isDisplayed(personalDetailSection));
		Assert.assertTrue(BP.isDisplayed(contactInformationSection));
		Assert.assertTrue(BP.isDisplayed(travelCompanionsSection));
		BP.javaScriptClick(viewPersonalDetailLink);
		Thread.sleep(5000);
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		BP.javaScriptClick(modifyButton);
		BP.mediumWait();
		BP.javaScriptScrollToView(searchNationality);
		BP.click("//ion-radio-group[@formcontrolname='concessionCard']/descendant::*[text()='"
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "CONCESSION_CARD", 4)
				+ "']/following-sibling::*");
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successMessage));
//		Assert.assertTrue(BP.isRadioButtonSelected("//*[text()='" + CalmacTestValues.getGender(3) + "']/following-sibling::*"));
		BP.javaScriptClick(cancelButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed(firstnameLabel));
		Assert.assertTrue(BP.isDisplayed(surnameLabel));
		Assert.assertTrue(BP.isDisplayed(titleTypeLabel));
		Assert.assertTrue(BP.isDisplayed(genderLabel));
		Assert.assertTrue(BP.isDisplayed(dateOfBirthLabel));
		Assert.assertTrue(BP.isDisplayed(concessionCardHolderLabel));
		Assert.assertTrue(BP.isDisplayed(assistanceRequiredLabel));
		Assert.assertTrue(BP.isDisplayed(verifyRemovedConcessionCard));

		return this;

	}

	public MyAccountPage updateStreetAddress() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		Assert.assertTrue(BP.isDisplayed(personalDetailSection));
		Assert.assertTrue(BP.isDisplayed(contactInformationSection));
		Assert.assertTrue(BP.isDisplayed(travelCompanionsSection));
		BP.javaScriptClick(viewContactInformationLink);
		Thread.sleep(5000);
		BP.javaScriptClick(modifyAddressButton);
		BP.setValue("CONTACT", contactNameList);
		BP.shortWait();
		BP.click("//div[contains(text(), 'CONTACT')]");
		BP.javaScriptScrollToView(addressLineOne);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "ADDRESS_LINE", 2), addressLineOne);
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successAddressMessage));
		BP.javaScriptClick(cancelButton);
		BP.javaScriptClick(modifyMobileButton);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "MOBILE", 2), mobileNumber);
		BP.javaScriptClick(updateButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successPhoneMessage));
		BP.javaScriptClick(cancelButton);
		BP.mediumWait();
		Assert.assertTrue(BP.isDisplayed("//p[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "ADDRESS_LINE", 2) + "')]"));
		BP.javaScriptScrollToView(modifyMobileButton);
		Assert.assertTrue(BP.isDisplayed("//p[contains(text(), '"
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "MOBILE", 2) + "')]"));
		return this;

	}

	public MyAccountPage createCompanion(int rowId, String Sheetname) throws InterruptedException {


		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewCompanionLink);
		Thread.sleep(5000);
		BP.javaScriptClick(addCompanionButton);
		BP.shortWait();

		BP.setValue(CalmacTestValues.getCompanionContactName(rowId, Sheetname), contactNameList);
		BP.shortWait();

		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionContactName(rowId, Sheetname) + "')]");

		BP.setValue(CalmacTestValues.getCompanionPassengerType(rowId, Sheetname), passengerTypeList);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionPassengerType(rowId, Sheetname) + "')]");

		BP.setValue(CalmacTestValues.getCompanionTitle(rowId, Sheetname), titleList);
		BP.shortWait();
		BP.javaScriptClick("//div[contains(text(), '" + CalmacTestValues.getCompanionTitle(rowId, Sheetname) + "')]");
		BP.javaScriptScrollToView(firstName);

		BP.setValue(CalmacTestValues.getCompanionFirstName(rowId, Sheetname), firstName);
		BP.setValue(CalmacTestValues.getCompanionLastName(rowId, Sheetname), lastName);
		BP.javaScriptScrollToView(email);

		BP.setValue(CalmacTestValues.getCompanionEmail(rowId, Sheetname), email);

		BP.javaScriptClick(
				"//*[text()='" + CalmacTestValues.getCompanionGender(rowId, Sheetname) + "']/following-sibling::*");
		BP.javaScriptScrollToView(searchNationality);
		BP.setValue(CalmacTestValues.getCompanionNationality(rowId, Sheetname), searchNationality);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionNationality(rowId, Sheetname) + "')]");
		BP.javaScriptScrollToView(day);

		BP.setValue(CalmacTestValues.getCompanionDay(rowId, Sheetname), day);
		BP.setValue(CalmacTestValues.getCompanionMonth(rowId, Sheetname), month);
		BP.setValue(CalmacTestValues.getCompanionYear(rowId, Sheetname), year);

		BP.shortWait();

		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[contains(text(), '"
				+ CalmacTestValues.getCompanionRequiredAssistance(rowId, Sheetname) + "')]/following-sibling::*");
		BP.shortWait();

		if ("Yes".equals(CalmacTestValues.getCompanionRequiredAssistance(rowId, Sheetname))) {
			BP.setValue(CalmacTestValues.getCompanionAssistanceType(rowId, Sheetname), assistanceTypeList);
			BP.shortWait();
			BP.click(
					"//div[contains(text(), '" + CalmacTestValues.getCompanionAssistanceType(rowId, Sheetname) + "')]");
		}

		if ("Yes".equals(CalmacTestValues.getCompanionPrefferedCompanion(rowId, Sheetname))) {
			BP.javaScriptClick(prefferedCheckbox);
		}

		BP.javaScriptClick(companionAddButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(successCompanionMessage));
		BP.javaScriptClick(modifyButton);

		return this;

	}


	public MyAccountPage viewCompanion() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewCompanionLink);
		Thread.sleep(5000);
//		BP.isDisplayed(addCompanionButton);
		BP.isDisplayed(modifyButton);
		return this;

		}
		
	public MyAccountPage viewVehicle() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);
		Thread.sleep(5000);
		BP.isDisplayed(modifyButton);
		return this;

		}
	public MyAccountPage modifyVehicle() throws InterruptedException {

		BP.javaScriptClick(modifyButton);
		
		BP.setValue("A1", 
				registrationField);
		BP.shortWait();
		BP.javaScriptClick(findVehicleButton);
		BP.shortWait();
		BP.javaScriptClick(updateButton);
		return this;

		}
	public MyAccountPage modifyCompanion() throws InterruptedException {
		BP.javaScriptClick(modifyButton);
		BP.javaScriptScrollToView(email);
		BP.setValue("abcde12345@calmac.co.uk", email);
		BP.javaScriptClick(updateButton);
//		BP.shortWait();
//		Assert.assertTrue(BP.isDisplayed(successAddressMessage));
//		BP.javaScriptClick(cancelButton);
		return this;

		}
		
	public MyAccountPage addMultiplePreffered(int rowId, String Sheetname) throws InterruptedException {
		BP.javaScriptClick(cancelButton);

		BP.javaScriptClick(addNewCompanion);
		BP.setValue(CalmacTestValues.getCompanionContactName(rowId, Sheetname), contactNameList);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionContactName(rowId, Sheetname) + "')]");

		BP.setValue(CalmacTestValues.getCompanionPassengerType(rowId, Sheetname), passengerTypeList);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionPassengerType(rowId, Sheetname) + "')]");

		BP.setValue(CalmacTestValues.getCompanionTitle(rowId, Sheetname), titleList);
		BP.shortWait();
		BP.javaScriptClick("//div[contains(text(), '" + CalmacTestValues.getCompanionTitle(rowId, Sheetname) + "')]");
		BP.javaScriptScrollToView(firstName);

		BP.setValue(CalmacTestValues.getCompanionFirstName(rowId, Sheetname), firstName);
		BP.setValue(CalmacTestValues.getCompanionLastName(rowId, Sheetname), lastName);
		BP.javaScriptScrollToView(email);

		BP.setValue(CalmacTestValues.getCompanionEmail(rowId, Sheetname), email);

		BP.javaScriptClick(
				"//*[text()='" + CalmacTestValues.getCompanionGender(rowId, Sheetname) + "']/following-sibling::*");
		BP.javaScriptScrollToView(searchNationality);
		BP.setValue(CalmacTestValues.getCompanionNationality(rowId, Sheetname), searchNationality);
		BP.shortWait();
		BP.click("//div[contains(text(), '" + CalmacTestValues.getCompanionNationality(rowId, Sheetname) + "')]");
		BP.javaScriptScrollToView(day);

		BP.setValue(CalmacTestValues.getCompanionDay(rowId, Sheetname), day);
		BP.setValue(CalmacTestValues.getCompanionMonth(rowId, Sheetname), month);
		BP.setValue(CalmacTestValues.getCompanionYear(rowId, Sheetname), year);

		BP.shortWait();

		BP.click("//ion-radio-group[contains(@formcontrolname, 'assistanceBoarding')]/descendant::*[contains(text(), '"
				+ CalmacTestValues.getCompanionRequiredAssistance(rowId, Sheetname) + "')]/following-sibling::*");
		BP.shortWait();

		if ("Yes".equals(CalmacTestValues.getCompanionRequiredAssistance(rowId, Sheetname))) {
			BP.setValue(CalmacTestValues.getCompanionAssistanceType(rowId, Sheetname), assistanceTypeList);
			BP.shortWait();
			BP.click(
					"//div[contains(text(), '" + CalmacTestValues.getCompanionAssistanceType(rowId, Sheetname) + "')]");
		}

		if ("Yes".equals(CalmacTestValues.getCompanionPrefferedCompanion(rowId, Sheetname))) {
			BP.javaScriptClick(prefferedCheckbox);
		}

		BP.javaScriptClick(companionAddButton);
		BP.shortWait();
		BP.click(confirmButton);
		BP.javaScriptClick(prefferedCheckbox);
		BP.javaScriptClick(companionAddButton);
		BP.shortWait();
		return this;

	}

	public MyAccountPage addVehicleAutoPopulated(int rowId) throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);

		BP.javaScriptClick(addVehicleButton);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_REG_NUMBER", rowId),
				registrationField);
		BP.shortWait();
		BP.javaScriptClick(findVehicleButton);
		BP.shortWait();
		BP.javaScriptScrollToView(vehiclePrefferedCheckbox);

		BP.javaScriptClick(vehiclePrefferedCheckbox);
		BP.shortWait();
		BP.javaScriptClick(vehicleAddButton);
		BP.shortWait();

		Assert.assertTrue(BP.isDisplayed(vehicleAddSuccessMessage));

		return this;

	}
	
	public MyAccountPage addVehicleManually(int rowId) throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);

		BP.javaScriptClick(addVehicleButton);
//		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_REG_NUMBER", rowId),
//				registrationField);
//		BP.shortWait();
//		BP.javaScriptClick(findVehicleButton);
		BP.shortWait();
//		if (BP.isDisplayed(vehicleNotExist)) {
			BP.mediumWait();
			BP.javaScriptScrollToView(searchVehicleType);
			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId),
					searchVehicleType);
			BP.shortWait();
			System.out.println(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId));
			System.out.println("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
					+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
			BP.javaScriptClick("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
					+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
			BP.javaScriptScrollToView(vehicleMake);
			BP.shortWait();
			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MAKE", rowId), vehicleMake);
			BP.shortWait();
			BP.javaScriptScrollToView(vehicleModel);

			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MODEL", rowId), vehicleModel);
//		}

		BP.javaScriptScrollToView(vehiclePrefferedCheckbox);

		BP.javaScriptClick(vehiclePrefferedCheckbox);
		BP.shortWait();
		BP.javaScriptClick(vehicleAddButton);
		BP.shortWait();

		Assert.assertTrue(BP.isDisplayed(vehicleAddSuccessMessage));

		return this;

	}

	public MyAccountPage removeVehicle(int rowId) throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);
		BP.shortWait();
		BP.javaScriptClick(modifyVehicle);
		BP.javaScriptClick(removeCompanionButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(deleteVehicleConfirm));

		BP.shortWait();
		BP.javaScriptClick(deleteVehiclebutton);

//	Assert.assertTrue(BP.isDisplayed(deletedVehicleMessage));

		return this;

	}

	public MyAccountPage addVehicleNoPopulated(int rowId) throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);

		BP.javaScriptClick(addVehicleButton);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_REG_NUMBER", rowId),
				registrationField);

		BP.javaScriptScrollToView(searchVehicleType);
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId), searchVehicleType);
		BP.shortWait();
		System.out.println(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId));
		System.out.println("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
		BP.javaScriptClick("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
				+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
		BP.javaScriptScrollToView(vehicleMake);
		BP.shortWait();
		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MAKE", rowId), vehicleMake);
		BP.shortWait();
		BP.javaScriptScrollToView(vehicleModel);

		BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MODEL", rowId), vehicleModel);
		BP.javaScriptScrollToView(vehiclePrefferedCheckbox);

		BP.javaScriptClick(vehiclePrefferedCheckbox);
		BP.shortWait();
		BP.javaScriptClick(vehicleAddButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(vehicleAddSuccessMessage));
		return this;

	}

	public MyAccountPage addPrefferedvehicle(int rowId) throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewVehicleLink);

		BP.javaScriptClick(addPrefferedVehicleButton);
		BP.setValue("A1",
				registrationField);
		BP.shortWait();
		BP.javaScriptClick(findVehicleButton);
		BP.shortWait();
//		if (BP.isDisplayed(vehicleNotExist)) {
//			BP.mediumWait();
//			BP.javaScriptScrollToView(searchVehicleType);
//			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId),
//					searchVehicleType);
//			BP.shortWait();
//			System.out.println(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId));
//			System.out.println("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
//					+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
//			BP.javaScriptClick("//div[contains(@class, 'edea-select-list')]/descendant::div[text()=' "
//					+ CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_TYPE", rowId) + " ']");
//			BP.javaScriptScrollToView(vehicleMake);
//			BP.shortWait();
//			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MAKE", rowId), vehicleMake);
//			BP.shortWait();
//			BP.javaScriptScrollToView(vehicleModel);
//
//			BP.setValue(CalmacTestValues.readCellForXML.getCellData("UPDATE_ACCOUNT", "V_MODEL", rowId), vehicleModel);
//		}

		BP.javaScriptScrollToView(vehiclePrefferedCheckbox);
		BP.javaScriptClick(vehiclePrefferedCheckbox);
		BP.shortWait();
		BP.javaScriptClick(vehicleAddButton);
		BP.shortWait();

//		Assert.assertTrue(BP.isDisplayed(vehicleMultiplePreffered));
		BP.javaScriptClick(vehiclePrefferedCheckbox);

		BP.javaScriptClick(vehicleAddButton);

//		Assert.assertTrue(BP.isDisplayed(vehicleAddSuccessMessage));

		return this;

	}

	public MyAccountPage addRoute() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewRouteLink);
		BP.javaScriptClick(addNewRouteButton);
		BP.setValue("Gourock (Dunoon)", routeFrom);
		BP.javaScriptClick(selectFromRoute);
		BP.setValue("Dunoon", routeTo);
		BP.javaScriptClick(selectToRoute);
		BP.shortWait();
		BP.javaScriptClick(addRouteButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(routeSuccessMessage));
		return this;

	}
	public MyAccountPage viewRoute() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewRouteLink);
		Thread.sleep(5000);
		BP.isDisplayed(modifyButton);
		return this;

		}

	public MyAccountPage modifyRoute() throws InterruptedException {

		BP.javaScriptClick(modifyButton);
		
		BP.setValue("Gourock (Dunoon)", routeFrom);
		BP.javaScriptClick(selectFromRoute);
		BP.setValue("Dunoon", routeTo);
		BP.javaScriptClick(selectToRoute);
//		BP.shortWait();
//		BP.javaScriptClick(addRouteButton);
//		BP.shortWait();
//		Assert.assertTrue(BP.isDisplayed(routeSuccessMessage));
		BP.shortWait();
		BP.javaScriptClick(updateButton);
		return this;

		}
	public MyAccountPage deleteRoute() throws InterruptedException {

		BP.javaScriptClick(myAccountLink);
		BP.javaScriptClick(viewRouteLink);
		BP.javaScriptClick(modifyRouteButton);
		BP.shortWait();
		BP.javaScriptClick(removeRouteButton);
		BP.shortWait();
		Assert.assertTrue(BP.isDisplayed(removeRouteConfirmMessage));

		BP.javaScriptClick(removeRouteConfirmButton);
//		Assert.assertTrue(BP.isDisplayed(removeRouteDeletedSuccessMessage));

		return this;

	}

}
