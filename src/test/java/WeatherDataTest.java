import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WeatherDataTest extends BaseTest {

    static RequestSpecification requestSpec;
    static ResponseSpecification responseWeatherDataDto;

    @BeforeEach
    public void setup() {
        RequestSpecification requestSpec = new RequestSpecBuilder().build();

    }


  // Test№1

    @Test
    public void shouldGetWeatherData1() {
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        Response response1 = given().get("https://api.openweathermap.org/data/2.5/weather?q=Rostov-na-Donu&units=metric&lang" +
                "=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");

        assertThat(response1)
                .extracting(
                        Response::getStatusCode,
                        Response::getStatusLine
                )
                .contains(
                        200,
                        "HTTP/1.1 200 OK"
                );
    }

    // Test№2
    @Test
    public void shouldGetWeatherData2() {
        RequestSpecification request = given();
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
    public void shouldGetWeatherDataByOkHttp() throws IOException {

    String baseUrl = "https://api.openweathermap.org/data/2.5";

        OkHttpClient client = new OkHttpClient();

        String str = "{}";
        RequestBody body = RequestBody.create(str.getBytes());

        Request getRequest = new Request.Builder()
                .url(baseUrl + "/weather?q=Rostov-na-Donu&units=metric&lang" +
                        "=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
                .build();

        Call call = client.newCall(getRequest);

        okhttp3.Response response = call.execute();

        assertThat(response.code()).isEqualTo(200);


    }

    // Test№4

    @Test
    public void shouldNotGetWeatherData() {
        RequestSpecification request = given();

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
        RequestSpecification getRequest = given();

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
        RequestSpecification requestSpec = given();
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
        RequestSpecification requestSpec = given();
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
    public void shouldGetWeatherDataWithExtracting() {
        RequestSpecification requestSpec = given();
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

    // Test№9
    @Step("Тест падает по не выясненой причине")
    @Test
    public void shouldGetWeatherDataWithTypeRef() {
        RequestSpecification requestSpec = given();
        WeatherDataDto response =requestSpec
                .get("https://api.openweathermap.org/data/2.5/weather?q=Moscow&id=802&lat=55.7522&lon=37." +
                        "6156&units=metric&lang=en&mode=json&appid=fdd6f45115e02076240d062f87add0b5")
                .as(new TypeRef<>() {});

        assertThat(response.getName()).isEqualTo("Moscow");
    }


   }


