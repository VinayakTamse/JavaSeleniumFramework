package seleniumjavaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import seleniumjavaframework.utils.WaitFor;

public class LoginPage {
	
	private WebDriver driver;
	WaitFor waitFor;
	
	
	By usernamefield = By.xpath("//input[@name='username']");
	By passwordfield = By.name("password");
	By submitbutton = By.cssSelector("button[type=submit]");
	
	
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public String url()
	{
		return driver.getCurrentUrl();
	}
	
	public String title()
	{
		return driver.getTitle();
	}
	
	public HomePage login(String username, String password)
	{
		waitFor = new WaitFor();
		waitFor.getElement(driver,usernamefield).sendKeys(username);
		waitFor.getElement(driver, passwordfield).sendKeys(password);
		waitFor.getElement(driver, submitbutton).click();
		return new HomePage(driver);
		
	}
	
	

}
