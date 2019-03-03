package reqres.simple_api_test;

import io.restassured.RestAssured;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.ITestContext;

public class BaseTest {
    public static long maxResponseTimeMs = 2000L;

    @BeforeClass
    public void setup(ITestContext context) {
        maxResponseTimeMs = Long.parseLong(context.getCurrentXmlTest().getParameter("maxResponseTimeMs"));
        RestAssured.baseURI = context.getCurrentXmlTest().getParameter("BaseURI");
        RestAssured.basePath = EndPoints.basePath;
        RestAssured.requestSpecification = Spec.baseRequestSpec;
        //RestAssured.responseSpecification = Spec.baseResponseSpec; // spec(Spec.errorResponseSpec) - в тесте не отрабатывает, поэтому вместо спецификации по-умолчанию везде указывается явно.
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new MyAllureRestAssured()
                .setRequestTemplate("http-request.ftl")
                .setResponseTemplate("http-response.ftl"));

        Reporter.log(
                "Base URI:" + RestAssured.baseURI +
                        "\nBase path:" + RestAssured.basePath +
                        "\nMax response time (ms):" + maxResponseTimeMs +
                        "\n----------------------------------------------"
                , true);
    }
}