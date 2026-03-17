package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.login.WrongCredentialsLoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.RequestSpec;
import static specs.login.LoginSpec.*;

public class LoginTests extends TestBase {

    String username = "qaguru";
    String password = "qaguru123";
    String wrongPassword = "qaguru1234";
    String wrongUsername = "qaguru1234";

    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void successfulLoginTest() {

        SuccessfulLoginResponseModel loginResponse = step("Авторизовываемся под существующим пользователем", () -> {
            LoginBodyModel loginData = new LoginBodyModel(username, password);
            return given(RequestSpec)
                    .body(loginData)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(successfulLoginResponseSpec)
                    .extract().as(SuccessfulLoginResponseModel.class);
        });
        step("Проверка ответа", () -> {
            String expectedTokenPath = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
            String actualAccess = loginResponse.access();
            String actualRefresh = loginResponse.refresh();

            assertThat(actualAccess).startsWith(expectedTokenPath);
            assertThat(actualRefresh).startsWith(expectedTokenPath);
            assertThat(actualAccess).isNotEqualTo(actualRefresh);
        });
    }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем")
    public void wrongCredentialsPasswordTest() {

        WrongCredentialsLoginResponseModel loginResponse = step("Авторизовываемся под существующим пользователем с неверным паролем", () -> {
            LoginBodyModel loginData = new LoginBodyModel(username, wrongPassword);
            return given(RequestSpec)
                    .body(loginData)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(wrongCredentialsLoginResponseSpec)
                    .extract().as(WrongCredentialsLoginResponseModel.class);
        });
        step("Проверка ошибки на некорректный логин или пароль", () -> {
            String expectedDetailError = "Invalid username or password.";
            String actualDetailError = loginResponse.detail();

            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        });
    }

    @Test
    @DisplayName("Попытка авторизации под несуществующим пользователем")
    public void wrongCredentialsLoginTest() {


        WrongCredentialsLoginResponseModel loginResponse = step("Авторизовываемся под несуществующим пользователем", () -> {
            LoginBodyModel loginData = new LoginBodyModel(wrongUsername, password);
            return given(RequestSpec)
                    .body(loginData)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(wrongCredentialsLoginResponseSpec)
                    .extract().as(WrongCredentialsLoginResponseModel.class);
        });
        step("Проверка ошибки на некорректный логин или пароль", () -> {
            String expectedDetailError = "Invalid username or password.";
            String actualDetailError = loginResponse.detail();

            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        });
    }
}