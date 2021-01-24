package demo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; //imports all packages under restassured
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

// adding comments here to test version control with GITHUB
// blah blah blah


public class basics {

	public static void main(String[] args) {

		//validate if Add Place api is working as expected
		//3 methods are there in all rest api automations - any rest assured api automation should be written on these 3 principles
		//given - all input details need to submit an api
		//when - after loading details in given, use when to submit api - resource, http method. all remaining input details under given
		//then - validate the response
		
		//set your BaseURI
		//some packages in rest assured are static (eg.to import for given) so has to be manually written, no prompt, give static keyword 
		//equalto is also from static package hamcrust
		//chain up all the parameters etc. with given method
		//for body, eclipse should auto convert content with the syntax. if not, windows-preferences-editor-java-editor-typing-escape text when pasting
		//use log.all for making sure all details are seen - one for input details and one for output (then)
		//after assert, everything is validations
		//usually every api test we do validation of header/Server - to ensure the response is soming from an authenticated/right server and not a risk of hacking
		//any .header or methods given before 'then' is input. after 'then' is output validations
		//if running test cases in java, wrap everything in ps void main.. not needed if testng/junit used
		
		
		/*
		 RestAssured.baseURI = "https://rahulshettyacademy.com";
		 given().log().all().queryParam("key",
		 "qaclick123").header("Content-Type","application/json")
		 .body(payload.AddPlace()).when().post("maps/api/place/add/json")
		 .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		 .header("server","Apache/2.4.18 (Ubuntu)");
		 */
	
	//create a new class to store body details and only call that
	//if we make method static, we can directly call it by using classname.methodname
	//else we need to create object for the class and use it to call
	
		
		//Add place -> Update Place with new Address -> Get Place to validate if new address is present in Response 
		// 3 API's here in this test case - Add Place -> PUT Place -> GET Place to retrieve
		//Take same test above and expand it
		//after getting response body, need to parse the JSON to get value for place id
		
		
		 RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key",
		 "qaclick123").header("Content-Type","application/json")
		 .body(payload.AddPlace()).when().post("maps/api/place/add/json")
		 .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		 .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
	
		System.out.println(response);
		JsonPath js = new JsonPath(response); //for parsing the json and storing the parsed values in js
		
		//for parameters having parent, need to access using them - like location. else, like place/accuracy, directly can use
		
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		
		//Update Place
		
		String newAddress = "70 summer walk, USA";	
		given().log().all().queryParam("key",
				 "qaclick123").header("Content-Type","application/json")
				 .body("{\r\n" + 
				 		"\"place_id\":\""+placeID+"\",\r\n" + 
				 		"\"address\":\""+newAddress+"\",\r\n" + 
				 		"\"key\":\"qaclick123\"\r\n" + 
				 		"}").
				 when().put("maps/api/place/update/json")
				 .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
				 
						 
		//Get Place - no header needed, as everything is in URL, no body
		//Jsonpath is a class whihc expects String arg
			
		String getPlaceResponse = given().log().all().queryParam("key","qaclick123")
		.queryParam("place_id",placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawtoJson(getPlaceResponse); //make it more readable by using another class to process the response
		
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		//since we are out of get now, we need to use java framework for assertion method
		//we can use Cucumber junit/testng
		
		Assert.assertEquals(actualAddress, newAddress);
	
		
	}

}
