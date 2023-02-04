package med.webpages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.apache.log4j.Logger;

import med.webproperties.MedicarePropertyConfig;

public class MedicareAboutPage extends TestPageBase {
	
	@FindBy(xpath="//a[text()='About']") WebElement aboutLink;
	@FindBy(xpath="//h1") WebElement aboutHeading;
	@FindBy(xpath="//p") WebElement aboutPara;
	
	
	public MedicareAboutPage(WebDriver driver){
		super(driver);
	}	
	
	public Logger log = Logger.getLogger(MedicareAboutPage.class);
	
	public void navigateAbout() {
		log.info("Clicking about button");
		aboutLink.click();
	}
	
	public void validateAboutHeading(String expHeadStr) {
		Assert.assertEquals(aboutHeading.getText(), expHeadStr);
	}
	
    public void validateAboutPara(String expParaStr) {
    	Assert.assertEquals(aboutPara.getText(), expParaStr);
	}

}
