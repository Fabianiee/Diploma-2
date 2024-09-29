import org.junit.Test;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdatingDataTests {

    private static final URI BASE_URL = URI.create("https://stellarburgers.nomoreparties.site/api/auth");

    @Test
    public void updateUserDataWithAuthorization() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";

        given()
                .contentType("application/json")
                .header("Authorization", token)
                .body("{\"email\":\"newemail@example.com\",\"name\":\"New Name\"}")
                .when()
                .patch(BASE_URL + "/user")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void updateUserDataWithoutAuthorization() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"newemail@example.com\",\"name\":\"New Name\"}")
                .when()
                .patch(BASE_URL + "/user")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
