package testrunners;

import pages.PIMPage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import java.time.Duration;


public class PIMExecuter extends Setup {
    PIMPage pimPage;
    LoginPage loginPage;

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

    @Test(priority = 1, description = "Fetch employees with the employment status Full Time Permanent")
    public void fetchEmployeeFullTimePermanent() throws InterruptedException {
        pimPage = new PIMPage(driver);
        Thread.sleep(2000);
        pimPage.selectEmployeeStatus(driver, 3);
        Utils.scrollDown(driver);
        WebElement table = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows = table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row: allRows) {
            List<WebElement> allCells = row.findElements(By.cssSelector("[role=cell]"));
            Assert.assertTrue(allCells.get(5).getText().contains("Permanent"));
        }
    }

    @Test(priority = 2, description = "Fetch employees with the status Full Time probation.")
    public void fetchEmployeeFullTimeProbation() {
        pimPage = new PIMPage(driver);
        Utils.scrollUp(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        pimPage.selectEmployeeStatus(driver, 4);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Utils.scrollDown(driver);
        WebElement table = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows = table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row: allRows) {
            List<WebElement> allCells = row.findElements(By.cssSelector("[role=cell]"));
            Assert.assertTrue(allCells.get(5).getText().contains("Full-Time Probation"));
        }
    }

    @Test(priority = 3, description = "Create First Employee")
    public void addFirstEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String id = pimPage.inputFields.get(4).getAttribute("value");
        String password = "kanYeWestLover999";

        pimPage.addEmployee(firstName, lastName, userName, password, password);
        String expectedName = firstName + " " + lastName;
        List<WebElement> listH6 = driver.findElements(By.tagName("h6"));
        Utils.waitForElement(driver, listH6.get(1), 10);
        String actualName = listH6.get(1).getText();
        Assert.assertTrue(actualName.contains(expectedName));
        if (listH6.get(1).isDisplayed()) {
            Utils.saveJsonList(userName, password, id);
        }
    }

    @Test(priority = 4, description = "Create Second Employee")
    public void addSecondEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = faker.name().username();
        String id = pimPage.inputFields.get(4).getAttribute("value");
        String password = "kanYeWestLover999";

        pimPage.addEmployee(firstName, lastName, userName, password, password);
        String expectedName = firstName + " " + lastName;
        List<WebElement> listH6 = driver.findElements(By.tagName("h6"));
        Utils.waitForElement(driver, listH6.get(1), 10);
        String actualName = listH6.get(1).getText();
        Assert.assertTrue(actualName.contains(expectedName));
        if (listH6.get(1).isDisplayed()) {
            Utils.saveJsonList(userName, password, id);
        }
    }

    @Test(priority = 5, description = "Create employee with existing username.")
    public void failedAddEmployee() throws IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String userName = Utils.getLastRegisteredUser();
        pimPage.checkUserName(userName);
        Assert.assertTrue(pimPage.userNameErrorMessage.isDisplayed());
    }

    @Test(priority = 6, description = "Search Employee with valid user ID.")
    public void searchEmployeeById() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        List usersList = Utils.readJSONList();
        JSONObject lastUserObj = (JSONObject) usersList.get(usersList.size()-1);
        String lastUserId = (String) lastUserObj.get("id");
        pimPage.employeeList.click();
        Thread.sleep(3000);
        pimPage.findEmployee(lastUserId);
        Thread.sleep(3000);
        Assert.assertTrue(pimPage.idExposer.getText().contains(lastUserId));
    }

    @AfterTest
    public void doLogout() {
        pimPage = new PIMPage(driver);
        pimPage.profilePicture.click();
        pimPage.logoutButton.click();
    }
}

