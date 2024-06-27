package com.mscs.emr.automation.functional;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.mscs.emr.automation.utils.Config;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class RESTAssured
{
    private String username = Config.getUsername();
    private String password = Config.getPassword();
    
    public Response post(String URI, Object xml, HashMap headers)
    {
        //Specify Base URI
        RestAssured.baseURI = URI;

        //Basic authentication
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(username);
        authScheme.setPassword(password);
        RestAssured.authentication = authScheme;

        //Request object
        RequestSpecification httpRequest = RestAssured.given();

        //Request payload
        httpRequest.headers(headers);
        if (headers.get("Content-Type").equals("application/xml")) httpRequest.body(xml);
        else httpRequest.formParam("xmldata", xml);

        //Response object
        Response response = httpRequest.request(Method.POST, RestAssured.baseURI);
        response.prettyPrint();
        return response;
    }


    public Response postPantheon(String URI, Object xml, HashMap headers)
    {
        //Specify Base URI
        RestAssured.baseURI = URI;
        //Request object
        RequestSpecification httpRequest = RestAssured.given();
        //Request payload
        httpRequest.headers(headers);
        if (headers.get("Content-Type").equals("application/xml")) httpRequest.body(xml);
        else httpRequest.formParam("xmldata", xml);
        //Response object
        Response response = httpRequest.request(Method.POST, RestAssured.baseURI);
        response.prettyPrint();
        return response;
    } 
    public Response postcollaboration(String URI, Object xml, HashMap headers)
    {
        //Specify Base URI
        RestAssured.baseURI = URI;
        //Request object
        RequestSpecification httpRequest = RestAssured.given();
        //Request payload
        httpRequest.headers(headers);
        if (headers.get("Content-Type").equals("application/xml")) httpRequest.body(xml);
        else httpRequest.formParam("xmldata", xml);
        //Response object
        Response response = httpRequest.request(Method.POST, RestAssured.baseURI);
        response.prettyPrint();
        return response;
    }
    
    public Response fileUpload(String URI, Object xml, HashMap headers1)
    {
    	
    	RestAssured.baseURI = URI;
    	RequestSpecification request = RestAssured.given();
    	// Adding headers
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Content-Type", "multipart/form-data");
    	request.headers(headers);
    	// Adding form data
    	File file = new File(CalmacTestValues.collaborationCenterFileUpload);
    	request.multiPart("file", file);
    	request.multiPart("REQUEST", xml);
    	// Sending the request
    	Response response = request.post(URI);
    	// Verifying the response
    	int statusCode = response.getStatusCode();
    	await().atMost(30, TimeUnit.SECONDS).until(() -> statusCode == 200);
    	String responseBody = response.getBody().asString();
    	System.out.println("Response Body is: " + responseBody);
    	return response;
    }
	public RESTAssured getAuthentication() throws Exception {
		RestAssured.baseURI = "https://testcfl.e-dea.it/EBookingRestServices/ebooking/authentication/login"; // replace with your actual endpoint
        String jsonBody = CalmacTestValues.readCellForXML.getCellData("API", "LOGIN", 2);
	
        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(RestAssured.baseURI) // replace with your actual login endpoint
                .then()
                .extract()
                .response();

        // Print the response
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        CalmacTestValues.token = response.jsonPath().getString("token");
    	System.out.println("TOKEN IS "+CalmacTestValues.token);
    	return this;
		
	}

}
