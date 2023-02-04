package med.webpages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class MedicareContactPage extends TestPageBase {
	
	@FindBy(xpath="//a[text()='Contact']") WebElement contactLink;
	@FindBy(xpath="//h1") WebElement contactHeading;
	@FindBy(xpath="//p") WebElement contactPara;
	
	public MedicareContactPage(WebDriver driver){
		super(driver);
	}
	public Logger log = Logger.getLogger(MedicareContactPage.class);
	
	public void navigateContact() {
		waitForElementToBeVisible(contactLink);
		contactLink.click();
		log.info("Contact link clicked");
		
	}
	
	public void validateContactHeading(String expHeadStr) {
		Assert.assertEquals(contactHeading.getText(), expHeadStr);
		log.info("Contact heading validated");
	}
	
    public void validateContactPara(String expParaStr) {
    	Assert.assertEquals(contactPara.getText(), expParaStr);
    	log.info("Contact paragraph validated");
	}
}
