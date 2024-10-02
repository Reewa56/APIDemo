package com.demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import com.files.Payload;

public class Basics {

	public static void main (String[] args)
	{
		//Validate addplace API
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given()
				.log()
				.all()
				.queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(Payload.Addplace())
				.when()
				.post("maps/api/place/add/json")
				.then()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract()
				.response()
				.asString();
		
		 System.out.println("Response : " + response);    
		 
		 		JsonPath js = new JsonPath(response);
		 		String placeID = js.getString("place_id");
		 		System.out.println(placeID);
		 		
		 		String newAdd = "70 winter walk, USA";
		 		
		 		given().log().all()
		 		.queryParam("key", "qaclick123")
		 		.header("Content-Type","application/json")
		 		.body("{\r\n"
		 				+ "    \"place_id\": \"" + placeID + "\",\r\n"
		 				+ "    \"address\": \""+newAdd+"\",\r\n"
		 				+ "    \"key\": \"qaclick123\"\r\n"
		 				+ "}")
		 		.when().put("maps/api/place/update/json")
		 		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		 		
		 		String getResponse = given().log().all()
		 		.queryParam("key", "qaclick123")
		 		.queryParam("place_id", placeID)
		 		.when().get("maps/api/place/get/json")
		 		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		 		//.body("address", equalTo("70 winter walk, USA"));
		 		
		 		JsonPath js1 = new JsonPath(getResponse);
		 		String updatedAdd = js1.getString("address");
		 		System.out.println(updatedAdd);
		 		Assert.assertEquals(updatedAdd, newAdd);
		 		
		 		
		 		
		 		
} 
}
