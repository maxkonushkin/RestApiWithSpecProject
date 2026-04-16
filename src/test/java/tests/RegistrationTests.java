package tests;

import models.registration.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

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

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse = api.users.register(registrationData);

        step("проверка ответа (201)", () -> {
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
    @DisplayName("Попытка повторной регистрации существующего пользователя")
    public void existingUserWrongRegistrationTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel firstRegistrationResponse = api.users.register(registrationData);

        assertThat(firstRegistrationResponse.username()).isEqualTo(username);

        ExistingUserResponseModel secondRegistrationResponse = api.users.wrongregister(registrationData);

        step("Проверка текста ошибки", () -> {
            String expectedError = "A user with that username already exists.";
            String actualError = secondRegistrationResponse.username().get(0);
            assertThat(actualError).isEqualTo(expectedError);
        });
    }


    @Test
    @DisplayName("Попытка регистрации пользователя без пароля")
    public void withoutPasswordRegistrationTest() {

        RegistrationWithoutPasswordBodyModel registrationData = new RegistrationWithoutPasswordBodyModel(username);

        WithoutPasswordResponseModel WithoutPasswordResponse = api.users.registerWithoutPassword(registrationData);

        step("Проверка текста ошибки", () -> {
            String expectedRefresh = "This field is required.";
            String actualRefresh = WithoutPasswordResponse.password().get(0);
            assertThat(actualRefresh).isEqualTo(expectedRefresh);
        });
    }
}
