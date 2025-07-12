package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.pages.DebitPage;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitTest {
    private MainPage mainPage;
    private DebitPage debitPage;

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
        debitPage = mainPage.goToDebitPage();
    }

    @AfterEach
    public void clean() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Buy by approved card")
    void shouldTestBuyWithApprovedCard() {
        debitPage.fillForm(DataHelper.getNumberApprovedCard());
        debitPage.verifySuccessNotification();
        assertEquals("APPROVED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Buy by declined card")
    void shouldTestBuyWithDeclinedCard() {
        debitPage.fillForm(DataHelper.getNumberDeclinedCard());
        debitPage.verifyErrorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Empty card number field")
    void shouldTestEmptyCardNumberField() {
        debitPage.fillForm(DataHelper.getEmptyCardField());
        debitPage.verifyCardNumberError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("15 simbols in card number field")
    void shouldTestNumberLess16Digits() {
        debitPage.fillForm(DataHelper.getNumberless16digits());
        debitPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Random number card")
    void shouldTestRandomNumberCard() {
        debitPage.fillForm(DataHelper.getRandomNumber());
        debitPage.verifyErrorNotification();
    }

    @Test
    @DisplayName("Card Number '0000 0000 0000 0000'")
    void shouldTestZeroNumberCard() {
        debitPage.fillForm(DataHelper.getZeroNumber());
        debitPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Empty month field")
    void shouldTestEmptyMonthField() {
        debitPage.fillForm(DataHelper.getEmptyMonthField());
        debitPage.verifyMonthError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Earlier month in month field")
    void shouldTestEarlierMonth() {
        debitPage.fillForm(DataHelper.getEarlierMonth());
        debitPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Next month in month field")
    void shouldTestNextMonth() {
        debitPage.fillForm(DataHelper.getNextMonth());
        debitPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("'00' in month field")
    void shouldTestZeroMonth() {
        debitPage.fillForm(DataHelper.get00Month());
        debitPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("One Number in month field")
    void shouldTestOneNumInMonth() {
        debitPage.fillForm(DataHelper.getOneNumMonth());
        debitPage.verifyMonthError("Неверный формат");
    }

    @Test
    @DisplayName("'13' in month field")
    void shouldTest13Month() {
        debitPage.fillForm(DataHelper.get13Month());
        debitPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty year field")
    void shouldTestEmptyYearField() {
        debitPage.fillForm(DataHelper.getEmptyYearField());
        debitPage.verifyYearError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in year field")
    void shouldTestOneSimbolInYearField() {
        debitPage.fillForm(DataHelper.getOneSimbolInYearField());
        debitPage.verifyYearError("Неверный формат");
    }

    @Test
    @DisplayName("Earlier year in year field")
    void shouldTestEarlierYear() {
        debitPage.fillForm(DataHelper.getEarlierYear());
        debitPage.verifyYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Last year in year field")
    void shouldTestLastValidYear() {
        debitPage.fillForm(DataHelper.getLstYear());
        debitPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("Over 6 year in year field")
    void shouldTestOverYear() {
        debitPage.fillForm(DataHelper.getOverYear());
        debitPage.verifyYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty holder field")
    void shouldTestEmptyHolderField() {
        debitPage.fillForm(DataHelper.getEmptyHolderField());
        debitPage.verifyHolderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in holder field")
    void shouldTestOneLetterInHolderField() {
        debitPage.fillForm(DataHelper.getOneLetter());
        debitPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Rus name in holder field")
    void shouldTestRusHolderName() {
        debitPage.fillForm(DataHelper.getRusHolder());
        debitPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Numbers in holder field")
    void shouldTestNumberInHolderField() {
        debitPage.fillForm(DataHelper.getNumberHolder());
        debitPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Empty CVC/CVV field")
    void shouldTestEmptyCVVField() {
        debitPage.fillForm(DataHelper.getEmptyCVVField());
        debitPage.verifyCvvError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("'000' in CVC/CVV field")
    void shouldTestZeroInCVVField() {
        debitPage.fillForm(DataHelper.getZeroCVV());
        debitPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("One simbol in CVC/CVV field")
    void shouldTestOneSimbolCVV() {
        debitPage.fillForm(DataHelper.getOneSimbolCVV());
        debitPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("Letters in CVC/CVV field")
    void shouldTestLettersInCVVField() {
        debitPage.fillForm(DataHelper.getLettersCVV());
        debitPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("Empty form")
    void shouldTestEmptyForm() {
        debitPage.fillForm(DataHelper.getEmptyForm());
        debitPage.verifyCardNumberError("Поле обязательно для заполнения");
        debitPage.verifyMonthError("Поле обязательно для заполнения");
        debitPage.verifyYearError("Поле обязательно для заполнения");
        debitPage.verifyHolderError("Поле обязательно для заполнения");
        debitPage.verifyCvvError("Поле обязательно для заполнения");
    }
}