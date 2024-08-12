package tests.positive;

import models.GroupCounterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import endpoints.Endpoints;
import endpoints.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specifications.ResponseSpecProvider.successJsonResponse;
import static testData.TestGroupsProvider.FOREIGN_GROUP_WITH_PUBLIC_MODERATORS_COUNTER;

/**
 * Тест, который проверяет получение публично открытого счетчика moderators чужой группы
 */
public class GetPublicModeratorsCounterOfForeignGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetPublicModeratorsCounterOfForeignGroupTest.class);

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет получение публично открытого счетчика moderators чужой группы")
    public void getModeratorsCounterOfForeignGroupWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя moderators, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.MODERATORS)
                .pathParam("group_id", FOREIGN_GROUP_WITH_PUBLIC_MODERATORS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа содержит moderators");
        assertTrue(groupCounters.containsKey("moderators"),
                "Тело ответа не содержит значение moderators");
    }
}
