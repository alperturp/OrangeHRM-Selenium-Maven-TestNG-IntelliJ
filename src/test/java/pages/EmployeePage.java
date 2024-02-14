package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import java.util.List;


public class EmployeePage {

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/img")
    public WebElement profilePicture;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/ul/li[4]/a")
    public WebElement logoutButton;

    @FindBy(className = "oxd-input")
    public List<WebElement> inputFields;

    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> selectButtons;

    @FindBy(className = "oxd-table-card")
    public WebElement employeeCardEdit;

    @FindBy(css = "[type=submit]")
    public List<WebElement> submitButtons;

    public EmployeePage(WebDriver driver) { PageFactory.initElements(driver, this);}

    public void selectNationality() {
        WebElement nationalityButton = selectButtons.get(0);
        nationalityButton.sendKeys("t");
        for(int i = 0; i<=7; i++){
            nationalityButton.sendKeys(Keys.ARROW_DOWN);
        }
        nationalityButton.sendKeys(Keys.ENTER);
        submitButtons.get(0).click();
    }

    public void selectBloodGroup() {
        WebElement bloodGroupButton = selectButtons.get(2);
        bloodGroupButton.sendKeys(Keys.ARROW_DOWN);
        bloodGroupButton.sendKeys(Keys.ENTER);
        submitButtons.get(1).click();
    }

}
