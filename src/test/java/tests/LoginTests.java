package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.login.WrongCredentialsLoginResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class LoginTests extends TestBase {

    @Test
    @Disabled
    @DisplayName("Успешная авторизация пользователя")
    public void successfulLoginTest() {

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        SuccessfulLoginResponseModel loginResponse = api.auth.login(loginData);

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
    @Disabled
    @DisplayName("Попытка авторизации с неверным паролем")
    public void wrongCredentialsPasswordTest() {

        LoginBodyModel loginData = new LoginBodyModel(username, wrongPassword);
        WrongCredentialsLoginResponseModel loginResponse = api.auth.wronglogin(loginData);

        step("Проверка ошибки на некорректный логин или пароль", () -> {
            String expectedDetailError = "Invalid username or password.";
            String actualDetailError = loginResponse.detail();
            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        });
    }

    @Test
    @Disabled
    @DisplayName("Попытка авторизации под несуществующим пользователем")
    public void wrongCredentialsLoginTest() {

            LoginBodyModel loginData = new LoginBodyModel(wrongUsername, password);
            WrongCredentialsLoginResponseModel loginResponse = api.auth.wronglogin(loginData);

        step("Проверка ошибки на некорректный логин или пароль", () -> {
            String expectedDetailError = "Invalid username or password.";
            String actualDetailError = loginResponse.detail();
            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        });
    }
}