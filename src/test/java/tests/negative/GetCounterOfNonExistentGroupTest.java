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
import static utils.TestGroupsProvider.NON_EXISTENT_GROUP_ID;

/**
 * Тест, который проверяет получение NOT_FOUND ошибки при попытке получить счетчика несуществующей группы
 */
public class GetCounterOfNonExistentGroupTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetCounterOfNonExistentGroupTest.class);

    @Test
    @Tag("group")
    @Tag("negative")
    @DisplayName("Тест, который проверяет получение NOT_FOUND ошибки при попытке получить счетчика несуществующей группы")
    public void getCounterOfNonExistentGroupAndValidateErrorTest() {
        log.info("Получаем показатели несуществующей группы");
        ResponseError error = given()
                .spec(RequestSpecProvider.BASE_SPEC)
                .pathParam("counterTypes", GroupCounterType.MEMBERS)
                .pathParam("group_id", NON_EXISTENT_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .get(Endpoints.getGroupCounters)
                .then()
                .log().all()
                .spec(successJsonResponse())
                .extract().as(ResponseError.class);
        log.info("Проверяем, что тело ответа содержит правильное сообщение об NOT_FOUND ошибке");
        assertEquals(ErrorMessages.NOT_FOUND.toString(), error.getError_msg(),
                "Сообщение об ошибке не совпало с требуемым");

        log.info("Проверяем, что тело ответа содержит код NOT_FOUND ошибки");
        assertEquals(ErrorCodes.NOT_FOUND.toString(), error.getError_code(),
                "Код ошибки не совпал с требуемым");
    }
}
