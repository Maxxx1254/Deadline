package ru.netology.sql.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class VerifyPage {
    private final SelenideElement codeInput = $x("//span[@data-test-id='code']//input");
    private final SelenideElement codeInputEmptyNotification = $x("//span[@data-test-id='code']//span[@class='input__sub']");
    private final SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");
    private final SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private final SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public VerifyPage() {
        codeInput.should(visible);
        verifyButton.should(visible);
        errorButton.should(hidden);
        errorNotification.should(hidden);
    }

    public void input(String code) {
        codeInput.val(code);
        verifyButton.click();
    }

    public void success() {
        errorNotification.should(hidden);
        errorButton.should(hidden);
        new DashboardPage();
    }
    public void failed() {
        errorNotification.should(visible);
        errorNotification.$x("Ошибка! Неверно указан код! Попробуйте еще раз.");
        errorButton.click();
        errorNotification.should(hidden);
    }

    public void emptyCode() {
        codeInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}
