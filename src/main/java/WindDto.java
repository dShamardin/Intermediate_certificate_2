public class WindDto {

    private Float speed;

    private Integer deg;


    public WindDto(Float speed, Integer deg){
        this.speed = speed;
        this.deg = deg;
    }


    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }
}
