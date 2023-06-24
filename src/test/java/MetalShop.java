import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MetalShop {

    static WebDriver driver = new ChromeDriver();
    Faker faker = new Faker();
    private String password = "Igor12345!";

    private String name = "Igor Anastaziak";

    private String username = "igoranasasdasdas@gmail.com";

    @BeforeAll

    public static void setUp() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        WebDriverWait wait = new WebDriverWait(driver, 5);
    }

    @Test
    @Order(1)
    public void emptyUserLogin() {

        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys(username);
        driver.findElement(By.xpath("//button[@name = 'login']")).click();
        String errorText = driver.findElement(By.cssSelector("woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", errorText);


    }

    @Test
    @Order(2)

    public void emptyPasswordLogin() {

        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
        String errorText = driver.findElement(By.cssSelector("woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", errorText);

    }

    @Test
    @Order(3)

    public void successRegister() throws InterruptedException {
        String username = faker.pokemon().name();
        String email = username + faker.name().username() + "wp.pl";
        driver.findElement(By.linkText("register")).click();
        driver.findElement(By.cssSelector("#user_login")).sendKeys(username);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        driver.findElement(By.cssSelector(".ur-submit-button")).click();
        Assertions.assertEquals("User successfully registered.", driver.findElement(By.cssSelector("ur-submit-" +
                "message-node ul")).getText());


    }

    @Test
    @Order(4)

    public void ifHomePageHasElements() {

        String logoText = driver.findElement(By.cssSelector(".site-title")).getText();
        WebElement search = driver.findElement(By.cssSelector("#woocommerce-product-search-field-0"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".site-title")).isDisplayed() &&
                search.isDisplayed());
        Assertions.assertEquals("Softie Metal Shop", logoText);

    }

    @Test
    @Order(5)

    public void fromeTheHomePageToContact(){

       WebElement ContactLink = driver.findElement(By.linkText("Kontakt"));
       driver.findElement(By.linkText("Kontakt")).click();
       String expectedTitle = "Kontakt";
       String actualTitle = driver.getTitle();
       if (actualTitle.equals(expectedTitle))
       Assertions.assertEquals("Correct transiton to page contact", driver.findElement(By.cssSelector("wpcf7-spinner"))
               .getText());
    }

    @Test
    @Order(6)

    public void fromeLogInPageToHomePage(){

        WebElement LogInPage = driver.findElement(By.linkText("Moje Konto"));
        driver.findElement(By.linkText("Moje Konto")).click();
        String expectedTitle = "Moje Konto";
        WebElement HomePage = driver.findElement(By.linkText("Softie Metal Shop"));
        driver.findElement(By.linkText("Softie Metal Shop")).click();
        String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle))
        Assertions.assertEquals("Correct transiton to home page", driver.findElement(By.cssSelector("attachment-woocommerce_thumbnail"))
                .getText());

    }

    @Test
    @Order(7)

    public void sendMessage(){

        driver.findElement(By.linkText("Kontakt")).click();
        driver.findElement(By.cssSelector(".wpcf7-form-control")).sendKeys(name);
        driver.findElement(By.cssSelector(".wpcf7-email")).sendKeys(username);
        driver.findElement(By.cssSelector(".wpcf7-text")).sendKeys("Topic");
        driver.findElement(By.cssSelector(".wpcf7-textarea")).sendKeys("My message");
        driver.findElement(By.cssSelector(".wpcf7-submit")).click();
        WebElement errorMessage = driver.findElement(By.xpath("//div[text() = 'Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.'"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @AfterEach

    public void logout() {
        driver.findElement(By.linkText("Moje konto")).click();
        if (driver.findElements(By.xpath("//a[text() = 'Wyloguj się']")).size() != 0) {
            driver.findElement(By.xpath("//a[text() = 'Wyloguj się']")).click();
        }
    }
    @AfterAll
    public static void closeBrowser () {
        driver.quit();
    }
}



