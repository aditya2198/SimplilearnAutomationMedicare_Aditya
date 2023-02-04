package med.webpages;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import med.webproperties.MedicarePropertyConfig;

public class TestPageBase {
	
    protected WebDriver _driver;
    private String fluentClickXpathStr = "";
    private String fluentFindXpathStr = "";
    	

	public TestPageBase(WebDriver driver){
		this._driver=driver;
	}
	
	
	public void navigateMedicare() throws InterruptedException {
		_driver.get(MedicarePropertyConfig.MEDICARETESTURL);
		_driver.manage().window().maximize();
		_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(2000);
	}
	
	public void closeMeidcare() {
		_driver.quit();
	}
	
	protected void waitForPageToBeVisible(String xpathStr) {
		WebDriverWait wait = new WebDriverWait(_driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitProductMvBtn)));
	}	
		
	protected void waitForElementToBeClickable(String xpathStr) {
		WebDriverWait wait = new WebDriverWait(_driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStr)));
		
	}
	
	protected void waitForElementToBeClickable2(WebElement element) {
		WebDriverWait wait = new WebDriverWait(_driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		
	}
	
	protected void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(_driver,30);
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	protected void clickElementWithFluentWait(String xpathStr) {		
		  
		  fluentClickXpathStr = xpathStr;
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(_driver)
			       .withTimeout(Duration.ofSeconds(30))
			       .pollingEvery(Duration.ofMillis(600))
			       .ignoring(ElementClickInterceptedException.class);
		  
		  wait.until(new Function<WebDriver, Boolean>() {
			     public Boolean apply(WebDriver _driver) {
			    	 _driver.findElement(By.xpath(fluentClickXpathStr)).click();	 
			       //_driver.findElement(By.xpath(loginNameXpath)).click();	 	 
			       return true;
			     }
			   });  
			 	
	}
	
	protected void findElementWithFluentWait(String xpathStr) {		
		  
		 fluentFindXpathStr = xpathStr;
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(_driver)
			       .withTimeout(Duration.ofSeconds(30))
			       .pollingEvery(Duration.ofMillis(600))
			       .ignoring(NoSuchElementException.class);
		  
		  wait.until(new Function<WebDriver, Boolean>() {
			     public Boolean apply(WebDriver _driver) {
			    	 _driver.findElement(By.xpath(fluentFindXpathStr));	 
			       //_driver.findElement(By.xpath(loginNameXpath)).click();	 	 
			       return true;
			     }
			   });  
			 	
	}
	
	
	
	protected String executeJsScript(String strJs) {
		
		JavascriptExecutor jsExecutor = (JavascriptExecutor) _driver;  
		String txtVal = (String) jsExecutor.executeScript(strJs);  
		return txtVal;
	}
	
	protected void execJsScript(String strJs) {
		
		JavascriptExecutor js = (JavascriptExecutor) _driver;
	 	   js.executeScript(strJs);
		
	}
	
	
	//document.querySelector("input[type='number']").value = 2
	
	
	protected void moveToElement(WebElement el) {
    	
   	 Actions actions = new Actions(_driver);
   		actions.moveToElement(el);
   		actions.perform();    	
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
	
	
}
