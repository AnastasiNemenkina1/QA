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
        creditPage.fillingOutForm(DataHelper.getNumberApprovedCard());
        creditPage.successNotification();
        assertEquals("APPROVED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Buy by declined card")
    void shouldTestBuyWithDeclinedCard() {
        creditPage.fillingOutForm(DataHelper.getNumberDeclinedCard());
        creditPage.errorNotification();
        assertEquals("DECLINED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Empty card number field")
    void shouldTestEmptyCardNumberField() {
        creditPage.fillingOutForm(DataHelper.getEmptyCardField());
        creditPage.requiredField();
    }

    @Test
    @DisplayName("15 simbols in card number field")
    void shouldTestNumberLess16Digits() {
        creditPage.fillingOutForm(DataHelper.getNumberless16digits());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Random number card")
    void shouldTestRandomNumberCard() {
        creditPage.fillingOutForm(DataHelper.getRandomNumber());
        creditPage.errorNotification();
    }

    @Test
    @DisplayName("Card Number '0000 0000 0000 0000'")
    void shouldTestZeroNumberCard() {
        creditPage.fillingOutForm(DataHelper.getZeroNumber());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty month field")
    void shouldTestEmptyMonthField() {
        creditPage.fillingOutForm(DataHelper.getEmptyMonthField());
        creditPage.requiredField();
    }

    @Test
    @DisplayName("Earlier month in month field")
    void shouldTestEarlierMonth() {
        creditPage.fillingOutForm(DataHelper.getEarlierMonth());
        creditPage.incorrectDeadline();
    }

    @Test
    @DisplayName("Next month in month field")
    void shouldTestNextMonth() {
        creditPage.fillingOutForm(DataHelper.getNextMonth());
        creditPage.successNotification();
    }

    @Test
    @DisplayName("'00' in month field")
    void shouldTestZeroMonth() {
        creditPage.fillingOutForm(DataHelper.get00Month());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("One Number in month field")
    void shouldTestOneNumInMonth() {
        creditPage.fillingOutForm(DataHelper.getOneNumMonth());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("'13' in month field")
    void shouldTest13Month() {
        creditPage.fillingOutForm(DataHelper.get13Month());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty year field")
    void shouldTestEmptyYearField() {
        creditPage.fillingOutForm(DataHelper.getEmptyYearField());
        creditPage.requiredField();
    }

    @Test
    @DisplayName("One simbol in year field")
    void shouldTestOneSimbolInYearField() {
        creditPage.fillingOutForm(DataHelper.getOneSimbolInYearField());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Earlier year in year field")
    void shouldTestEarlierYear() {
        creditPage.fillingOutForm(DataHelper.getEarlierYear());
        creditPage.deadlineIsOver();
    }

    @Test
    @DisplayName("Last valid year in year field")
    void shouldTestLastValidYear() {
        creditPage.fillingOutForm(DataHelper.getLstYear());
        creditPage.successNotification();
    }

    @Test
    @DisplayName("Over 6 year in year field")
    void shouldTestOverYear() {
        creditPage.fillingOutForm(DataHelper.getOverYear());
        creditPage.incorrectDeadline();
    }

    @Test
    @DisplayName("Empty holder field")
    void shouldTestEmptyHolderField() {
        creditPage.fillingOutForm(DataHelper.getEmptyHolderField());
        creditPage.requiredField();
    }

    @Test
    @DisplayName("One simbol in holder field")
    void shouldTestOneLetterInHolderField() {
        creditPage.fillingOutForm(DataHelper.getOneLetter());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Rus name in holder field")
    void shouldTestRusHolderName() {
        creditPage.fillingOutForm(DataHelper.getRusHolder());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Numbers in holder field")
    void shouldTestNumberInHolderField() {
        creditPage.fillingOutForm(DataHelper.getNumberHolder());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty CVC/CVV field")
    void shouldTestEmptyCVVField() {
        creditPage.fillingOutForm(DataHelper.getEmptyCVVField());
        creditPage.setRequiredFieldForCVVField();
    }

    @Test
    @DisplayName("'000' in CVC/CVV field")
    void shouldTestZeroInCVVField() {
        creditPage.fillingOutForm(DataHelper.getZeroCVV());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("One simbol in CVC/CVV field")
    void shouldTestOneSimbolCVV() {
        creditPage.fillingOutForm(DataHelper.getOneSimbolCVV());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Letters in CVC/CVV field")
    void shouldTestLettersInCVVField() {
        creditPage.fillingOutForm(DataHelper.getLettersCVV());
        creditPage.invalidFormat();
    }

    @Test
    @DisplayName("Empty form")
    void shouldTestEmptyForm() {
        creditPage.fillingOutForm(DataHelper.getEmptyForm());
        creditPage.setRequiredFieldForNumberCard();
        creditPage.setRequiredFieldForMonthField();
        creditPage.setRequiredFieldForYearField();
        creditPage.setRequiredFieldForHolderField();
        creditPage.setRequiredFieldForCVVField();
    }
}