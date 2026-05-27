package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.HomePage;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.get("https://qa-scooter.education-services.ru");
    }

    @ParameterizedTest
    @MethodSource("answerProvider")
    public void checkFaq(int index, String expectedAnswer) {
        HomePage homePage = new HomePage(driver);
        homePage.goToFAQ();
        String actualAnswer = homePage.getAnswerText(index);
        assertEquals(expectedAnswer, actualAnswer, "Ответы не совпадают");
    }

    @Test
    public void checkRedirectYandexLogo(){
        HomePage homePage = new HomePage(driver);

        String parentWindow = driver.getWindowHandle();
        homePage.yandexLogoClick();

        Set<String> allWindowHandles = driver.getWindowHandles();

        for (String windowHandle : allWindowHandles ) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
            }
        }
        String newPageTitle = driver.getTitle();

        assertEquals("Яндекс — быстрый поиск в интернете", newPageTitle, "Ожидается заголовок Yandex, а получен: " + newPageTitle);
    }

    @Test
    public void checkRedirectScooterLogo(){
        HomePage homePage = new HomePage(driver);
        String expectedTitle = homePage.getMainPageTitle();

        homePage.createNewOrderWithHeaderButton();
        homePage.scooterLogoClick();

        String actualTitle = homePage.getMainPageTitle();

        assertEquals(expectedTitle,actualTitle);
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    static Stream<Arguments> answerProvider() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }
}
