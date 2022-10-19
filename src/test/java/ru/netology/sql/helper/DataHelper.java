package ru.netology.sql.helper;

import com.github.javafaker.Faker;
import lombok.Value;
import ru.netology.sql.data.UserData;

public class DataHelper {
    private static final Faker faker = new Faker();

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }
    @Value
    public static class VerifyCode {
        private final String verifyCode;
    }

    public static VerifyCode getValidCode(String login) {
        return new VerifyCode(SQLHelper.getVerifyCodeByLogin(login, "1"));
    }


    public static String getInvalidVerifyCode() {
        int invalidVerifyCode = faker.number().numberBetween(100_000, 999_999);
        return String.valueOf(invalidVerifyCode);
    }
}
