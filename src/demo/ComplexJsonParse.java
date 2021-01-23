package demo;
import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath js3 = new JsonPath(payload.CoursePrice()); //successfully mocked the response because api is not ready
		
		//print number of courses
		int count = js3.getInt("courses.size()"); //  size is a method used in jsonpath to get the count - can be applied only on arrays, number of items in array - in this case, for courses, 3
		System.out.println(count);
	
		//print purchase amount - present under dashboard
		int totalAmount = js3.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//print title of first course - present in 0 index
		String titleFirstCourse = js3.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//print all courses and their respective prices
		for(int i = 0;i<count;i++)
		{
			System.out.println(js3.get("courses["+i+"].title").toString()); //for concatenating a variable 'i' in between a string, use +
			System.out.println(js3.get("courses["+i+"].price").toString());
			
		}
	
		//print number of copies sold by RPA course
		for(int i = 0;i<count;i++)
		{
			String courseTitles = js3.get("courses["+i+"].title"); //for concatenating a variable 'i' in between a string, use +

			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				int copies = js3.get("courses["+i+"].copies");
				System.out.println(copies);
				break;  //optimize the code so that it doesn't iterate the whole list
						
			}
			
		//Verify if Sum of all Course prices matches with Purchase Amount
			
		
			
		}
		
	
	}

	
}
