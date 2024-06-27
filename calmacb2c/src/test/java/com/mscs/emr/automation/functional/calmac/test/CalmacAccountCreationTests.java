package com.mscs.emr.automation.functional.calmac.test;

import java.io.IOException;
import java.sql.ResultSet;

import org.testng.annotations.Test;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.CalmacTestValues;
import com.mscs.emr.automation.functional.TestCaseId;
import com.mscs.emr.automation.functional.TestGroup;
import com.mscs.emr.automation.functional.calmac.pages.core.BookingPage;
import com.mscs.emr.automation.functional.calmac.pages.core.CalmacAccountCreationPage;
import com.mscs.emr.automation.functional.calmac.pages.core.MyAccountPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartBoardingPage;
import com.mscs.emr.automation.functional.calmac.pages.core.SmartApplicationDirectSalesPage;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.OracleDbUtils;

/**
 * @author muhammad.iftikhar
 *
 */
public class CalmacAccountCreationTests extends BaseTestPage {
	CalmacAccountCreationPage calmacAccountCreationPage = new CalmacAccountCreationPage();
	BookingPage bookingpage = new BookingPage();
	BaseTestPage BP = new BaseTestPage();
	String transID = CalmacTestValues.transactionId;
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	SmartBoardingPage smartBoardingPage = new SmartBoardingPage();
	MyAccountPage myAccountPage = new MyAccountPage();
	SmartApplicationDirectSalesPage smartApplicationDirectSalesPage = new SmartApplicationDirectSalesPage();

	/*
	 * @TestCaseId(40)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=1,
	 * description = "Creation of an account with gender female") public void
	 * createAccountUsingGenderFemale_REG_06_001a () throws Exception {
	 * calmacAccountCreationPage .findAvailableDateForSailing(); //
	 * .clickCreateAccountLink() // .enterAccountDetail(2) // .activateAccount();
	 * 
	 * }
	 */
	@TestCaseId(1)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 1, description = "Creation of an account with gender female")
	public void createAccountUsingGenderFemale_REG_06_001a() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink()
		.enterAccountDetail(2, "CALMAC_REGISTRATION")
		.activateAccount();

	}

	@TestCaseId(2)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 2, description = "Creation of an account with gender male")
	public void createAccountUsingGenderMale_REG_06_001b() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(3, "CALMAC_REGISTRATION")
				.activateAccount();

	}

	@TestCaseId(3)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 3, description = "Creation of an account with gender female and Concession Yes")
	public void createAccountUsingConcession_REG_06_001c() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(4, "CALMAC_REGISTRATION")
				.activateAccount();
	}

	@TestCaseId(4)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 4, description = "Creation of an account with Blue Badge Yes")
	public void createAccountUsingBlueBadge_REG_06_001d() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(5, "CALMAC_REGISTRATION")
				.activateAccount();

	}

	@TestCaseId(5)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 5, description = "Creation of an account with Concession and Blue Badge Yes")
	public void createAccountUsingConcessionAndBlueBadgeREG_06_001e() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(6, "CALMAC_REGISTRATION")
				.activateAccount();

	}

	@TestCaseId(6)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 6, description = "Creation of an account with gender female and Country Italy")
	public void createFemaleAccountWithAssistance_REG_06_001f() throws Exception {
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(7, "CALMAC_REGISTRATION")
				.activateAccount();

	}

	@TestCaseId(8)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 8, description = "Update Last name")

//	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 8, description = "Update Last name", dependsOnMethods = "createAccountUsingGenderFemale_REG_06_001a")
	public void updateLastName_REG_06_002a() throws Exception {
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType().updateLastName();

//		calmacAccountCreationPage.loginCalmac(2).verifyTicketsType().updateLastName();
	}

	@TestCaseId(9)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 9, description = "Update gender")

	public void updateGender_REG_06_002b() throws Exception {
		createAccountUsingGenderFemale_REG_06_001a();

		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType().updateGender(3, "CALMAC_REGISTRATION");
	
	}

	@TestCaseId(10)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 10, description = "Update Nationality")
	public void updateNationality_REG_06_002c() throws Exception {
//		calmacAccountCreationPage.loginCalmac(3).verifyTicketsType().updateNationality();
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType().updateNationality();
	
	}

	@TestCaseId(11)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 11, description = "Remove Concession")
	public void removeConcession_REG_06_002d() throws Exception {
//		calmacAccountCreationPage.loginCalmac(4).verifyTicketsType().removeConcession();
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType().removeConcession();
	
	}

	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void updateAddress_REG_06_002e() throws Exception {
//		calmacAccountCreationPage.loginCalmac(4).verifyTicketsType().updateStreetAddress();
		
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType().updateStreetAddress();
	
	}

	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void modiyCompanion_REG_06_002f() throws Exception {
		
		createMaleCompanion_REG_06_004a();
		calmacAccountCreationPage.navigateToHomePage()
		.verifyTicketsType()
		.viewCompanion().modifyCompanion();
	
	}
	
	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void modifyVehicle_REG_06_002g() throws Exception {
		
//		createMaleCompanion_REG_06_004a();
//		calmacAccountCreationPage.navigateToHomePage()
//		.verifyTicketsType()
//		.viewCompanion().modifyCompanion();
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addVehicleAutoPopulated(2);
		calmacAccountCreationPage.navigateToHomePage()
		.verifyTicketsType()
		.viewVehicle()
		.modifyVehicle();

	
	}
	
	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void modifyRoute_REG_06_002h() throws Exception {
		

		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addRoute();
		calmacAccountCreationPage
		.navigateToHomePage()
		.verifyTicketsType()
		.viewRoute()
		.modifyRoute();	
	}
	
	  @TestCaseId(20)
	  
	  @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=20, description = "Update Last name")
	  public void resetPassword_REG_06_003 () throws
	  Exception { 
		  calmacAccountCreationPage .resetPassword(2);
	  
	  }
	 

	
	@TestCaseId(13)	
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void addNewVehicleAuto_REG_06_005a() throws Exception {
		
//		createMaleCompanion_REG_06_004a();
//		calmacAccountCreationPage.navigateToHomePage()
//		.verifyTicketsType()
//		.viewCompanion().modifyCompanion();
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addVehicleAutoPopulated(2);
	
	}
	@TestCaseId(14)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void addNewVehicleNoPopulaed_REG_06_005b() throws Exception {
		
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addVehicleNoPopulated(3);;
	
	}
	
	@TestCaseId(15)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void addMultipleVehicleWithDuplicatePreffered_REG_06_005c() throws Exception {
		
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addVehicleAutoPopulated(2).addPrefferedvehicle(3);
	
	}
	
	
	@TestCaseId(15)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void removeVehicle_REG_06_005d() throws Exception {
		
		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addVehicleAutoPopulated(2).removeVehicle(2);
	
	}
	
	
	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void addRoute_REG_06_006a() throws Exception {
		

		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addRoute();
//		calmacAccountCreationPage
//		.navigateToHomePage()
//		.verifyTicketsType()
//		.viewRoute()
//		.modifyRoute();	
	}
	
	
	@TestCaseId(12)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 12, description = "Update address")
	public void deleteRoute_REG_06_006b() throws Exception {
		

		createAccountUsingGenderFemale_REG_06_001a();
		calmacAccountCreationPage.loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION");
		myAccountPage.addRoute().deleteRoute();
//		calmacAccountCreationPage
//		.navigateToHomePage()
//		.verifyTicketsType()
//		.viewRoute()
//		.deleteRoute();	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@TestCaseId(13)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 13, description = "Create male companion")
	public void createMaleCompanion_REG_06_004a() throws Exception {

		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(2, "COMPANION_DETAIL");
	}

	@TestCaseId(14)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 14, description = "Create female companion with concession")
	public void CreateFemaleCompanionWithConcession_REG_06_004b() throws Exception {

		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(3, "COMPANION_DETAIL");
	}

	@TestCaseId(15)

	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 15, description = "Create chile companion")
	public void CreateChildCompanion_REG_06_004c() throws Exception {

		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(4, "COMPANION_DETAIL");
	}

	@TestCaseId(16)
	@Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority = 16, description = "Create infant comapnion")
	public void createInfantFemaleCompantion_REG_06_004_d() throws Exception {

		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(5, "COMPANION_DETAIL");
	}

	@TestCaseId(17)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 17, description = "Create multiple preffered companion")
	public void createMultiplePrefferedCompanion_REG_06_004e() throws Exception {

		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(2, "COMPANION_DET"
						+ "AIL").addMultiplePreffered(3, "COMPANION_DETAIL");

	}

	@TestCaseId(18)
	@Test(groups = { TestGroup.REGRESSION,
			TestGroup.SMOKE }, priority = 18, description = "Create multiple preffered companion")
	public void removeCompanion_REG_06_004f() throws Exception {
		// Need to be fixed
		calmacAccountCreationPage.clickCreateAccountLink().enterAccountDetail(2, "CALMAC_REGISTRATION")
				.activateAccount().loginNewlyCreatedAccount(2, "CALMAC_REGISTRATION").verifyTicketsType()
				.createCompanion(2, "COMPANION_DETAIL").deleteCompanion();
	}

// Change Password
	/*
	 * @TestCaseId(20)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=20,
	 * description = "Update Last name") public void updatePassword () throws
	 * Exception { calmacAccountCreationPage .resetPassword(2);
	 * 
	 * }
	 */

	/*
	 * @TestCaseId(20)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=21,
	 * description = "Add Vehcile Auto populate") public void
	 * addNewVehicle_REG_06_005a () throws Exception { calmacAccountCreationPage
	 * .loginToAddVehicle(2) .addVehicleAutoPopulated(2);
	 * 
	 * }
	 * 
	 * @TestCaseId(21)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=22,
	 * description = "Delete Vehicle") public void removeVehicle_REG_06_005c ()
	 * throws Exception { calmacAccountCreationPage .loginToAddVehicle(2)
	 * .removeVehicle(2);
	 * 
	 * }
	 * 
	 * 
	 * @TestCaseId(22)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=23,
	 * description = "Add vehicle by entering manula name") public void
	 * addNewVehicleNoPopulaed_REG_06_005b () throws Exception {
	 * calmacAccountCreationPage .loginToAddVehicle(2) .addVehicleNoPopulated(3);
	 * 
	 * }
	 * 
	 * 
	 * @TestCaseId(23)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=24,
	 * description = "Add Multiple Preffered Vehicle") public void
	 * addMultipleVehicleWithDuplicatePreffered_REG_06_005d () throws Exception {
	 * calmacAccountCreationPage .loginToAddVehicle(2) .addPrefferedvehicle(4);
	 * 
	 * }
	 * 
	 * @TestCaseId(24)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=25,
	 * description = "Add Route") public void addRoute_REG_06_006a () throws
	 * Exception { calmacAccountCreationPage .loginToAddVehicle(2) .addRoute(4);
	 * 
	 * }
	 * 
	 * @TestCaseId(24)
	 * 
	 * @Test(groups = { TestGroup.REGRESSION, TestGroup.SMOKE }, priority=26,
	 * description = "Delete Route") public void deleteRoute_REG_06_006b () throws
	 * Exception { calmacAccountCreationPage .loginToAddVehicle(2) .deleteRoute(4);
	 * 
	 * }
	 */
}
