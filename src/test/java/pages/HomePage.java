package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    // Кнопка заказа в хедере
    private By orderButtonOnHeader = By.xpath(".//button[@class='Button_Button__ra12g']");
    // Кнопка заказа в теле сайта
    private By orderButtonOnBody = By.xpath(".//div[starts-with(@class, 'Home_FinishButton')]/button");
    // Секция с вопросами
    private By questionSection = By.xpath(".//div[@class='accordion__button']");
    // Локатор для конкретного вопроса
    private static final String ANSWER_LOCATOR = "(.//div[@class='accordion__panel']/p)[%d]";
    // Локатор заголовка сервиса
    private By serviceHeader = By.xpath(".//img[@alt='Scooter']");
    // Локатор яндекса
    private By yandexLogo = By.xpath(".//img[@alt='Yandex']");
    // Заголовок страницы
    private By mainPageTitle = By.xpath(".//div[@class='Home_Header__iJKdX']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void createNewOrderWithHeaderButton() {
        driver.findElement(orderButtonOnHeader).click();
    }

    public void createNewOrderWithBodyButton() {
        WebElement orderButton = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated((orderButtonOnBody)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", orderButton);

        orderButton.click();
    }

    public void goToFAQ() {
        WebElement FAQ = driver.findElement(questionSection);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", FAQ);
    }

    public String getAnswerText(int index) {
        driver.findElements(questionSection).get(index).click();

        String currentAnswer = String.format(ANSWER_LOCATOR, index + 1);

        WebElement targetAnswer = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(currentAnswer)));

        return targetAnswer.getText();
    }

    public void scooterLogoClick() {
        driver.findElement(serviceHeader).click();
    }

    public void yandexLogoClick() {
        driver.findElement(yandexLogo).click();
    }

    public String getMainPageTitle() {
        WebElement titleElement = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPageTitle));

        return titleElement.getText();
    }


}
