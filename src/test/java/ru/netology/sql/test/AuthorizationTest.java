package ru.netology.sql.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.UserData;
import ru.netology.sql.helper.DataHelper;
import ru.netology.sql.helper.SQLHelper;
import ru.netology.sql.page.DashboardPage;
import ru.netology.sql.page.LoginPage;
import ru.netology.sql.page.VerifyPage;

import java.sql.SQLData;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest {

    UserData user;
    LoginPage loginPage;

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
        user = DataHelper.getUser();
        loginPage = new LoginPage();
    }

    @AfterEach
    public void tearnDown() {
        SQLHelper.resetUserStatus(user.getLogin());
        SQLHelper.resetVerifyCode();
    }

    @Test
    @DisplayName("Тест happy path")
    public void  test1() {
        loginPage.input(user.getLogin(), user.getPassword());
        VerifyPage verifyPage = loginPage.success();
        verifyPage.input(DataHelper.getValidVerifiCode(user.getLogin()));
        DashboardPage dashboardPage = verifyPage.success();
    }

    @Test
    @DisplayName("Тест с пустым полем пароль")
    public void test2() {
        loginPage.input(user.getLogin(), null);
        loginPage.emptyPassword();
    }

    @Test
    @DisplayName("Пользователя должно заблокировать после трехкратного невалидного ввода пароля")
    public void test3() {
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();

        assertEquals("blocked", SQLHelper.getUserStatus(user.getLogin()));
    }
}
