package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement heading = $x("//h2[contains(text(), 'Путешествие дня')]");
    private final SelenideElement buyButton = $x("//span[contains(text(), 'Купить')]/ancestor::button");
    private final SelenideElement creditButton = $x("//span[contains(text(), 'Купить в кредит')]/ancestor::button");

    public MainPage() {
        heading.shouldBe(visible, Duration.ofSeconds(15));
    }

    public DebitPage goToDebitPage() {
        buyButton
                .shouldBe(enabled, Duration.ofSeconds(10))
                .click();
        return new DebitPage();
    }

    public CreditPage goToCreditPage() {
        creditButton
                .shouldBe(enabled, Duration.ofSeconds(10))
                .click();
        return new CreditPage();
    }
}