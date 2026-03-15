package tests;

import models.login.LoginBodyModel;
import models.logout.EmptyLogoutResponseModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;

public class LogoutTests extends TestBase {

    String username = "qaguru";
    String password = "qaguru123";

    @Test
    @DisplayName("Успешная отправка токена и проверка ответа")
    public void successfulLogoutTest(){
        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String refreshToken = step ("Авторизация пользователя" , () ->
        given(RequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh"));

        step ("Отправка токена и проверка ответа" , () -> {
            String logoutData = format("{\"refresh\": \"%s\"}", refreshToken);
        given(RequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
        assertThat(loginData.username()).isEqualTo(username);
        assertThat(loginData.password()).isEqualTo(password);
        });
    }

    @Test
    @DisplayName("Отправка токена под неавторизованным пользователем")
    public void emptyLogoutResponseTest(){
        step ("Отправка токена под неавторизованным пользователем" , () -> {
        String refresh = "";
        LogoutBodyModel logoutData = new LogoutBodyModel(refresh);

        EmptyLogoutResponseModel logoutResponse = given(RequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyLogoutResponseSpec)
                .extract().as(EmptyLogoutResponseModel.class);

        String expectedRefresh = "This field may not be blank.";
        String actualRefresh = logoutResponse.refresh().get(0);
        assertThat(actualRefresh).isEqualTo(expectedRefresh);
        });
    }
}