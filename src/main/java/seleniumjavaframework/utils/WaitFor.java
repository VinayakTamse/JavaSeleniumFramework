package seleniumjavaframework.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitFor{
	

	
	public   WebElement getElement(WebDriver driver, By locator)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = driver.findElement(locator);
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	
	public  WebElement getElements(WebDriver driver, By locator, String filter)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		List<WebElement> elelists = driver.findElements(locator);
		WebElement ele = null;
		for (WebElement element : elelists)
		{
			if (element.getText().equals(filter))
			{
				ele = element;
				break;
			}
		}
		wait.until(ExpectedConditions.visibilityOf(ele));
		return ele;
		
	}
	
	public  WebElement getRelativeLocator(WebDriver driver, String tagname, By relativeTo)
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		WebElement element = driver.findElement(relativeTo);
		wait.until(ExpectedConditions.visibilityOf(element));
		return driver.findElement(RelativeLocator.with(By.tagName(tagname)).below(relativeTo));
	}
	
	public  void waitForElement()
	{
		try {
		Thread.sleep(3000);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
