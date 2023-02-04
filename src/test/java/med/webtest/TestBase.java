package med.webtest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import med.webproperties.MedicarePropertyConfig;

public class TestBase {
	
	
	public static WebDriver driver;
	public static ChromeOptions options; 
	public static JavascriptExecutor js;

	public TestBase(){
		//System.setProperty(MedicarePropertyConfig.CHROMEDRIVER,MedicarePropertyConfig.CHROMEDRIVERURI);
		//driver = new ChromeDriver();		
	}
	
	protected void setUpBrowser() {
		options= new ChromeOptions();
		options.addArguments("--disable-notifications");		
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver(options);	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	public void takeScreenShots(String fileName, WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		String destFile = fileName + "-" + dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(scrFile, new File("./screenshotImages" + "\\" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
	}
	
	
	
	 /*@BeforeSuite(alwaysRun = true)
	  public void beforeSuite(ITestContext context) {
		  String chromdriver = context.getCurrentXmlTest().getParameter("selenium.chromedriver");
		  String chromedriverurl = context.getCurrentXmlTest().getParameter("selenium.chromedriverurl");
		  System.setProperty(chromdriver,chromedriverurl);
		  //System.setProperty("webdriver.chrome.driver",
					//"C:\\Users\\DELL\\eclipse-workspace\\BrowserDrivers\\chromedriver.exe");
		  System.out.println("I am in beforeSuite Method" +" - "+"beforeSuite");
	  }*/

}
