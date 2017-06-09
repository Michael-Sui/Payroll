package bean;

import java.sql.Timestamp;

/**
 * Created by Michael on 2017/6/9.
 */
public class Salary {
    private String id;
    private Timestamp time;
    private double salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
