package tests;

import models.login.LoginBodyModel;
import models.logout.EmptyLogoutResponseModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;

public class LogoutTests extends TestBase {

    String username = "qaguru";
    String password = "qaguru123";

    @Test
    public void successfulLogoutTest(){
        LoginBodyModel loginData = new LoginBodyModel(username, password);

        String refreshToken = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh");

        String logoutData = format("{\"refresh\": \"%s\"}", refreshToken);

        given(logoutRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
        assertThat(loginData.username()).isEqualTo(username);
        assertThat(loginData.password()).isEqualTo(password);
        // todo check logoutResponse is empty
    }

    @Test
    public void emptyLogoutResponseTest(){
        String refresh = "";
        LogoutBodyModel logoutData = new LogoutBodyModel(refresh);

        EmptyLogoutResponseModel logoutResponse = given(logoutRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyLogoutResponseSpec)
                .extract().as(EmptyLogoutResponseModel.class);

        String expectedRefresh = "This field may not be blank.";
        String actualRefresh = logoutResponse.refresh().get(0);
        assertThat(actualRefresh).isEqualTo(expectedRefresh);

    }
}