package med.webpages;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import med.webproperties.MedicarePropertyConfig;
import med.webutil.MedicareFileUtil;

public class MedicareAdminPage extends TestPageBase {
	
	private final String saveBtnXpathBuilder = "//form[@id='product']//input[@type='submit']";
	private final String managePdtXpathBuilder = "//a[text()='Manage Product']";
	private final String addCatXpathBuilder = "//button[text()='Add New Category']";
	private final String orderPageScrollJsBuilder = "window.scrollBy(0,document.body.scrollHeight)";
	private final String unitPriceJsBuilder = "document.querySelector('#unitPrice').value =";
	private final String quantityJsBuilder = "document.querySelector('#quantity').value =";
	
	
	
	@FindBy(xpath="//a[text()='Manage Product']") WebElement managePdtLinkEl;
	@FindBy(xpath="//button[text()='Add New Category']") WebElement addCategoryEl;
	@FindBy(xpath="//form[@id='categoryForm']//input[@id='name']") WebElement popNameEl;
	@FindBy(xpath="//form[@id='categoryForm']//textarea[@id='description']") WebElement popDescEl;
	@FindBy(xpath="//form[@id='categoryForm']//input[@type='submit']") WebElement popBtnSaveEl;
	@FindBy(xpath="//select[@id='categoryId']/option") List<WebElement> drpdnBtnEl;
	
	
	@FindBy(xpath="//form[@id='product']//input[@id='name']") WebElement addPdtNameEl;
	@FindBy(xpath="//form[@id='product']//input[@id='brand']") WebElement addPdtBndEl;
	@FindBy(xpath="//form[@id='product']//textarea[@id='description']") WebElement addPdtDescEl;
	@FindBy(xpath="//form[@id='product']//input[@id='unitPrice']") WebElement addPdtUnitEl;
	@FindBy(xpath="//form[@id='product']//input[@id='quantity']") WebElement addPdtQtyEl;
	@FindBy(xpath="//form[@id='product']//input[@id='file']") WebElement addPdtFileEl;
	@FindBy(xpath="//form[@id='product']//select[@id='categoryId']") WebElement drpdnPdtCatEl;
	@FindBy(xpath="//form[@id='product']//input[@type='submit']") WebElement pdtBtnSaveEl;
		
	
	public MedicareAdminPage(WebDriver driver){
		super(driver);
	}	
	
	public Logger log = Logger.getLogger(MedicareAdminPage.class);
	
	public void validateAddNewCategory(String cat, String desc,MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
		clickElementWithFluentWait(managePdtXpathBuilder);
		waitForPageToBeVisible(saveBtnXpathBuilder);
		clickElementWithFluentWait(addCatXpathBuilder);
		popNameEl.sendKeys(cat);
		popDescEl.sendKeys(desc);
		popBtnSaveEl.click();
		viewProductPage.clickViewProductLink();
		viewProductPage.validateCategory(cat);
		clickElementWithFluentWait(managePdtXpathBuilder);
		Assert.assertTrue(validateCategoryDropdown(cat));
		
	}
	
	public void validateAddNewProduct(HashMap<String,String> hashMapProductVal,MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
		clickElementWithFluentWait(managePdtXpathBuilder);
		waitForPageToBeVisible(saveBtnXpathBuilder);
		addPdtNameEl.sendKeys(hashMapProductVal.get("productName"));
		Thread.sleep(2000);
		addPdtBndEl.sendKeys(hashMapProductVal.get("productBrand"));
		Thread.sleep(2000);
		addPdtDescEl.sendKeys(hashMapProductVal.get("productDesc"));
		Thread.sleep(2000);
		addPdtUnitEl.clear();
		Thread.sleep(2000);
		addPdtUnitEl.sendKeys(hashMapProductVal.get("productPrice"));
		Thread.sleep(2000);
		addPdtQtyEl.clear();
		Thread.sleep(2000);
		addPdtQtyEl.sendKeys(hashMapProductVal.get("productQty"));
		Thread.sleep(2000);
		//String filePath = MedicareFileUtil.getMedicareTestImagesResourcePath()+"\\"+hashMapProductVal.get("fileLocation");
		addPdtFileEl.sendKeys(MedicarePropertyConfig.PRODUCTIMAGEURI);
		Thread.sleep(2000);
		Select dropdown = new Select(drpdnPdtCatEl);
		Thread.sleep(2000);
  		dropdown.selectByVisibleText(hashMapProductVal.get("productCategory"));
  		Thread.sleep(2000);
  		pdtBtnSaveEl.click();
  		Thread.sleep(2000);
  		viewProductPage.clickViewProductLink();Thread.sleep(2000);
  		viewProductPage.clickEntryDropdown();
  		Thread.sleep(5000);
  		viewProductPage.validateAdminProduct(hashMapProductVal);
  		Thread.sleep(5000);
  		//Thread.sleep(5000);
		
	}
	
	public void validateEditNewProduct(HashMap<String,String> hashMapPdtEditVal,MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
		//clickElementWithFluentWait(managePdtXpathBuilder);
		log.info("Program starting");
		viewProductPage.clickViewProductLink();
		log.info("Program starting - View Product link clicked");
		//execJsScript(orderPageScrollJsBuilder);		
		viewProductPage.selectDropdownViewProduct(hashMapPdtEditVal.get("dropdownSelectValue"));
		execJsScript(orderPageScrollJsBuilder);
		log.info("Program starting - scroll clicked");
		viewProductPage.selectPageViewProduct(hashMapPdtEditVal.get("pageNumber"));
		log.info("Program starting - page number clicked");
		viewProductPage.clickviewProductLast(hashMapPdtEditVal.get("rowNumber"));
		log.info("Program starting - row number clicked");
		waitForPageToBeVisible(saveBtnXpathBuilder);		
		log.info("Program starting - editing values");
		//addPdtNameEl.clear();
		//addPdtNameEl.sendKeys(hashMapPdtEditVal.get("expProductName"));
		//System.out.println(hashMapPdtEditVal.get("expProductName"));
		//addPdtBndEl.clear();
		//addPdtBndEl.sendKeys(hashMapPdtEditVal.get("expProductBrand"));
		//System.out.println(hashMapPdtEditVal.get("expProductBrand"));
		//addPdtDescEl.clear();
		//addPdtDescEl.sendKeys(hashMapPdtEditVal.get("expProductDesc"));
		//System.out.println(hashMapPdtEditVal.get("expProductDesc"));
		//addPdtUnitEl.clear();
		execJsScript(unitPriceJsBuilder+hashMapPdtEditVal.get("expUnitPrice"));
		//addPdtUnitEl.sendKeys(hashMapPdtEditVal.get("expUnitPrice"));
		System.out.println(hashMapPdtEditVal.get("expUnitPrice"));
		//addPdtQtyEl.clear();
		execJsScript(quantityJsBuilder+hashMapPdtEditVal.get("expProductQty"));
		//addPdtQtyEl.sendKeys(hashMapPdtEditVal.get("expProductQty"));
		System.out.println(hashMapPdtEditVal.get("expProductQty"));
		//addPdtFileEl.clear();
		//String filePath = MedicareFileUtil.getMedicareTestImagesResourcePath()+"\\"+hashMapPdtEditVal.get("fileLocation");
		addPdtFileEl.sendKeys(MedicarePropertyConfig.PRODUCTIMAGEURI);
		log.info("file selected");
		Select dropdown = new Select(drpdnPdtCatEl);
  		dropdown.selectByVisibleText(hashMapPdtEditVal.get("productCategory"));
  		log.info("dropdown category selected");
		pdtBtnSaveEl.click();
		log.info("save clicked");
  		viewProductPage.clickViewProductLink();
  		viewProductPage.validateAdminProduct(hashMapPdtEditVal);  		
		
		}	
	
	
	public Boolean validateCategoryDropdown(String catVal) {
		
	    for(WebElement e :drpdnBtnEl){  
			
	    	if(e.getText().equals(catVal))
	    		return true;
	    	
		}  
		
		return false;
	}

}
