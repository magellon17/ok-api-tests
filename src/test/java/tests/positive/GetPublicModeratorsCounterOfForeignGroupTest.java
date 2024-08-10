package tests.positive;

import models.GroupCounterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который проверяет получение публично открытого счетчика moderators чужой группы
 */
public class GetPublicModeratorsCounterOfForeignGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetPublicModeratorsCounterOfForeignGroupTest.class);

    // ID группы, у которой открыт счетчик moderators
    private static final String GROUP_WITH_PUBLIC_MODERATORS_COUNTER = "70000007221417";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет получение публично открытого счетчика moderators чужой группы")
    public void getModeratorsCounterOfForeignGroupWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя moderators, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", GroupCounterType.MODERATORS)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_WITH_PUBLIC_MODERATORS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа содержит moderators");
        assertTrue(groupCounters.containsKey("moderators"),
                "Тело ответа не содержит значение moderators");
    }
}
