package tests.negative;

import models.ErrorCodes;
import models.ErrorMessages;
import models.GroupCounterType;
import models.ResponseError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specifications.RequestSpecProvider;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specifications.ResponseSpecProvider.successJsonResponse;

/**
 * Тест, который проверяет получение GROUP_RESTRICTION ошибки при попытке получить счетчик чужой приватной группы
 */
public class GetCounterOfForeignPrivateGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetCounterOfForeignPrivateGroupTest.class);

    // ID чужой приватной группы
    private static final String FOREIGN_PRIVATE_GROUP_ID = "70000007250857";

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет получение GROUP_RESTRICTION ошибки при попытке получить счетчик чужой приватной группы")
    public void getCounterOfForeignPrivateGroupAndValidateErrorTest() {
        log.info("Отправляем запрос на получение показателя чужой приватной группы");
        ResponseError error = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.MEMBERS)
                .pathParam("group_id", FOREIGN_PRIVATE_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().as(ResponseError.class);
        log.info("Проверяем, что тело ответа содержит правильное сообщение об ошибке");
        assertEquals(ErrorMessages.GROUP_RESTRICTION.toString(), error.getError_msg(),
                "Сообщение об GROUP_RESTRICTION ошибке не совпало с требуемым");

        log.info("Проверяем, что тело ответа содержит правильный код ошибки");
        assertEquals(ErrorCodes.GROUP_RESTRICTION.toString(), error.getError_code(),
                "Код GROUP_RESTRICTION ошибки не совпал с требуемым");
    }
}
