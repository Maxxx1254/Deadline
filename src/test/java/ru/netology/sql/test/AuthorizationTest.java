package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.UserData;
import ru.netology.sql.helper.DataHelper;
import ru.netology.sql.helper.SQLHelper;
import ru.netology.sql.page.LoginPage;

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
    public void tearDown() {
        SQLHelper.resetUserStatus(user.getLogin());
        SQLHelper.resetVerifyCode();
    }

    @AfterAll
    public void cleanTable() {
        SQLHelper.cleanTable();
    }

    @Test
    public void shouldAuth() {
        loginPage.input(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        var code = DataHelper.getValidCode(user.getLogin());
        verifyPage.input(code.getVerifyCode());
        verifyPage.success();
    }

    @Test
    public void ifEmptyPassword() {
        loginPage.input(user.getLogin(), null);
        loginPage.emptyPassword();
    }

    @Test
    public void ifEmptyLogin() {
        loginPage.input(null, user.getPassword());
        loginPage.emptyLogin();
    }

    @Test
    public void ifUnregisteredLogin() {
        loginPage.input(DataHelper.getRandomLogin(), user.getPassword());
        loginPage.failed();
    }

    @Test
    public void ifInvalidPassword() {
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
    }

    @Test
    public void ifInvalidCode() {
        loginPage.input(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        verifyPage.input(DataHelper.getInvalidVerifyCode());
        verifyPage.failed();
    }

    @Test
    public void ifEmptyCode() {
        loginPage.input(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();;
        verifyPage.input(null);
        verifyPage.emptyCode();
    }

    @Test
    public void shouldBlocked() {
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.clean();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.clean();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        assertEquals("Пользователь заблокирован.", loginPage.blocked());
    }
}
