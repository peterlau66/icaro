//Created by Peter Liu
//Feb-10-2025 
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class IcaroTest {
	  public static void main(String args[]) {
		  JUnitCore junit = new JUnitCore();
		  junit.run(IcaroTest.class);
	  };
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	//System.setProperty("webdriver.chrome.driver","C:\\work\\chromedriver-win32\\chromedriver-win64\\chromedriver.exe");	  
    driver = new ChromeDriver();
    
    //the following setting for headless, need to comment the above line
    //ChromeOptions chromeOptions = new ChromeOptions();
    //chromeOptions.addArguments("--headless");
    //driver = new ChromeDriver(chromeOptions);
    //driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS); 
   
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void icaro() throws InterruptedException, IOException {
    driver.get("https://www.demoblaze.com/index.html");
    driver.manage().window().maximize();
    // click login link
    driver.findElement(By.id("login2")).click();
    Thread.sleep(1000);
    driver.findElement(By.id("loginusername")).click();
    driver.findElement(By.id("loginusername")).sendKeys("peterliu");
    driver.findElement(By.id("loginpassword")).click();
    driver.findElement(By.id("loginpassword")).sendKeys("P@ssw0rd");
    // click login button
    driver.findElement(By.cssSelector("#logInModal .btn-primary")).click();
    Thread.sleep(1000);
    // start data driven, the first column is catp[0] from icaro.csv
    // start data driven, the second column is catp[1] from icaro.csv
    // start data driven, the third column is catp[2] from icaro.csv
	String csvFile = "/work/icaro.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
        String[] catp = line.split(cvsSplitBy);

    //Click linkText: Phones, Laptops, Monitors (the first column of icaro.csv) 
    Thread.sleep(1000);
    driver.findElement(By.linkText(catp[0])).click();
    Thread.sleep(1000);
    //click product name, the second column of icaro.csv
    driver.findElement(By.linkText(catp[1])).click();
    Thread.sleep(1000);
    driver.findElement(By.linkText("Add to cart")).click();
    Thread.sleep(1000);
    // wait until pop up window 
    while(driver.switchTo().alert()== null){
    };
    // pop up window, accept it
    if(driver.switchTo().alert() != null)
{
    Alert alert = driver.switchTo().alert();
    //String alertText = alert.getText();
	//System.out.println(alertText);
    alert.accept();
    Thread.sleep(1000);
}
    //Click Cart Link
    driver.findElement(By.id("cartur")).click();
    //Click Place Order Button
    driver.findElement(By.cssSelector(".btn-success")).click();
    Thread.sleep(1000);
    //driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Peter Liu");
    driver.findElement(By.id("country")).sendKeys("Canada");
    driver.findElement(By.id("city")).sendKeys("Scarborough");
    //driver.findElement(By.id("card")).click();
    driver.findElement(By.id("card")).sendKeys("1234567890");
    driver.findElement(By.id("month")).sendKeys("02");
    driver.findElement(By.id("year")).sendKeys("2026");
    // Click Purchase Button
    driver.findElement(By.cssSelector("#orderModal .btn-primary")).click();
    // Verify order summary
    Thread.sleep(1000);
    String bodyText = driver.findElement(By.cssSelector("body > div.sweet-alert.showSweetAlert.visible > p")).getText();
    //if we don't want stop test whenever assertion failed. use try / catch
    try {
    	//assertTrue(bodyText.contains(the third column of icaro.csv));} 
    	assertTrue(bodyText.contains(catp[2]));}
    	catch (AssertionError e) {
       	String message = e.getMessage();
    	System.out.println("Assertion Amount failed: " + message);
    	}
    try {
    	assertTrue(bodyText.contains("Card Number: 1234567890"));} 
    	catch (AssertionError e) {
       	String message = e.getMessage();
    	System.out.println("Assertion Card Number failed: " + message);
    	}
    try {
    	assertTrue(bodyText.contains("Name: Peter Liu"));} 
    	catch (AssertionError e) {
       	String message = e.getMessage();
    	System.out.println("Assertion Name failed: " + message);
    	}
    try {
        assertTrue(bodyText.contains("Date: 10/1/2025"));} 
    	catch (AssertionError e) {
       	String message = e.getMessage();
    	System.out.println("Assertion Date failed: " + message);
    	}
    //if we want to stop test whenever assertion failed.
    //assertTrue(bodyText.contains(catp[2])); 
    //assertTrue(bodyText.contains("Card Number: 1234567890"));    
    //assertTrue(bodyText.contains("Name: Peter Liu")); 
    //assertTrue(bodyText.contains("Date: 10/1/2025")); 
    //Assert.assertTrue(bodyText.contains("Amount: 360 USD"));
    Thread.sleep(1000);
    
    // Click OK Button
    driver.findElement(By.cssSelector(".confirm")).click();
    
	}; //end of while, data driven
    
    Thread.sleep(1000);
    // Click logout Button
    driver.findElement(By.id("logout2")).click();
    // close browser
    driver.close();
  }
}
