package tests;

import models.clubs.*;
import models.login.LoginBodyModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.password;

public class ClubsTests extends TestBase {

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
    }

//    @Test
//    public void getClubsReturns200AndValidStructure() {
//        ClubsListResponseModel response = api.clubs.getClubs();
//
//        assertThat(response).isNotNull();
//        assertThat(response.count()).isGreaterThanOrEqualTo(0);
//        assertThat(response.results()).isNotNull();
//        assertThat(response.results()).hasSize(response.count());
//    }
//
//    @Test
//    public void getClubsCountMatchesResultsSize() {
//        ClubsListResponseModel response = api.clubs.getClubs();
//
//        assertThat(response.results()).as("count должно совпадать с размером results").hasSize(response.count());
//    }
//
//    @Test
//    public void getClubsEachClubHasRequiredFields() {
//        ClubsListResponseModel response = api.clubs.getClubs();
//
//        for (ClubModel club : response.results()) {
//            assertThat(club.id()).isNotNull().isPositive();
//            assertThat(club.bookTitle()).isNotNull();
//            assertThat(club.bookAuthors()).isNotNull();
//            assertThat(club.publicationYear()).isNotNull();
//            assertThat(club.description()).isNotNull();
//            assertThat(club.telegramChatLink()).isNotNull();
//            assertThat(club.owner()).isNotNull().isPositive();
//            assertThat(club.members()).isNotNull();
//            assertThat(club.reviews()).isNotNull();
//            assertThat(club.created()).isNotNull();
//        }
//    }
//
//    @Test
//    public void getClubsPaginationFieldsPresent() {
//        ClubsListResponseModel response = api.clubs.getClubs();
//
//        assertThat(response.count()).isNotNull();
//
//        assertThat(response.results()).isNotNull();
//    }

    @Test
    @DisplayName("Успешная регистрация клуба")
    public void successfulClubCreationTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModel createClubBodyModel = api.clubs.clubCreate(createClub, accessToken);

        step("Проверка значений созданного клуба", () -> {
            assertThat(createClubBodyModel.id()).isNotNull();
            assertThat(createClubBodyModel.bookTitle()).isEqualTo(createClub.bookTitle());
            assertThat(createClubBodyModel.bookAuthors()).isEqualTo(createClub.bookAuthors());
            assertThat(createClubBodyModel.publicationYear()).isEqualTo(createClub.publicationYear());
            assertThat(createClubBodyModel.description()).isEqualTo(createClub.description());
            assertThat(createClubBodyModel.telegramChatLink()).isEqualTo(createClub.telegramChatLink());
            assertThat(createClubBodyModel.owner()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.members().get(0).intValue()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.reviews().isEmpty());
            assertThat(createClubBodyModel.created()).isNotNull();
            assertThat(createClubBodyModel.modified()).isNull();
        });

    }

    @Test
    @DisplayName("Получение информации о созданном клубе по ID")
    public void successfulClubGetTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModel createClubBodyModel = api.clubs.clubCreate(createClub, accessToken);

        step("Проверка значений созданного клуба", () -> {
            assertThat(createClubBodyModel.id()).isNotNull();
            assertThat(createClubBodyModel.bookTitle()).isEqualTo(createClub.bookTitle());
            assertThat(createClubBodyModel.bookAuthors()).isEqualTo(createClub.bookAuthors());
            assertThat(createClubBodyModel.publicationYear()).isEqualTo(createClub.publicationYear());
            assertThat(createClubBodyModel.description()).isEqualTo(createClub.description());
            assertThat(createClubBodyModel.telegramChatLink()).isEqualTo(createClub.telegramChatLink());
            assertThat(createClubBodyModel.owner()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.members().get(0).intValue()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.reviews().isEmpty());
            assertThat(createClubBodyModel.created()).isNotNull();
            assertThat(createClubBodyModel.modified()).isNull();
        });
        GetClubModel getClubResponse =
                api.clubs.clubGet(Integer.parseInt(createClubBodyModel.id()), accessToken);

        step("Проверка значений полученного клуба", () -> {
            assertThat(getClubResponse.id()).isEqualTo(createClubBodyModel.id());
            assertThat(getClubResponse.bookTitle()).isEqualTo(createClubBodyModel.bookTitle());
            assertThat(getClubResponse.bookAuthors()).isEqualTo(createClubBodyModel.bookAuthors());
            assertThat(getClubResponse.publicationYear()).isEqualTo(createClubBodyModel.publicationYear());
            assertThat(getClubResponse.description()).isEqualTo(createClubBodyModel.description());
            assertThat(getClubResponse.telegramChatLink()).isEqualTo(createClubBodyModel.telegramChatLink());
            assertThat(getClubResponse.owner()).isEqualTo(createClubBodyModel.owner());
            assertThat(getClubResponse.members().get(0).intValue()).isEqualTo(createClubBodyModel.members().get(0).intValue());
            assertThat(getClubResponse.reviews().isEmpty());
            assertThat(getClubResponse.created()).isEqualTo(createClubBodyModel.created());
            assertThat(getClubResponse.modified()).isNull();
        });
    }

    @Test
    @DisplayName("Внесение изменений в клуб")
    public void successfulClubPutTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModel createClubBodyModel = api.clubs.clubCreate(createClub, accessToken);

        step("Проверка значений созданного клуба", () -> {
            assertThat(createClubBodyModel.id()).isNotNull();
            assertThat(createClubBodyModel.bookTitle()).isEqualTo(createClub.bookTitle());
            assertThat(createClubBodyModel.bookAuthors()).isEqualTo(createClub.bookAuthors());
            assertThat(createClubBodyModel.publicationYear()).isEqualTo(createClub.publicationYear());
            assertThat(createClubBodyModel.description()).isEqualTo(createClub.description());
            assertThat(createClubBodyModel.telegramChatLink()).isEqualTo(createClub.telegramChatLink());
            assertThat(createClubBodyModel.owner()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.members().get(0).intValue()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.reviews().isEmpty());
            assertThat(createClubBodyModel.created()).isNotNull();
            assertThat(createClubBodyModel.modified()).isNull();
        });
        UpdateClubPutRequestBodyModel updateClub = new UpdateClubPutRequestBodyModel(updatedBookTitle, updatedBookAuthors,
                updatedPublicationYear, updatedDescription, updatedTelegram_link);
        UpdateClubPutResponseBodyModel updateClubBodyModel =
                api.clubs.clubPutUpdate(Integer.parseInt(createClubBodyModel.id()), updateClub, accessToken);

        step("Проверка значений полученного клуба", () -> {
            assertThat(updateClubBodyModel.id()).isEqualTo(createClubBodyModel.id());
            assertThat(updateClubBodyModel.bookTitle()).isEqualTo(updateClub.bookTitle());
            assertThat(updateClubBodyModel.bookAuthors()).isEqualTo(updateClub.bookAuthors());
            assertThat(updateClubBodyModel.publicationYear()).isEqualTo(updateClub.publicationYear());
            assertThat(updateClubBodyModel.description()).isEqualTo(updateClub.description());
            assertThat(updateClubBodyModel.telegramChatLink()).isEqualTo(updateClub.telegramChatLink());
            assertThat(updateClubBodyModel.owner()).isEqualTo(createClubBodyModel.owner());
            assertThat(updateClubBodyModel.members().get(0).intValue()).isEqualTo(createClubBodyModel.members().get(0).intValue());
            assertThat(updateClubBodyModel.reviews().isEmpty());
            assertThat(updateClubBodyModel.created()).isEqualTo(createClubBodyModel.created());
            assertThat(updateClubBodyModel.modified()).isNotNull();
        });

    }

    @Test
    @DisplayName("Проверка создания клуба и последующее его удаление")
    public void successfulClubDeleteTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        CreateClubRequestModel createClub = new CreateClubRequestModel(bookTitle, bookAuthors, publicationYear, description, telegram_link);

        CreateClubResponseModel createClubBodyModel = api.clubs.clubCreate(createClub, accessToken);

        step("Проверка значений созданного клуба", () -> {
            assertThat(createClubBodyModel.id()).isNotNull();
            assertThat(createClubBodyModel.bookTitle()).isEqualTo(createClub.bookTitle());
            assertThat(createClubBodyModel.bookAuthors()).isEqualTo(createClub.bookAuthors());
            assertThat(createClubBodyModel.publicationYear()).isEqualTo(createClub.publicationYear());
            assertThat(createClubBodyModel.description()).isEqualTo(createClub.description());
            assertThat(createClubBodyModel.telegramChatLink()).isEqualTo(createClub.telegramChatLink());
            assertThat(createClubBodyModel.owner()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.members().get(0).intValue()).isEqualTo(registrationResponse.id());
            assertThat(createClubBodyModel.reviews().isEmpty());
            assertThat(createClubBodyModel.created()).isNotNull();
            assertThat(createClubBodyModel.modified()).isNull();
        });
        api.clubs.clubDelete(Integer.parseInt(createClubBodyModel.id()), accessToken);
        GetNotExistingClubResponseBodyModel getLostClub =
                api.clubs.getNotExistingClub(Integer.parseInt(createClubBodyModel.id()), accessToken);

        step("Проверка корректной ошибки", () -> {
            assertThat(getLostClub.detail()).isEqualTo(remote_club_error);

        });
    }
}