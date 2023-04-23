package swagger.parser.ru;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SwaggerUtil {

    public static Swagger swagger = new SwaggerParser().read("https://api.openweathermap.org/data/2.5/" +
            "weather?q=Rostov-na-Donu&units=metric&lang=" +
            "en&mode=json&appid=fdd6f45115e02076240d062f87add0b5");

    public static void writeJson() throws IOException {
        String jsonString = Json.pretty(swagger);
        File swaggerJsonFile = new File("src/main/resources/swagger.json");
        FileWriter fw = new FileWriter(swaggerJsonFile);
        fw.write(jsonString);
        fw.close();
    }
}
