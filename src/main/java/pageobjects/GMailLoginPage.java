package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by EugenKrasotkin on 5/25/2017.
 */
public class GMailLoginPage {
    private static final String URL = "https://gmail.com";
    private WebDriver driver;
    @FindBy(css = "input[class=\"whsOnd zHQkBf\"]")
    private WebElement inputField;
    @FindBy(css = "span[class = \"RveJvd snByac\"]")
    private WebElement nextButton;
    @FindBy(css = "div[id=\"identifierLink\"] div[class=\"vdE7Oc f3GIQ\"]")
    private WebElement anotherUser;
    @FindBy(css = "content[class =\"xjKiLb\"] span")
    private WebElement anotherClick;
    @FindBy(id = "headingText")
    private WebElement anotherAccountText;
    @FindBy(css = "ul[class=\"Bgzgmd\"] li:first-child a")
    private WebElement helpLink;


    public GMailLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void load() {
        driver.get(URL);
    }

    public GMailMailPage login(String login, String password) throws InterruptedException {
        if (anotherAccountText.getText().equals("Choose an account")) {
            anotherUser.click();
        } else if (anotherAccountText.getText().equals("Hi Selenium")) {
            anotherClick.click();
            Thread.sleep(1000);
            anotherUser.click();
        }
        inputField.sendKeys(login);
        Thread.sleep(1000);
        nextButton.click();
        Thread.sleep(1000);
        inputField.sendKeys(password);
        nextButton.click();
        return new GMailMailPage(driver);
    }

    public String getHelpLinkValue() {
        return helpLink.getText();
    }
}