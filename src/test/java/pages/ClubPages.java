package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.open;

public class ClubPages {

//    private SelenideElement

    @Step("Открываем сайт")
    public ClubPages openPage(String value) {
        open("/favicon.ico");
        localStorage().setItem("book_club_auth", value);
        return this;
    }

    @Step("Открываем созданный клуб")
    public ClubPages openClubPage(int value) {
        open("/clubs/" + value);
        return this;
    }
}
