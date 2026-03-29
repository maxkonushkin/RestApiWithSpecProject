package specs.clubs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static specs.RequestSpec.RequestSpec;

public class ClubsSpec {

    public static RequestSpecification clubsRequestSpec = RequestSpec;

    public static ResponseSpecification successfulClubsListResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/clubs_list_response_schema.json"))
            .expectBody("count", notNullValue())
            .expectBody("count", greaterThanOrEqualTo(0))
            .expectBody("results", notNullValue())
            .build();

    public static ResponseSpecification successfulClubCreateSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/club_create_schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("bookTitle", notNullValue())
            .expectBody("bookAuthors", notNullValue())
            .expectBody("publicationYear", notNullValue())
            .expectBody("description", notNullValue())
            .expectBody("telegramChatLink", notNullValue())
            .expectBody("owner", notNullValue())
            .expectBody("members", notNullValue())
            .expectBody("reviews", empty())
            .expectBody("created", notNullValue())
            .expectBody("modified", nullValue())
            .build();

    public static ResponseSpecification successfulGetClubSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/club_create_schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("bookTitle", notNullValue())
            .expectBody("bookAuthors", notNullValue())
            .expectBody("publicationYear", notNullValue())
            .expectBody("description", notNullValue())
            .expectBody("telegramChatLink", notNullValue())
            .expectBody("owner", notNullValue())
            .expectBody("members", notNullValue())
            .expectBody("reviews", empty())
            .expectBody("created", notNullValue())
            .expectBody("modified", nullValue())
            .build();

    public static ResponseSpecification successfulPutClubSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/club_update_schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("bookTitle", notNullValue())
            .expectBody("bookAuthors", notNullValue())
            .expectBody("publicationYear", notNullValue())
            .expectBody("description", notNullValue())
            .expectBody("telegramChatLink", notNullValue())
            .expectBody("owner", notNullValue())
            .expectBody("members", notNullValue())
            .expectBody("reviews", empty())
            .expectBody("created", notNullValue())
            .expectBody("modified", notNullValue())
            .build();

    public static ResponseSpecification successfulDeleteSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification notExistingClubGetSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(404)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/remote_club_response_schema.json"))
            .expectBody("detail", notNullValue())
            .build();
}
