package tests.group.get.positive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который проверяет получение всех публичных счетчиков и проверяет их количество
 */
public class GetAllGroupCountersAndValidateResponseTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetAllGroupCountersAndValidateResponseTest.class);

    private static final String GROUP_ID = "70000006977225";
    private static final String ALL_COUNTER_TYPES = "BLACK_LIST, MEMBERS, MODERATORS, PHOTOS, PHOTO_ALBUMS, THEMES, LINKS, PRESENTS";

    @Test
    @Tag("GET")
    @DisplayName("Тест, который проверяет получение всех публичных счетчиков и проверяет их количество")
    public void getAllGroupCountersAndValidateResponseTest() {
        log.info("Получаем показатели группы");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", ALL_COUNTER_TYPES)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .log().all()
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа содержит только требуемый элемент");
        assertEquals(8, groupCounters.size());

        log.info("Проверяем, что тело ответа содержит каждый запрошенный счетчик");
        assertTrue(Arrays.stream(
                ALL_COUNTER_TYPES.split(", "))
                .allMatch(counter -> groupCounters.containsKey(counter.toLowerCase())));
    }
}
