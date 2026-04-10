package api;

import io.qameta.allure.Step;
import models.review.CreateReviewRequestModel;
import models.review.GetReviewResponseModel;
import models.review.SuccessfulReviewResponseModel;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.RequestSpec;
import static specs.review.ReviewSpec.*;

public class ReviewApiClient {


    @Step("Создание ревью")
    public SuccessfulReviewResponseModel createReviewBody(CreateReviewRequestModel reviewData, String accessToken) {
        return given(RequestSpec)
                .body(reviewData)
                .header("Authorization", accessToken)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(successfulReviewResponseSpec)
                .extract().as(SuccessfulReviewResponseModel.class);
    }

    @Step("Успешный просмотр обзора на книгу")
    public GetReviewResponseModel getReviewBody(int reviewId, String accessToken){
        return given(RequestSpec)
                .header("Authorization", accessToken)
                .when()
                .get("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulGetReviewResponseSpec)
                .extract().as(GetReviewResponseModel.class);
    }

    @Step("Успешное удаление обзора на книгу")
    public void deleteReviewBody(int reviewId, String accessToken){
        given(RequestSpec)
                .header("Authorization", accessToken)
                .when()
                .delete("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulDeleteReviewResponseSpec);
    }
}
