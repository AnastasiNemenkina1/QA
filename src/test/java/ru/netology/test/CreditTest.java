package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.pages.CreditPage;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    private MainPage mainPage;
    private CreditPage creditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUpTest() {
        open("http://localhost:8080/");
        Configuration.holdBrowserOpen = true;
        mainPage = new MainPage();
        creditPage = mainPage.goToCreditPage();
    }

    @AfterEach
    public void clean() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Buy by approved card")
    void shouldTestBuyWithApprovedCard() {
        creditPage.fillForm(DataHelper.getApprovedCard());
        creditPage.verifySuccessNotification();
        assertEquals("APPROVED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Buy by declined card")
    void shouldTestBuyWithDeclinedCard() {
        creditPage.fillForm(DataHelper.getDeclinedCard());
        creditPage.verifyErrorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Empty card number field")
    void shouldTestEmptyCardNumberField() {
        creditPage.fillForm(DataHelper.getEmptyCard());
        creditPage.verifyCardNumberError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("15 simbols in card number field")
    void shouldTestNumberLess16Digits() {
        creditPage.fillForm(DataHelper.getInvalidShortCardNumber());
        creditPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Random number card")
    void shouldTestRandomNumberCard() {
        creditPage.fillForm(DataHelper.getRandomCardNumber());
        creditPage.verifyErrorNotification();
    }

    @Test
    @DisplayName("Card Number '0000 0000 0000 0000'")
    void shouldTestZeroNumberCard() {
        creditPage.fillForm(DataHelper.getZeroCardNumber());
        creditPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Empty month field")
    void shouldTestEmptyMonthField() {
        creditPage.fillForm(DataHelper.getEmptyMonth());
        creditPage.verifyMonthError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Earlier month in month field")
    void shouldTestEarlierMonth() {
        creditPage.fillForm(DataHelper.getExpiredMonth());
        creditPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Next month in month field")
    void shouldTestNextMonth() {
        creditPage.fillForm(DataHelper.getNextMonth());
        creditPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("'00' in month field")
    void shouldTestZeroMonth() {
        creditPage.fillForm(DataHelper.getInvalidZeroMonth());
        creditPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("One Number in month field")
    void shouldTestOneNumInMonth() {
        creditPage.fillForm(DataHelper.getInvalidSingleDigitMonth());
        creditPage.verifyMonthError("Неверный формат");
    }

    @Test
    @DisplayName("'13' in month field")
    void shouldTest13Month() {
        creditPage.fillForm(DataHelper.getInvalidMonth13());
        creditPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty year field")
    void shouldTestEmptyYearField() {
        creditPage.fillForm(DataHelper.getEmptyYear());
        creditPage.verifyYearError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in year field")
    void shouldTestOneSimbolInYearField() {
        creditPage.fillForm(DataHelper.getInvalidSingleDigitYear());
        creditPage.verifyYearError("Неверный формат");
    }

    @Test
    @DisplayName("Earlier year in year field")
    void shouldTestEarlierYear() {
        creditPage.fillForm(DataHelper.getExpiredYear());
        creditPage.verifyYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Last valid year in year field")
    void shouldTestLastValidYear() {
        creditPage.fillForm(DataHelper.getFutureYear());
        creditPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("Over 6 year in year field")
    void shouldTestOverYear() {
        creditPage.fillForm(DataHelper.getTooDistantFutureYear());
        creditPage.verifyYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty holder field")
    void shouldTestEmptyHolderField() {
        creditPage.fillForm(DataHelper.getEmptyHolder());
        creditPage.verifyHolderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in holder field")
    void shouldTestOneLetterInHolderField() {
        creditPage.fillForm(DataHelper.getInvalidSingleLetterHolder());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Rus name in holder field")
    void shouldTestRusHolderName() {
        creditPage.fillForm(DataHelper.getRussianHolder());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Numbers in holder field")
    void shouldTestNumberInHolderField() {
        creditPage.fillForm(DataHelper.getInvalidNumericHolder());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Empty CVC/CVV field")
    void shouldTestEmptyCVVField() {
        creditPage.fillForm(DataHelper.getEmptyCvv());
        creditPage.verifyCvvError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("'000' in CVC/CVV field")
    void shouldTestZeroInCVVField() {
        creditPage.fillForm(DataHelper.getInvalidZeroCvv());
        creditPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("One simbol in CVC/CVV field")
    void shouldTestOneSimbolCVV() {
        creditPage.fillForm(DataHelper.getInvalidSingleDigitCvv());
        creditPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("Letters in CVC/CVV field")
    void shouldTestLettersInCVVField() {
        creditPage.fillForm(DataHelper.getInvalidSymbolsCvv());
        creditPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("Empty form")
    void shouldTestEmptyForm() {
        creditPage.fillForm(DataHelper.getEmptyForm());
        creditPage.verifyCardNumberError("Поле обязательно для заполнения");
        creditPage.verifyMonthError("Поле обязательно для заполнения");
        creditPage.verifyYearError("Поле обязательно для заполнения");
        creditPage.verifyHolderError("Поле обязательно для заполнения");
        creditPage.verifyCvvError("Поле обязательно для заполнения");
    }
}