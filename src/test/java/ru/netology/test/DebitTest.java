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
        debitPage.fillingOutForm(DataHelper.getNumberApprovedCard());
        debitPage.successNotification();
        assertEquals("APPROVED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Buy by declined card")
    void shouldTestBuyWithDeclinedCard() {
        debitPage.fillingOutForm(DataHelper.getNumberDeclinedCard());
        debitPage.errorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Empty card number field")
    void shouldTestEmptyCardNumberField() {
        debitPage.fillingOutForm(DataHelper.getEmptyCardField());
        debitPage.requiredField();
    }

    @Test
    @DisplayName("15 simbols in card number field")
    void shouldTestNumberLess16Digits() {
        debitPage.fillingOutForm(DataHelper.getNumberless16digits());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Random number card")
    void shouldTestRandomNumberCard() {
        debitPage.fillingOutForm(DataHelper.getRandomNumber());
        debitPage.errorNotification();
    }

    @Test
    @DisplayName("Card Number '0000 0000 0000 0000'")
    void shouldTestZeroNumberCard() {
        debitPage.fillingOutForm(DataHelper.getZeroNumber());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty month field")
    void shouldTestEmptyMonthField() {
        debitPage.fillingOutForm(DataHelper.getEmptyMonthField());
        debitPage.requiredField();
    }

    @Test
    @DisplayName("Earlier month in month field")
    void shouldTestEarlierMonth() {
        debitPage.fillingOutForm(DataHelper.getEarlierMonth());
        debitPage.incorrectDeadline();
    }

    @Test
    @DisplayName("Next month in month field")
    void shouldTestNextMonth() {
        debitPage.fillingOutForm(DataHelper.getNextMonth());
        debitPage.successNotification();
    }

    @Test
    @DisplayName("'00' in month field")
    void shouldTestZeroMonth() {
        debitPage.fillingOutForm(DataHelper.get00Month());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("One Number in month field")
    void shouldTestOneNumInMonth() {
        debitPage.fillingOutForm(DataHelper.getOneNumMonth());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("'13' in month field")
    void shouldTest13Month() {
        debitPage.fillingOutForm(DataHelper.get13Month());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty year field")
    void shouldTestEmptyYearField() {
        debitPage.fillingOutForm(DataHelper.getEmptyYearField());
        debitPage.requiredField();
    }

    @Test
    @DisplayName("One simbol in year field")
    void shouldTestOneSimbolInYearField() {
        debitPage.fillingOutForm(DataHelper.getOneSimbolInYearField());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Earlier year in year field")
    void shouldTestEarlierYear() {
        debitPage.fillingOutForm(DataHelper.getEarlierYear());
        debitPage.deadlineIsOver();
    }

    @Test
    @DisplayName("Last year in year field")
    void shouldTestLastValidYear() {
        debitPage.fillingOutForm(DataHelper.getLstYear());
        debitPage.successNotification();
    }

    @Test
    @DisplayName("Over 6 year in year field")
    void shouldTestOverYear() {
        debitPage.fillingOutForm(DataHelper.getOverYear());
        debitPage.incorrectDeadline();
    }

    @Test
    @DisplayName("Empty holder field")
    void shouldTestEmptyHolderField() {
        debitPage.fillingOutForm(DataHelper.getEmptyHolderField());
        debitPage.requiredField();
    }

    @Test
    @DisplayName("One simbol in holder field")
    void shouldTestOneLetterInHolderField() {
        debitPage.fillingOutForm(DataHelper.getOneLetter());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Rus name in holder field")
    void shouldTestRusHolderName() {
        debitPage.fillingOutForm(DataHelper.getRusHolder());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Numbers in holder field")
    void shouldTestNumberInHolderField() {
        debitPage.fillingOutForm(DataHelper.getNumberHolder());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty CVC/CVV field")
    void shouldTestEmptyCVVField() {
        debitPage.fillingOutForm(DataHelper.getEmptyCVVField());
        debitPage.setRequiredFieldForCVVField();
    }

    @Test
    @DisplayName("'000' in CVC/CVV field")
    void shouldTestZeroInCVVField() {
        debitPage.fillingOutForm(DataHelper.getZeroCVV());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("One simbol in CVC/CVV field")
    void shouldTestOneSimbolCVV() {
        debitPage.fillingOutForm(DataHelper.getOneSimbolCVV());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Letters in CVC/CVV field")
    void shouldTestLettersInCVVField() {
        debitPage.fillingOutForm(DataHelper.getLettersCVV());
        debitPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty form")
    void shouldTestEmptyForm() {
        debitPage.fillingOutForm(DataHelper.getEmptyForm());
        debitPage.setRequiredFieldForNumberCard();
        debitPage.setRequiredFieldForMonthField();
        debitPage.setRequiredFieldForYearField();
        debitPage.setRequiredFieldForHolderField();
        debitPage.setRequiredFieldForCVVField();
    }
}