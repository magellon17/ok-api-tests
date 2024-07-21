package tests.group.get.positive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который получает определенный счетчик и проверяет, что получен только он
 */
public class GetEachGroupCounterTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetEachGroupCounterTest.class);

    private static final String GROUP_ID = "70000006977225";

    @Tag("GET")
    @DisplayName("Тест, который получает определенный счетчик и проверяет, что получен только он")
    @ParameterizedTest
    @ValueSource(strings = {"BLACK_LIST", "MEMBERS", "MODERATORS", "PHOTOS", "PHOTO_ALBUMS", "THEMES", "LINKS", "PRESENTS"})
    public void getEachGroupCounterTest(String ContentType) {
        log.info("Получаем показатели группы");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", ContentType)
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
        log.info("Проверяем, что тело ответа содержит требуемый элемент");
        assertTrue(groupCounters.containsKey(ContentType.toLowerCase()));

        log.info("Проверяем, что тело ответа содержит только требуемый элемент");
        assertEquals(1, groupCounters.size());
    }
}
