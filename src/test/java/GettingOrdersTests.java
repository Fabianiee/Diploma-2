import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GettingOrdersTests {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/orders";

    @Test
    public void authorizedUserGetsOrder() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";

        given()
                .header("Authorization", token)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void unauthorizedUserGetsOrder() {
        given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
