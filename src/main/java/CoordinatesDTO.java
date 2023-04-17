import com.github.javafaker.Faker;

public class CoordinatesDTO {

 private Float  lon;  // longitude

 private Float  lat;  // latitude


 public CoordinatesDTO(Float lon, Float lat){
    this.lon = lon;
    this.lat = lat;

 }

 public CoordinatesDTO() {
    final Faker faker = new Faker;

    this.lon = Float.valueOf(faker.address().longitude());
    this.lat = Float.valueOf(faker.address().latitude());

 }



}
