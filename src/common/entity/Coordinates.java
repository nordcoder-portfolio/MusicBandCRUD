package common.entity;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x; //Максимальное значение поля: 644, Поле не может быть null
    private Long y; //Значение поля должно быть больше -154, Поле не может быть null

    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }

    double count_to_compare() {
        return x + y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
