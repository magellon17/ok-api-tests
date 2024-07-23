package tests.group.negative;

import models.ErrorCodes;
import models.ErrorMessages;
import models.GroupCounterType;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который проверяет получение GROUP_RESTRICTION ошибки при попытке получить счетчик чужой приватной группы
 */
public class GetCounterOfForeignPrivateGroupAndValidateErrorTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetCounterOfForeignPrivateGroupAndValidateErrorTest.class);

    // ID чужой приватной группы
    private static final String FOREIGN_PRIVATE_GROUP_ID = "70000007250857";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет получение GROUP_RESTRICTION ошибки при попытке получить счетчик чужой приватной группы")
    public void getCounterOfForeignPrivateGroupAndValidateErrorTest() {
        log.info("Отправляем запрос на получение показателя чужой приватной группы");
        ResponseError error = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", GroupCounterType.MEMBERS)
                .pathParam("format", "json")
                .pathParam("group_id", FOREIGN_PRIVATE_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().as(ResponseError.class);
        log.info("Проверяем, что тело ответа содержит правильное сообщение об ошибке");
        assertEquals(ErrorMessages.GROUP_RESTRICTION.toString(), error.getError_msg(),
                "Сообщение об GROUP_RESTRICTION ошибке не совпало с требуемым");

        log.info("Проверяем, что тело ответа содержит правильный код ошибки");
        assertEquals(ErrorCodes.GROUP_RESTRICTION.toString(), error.getError_code(),
                "Код GROUP_RESTRICTION ошибки не совпал с требуемым");
    }
}
