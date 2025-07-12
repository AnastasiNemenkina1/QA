package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker fakerEn = new Faker(new Locale("en"));
    private static final Faker fakerRu = new Faker(new Locale("ru"));
    private static final String APPROVED_CARD = "4444 4444 4444 4441";
    private static final String DECLINED_CARD = "4444 4444 4444 4442";

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvv;
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(DECLINED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static String getShiftedMonth(int months) {
        return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getShiftedYear(int years) {
        return LocalDate.now().plusYears(years).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateHolder() {
        return fakerEn.name().fullName().toUpperCase();
    }

    public static String generateRussianHolder() {
        return fakerRu.name().fullName().toUpperCase();
    }

    public static String generateCvv() {
        return fakerEn.number().digits(3);
    }

    public static String getCurrentMonth() {
        return getShiftedMonth(0);
    }

    public static String getCurrentYear() {
        return getShiftedYear(0);
    }

    public static CardInfo getEmptyCard() {
        return new CardInfo("", getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getInvalidShortCardNumber() {
        return new CardInfo("4444 4444 4444", getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getRandomCardNumber() {
        return new CardInfo(fakerEn.business().creditCardNumber(), getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getZeroCardNumber() {
        return new CardInfo("0000 0000 0000 0000", getCurrentMonth(), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getEmptyMonth() {
        return new CardInfo(APPROVED_CARD, "", getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getExpiredMonth() {
        return new CardInfo(APPROVED_CARD, getShiftedMonth(-1), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getNextMonth() {
        return new CardInfo(APPROVED_CARD, getShiftedMonth(1), getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getInvalidZeroMonth() {
        return new CardInfo(APPROVED_CARD, "00", getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getInvalidMonth13() {
        return new CardInfo(APPROVED_CARD, "13", getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getInvalidSingleDigitMonth() {
        return new CardInfo(APPROVED_CARD, "1", getCurrentYear(), generateHolder(), generateCvv());
    }

    public static CardInfo getEmptyYear() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), "", generateHolder(), generateCvv());
    }

    public static CardInfo getInvalidSingleDigitYear() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), "2", generateHolder(), generateCvv());
    }

    public static CardInfo getExpiredYear() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getShiftedYear(-1), generateHolder(), generateCvv());
    }

    public static CardInfo getFutureYear() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getShiftedYear(5), generateHolder(), generateCvv());
    }

    public static CardInfo getTooDistantFutureYear() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getShiftedYear(6), generateHolder(), generateCvv());
    }

    public static CardInfo getEmptyHolder() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), "", generateCvv());
    }

    public static CardInfo getInvalidSingleLetterHolder() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), "A", generateCvv());
    }

    public static CardInfo getRussianHolder() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateRussianHolder(), generateCvv());
    }

    public static CardInfo getInvalidNumericHolder() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), "12345", generateCvv());
    }

    public static CardInfo getEmptyCvv() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), "");
    }

    public static CardInfo getInvalidZeroCvv() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), "000");
    }

    public static CardInfo getInvalidSingleDigitCvv() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), "1");
    }

    public static CardInfo getInvalidSymbolsCvv() {
        return new CardInfo(APPROVED_CARD, getCurrentMonth(), getCurrentYear(), generateHolder(), "A!@");
    }

    public static CardInfo getEmptyForm() {
        return new CardInfo("", "", "", "", "");
    }
}