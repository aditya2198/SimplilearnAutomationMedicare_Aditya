package med.webpages;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class MedicareUserPage extends TestPageBase {
	
	private final String cartLinkXpathBuilder = "//li[@id='cart']/a";
	private final String usrNameLinkXpathBuilder = "//li[@id='userModel']";
	private final String cartEmptyDescXpathBuilder = "//h3";
	private final String cartDelDescXpathBuilder = "//div[@class='content']/div/div[2]/h3";	
	private final String cartQtyXpathBuilder = "//h6";
	private final String cartTotalBadgeJsBuilder = "return document.querySelector('li#cart > a > span.badge').textContent";
	private final String cartTotalJsBuilder = "return document.querySelector('table#cart > tfoot > tr + tr strong').textContent";
	private final String cartBdgTotalJsBuilder = "return document.querySelector('li#cart > a').textContent";
	private final String pdDetlConXpathBuilder = "//a[contains(text(),'Continue Shopping')]";
	private final String pdDetlCartXpathBuilder = "//a[text()=' Add to Cart']";
	private final String tableCartRowBuilder = "//table[@id='cart']/tbody/tr[";
	private final String cartDelBtnXpathBuilder = "//table[@id='cart']/tbody/tr/td[last()]/a";
	private final String cartUpdBtnXpathBuilder = "//table[@id='cart']/tbody/tr/td[last()]/button";
	private final String cartPdtQtyJsBuilder = "document.querySelector(\"input[type='number']\").value =";
	
	
	
	
	@FindBy(xpath="//li[@id='cart']/a") WebElement cartTotalAmmountEl;
	@FindBy(xpath="//li[@id='cart']/a/span/following-sibling::span") WebElement cartTotalBadgeEl;
	@FindBy(xpath="//h3") WebElement cartEmptyDescEl;
	@FindBy(xpath="//table[@id='productListTable']/tbody/tr[2]/td[last()]/a") WebElement pdtDetailLinkEl;
	@FindBy(xpath="//table[@id='productListTable']/tbody/tr[2]/td[last()]/a/following-sibling::a") WebElement pdtCartLinkEl;	
	@FindBy(xpath="//h3/parent::div/p") WebElement cartPdtDetlDescEl;
	@FindBy(xpath="//h4/strong") WebElement cartPdtDetlPriceEl;
	@FindBy(xpath="//h6") WebElement cartPdtDetlQtyEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Quantity']/input") WebElement pdtCheckoutQtyEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[last()]/button") WebElement cartRowUpdtBtnEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Product']/div/div/following-sibling::div/h4") WebElement cartRowPdtNameEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Product']/div/div/following-sibling::div/p") WebElement cartRowPdtBrndEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Product']/div/div/following-sibling::div/p/following-sibling::p") WebElement cartRowPdtDescEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Price']") WebElement cartRowPdtPriceEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[@data-th='Subtotal']") WebElement cartRowSubTotlEl;
	@FindBy(xpath="table[@id='cart']/tfoot/tr[last()]//strong") WebElement cartSumTotalEl;
	@FindBy(xpath="//table[@id='cart']/tbody/tr/td[last()]/a") WebElement cartRowDelBtnEl;
	@FindBy(xpath="//div[@class='content']/div/div[2]/h3") WebElement cartDelEmptyDescEl;
	@FindBy(xpath="//a[contains(@href, 'remove')]") WebElement removeFromCart;
	@FindBy(xpath="//li[@class='dropdown open']/a") WebElement dropdownLogin;

	public MedicareUserPage(WebDriver driver){
		super(driver);
	}	
	
	public Logger log = Logger.getLogger(MedicareUserPage.class);
	
	public void validateCartInfo(String cartTotalItems, String cartTotalAmt, String cartEmptyDesc, String cartEmptyUrl, MedicareLoginPage loginPage) throws InterruptedException {
		
		Assert.assertEquals(cartTotalItems, executeJsScript(cartTotalBadgeJsBuilder));
		Assert.assertTrue(cartTotalAmt.contains(cartTotalAmmountEl.getText()));
		log.info("cartTotalAmmountEl "+cartTotalAmmountEl );
		takeScreenShots("TotalCartItems", _driver);
		clickCartUserName(loginPage);		
		waitForPageToBeVisible(cartEmptyDescXpathBuilder);
		Assert.assertTrue(cartEmptyDescEl.getText().contains(cartEmptyDesc));
		log.info("cartEmptyDescEl "+cartEmptyDescEl.getText() );
		takeScreenShots("cartEmpty", _driver);
		Assert.assertTrue(_driver.getCurrentUrl().contains(cartEmptyUrl));
		takeScreenShots("cartEmpty_01", _driver);
	}
	
	public void validateUserProductDetail(HashMap<String,String> hashMapProductVal, MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
		waitForPageToBeVisible(usrNameLinkXpathBuilder);
		viewProductPage.clickViewProductLink();
//		viewProductPage.selectDropdownViewProduct(hashMapProductVal.get("dropdownSelectValue"));
		viewProductPage.selectPageViewProduct(hashMapProductVal.get("pageNumber"));
		viewProductPage.clickViewProductDetail(hashMapProductVal.get("rowNumber"));
		waitForPageToBeVisible(pdDetlConXpathBuilder);
		//findElementWithFluentWait(pdDetlConXpathBuilder);		
		Assert.assertEquals(hashMapProductVal.get("productName"),cartEmptyDescEl.getText());
		Assert.assertTrue(cartPdtDetlDescEl.getText().contains(hashMapProductVal.get("expProductDesc")));
		log.info("cartPdtDetlDescEl " + cartPdtDetlDescEl.getText());
		Assert.assertTrue(cartPdtDetlPriceEl.getText().contains(hashMapProductVal.get("expProductPrice")));
		log.info("expProductPrice " + cartPdtDetlPriceEl.getText());
		Assert.assertTrue(cartPdtDetlQtyEl.getText().contains(hashMapProductVal.get("productQty")));
		log.info("productQty " + cartPdtDetlQtyEl.getText());
		
	}
	
	public void validateCartNumberDetail(HashMap<String,String> hashMapProductVal, MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
		clickCartLinkTableRow(viewProductPage,hashMapProductVal.get("dropdownSelectValue"),hashMapProductVal.get("pageNumber"),hashMapProductVal.get("rowNumber"));
		execJsScript(cartPdtQtyJsBuilder+hashMapProductVal.get("pdtCheckoutQty"));
		//pdtCheckoutQtyEl.clear();
		//pdtCheckoutQtyEl.sendKeys(hashMapProductVal.get("pdtCheckoutQty"));
		clickElementWithFluentWait(cartUpdBtnXpathBuilder);
		//cartRowUpdtBtnEl.click();
		Assert.assertTrue(cartRowPdtNameEl.getText().contains(hashMapProductVal.get("expProductName")));
		log.info("expProductName " + cartRowPdtNameEl.getText());
		Assert.assertTrue(cartRowPdtBrndEl.getText().contains(hashMapProductVal.get("expProductBrand")));
		log.info("expProductBrand " + cartRowPdtBrndEl.getText());
		Assert.assertTrue(cartRowPdtDescEl.getText().contains(hashMapProductVal.get("expProductDesc")));
		log.info("expProductDesc " + cartRowPdtDescEl.getText());
		Assert.assertEquals(hashMapProductVal.get("expProductPrice"),cartRowPdtPriceEl.getText());
		log.info("expProductPrice " + cartRowPdtPriceEl.getText());
		Assert.assertEquals(hashMapProductVal.get("expSubtotal"),cartRowSubTotlEl.getText());
		log.info("expSubtotal " + cartRowSubTotlEl.getText());
		Assert.assertTrue(executeJsScript(cartTotalJsBuilder).contains(hashMapProductVal.get("expTotal")));
		Assert.assertEquals(hashMapProductVal.get("expCartBdgQty"), executeJsScript(cartTotalBadgeJsBuilder));
		Assert.assertTrue(executeJsScript(cartBdgTotalJsBuilder).contains(hashMapProductVal.get("expCartBdgTot")));
		//cartRowDelBtnEl.click();
		clickElementWithFluentWait(cartDelBtnXpathBuilder); 
		waitForPageToBeVisible(cartDelDescXpathBuilder);
		Assert.assertTrue(cartDelEmptyDescEl.getText().contains(hashMapProductVal.get("expDelPdtConf")));		
		
	}
	
	public void checkCartTableRow(HashMap<String,String> hashMapProductVal, String rowNum) {
				
		WebElement expCartRowPdtNameEl = _driver.findElement(By.xpath(tableCartRowBuilder+rowNum+"]/td[@data-th='Product']/div/div/following-sibling::div/h4"));
		WebElement expCartRowPdtBrndEl = _driver.findElement(By.xpath(tableCartRowBuilder+rowNum+"]/td[@data-th='Product']/div/div/following-sibling::div/p"));
		WebElement expCartRowPdtDescEl = _driver.findElement(By.xpath(tableCartRowBuilder+rowNum+"]/td[@data-th='Product']/div/div/following-sibling::div/p/following-sibling::p"));
		WebElement expCartRowPdtPriceEl = _driver.findElement(By.xpath(tableCartRowBuilder+rowNum+"]/td[@data-th='Price']"));
		WebElement expCartRowSubTotlEl = _driver.findElement(By.xpath(tableCartRowBuilder+rowNum+"]/td[@data-th='Subtotal']"));
		Assert.assertTrue(expCartRowPdtNameEl.getText().contains(hashMapProductVal.get("expProductName")));
		log.info(expCartRowPdtNameEl.getText());
		Assert.assertTrue(expCartRowPdtBrndEl.getText().contains(hashMapProductVal.get("expProductBrand")));
		log.info(expCartRowPdtBrndEl.getText());
		Assert.assertTrue(expCartRowPdtDescEl.getText().contains(hashMapProductVal.get("expProductDesc")));
		log.info(expCartRowPdtDescEl.getText());
		Assert.assertEquals(hashMapProductVal.get("expProductPrice"),expCartRowPdtPriceEl.getText());
		Assert.assertEquals(hashMapProductVal.get("expSubtotal"),expCartRowSubTotlEl.getText());
		Assert.assertTrue(executeJsScript(cartTotalJsBuilder).contains(hashMapProductVal.get("expTotal")));
		Assert.assertEquals(hashMapProductVal.get("expCartBdgQty"), executeJsScript(cartTotalBadgeJsBuilder));
		Assert.assertTrue(executeJsScript(cartBdgTotalJsBuilder).contains(hashMapProductVal.get("expCartBdgTot")));
			
	}
	
	public void clickCartUserName(MedicareLoginPage loginPage) {
		
		loginPage.clickLoginName();
		waitForElementToBeClickable(cartLinkXpathBuilder);
		cartTotalAmmountEl.click();
	}
	
	public void clickCartLinkTableRow(MedicareViewProductsPage viewProductPage, String dpVal, String pNum, String rNum) throws InterruptedException {
		
		waitForPageToBeVisible(usrNameLinkXpathBuilder);
		viewProductPage.clickViewProductLink();
		viewProductPage.selectDropdownViewProduct(dpVal);
		viewProductPage.selectPageViewProduct(pNum);
		viewProductPage.clickviewProductLast(rNum);
		waitForPageToBeVisible(pdDetlConXpathBuilder);
		
	}
	
	public void removeMedicine() throws InterruptedException {
		
		waitForElementToBeVisible(dropdownLogin);
		dropdownLogin.click();
		waitForElementToBeClickable(cartLinkXpathBuilder);
		cartTotalAmmountEl.click();
		
	List<WebElement> list = _driver.findElements(By.xpath("//a[contains(@href, 'remove')]"));
	for (WebElement wb : list) {
		removeFromCart.click();
		Thread.sleep(2000);
		}
	}
	
	
	
	
	

}
