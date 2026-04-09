package api;

import io.qameta.allure.Step;
import models.review.CreateReviewRequestModel;
import models.review.SuccessfulReviewResponseModel;

import static io.restassured.RestAssured.given;
import static specs.RequestSpec.RequestSpec;
import static specs.review.ReviewSpec.successfulReviewResponseSpec;

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
}
