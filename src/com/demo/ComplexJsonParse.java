package com.demo;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.files.Payload;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//print no. of courses returned by API
		JsonPath js = new JsonPath(Payload.JsonCourse());
		int cnt = js.getInt("courses.size()");
		System.out.println(cnt);
		
		//PRINT purchase amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount); 
		
		//print title of first course
		String title = js.getString("courses[0].title");
		System.out.println(title);
		
		//print all course titles and their respective prices
		for (int i = 0;i<cnt ;i++)
		{
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getString("courses["+i+"].price"));
		}
		
		//print num of copies sold by RPA course
		for (int i = 0;i<cnt ;i++)
		{
			if(js.getString("courses["+i+"].title").equals("RPA"))
			{
				System.out.println(js.getString("courses["+i+"].copies"));
				break;
			}
		}
		
		//verify if sum of all course prices matches with pirchase amnt
		int sum_courses = 0;
		for (int i = 0;i<cnt ;i++)		
		{
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			sum_courses = sum_courses + (price*copies);
			System.out.println(sum_courses);
		}
		Assert.assertEquals(sum_courses, amount);

}}
