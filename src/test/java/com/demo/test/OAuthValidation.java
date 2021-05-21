package com.demo.test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthValidation {

	@Test
	public void getAccessToken() {
		
		String[] courseTitle = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		/*
		System.setProperty("webdriver.chrome.driver", "D:/REST API Documents/chromedriver_win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		
		WebElement inputUsername =	driver.findElement(By.xpath(".//input[@id='identifierId']"));
		inputUsername.sendKeys("basusarani");
		WebElement btnNext = driver.findElement(By.xpath(".//span[text()='Next']/following-sibling::div"));
		btnNext.click();
		String currentURL = driver.getCurrentUrl();
		System.out.println(currentURL);
		*/
		
		String actualURL = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g6g9H1v6XBR50_xsNjA1G74MACb947dfVPTxX4q6eZYQU5sU2HCK6oqrvlnF-EUQg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String partialCode = actualURL.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println("Code: "+code);
		
		
		
	String access_token_resonse = given().urlEncodingEnabled(false)
			.queryParams("code",code)
			.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			.when().log().all().post("https://www.googleapis.com/oauth2/v4/token")
			.asString();
		
		JsonPath js = new JsonPath(access_token_resonse);
		String res_access_token = js.getString("access_token");
		System.out.println("res_access_token: "+res_access_token);
			
			String response = given().queryParam("access_token", res_access_token)
			.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
			
			System.out.println("response: "+response);
			
			//Deserialization
			GetCourse getCourseObj = given().queryParam("access_token", res_access_token).expect().defaultParser(Parser.JSON)
					.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);


			System.out.println("linkedIn "+getCourseObj.getLinkedIn());
			System.out.println("instructor "+getCourseObj.getInstructor());
			
			System.out.println("Api Course Title: "+getCourseObj.getCourses().getApi().get(1).getCourseTitle());
			List<Api> apiCourse = getCourseObj.getCourses().getApi();
			
			//print the price of get course "SoapUI Webservices testing"
			for(int i=0; i<apiCourse.size(); i++)
			{
				if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					System.out.println("gerPrice of Api: "+apiCourse.get(i).getPrice());
					
			}
			
			//print all the course titles of WebAutomation
			List<WebAutomation> webAutomation = getCourseObj.getCourses().getWebAutomation();
			ArrayList<String> a = new ArrayList<String>();
						
			for(int j=0; j<webAutomation.size(); j++)
			{
				System.out.println("WebAutomation courseTitles: "+webAutomation.get(j).getCourseTitle());
								a.add(webAutomation.get(j).getCourseTitle());
			}
			
			//converting expected course title into array list
			List<String> expectedList = Arrays.asList(courseTitle);
			Assert.assertTrue(a.equals(expectedList));
			
}
}
