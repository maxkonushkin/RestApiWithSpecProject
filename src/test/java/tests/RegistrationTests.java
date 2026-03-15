package tests;

import models.registration.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.RequestSpec;
import static specs.registration.RegisterSpec.*;

public class RegistrationTests extends TestBase {

    String username;
    String password;

    @BeforeEach
    public void prepareTestData() {
        Faker faker = new Faker();
        username = faker.name().firstName();
        password = faker.name().firstName();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void successfulRegistrationTest() {
        step("Регистрация нового пользователя и проверка ответа (201)", () -> {
            RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);

            SuccessfulRegistrationResponseModel registrationResponse = given(RequestSpec)
                    .body(registrationData)
                    .when()
                    .post("/users/register/")
                    .then()
                    .spec(successRegisterResponseSpec)
                    .extract().as(SuccessfulRegistrationResponseModel.class);

            assertThat(registrationResponse.id()).isGreaterThan(0);
            assertThat(registrationResponse.username()).isEqualTo(username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");

            String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
            assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);
        });
    }

    @Test
    @DisplayName("Попытка повторной регистрации пользователя")
    public void existingUserWrongRegistrationTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        step("Регистрация нового пользователя и проверка ответа (201)", () -> {
        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successRegisterResponseSpec)
                .extract().as(SuccessfulRegistrationResponseModel.class);

        assertThat(firstRegistrationResponse.username()).isEqualTo(username);
        });
        step("Попытка повторной регистрации пользователя", () -> {
        ExistingUserResponseModel secondRegistrationResponse = given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(wrongCredentialsRegisterResponseSpec)
                .extract().as(ExistingUserResponseModel.class);

        String expectedError = "A user with that username already exists.";
        String actualError = secondRegistrationResponse.username().get(0);
        assertThat(actualError).isEqualTo(expectedError);
        });
    }

    @Test
    @DisplayName("Попытка регистрации пользователя без пароля")
    public void withoutPasswordRegistrationTest(){
        step("Попытка повторной регистрации пользователя", () -> {
        RegistrationWithoutPasswordBodyModel registrationData = new RegistrationWithoutPasswordBodyModel(username);

        WithoutPasswordResponseModel registrationResponse = given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(withoutPasswordRegisterResponseSpec)
                .extract().as(WithoutPasswordResponseModel.class);

        String expectedRefresh = "This field is required.";
        String actualRefresh = registrationResponse.password().get(0);
        assertThat(actualRefresh).isEqualTo(expectedRefresh);
        });
    }
}
