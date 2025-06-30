package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));


    /*заполнение формы данными подтвержденной карты и валидными данными*/
    public static CardInfo getNumberApprovedCard() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*заполнение формы данными отмененной карты и валидными данными*/
    public static CardInfo getNumberDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*пустое поле "Номер карты"*/
    public static CardInfo getEmptyCardField() {
        return new CardInfo("", getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*менее 16 символов в поле "Номер карты"*/
    public static CardInfo getNumberless16digits() {
        return new CardInfo("4444 4444 4444", getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*случайный номер карты в поле*/
    public static CardInfo getRandomNumber() {
        return new CardInfo(fakerEn.business().creditCardNumber(), getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*"0000 0000 0000 0000" в поле ""Номер карты"*/
    public static CardInfo getZeroNumber() {
        return new CardInfo("0000 0000 0000 0000", getMonth(0), getYear(0), getValidHolder(), getValidCVV());
    }

    /*Пустое поле "Месяц"*/
    public static CardInfo getEmptyMonthField() {
        return new CardInfo("4444 4444 4444 4441", "", getYear(0), getValidHolder(), getValidCVV());
    }

    /*Месяц ранее текущего*/
    public static CardInfo getEarlierMonth() {
        return new CardInfo("4444 4444 4444 4441", getMonth(-1), getYear(0), getValidHolder(), getValidCVV());
    }

    /*Следующий валидный месяц*/
    public static CardInfo getNextMonth() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(0), getValidHolder(), getValidCVV());
    }

    /*Невалидный месяц "00"*/
    public static CardInfo get00Month() {
        return new CardInfo("4444 4444 4444 4441", "00", getYear(0), getValidHolder(), getValidCVV());
    }

    /*Невалидный месяц "13"*/
    public static CardInfo get13Month() {
        return new CardInfo("4444 4444 4444 4441", "13", getYear(0), getValidHolder(), getValidCVV());
    }

    /*Одна цифра в поле "Месяц"*/
    public static CardInfo getOneNumMonth() {
        return new CardInfo("4444 4444 4444 4441", "1", getYear(0), getValidHolder(), getValidCVV());
    }

    /*Пустое поле "Год"*/
    public static CardInfo getEmptyYearField() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), "", getValidHolder(), getValidCVV());
    }

    /*Один символ в поле "Год"*/
    public static CardInfo getOneSimbolInYearField() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), "2", getValidHolder(), getValidCVV());
    }

    /*Год ранее текущего*/
    public static CardInfo getEarlierYear() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(-1), getValidHolder(), getValidCVV());
    }

    /*Год, превышающий срок карты (+6)*/
    public static CardInfo getOverYear() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(6), getValidHolder(), getValidCVV());
    }

    /*Год окончания карты (+5)*/
    public static CardInfo getLstYear() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(5), getValidHolder(), getValidCVV());
    }

    /*Пустое поле "Владелец"*/
    public static CardInfo getEmptyHolderField() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), "", getValidCVV());
    }

    /*Один символ в поле "Владелец"*/
    public static CardInfo getOneLetter() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), "U", getValidCVV());
    }

    /*Кириллица в поле "Владелец"*/
    public static CardInfo getRusHolder() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getRusName(), getValidCVV());
    }

    /*Цифры и символы в поле "Владелец"*/
    public static CardInfo getNumberHolder() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), "1+5", getValidCVV());
    }

    /*Пустое поле ""CVC/CVV*/
    public static CardInfo getEmptyCVVField() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getValidHolder(), "");
    }

    /*"000" в поле "CVC/CVV"*/
    public static CardInfo getZeroCVV() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getValidHolder(), "000");
    }

    /*Один символ в поле "CVC/CVV"*/
    public static CardInfo getOneSimbolCVV() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getValidHolder(), "1");
    }

    /*Буквы и спецсимволы в поле "CVC/CVV"*/
    public static CardInfo getLettersCVV() {
        return new CardInfo("4444 4444 4444 4441", getMonth(0), getYear(0), getValidHolder(), "п=х");
    }

    /*пустая форма*/
    public static CardInfo getEmptyForm() {
        return new CardInfo("", "", "", "", "");
    }


    public static String getMonth(int shiftMonthFromToday) {
        String month = LocalDate.now().plusMonths(shiftMonthFromToday).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public static String getYear(int shiftYearFromToday) {
        String year = LocalDate.now().plusYears(shiftYearFromToday).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public static String getValidHolder() {
        String validHolder = fakerEn.name().firstName();
        return validHolder;
    }

    public static String getRusName() {
        String RusName = fakerRu.name().firstName();
        return RusName;
    }

    public static String getValidCVV() {
        String validCVV = fakerEn.number().digits(3);
        return validCVV;
    }

    @Value
    public static class CardInfo {
        private String numberCard;
        private String month;
        private String year;
        private String validHolder;
        private String validCVV;
    }
}