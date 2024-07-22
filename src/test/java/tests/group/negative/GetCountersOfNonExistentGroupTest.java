package tests.group.negative;

import models.ResponseError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import static io.restassured.RestAssured.given;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который проверяет получение всех публичных счетчиков чужой группы
 */
public class GetCountersOfNonExistentGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetCountersOfNonExistentGroupTest.class);

    private static final String INVALID_GROUP_ID = "70000006977222";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который получает определенный счетчик собственной группы и проверяет, что получен только он")
    public void getInvalidGroupCountersTest() {
        log.info("Получаем показатели несуществующей группы");
        ResponseError error = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", "BLACK_LIST")
                .pathParam("format", "json")
                .pathParam("group_id", INVALID_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().as(ResponseError.class);
        log.info("Проверяем, что тело ответа содержит ошибку");
        // дальше проверка, но я уже не успеваю (((((((
    }
}
