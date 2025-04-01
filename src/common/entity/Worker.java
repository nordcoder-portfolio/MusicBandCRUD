package common.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static server.db.DatabaseOperations.getCoordinatesById;
import static server.db.DatabaseOperations.getPersonById;

public class Worker implements Comparable<Worker>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double salary; //Значение поля должно быть больше 0
    private java.time.LocalDate endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Status status; //Поле может быть null
    private Person person; //Поле не может быть null

    public Worker(String name, Coordinates coordinates, LocalDateTime creationDate, double salary, LocalDate endDate, Position position, Status status, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    public double countToCompare() {
        double sum = name.length();
        sum += coordinates.count_to_compare();
        sum += salary;
        if (position != null) {
            sum += position.ordinal();
        }
        if (status != null) {
            sum += status.ordinal();
        }
        sum += person.count_to_compare();
        return sum;
    }

    public Person getPerson() {
        return person;
    }

    public double getPersonCompareValue() {
        return person.count_to_compare();
    }

    public double getCoordinatesCompareValue() {
        return coordinates.count_to_compare();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(Worker o) {
        return Double.compare(countToCompare() + id, o.countToCompare() + o.getId());
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", endDate=" + endDate +
                ", position=" + position +
                ", status=" + status +
                ", person=" + person.toString() +
                ", totalValue=" + countToCompare() +
                '}';
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Object[] getObjects(int coordsId, int personId, int userId) {
        return new Object[] { name,
                coordsId,
                salary,
                endDate != null ? Date.valueOf(endDate) : null,
                position.name(),
                status != null ? status.name() : null,
                personId,
                userId
        };
    }
}


