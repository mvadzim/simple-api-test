package reqres.simple_api_test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;
import static reqres.simple_api_test.BaseTest.maxResponseTimeMs;

public class Spec {
    public static final Matcher<Integer> successStatusCodeMatcher = anyOf(is(200), is(201), is(204), is(304));
    public static final Matcher<Integer> errorStatusCodeMatcher = anyOf(is(400), is(401), is(404), is(500));

    public static final RequestSpecification baseRequestSpec =
            new RequestSpecBuilder()
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.METHOD)
                    .log(LogDetail.URI)
                    .log(LogDetail.BODY)
                    .build();

    public static final ResponseSpecification baseResponseSpec =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(successStatusCodeMatcher)
                    .expectResponseTime(lessThan(maxResponseTimeMs))
                    .build();

    public static final ResponseSpecification errorResponseSpec =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(errorStatusCodeMatcher)
                    .expectResponseTime(lessThan(maxResponseTimeMs))
                    .build();

}
