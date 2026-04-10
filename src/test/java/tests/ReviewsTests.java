package tests;

import models.clubs.CreateClubRequestModel;
import models.clubs.CreateClubResponseModel;
import models.clubs.CreateClubResponseModelFromReview;
import models.login.LoginBodyModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.review.CreateReviewRequestModel;
import models.review.GetReviewResponseModel;
import models.review.SuccessfulReviewResponseModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.password;

public class ReviewsTests extends TestBase {

    String remote_club_error = "No Club matches the given query.";
    String username;
    String bookTitle;
    String updatedBookTitle;
    String bookAuthors;
    String updatedBookAuthors;
    int publicationYear;
    int updatedPublicationYear;
    String description;
    String updatedDescription;
    String telegram_link;
    String updatedTelegram_link;
    String review;
    int assessment;
    int readPages;


    @BeforeEach
    public void prepareTestData() {
        Faker faker = new Faker();
        username = faker.name().firstName() + "_new";
        telegram_link = faker.internet().url();
        updatedTelegram_link = faker.internet().url();
        bookTitle = faker.book().title() + " " + faker.naruto().character() + " " + faker.battlefield1().weapon();
        updatedBookTitle = faker.book().title() + " " + faker.naruto().eye() + " " + faker.battlefield1().map();
        bookAuthors = faker.book().author();
        updatedBookAuthors = faker.book().author();
        publicationYear = faker.number().numberBetween(1, 2026);
        updatedPublicationYear = faker.number().numberBetween(1, 2026);
        description = faker.book().genre() + " " + faker.book().publisher();
        updatedDescription = faker.book().genre() + " " + faker.book().publisher();
        review = "Отличная книга! Жаль, что я не умею читать";
        assessment = faker.number().numberBetween(1, 5);
        readPages = faker.number().numberBetween(1, 400);
    }

    @Test
    @DisplayName("Успешная регистрация клуба и добавление ревью")
    public void successfulClubCreationAndReviewTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModelFromReview createClubBodyModel = api.clubs.clubCreate2(createClub, accessToken);

        CreateReviewRequestModel createReview = new CreateReviewRequestModel(createClubBodyModel.id(), review,
                assessment, readPages);
        SuccessfulReviewResponseModel newReview = api.review.createReviewBody(createReview, accessToken);

        step("Проверка значений созданного обзора", () -> {

            assertThat(newReview.review()).isEqualTo(createReview.review());
            assertThat(newReview.assessment()).isEqualTo(createReview.assessment());
            assertThat(newReview.readPages()).isEqualTo(createReview.readPages());

        });
    }


    @Test
    @DisplayName("Получение добавленного ревью")
    public void successfulGetReviewTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModelFromReview createClubBodyModel = api.clubs.clubCreate2(createClub, accessToken);

        CreateReviewRequestModel createReview = new CreateReviewRequestModel(createClubBodyModel.id(), review,
                assessment, readPages);
        SuccessfulReviewResponseModel newReview = api.review.createReviewBody(createReview, accessToken);

        GetReviewResponseModel getReview = api.review.getReviewBody(newReview.id(), accessToken);

        step("Проверка значений созданного обзора", () -> {

            assertThat(getReview.review()).isEqualTo(createReview.review());
            assertThat(getReview.assessment()).isEqualTo(createReview.assessment());
            assertThat(getReview.readPages()).isEqualTo(createReview.readPages());

        });

    }

    @Test
    @DisplayName("Успешное удаление ревью")
    public void successfulDeleteReviewTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModelFromReview createClubBodyModel = api.clubs.clubCreate2(createClub, accessToken);

        CreateReviewRequestModel createReview = new CreateReviewRequestModel(createClubBodyModel.id(), review,
                assessment, readPages);
        SuccessfulReviewResponseModel newReview = api.review.createReviewBody(createReview, accessToken);

        api.review.deleteReviewBody(newReview.id(), accessToken);

    }
}
