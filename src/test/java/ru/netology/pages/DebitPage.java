package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DebitPage {

    private final SelenideElement heading = $x("//h3[contains(text(), 'Оплата по карте')]");

    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement holderField = $x("//span[contains(text(), 'Владелец')]/..//input");
    private final SelenideElement cvvField = $("input[placeholder='999']");
    private final SelenideElement continueButton = $x("//span[contains(text(), 'Продолжить')]/..");

    private final SelenideElement cardNumberError = $x("//*[contains(text(), 'Номер карты')]/following-sibling::*[contains(@class, 'input__sub')]");
    private final SelenideElement monthError = $x("//*[contains(text(), 'Месяц')]/following-sibling::*[contains(@class, 'input__sub')]");
    private final SelenideElement yearError = $x("//*[contains(text(), 'Год')]/following-sibling::*[contains(@class, 'input__sub')]");
    private final SelenideElement holderError = $x("//*[contains(text(), 'Владелец')]/following-sibling::*[contains(@class, 'input__sub')]");
    private final SelenideElement cvvError = $x("//*[contains(text(), 'CVV')]/following-sibling::*[contains(@class, 'input__sub')]");

    private final SelenideElement successNotification = $(".notification_status_ok .notification__content");
    private final SelenideElement errorNotification = $(".notification_status_error .notification__content");

    public DebitPage() {
        heading.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getNumber());  // Исправлено с getNumberCard() на getNumber()
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        holderField.setValue(cardInfo.getHolder());
        cvvField.setValue(cardInfo.getCvv());
        continueButton.click();
    }

    public void verifySuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void verifyErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Банк отказал в проведении операции."));
    }

    public void verifyCardNumberError(String expectedError) {
        cardNumberError.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedError));
    }

    public void verifyMonthError(String expectedError) {
        monthError.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedError));
    }

    public void verifyYearError(String expectedError) {
        yearError.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedError));
    }

    public void verifyHolderError(String expectedError) {
        holderError.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedError));
    }

    public void verifyCvvError(String expectedError) {
        cvvError.shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedError));
    }
}