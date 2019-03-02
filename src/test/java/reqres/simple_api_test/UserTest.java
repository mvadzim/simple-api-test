package reqres.simple_api_test;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.testng.Reporter;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {
    private static final String getUserApiPath = "/api/users/{id}";
    @Test
    public void getExistUser() {
        Reporter.log("-> Get exist user", true);
        given().
        when().
                log().ifValidationFails().
                get(getUserApiPath, 2).
        then().
                log().ifValidationFails().
                contentType(ContentType.JSON).
                statusCode(200).
                body("data.first_name", equalTo("Janet")).
                body(matchesJsonSchemaInClasspath("json_schema/getUserSchema.json")).
                time(lessThan(maxResponseTimeMs));
    }
    @Test
    public void getNonExistUser() {
        Reporter.log("-> Get non exist user", true);
        given().
        when().
                log().ifValidationFails().
                get(getUserApiPath, 22).
        then().
                log().ifValidationFails().
                contentType(ContentType.JSON).
                statusCode(404).
                body(equalTo("{}")).
                time(lessThan(maxResponseTimeMs));;
    }
    @Test
    public void loginUser() {
        Reporter.log("-> Login user", true);
        final String loginPasswordJson = "{\"email\": \"peter@klaven\", \"password\": \"cityslicka\"}";
        given().
                contentType("application/json").
                body(loginPasswordJson).
        when().
                log().ifValidationFails().
                post("/api/login").
        then().
                log().ifValidationFails().
                statusCode(200).
                body("token", equalTo("QpwL5tke4Pnpja7X"));
    }
}