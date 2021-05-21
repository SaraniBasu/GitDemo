package com.demo.test;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class SerializeTest {
	
	@Test
	public void serializeTest()
	{
		System.out.println("postJira6");
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

		RestAssured.baseURI = "https://rahulshettyacademy.com";		
		Response response = given().queryParam("key", "qaclick123")
		.body(obj)
		.when().post("maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response();
		
		String strResponse = response.asString();
		
		System.out.println("Response: "+strResponse);
		
	}

}
