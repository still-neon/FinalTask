import listeners.FinalTestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.GMailLoginPage;
import pageobjects.GMailMailPage;
import ru.yandex.qatools.allure.annotations.*;
import singleton.InstanPage;

import java.io.*;
import java.util.Scanner;

import static java.util.concurrent.TimeUnit.SECONDS;

@Listeners(FinalTestListener.class)
public class FinalTest {
    private WebDriver driver;
    private String text;
    private GMailLoginPage gmailLoginPage;
    private GMailMailPage gmailMailPage;

    @Parameter("My Param")
    private String myParameter;

    @BeforeMethod
    public void setUp() {
        driver = InstanPage.getInstance();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.manage().window().maximize();
        myParameter = "Browser: Google Chrome";
        myParameter = "Browser's version: 57.0.2987.133 (64-bit)";
    }

    @AfterClass
    public void tearDown() {
        InstanPage.close();
    }

    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentialsData() throws IOException {
        FileReader reader = new FileReader(new File("loginCredentials.txt"));
        Scanner scan = new Scanner(reader);

        Object[][] matrix = new Object[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scan.next();
            }
        }
        return matrix;
    }

    @DataProvider(name = "logoutCredentials")
    public static Object[][] logoutCredentialsData() throws IOException {
        FileReader reader = new FileReader(new File("logoutCredentials.txt"));
        Scanner scan = new Scanner(reader);

        Object[][] matrix = new Object[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scan.next();
            }
        }
        return matrix;
    }

    @DataProvider(name = "sendEmailCredentials")
    public static Object[][] sendEmailCredentialsData() throws IOException {
        FileReader reader = new FileReader(new File("sendEmailCredentials.txt"));
        Scanner scan = new Scanner(reader);

        Object[][] matrix = new Object[1][3];

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scan.next();
            }
        }
        return matrix;
    }

    @DataProvider(name = "deleteEmailCredentials")
    public static Object[][] deleteEmailCredentialsData() throws IOException {
        FileReader reader = new FileReader(new File("deleteEmailCredentials.txt"));
        Scanner scan = new Scanner(reader);

        Object[][] matrix = new Object[1][2];

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = scan.next();
            }
        }
        return matrix;
    }

    @Features("LogIn")
    @Description("The method tests user's log in")
    @TestCaseId("TMS-1")
    @Test(dataProvider = "loginCredentials")
    public void login(String login, String password, String gmailTitle) throws InterruptedException {
        gmailLoginPage = new GMailLoginPage(driver);
        gmailLoginPage.load();
        gmailMailPage = gmailLoginPage.login(login, password);
        Assert.assertEquals(gmailMailPage.getGmailLinkValue(), gmailTitle);
        gmailLoginPage = gmailMailPage.logout();
    }

    @Features("LogOut")
    @Description("The method tests user's log out")
    @TestCaseId("TMS-2")
    @Test(dataProvider = "logoutCredentials")
    public void logout(String login, String password, String helpText) throws InterruptedException {
        gmailLoginPage = new GMailLoginPage(driver);
        gmailLoginPage.load();
        gmailMailPage = gmailLoginPage.login(login, password);
        gmailLoginPage = gmailMailPage.logout();
        Assert.assertEquals(gmailLoginPage.getHelpLinkValue(), helpText);
    }

    @Features("SendEmails")
    @Description("The method tests user's ability to send emails")
    @TestCaseId("TMS-3")
    @Test(dataProvider = "sendEmailCredentials")
    public void sendEmail(String login, String password, String email) throws InterruptedException {
        text = "" + Math.random() * Math.random();

        gmailLoginPage = new GMailLoginPage(driver);
        gmailLoginPage.load();
        gmailMailPage = gmailLoginPage.login(login, password);
        gmailMailPage.sendLetter(email, text);
        gmailLoginPage = gmailMailPage.logout();
        gmailMailPage = gmailLoginPage.login(email, password);
        Assert.assertEquals(gmailMailPage.inboxLetter(text), true);
        gmailLoginPage = gmailMailPage.logout();
    }

    @Features("SentEmails")
    @Description("The method tests that email appears in Sent Mail folder")
    @TestCaseId("TMS-4")
    @Test(dataProvider = "sendEmailCredentials")
    public void sentEmail(String login, String password, String email) throws InterruptedException {
        text = "" + Math.random() * Math.random();

        gmailLoginPage = new GMailLoginPage(driver);
        gmailLoginPage.load();
        gmailMailPage = gmailLoginPage.login(login, password);
        gmailMailPage.sendLetter(email, text);
        Assert.assertEquals(gmailMailPage.sentLetter(text), true);
        gmailLoginPage = gmailMailPage.logout();
    }

    @Features("Trash")
    @Description("The method tests that deleted email is listed in Trash")
    @TestCaseId("TMS-5")
    @Test(dataProvider = "deleteEmailCredentials")
    public void deleteEmail(String login, String password) throws InterruptedException {
        text = "" + Math.random() * Math.random();

        gmailLoginPage = new GMailLoginPage(driver);
        gmailLoginPage.load();
        gmailMailPage = gmailLoginPage.login(login, password);
        gmailMailPage.sendLetter(login, text);
        gmailMailPage.deleteLetter(text);
        Assert.assertEquals(gmailMailPage.deletedLetter(text), true);
        gmailLoginPage = gmailMailPage.logout();
    }
}