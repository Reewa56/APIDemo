package com.demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EComAPITest {

	public static <ordersdetailsobj> void main(String[] args) {
		// TODO Auto-generated method stub

	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON)
				.build();
	
	 EComLoginPOJO obj = new EComLoginPOJO();
	 obj.setUserEmail("reewa56@gmail.com");
	 obj.setUserPassword("Neere@1993");
	 
	ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();
	 
	EComResPOJO responseobj = given().log().all().spec(req).body(obj).when().post("api/ecom/auth/login").then().spec(res).extract()
			.as(EComResPOJO.class);
	
	String token = responseobj.getToken();
	String userId = responseobj.getUserId();
	System.out.println(responseobj.getToken());
	System.out.println(responseobj.getUserId());
	System.out.println(responseobj.getMessage());
	
	// add product 
	
	RequestSpecification AddProductreq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
			      .addHeader("authorization", token).build();
	
	RequestSpecification reqAddProd = given().spec(AddProductreq).formParam("productName", "adidas original w").formParam("productAddedBy", userId)
	.formParam("productCategory", "fashion").formParam("productSubCategory", "shirts").formParam("productPrice", "11500")
	.formParam("productDescription", "Addias Originals W").formParam("productFor", "women").multiPart("productImage",new File("C://Users//sanga//OneDrive//Pictures//sign-444555555.jpg"));
	
	String ResAddProd = reqAddProd.when().post("api/ecom/product/add-product").then().log().all().extract().asString();
	
	JsonPath js = new JsonPath(ResAddProd);
	String productId = js.get("productId");
	System.out.println(productId);
			
	
	//Create order
	
	RequestSpecification ReqCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON)
			.addHeader("authorization", token).build();
	
	
	EComNestedOrdersPOJO ordersdetailsobj = new EComNestedOrdersPOJO();
	ordersdetailsobj.setCountry("India");
	ordersdetailsobj.setProductOrderedId(productId);
	List<EComNestedOrdersPOJO> ordersdetailList = new ArrayList<EComNestedOrdersPOJO>();
	ordersdetailList.add(ordersdetailsobj);
	
	EComOrdersPOJO ordersobj = new EComOrdersPOJO();
	ordersobj.setOrders(ordersdetailList);
	
	String ResponseOrders = given().log().all().spec(ReqCreateOrder).body(ordersobj).when().post("api/ecom/order/create-order").then().log().all().
	extract().asString();
	
	System.out.println(ResponseOrders);
	
	//delete product
	
	RequestSpecification ReqDelOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("authorization", token).build();
	
	String Delmsg = given().log().all().spec(ReqDelOrder).pathParam("productId", productId).when().delete("api/ecom/product/delete-product/{productId}").then().assertThat().statusCode(200).extract().asString();
	System.out.println(Delmsg);
	}

}
