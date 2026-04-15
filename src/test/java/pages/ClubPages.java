package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ClubPages {

    private final SelenideElement review = $(".review-card.user-review");
    private final SelenideElement addReviewButton = $(".add-review-btn");
    private final SelenideElement reviewInput = $("#review");
    private final SelenideElement saveButton = $(".save-btn");
    private final SelenideElement deleteReviewButton = $(".delete-review-btn");

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

    @Step("Нажимаем кнопку Написать отзыв")
    public ClubPages addReview() { //метод для подтверждения
        addReviewButton.click();

        return this;
    }

    @Step("Вводим текст ревью")
    public ClubPages setReviewInput(String value) { //метод для имени
        reviewInput.setValue(value);

        return this;
    }

    @Step("Нажимаем кнопку Опубликовать")
    public ClubPages saveButton() { //метод для подтверждения
        saveButton.click();

        return this;
    }

    @Step("Нажимаем кнопку 'Удалить'")
    public ClubPages deleteReview() { //метод для подтверждения
        deleteReviewButton.click();

        return this;
    }

    public SelenideElement getReview() {
        return review;


    }
}
