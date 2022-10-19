package ru.netology.sql.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private SelenideElement loginInput = $x("//span[@data-test-id='login']//input");
    private SelenideElement loginInputEmptyNotification = $x("//span[@data-test-id='login']//span[@class='input__sub']");
    private SelenideElement passwordInput = $x("//span[@data-test-id='password']//input");
    private SelenideElement passwordInputEmptyNotification = $x("//span[@data-test-id='password']//span[@class='input__sub']");
    private SelenideElement loginButton = $x("//button[@data-test-id='action-login']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");
    private SelenideElement blockedNotification = $x("//div[@data-test-id='blocked-notification']/button");


    public LoginPage() {
        loginInput.should(visible);
        passwordInput.should(visible);
        loginButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void input(String login, String password) {
        loginInput.val(login);
        passwordInput.val(password);
        loginButton.click();
    }

    public VerifyPage success() {
        errorNotification.should(hidden);
        errorButton.should(hidden);
        return new VerifyPage();
    }

    public void failed() {
        errorNotification.should(visible);
        errorNotification.$x(".//div[@class='notification__content']").should(text("Ошибка! Неверно указан логин или пароль"));
        errorButton.click();
        errorNotification.should(hidden);
    }

    public void clean()  {
        loginInput.click();
        loginInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        passwordInput.click();
        passwordInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public SelenideElement blocked() {
        return blockedNotification.should(visible);
    }
    public void emptyLogin() {
        loginInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }

    public void emptyPassword() {
        passwordInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}
