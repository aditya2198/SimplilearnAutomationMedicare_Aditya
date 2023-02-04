package med.webpages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class MedicareLoginPage extends TestPageBase {
	
	private final String loginBtnXpath = "//input[@type='submit']";
	private final String loginNameXpath = "//li[@id='userModel']/a";
	private final String waitProductMvBtn = "//div[@ng-repeat='product in pCtrl.mvProducts']/parent::div/div/a[text()='More Products']";
	private final String logoutBtnXpath = "//li[@id='logout']";
	
	@FindBy(xpath="//input[@type='submit']") WebElement loginBtn;
	@FindBy(xpath="//input[@id='username']") WebElement userNameEl;
	@FindBy(xpath="//input[@id='password']") WebElement passwordEl;
	@FindBy(xpath="//li[@id='userModel']/a") WebElement expLoginNameEl;
	@FindBy(xpath="//li[@id='logout']") WebElement logoutEl;
	@FindBy(xpath="//a[contains(text(),'Login')]") WebElement loginLinkEl;
	@FindBy(xpath="//div[@class='alert alert-danger fade in']") WebElement errorMessage;
	
	public MedicareLoginPage(WebDriver driver){
		super(driver);
	}	
	public Logger log = Logger.getLogger(MedicareLoginPage.class);
	
	public void validateLoginExistingUser(String userName,String passWord,String expDisplayName) throws InterruptedException {
		waitForElementToBeClickable("//a[contains(text(),'Login')]");
		loginLinkEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys(userName);
		passwordEl.sendKeys(passWord);
		loginBtn.click();
		waitForPageToBeVisible(waitProductMvBtn);
		Assert.assertEquals(expLoginNameEl.getText(),expDisplayName);
		clickElementWithFluentWait(loginNameXpath);
		log.info("user logged in "+ expDisplayName);
		waitForElementToBeClickable(logoutBtnXpath);		
		logoutEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		Assert.assertTrue(_driver.getCurrentUrl().contains("logout"));
		
	}
	
	public void loginUser(String userName,String passWord) {
		waitForElementToBeClickable("//a[contains(text(),'Login')]");
		loginLinkEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys(userName);
		passwordEl.sendKeys(passWord);
		loginBtn.click();
		waitForPageToBeVisible(waitProductMvBtn);
		
	}
	
	public void loginUserinvalid(String userName,String passWord) {
		waitForElementToBeClickable("//a[contains(text(),'Login')]");
		loginLinkEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys(userName);
		passwordEl.sendKeys(passWord);
		loginBtn.click();
		waitForElementToBeVisible(errorMessage);
		String error =errorMessage.getText();
		System.out.println(error);
		Assert.assertEquals("Username and Password is invalid!", error);
		log.info(error);
		
		
	}
	
	public void logoutUser() {
		
		clickElementWithFluentWait(loginNameXpath);
		waitForElementToBeClickable(logoutBtnXpath);		
		logoutEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		
	}
	
	public void clickLoginName() {
		
		clickElementWithFluentWait(loginNameXpath);		
	}
	

}
