package tests.group.negative;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который проверяет неполучение счетчика videos, когда он скрыт в настройках группы
 */
public class GetPrivateVideosCounterOfForeignGroupWithoutAccessTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetModeratorsCounterOfForeignGroupWithoutAccessTest.class);

    // ID группы, у которой скрыт счетчик videos
    private static final String GROUP_WITH_PRIVATE_VIDEOS_COUNTER = "70000007219113";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет неполучение счетчика videos при отсутствии прав админа")
    public void getVideosCounterOfForeignGroupWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя videos, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", GroupCounterType.VIDEOS)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_WITH_PRIVATE_VIDEOS_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .log().all()
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит videos");
        assertFalse(groupCounters.containsKey("videos"));
    }
}
