import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserTests {
    private final String baseUrl = "https://stellarburgers.nomoreparties.site/api/auth";

String token =  "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";
    @Test
    public void createUniqueUser() {
        //Надо убедиться, что пользователь не создан ранее
        deleteUserIfExists("I.am.Mary@gmail.com");

        given()
                .contentType("application/json")
                .body("{\"email\":\"I.am.Mary@gmail.com\",\"password\":\"PaSsWoRd798465132\",\"name\":\"Fabiani\"}")
                .when()
                .post(baseUrl + "/register")
                .then()
                .statusCode(201)
                .body("success", equalTo(true));
    }

    @Test
    public void createUserWithExistingEmail() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"I.am.Mary@gmail.com\",\"password\":\"PaSsWoRd798465132\",\"name\":\"Fabiani\"}")
                .when()
                .post(baseUrl + "/register")
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void createUserWithoutRequiredFields() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"\",\"password\":\"\"}")
                .when()
                .post(baseUrl + "/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    private void deleteUserIfExists(String email) {

        given()
               .header("Authorization", token)
                 .when()
                 .delete(baseUrl + "/user")
                 .then()
                 .statusCode(200);
    }
}
