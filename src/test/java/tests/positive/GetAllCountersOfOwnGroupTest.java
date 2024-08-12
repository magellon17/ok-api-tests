package tests.positive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import endpoints.Endpoints;
import endpoints.GroupMethodsUri;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specifications.ResponseSpecProvider.successJsonResponse;
import static testData.GroupCountersProvider.ALL_GROUP_COUNTERS_OF_OWN_GROUP;
import static testData.TestGroupsProvider.OWN_GROUP_ID;

/**
 * Тест, который проверяет получение всех счетчиков своей группы
 */
public class GetAllCountersOfOwnGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetAllCountersOfOwnGroupTest.class);

    @Test
    @Tag("group")
    @Tag("positive")
    @DisplayName("Тест, который проверяет получение всех счетчиков своей группы")
    public void getAllCountersOfOwnGroupAndValidateResponseTest() {
        log.info("Получаем показатели группы");
        Map<String, Object> groupCounters = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", ALL_GROUP_COUNTERS_OF_OWN_GROUP)
                .pathParam("group_id", OWN_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что получили нужное количество счетчиков");
        assertEquals(11, groupCounters.size());
        log.info("Проверяем, что тело ответа содержит каждый запрошенный счетчик");
        assertTrue(Arrays.stream(
                        ALL_GROUP_COUNTERS_OF_OWN_GROUP.split(", "))
                .allMatch(counter -> groupCounters.containsKey(counter.toLowerCase())));
    }
}
