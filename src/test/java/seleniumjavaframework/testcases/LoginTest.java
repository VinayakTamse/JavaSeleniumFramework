package seleniumjavaframework.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumjavaframework.base.BaseTest;
import seleniumjavaframework.utils.ConfigConstants;


public class LoginTest extends BaseTest {
	
	
	@Test(priority=3)
	public void Login_Logout_Test() 
	{
		homepage = loginpage.login("Admin", "admin123");
		Assert.assertTrue(homepage.verifyLoginStatus());
		homepage.logout();
	}
	
	@Test(priority=2)
	public void Verify_Title_Test()
	{
		Assert.assertEquals(loginpage.title(), ConfigConstants.LOGIN_PAGE_TITLE);
	}
	
	@Test(priority=1)
	public void Verify_Url_Test()
	{
		Assert.assertEquals(loginpage.url(), ConfigConstants.LOGIN_PAGE_URL);
	}

}
