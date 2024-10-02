package com.demo;

import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.files.Payload;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Library {
	
	@Test(dataProvider="BooksData")
	public void AddBook(String isbn, String aisle )
	{
		RestAssured.baseURI = "http://216.10.245.166";
		Response res = given().log().all().header("Content-Type","application/json")
		.body(Payload.LibraryLoad(isbn,aisle)).when().post("Library/Addbook.php")
		.then().log().all().extract().response();	
	}

	@DataProvider(name="BooksData")
	public  Object[][] getdata()
	{
		return new Object[][] {{"bni","665"},{"oiu","375"},{"oyu","736"}};
	}
}