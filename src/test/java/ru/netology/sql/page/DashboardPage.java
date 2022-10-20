package ru.netology.sql.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {

    public DashboardPage() {
        SelenideElement dashboardHeader = $x("//h2[@data-test-id='dashboard']");
        dashboardHeader.should(visible);
    }
}
