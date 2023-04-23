import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;

public class WeatherDataTest extends BaseTest {

    RequestSpecification requestSpec;

    ResponseSpecification responseWeatherDataDto;


  // Test№1

    @Test
    public void shouldGetWeatherDataByNameOfTheCity() {
//        RequestSpecification requestSpec = RestAssured.given();
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        ResponseSpecification responseWeatherDataDto = specBuilder.build();
        WeatherDataDto response = requestSpec
                .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang" +
                        "=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
                .as(new TypeRef<>() {});

        assertThat(response.getName()).isEqualTo("Rostov-on-Don");

        assertThat(response)
                .extracting(
                        WeatherDataDto::getName,
                        WeatherDataDto::getId
                )
                .containsExactlyInAnyOrder(
                        tuple(
                                "Rostov-on-Don",
                                802
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
        requestSpec
            .get("https://api.openweathermap.org/data/2.5/weather?q=Moscow&id=802&lat=55.7522&lon=37." +
                    "6156&units=metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
            .then()
            .spec(responseWeatherDataDto)
            .body("name", equalTo("Moscow"))
            .body("lon", equalTo("37.6156"));
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

    // Test№6

    @Test
    public void shouldGetWeatherDataByTheCityName() {
        RequestSpecification requestSpec = RestAssured.given();
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        ResponseSpecification responseWeatherDataDto = specBuilder.build();
    requestSpec
            .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang=" +
                    "en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
            .then()
            .spec(responseWeatherDataDto)
            .body("name", equalTo("Rostov-on-Don"));
    }

    // Test№7

    @Test
    public void shouldGetWeatherDataWithNegativeLongitude() {
        RequestSpecification requestSpec = RestAssured.given();
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        ResponseSpecification responseWeatherDataDto = specBuilder.build();
        requestSpec
                .param("lon","-10")
                .get("https://api.openweathermap.org/data/2.5/weather?lon=-10&units=" +
                        "metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
                .then()
                .statusCode(400)
                .assertThat()
                .body(
                        "cod", equalTo("400"),
                        "message", equalTo("Nothing to geocode")
                );
    }

    // Test№8
    @Test
    public void shouldGetWeatherDataWithExtractind() {
        RequestSpecification requestSpec = RestAssured.given();
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        ResponseSpecification responseWeatherDataDto = specBuilder.build();
       String nameCity = requestSpec
               .get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-on-Don&lon=-10&units=" +
                       "metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
               .then()
               .spec(responseWeatherDataDto)
               .body("name",equalTo("Rostov-on-Don"))
               .extract()
               .path("name");

    }

   }


