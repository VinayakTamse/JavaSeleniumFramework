package seleniumjavaframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;

import seleniumjavaframework.utils.WaitFor;

public class AdminPage {
	
	private WebDriver driver;
	WaitFor waitFor;
	
	By adduserbutton = By.xpath("//button/i[contains(@class,'bi-plus')]");
	By dropdown = By.cssSelector("div.oxd-select-text-input");
	By userRole = By.xpath("//label[text()='User Role']");
	By selectAdmin = By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[text()='Admin']");
	By status = By.xpath("//label[text()='Status']");
	By selectEnabled = By.xpath("//span[text()='Enabled']");
	By employeename = By.xpath("//label[text()='Employee Name']");
	By empJohn = By.xpath("//span[text()='John  Smith']");
	By username = By.xpath("//label[text()='Username']");
	By passwordlabel = By.xpath("//label[text()='Password']");
	By confirmpasswordlabel = By.xpath("//label[text()='Confirm Password']");
	By saveButton = By.xpath("//button[@type='submit']");
	By savedMessage = By.xpath("//p[text()='Successfully Saved']");
	By searchbutton = By.xpath("//button[@type='submit']");
	By profilepicname = By.cssSelector("p.oxd-userdropdown-name");
	By deletebutton = By.xpath("//button[contains(@class,'oxd-button--label-danger')]");
	By confirmdelete = By.xpath("//*[text()=' Yes, Delete ']");
	By deleteMsg = By.xpath("//p[text()='Successfully Deleted']");
	
	public AdminPage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public boolean addUser()
	{
		waitFor = new WaitFor();
		waitFor.getElement(driver, adduserbutton).click();
		waitFor.getRelativeLocator(driver,"div",userRole).click();
		waitFor.getElement(driver, selectAdmin).click();
		waitFor.getRelativeLocator(driver,"div", status).click();
		waitFor.getElement(driver, selectEnabled).click();
		String user = waitFor.getElement(driver, profilepicname).getText();
		waitFor.getRelativeLocator(driver, "input", employeename).sendKeys(user);
		waitFor.waitForElement();
		//WaitFor.getElement(driver, By.xpath("//span[contains(text(),'"+user+"')]")).click();
		new Actions(driver).sendKeys(Keys.DOWN).perform();
		new Actions(driver).sendKeys(Keys.ENTER).perform();
		waitFor.getRelativeLocator(driver, "input", username).sendKeys("Adam Levin");
		waitFor.getRelativeLocator(driver, "input", passwordlabel).sendKeys("Abcd@123");
		waitFor.getRelativeLocator(driver, "input", confirmpasswordlabel).sendKeys("Abcd@123");
		waitFor.getElement(driver, saveButton).click();
		return waitFor.getElement(driver, savedMessage).isDisplayed();
		
	}
	
	public boolean deleteUser(String user)
	{
		waitFor = new WaitFor();
		waitFor.getRelativeLocator(driver, "input", username).sendKeys(user);
		waitFor.getElement(driver, searchbutton).click();
		waitFor.waitForElement();
		try {
		boolean userExists = waitFor.getElement(driver, By.xpath("//div[text()='Adam Levin']")).isDisplayed();
		WebElement founduser = waitFor.getElement(driver, By.xpath("//div[text()='Adam Levin']"));
		waitFor.waitForElement();
		driver.findElement(RelativeLocator.with(By.tagName("i")).toLeftOf(founduser)).click();
		waitFor.waitForElement();
		waitFor.getElement(driver, deletebutton).click();
		waitFor.getElement(driver, confirmdelete).click();
		return waitFor.getElement(driver, deleteMsg).isDisplayed();
		
		}
		catch (Exception e)
		{
			System.out.println(user + " Not exists");
			return false;
			
		}
	}

}
