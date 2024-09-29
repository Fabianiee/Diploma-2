import org.junit.Test;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AuthorizationTests {

    private static final URI BASE_URL = URI.create("https://stellarburgers.nomoreparties.site/api/auth");

    @Test
    public void loginUserSuccessfully() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"I.am.Mary@gmail.com\",\"password\":\"PaSsWoRd798465132\"}")
                .when()
                .post(BASE_URL + "/login")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginUserWithInvalidCredentials() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"wrong@gmail.com\",\"password\":\"wrongpassword\"}")
                .when()
                .post(BASE_URL + "/login")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
