package reqres.simple_api_test;

import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;


@Test(description = "User API", groups = {"user","api"})
@Epic("Epic API для работы с пользователями")
@Feature("Feature Получение информации о пользователе и его авторизация")
public class UserTest extends BaseTest {

    @Test(description = "Получить существующего пользователя")
    @Story("Story Зная id пользователя через api получаю данные о пользователе ")
    @Description("Проверяем 200 ответ, соответствие json схеме и правильность имени.")
    public void getExistUser() {
        given().
                when().
                get(EndPoints.getUser, 2).
                then().
                spec(Spec.baseResponseSpec).
                statusCode(200).
                body("data.first_name", equalTo("Janet")).
                body(matchesJsonSchemaInClasspath("json_schema/getUserSchema.json"));
    }

    @Test(description = "Получить не существующего пользователя")
    public void getNonExistUser() {
        when().
                get(EndPoints.getUser, 22).
                then().
                spec(Spec.errorResponseSpec).
                statusCode(404).
                body(equalTo("{}"));

    }

    @Test(description = "Авториация")
    public void loginUser() {
        final String loginPasswordJson = "{\"email\": \"peter@klaven\", \"password\": \"cityslicka\"}";

        given().
                body(loginPasswordJson).
                when().
                post(EndPoints.loginUser).
                then().
                spec(Spec.baseResponseSpec).
                statusCode(200).
                body("token", equalTo("QpwL5tke4Pnpja7X"));
    }
}