package yandex.praktikum;


import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.praktikum.pages.MainPage;

import java.util.Objects;

@RunWith(Parameterized.class)
public class MainPageTest {
    private final String username;

    private final String userLastname;
    private final String address;
    private final String stationName;
    private final String phoneNumber;
    private final String orderDate;
    private final String quantityOfDays;
    private final String colorId;
    private final String comment;
    private final String orderButtonPlace;

    @ClassRule
    public static DriverRule driverRule = new DriverRule();

    public MainPageTest(String orderButtonPlace, String username, String userLastname, String address, String stationName, String phoneNumber, String orderDate, String quantityOfDays, String colorId, String comment) {
        this.orderButtonPlace = orderButtonPlace;
        this.username = username;
        this.userLastname = userLastname;
        this.address = address;
        this.stationName = stationName;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.quantityOfDays = quantityOfDays;
        this.colorId = colorId;
        this.comment = comment;
    }

    @Before
    public void openPage() {
        new MainPage(driverRule.getDriver()).open().acceptCookiesIfExist();
    }

    @Parameterized.Parameters
    public static Object[][] testOrder() {
        return new Object[][]{
                {"header", "Светлана", "Федотова", "Москва", "Лермонтовский проспект", "+79991547867", "10.02.2025", "сутки", "black", ""},
                {"middle", "Игорь", "Кошкин", "Москва", "Медведково", "89557894532", "01.02.2025", "двое суток", "grey", "звонить по указанному номеру"},
                {"header", "Иван", "Иванов", "Люберцы", "Сокольники", "+79674532213", "20.03.2025", "шестеро суток", "black", "главное, чтобы самокат был заряжен"},
        };
    }

    @Test
    public void checkOrder() {
        MainPage mainPageObj = new MainPage(driverRule.getDriver());

        if (Objects.equals(this.orderButtonPlace, "header")) {
            mainPageObj.clickOrderHeaderButton();
        } else {
            mainPageObj.clickOrderMiddleButton();
        }

        mainPageObj.waitForOrderForm()
                .setUsername(username)
                .setUserLastname(userLastname)
                .setAddress(address)
                .choosingMetroStation(stationName)
                .setPhoneNumber(phoneNumber)
                .clickNextButton()
                .waitForOrderAboutRentForm()
                .setDate(orderDate)
                .choosingTheRentalPeriod(quantityOfDays)
                .choosingScooterColor(colorId)
                .setComment(comment)
                .clickOrderModalButton()
                .orderConfirmation()
                .checkOrderSuccess();

    }
}
