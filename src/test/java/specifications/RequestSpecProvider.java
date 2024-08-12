package specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static utils.TestData.*;

public class RequestSpecProvider {
    public static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .addPathParam("application_key", APPLICATION_KEY)
            .addPathParam("format", "json")
            .addPathParam("sig", SIG)
            .addPathParam("access_token", ACCESS_TOKEN)
            .build();
}
