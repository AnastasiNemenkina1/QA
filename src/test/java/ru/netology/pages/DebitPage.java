package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DebitPage {

    private final SelenideElement heading = $x("//h3[text()='Оплата по карте']");

    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement holderField = $x("//*[text()='Владелец']/..//input");
    private final SelenideElement cvvField = $("[placeholder='999']");
    private final SelenideElement continueButton = $x("//span[text()='Продолжить']");

    private final SelenideElement cardNumberError = $x("//*[text()='Номер карты']/following-sibling::span[@class='input__sub']");
    private final SelenideElement monthError = $x("//*[text()='Месяц']/following-sibling::span[@class='input__sub']");
    private final SelenideElement yearError = $x("//*[text()='Год']/following-sibling::span[@class='input__sub']");
    private final SelenideElement holderError = $x("//*[text()='Владелец']/following-sibling::span[@class='input__sub']");
    private final SelenideElement cvvError = $x("//*[contains(text(),'CVV')]/following-sibling::span[@class='input__sub']");

    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public DebitPage() {
        heading.shouldBe(visible);
    }

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getNumberCard());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        holderField.setValue(cardInfo.getValidHolder());
        cvvField.setValue(cardInfo.getValidCVV());
        continueButton.click();
    }

    public void verifySuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void verifyErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка"))
                .shouldHave(text("Банк отказал в проведении операции."));
    }

    public void verifyCardNumberError(String expectedError) {
        cardNumberError.shouldBe(visible)
                .shouldHave(exactText(expectedError));
    }

    public void verifyMonthError(String expectedError) {
        monthError.shouldBe(visible)
                .shouldHave(exactText(expectedError));
    }

    public void verifyYearError(String expectedError) {
        yearError.shouldBe(visible)
                .shouldHave(exactText(expectedError));
    }

    public void verifyHolderError(String expectedError) {
        holderError.shouldBe(visible)
                .shouldHave(exactText(expectedError));
    }

    public void verifyCvvError(String expectedError) {
        cvvError.shouldBe(visible)
                .shouldHave(exactText(expectedError));
    }
}