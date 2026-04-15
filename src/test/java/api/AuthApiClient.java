package api;

import io.qameta.allure.Step;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.login.WrongCredentialsLoginResponseModel;
import models.logout.EmptyLogoutResponseModel;
import models.logout.LogoutBodyModel;
import models.update.SuccessfulUpdateUserModel;
import models.update.UpdateBodyModel;
import static io.restassured.RestAssured.given;
import static specs.RequestSpec.RequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;
import static specs.login.LoginSpec.wrongCredentialsLoginResponseSpec;
import static specs.logout.LogoutSpec.emptyLogoutResponseSpec;
import static specs.logout.LogoutSpec.successfulLogoutResponseSpec;
import static specs.update.UpdateSpec.successfulUpdateResponseSpec;

public class AuthApiClient {

    @Step("Ввод логина и пароля")
    public SuccessfulLoginResponseModel login(LoginBodyModel loginBody) {
        return given(RequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);

    }

    @Step("Ввод логина и пароля и получение токена")
    public String loginWithRefreshToken(LoginBodyModel loginBody) {
        return given(RequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh");

    }

    @Step("Ввод логина и пароля и получение токена")
    public String loginWithAccessToken(LoginBodyModel loginBody) {
        return given(RequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");

    }

    @Step("Отправка запроса logout")
    public void logout(LogoutBodyModel logoutBody) {
        given(RequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }

    @Step("Отправка запроса logout с пустым токеном")
    public EmptyLogoutResponseModel emptylogout(LogoutBodyModel logoutBody) {
        return given(RequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyLogoutResponseSpec)
                .extract().as(EmptyLogoutResponseModel.class);
    }

    @Step("Отправка Patch запроса на изменение пользователя ")
    public SuccessfulUpdateUserModel updatePatchlogin(UpdateBodyModel logoutBody, String accessToken) {
        return given(RequestSpec)
                .body(logoutBody)
                .header("Authorization", accessToken)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulUpdateResponseSpec)
                .extract().as(SuccessfulUpdateUserModel.class);
    }

    @Step("Отправка Put запроса на изменение пользователя ")
    public SuccessfulUpdateUserModel updatePutlogin(UpdateBodyModel logoutBody, String accessToken) {
        return given(RequestSpec)
                .body(logoutBody)
                .header("Authorization", accessToken)
                .when()
                .put("/users/me/")
                .then()
                .spec(successfulUpdateResponseSpec)
                .extract().as(SuccessfulUpdateUserModel.class);
    }


    @Step("Ввод неправильного логина или пароля")
    public WrongCredentialsLoginResponseModel wronglogin(LoginBodyModel loginBody) {
        return given(RequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract()
                .as(WrongCredentialsLoginResponseModel.class);

    }
}
