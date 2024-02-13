package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
public class PIMPage {

    @FindBy(className = "oxd-userdropdown-img")
    public WebElement profilePicture;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/ul/li[4]/a")
    public WebElement logoutButton;

    @FindBy(className = "oxd-select-text-input")
    List<WebElement> dropdowns;

    @FindBy(css = "[type=submit]")
    WebElement submitBtn;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/header/div[2]/nav/ul/li[3]/a")
    public WebElement addEmployeeLinkText;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/header/div[2]/nav/ul/li[2]/a")
    public WebElement employeeList;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div[2]/div[2]/div/label/span")
    WebElement checkBox;

    @FindBy(className = "oxd-input")
    public List<WebElement> inputFields;

    @FindBy(className = "orangehrm-main-title")
    public WebElement title;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div/div/div[2]")
    public WebElement idExposer;

    @FindBy(className = "oxd-input-field-error-message")
    public WebElement userNameErrorMessage;

    public PIMPage(WebDriver driver) { PageFactory.initElements(driver, this);}

    public void selectEmployeeStatus(WebDriver driver, int position) {
        dropdowns.get(0).click();
        for (int i = 0; i < position; i++) {
            dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        }
        dropdowns.get(0).sendKeys(Keys.ENTER);
        submitBtn.click();

    }

    public void checkUserName(String userName) {
        checkBox.click();
        inputFields.get(5).sendKeys(userName);
    }

    public void addEmployee(String firstName, String lastName, String userName, String pass, String confPass) throws InterruptedException {
    checkBox.click();
    inputFields.get(1).sendKeys(firstName);
    inputFields.get(3).sendKeys(lastName);
    inputFields.get(5).sendKeys(userName);
    inputFields.get(6).sendKeys(pass);
    inputFields.get(7).sendKeys(confPass);
    submitBtn.click();
    Thread.sleep(10000);

    }

    public void findEmployee(String id) {
        inputFields.get(1).sendKeys(id);
        submitBtn.click();
    }
}
