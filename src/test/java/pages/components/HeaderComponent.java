package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderComponent {

    private WebDriver driver;

    private By orderStatusButton = By.xpath(".//button[@class='Header_Link__1TAG7']");
    private By inputOrderNumberField = By.xpath(".//input[@class='Input_Input__1iN_Z Header_Input__xIoUq']");
    private By checkOrderStatusButton = By.xpath(".//button[@class='Button_Button__ra12g Header_Button__28dPO']");


    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
    }

    public void clickStatusButton() {
        driver.findElement(orderStatusButton).click();
    }

    public void setEnterOrderNumber(String orderNumber) {
        WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(inputOrderNumberField));

        inputField.sendKeys(orderNumber);
    }

    public void clickCheckOrderButton() {
        driver.findElement(checkOrderStatusButton).click();
    }


}
