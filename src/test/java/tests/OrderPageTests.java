package tests;

import data.RentalPeriod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.HomePage;
import pages.RentOrderPage;
import pages.UserOrderPage;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderPageTests {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.get("https://qa-scooter.education-services.ru");
    }


    @ParameterizedTest
    @MethodSource("orderProvider")
    public void createOrder(String whereOrderButton, String name, String surname, String address, String metro, String phoneNumber,
                            LocalDate date, String rentalPeriod, String color, String comment) {
        HomePage homePage = new HomePage(driver);
        if (whereOrderButton.equals("body")) {
            homePage.createNewOrderWithBodyButton();
        } else {
            homePage.createNewOrderWithHeaderButton();
        }

        UserOrderPage userOrderPage = new UserOrderPage(driver);
        userOrderPage.setUserInfo(name, surname, address, metro, phoneNumber);
        userOrderPage.clickNextButton();

        RentOrderPage rentOrderPage = new RentOrderPage(driver);
        rentOrderPage.setOrderInfo(date, rentalPeriod, color, comment);
        rentOrderPage.clickOrderButton();

        rentOrderPage.clickConfirmOrder();

        String actualTitle = rentOrderPage.getConfirmTitleText();

        assertTrue(actualTitle.startsWith("Заказ оформлен"), "Ожидался заголовок об успешном заказе, " +
                "но получен: " + actualTitle);
    }


    @ParameterizedTest
    @MethodSource("invalidDataProvider")
    public void checkValidation(String name, String surname, String address, String metro, String phone, String expectedError, String type) {
        HomePage homePage = new HomePage(driver);
        homePage.createNewOrderWithHeaderButton();

        UserOrderPage userOrderPage = new UserOrderPage(driver);
        userOrderPage.setUserInfo(name, surname, address, metro, phone);
        userOrderPage.clickNextButton();

        String actualError = userOrderPage.getErrorMessage(expectedError);
        assertEquals(expectedError, actualError, "Текст ошибки не совпадает!");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    static Stream<Arguments> orderProvider() {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        return Stream.of(
                Arguments.of("body", "Иван", "Иванович", "ул.Пушкина, д. Колотушкина", "Коптево", "79999999999",
                        today, RentalPeriod.SEVEN_DAYS.getText(), "black", "Ты лапочка"),
                Arguments.of("header", "Геннадий", "Думович", "ул.Колотушкина, д. Пушкина", "Бульвар Рокоссовского", "78888888888",
                        tomorrow, RentalPeriod.ONE_DAY.getText(), "grey", "")

        );
    }

    static Stream<Arguments> invalidDataProvider() {
        return Stream.of(
                Arguments.of("", "Опич", "Огуреченск", "Сокольники", "79000000000", "Введите корректное имя", "input"),
                Arguments.of("Илюха", "", "Егорьевск", "Сокольники", "89000000000", "Введите корректную фамилию", "metro"),
                Arguments.of("Илюха", "Опич", "Маслачусец", "", "99000000000", "Выберите станцию", "input"),
                Arguments.of("Илюха", "Опич", "Суздаль", "Черкизовская", "", "Введите корректный номер", "input")

        );
    }
}
