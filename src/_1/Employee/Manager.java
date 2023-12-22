package _1.Employee;

import java.io.Serializable;

public class Manager extends Employee implements Serializable {
    private int popularityRating;

    public Manager() {
        super();
    }

    public Manager(int index, String fullName, String position, int age, double salary, int experience,
            int vacationDays, int popularityRating) {
        super(index, fullName, position, age, salary, experience, vacationDays);
        this.popularityRating = popularityRating;
    }

    // Геттер и сеттер для popularityRating
    public int getRating() {
        return popularityRating;
    }

    public void setRating(int popularityRating) {
        this.popularityRating = popularityRating;
    }
}
