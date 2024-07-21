package tests.group.get.contract;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

public class ValidateGetGroupCountersSchemaTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(ValidateGetGroupCountersSchemaTest.class);

    private static final String GROUP_ID = "54051835543681";

    @Test
    @Tag("GET")
    // Названия могут быть лучше, над ними не успел подумать
    public void validateGetGroupCountersSchemaTest() {
        log.info("Получаем список пользователей и производим валидацию схемы");
        given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", "BLACK_LIST, MEMBERS, PHOTOS, PRESENTS") // и так далее.. (не успел более изящное решение написать)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .log().all()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("GetGroupCountersResponseSchema.json"));
    }
}
