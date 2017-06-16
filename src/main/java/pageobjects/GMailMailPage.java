package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by EugenKrasotkin on 5/25/2017.
 */
public class GMailMailPage {
    private WebDriver driver;
    @FindBy(css = "a[title=\"Отправленные\"]")
    private WebElement sentLink;
    @FindBy(css = "span[class=\"gb_8a gbii\"]")
    private WebElement title;
    @FindBy(id = "gb_71")
    private WebElement exit;
    @FindBy(css = "div[class=\"T-I J-J5-Ji T-I-KE L3\"]")
    private WebElement newButton;
    @FindBy(css = "textarea[aria-label=\"Кому\"]")
    private WebElement addressField;
    @FindBy(css = "input[placeholder=\"Тема\"]")
    private WebElement subjectArea;
    @FindBy(css = "div[class=\"T-I J-J5-Ji aoO T-I-atl L3\"]")
    private WebElement sendButton;
    @FindBy(css = "span[class = \"bog\"]")
    private List<WebElement> lastLetter;
    @FindBy(css = "span[class = \"bog\"]")
    private List<WebElement> lastSentLetter;
    @FindBy(css = "a[title^=\"Входящие\"]")
    private WebElement inboxLink;
    @FindBy(css = "img[class=\"hA T-I-J3\"]")
    private WebElement menuButton;
    @FindBy(css = "div[id=\"tm\"] div img")
    private WebElement deleteButton;
    @FindBy(css = "span[class=\"CJ\"]")
    private WebElement leftMenuButton;
    @FindBy(css = "a[title=\"Корзина\"]")
    private WebElement deletedLink;
    @FindBy(css = "span[class = \"bog\"]")
    private List<WebElement> lastDeletedLetter;
    @FindBy(css = ".aki.pp span")
    private WebElement gmailLink;


    public GMailMailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public GMailLoginPage logout() {
        title.click();
        exit.click();
        return new GMailLoginPage(driver);
    }

    public void sendLetter(String email, String text) throws InterruptedException {
        newButton.click();
        Thread.sleep(1000);
        addressField.sendKeys(email);
        subjectArea.sendKeys(text);
        Thread.sleep(500);
        sendButton.click();
        Thread.sleep(500);
    }

    public String getGmailLinkValue() {
        return gmailLink.getText();
    }

    public boolean inboxLetter(String text) {
        for (WebElement element : lastLetter) {
            if (element.getText().equals(text)) return true;
        }
        return false;
    }

    public boolean sentLetter(String text) throws InterruptedException {
        sentLink.click();
        Thread.sleep(1000);
        for (WebElement element : lastSentLetter) {
            if (element.getText().equals(text)) return true;
        }
        return false;
    }

    public void deleteLetter(String text) throws InterruptedException {
        inboxLink.click();

        Thread.sleep(1000);
        for (WebElement element : lastLetter) {
            if (element.getText().equals(text)) element.click();
        }
        menuButton.click();
        Thread.sleep(1000);
        deleteButton.click();
    }

    public boolean deletedLetter(String text) throws InterruptedException {
        leftMenuButton.click();
        deletedLink.click();

        Thread.sleep(1000);
        for (WebElement element : lastDeletedLetter) {
            if (element.getText().equals(text)) return true;
        }
        return false;
    }
}