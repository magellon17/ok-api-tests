package tests.negative;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static specifications.ResponseSpecProvider.successJsonResponse;
import static testData.TestGroupsProvider.GROUP_WITH_PRIVATE_MODERATORS_COUNTER;

/**
 * Тест, который проверяет неполучение счетчика moderators при отсутствии прав админа
 */
public class GetModeratorsCounterOfForeignGroupWithoutAccessTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetModeratorsCounterOfForeignGroupWithoutAccessTest.class);

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет неполучение счетчика moderators при отсутствии прав админа")
    public void getModeratorsCounterOfForeignGroupWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя moderators, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.MODERATORS)
                .pathParam("group_id", GROUP_WITH_PRIVATE_MODERATORS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит moderators");
        assertFalse(groupCounters.containsKey("moderators"),
                "Тело ответа содержит значение moderators");
    }
}
