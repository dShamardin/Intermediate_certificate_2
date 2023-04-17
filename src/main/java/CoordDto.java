import com.github.javafaker.Faker;

public class CoordDto {

 private Float  lon;  // longitude

 private Float  lat;  // latitude


 public CoordDto(Float lon, Float lat){
    this.lon = lon;
    this.lat = lat;

 }

 public CoordDto() {
    final Faker faker = new Faker();

    this.lon = Float.valueOf(faker.address().longitude());
    this.lat = Float.valueOf(faker.address().latitude());

 }


    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
}
