package yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yandex.praktikum.EnvConfig;

import java.time.Duration;


public class ImportantQuestions {
    private final WebDriver driver;
    private final By cookieButton = By.id("rcc-confirm-button");
    private final String accordionQuestionPrefix = "accordion__heading-";
    private final String accordionAnswerPrefix = "accordion__panel-";

    public ImportantQuestions(WebDriver driver) {
        this.driver = driver;
    }

    public ImportantQuestions open() {
        driver.get(EnvConfig.BASE_URL);

        return this;
    }

    public ImportantQuestions acceptCookies() {
        waitForCookiesFloater();
        driver.findElement(cookieButton).click();
        waitForCookiesFloaterToDisappear();

        return this;
    }

    private void waitForCookiesFloater() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(cookieButton));
    }

    private void waitForCookiesFloaterToDisappear() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT))
                .until(ExpectedConditions.invisibilityOfElementLocated(cookieButton));
    }

    public ImportantQuestions clickOnQuestion(String id) {
        driver.findElement(By.id(accordionQuestionPrefix + id)).click();

        return this;
    }

    public ImportantQuestions waitForAnswer(String id) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(accordionAnswerPrefix + id)));

        return this;
    }

    public ImportantQuestions checkAnswerText(String id, String expectedAnswer) {
        String actualText = driver.findElement(By.id(accordionAnswerPrefix + id)).getText();
        assert actualText.equals(expectedAnswer) : "Ожидали: " + expectedAnswer + ", но получили: " + actualText;

        return this;
    }

    public ImportantQuestions checkAnswerIsInvisible(String id) {
        assert !driver.findElement(By.id(accordionAnswerPrefix + id)).isDisplayed();

        return this;
    }
}














