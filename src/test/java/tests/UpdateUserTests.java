package tests;

import models.update.SuccessfulUpdateUserModel;
import models.update.UpdateBodyModel;
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

public class UpdateUserTests extends TestBase {
    String username;
    String firstName = "maksim";
    String lastName = "ivanov";
    String email = "mkon@gmail.com";

    @BeforeEach
    public void prepareTestData() {
        Faker faker = new Faker();
        username = faker.name().firstName();
    }

    @Test
    @DisplayName("Успешное изменение пользователя методом patch")
    public void successfulPatchUpdateTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
        assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        SuccessfulUpdateUserModel updateResponse = api.auth.updatePatchlogin(new UpdateBodyModel(username, firstName, lastName, email), accessToken);

        step("Проверка обновлённых данных", () -> {
            assertThat(updateResponse.username()).isEqualTo(username);
            assertThat(updateResponse.firstName()).isEqualTo(firstName);
            assertThat(updateResponse.lastName()).isEqualTo(lastName);
            assertThat(updateResponse.email()).isEqualTo(email);
        });
    }


    @Test
    @DisplayName("Успешное изменение пользователя методом put")
    public void successfulPutUpdateTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
        assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);

        LoginBodyModel loginData = new LoginBodyModel(username, password);
        String accessToken = "Bearer " + api.auth.loginWithAccessToken(loginData);

        SuccessfulUpdateUserModel updateResponse = api.auth.updatePutlogin(new UpdateBodyModel(username, firstName, lastName, email), accessToken);
        step("Проверка обновлённых данных", () -> {
            assertThat(updateResponse.username()).isEqualTo(username);
            assertThat(updateResponse.firstName()).isEqualTo(firstName);
            assertThat(updateResponse.lastName()).isEqualTo(lastName);
            assertThat(updateResponse.email()).isEqualTo(email);
        });
    }
}