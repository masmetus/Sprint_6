package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserOrderPage {

    private WebDriver driver;

    // Локатор имени заказывающего
    private By nameField = By.xpath(".//input[@placeholder='* Имя']");
    // Локатор фамилии заказывающего
    private By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    // Локатор адреса
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Локатор поля метро
    private By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    // Локатор телефона заказывающего
    private By phoneNumberField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    //Локаторы для ошибок
    private By errorMessage = By.xpath(".//div[contains(@class, 'Input_ErrorMessage__3HvIb Input_Visible___syz6')]");
    private By errorMetroMessage = By.xpath(".//div[@class='Order_MetroError__1BtZb']");


    // Локатор имени заказывающего
    private By nextButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Локатор для поиска метро в выпадающем списке
    private static final String METRO_STATION = "(//div[text()='%s'])";

    public UserOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setNameField(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setSurnameField(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void setAddressField(String address) {
        driver.findElement(addressField).sendKeys(address);

    }

    public void setMetroField(String metro) {
        driver.findElement(metroField).sendKeys(metro);

        String currentStation = String.format(METRO_STATION, metro);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(currentStation))).click();
    }

    public void setPhoneNumberField(String phoneNumber) {
        driver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void setUserInfo(String name, String surname, String address, String metro, String phoneNumber) {
        setNameField(name);
        setSurnameField(surname);
        setAddressField(address);
        if (!metro.isEmpty()) {
            setMetroField(metro);
        }
        setPhoneNumberField(phoneNumber);
    }

    public String getErrorMessage(String type) {
        By locator;

        if ("Выберите станцию".equals(type)) {
            locator = errorMetroMessage;
        } else {
            locator = errorMessage;
        }

        WebElement message = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(locator));

        return message.getText();
    }
}
