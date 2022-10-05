package ru.netology.sql.helper;

import com.github.javafaker.Faker;
import ru.netology.sql.data.UserData;

public class DataHelper {
    private static Faker faker = new Faker();

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomPassword() {
        String randomPassword = faker.internet().password();
        return randomPassword;
    }

    public static String getValidVerifiCode(String login) {
        String verfyCode = SQLHelper.getValidVerifyCode(login);
        return verfyCode;
    }

    public static String getValidVerifyCode() {
        int invalidVerifyCode = faker.number().numberBetween(100_000, 999_999);
        return String.valueOf(invalidVerifyCode);
    }
}
