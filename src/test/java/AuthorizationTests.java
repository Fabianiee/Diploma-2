import org.junit.Test;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AuthorizationTests {

    private static final URI BASE_URL = URI.create("https://stellarburgers.nomoreparties.site/api/auth");

    @Test
    public void loginUserSuccessfully() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "I.am.Mary@gmail.com");
        data.put("password", "PaSsWoRd798465132");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .post(BASE_URL + "/login")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginUserWithInvalidCredentials() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "wrong@gmail.com");
        data.put("password", "wrongpassword");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .post(BASE_URL + "/login")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
