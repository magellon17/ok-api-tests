package tests.group.contract;

import org.junit.jupiter.api.DisplayName;
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

/**
 * Тест, который проводит валидацию ответа со всеми возможными счетчиками
 */
public class ValidateGetAllGroupCountersSchemaTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(ValidateGetAllGroupCountersSchemaTest.class);

    // ID собственной группы
    private static final String OWN_GROUP_ID = "70000006977481";

    private static final String ALL_GROUP_COUNTERS
            = "VIDEOS, BLACK_LIST, MAYBE, JOIN_REQUESTS, MODERATORS, MEMBERS, PHOTOS, PHOTO_ALBUMS, THEMES, LINKS, PRESENTS";

    @Test
    @Tag("group")
    @Tag("contract")
    @DisplayName("Тест, который проводит валидацию ответа со всеми возможными счетчиками")
    public void validateGetGroupCountersSchemaTest() {
        log.info("Получаем все счетчики и производим валидацию схемы");
        given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", ALL_GROUP_COUNTERS)
                .pathParam("format", "json")
                .pathParam("group_id", OWN_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .assertThat()
                .body(matchesJsonSchemaInClasspath("GetGroupCountersResponseSchema.json"));
    }
}
