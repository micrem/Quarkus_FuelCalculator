package org.dhbw.mosbach.ai.owm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WeatherServiceTest {

    @Test
    public void testHelloEndpoint() {
//        given()
//          .when().get("/weather/version")
//          .then()
//             .statusCode(200)
//             .body(is("0.10 alpha"));
    }

}
