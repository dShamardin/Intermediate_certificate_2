package swagger.parser.ru;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static swagger.parser.ru.SwaggerUtil.writeJson;

public class WeatherDataDtoSwagger {

    @BeforeAll
    public static void getApiWeatherDataDto() throws IOException {
        writeJson();
    }

    @BeforeEach
    public void setBaseUri() {
        RestAssured.baseURI = "https://" + SwaggerUtil.swagger.getHost() + SwaggerUtil.swagger.getBasePath();
    }

    @Test
    public void shouldWeatherDataDtoSwagger() {

        RequestSpecification request = RestAssured.given();
        request
                .contentType("multipart/form-data")
                .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang=" +
                        "en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
                .then()
                .log().body()
                .statusCode(200);
    }


}
