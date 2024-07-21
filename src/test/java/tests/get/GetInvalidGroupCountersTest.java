package tests.get;

import models.ResponseError;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

public class GetInvalidGroupCountersTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetInvalidGroupCountersTest.class);

    private static final String INVALID_GROUP_ID = "70000006977222";

    @Tag("GET")
    @Test
    // Название могут быть лучше, над ними не успел подумать
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
                .log().all()
                .extract().as(ResponseError.class);
        log.info("Проверяем, что тело ответа содержит ошибку");
        // дальше проверка, но я уже не успеваю (((((((
    }
}
