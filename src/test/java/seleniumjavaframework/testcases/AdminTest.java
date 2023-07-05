package seleniumjavaframework.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import seleniumjavaframework.base.BaseTest;


public class AdminTest extends BaseTest{
	
	@Test
	public void Admin_Test()
	{
		homepage = loginpage.login("Admin", "admin123");
		adminpage = homepage.clickOnAdminLink();
		Assert.assertTrue(adminpage.addUser());
		homepage.logout();
		
	}

}
