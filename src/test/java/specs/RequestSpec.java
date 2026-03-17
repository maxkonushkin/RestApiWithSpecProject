package specs;

import io.restassured.specification.RequestSpecification;

import static allure.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpec {
    public static RequestSpecification RequestSpec = with()
            .filter(withCustomTemplate())
            .log().all()
            .contentType(JSON);
}
