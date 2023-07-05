package seleniumjavaframework.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumjavaframework.base.BaseTest;

public class DeleteUserTest extends BaseTest {
	
	
	@Test
	public void DeleteUser_Test()
	{
		homepage = loginpage.login("Admin", "admin123");
		adminpage = homepage.clickOnAdminLink();
		boolean isDeleted = adminpage.deleteUser("Adam Levin");
		Assert.assertTrue(isDeleted);
		homepage.logout();
		
	}

}
