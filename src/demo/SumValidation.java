package demo;
//<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;
//import org.junit.Test;


public class SumValidation {

	@Test
	public void sumofcourses(){
		
		int sum = 0;
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");	
		
		
		for(int i=0;i<count;i++)
		{
			int price = js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			int amount = price*copies;
			System.out.println(amount);
			sum = sum + amount;
			
		}
		
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum,purchaseAmount);
	
	
	}
	
	
	
}
