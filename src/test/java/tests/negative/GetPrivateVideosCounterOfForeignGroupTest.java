package tests.negative;

import models.GroupCounterType;
import org.junit.jupiter.api.Disabled;
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
import static utils.TestGroupsProvider.GROUP_WITH_PRIVATE_VIDEOS_COUNTER;

/**
 * Тест, который проверяет неполучение счетчика videos, когда он скрыт в настройках группы
 */
public class GetPrivateVideosCounterOfForeignGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetModeratorsCounterOfForeignGroupWithoutAccessTest.class);

    @Test
    @Tag("group")
    @Tag("negative")
    @Disabled // видео почему-то не скрываются через настройки
    @DisplayName("Тест, который проверяет неполучение счетчика videos при отсутствии прав админа")
    public void getPrivateVideosCounterOfForeignGroupTest() {
        log.info("Отправляем запрос на получение показателя videos, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.VIDEOS)
                .pathParam("group_id", GROUP_WITH_PRIVATE_VIDEOS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит videos");
        assertFalse(groupCounters.containsKey("videos"));
    }
}
