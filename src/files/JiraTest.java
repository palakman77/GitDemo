package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.Assert;

//whenever we give with {} - restAssured will check for any path parameter defined with that value
//use SessionFilter as shortcut to store session info and use it in other parts. it will store response info
//thus second place where its used will take logged in user info from the response already there

public class JiraTest {

	public static void main(String[] args) {

	//Login scenario
	//relaxedhttpsvalidation used for bypassing https even if no certificate. not required here, as we are running local server. but in real time maybe needed

		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
				
		String response = given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"prav77\", \"password\": \"pune1234\" }")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
	
	String expectedMessage = "Coomented";
	//Add comment
		String addCommentResponse = given().pathParam("key", "10200").log().all().header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \""+expectedMessage+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
					.extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String commentID = js.getString("id");
		
	//Add attachment
	//if created under project path, directly give filename. Else full path
	//header will be different here, as no body, it will be multipart
		
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10200")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("jira.txt")).when().
		post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
	//Get issue
	//no header needed as it's not POST
		
		String issueDetails = given().filter(session).pathParam("key", "10200").when().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		
		
		
	//if we want only specific fields returned, then use query parameter. this way, both path and query parameter can be used here
	//basically Path parameter helps to reroute the result to the specific sub-resource
	//query parameter will sort/drill down through the returned result
	//to verify output, copy output from below and paste in jsoneditor and checkl fields
		
		String issueDetails1 = given().filter(session).pathParam("key", "10200")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{key}")
				.then().log().all().extract().response().asString();
				
		System.out.println(issueDetails1);
		JsonPath js1 = new JsonPath(issueDetails1);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
		for (int i =0; i<commentsCount; i++)
		{
			String commentIDIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			if (commentIDIssue.equalsIgnoreCase(commentID))
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
			
		}
		
	}

}
