package med.webpages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class MedicareUserCheckoutPage extends TestPageBase {
	
	private List<Map> cartFinalList = new ArrayList();
	private final String finalItemsXpathBuilder = "/html/body/div[2]/div[1]/div/div/div[1]/div/div[";
	private final String chkoutBtnXpathBuilder = "//a[contains(text(),'Checkout')]";
	private final String shipAddrXpathBuilder = "//h4[text()='Select Shipping Address']/../div/div/div/a";
	private final String orderComXpathBuilder = "//table/tbody/tr[";
	private final String tableXpathBuilder = "//table";
	private final String medicareLinkXpathBuilder = "//a[text()='Medicare']";
	private final String orderPageScrollJsBuilder = "window.scrollBy(0,document.body.scrollHeight)"; 
		
	@FindBy(xpath="//a[contains(text(),'Checkout')]") WebElement cartChkoutEl;
	@FindBy(xpath="//h4[text()='Select Shipping Address']/../div/div/div/a") WebElement cartShipAddrEl;
	@FindBy(xpath="//span[contains(@class,'badge')]") WebElement cartFinalAmmtEl;
	@FindBy(xpath="//a[text()='Pay']") WebElement payLnkEl;
	@FindBy(xpath="//a[text()='Medicare']") WebElement medicareLnkEl;	
	@FindBy(xpath="/html/body/div[2]/div[1]/div/div/div") List<WebElement> cartFinalListEl;
	
	
	
	
	
	public MedicareUserCheckoutPage(WebDriver driver){
		super(driver);
	}	
	public Logger log = Logger.getLogger(MedicareUserCheckoutPage.class);
	
    public void addProductToCart(HashMap<String,String> hashMapProductVal, MedicareUserPage userPage, MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
    	userPage.clickCartLinkTableRow(viewProductPage,hashMapProductVal.get("dropdownSelectValue"),hashMapProductVal.get("pageNumber"),hashMapProductVal.get("rowNumber"));
		
	}
    
    public void validateProductItemCart(HashMap<String,String> hashMapProductVal, MedicareLoginPage loginPage, MedicareUserPage userPage, MedicareViewProductsPage viewProductPage) {
		
    	userPage.clickCartUserName(loginPage);
    	userPage.checkCartTableRow(hashMapProductVal,hashMapProductVal.get("rowNumber"));
		
	}
    
    public void validateProductFinalItem(HashMap<String,String> hashMapProductVal, MedicareLoginPage loginPage, MedicareUserPage userPage, MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
    	navigateToCheckOut(loginPage,userPage);
    	//cartChkoutEl.click();    	
    	//cartShipAddrEl.click();
    	checkFinalCartRow(hashMapProductVal,hashMapProductVal.get("rowNumber"));
    	clickElementWithFluentWait(medicareLinkXpathBuilder);
    	//medicareLnkEl.click();
   	}
    
    public void validateOrderItemInfo(MedicareLoginPage loginPage, MedicareUserPage userPage, MedicareViewProductsPage viewProductPage) throws InterruptedException {
		
    	navigateToCheckOut(loginPage,userPage);
    	List<Map> finalOrderItems = getCartFinalList();
    	payLnkEl.click();
    	takeScreenShots("PaymentPage", _driver);
    	waitForPageToBeVisible(tableXpathBuilder);
    	execJsScript(orderPageScrollJsBuilder);
    	compareFinalOrderList(finalOrderItems); 
    	log.info(finalOrderItems);
    	clickElementWithFluentWait(medicareLinkXpathBuilder);
    	takeScreenShots("PaymentPage_01", _driver);
    	//medicareLnkEl.click();
		
   	}
    
    private void navigateToCheckOut(MedicareLoginPage loginPage,MedicareUserPage userPage) throws InterruptedException {
    	
    	userPage.clickCartUserName(loginPage);
    	waitForPageToBeVisible(chkoutBtnXpathBuilder);
    	clickElementWithFluentWait(chkoutBtnXpathBuilder);
    	clickElementWithFluentWait(shipAddrXpathBuilder);		
    	takeScreenShots("Checkout", _driver);
    }   
 
    
    public void compareFinalOrderList(List<Map> mapOrderItems) {
    	
    	int count =1;
    	
    	for(Map mapOrderItem:mapOrderItems) {
    		
    		HashMap<String, String> hashMapItem = (HashMap<String, String>)mapOrderItem;
    		WebElement expOrderRowPdtNameEl = _driver.findElement(By.xpath(orderComXpathBuilder+Integer.toString(count)+"]/td[1]"));
    		WebElement expOrderRowPdtPriceEl = _driver.findElement(By.xpath(orderComXpathBuilder+Integer.toString(count)+"]/td[2]"));
    		WebElement expOrderRowQtyEl = _driver.findElement(By.xpath(orderComXpathBuilder+Integer.toString(count)+"]/td[3]"));
    		WebElement expOrderRowGndTotEl = _driver.findElement(By.xpath(orderComXpathBuilder+Integer.toString(count)+"]/td[4]"));
    		System.out.println(hashMapItem.get("rowNumber"));
    		System.out.println(expOrderRowPdtNameEl.getText());
    		System.out.println(hashMapItem.get("expProductName"));
    		Assert.assertTrue(expOrderRowPdtNameEl.getText().contains(hashMapItem.get("expProductName")));
    		System.out.println(hashMapItem.get("rowNumber"));
    		System.out.println(expOrderRowPdtPriceEl.getText());
    		System.out.println(hashMapItem.get("expPurchasePrice"));
    		Assert.assertTrue(hashMapItem.get("expPurchasePrice").contains(expOrderRowPdtPriceEl.getText()));
    		System.out.println(hashMapItem.get("rowNumber"));
    		System.out.println(expOrderRowQtyEl.getText());
    		System.out.println(hashMapItem.get("expProductQty"));
    		Assert.assertTrue(hashMapItem.get("expProductQty").contains(expOrderRowQtyEl.getText()));
    		System.out.println(hashMapItem.get("rowNumber"));
    		System.out.println(expOrderRowGndTotEl.getText());
    		System.out.println(hashMapItem.get("expGrandtotal"));
    		Assert.assertTrue(hashMapItem.get("expGrandtotal").contains(expOrderRowGndTotEl.getText()));    		
    		count++;  				
    			    		
    	}   	
    	
    }
    
    public List<Map> getCartFinalList(){
    	
    	int count = 1;
    	List<Map> cartList = new ArrayList();
    	for(WebElement element:cartFinalListEl) {
    		
    		WebElement expCartRowPdtNameEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+Integer.toString(count)+"]/div[1]/h3"));
    		WebElement expCartRowGndTotEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+Integer.toString(count)+"]/div[2]/h3"));
    		WebElement expCartRowQtyEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+Integer.toString(count)+"]/div[1]/h4"));
    		WebElement expCartRowPdtPriceEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+Integer.toString(count)+"]/div[1]/h5"));
    		HashMap<String, String> hashMapItems = new HashMap<String, String>();
    		hashMapItems.put("rowNumber", Integer.toString(count));
    		hashMapItems.put("expProductName", expCartRowPdtNameEl.getText());
 		    hashMapItems.put("expPurchasePrice", expCartRowPdtPriceEl.getText());		    
 		    hashMapItems.put("expProductQty", expCartRowQtyEl.getText());
 		    hashMapItems.put("expGrandtotal", expCartRowGndTotEl.getText());
 		    cartList.add(hashMapItems);
 		    count++;			
		    }
    	
    	   return cartList;
		}
    	
    
    
    
    
    
    
    public void checkFinalCartRow(HashMap<String,String> hashMapProductVal, String rowNum) throws InterruptedException {
    	
    	WebElement expCartRowPdtNameEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+rowNum+"]/div[1]/h3"));
		WebElement expCartRowGndTotEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+rowNum+"]/div[2]/h3"));
		WebElement expCartRowQtyEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+rowNum+"]/div[1]/h4"));
		WebElement expCartRowPdtPriceEl = _driver.findElement(By.xpath(finalItemsXpathBuilder+rowNum+"]/div[1]/h5"));
		Assert.assertTrue(expCartRowPdtNameEl.getText().contains(hashMapProductVal.get("expProductName")));
		log.info(expCartRowPdtNameEl.getText());
		Assert.assertTrue(expCartRowGndTotEl.getText().contains(hashMapProductVal.get("expGrandtotal")));
		log.info(expCartRowGndTotEl.getText());
		Assert.assertTrue(expCartRowQtyEl.getText().contains(hashMapProductVal.get("expProductQty")));
		log.info(expCartRowQtyEl.getText());
		Assert.assertTrue(expCartRowPdtPriceEl.getText().contains(hashMapProductVal.get("expPurchasePrice")));
		log.info(expCartRowPdtPriceEl.getText());
		Assert.assertEquals(cartFinalAmmtEl.getText(),hashMapProductVal.get("expFinalTotal"));		
		log.info(cartFinalAmmtEl.getText());
		takeScreenShots("checkoutPage", _driver);
			
	}
    
    
    
    
    
    
    
	
	

}
