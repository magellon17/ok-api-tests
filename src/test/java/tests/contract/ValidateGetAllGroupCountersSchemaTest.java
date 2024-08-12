package tests.contract;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import endpoints.Endpoints;
import endpoints.GroupMethodsUri;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specifications.ResponseSpecProvider.successJsonResponse;
import static testData.GroupCountersProvider.ALL_GROUP_COUNTERS_OF_OWN_GROUP;
import static testData.TestGroupsProvider.OWN_GROUP_ID;

/**
 * Тест, который проводит валидацию ответа со всеми возможными счетчиками
 */
public class ValidateGetAllGroupCountersSchemaTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(ValidateGetAllGroupCountersSchemaTest.class);

    @Test
    @Tag("group")
    @Tag("contract")
    @DisplayName("Тест, который проводит валидацию ответа со всеми возможными счетчиками")
    public void validateGetGroupCountersSchemaTest() {
        log.info("Получаем все счетчики и производим валидацию схемы");
        given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", ALL_GROUP_COUNTERS_OF_OWN_GROUP)
                .pathParam("group_id", OWN_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(successJsonResponse())
                .assertThat()
                .body(matchesJsonSchemaInClasspath("GetGroupCountersResponseSchema.json"));
    }
}
