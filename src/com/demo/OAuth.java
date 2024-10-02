package com.demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.List;

import com.POJO.API;
import com.POJO.GetCourses;
import com.POJO.WebAutomation;

public class OAuth {

	public static void main(String[] args) {                                                                                                                  
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		String res = given().log().all().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
				.then().extract().response().asString();
		
		JsonPath js = new JsonPath(res);
		String access_id = js.getString("access_token");
		
		GetCourses gc=given().queryParam("access_token", access_id)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all().extract().as(GetCourses.class);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getServices());
		System.out.println(gc.getUrl());
		System.out.println(gc.getCourses().getApi().getLast().getCourseTitle());
		
		List<API> apicourses = gc.getCourses().getApi();
		for(int i =0;i<apicourses.size();i++)
		{
			if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apicourses.get(i).getPrice());
			}
			
		}
		
		 List<WebAutomation> webatmnCourses =  gc.getCourses().getWebAutomation();
		 for(int j=0;j<webatmnCourses.size();j++)
		 {
			 System.out.println(webatmnCourses.get(j).getCourseTitle());
		 }
	}

}
