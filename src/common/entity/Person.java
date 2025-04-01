package common.entity;

import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable {
    private Integer height; //Поле не может быть null, Значение поля должно быть больше 0
    private Double weight; //Поле может быть null, Значение поля должно быть больше 0
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person(Integer height, Double weight, Color hairColor, Country nationality) {
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    double count_to_compare() {
        double sum = height;
        if (weight != null) {
            sum += weight;
        }
        if (hairColor != null) {
            sum += hairColor.ordinal();
        }
        if (nationality != null) {
            sum += nationality.ordinal();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Person{" +
                "height=" + height +
                ", weight=" + weight +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return Double.compare(count_to_compare(), o.count_to_compare());
    }

    public Object[] getObjects() {
        return new Object[] {
                height,
                weight,
                hairColor != null ? hairColor.name() : null,
                nationality.name()
        };
    }
}