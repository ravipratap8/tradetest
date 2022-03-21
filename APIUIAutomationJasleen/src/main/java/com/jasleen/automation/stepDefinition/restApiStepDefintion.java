package com.jasleen.automation.stepDefinition;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.entity.ContentType;
import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jasleen.automation.lib.createListing;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class restApiStepDefintion implements En {

	private static ResourceBundle configProp = ResourceBundle.getBundle("application");
	public static RequestSpecification req = RestAssured.given();
	String url;
	String request;
	Response response;

	public restApiStepDefintion() {

		Given("{string} to hit the api and test", (String endUrl) -> {

			url = configProp.getString(endUrl);
			System.out.println("API End Url: " + url);

		});

		When("we use {string} method and send the API request", (String method) -> {

			switch (method) {
			case "POST":
//				Currently we don't have proper access token 
//				req.auth().oauth2(configProp.getString("token"));
				
				response = req.contentType(ContentType.APPLICATION_JSON.getMimeType()).body(request).when()
						.post(url);
				break;
			case "GET":
				response = req.contentType(ContentType.APPLICATION_JSON.getMimeType()).when().get(url);
				System.out.println("API Method: " + method);
				break;
			}

		});

		Then("verify the success response and validate the presences of {string} charity", (String charityName) -> {
			boolean result = false;
			if (response.getStatusCode() == 200) {
				System.out.println("Verified Success response");
				JsonParser parser = new JsonParser();
				JsonElement responseObject = parser.parse(response.getBody().asString());
				JsonArray jArray = responseObject.getAsJsonArray();
				for (int i = 0; i < jArray.size(); i++) {
					String charity = jArray.get(i).getAsJsonObject().get("Description").getAsString();
					if (charity.equals(charityName)) {
						result = true;
					}
				}
				Assert.assertTrue("Expected charity is not present in response list", result);
				System.out.println("Test Passed !!");

			} else {
				System.out.println("Test Failed !!");
			}

		});
		
		And("create request body with input parameter listing id {string}", (String id) -> {
			ObjectMapper obj = new ObjectMapper();
			createListing c = new createListing();
			c.setListingId(id);
					request = obj.writerWithDefaultPrettyPrinter().writeValueAsString(c);
					System.out.println(request);
		});

		Then("verify the success response and ensure the list is created", () -> {
			if (response.getStatusCode() == 200) {
				System.out.println("Verified Success response");
			}else {
				System.out.println("Test Failed!! "+ response.getStatusCode() + "\nError: "+ response.getBody().asString() );

			}
		});


	}
}
