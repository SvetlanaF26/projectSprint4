package yandex.praktikum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yandex.praktikum.EnvConfig;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    // кнопка Заказать в хедере
    private final By orderHeaderButton = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");
    // кнопка Заказать в центре
    private final By orderMiddleButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");
    // кнопка Заказать в середине страницы
    private final By orderButtonInModal = By.xpath(".//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");
    // Форма "Для кого самокат"
    private final By orderForm = By.xpath(".//div[text()='Для кого самокат']");
    // поле ввода имени
    private final By nameInputField = By.xpath("//input[@placeholder='* Имя']");
    // поле ввода фамилии
    private final By lastNameInputField = By.xpath("//input[@placeholder='* Фамилия']");
    // поле ввода адреса
    private final By addressInputField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // поле станция метро
    private final By metroStationField = By.className("select-search__input");
    // список станций метро
    private final By listOfMetroStations = By.className("select-search__options");
    // поле ввода номера телефона
    private final By phoneInputField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // кнопка Далее
    private final By theNextButton = By.xpath(".//button[starts-with(@class,'Button_Button') and text()='Далее']");
    // форма заказа "Про аренду"
    private final By orderAboutRentForm = By.xpath(".//div[text()='Про аренду']");
    // поле ввода даты
    private final By dateSelectionField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // поле выбора срока аренды
    private final By theRentalPeriodField = By.className("Dropdown-root");
    // выпадающий список дней аренды
    private final By rentalDaysDropdown = By.className("Dropdown-menu");
    // поле ввода комментария
    private final By commentInputField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // окно подтверждения заказа
    private final By orderModal = By.className("Order_Modal__YZ-d3");
    // кнопка Да
    private final By yesButton = By.xpath(".//button[text()= 'Да']");
    // кнопка принять куки
    private final By cookieButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // открываем страницу
    public MainPage open() {
        driver.get(EnvConfig.BASE_URL);

        return this;
    }

    // Проверяем есть ли куки Cartoshka=true если нет то кликаем по скрытию сообщение о куки
    public MainPage acceptCookiesIfExist() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String cookies = (String) js.executeScript("return document.cookie;");

            assert cookies != null;
            if (!cookies.contains("Cartoshka=true")) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
                WebElement cookieElement = wait.until(ExpectedConditions.presenceOfElementLocated(cookieButton));

                if (cookieElement.isDisplayed()) {
                    cookieElement.click();
                }
            }
        } catch (TimeoutException | NoSuchElementException e) {
            // Игнорируем, если кнопки нет
        }

        return this;
    }


    public MainPage clickOrderHeaderButton() {
        driver.findElement(orderHeaderButton).click();

        return this;
    }


    public MainPage waitForOrderForm() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(orderForm));

        return this;
    }

    // вводим имя
    public MainPage setUsername(String username) {
        driver.findElement(nameInputField).sendKeys(username);

        return this;
    }

    // вводим Фамилию
    public MainPage setUserLastname(String userLastname) {
        driver.findElement(lastNameInputField).sendKeys(userLastname);

        return this;
    }

    // вводим адрес
    public MainPage setAddress(String address) {
        driver.findElement(addressInputField).sendKeys(address);

        return this;
    }

    // выбираем станцию метро
    public MainPage choosingMetroStation(String stationName) {
        var metroStationBlock = driver.findElement(metroStationField);

        metroStationBlock.click();

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(listOfMetroStations));

        metroStationBlock.sendKeys(stationName);

        var stationElement = driver.findElement(getStationLocator(stationName));

        stationElement.click();

        return this;
    }

    private By getStationLocator(String stationName) {
        return By.xpath("//button//div[text()='" + stationName + "']");
    }

    // вводим номер телефона
    public MainPage setPhoneNumber(String phoneNumber) {
        driver.findElement(phoneInputField).sendKeys(phoneNumber);

        return this;
    }

    // нажимаем кнопку далее
    public MainPage clickNextButton() {
        driver.findElement(theNextButton).click();

        return this;
    }

    // ждём появление формы Про аренду
    public MainPage waitForOrderAboutRentForm() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(orderAboutRentForm));

        return this;
    }

    // вводим дату "когда нужно привезти самокат"
    public MainPage setDate(String orderDate) {
        driver.findElement(dateSelectionField).sendKeys(orderDate);
        new Actions(driver)
                .sendKeys(Keys.ENTER)
                .perform();

        return this;
    }

    private By getQuantityOfDaysLocator(String quantityOfDays) {
        return By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + quantityOfDays + "']");

    }

    // выбираем срок аренды
    public MainPage choosingTheRentalPeriod(String quantityOfDays) {
        driver.findElement(theRentalPeriodField).click();

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(rentalDaysDropdown));

        driver.findElement(getQuantityOfDaysLocator(quantityOfDays)).click();

        return this;
    }

    // выбираем цвет самоката
    public MainPage choosingScooterColor(String colorId) {
        driver.findElement(By.id(colorId)).click();
        return this;
    }

    // пишем комментарий
    public MainPage setComment(String comment) {
        driver.findElement(commentInputField).sendKeys(comment);

        return this;
    }

    public MainPage clickOrderMiddleButton() {
        driver.findElement(orderMiddleButton).click();

        return this;
    }

    // нажимаем на кнопку заказать
    public MainPage clickOrderModalButton() {
        driver.findElement(orderButtonInModal).click();

        return this;
    }

    // ждём окно "Хотите оформить заказ?" и нажимаем кнопку Да
    public MainPage orderConfirmation() {

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(orderModal));

        driver.findElement(yesButton).click();

        return this;
    }

    // проверяем, что появилось окно с сообщением об успешном заказе
    public MainPage checkOrderSuccess() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(orderModal));

        WebElement modal = driver.findElement(orderModal);
        assertTrue("Модальное окно заказа не отображается", modal.isDisplayed());

        String modalText = modal.getText();
        assertTrue("Текст 'Заказ оформлен' отсутствует в модальном окне", modalText.contains("Заказ оформлен"));

        return this;
    }


}

