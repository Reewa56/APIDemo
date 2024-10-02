package com.demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class JIRATest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://reewa56.atlassian.net/";

		String res =  given().log().all().header("Content-Type","application/json")
			.header("Authorization","Basic cmVld2E1NkBnbWFpbC5jb206QVRBVFQzeEZmR0Ywb0VyX2Zxa2dacDJmZ1UxOUZHZDVPZmdvclhHVFdMVmNhTkdIbll2bWs1b3FKbWUzRjlBZGU3ZUhoMWt3SEQydmVyTFJwd2I5TkRrM3MxMWFCdkh1VnBZZ1NnN0RudE9ObXoyLTFUXzhlTC1WdWlOcFBWTGV4OUxWQ211a3R3bEdsVGRqNjhmdGRacVBZcmFyRDJwQkx2Z1dkNzl4VlJqZVRrTnYxRlNVX2l3PTZCOEJGMEEx")
			.body("{\r\n"
					+ "    \"fields\": {\r\n"
					+ "       \"project\":\r\n"
					+ "       {\r\n"
					+ "          \"key\": \"SCRUM\"\r\n"
					+ "       },\r\n"
					+ "       \"summary\": \"Phir aya bug\",\r\n"
					+ "       \"issuetype\": {\r\n"
					+ "          \"name\": \"Bug\"\r\n"
					+ "       }\r\n"
					+ "   }\r\n"
					+ "}").when().post("rest/api/2/issue").then()
			.log().all().extract().response().asString();
			
		JsonPath js = new JsonPath(res);
		String BugId = js.getString("id");
		
		 given().header("X-Atlassian-Token","no-check")
				.header("Authorization","Basic cmVld2E1NkBnbWFpbC5jb206QVRBVFQzeEZmR0Ywb0VyX2Zxa2dacDJmZ1UxOUZHZDVPZmdvclhHVFdMVmNhTkdIbll2bWs1b3FKbWUzRjlBZGU3ZUhoMWt3SEQydmVyTFJwd2I5TkRrM3MxMWFCdkh1VnBZZ1NnN0RudE9ObXoyLTFUXzhlTC1WdWlOcFBWTGV4OUxWQ211a3R3bEdsVGRqNjhmdGRacVBZcmFyRDJwQkx2Z1dkNzl4VlJqZVRrTnYxRlNVX2l3PTZCOEJGMEEx")
				.multiPart("file",new File("D:/Reewa/IMG_0785.HEIC"))
				
				.when().post("rest/api/3/issue/"+BugId+"/attachments").then().log().all()
				.assertThat().statusCode(200);
				
	}

}
