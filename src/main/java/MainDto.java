public class MainDto {

    private Float temp;

    private Integer pressure;

    private Integer humidity;

    private Float temp_min;

    private Float temp_max;

    private Short sea_level;

    private Short grnd_level;


    public MainDto(Float temp, Integer pressure, Integer humidity, Float temp_min, Float temp_max, Short sea_level,
                   Short grnd_level) {

        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Float temp_min) {
        this.temp_min = temp_min;
    }

    public Float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Float temp_max) {
        this.temp_max = temp_max;
    }

    public Short getSea_level() {
        return sea_level;
    }

    public void setSea_level(Short sea_level) {
        this.sea_level = sea_level;
    }

    public Short getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(Short grnd_level) {
        this.grnd_level = grnd_level;
    }
}

