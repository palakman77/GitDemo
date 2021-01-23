package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	@Test

	public static void addBook1() throws IOException 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json").
		body(GenerateStringFromResource("C:\\Users\\prav77\\Documents\\docs\\rest-api-udemy\\LibraryAPI\\Addbookdetail.json")).
		when()
		.post("/Library/Addbook.php")						//if aisle number given as numeric in payload, validation works. But alphanumeric, we can repeatedly add
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawtoJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}

	//for reading all bytes from a Json file and converts to String
	public static String GenerateStringFromResource(String path) throws IOException
	{	
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
	
}
	