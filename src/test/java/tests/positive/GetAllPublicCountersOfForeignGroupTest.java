package tests.positive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specifications.ResponseSpecProvider.successJsonResponse;

/**
 * Тест, который проверяет получение всех публичных счетчиков чужой группы
 */
public class GetAllPublicCountersOfForeignGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetAllPublicCountersOfForeignGroupTest.class);

    private static final String FOREIGN_GROUP_ID = "54051835543681";

    private static final String ALL_PUBLIC_GROUP_COUNTERS
            = "MEMBERS, PHOTOS, PHOTO_ALBUMS, THEMES, LINKS, PRESENTS";

    @Test
    @Tag("group")
    @Tag("positive")
    @DisplayName("Тест, который проверяет получение всех публичных счетчиков чужой группы")
    public void getAllPublicCountersOfForeignGroupAndValidateResponseTest() {
        log.info("Получаем показатели группы");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", ALL_PUBLIC_GROUP_COUNTERS)
                .pathParam("group_id", FOREIGN_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что получили нужное количество счетчиков");
        assertEquals(6, groupCounters.size());

        log.info("Проверяем, что тело ответа содержит каждый запрошенный счетчик");
        assertTrue(Arrays.stream(
                        ALL_PUBLIC_GROUP_COUNTERS.split(", "))
                .allMatch(counter -> groupCounters.containsKey(counter.toLowerCase())));
    }
}
