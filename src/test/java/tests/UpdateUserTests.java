package tests;

import models.Update.SuccessfulUpdateUserModel;
import models.Update.UpdateBodyModel;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.loginRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;
import static specs.registration.RegisterSpec.RegisterRequestSpec;
import static specs.registration.RegisterSpec.successRegisterRequestSpec;
import static specs.update.UpdateSpec.successfulUpdateResponseSpec;
import static specs.update.UpdateSpec.updateRequestSpec;

public class UpdateUserTests extends TestBase {
    String username;
    String firstName = "maksim";
    String lastName = "ivanov";
    String email = "mkon@gmail.com";
    String password = "1234";

    @BeforeEach
    public void prepareTestData() {
        Faker faker = new Faker();
        username = faker.name().firstName();
    }

    @Test
    public void successfulRegistrationTest(){
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);

        SuccessfulRegistrationResponseModel registrationResponse = given(RegisterRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successRegisterRequestSpec)
                .extract().as(SuccessfulRegistrationResponseModel.class);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
        assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);

        LoginBodyModel loginData = new LoginBodyModel(username, password);

        SuccessfulLoginResponseModel loginResponse = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(SuccessfulLoginResponseModel.class);

        String expectedTokenPath = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();

        assertThat(actualAccess).startsWith(expectedTokenPath);
        assertThat(actualRefresh).startsWith(expectedTokenPath);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
        String accessToken = "Bearer " + loginResponse.access();

        UpdateBodyModel loginData2 = new UpdateBodyModel(username, firstName, lastName, email);

        SuccessfulUpdateUserModel loginResponse2 = given(updateRequestSpec)
                .body(loginData2)
                .header("Authorization", accessToken)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulUpdateResponseSpec)
                .extract().as(SuccessfulUpdateUserModel.class);
    }
}
