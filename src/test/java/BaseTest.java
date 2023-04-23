import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    static Faker faker;

    @BeforeAll
    public static void setBaseUri() {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        faker = new Faker();
    }

    @BeforeEach
    public void setup() {
        RequestSpecification requestSpec = RestAssured.given();
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        ResponseSpecification responseWeatherDto =specBuilder.build();
    }

}
