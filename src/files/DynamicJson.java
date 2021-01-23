package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
//import org.junit.Test;

//use freeformatter.com/json-escape.html for formatting payload info for java 


public class DynamicJson {
	

	
	/*
	 * public void addBook() { RestAssured.baseURI = "http://216.10.245.166"; String
	 * response = given().header("Content-Type","application/json").
	 * body(payload.Addbook()). when() .post("/Library/Addbook.php") //if aisle
	 * number given as numeric in payload, validation works. But alphanumeric, we
	 * can repeatedly add .then().log().all().assertThat().statusCode(200)
	 * .extract().response().asString();
	 * 
	 * //JsonPath js = ReusableMethods.rawtoJson(response); //String id =
	 * js.get("ID"); //System.out.println(id);
	 * 
	 * 
	 * }
	 */
/*	
	public static void addBook1() 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json").
		body(payload.Addbook("asda","37773")).
		when()
		.post("/Library/Addbook.php")						//if aisle number given as numeric in payload, validation works. But alphanumeric, we can repeatedly add
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawtoJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
	//deleteBook - POST - needed so that after adding, it will auto delete and will not throw error next time.
		/*
		 given().header("Content-Type","application/json").
		 body(payload.Delbook(id)). when() .post("/Library/DeleteBook.php")
		  .then().log().all().assertThat().statusCode(200)
		  .extract().response().asString();

		
		
		
}
*/

@Test(dataProvider = "BooksData")

	public static void addBook1(String isbn,String aisle) 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json").
		body(payload.Addbook(isbn,aisle)).
		when()
		.post("/Library/Addbook.php")						//if aisle number given as numeric in payload, validation works. But alphanumeric, we can repeatedly add
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawtoJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
	//deleteBook - POST - needed so that after adding, it will auto delete and will not throw error next time.
		/*
		 given().header("Content-Type","application/json").
		 body(payload.Delbook(id)). when() .post("/Library/DeleteBook.php")
		  .then().log().all().assertThat().statusCode(200)
		  .extract().response().asString();

		*/
		
		
}


@DataProvider(name = "BooksData")
public Object[][] getData()
{
	//array - collection of elements 
	//multidimensional array - collection of arrays - we will use each array to hold data for 1 record of books
	//this single step will take care of creating obj/initialising 
	//pass the same number and type of parameters to the function which calls this dataprovider.
	//can also create a similar one for delete book with same data
	
	return new Object[][] {{"iwed", "24231"},{"rewtq", "54353"}};
}


/*
  public void DelBook()
  { 
	  
	  RestAssured.baseURI = "http://216.10.245.166";
	  DynamicJson.addBook1(id);
	  
	  given().header("Content-Type","application/json").
			  body(payload.Delbook()). when() .post("/Library/DeleteBook.php")
			  .then().log().all().assertThat().statusCode(200)
			  .extract().response().asString();
  
  //JsonPath js = ReusableMethods.rawtoJson(response); //String id =
  //js.get("ID"); //System.out.println(id);
  
  
  }
 
*/
}
	