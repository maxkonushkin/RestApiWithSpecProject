package api;

import io.qameta.allure.Step;
import models.registration.*;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.RequestSpec;
import static specs.registration.RegisterSpec.*;

public class UsersApiClient {

    @Step("Успешная регистрация пользователя")
    public SuccessfulRegistrationResponseModel register(RegistrationBodyModel registrationData) {
        return given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successRegisterResponseSpec)
                .extract().as(SuccessfulRegistrationResponseModel.class);
    }

    @Step("Неуспешная регистрация пользователя")
    public ExistingUserResponseModel wrongregister(RegistrationBodyModel registrationData) {
        return given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(wrongCredentialsRegisterResponseSpec)
                .extract().as(ExistingUserResponseModel.class);
    }

    @Step("Неуспешная регистрация пользователя")
    public WithoutPasswordResponseModel registerWithoutPassword(RegistrationWithoutPasswordBodyModel registrationData) {
        return given(RequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(withoutPasswordRegisterResponseSpec)
                .extract().as(WithoutPasswordResponseModel.class);
    }

}
