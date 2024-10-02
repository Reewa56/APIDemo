package com.Serialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.List;

import com.files.Payload;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Googlemaps {
	public static void main (String[] args)
	{
		PlacePOJO obj = new PlacePOJO();	
		obj.setAccuracy(50);
		obj.setAddress("delhi");
		obj.setLanguage("hindi");
		
		obj.setName("reewa");
		obj.setPhone_number("0098");
		
		obj.setWebsite("www.ojk.com");
		
		List<String> list = new ArrayList<String>();
		list.add("okie");
		list.add("oki");
		obj.setTypes(list);
		
		location l = new location();
		l.setLat(38.099876);
		l.setLng(57.988867);
		
		
		// Request Spec builder implementation
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		obj.setLocation(l);
		
		String response = given().spec(req).body(obj).when()
		  .post("maps/api/place/add/json").then().spec(res)  
		  .body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
		  .extract().response().asString();
		
		
		/*
		 * //RestAssured.baseURI = "https://rahulshettyacademy.com/"; 
		 * String response =
		 * given() .log() .all() .queryParam("key",
		 * "qaclick123").header("Content-Type","application/json") .body(obj) .when()
		 * .post("maps/api/place/add/json") .then() .assertThat() .statusCode(200)
		 * .body("scope", equalTo("APP")) .header("Server", "Apache/2.4.52 (Ubuntu)")
		 * .extract() .response() .asString();
		 */
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		System.out.println(js.getString("status"));
	}
}
