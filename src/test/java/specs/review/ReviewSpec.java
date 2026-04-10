package specs.review;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ReviewSpec {

    public static ResponseSpecification successfulReviewResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/review/successful_review_response_schema.json"))
            .build();

    public static ResponseSpecification successfulGetReviewResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/review/successful_review_response_schema.json"))
            .build();

    public static ResponseSpecification successfulDeleteReviewResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(204)
            .build();
}
