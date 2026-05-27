package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderStatusPage {

    private WebDriver driver;

    private By notFoundImg = By.xpath(".//img[@alt='Not found']");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isNotFoundImgPresent() {
        WebElement img = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(notFoundImg));
        
        return img.isDisplayed();
    }

}
