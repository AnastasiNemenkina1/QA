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
        creditPage.fillForm(DataHelper.getNumberApprovedCard());
        creditPage.verifySuccessNotification();
        assertEquals("APPROVED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Buy by declined card")
    void shouldTestBuyWithDeclinedCard() {
        creditPage.fillForm(DataHelper.getNumberDeclinedCard());
        creditPage.verifyErrorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Empty card number field")
    void shouldTestEmptyCardNumberField() {
        creditPage.fillForm(DataHelper.getEmptyCardField());
        creditPage.verifyCardNumberError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("15 simbols in card number field")
    void shouldTestNumberLess16Digits() {
        creditPage.fillForm(DataHelper.getNumberless16digits());
        creditPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Random number card")
    void shouldTestRandomNumberCard() {
        creditPage.fillForm(DataHelper.getRandomNumber());
        creditPage.verifyErrorNotification();
    }

    @Test
    @DisplayName("Card Number '0000 0000 0000 0000'")
    void shouldTestZeroNumberCard() {
        creditPage.fillForm(DataHelper.getZeroNumber());
        creditPage.verifyCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Empty month field")
    void shouldTestEmptyMonthField() {
        creditPage.fillForm(DataHelper.getEmptyMonthField());
        creditPage.verifyMonthError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Earlier month in month field")
    void shouldTestEarlierMonth() {
        creditPage.fillForm(DataHelper.getEarlierMonth());
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
        creditPage.fillForm(DataHelper.get00Month());
        creditPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("One Number in month field")
    void shouldTestOneNumInMonth() {
        creditPage.fillForm(DataHelper.getOneNumMonth());
        creditPage.verifyMonthError("Неверный формат");
    }

    @Test
    @DisplayName("'13' in month field")
    void shouldTest13Month() {
        creditPage.fillForm(DataHelper.get13Month());
        creditPage.verifyMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty year field")
    void shouldTestEmptyYearField() {
        creditPage.fillForm(DataHelper.getEmptyYearField());
        creditPage.verifyYearError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in year field")
    void shouldTestOneSimbolInYearField() {
        creditPage.fillForm(DataHelper.getOneSimbolInYearField());
        creditPage.verifyYearError("Неверный формат");
    }

    @Test
    @DisplayName("Earlier year in year field")
    void shouldTestEarlierYear() {
        creditPage.fillForm(DataHelper.getEarlierYear());
        creditPage.verifyYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Last valid year in year field")
    void shouldTestLastValidYear() {
        creditPage.fillForm(DataHelper.getLstYear());
        creditPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("Over 6 year in year field")
    void shouldTestOverYear() {
        creditPage.fillForm(DataHelper.getOverYear());
        creditPage.verifyYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty holder field")
    void shouldTestEmptyHolderField() {
        creditPage.fillForm(DataHelper.getEmptyHolderField());
        creditPage.verifyHolderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("One simbol in holder field")
    void shouldTestOneLetterInHolderField() {
        creditPage.fillForm(DataHelper.getOneLetter());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Rus name in holder field")
    void shouldTestRusHolderName() {
        creditPage.fillForm(DataHelper.getRusHolder());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Numbers in holder field")
    void shouldTestNumberInHolderField() {
        creditPage.fillForm(DataHelper.getNumberHolder());
        creditPage.verifyHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Empty CVC/CVV field")
    void shouldTestEmptyCVVField() {
        creditPage.fillForm(DataHelper.getEmptyCVVField());
        creditPage.verifyCvvError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("'000' in CVC/CVV field")
    void shouldTestZeroInCVVField() {
        creditPage.fillForm(DataHelper.getZeroCVV());
        creditPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("One simbol in CVC/CVV field")
    void shouldTestOneSimbolCVV() {
        creditPage.fillForm(DataHelper.getOneSimbolCVV());
        creditPage.verifyCvvError("Неверный формат");
    }

    @Test
    @DisplayName("Letters in CVC/CVV field")
    void shouldTestLettersInCVVField() {
        creditPage.fillForm(DataHelper.getLettersCVV());
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