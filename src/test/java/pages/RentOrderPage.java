package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentOrderPage {

    WebDriver driver;

    // Локатор для поиска срока аренды
    private final String RENTAL_PERIOD_TEXT = "//*[@class='Dropdown-option' and text()='%s']";
    // Локатор даты заказа
    private By dateScootDeliveryField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Локатор для раскрытия срока аренды
    private By rentalPeriodField = By.xpath(".//span[@class='Dropdown-arrow']");
    // Локатор коммента курьеру
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Локатор кнопки заказать в конце формы
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Локатор подтверждения заказа
    private By confirmOrderButton = By.xpath(".//button[text()='Да']");
    // Локатор заголовка успешно оформленного заказа
    private By successOrderTitle = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");

    public RentOrderPage(WebDriver driver) {
        this.driver = driver;
    }


    public void setDateScootDeliveryField(LocalDate date) {
        String formatedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        driver.findElement(dateScootDeliveryField).sendKeys(formatedDate);
    }

    public void setDateRentalPeriodField(String rentalPeriod) {
        driver.findElement(rentalPeriodField).click();

        String period = String.format(RENTAL_PERIOD_TEXT, rentalPeriod);

        driver.findElement(By.xpath(period)).click();
    }

    public void selectScooterColor(String color) {
        By scooterColor = By.id(color.toLowerCase());
        WebElement checkbox = driver.findElement(scooterColor);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void setCommentField(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }


    public void setOrderInfo(LocalDate date, String rentalPeriod, String color, String comment) {
        setDateScootDeliveryField(date);
        setDateRentalPeriodField(rentalPeriod);
        selectScooterColor(color);
        setCommentField(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']")));
    }

    public void clickConfirmOrder() {
        driver.findElement(confirmOrderButton).click();
    }

    public String getConfirmTitleText() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(confirmOrderButton));
        return driver.findElement(successOrderTitle).getText().trim();
    }
}
