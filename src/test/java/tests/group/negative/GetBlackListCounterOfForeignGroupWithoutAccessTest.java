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
 * Тест, который проверяет неполучение счетчика black_list при отсутствии прав админа
 */
public class GetBlackListCounterOfForeignGroupWithoutAccessTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetBlackListCounterOfForeignGroupWithoutAccessTest.class);

    // ID группы, у которой скрыт счетчик black_list
    private static final String GROUP_WITH_PRIVATE_BLACKLIST_COUNTER = "54051835543681";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет неполучение счетчика black_list при отсутствии прав админа")
    public void getForeignGroupBlackListCounterWithoutAccessTest() {
        log.info("Отправляем запрос на получение показателя black_list, не имея необходимых прав");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", GroupCounterType.BLACK_LIST)
                .pathParam("format", "json")
                .pathParam("group_id", GROUP_WITH_PRIVATE_BLACKLIST_COUNTER)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа не содержит black_list");
        assertFalse(groupCounters.containsKey("black_list"),
                "Тело ответа содержит значение black_list");
    }
}
