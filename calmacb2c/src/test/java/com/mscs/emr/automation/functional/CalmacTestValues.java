package com.mscs.emr.automation.functional;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.json.XML;

import com.mscs.emr.automation.testData.MockDataUtils;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.ExcelReader;
import com.mscs.emr.automation.utils.FileUtilities;

import io.restassured.response.Response;
public class CalmacTestValues {
    public static final String ATTRIBUTE_CLASS = "class";
    public static final String VALUE_DISABLED_ANCHOR = "disabledAnchor";
    public static final String ATTRIBUTE_ARIA = "aria-disabled";
    public static final String VALUE_TRUE = "true";
    public static final String PAGE_RANGE = ".pageRange";
    public static final String BACKGROUND_COLOR = "background-color";
    public static final String ATTRIBUTE_COLOR = "color";
    public static final String BORDER_TOP_COLOR = "border-top-color";
    public static final String ATTRIBUTE_TITLE = "title";
    public static final String ATTRIBUTE_VALUE = "value";
    public static final String ATTRIBUTE_SCROLL_HEIGHT = "scrollHeight";
    public static final String ATTRIBUTE_OFFSET_HEIGHT = "offsetHeight";
    public static final String PANTHEON_PLACED_ORDER_STATUS_CODE = "Wells 130";
    public static final String PANTHEON_COMMENT_STATUS_CODE = "Wells 220";
    public static final String PANTHEON_DOCUMENT_STATUS_CODE = "Wells 180";
    public static final String PANTHEON_CANCELLED_ORDER_STATUS_CODE = "Wells 240";
    public static String relativePath = Config.getResourcesFolderPath();
    public static String testDataRelativePath = Config.getTestDataFolderPath();
    public static String collaborationCenterFileUpload = Config.getXMLFileToUpload();
    public static String integrationsOrderIdentifier;
    public static JSONObject DocXmlData;
    public static JSONObject SendDocFromRWData;
    public static JSONObject SendNoteFromEncXmlData;
    public static JSONObject CancelOrderData;
    public static JSONObject EncompassSendNoteToRW;
    public static String noteSubject = null;
    public static String noteBody = null;
	public static String token;
    public static final String Key="Accept";
    public static String lQBTEFSOrderType = "LQB_TEFS";
    public static String lqbCTSOrderType = "LQB_CTS";
    public static String PointCTSOrderType = "POINT_CTS";
    public static String calyxPathTEFSOrderType = "CalyxPath_TEFS";
    public static String calyxPathCTSOrderType = "CalyxPath_CTS";
    public static final String CONTENT_TYPE="application/json";
    public static final String LQBNoteContent = "Vendor Communication - Scheduling¦Testing Note";
    public static final String CalyxesNoteContent = "Posting IntegrationOrder to CATS completed with status: Success";
    public static final String RWNoteValidation = "Data successfully sent to Title Port for action: NOTE";
    public static final String CalyxPathDocContent = "TestDocument.pdf";
    //Database Queries
    public String sqlStatementQueryCommentForResWare="')and Comment = 'Sent Note to ResWare started' ORDER BY ActivityId DESC";
    public String sqlStatementQueryCommentForCancelOrder="')and Comment = 'Cancel order in ResWare processed' ORDER BY ActivityId DESC";
    public String sqlStatementQueryCommentForSendingDocFromRW="')and Comment = 'Posting Document to CATS completed with status : Success' ORDER BY ActivityId DESC";
    public String sqlStatementQueryCommentForSendingNoteFromRW="')and Comment = 'Posting IntegrationOrder to CATS completed with status: Success' ORDER BY ActivityId DESC";
    public String sqlStatementQueryCommentForSendingNoteFromRWUpdated="')and Comment = 'Data successfully sent to Title Port for action: NOTE' ORDER BY ActivityId DESC";
    public String sqlStatement = "SELECT Data, Comment, ActivityType, StatusId FROM dbo.Activity (NOLOCK) WHERE [TransId] = (SELECT ID FROM dbo.[Transaction] WHERE ContextId ='";
    public String sqlStatementToRetriveCompleteData = "select * from IntegrationOrder where LoanNumber='";
    public static String pantheonuserName= Config.getPantheonUserName();
    public static String pantheonpassword= Config.getPantheonPassword();
    public static String calmacb2cusername= Config.getCalmacUser();
    public static String calmacb2cpassword= Config.getCalmacPassword();
    public static final String ENCAPIUsername = Config.getUsername();
    public static final String ENCAPIPassword = Config.getPassword();

    public static String GenerateloanNumber = MockDataUtils.createUniqueNumber(10);
    public static String unstructuredAddress = MockDataUtils.createUniqueNumber(7);
    public static String getSheetName= Config.getSheetName();
    public static String fileName = Config.getExcelFineNameToReadTestData();
    public static String dataFilePath = testDataRelativePath + "/" + fileName;
    public static String xmlTestDataFileName = Config.getExcelFineNameToReadXMLTestData();
    public static ExcelReader readCellForXML = new ExcelReader(relativePath + "/" + xmlTestDataFileName);  


    public static String b2cUsername =readCellForXML.getCellData("CALMACB2C" ,"B2C_USERNAME" , 2);
    public static String b2cPassword =readCellForXML.getCellData("CALMACB2C" ,"B2C_PASSWORD" , 2);
    public static String b2cDeparturePort =readCellForXML.getCellData("CALMACB2C" ,"DEPARTURE_PORT" , 2);
    public static String b2cArrivalPort =readCellForXML.getCellData("CALMACB2C" ,"ARRIVAL_PORT" , 2);
    public static String b2cSailingDate =readCellForXML.getCellData("CALMACB2C" ,"SAILING_DATE" , 2);
    public static String b2cSailingTime =readCellForXML.getCellData("CALMACB2C" ,"SAILING_TIME" , 2);
    public static String TitlePortPlaceUSBankOrderXML =readCellForXML.getCellData("PRICE" ,"CHECK" , 2);

    public static String departureSailingCode1;
    public static String departureSailingCode2;
    public static String departureSailingCode3;
    
    public static String priceAvailabilityXml =readCellForXML.getCellData("API", "PRICEAVAILABILITY", 2);
    public static String capacity =readCellForXML.getCellData("API", "CAPACITY", 2);

    //Creation of An Account
//    public static String accountTitle =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"TITLE" , 2);
//    public static String accountFirstname =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"FIRST_NAME" , 2);
//    public static String accountLastname =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"LAST_NAME" , 2);
//    public static String accountGender =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"GENDER" , 2);
//    public static String accountDay =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"DAY" , 2);
//    public static String accountMonth =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"MONTH" , 2);
//    public static String accountYear =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"YEAR" , 2);
//    public static String accountNationality =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"NATIONALITY" , 2);
//    public static String accountPostalCode =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"POSTAL_CODE" , 2);
//    public static String accountConcessioncard =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"CONCESSION_CARD" , 2);
//    public static String accountBludeBadge =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"BLUE_BADGE" , 2);
//    public static String accountAssistance =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"ASSISTANCE" , 2);
//    public static String accountCountry =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"COUNTRY" , 2);
//    public static String accountMobilePrefix =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"MOBILE_PREFIX" , 2);
//    public static String accountMobile =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"MOBILE_NO" , 2);
    public static String accountPassword =readCellForXML.getCellData("CALMAC_REGISTRATION" ,"PASSWORD" , 2);

	public static String availableDepartureSailingCode;
	public static String availableArrivalSailingCode;


    public static String sailingTimeForDepartureAPI;
    public static String sailingDayForDepartureAPI;
    
    public static String sailingDayForDeparture;
    public static String sailingTimeForDeparture;
    public static String sailingDayForArrival;
    public static String sailingTimeForArrival;
    public static String availableCapacityBeforeBooking;
    public static String availableCapacityAfterBooking;
    
    public static String soldPetCapacityBeforeBooking;
    public static String soldPetCapacityAfterBooking;

    public static String soldPassengerCapacityBeforeBooking;
    public static String soldPassengerCapacityAfterBooking;

    public static String getLoginEmail(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "EMAIL", cellIndex);
    }
    public static String getAccountTitle(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "TITLE", cellIndex);
    }
    public static String getLegCode(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "LEG_CODE", cellIndex);
    }
    public static String getDeparturePort(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "DEPARTURE_PORT", cellIndex);
    }
    public static String getArrivalPort(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "ARRIVAL_PORT", cellIndex);
    }


    public static String getFirstName(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "FIRST_NAME", cellIndex);
    }
    
    public static String getLastName(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "LAST_NAME", cellIndex);
    }
    public static String getGender(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "GENDER", cellIndex);
    }
    public static String getDay(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "DAY", cellIndex);
    }
    public static String getMonth(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "MONTH", cellIndex);
    }
    public static String getYear(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "YEAR", cellIndex);
    }
    
    public static String getNationality(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "NATIONALITY", cellIndex);
    }
    public static String getPostalCode(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "POSTAL_CODE", cellIndex);
    }
    
    public static String getEmail(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "EMAIL", cellIndex);
    }
    

    public static String getConcessionCard(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "CONCESSION_CARD", cellIndex);
    }
    public static String getTypeOfConcession(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "TYPE_OF_CONNECTION", cellIndex);
    }
    public static String getConcessionCardNumber(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "CONNECTION_CARD_NUMBER", cellIndex);
    }

    public static String getBlueBadge(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "BLUE_BADGE", cellIndex);
    }
    public static String getBlueBadgeNumber(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "BLUE_BADGE_NUMBER", cellIndex);
    }
    public static String getAssistance(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "ASSISTANCE", cellIndex);
    }
    public static String getAssistanceType(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "ASSISTANCE_TYPE", cellIndex);
    }
    public static String getCountry(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "COUNTRY", cellIndex);
    }
    public static String getMobilePrefix(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "MOBILE_PREFIX", cellIndex);
    }
    public static String getMobile(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "MOBILE_NO", cellIndex);
    }
    public static String getPassword(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "PASSWORD", cellIndex);
    }
    
    
    public static String getCompanionContactName(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "CONTACT_NAME", cellIndex);
    }
    
    
    public static String getCompanionPassengerType(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "PASSENGER_TYPE", cellIndex);
    }
    
    public static String getCompanionTitle(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "TITLE", cellIndex);
    }
    public static String getCompanionFirstName(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "FIRST_NAME", cellIndex);
    }
    public static String getCompanionLastName(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "LAST_NAME", cellIndex);
    }
    public static String getCompanionEmail(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "EMAIL", cellIndex);
    }
    public static String getCompanionGender(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "GENDER", cellIndex);
    }
    public static String getCompanionNationality(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "NATIONALITY", cellIndex);
    }
    public static String getCompanionDay(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "DAY", cellIndex);
    }
    public static String getCompanionMonth(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "MONTH", cellIndex);
    }
    public static String getCompanionYear(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "YEAR", cellIndex);
    }
    public static String getCompanionRequiredAssistance(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "REQUIRED_ASSISTANCE", cellIndex);
    }
    public static String getCompanionAssistanceType(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "ASSISTANCE_TYPE", cellIndex);
    }
    public static String getCompanionPrefferedCompanion(int cellIndex, String Sheetname) {
        return readCellForXML.getCellData(Sheetname, "PREFFERED_COMPANION", cellIndex);
    }
    

    /// Login 
    public static String existingDocID="20221012-0566-1";
    public static String existingDocIDUSBANK="USB-210525-0009-1";
    public static String CTSSendDocToRW;
    public static String TEFSSendDocToRW;
    public static String CTSCancelOrderXML;
    public static String TEFSCancelOrderXML;
    public static String TEFSSendNoteToRW;
    public static String CTSSendNoteToRW;
    public static String TEFSSendNoteToSource;
    public static String CTSSendNoteToSource;
    public static String CTSSendDocToSource;
    public static String TEFSSendDocToSource;
    public static String loanColumnName="LoanNumber";
    public static String TransactionIdColumnName="LoanNumber";
    public static String docColumnName="DocumentID";
    public static String folderColumnName="FolderID";
    public static String orderColumnName="OrderNumber";
    public static String contextIDColumnName="ContextId";
    public static String MethodNameColumn="MethodName";
    public static String Email;
    public static String VerifyLogin="Hi Muhammad";

    public static String Token;
    public static String CollaborationCenterFolderID;
    public static String CollaborationCenterMessageID;
    public static String docID;
    public static String folderID;
    public static String integrationsResWareFileNumber;
    public static String transactionId;
    public static String bookingReference=null;
    public static String portOfficebookingReference=null;
    public static String portOfficeSailingDateTime=null;

    public static String integrationsContextId;
    public static String ResWareFileNumber = null;
    public static String collaborationAuthenticationURL="/Auth/Login";
    public static String collaborationVerificationURL="/Auth/TokenCheck/";
    public static String collaborationInsertFolderURL="/Folder/Insert/";
    public static String collaborationGetFolderURL="/Folder/Get/"; 
    public static String collaborationInsertMessageURL="/Message/Insert/";  
    public static String collaborationGetMessageURL="/Message/Get/"; 
    public static String collaborationFileUploadURL="/File/Upload/";  
    public HashMap<String, String> headers = new HashMap(){
        {
            put("Content-Type", "application/xml");
            put("Host", "esb-uat.com");
            put("Accept", "application/xml");

        }};
    public HashMap<String, String> TitlePortHeaders = new HashMap(){
        {
            put("Content-Type", "application/xml");

        }};
        public HashMap<String, String> CCHeader = new HashMap(){
            {
                put("Content-Type", "application/xml");
                put("Authorization", "Basic Og==");         
            }};
            public HashMap<String, String> CCFileUploadHeader = new HashMap(){
                {
                    put("Content-Type", "multipart/form-data");
                    put("Authorization", "Basic Og==");
                    put("Content-Type", "application/xml");
                    
                }};
        public HashMap<String, String> PantheonHeaders = new HashMap(){
            {
                put("Content-Type", "application/xml");

            }};
    public HashMap<String, String> OutBoundheaders = new HashMap(){
        {
            put("Content-Type", "application/x-www-form-urlencoded");
            put("Host", "esb-uat.stewart.com");
            put("Authorization", "Basic dGVzdHVzZXIwMTp0ZXN0dXNlcjAx");
        }};

    public void initializeRequiredFiles (String orderType)
    {

    }
    public String getRWNoteXMLData () throws IOException
    {
        EncompassSendNoteToRW = FileUtilities.convertXMLToJson("/outBoundNoteAPI.xml");

        EncompassSendNoteToRW
                .getJSONObject("ResWareAPI")
                .getJSONObject("Message")
                .getJSONObject("Transaction")
                .put("FileId", ResWareFileNumber);

        return (XML.toString(EncompassSendNoteToRW));
    }

    public  String getCancelOrderData () throws IOException
    {
        CancelOrderData = FileUtilities.convertXMLToJson("/cancelOrder.xml");

        CancelOrderData
                .getJSONObject("Encompass")
                .getJSONObject("Header")
                .put("ContextID",integrationsContextId);

        return (XML.toString(CancelOrderData));
    }

    public String getRWDocXMLData () throws IOException
    {
        SendDocFromRWData = FileUtilities.convertXMLToJson("/outBoundDocAPI.xml");

        SendDocFromRWData
                .getJSONObject("ResWareAPI")
                .getJSONObject("Message")
                .getJSONObject("Transaction")
                .put("FileId", ResWareFileNumber);

        return (XML.toString(SendDocFromRWData));
    }

    public  String getDocXMLData () throws IOException
    {
        DocXmlData = FileUtilities.convertXMLToJson("/docData.xml");

        DocXmlData
                .getJSONObject("Stewart-XML")
                .getJSONObject("Header")
                .put("ContextID",integrationsContextId);

        return (XML.toString(DocXmlData));
    }

    public  String getNoteXMLData () throws IOException
    {
        SendNoteFromEncXmlData = FileUtilities.convertXMLToJson("/sendNoteFromEnc.xml");

        SendNoteFromEncXmlData
                .getJSONObject("Encompass")
                .getJSONObject("Header")
                .put("ContextID", integrationsContextId);

        noteSubject = (String) SendNoteFromEncXmlData
                .getJSONObject("Encompass")
                .getJSONObject("Order")
                .getJSONObject("Notes")
                .getJSONObject("Note")
                .get("subject");

        noteBody = (String) SendNoteFromEncXmlData
                .getJSONObject("Encompass")
                .getJSONObject("Order")
                .getJSONObject("Notes")
                .getJSONObject("Note")
                .get("Message");

        return (XML.toString(SendNoteFromEncXmlData));
    }

    public static Response response = null;

    public String getRequiredValue(String fieldsName) throws Exception {

        return  (String) CalmacTestValues.class.getField(Config.getEnv()+fieldsName).get(this.getClass());
    }

    /**
     *  This method is used to set the value in excel sheet with respect to given data
     * @param LoanNumber is used to set the OrderNumber in excel
     * @param docNumber is used to set the docNumber in excel
     * @param folderID is used to set the folderID in excel
     * @param orderNumber is used to set the OrderNumber in excel
     * @param contextID is used to set the contextID in excel
     * @throws IOException
     */
    @SuppressWarnings("null")
    public static void setValueInExcel(String LoanNumber, String docNumber, String folderID, String orderNumber, String IntegrationContectId, String TransID) throws IOException {
        System.out.println("The method name is: " + new Exception().getStackTrace()[0].getMethodName());
        ExcelReader excelReader = new ExcelReader(dataFilePath);
        int lastRow= excelReader.getRowCount(getSheetName);
        if(LoanNumber!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.loanColumnName, lastRow, LoanNumber);
            LoanNumber=null;
        }
        if(TransID!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.TransactionIdColumnName, lastRow, TransID);
            TransID=null;
        }
        if(docNumber!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.docColumnName, lastRow, docNumber);
            docNumber=null;
        }
        if(folderID!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.folderColumnName, lastRow, folderID);
            folderID=null;
        }
        if(orderNumber!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.orderColumnName, lastRow, orderNumber);
            orderNumber=null;
        }

        if(integrationsContextId!=null) {
            excelReader.setCellData(getSheetName, CalmacTestValues.contextIDColumnName, lastRow, integrationsContextId);
            integrationsContextId=null;
        }
        

        String methodName =InvokedMethodListener.methodname;

        if(!(methodName=="beforeSuite" || methodName=="afterSuite")) {
            excelReader.setCellData(getSheetName, CalmacTestValues.MethodNameColumn, lastRow,  InvokedMethodListener.methodname);
        }
        }

        @SuppressWarnings("null")
        public static void setEmailInExcel(String email) throws IOException {
            System.out.println("The method name is: " + new Exception().getStackTrace()[0].getMethodName());
            ExcelReader excelReader = new ExcelReader(dataFilePath);
            int lastRow= excelReader.getRowCount(getSheetName);

            if(email!=null) {
//                excelReader.setCellData(getSheetName, CalmacTestValues.loanColumnName, lastRow, LoanNumber);
                excelReader.setCellData(getSheetName, "EMAIL", lastRow, email);
                email=null;
            }
          

            String methodName =InvokedMethodListener.methodname;

            if(!(methodName=="beforeSuite" || methodName=="afterSuite")) {
                excelReader.setCellData(getSheetName, CalmacTestValues.MethodNameColumn, lastRow,  InvokedMethodListener.methodname);

            }


    }
    /**
     * This method is used to set the response to null
     * @throws IOException
     */
    public  static void setResponseToNull() throws IOException {

    	Email=null;
  
    }

    public String priceAvailabilityPayload() throws IOException {

    	LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formattedDate = tomorrow.format(formatter);
        System.out.println("Tomorrow's date in YYMMDD format is: " + formattedDate);
        LocalDate dayAfterTomorrow = today.plusDays(10);
        String dayAfterTomorrowFormattedDate = dayAfterTomorrow.format(formatter);
        System.out.println("Tomorrow's date in YYMMDD format is: " + dayAfterTomorrowFormattedDate);
       return CalmacTestValues.priceAvailabilityXml.replace("240517", formattedDate).replace("240517", dayAfterTomorrowFormattedDate);
    }
    public String availableCapacityPayLoad() throws IOException {

       return CalmacTestValues.capacity;
    }

    
}
