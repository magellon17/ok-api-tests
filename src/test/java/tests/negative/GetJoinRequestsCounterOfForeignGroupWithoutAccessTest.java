package tests.negative;

import models.GroupCounterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static specifications.ResponseSpecProvider.successJsonResponse;

/**
 * Тест, который проверяет неполучение счетчика join_requests при отсутствии прав админа
 */
public class GetJoinRequestsCounterOfForeignGroupWithoutAccessTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetJoinRequestsCounterOfForeignGroupWithoutAccessTest.class);

    // ID группы, у которой скрыт счетчик join_requests
    private static final String GROUP_WITH_PRIVATE_JOIN_REQUESTS_COUNTER = "54051835543681";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет неполучение счетчика join_requests при отсутствии прав админа")
    public void getGroupJoinRequestsCounterWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя join_requests, не имея прав админа");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.JOIN_REQUESTS)
                .pathParam("group_id", GROUP_WITH_PRIVATE_JOIN_REQUESTS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит join_requests");
        assertFalse(groupCounters.containsKey("join_requests"),
                "Тело ответа содержит значение join_requests");
    }
}
