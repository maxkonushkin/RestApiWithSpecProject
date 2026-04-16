package tests;

import models.login.LoginBodyModel;
import models.logout.EmptyLogoutResponseModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.password;
import static tests.TestData.username;

public class LogoutTests extends TestBase {

    @Test
    @DisplayName("Успешная отправка токена и проверка ответа")
    public void successfulLogoutTest() {

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String refreshToken = api.auth.loginWithRefreshToken(loginData);

        LogoutBodyModel logoutData = new LogoutBodyModel(refreshToken);
        api.auth.logout(logoutData);

        step("Проверка, что данные для логина не изменились", () -> {
            assertThat(loginData.username()).isEqualTo(username);
            assertThat(loginData.password()).isEqualTo(password);
        });
    }

    @Test
    @DisplayName("Отправка пустого токена")
    public void emptyLogoutResponseTest() {
        String refresh = "";

        EmptyLogoutResponseModel logoutResponse
                = api.auth.emptylogout(new LogoutBodyModel(refresh));

        step("Проверка текста ошибки", () -> {
            String expectedRefresh = "This field may not be blank.";
            String actualRefresh = logoutResponse.refresh().get(0);

            assertThat(actualRefresh).isEqualTo(expectedRefresh);
        });
    }
}