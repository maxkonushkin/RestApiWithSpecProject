package api;

import io.qameta.allure.Step;
import models.clubs.*;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.RequestSpec;
import static specs.clubs.ClubsSpec.*;

public class ClubsApiClient {

    @Step("Получение списка клубов GET /clubs/")
    public ClubsListResponseModel getClubs() {
        return given(clubsRequestSpec)
                .when()
                .get("/clubs/")
                .then()
                .spec(successfulClubsListResponseSpec)
                .extract()
                .as(ClubsListResponseModel.class);
    }

    @Step("Создание клуба")
    public CreateClubResponseModel clubCreate(CreateClubRequestModel clubData, String accessToken) {
        return given(RequestSpec)
                .body(clubData)
                .header("Authorization", accessToken)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulClubCreateSpec)
                .extract().as(CreateClubResponseModel.class);
    }

    @Step("Просмотр клуба")
    public GetClubModel clubGet(int clubId, String accessToken) {
        return given(RequestSpec)
                .header("Authorization", accessToken)
                .when()
                .get("clubs/" + clubId + "/")
                .then()
                .spec(successfulGetClubSpec)
                .extract().as(GetClubModel.class);
    }

    @Step("Обновление клуба")
    public UpdateClubPutResponseBodyModel clubPutUpdate(int clubId, UpdateClubPutRequestBodyModel clubData, String accessToken) {
        return given(RequestSpec)
                .body(clubData)
                .header("Authorization", accessToken)
                .when()
                .put("clubs/" + clubId + "/")
                .then()
                .spec(successfulPutClubSpec)
                .extract().as(UpdateClubPutResponseBodyModel.class);
    }

    @Step("Удаление клуба")
    public void clubDelete(int clubId, String accessToken) {
        given(RequestSpec)
                .header("Authorization", accessToken)
                .when()
                .delete("clubs/" + clubId + "/")
                .then()
                .spec(successfulDeleteSpec);
    }

    @Step("Поиск удалённого клуба")
    public GetNotExistingClubResponseBodyModel getNotExistingClub(int clubId, String accessToken) {
        return given(RequestSpec)
                .header("Authorization", accessToken)
                .when()
                .get("clubs/" + clubId + "/")
                .then()
                .spec(notExistingClubGetSpec)
                .extract().as(GetNotExistingClubResponseBodyModel.class);
    }
}