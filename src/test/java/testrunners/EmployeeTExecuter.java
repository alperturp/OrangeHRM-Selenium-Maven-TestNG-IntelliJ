package testrunners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.EmployeePage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;

public class EmployeeTExecuter extends Setup {
    LoginPage loginPage;
    EmployeePage employeePage;

    @BeforeTest
    public void doLogin() throws InterruptedException {
        loginPage = new LoginPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage.doLogin("Admin", "admin123");
        Thread.sleep(5000);
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "dashboard";
        Assert.assertTrue(actualUrl.contains(expectedUrl));
        List<WebElement> menus = driver.findElements(By.className("oxd-main-menu-item--name"));
        menus.get(1).click();
    }

    @Test(priority = 1, description = "Update employee Nationality & Driving license expiry Information")
    public void updateNationality() throws InterruptedException {
        employeePage = new EmployeePage(driver);
        employeePage.employeeCardEdit.click();
        employeePage.inputFields.get(8).sendKeys("1977-06-08");
        Thread.sleep(3000);
        employeePage.selectNationality();
        Thread.sleep(3000);
        String actualNationality = "Turkish";
        String expectedNationality = employeePage.selectButtons.get(0).getText();
        Assert.assertEquals(actualNationality, expectedNationality);
    }

    @Test(priority = 2, description = "Update employee Blood group Information")
    public void updateBloodGroup() throws InterruptedException {
        employeePage = new EmployeePage(driver);
        Utils.scrollDown(driver);
        employeePage.selectBloodGroup();
        Thread.sleep(3000);
        String actualBloodGroup = "A+";
        String expectedBloodGroup = employeePage.selectButtons.get(2).getText();
        Assert.assertEquals(actualBloodGroup, expectedBloodGroup);
    }

    @AfterTest
    public void doLogout() {
        employeePage = new EmployeePage(driver);
        employeePage.profilePicture.click();
        employeePage.logoutButton.click();
    }

}
