package seleniumjavaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import seleniumjavaframework.utils.WaitFor;

public class HomePage {
	
	private WebDriver driver;
	private WaitFor waitFor;

	
	By adminlink = By.xpath("//span[text()='Admin']");
	By pimlink = By.xpath("//span[text()='PIM']");
	By leavelink = By.xpath("//span[text()='Leave']");
	By infolink = By.xpath("//span[text()='My Info']");
	By buzzlink = By.xpath("//span[text()='Buzz']");
	By profilepic = By.xpath("//span[@class='oxd-userdropdown-tab']/img[@alt='profile picture']");
	By profileDropdown = By.cssSelector("ul.oxd-dropdown-menu>li a");
	
	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public AdminPage clickOnAdminLink()
	{
		waitFor = new WaitFor();
		waitFor.getElement(driver, adminlink).click();
		return new AdminPage(driver);
	}
	
	public boolean verifyLoginStatus()
	{
		waitFor = new WaitFor();
		return waitFor.getElement(driver, profilepic).isDisplayed();
	}
	
	public void logout() 
	{
		waitFor = new WaitFor();
		waitFor.waitForElement();
		waitFor.getElement(driver, profilepic).click();
		waitFor.waitForElement();
		waitFor.getElements(driver, profileDropdown, "Logout").click();
	
		
	}

}
