package specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.lessThan;

public class ResponseSpecProvider {
    public static ResponseSpecification successJsonResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }
}
