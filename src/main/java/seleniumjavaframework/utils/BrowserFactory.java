package seleniumjavaframework.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {

	
	private static ThreadLocal<WebDriver> ldriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<ChromeOptions> lopt = new ThreadLocal<ChromeOptions>();
	
	public static WebDriver getDriver()
	{
		return ldriver.get();
	}
	
	public static ChromeOptions getOpt()
	{
		return lopt.get();
	}

	public WebDriver launchBrowser(String browsername, String url) throws Exception {

		switch (browsername.toLowerCase()) {
		
		case "grid":
			
			lopt.set(new ChromeOptions());
			getOpt().addArguments("--incognito");
			ldriver.set(new RemoteWebDriver(new URL("http://localhost:4444"), getOpt()));
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
			lopt.set(new ChromeOptions());
			ldriver.set(new ChromeDriver(getOpt().addArguments("--incognito")));
			break;
		case "firefox":
			ldriver.set(new FirefoxDriver());
			break;
		case "msedge":
			EdgeOptions opt = new EdgeOptions();
			opt.addArguments("--guest");
			ldriver.set(new EdgeDriver(opt));
			break;
		default:
			throw new Exception("No Such Browser Configured");

		}
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		getDriver().manage().window().maximize();
		getDriver().get(url);
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
		return getDriver();
	}

	public WebDriver launch_grid(String url) throws MalformedURLException {
		lopt.set(new ChromeOptions());
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), getOpt());
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		return driver;
	}

	public void closeBrowser() {
		getDriver().quit();
	}

}
