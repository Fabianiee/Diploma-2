import org.junit.Test;
import java.net.URI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderTests {

        private static final URI BASE_URL = URI.create("https://stellarburgers.nomoreparties.site/api/orders");

    @Test
    public void createOrderWithAuthorization() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";
        String ingredients = "[\"60d3b41abdacab0026a733c6\",\"609646e4dc916e00276b2870\"]";

        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body("{\"ingredients\":" + ingredients + "}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutAuthorization() {
        String ingredients = "[\"60d3b41abdacab0026a733c6\",\"609646e4dc916e00276b2870\"]";

        given()
                .header("Content-type", "application/json")
                .body("{\"ingredients\":" + ingredients + "}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400)
                .body("success", equalTo(false));

    }
    @Test
    public void createOrderWithIngredients() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body("\"ingredients\": [\"60d3b41abdacab0026a733c6\",\"609646e4dc916e00276b2870\"]")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrderWithoutIngredients() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";

        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body("{\"ingredients\":[]}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    public void createOrderWithInvalidIngredientHash() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2ZjZjNzMyOWVkMjgwMDAxYjRjNDNiZCIsImlhdCI6MTcyNzQ0ODkxMCwiZXhwIjoxNzI3NDUwMTEwfQ.OPvRJ0WmDquAnw013KOjXCCDbIkAnGMCiuWTUVyS7fc";

        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body("{\"ingredients\":[\"invalid_hash\"]}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(403);
    }
}
