package tests.group.positive;

import models.GroupCounterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ApiTest;
import utils.Endpoints;
import utils.GroupMethodsUri;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Specifications.requestSpec;
import static utils.Specifications.responseSpecOK200;

/**
 * Тест, который получает определенный счетчик собственной группы и проверяет, что получен только он
 */
public class GetEachCounterOfOwnGroupAndValidateResponseTest extends ApiTest {

    private static final Logger log = LoggerFactory.getLogger(GetEachCounterOfOwnGroupAndValidateResponseTest.class);

    private static final String OWN_GROUP_ID = "70000006977481";

    @Tag("group")
    @Tag("positive")
    @DisplayName("Тест, который получает определенный счетчик собственной группы и проверяет, что получен только он")
    @ParameterizedTest
    @ArgumentsSource(EmployeesArgumentsProvider.class)
    public void getEachCounterOfOwnGroupAndValidateResponseTest(GroupCounterType ContentType) {
        log.info("Получаем показатель собственной группы");
        Map<String, Object> groupCounters = given()
                .spec(requestSpec(BASE_URL))
                .pathParam("application_key", APPLICATION_KEY)
                .pathParam("counterTypes", ContentType)
                .pathParam("format", "json")
                .pathParam("group_id", OWN_GROUP_ID)
                .pathParam("method", GroupMethodsUri.getGroupCounters)
                .pathParam("sig", SIG)
                .pathParam("access_token", ACCESS_TOKEN)
                .get(Endpoints.getGroupCounters)
                .then()
                .spec(responseSpecOK200())
                .extract().response().jsonPath().getMap("counters");
        log.info("Проверяем, что тело ответа содержит требуемый элемент");
        assertTrue(groupCounters.containsKey(ContentType.toString().toLowerCase()));

        log.info("Проверяем, что тело ответа содержит только требуемый элемент");
        assertEquals(1, groupCounters.size());
    }

    static class EmployeesArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(GroupCounterType.VIDEOS),
                    Arguments.of(GroupCounterType.BLACK_LIST),
                    Arguments.of(GroupCounterType.MAYBE),
                    Arguments.of(GroupCounterType.MODERATORS),
                    Arguments.of(GroupCounterType.MEMBERS),
                    Arguments.of(GroupCounterType.PHOTOS),
                    Arguments.of(GroupCounterType.PHOTO_ALBUMS),
                    Arguments.of(GroupCounterType.JOIN_REQUESTS),
                    Arguments.of(GroupCounterType.THEMES),
                    Arguments.of(GroupCounterType.LINKS),
                    Arguments.of(GroupCounterType.PRESENTS)
            );
        }
    }
}
