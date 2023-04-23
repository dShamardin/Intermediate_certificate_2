import com.sun.security.jgss.AuthorizationDataEntry;
import io.qameta.allure.Flaky;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.equalTo;

public class WeatherDataTest extends BaseTest {

    RequestSpecification requestSpec;

    ResponseSpecification responseWeatherDto;


  // Test№1
    @Flaky
    @Test
    public void shouldGetWeatherDataByNameOfTheCity() {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        RequestSpecification request = RestAssured.given();
        WeatherDataDto response = (WeatherDataDto) request
                //.param("name", "Moscow")
                .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang" +
                        "=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");
//                .as(new TypeRef<>() {
//                });

        assertThat(response)
                .extracting(
                        WeatherDataDto::getName,
                        WeatherDataDto::getId,
                        WeatherDataDto::getVisibility
                )
                .containsExactlyInAnyOrder(
                        tuple(
                                "Rostov-na-Donu",
                                802,
                                10000
                        )
                );
    }

    // Test№2
    @Test
    public void shouldGetWeatherData() {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        RequestSpecification request = RestAssured.given();
        Response response = request
                .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang" +
                        "=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");


        assertThat(response).extracting(
                Response::getContentType,
                Response::getStatusCode
        ).contains(
                "application/json; charset=utf-8",
                200
        );
    }

    // Test№3
    @Test
    public void shouldGetWeatherDataByParameters() throws NullPointerException {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        RequestSpecification request = RestAssured.given();
        ValidatableResponse response = request
            .get("https://api.openweathermap.org/data/2.5/weather?q=Moscow&id=802&lat=55.7522&lon=37." +
                    "6156&units=metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
            .then()
            .spec(responseWeatherDto)
            .body("name", equalTo("Moscow"));
//            .body("id",equalTo("802"))
//            .body("lat", equalTo("55.7522"))
//            .body("lon", equalTo("37.6156"));
    }

    // Test№4

    @Test
    public void shouldNotGetWeatherData() {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        RequestSpecification request = RestAssured.given();

        Response response = request.get("https://api.openweathermap.org/data/2.5/weather?units=" +
                "metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");
        response.then()
                .statusCode(400)
                .assertThat()
                .body(
                        "cod", equalTo("400"),
"message", equalTo("Nothing to geocode")
                );
    }

    // Test№5
    @Test
    public void shouldGetWeatherDataByJson() {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        RequestSpecification getRequest = RestAssured.given();

        final CoordDto coord = new CoordDto();

        JSONObject object = new JSONObject();
        object.put("lon", coord.getLon());
        object.put("lat", coord.getLat());

        final JSONArray coordjson =new JSONArray(List.of(object));
        getRequest.body(coordjson.toString());


        System.out.println(getRequest.log().body());
       // getRequest.body(object.toString());
       // getRequest.header("content-type", "application/json");
        Response response = getRequest.get("https://api.openweathermap.org/data/2.5/weather?q=Moscow&units=" +
                "imperial&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");

        response
                .then()
                .statusCode(200)
                .body(
                        "37.6156", equalTo(coord.getLon()),
                        "55.7522", equalTo(coord.getLat())
                );
    }

   }


