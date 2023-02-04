package med.webpages;

import java.time.Duration;
import java.util.HashMap;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import med.webtest.TestBase;

public class MedicareSignupPage extends TestPageBase {
	
	private final String signupBtnXpath = "//button[@type='submit']";
	private final String confirmAddrBtnXpath = "//button[@type='submit' and contains(@name,'confirm')]";
	private final String confirmBtnXpath = "//a[text()='Confirm']";
	private final String loginRdBtnXpath = "//a[text()='Login Here']";
	private final String loginBtnXpath = "//input[@type='submit']";
	private final String loginNameXpath = "//li[@id='userModel']/a";
	private final String waitProductMvBtn = "//div[@ng-repeat='product in pCtrl.mvProducts']/parent::div/div/a[text()='More Products']";
	private final String logoutBtnXpath = "//li[@id='logout']";
	private final String confirmCountryFldXpath = "//input[@id='country']";
		
	@FindBy(xpath="//a[text()='Sign Up']") WebElement signUpLink;
	@FindBy(xpath="//button[@type='submit']") WebElement signUpBtn;	
	@FindBy(xpath="//input[@id='firstName']") WebElement firstName;
	@FindBy(xpath="//input[@id='lastName']") WebElement lastName;
	@FindBy(xpath="//input[@id='email']") WebElement email;
	@FindBy(xpath="//input[@id='contactNumber']") WebElement contactNumber;
	@FindBy(xpath="//input[@id='password']") WebElement passwd;
	@FindBy(xpath="//input[@id='confirmPassword']") WebElement confPasswd;
	@FindBy(xpath="//input[@type='radio'and@id='role1']") WebElement userRole;
	@FindBy(xpath="//input[@type='radio'and@id='role2']") WebElement supplierRole;
	@FindBy(xpath="//button[@type='submit' and contains(@name,'confirm')]") WebElement confirmAddrBtn;
	@FindBy(xpath="//a[@href='/medicare/cart/add/4/product']") WebElement AddToCart;
	@FindBy(xpath="//div[@class='jumbotron']//h1") WebElement supplierError;
	@FindBy(xpath="//div[@class='jumbotron']//blockquote") WebElement supplierErrorMsg;
	
	
	
	
	@FindBy(xpath="//input[@id='addressLineOne']") WebElement addrOne;
	@FindBy(xpath="//input[@id='addressLineTwo']") WebElement addrTwo;
	@FindBy(xpath="//input[@id='city']") WebElement city;
	@FindBy(xpath="//input[@id='postalCode']") WebElement posCode;
	@FindBy(xpath="//input[@id='state']") WebElement state;
	@FindBy(xpath="//input[@id='country']") WebElement country;
	@FindBy(xpath="//a[text()='Confirm']") WebElement confirmBtn;
	@FindBy(xpath="//h3[contains(text(),'Name')]/strong") WebElement expNameEl;
	@FindBy(xpath="//h4[contains(text(),'Email')]/strong") WebElement expEmailEl;
	@FindBy(xpath="//h4[contains(text(),'Contact')]/strong") WebElement expContactEl;
	@FindBy(xpath="//h4[contains(text(),'Role')]/strong") WebElement expRoleEl;	
	@FindBy(xpath="//h4[text()='Billing Address']/parent::div/following-sibling::div/div/p[1]") WebElement expAddr1El;
	@FindBy(xpath="//h4[text()='Billing Address']/parent::div/following-sibling::div/div/p[2]") WebElement expAddr2El;
	@FindBy(xpath="//h4[text()='Billing Address']/parent::div/following-sibling::div/div/p[3]") WebElement expCityZipEl;
	@FindBy(xpath="//h4[text()='Billing Address']/parent::div/following-sibling::div/div/p[4]") WebElement expStateEl;
	@FindBy(xpath="//h4[text()='Billing Address']/parent::div/following-sibling::div/div/p[5]") WebElement expCountryEl;
	@FindBy(xpath="//a[text()='Login Here']") WebElement loginRdBtn;	
	@FindBy(xpath="//h1") WebElement expHeadingEl;
	@FindBy(xpath="//h3") WebElement expParaEl;
	@FindBy(xpath="//h6") WebElement expDescEl;
	@FindBy(xpath="//input[@type='submit']") WebElement loginBtn;
	@FindBy(xpath="//input[@id='username']") WebElement userNameEl;
	@FindBy(xpath="//input[@id='password']") WebElement passwordEl;
	@FindBy(xpath="//li[@id='userModel']/a") WebElement expLoginNameEl;
	@FindBy(xpath="//li[@id='logout']") WebElement logoutEl;
	@FindBy(xpath="//li[@id='login']/a") WebElement loginLinkEl;
	@FindBy(xpath="//a[@ng-href='/medicare/show/4/product']") WebElement viewProduct;
	
	

	public MedicareSignupPage(WebDriver driver){
		super(driver);
	}
	

	public Logger log = Logger.getLogger(MedicareSignupPage.class);
	
	public void validateLoginExistingUser() throws InterruptedException {
		loginLinkEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys("kn@gmail.com");
		passwordEl.sendKeys("12345");
		loginBtn.click();
		waitForPageToBeVisible(waitProductMvBtn);
		Assert.assertEquals(expLoginNameEl.getText(),"Kavita Nigam");
		viewProduct.click();
		Thread.sleep(3000);
		takeScreenShots("UserImage", _driver);
		Assert.assertEquals(AddToCart, true);
		clickElementWithFluentWait(loginNameXpath);
		waitForElementToBeClickable(logoutBtnXpath);		
		logoutEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		Assert.assertTrue(_driver.getCurrentUrl().contains("logout"));		
	}
	
	public void validateLoginExistingSupplier() throws InterruptedException {
		loginLinkEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys("rj@gmail.com");
		passwordEl.sendKeys("12345");
		loginBtn.click();
		waitForPageToBeVisible(waitProductMvBtn);
		Assert.assertEquals(expLoginNameEl.getText(),"Ravindra Jadeja");
		log.info(expLoginNameEl.getText());
		viewProduct.click();
		takeScreenShots("SupplierImage", _driver);
		
		_driver.findElement(By.xpath("//a[@href='/medicare/show/all/products']")).click();
		waitForElementToBeVisible(AddToCart);
		AddToCart.click(); 
		waitForElementToBeVisible(supplierError);
		log.info(supplierError.getText());
		takeScreenShots("SupplierError", _driver);
		String errormsg = supplierErrorMsg.getText();
		Assert.assertEquals(errormsg.contains(errormsg), true);
		
		navigateMedicare();
		clickElementWithFluentWait(loginNameXpath);
		waitForElementToBeClickable(logoutBtnXpath);		
		logoutEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		Assert.assertTrue(_driver.getCurrentUrl().contains("logout"));		
	}
	
	
	
    public void validateSignUpNewUser(HashMap<String,String> expHashMapVal) throws InterruptedException {
		
		signUpLink.click();
		waitForPageToBeVisible(signupBtnXpath);
		firstName.sendKeys(expHashMapVal.get("firstName"));
		lastName.sendKeys(expHashMapVal.get("lastName"));
		email.sendKeys(expHashMapVal.get("email"));
		contactNumber.sendKeys(expHashMapVal.get("contactNumber"));
		passwd.sendKeys(expHashMapVal.get("password"));
		confPasswd.sendKeys(expHashMapVal.get("confirmPassword"));
		selectRadio(expHashMapVal.get("selectRole"));
		waitForElementToBeClickable(signupBtnXpath);
		signUpBtn.click();
		waitForPageToBeVisible(confirmCountryFldXpath);
		addrOne.sendKeys(expHashMapVal.get("addrLine1"));
		addrTwo.sendKeys(expHashMapVal.get("addrLine2"));
		city.sendKeys(expHashMapVal.get("city"));
		posCode.sendKeys(expHashMapVal.get("postalCode"));
		state.sendKeys(expHashMapVal.get("state"));
		country.sendKeys(expHashMapVal.get("country"));
		waitForElementToBeClickable(confirmAddrBtnXpath);
		confirmAddrBtn.click();
		waitForPageToBeVisible(confirmBtnXpath);
		Assert.assertEquals(expNameEl.getText(),expHashMapVal.get("expName"));
		log.info(expNameEl.getText());
		Assert.assertEquals(expEmailEl.getText(),expHashMapVal.get("expEmail"));
		log.info(expEmailEl.getText());
		Assert.assertEquals(expContactEl.getText(),expHashMapVal.get("expContact"));
		log.info(expContactEl.getText());
		Assert.assertEquals(expRoleEl.getText(),expHashMapVal.get("expRole"));
		log.info(expRoleEl.getText());
		Assert.assertTrue(expAddr1El.getText().contains(expHashMapVal.get("expAddr1")));
		log.info(expAddr1El.getText());
		Assert.assertTrue(expAddr2El.getText().contains(expHashMapVal.get("expAddr2")));
		log.info(expAddr2El.getText());
		/*System.out.println(expCityZipEl.getText());
		System.out.println(expHashMapVal.get("expCityZip"));*/
		Assert.assertTrue(expCityZipEl.getText().contains(expHashMapVal.get("expCityZip")));
		log.info(expCityZipEl.getText());
		Assert.assertTrue(expStateEl.getText().contains(expHashMapVal.get("expState")));
		log.info(expStateEl.getText());
		Assert.assertTrue(expCountryEl.getText().contains(expHashMapVal.get("expCountry")));
		log.info(expCountryEl.getText());
		waitForElementToBeClickable(confirmBtnXpath);
		confirmBtn.click();
		waitForPageToBeVisible(loginRdBtnXpath);
		Assert.assertTrue(expHeadingEl.getText().contains(expHashMapVal.get("expHeading")));
		log.info(expHeadingEl.getText());
		Assert.assertTrue(expParaEl.getText().contains(expHashMapVal.get("expPara")));
		log.info(expParaEl.getText());
		Assert.assertTrue(expDescEl.getText().contains(expHashMapVal.get("expDesc")));
		log.info(expDescEl.getText());
		//waitForElementToBeClickable(loginRdBtnXpath);
		//loginRdBtn.click();
		clickElementWithFluentWait(loginRdBtnXpath);
		waitForPageToBeVisible(loginBtnXpath);
		userNameEl.sendKeys(expHashMapVal.get("email"));
		passwordEl.sendKeys(expHashMapVal.get("password"));
		waitForElementToBeClickable(loginBtnXpath);
		loginBtn.click();
		waitForPageToBeVisible(waitProductMvBtn);
		Assert.assertEquals(expLoginNameEl.getText(),expHashMapVal.get("expLoginName"));
		//waitForElementToBeClickable(loginNameXpath);
		//expLoginNameEl.click();
		clickElementWithFluentWait(loginNameXpath);
		waitForElementToBeClickable(logoutBtnXpath);		
		logoutEl.click();
		waitForPageToBeVisible(loginBtnXpath);
		Assert.assertTrue(_driver.getCurrentUrl().contains("logout"));		
				
	}	
    
    private void selectRadio(String role) {
		
		if(role.equals("User")) {
			if(!userRole.isSelected()) {
				userRole.click();
			}
		}
		
		if(role.equals("Supplier")) {
			if(!supplierRole.isSelected()) {
				supplierRole.click();
			}
		}
	}

}
