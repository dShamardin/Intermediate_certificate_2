import com.sun.security.jgss.AuthorizationDataEntry;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WeatherDataTest  extends BaseTest{

    RequestSpecification request;



    @BeforeEach
    public void setUp() {
        request = RestAssured.given();
    }

    @Test
    void shouldGetWeatherDataByCityname() {
    Response response = request.get("/weather");
    RequestSpecification getRequest = RestAssured.given();
    getRequest.header("authority", "fdd6f45115e02076240d062f87add0b5");

    assertThat(response)
            .extracting(
                    Response::getContentType,
                    Response::getStatusCode
            ).contains(
                    "application/json; charset=utf-8"
            );

    }




}
