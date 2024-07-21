package tests.group.get.negative;

import models.GroupCounterType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

public class GetGroupJoinRequestsCounterWithoutNecessaryRightsTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetGroupJoinRequestsCounterWithoutNecessaryRightsTest.class);

    private static final String GROUP_ID = "54051835543681";

    @Test
    @Tag("GET")
    public void getGroupJoinRequestsCounterWithoutNecessaryRightsTest() {
        log.info("Отправляем GET запрос на получение показателя join_requests, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", GroupCounterType.JOIN_REQUESTS)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит join_requests");
        assertFalse(groupCounters.containsKey("join_requests"));
    }
}
