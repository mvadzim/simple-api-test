package reqres.simple_api_test;

import io.restassured.RestAssured;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.ITestContext;

public class BaseTest {
    public static long maxResponseTimeMs = 2000L;

    @BeforeClass
    public void beforeClass(ITestContext context) {
        maxResponseTimeMs = Long.parseLong(context.getCurrentXmlTest().getParameter("maxResponseTimeMs"));
        RestAssured.baseURI = context.getCurrentXmlTest().getParameter("BaseURI");
        Reporter.log(
                "Base URI:" + RestAssured.baseURI +
                 "\nMax response time (ms):" + maxResponseTimeMs
                , true);
    }
}