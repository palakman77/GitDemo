package demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		/*
		System.setProperty("webdriver.chrome.driver", "C://Users//prav77//Documents//docs//rest-api-udemy//selenium-java-3.141.59//chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("https://accounts.google.com/signin/oauth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&o2v=2&as=X_SfT5pcGDRKiBPxw__Z-A&flowName=GeneralOAuthFlow");
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("prav77");
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("123");
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
			Thread.sleep(4000);
			
			//split the whole URL into two parts either side of code. So, index 1 will give the section after code and index 0 before code
			//after that split partialcode by '&scopre' - index 0 of this will give the actual code we need
			//split is a java function
			//also, after this, need to tell restassured not to perform encoding, so that %,& etc are not changed. use urlEncodingEnabled(false)
			//but from 2020, a google update now does not allow user to login from automation script - username/password cannot be used
			
			String uri = driver.getCurrentUrl();
		
			*/
		
			String uri = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AGfW7hnVeCtPC8cISQctnip7zTlURip3dywqXpm5q4NOJiCTRoXg1gaouQo3ijNKKNJzqTMfYil2LMH28ouxRE&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
			String partialCode = uri.split("code=")[1];
			String code = partialCode.split("&scope")[0];
			System.out.println(code);
			
			//for client credentials grant type, directly start with the below section
			
			String accessTokenResponse = given().urlEncodingEnabled(false)
									.queryParams("code", code)
									.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
									.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
									.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
									.queryParams("grant_type", "authorization_code")
									.when().log().all()
									.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		
		
		String response = given().queryParam("access_token", accessToken)
							.when().log().all()
							.get("https://rahulshettyacademy.com/getCourse.php").asString();
				
		System.out.println(response);
		
	}

}
