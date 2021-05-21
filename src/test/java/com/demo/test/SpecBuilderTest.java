package com.demo.test;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {
	
	@Test
	public void serializeTest()
	{
		AddPlace obj = new AddPlace();
		obj.setAccuracy(50);
		obj.setAddress("29, side layout, cohen 09");
		obj.setLanguage("English");
		obj.setName("Frontline house");
		obj.setPhone_number("(+91) 983 893 3937");
		obj.setWebsite("http://google.com");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		obj.setTypes(myList);
		
		Location myLocation = new Location();
		myLocation.setLat(-38.383494);
		myLocation.setLng(33.427362);		
		obj.setLocation(myLocation);
		
		//RequestSpecBuilder
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();

		//ResponseSpecBuilder
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		/*
		RestAssured.baseURI = "https://rahulshettyacademy.com";		
		Response response = given().queryParam("key", "qaclick123")
		.body(obj)
		.when().post("maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response();
		*/
		
		RequestSpecification res = given().spec(req)
		.body(obj);
		
		
		 Response response = res
		.when().post("maps/api/place/add/json")
		.then().spec(resSpec).extract().response();
		
		
		String strResponse = response.asString();
		
		System.out.println("Response: "+strResponse);
		
	}

}
