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
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.ExcelReader;

public class CalmacTicketsCreationPage {
	CalmacTestValues calmacTestValues = new CalmacTestValues();
	RESTAssured restAssured = new RESTAssured();
	BaseTestPage BP = new BaseTestPage();
	String oneWayButton = "//*[contains(text(), 'One way')]";
	String returnButton = "//*[contains(text(), 'Return')]";
	String multiIslandButton = "//*[contains(text(), 'Multi-island')]";
	String journeyButton = "//*[contains(text(), 'Journey')]";

	String ticketsTypeText = "//p[contains(text(), 'What type of ticket are you looking for?')]";

	String relativePath = Config.getResourcesFolderPath();
	String xmlTestDataFileName = Config.getExcelFineNameToReadXMLTestData();
	ExcelReader readCellForXML = new ExcelReader(relativePath + "/" + xmlTestDataFileName);

	public MyAccountPage verifyTicketsType() throws InterruptedException {
		Assert.assertTrue(BP.isDisplayed(ticketsTypeText));
		Assert.assertTrue(BP.isDisplayed(oneWayButton));
		Assert.assertTrue(BP.isDisplayed(returnButton));
		Assert.assertTrue(BP.isDisplayed(multiIslandButton));
//		Assert.assertTrue(BP.isDisplayed(journeyButton));
		Thread.sleep(5000);
		return new MyAccountPage();
	}

}
