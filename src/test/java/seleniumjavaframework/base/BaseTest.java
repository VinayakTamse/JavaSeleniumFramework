package seleniumjavaframework.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import seleniumjavaframework.pages.AdminPage;
import seleniumjavaframework.pages.HomePage;
import seleniumjavaframework.pages.LoginPage;
import seleniumjavaframework.utils.BrowserFactory;
import seleniumjavaframework.utils.ConfigConstants;
import seleniumjavaframework.utils.ReadProperties;

public class BaseTest {
	
	protected WebDriver driver;
	protected BrowserFactory browserfactory;
	protected LoginPage loginpage;
	protected HomePage homepage;
	protected AdminPage adminpage;
	protected Properties props;
	
	@BeforeClass
	@Parameters({"browser", "url"})
	public void setUp(String browsername, String appurl) throws Exception
	{
		browserfactory = new BrowserFactory();
		
		props = ReadProperties.getPropertyValue(ConfigConstants.CONFIG_FILE);
		if (browsername != null)
		{
			props.setProperty("browser", browsername);
			props.setProperty("url", appurl);
		}
		System.out.println(appurl);
		driver = browserfactory.launchBrowser(props.getProperty("browser"), props.getProperty("url"));
		loginpage = new LoginPage(driver);
	}
	
	@AfterClass
	public void tearDown()
	{
		browserfactory.closeBrowser();
	}

}
